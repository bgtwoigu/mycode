#!/usr/bin/env python
# -*- coding: utf-8 -*-
import os
import shutil
import sys
import threading
import inspect
import ctypes
import time
import datetime
import re
import random
import json
import codecs
import tempfile
import socket
import SocketServer
import Queue
import scandir
import logging


strCurrentFileDirPath = os.path.dirname(os.path.abspath(__file__))
sys.path.append(os.path.join(strCurrentFileDirPath, "lib"))
import DatabaseOperation
import TestOperation
import FileSystem
import CodecUtil
import MailOperation
import AdbOperation
import FastbootOperation
import FlashImageOperation


class ThreadedTCPRequestHandler(SocketServer.BaseRequestHandler):

    def handle(self):
        data = ""
        part_data = None
        while True:
            part_data = self.request.recv(1024)
            data += part_data
            if len(part_data) < 1024:
                break

        cur_thread = threading.current_thread()
        self.strCurThreadName = cur_thread.name
        cur_client_ip = self.client_address[0]
        try:
            cur_client_name = socket.gethostbyaddr(cur_client_ip)[0]
        except Exception:
            logger.warning("Failed To Get Host Name By IP Address, Use IP Address Directly")
            cur_client_name = cur_client_ip

        logger.info("[%s] Receive Request From: %s" % (self.strCurThreadName, cur_client_name))

        rwl.acquire_read()
        listCurrentPortalServerHostName = listPortalClientHostName[:]
        listCurrentBuildClientHostName = listBuildClientHostName[:]
        rwl.release()

        if cur_client_name in listCurrentPortalServerHostName or cur_client_name.rstrip(".oneplus.cn") in listCurrentPortalServerHostName:
            logger.info("[%s] Receive Request From Portal Server" % self.strCurThreadName)
            boolRequestValid, strTaskID = self.checkIsRequestValid(data)
            if boolRequestValid is True:
                self.request.sendall("PASS:TaskID %s" % strTaskID)
            else:
                self.request.sendall("FAIL:Wrong Request Type")
        elif cur_client_name in listCurrentBuildClientHostName or cur_client_name.rstrip(".oneplus.cn") in listCurrentBuildClientHostName:
            logger.info("[%s] Receive Build Result From Build Server" % self.strCurThreadName)
            if self.checkIsResultValid(data) is True:
                self.request.sendall("PASS")
            else:
                self.request.sendall("FAIL:Invalid Result")
        else:
            logger.warning("[%s] Request From Illegal Host, Drop" % self.strCurThreadName)
            self.request.sendall("FAIL:You Are Illegal Host")

    def checkIsRequestValid(self, jsonRequest):
        strTaskID = queueTaskID.get()
        queueTaskID.task_done()

        try:
            logger.info("[%s] Convert Json Format Arguments To Dict" % self.strCurThreadName)
            dictRequest = json.loads(jsonRequest)                # analyze request json data
            logger.info("jsonResut: %s" %dictRequest)
            strType, dictParameters = dictRequest.popitem()
            logger.info("[%s] Request Type: %s, TaskID: %s, Variable: %s" % (self.strCurThreadName, strType, strTaskID, dictParameters))
            queueTaskRequest.put((strTaskID, strType, dictParameters))
            return (True, strTaskID)
        except Exception:
            logger.exception("[%s] Exception Caught" % self.strCurThreadName)

        return (False, strTaskID)

    def checkIsResultValid(self, jsonResult):
        try:
            logger.info("[%s] Convert Json Format Arguments To Dict" % self.strCurThreadName)
            dictResult = json.loads(jsonResult)

            if "TaskID" in dictResult:
                strTaskID = dictResult["TaskID"]
                logger.info("[%s] Get Build Result For TaskID: %s" % (self.strCurThreadName, strTaskID))
                queueTaskRequest.put((strTaskID, "BuildResult", dictResult))
                return True
        except Exception:
            logger.exception("[%s] Exception Caught" % self.strCurThreadName)

        return False


class TaskFarmer(object):
    """docstring for TaskFarmer"""

    def requestListener(self):
        """
        Listener the Task Queue
        """
        while True:
            strTaskID, strRequestType, dictParameters = queueTaskRequest.get()
            thrRequestHandler = threading.Thread(target=self.requestHandler, name=strTaskID, args=(strTaskID, strRequestType, dictParameters,))
            thrRequestHandler.daemon = True
            try:
                thrRequestHandler.start()
                thrRequestHandler.join(timeout=None)
            except Exception:
                logger.exception("Exception Caught")
            finally:
                queueTaskRequest.task_done()

    def requestHandler(self, strTaskID, strRequestType, dictParameters):
        """
        judge the the type of request
        """
        if strRequestType == "UpdateTestDeviceStatus":
            logger.info("Update Test Device Status")
            dictUpdateParameters = {"Status": dictParameters["Status"]}
            dictWhereParameters = {"DeviceID": dictParameters["DeviceID"]}
            if dictParameters["DeviceID"] == "InitAll":
                dictWhereParameters.clear()

            intEffectedRows = DatabaseOperation.updateDataToTestDeviceTable(dictUpdateParameters, dictWhereParameters)
            if intEffectedRows == 0:
                if "DeviceID" in dictWhereParameters:                   
                    logger.error("Update Test Device Status Failed")
                else:
                    logger.info("All Test Device Already Inited")
            elif intEffectedRows > 1:
                logger.warning("%s devices have been updated" % intEffectedRows)

            if dictParameters["Status"] != "Down":
                queueTestCheck.put("Test Device Status Update")

        elif strRequestType == "UpdateTestDeviceInfo":
            logger.info("Update Test Device Info")
            dictWhereParameters = {"DeviceID": dictParameters["DeviceID"]}
            dictUpdateParameters = dictParameters["DeviceInfo"]    # error
            intEffectedRows = DatabaseOperation.updateDataToTestDeviceTable(dictUpdateParameters, dictWhereParameters)
            if intEffectedRows != 1:
                logger.warning("Invalid Test Device, %s Rows Effected (Should Be 1)" % intEffectedRows)

        elif strRequestType == "UpdateTaskStatus":
            logger.info("Update Task Status")
            strStatus = dictParameters["TaskStatus"]
            strTaskType = dictParameters["TaskType"]
            intEffectedRows = DatabaseOperation.updateDataToTaskTable(strTaskID, strTaskType, {"Status": strStatus})
            if intEffectedRows != 1:
                logger.warning("Invalid TaskID, %s Rows Effected (Should Be 1)" % intEffectedRows)

        elif strRequestType == "AddScheduleTask":
            logger.info("Add Schedule Task")
            self.scheduleTaskHandler("add", dictParameters)

        elif strRequestType == "RemoveScheduleTask":
            logger.info("Remove Schedule Task")
            self.scheduleTaskHandler("remove", dictParameters)

        elif strRequestType == "UpdateScheduleTaskPoint":
            logger.info("Update Schedule Task Next Running Point")
            dictUpdateParameters = {"NextPoint": str(dictParameters["NextPoint"])}
            intEffectedRows = DatabaseOperation.updateDataToTestScheduleTable(dictParameters["ID"], dictUpdateParameters)
            if intEffectedRows != 1:
                logger.warning("Invalid Schedule ID, %s Rows Effected (Should Be 1)" % intEffectedRows)

        elif strRequestType == "TestRequest":
            logger.info("Receieve Test Request")
            self.testRequestHandler(strTaskID, dictParameters)
            queueTestCheck.put("New Test Request Receieved")

        elif strRequestType == "TestResult":
            logger.info("Receieve Test Result")
            self.testResultHandler(strTaskID, dictParameters)
            queueTestCheck.put("Test Task Finished")

        elif strRequestType == "AbortTask":
            logger.info("Receieve Task Abort Request")
            strTaskID = dictParameters["TaskID"]
            self.taskAbortHandler(strTaskID)

        elif strRequestType == "TerminalServer":
            logger.info("Receieve Server Terminal Signal")
            serverReceiver.shutdown()
            serverReceiver.socket.close()
            logger.info("Server Down, Now Exit")

            intStatus = queueTaskRequest.unfinished_tasks - 1
            if intStatus > 0:
                logger.warning("There Still Task On Going, Will Do Enforce Terminal")

            os._exit(intStatus)
        else:
            logger.error("Not Supported Request Type '%s'" % strRequestType)

    def buildRequestHandler(self, strTaskID, dictParameters):
        # Todo+
        # Todo-
        return

    def buildResultHandler(self, strTaskID, dictBuildResult):
        # Todo+
        # Todo-
        return

    def testRequestHandler(self, strTaskID, dictParameters):
        """
        Action of Test Request
        """
        for key in ["info", "build", "device"]:
            if key not in dictParameters:
                logger.error("Task Parameter '%s' Missed" % key)
                DatabaseOperation.deleteDataFromTaskTable(strTaskID)
                return False

        try:
            dictCurrentTestInfo = dictParameters["info"]
            logger.info("Check Test Info Valid Or Not")
            listTargetTestScript = dictCurrentTestInfo.get("TestScriptList", [])
            for strTargetTestScriptName in listTargetTestScript:
                if os.path.isfile(os.path.join(strTestScriptPoolPath, strTargetTestScriptName)) is False:
                    logger.warning("Test Script '%s' Not Exists, Will Skip This Test" % strTargetTestScriptName)
                    listTargetTestScript.remove(strTargetTestScriptName)

            if len(listTargetTestScript) == 0:
                logger.warning("No Test Script Been Specified, Will Skip Test")
                DatabaseOperation.insertDataToTaskTable(strTaskID, "task_test_result", {"Result": "Skip", "ErrorMsg": "Test Script Not Valid"})
                self.mailReportHandler(strTaskID)
                return False

            logger.info("Add Data For task_test_info")
            if DatabaseOperation.insertDataToTaskTable(strTaskID, "task_test_info", dictCurrentTestInfo) == 0:
                logger.error("Failed To Add Data")
                DatabaseOperation.deleteDataFromTaskTable(strTaskID)
                return False

            dictCurrentTestDevice = dictParameters["device"]
            logger.info("Check Test Device Valid Or Not")
            if dictCurrentTestDevice.get("DeviceID", None) is None:
                strTargetProductArchitecture = dictCurrentTestDevice.get("ProductArchitecture", None)
                strTargetProjectCode = dictCurrentTestDevice.get("ProjectCode", None)
                if strTargetProductArchitecture is None and strTargetProjectCode is None:
                    logger.warning("No Test Device Or Test Project Been Specified, Will Skip Test")
                    DatabaseOperation.insertDataToTaskTable(strTaskID, "task_test_result", {"Result": "Skip", "ErrorMsg": "Test Device Not Valid"})
                    self.mailReportHandler(strTaskID)
                    return False
                else:
                    strWhereClause = "ProductArchitecture=\"%s\"" % strTargetProductArchitecture if strTargetProjectCode is None else "ProjectCode=\"%s\"" % strTargetProjectCode
                    strWhereClause += " AND Scope=\"Public\""

                    tupleTestDeviceData = DatabaseOperation.getDataFromTable("device_test", strFieldName="DeviceID, ProductArchitecture, ProjectCode", strWhereClause=strWhereClause)
                    if len(tupleTestDeviceData) == 0:
                        logger.warning("Can Not Find Any Test Device For '%s'-'%s', Will Skip Test" % (strTargetProductArchitecture, strTargetProjectCode))
                        DatabaseOperation.insertDataToTaskTable(strTaskID, "task_test_result", {"Result": "Skip", "ErrorMsg": "Test Device Not Valid"})
                        self.mailReportHandler(strTaskID)
                        return False

                    for testDeviceData in tupleTestDeviceData:
                        dictCurrentTestDevice["DeviceID"] = testDeviceData[0]
                        dictCurrentTestDevice["ProductArchitecture"] = testDeviceData[1]
                        dictCurrentTestDevice["ProjectCode"] = testDeviceData[2]
                        if random.randint(0, 1):
                            logger.info("Test Has Been Allocated To %s" % dictCurrentTestDevice["DeviceID"])
                            break
            else:
                tupleTestDeviceData = DatabaseOperation.getDataFromTable("device_test", strFieldName="ProductArchitecture, ProjectCode", strWhereClause="DeviceID=\"%s\"" % dictCurrentTestDevice["DeviceID"])
                if len(tupleTestDeviceData) == 0:
                    logger.warning("Invalid Test Device '%s', Will Skip Test" % dictCurrentTestDevice["DeviceID"])
                    DatabaseOperation.insertDataToTaskTable(strTaskID, "task_test_result", {"Result": "Skip", "ErrorMsg": "Test Device Not Valid"})
                    self.mailReportHandler(strTaskID)
                    return False
                else:
                    dictCurrentTestDevice["ProductArchitecture"] = tupleTestDeviceData[0][0]
                    dictCurrentTestDevice["ProjectCode"] = tupleTestDeviceData[0][1]

            logger.info("Add Data For task_test_device")
            if DatabaseOperation.insertDataToTaskTable(strTaskID, "task_test_device", dictCurrentTestDevice) == 0:
                logger.error("Failed To Add Data")
                DatabaseOperation.deleteDataFromTaskTable(strTaskID)
                return False

            logger.info("Check Test Build Valid Or Not")
            rwl.acquire_read()
            dictCurrentMountedPathInfoList = dictMountedPathInfoList.copy()
            rwl.release()

            dictCurrentTestBuild = dictParameters["build"]
            strExternalBuildPath = dictCurrentTestBuild.get("BuildPath", None)
            if strExternalBuildPath is not None:
                boolExternalBuildPathVaild = False
                strExternalBuildPath = strExternalBuildPath.replace('\\', '/')
                for dictMountedPathInfo in dictCurrentMountedPathInfoList.get("BuildShare", []):
                    if strExternalBuildPath.startswith(dictMountedPathInfo["ExternalPath"]):
                        boolExternalBuildPathVaild = True
                        break

                if boolExternalBuildPathVaild is True:
                    ma = re.match(r'.+/(%s[^/]*)/([^/]+)/*([^/]*)/(.+)/(.+)$' % dictCurrentTestDevice["ProjectCode"], strExternalBuildPath)
                    if ma:
                        dictCurrentTestBuild["BuildType"] = ma.group(2)
                        dictCurrentTestBuild["BuildModule"] = ma.group(3)
                        dictCurrentTestBuild["BuildOS"] = ma.group(4)
                        dictCurrentTestBuild["BuildVersion"] = ma.group(5)
                    else:
                        boolExternalBuildPathVaild = False

                if boolExternalBuildPathVaild is False:
                    logger.warning("Invalid Build Path '%s', Will Skip Test" % strExternalBuildPath)
                    DatabaseOperation.insertDataToTaskTable(strTaskID, "task_test_result", {"Result": "Skip", "ErrorMsg": "Test Build Path Not Valid"})
                    self.mailReportHandler(strTaskID)
                    return False
            else:
                # Have Performance Issue, Will Block The Main Thread+
                logger.info("Try To Find Build Meets User's Specified")
                strTargetBuildOS = dictCurrentTestBuild.get("BuildOS", None)
                strTargetBuildVersion = dictCurrentTestBuild.get("BuildVersion", r'[^/]+')
                strTargetBuildType = dictCurrentTestBuild.get("BuildType", r'[^/]+')
                strTargetBuildModule = dictCurrentTestBuild.get("BuildModule", r'[^/]*')

                if strTargetBuildOS is None:
                    logger.warning("No Test OS Been Specified, Will Skip Test")
                    DatabaseOperation.insertDataToTaskTable(strTaskID, "task_test_result", {"Result": "Skip", "ErrorMsg": "Test Build Info Not Valid"})
                    self.mailReportHandler(strTaskID)
                    return False

                try:
                    for dictMountedPathInfo in dictCurrentMountedPathInfoList.get("BuildShare", []):
                        strCurrentInternalBuildRootPath = dictMountedPathInfo["InternalPath"]
                        strCurrentExternalBuildRootPath = dictMountedPathInfo["ExternalPath"]
                        for root, dirs, files in scandir.walk(strCurrentInternalBuildRootPath, topdown=True):
                            dirs.sort(reverse=True)
                            for strDirName in dirs:
                                strCurrentDirPath = os.path.join(root, strDirName).replace('\\', '/')
                                ma = re.match(r'.+/(%s[^/]*)/(%s)/*(%s)/(%s)/([^/]*%s[^/]*)$' % (dictCurrentTestDevice["ProjectCode"], strTargetBuildType, strTargetBuildModule, strTargetBuildOS, strTargetBuildVersion), strCurrentDirPath)
                                if ma:
                                    logger.info("Build Found'%s'" % strCurrentDirPath)
                                    if strCurrentDirPath.endswith("ERROR"):
                                        logger.info("This Build Not Good, Skip")
                                        continue
                                    strExternalBuildPath = strCurrentDirPath.replace(strCurrentInternalBuildRootPath, strCurrentExternalBuildRootPath)
                                    dictCurrentTestBuild["BuildType"] = ma.group(2)
                                    dictCurrentTestBuild["BuildModule"] = ma.group(3)
                                    dictCurrentTestBuild["BuildOS"] = ma.group(4)
                                    dictCurrentTestBuild["BuildVersion"] = ma.group(5)
                                    dictCurrentTestBuild["BuildPath"] = strExternalBuildPath
                                    break
                            if strExternalBuildPath is not None:
                                break
                        if strExternalBuildPath is not None:
                                break
                except Exception:
                    logger.exception("Exception Caught")
                    strExternalBuildPath = None

                if strExternalBuildPath is None:
                    logger.warning("Cannot Find Any Build Meets '%s %s %s %s', Will Skip Test" % (strTargetBuildType, strTargetBuildModule, strTargetBuildOS, strTargetBuildVersion))
                    DatabaseOperation.insertDataToTaskTable(strTaskID, "task_test_result", {"Result": "Skip", "ErrorMsg": "No Test Build Find"})
                    self.mailReportHandler(strTaskID)
                    return False
                # Have Performance Issue, Will Block The Main Thread-

            logger.info("Add Data For task_test_build")
            if DatabaseOperation.insertDataToTaskTable(strTaskID, "task_test_build", dictCurrentTestBuild) == 0:
                logger.error("Failed To Add Data")
                DatabaseOperation.deleteDataFromTaskTable(strTaskID)
                return False

            logger.info("Create Test Temp Folder")
            strCurrentTestTempPath = os.path.join(strTestQueuepoolPath, strTaskID)
            if os.path.isdir(strCurrentTestTempPath) is True:
                logger.warning("Test Folder Already Exists, Remove")
                FileSystem.deleteDir(strCurrentTestTempPath)

            os.makedirs(strCurrentTestTempPath)

            logger.info("Add Data For task_test_result")
            if DatabaseOperation.insertDataToTaskTable(strTaskID, "task_test_result", {"Result": None}) == 0:
                logger.error("Failed To Add Data")
                DatabaseOperation.deleteDataFromTaskTable(strTaskID)
                return False

            logger.info("Add Test Task To Queue")
            myTestManager.listTestRequest.append(strTaskID)
            return True
        except Exception:
            logger.exception("Exception Caught")
            DatabaseOperation.deleteDataFromTaskTable(strTaskID)
            return False

    def testResultHandler(self, strTaskID, dictTestTaskResult):
        strCurrentTestTempPath = os.path.join(strTestQueuepoolPath, strTaskID)
        strCurrentTestResultPath = strCurrentTestTempPath

        if strTaskID in myTestManager.dictTestThreadID:
            del myTestManager.dictTestThreadID[strTaskID]

        logger.info("Upload Test Temp Folder To Share Folder")
        rwl.acquire_read()
        dictCurrentMountedPathInfoList = dictMountedPathInfoList.copy()
        rwl.release()

        listShareFolderInfo = dictCurrentMountedPathInfoList.get("ResultShare", [])
        if len(listShareFolderInfo) > 0:
            strShareFolderInternalPath = listShareFolderInfo[0]["InternalPath"]
            if FileSystem.copyDir(strCurrentTestTempPath, strShareFolderInternalPath) is True:
                logger.info("Upload Finished, Remove Test Temp Folder")
                strCurrentTestResultPath = os.path.join(strShareFolderInternalPath, strTaskID)
                FileSystem.deleteDir(strCurrentTestTempPath)
            else:
                logger.warning("Failed To Upload Test Temp Folder, Will Remain The Tmp Folder")
        else:
            logger.warning("No Share Folder Found, Will Remain The Tmp Folder")

        logger.info("Update Test Device Status To Database")
        intEffectedRows = DatabaseOperation.updateDataToTestDeviceTable(dictUpdateParameters={"Status": "Up"}, dictWhereParameters={"DeviceID": dictTestTaskResult["DeviceID"]})
        if intEffectedRows != 1:
            logger.warning("Invalid TaskID, %s Rows Effected (Should Be 1)" % intEffectedRows)

        logger.info("Update Task Status To Database")
        intEffectedRows = DatabaseOperation.updateDataToTaskTable(strTaskID, "task_test_info", {"Status": "Finish"})
        if intEffectedRows != 1:
            logger.warning("Invalid TaskID, %s Rows Effected (Should Be 1)" % intEffectedRows)

        logger.info("Update Test Result To Database")
        if dictTestTaskResult["Result"] is True:
            intEffectedRows = DatabaseOperation.updateDataToTaskTable(strTaskID, "task_test_result", {"Result": "Pass", "ResultPath": strCurrentTestResultPath})
        else:
            intEffectedRows = DatabaseOperation.updateDataToTaskTable(strTaskID, "task_test_result", {"Result": "Fail", "ResultPath": strCurrentTestResultPath, "ErrorMsg": dictTestTaskResult["ErrorMsg"]})
        if intEffectedRows != 1:
            logger.warning("Invalid TaskID, %s Rows Effected (Should Be 1)" % intEffectedRows)

        logger.info("Update Test Summary To Database")
        dictTotalResultSummarize = dictTestTaskResult["TestSummarize"]
        if len(dictTotalResultSummarize) > 0:
            intEffectedRows = DatabaseOperation.updateDataToTaskTable(strTaskID, "task_test_result", {"TestSummarize": dictTotalResultSummarize})
            if intEffectedRows != 1:
                logger.warning("Invalid TaskID, %s Rows Effected (Should Be 1)" % intEffectedRows)

        logger.info("Update Test Details To Database")
        dictTestResultDetails = dictTestTaskResult["TestDetails"]
        if len(dictTestResultDetails) > 0:
            intEffectedRows = DatabaseOperation.updateDataToTaskTable(strTaskID, "task_test_result", {"TestDetails": dictTestResultDetails})
            if intEffectedRows != 1:
                logger.warning("Invalid TaskID, %s Rows Effected (Should Be 1)" % intEffectedRows)

        logger.info("Update Test Issue List To Database")
        dictIssueList = dictTestTaskResult["IssueList"]
        if len(dictIssueList) > 0:
            intEffectedRows = DatabaseOperation.updateDataToTaskTable(strTaskID, "task_test_result", {"IssueList": dictIssueList})
            if intEffectedRows != 1:
                logger.warning("Invalid TaskID, %s Rows Effected (Should Be 1)" % intEffectedRows)

        logger.info("Update Test Report Image To Database")
        listReportImage = dictTestTaskResult["ReportImage"]
        if len(listReportImage) > 0:
            for dictReportImage in listReportImage:
                dictReportImage["Path"] = dictReportImage["Path"].replace(strCurrentTestTempPath, strCurrentTestResultPath)

            intEffectedRows = DatabaseOperation.updateDataToTaskTable(strTaskID, "task_test_result", {"ReportImage": listReportImage})
            if intEffectedRows != 1:
                logger.warning("Invalid TaskID, %s Rows Effected (Should Be 1)" % intEffectedRows)

        logger.info("Task %s Finished, Generate Report To Send" % strTaskID)
        self.mailReportHandler(strTaskID)

    def mailReportHandler(self, strTaskID):
        try:
            logger.info("Get Task Info From Database")
            dictTaskInfo = {"CreateTime": None, "Submitter": None, "Purpose": None, "ProjectName": None}
            for key in dictTaskInfo:
                tupleValue = DatabaseOperation.getDataFromTable("task_test_info", strFieldName=key, strWhereClause="TaskID=\"%s\"" % strTaskID)
                dictTaskInfo[key] = tupleValue[0][0]

            dictTaskInfo["TaskID"] = strTaskID
            dictTaskInfo["MailList"] = []
            strMailTo = DatabaseOperation.getDataFromTable("task_test_info", strFieldName="MailList", strWhereClause="TaskID=\"%s\"" % strTaskID)[0][0]
            if strMailTo is not None:
                listMailTo = json.loads(strMailTo, encoding='utf-8', strict=False)
                dictTaskInfo["MailList"] = listMailTo

            logger.info("Get Build Info From Database")
            logger.warning("tast_test_build insert is after mailReport, need to repair-(add by libin)")            
            dictBuildInfo = {"BuildOS": None, "BuildVersion": None, "BuildType": None, "BuildModule": None, "BuildPath": None}
            for key in dictBuildInfo:
                tupleValue = DatabaseOperation.getDataFromTable("task_test_build", strFieldName=key, strWhereClause="TaskID=\"%s\"" % strTaskID)
                if tupleValue:
                    dictBuildInfo[key] = tupleValue[0][0]
                else:
                    logger.warning("mailReport Get Build Info From DataBase Fail: %s" %tupleValue)       # add debug message by libin

            logger.info("Get Test Result From Database")
            dictTestResult = None
            dictTestInfo = {"Result": None, "ErrorMsg": None}
            for key in dictTestInfo:
                tupleValue = DatabaseOperation.getDataFromTable("task_test_result", strFieldName=key, strWhereClause="TaskID=\"%s\"" % strTaskID)
                dictTestInfo[key] = tupleValue[0][0]

            dictTestInfo["DeviceID"] = DatabaseOperation.getDataFromTable("task_test_device", strFieldName="DeviceID", strWhereClause="TaskID=\"%s\"" % strTaskID)[0][0]
            dictTestInfo["ProductArchitecture"] = DatabaseOperation.getDataFromTable("task_test_device", strFieldName="ProductArchitecture", strWhereClause="TaskID=\"%s\"" % strTaskID)[0][0]
            dictTestInfo["ProjectCode"] = DatabaseOperation.getDataFromTable("task_test_device", strFieldName="ProjectCode", strWhereClause="TaskID=\"%s\"" % strTaskID)[0][0]

            dictTestSummarize = {"Total": {"Pass": 0, "Fail": 0, "Skip": 0, "Abort": 0}}
            tupleTestSummarize = DatabaseOperation.getDataFromTable("task_test_result", strFieldName="TestSummarize", strWhereClause="TaskID=\"%s\"" % strTaskID)
            if tupleTestSummarize[0][0] is not None:
                dictTestSummarize = json.loads(tupleTestSummarize[0][0], encoding='utf-8', strict=False)

            dictTestIssueList = {}
            tupleTestIssue = DatabaseOperation.getDataFromTable("task_test_result", strFieldName="IssueList", strWhereClause="TaskID=\"%s\"" % strTaskID)
            if tupleTestIssue[0][0] is not None:
                dictTestIssueList = json.loads(tupleTestIssue[0][0], encoding='utf-8', strict=False)

            strInternalTestResultPath = DatabaseOperation.getDataFromTable("task_test_result", strFieldName="ResultPath", strWhereClause="TaskID=\"%s\"" % strTaskID)[0][0]
            strExternalTestResultPath = strInternalTestResultPath

            rwl.acquire_read()
            dictCurrentMountedPathInfoList = dictMountedPathInfoList.copy()
            rwl.release()

            for dictMountedPathInfo in dictCurrentMountedPathInfoList.get("ResultShare", []):
                if strInternalTestResultPath.startswith(dictMountedPathInfo["InternalPath"]):
                    strExternalTestResultPath = strExternalTestResultPath.replace(dictMountedPathInfo["InternalPath"], dictMountedPathInfo["ExternalPath"])
                    break

            dictTestInfo["ResultPath"] = strExternalTestResultPath

            listReportImage = []
            tupleReportImage = DatabaseOperation.getDataFromTable("task_test_result", strFieldName="ReportImage", strWhereClause="TaskID=\"%s\"" % strTaskID)
            if tupleReportImage[0][0] is not None:
                listReportImage = json.loads(tupleReportImage[0][0], encoding='utf-8', strict=False)

            for dictReportImage in listReportImage:
                strReportImagePath = dictReportImage.get("Path", None)
                if strReportImagePath is not None:
                    dictReportImage["Path"] = os.path.join(strInternalTestResultPath, strReportImagePath)
                else:
                    logger.warning("Report Image '%s' Not Valid, Ignore" % str(dictReportImage))

                dictTestResult = {"TestInfo": dictTestInfo, "TestSummarize": dictTestSummarize, "IssueList": dictTestIssueList, "ReportImage": listReportImage}

            logger.info("Make Sure No Unicode")
            dictTaskInfo = CodecUtil.transferUnicodeToStr(dictTaskInfo)
            dictBuildInfo = CodecUtil.transferUnicodeToStr(dictBuildInfo)
            dictTestResult = CodecUtil.transferUnicodeToStr(dictTestResult)

            strReportTitle = dictTaskInfo["ProjectName"] + dictTaskInfo["Purpose"]
            myMail = MailOperation.MailConstruct(dictTaskInfo, dictBuildInfo, dictTestResult)
        except Exception:
            logger.exception("Exception Caught")
        else:
            logger.info("Upload Report To Share Folder")
            try:
                tmpReportFile = tempfile.NamedTemporaryFile(mode='w+b', delete=False)
                tmpReportFile.write(myMail.msgBody)
                strtmpReportFilePath = tmpReportFile.name
                strUploadFolder = os.path.join(strInternalTestResultPath, "Report")
                tmpReportFile.close()

                logger.info("Create Share Report Folder")
                os.makedirs(strUploadFolder)
                if os.path.isdir(strUploadFolder) is False:
                    logger.warning("Create Report Folder Failed, Will Upload The Report To The Result Folder Directly")
                    strUploadFolder = strInternalTestResultPath

                strUploadPath = os.path.join(strUploadFolder, "%s-%s.html" % (strTaskID, strReportTitle.replace(" ", "")))
                shutil.copy2(strtmpReportFilePath, strUploadPath)
            except Exception:
                logger.exception("Exception Caught")
            finally:
                if os.path.isfile(strtmpReportFilePath):
                    os.remove(strtmpReportFilePath)

            logger.info("Start A New Thread For Mail Send")
            thrSendMail = threading.Thread(target=myMail.sendMail, name=strTaskID)
            thrSendMail.daemon = False
            thrSendMail.start()

    def scheduleTaskHandler(self, strRequestType, dictParameters):
        if strRequestType.lower() == "add":
            try:
                if dictParameters.get("NextPoint", None) is None:
                    dictParameters["NextPoint"] = str(datetime.datetime.today().replace(minute=0, second=0, microsecond=0))

                if DatabaseOperation.insertDataToTestScheduleTable(dictParameters) == 1:
                    return True
                logger.error("Failed To Add Data")
            except Exception:
                logger.exception("Exception Caught")

        if strRequestType.lower() == "remove":
            try:
                strID = dictParameters.get("ID", None)
                DatabaseOperation.deleteDataToTestScheduleTable(strID)
                return True
            except Exception:
                logger.exception("Exception Caught")

        return False

    def taskAbortHandler(self, strTaskID):
        logger.info("Check Task '%s' Is Valid Or Not" % strTaskID)
        if DatabaseOperation.checkIsTaskExistsInTaskTable("task_test_info", strTaskID) is True:
            logger.info("Task '%s' Is A Test Task" % strTaskID)
            strTaskStatus = DatabaseOperation.getDataFromTable("task_test_info", strFieldName="Status", strWhereClause="TaskID=\"%s\"" % strTaskID)[0][0]
            if strTaskStatus == "Finish":
                logger.info("Task '%s' Already Finished" % strTaskID)
                return True
            myTestManager.testTaskKiller(strTaskID)
            return True

        if DatabaseOperation.checkIsTaskExistsInTaskTable("task_build_info", strTaskID) is True:
            logger.info("Task '%s' Is A Build Task" % strTaskID)
            strTaskStatus = DatabaseOperation.getDataFromTable("task_build_info", strFieldName="Status", strWhereClause="TaskID=\"%s\"" % strTaskID)[0][0]
            if strTaskStatus in ["Pending", "Ongoing"]:
                # Todo+
                # Todo-
                return True

        logger.warning("Task '%s' Not Exists" % strTaskID)
        return False


class TestManager(object):
    """docstring for TestManager"""

    def __init__(self):
        self.listTestRequest = []
        self.dictTestThreadID = {}
        self.dictTestDevicePending = {}
        self.checkTestTaskNotRun()

    def _async_raise(self, tid, exctype):
        '''Raises an exception in the threads with id tid'''

        if not inspect.isclass(exctype):
            raise TypeError("Only types can be raised (not instances)")

        res = ctypes.pythonapi.PyThreadState_SetAsyncExc(ctypes.c_long(tid), ctypes.py_object(exctype))
        if res == 0:
            raise ValueError("invalid thread id")
        elif res != 1:
            # "if it returns a number greater than one, you're in trouble,
            # and you should call it again with exc=NULL to revert the effect"
            ctypes.pythonapi.PyThreadState_SetAsyncExc(ctypes.c_long(tid), 0)
            raise SystemError("PyThreadState_SetAsyncExc failed")

    def testTaskListener(self):
        DEVICE_INVALID = 0
        DEVICE_UP_BUSY = 1
        DEVICE_UP_IDLE = 2
        DEVICE_DOWN = 3

        while True:
            strCheckReason = queueTestCheck.get()
            logger.info("Check Test Task Since %s" % strCheckReason)

            if len(self.listTestRequest) > 0:
                for strTaskID in self.listTestRequest:
                    dictTestTaskResult = {"Result": False, "ErrorMsg": None, "TestSummarize": {}, "IssueList": {}, "ReportImage": []}
                    strCurrentTestTempPath = os.path.join(strTestQueuepoolPath, strTaskID)
                    dictCurrentTestTaskData = {"TempPath": strCurrentTestTempPath}

                    try:
                        logger.info("Get Necessary Info From Database")
                        strCurrentTestProjectName = DatabaseOperation.getDataFromTable("task_test_info", strFieldName="ProjectName", strWhereClause="TaskID=\"%s\"" % strTaskID)[0][0]
                        strCurrentTestProjectKey = DatabaseOperation.getDataFromTable("map_jira_project", strFieldName="ProjectKey", strWhereClause="ProjectName=\"%s\"" % strCurrentTestProjectName)[0][0]
                        strCurrentTsetDeviceID = DatabaseOperation.getDataFromTable("task_test_device", strFieldName="DeviceID", strWhereClause="TaskID=\"%s\"" % strTaskID)[0][0]
                        strCurrentTestBuildPath = DatabaseOperation.getDataFromTable("task_test_build", strFieldName="BuildPath", strWhereClause="TaskID=\"%s\"" % strTaskID)[0][0]
                        listCurrentTestBuildImageIgnore = DatabaseOperation.getDataFromTable("task_test_build", strFieldName="ImageIgnore", strWhereClause="TaskID=\"%s\"" % strTaskID)[0][0]
                        listCurrentTestBuildImageIgnore = [] if listCurrentTestBuildImageIgnore is None else json.loads(listCurrentTestBuildImageIgnore, encoding='utf-8', strict=False)
                        boolCurrentTestBuildForceFlash = DatabaseOperation.getDataFromTable("task_test_build", strFieldName="ForceFlash", strWhereClause="TaskID=\"%s\"" % strTaskID)[0][0]
                        listCurrentTestScriptList = json.loads(DatabaseOperation.getDataFromTable("task_test_info", strFieldName="TestScriptList", strWhereClause="TaskID=\"%s\"" % strTaskID)[0][0], encoding='utf-8', strict=False)
                        dictCurrentTestParameterList = DatabaseOperation.getDataFromTable("task_test_info", strFieldName="TestParameterList", strWhereClause="TaskID=\"%s\"" % strTaskID)[0][0]
                        dictCurrentTestParameterList = {} if dictCurrentTestParameterList is None else json.loads(dictCurrentTestParameterList, encoding='utf-8', strict=False)
                        dictCurrentTestBuildInfo = {"BuildOS": None, "BuildVersion": None, "BuildType": None, "BuildModule": None}
                        for key in dictCurrentTestBuildInfo:
                            tupleValue = DatabaseOperation.getDataFromTable("task_test_build", strFieldName=key, strWhereClause="TaskID=\"%s\"" % strTaskID)
                            dictCurrentTestBuildInfo[key] = tupleValue[0][0]

                        dictCurrentTestTaskData["TestProjectKey"] = strCurrentTestProjectKey
                        dictCurrentTestTaskData["TestDeviceID"] = strCurrentTsetDeviceID
                        dictCurrentTestTaskData["TestBuildPath"] = strCurrentTestBuildPath
                        dictCurrentTestTaskData["TestBuildInfo"] = dictCurrentTestBuildInfo
                        dictCurrentTestTaskData["FlashImageIgnore"] = listCurrentTestBuildImageIgnore
                        dictCurrentTestTaskData["ForceFlashImage"] = bool(boolCurrentTestBuildForceFlash)
                        dictCurrentTestTaskData["TestScriptList"] = listCurrentTestScriptList
                        dictCurrentTestTaskData["TestParameterList"] = dictCurrentTestParameterList
                    except Exception, e:
                        logger.exception("Exception Caught")
                        dictTestTaskResult["ErrorMsg"] = str(e)
                        queueTaskRequest.put((strTaskID, "TestResult", dictTestTaskResult))
                    else:
                        intTestDeviceStatus = self.testDeviceStatusCheck(strCurrentTsetDeviceID)
                        if intTestDeviceStatus == DEVICE_INVALID:
                            logger.error("Invalid Test Device '%s'" % strCurrentTsetDeviceID)
                            dictTestTaskResult["ErrorMsg"] = "Test Device Invalid"
                            queueTaskRequest.put((strTaskID, "TestResult", dictTestTaskResult))
                        elif intTestDeviceStatus == DEVICE_UP_IDLE:
                            logger.info("Test Device Up, Will Start A New Test Thread")
                            queueTaskRequest.put((strTaskID, "UpdateTestDeviceStatus", {"Status": "Busy", "DeviceID": strCurrentTsetDeviceID}))
                            queueTaskRequest.put((strTaskID, "UpdateTaskStatus", {"TaskType": "task_test_info", "TaskStatus": "Ongoing"}))

                            thrCurrentTest = self.TestThread(strTaskID, dictCurrentTestTaskData)
                            thrCurrentTest.daemon = False
                            thrCurrentTest.start()
                            self.dictTestThreadID[strTaskID] = thrCurrentTest.ident
                        elif intTestDeviceStatus == DEVICE_UP_BUSY:
                            logger.info("Test Device '%s' Is In Used" % strCurrentTsetDeviceID)
                            continue
                        elif intTestDeviceStatus == DEVICE_DOWN:
                            logger.info("Test Device '%s' Is Down, Add To Watcher" % strCurrentTsetDeviceID)
                            queueTaskRequest.put((strTaskID, "UpdateTestDeviceStatus", {"Status": "Down", "DeviceID": strCurrentTsetDeviceID}))
                            self.dictTestDevicePending[strTaskID] = strCurrentTsetDeviceID
                            continue

                    self.listTestRequest.remove(strTaskID)

            queueTestCheck.task_done()

    def testDeviceStatusCheck(self, strTestDeviceID):
        DEVICE_INVALID = 0
        DEVICE_UP_BUSY = 1
        DEVICE_UP_IDLE = 2
        DEVICE_DOWN = 3

        logger.info("Get Test Device Status")
        tupleTestDeviceStatus = DatabaseOperation.getDataFromTable("device_test", strFieldName="Status", strWhereClause="DeviceID=\"%s\"" % strTestDeviceID)
        if len(tupleTestDeviceStatus) == 0:
            return DEVICE_INVALID

        strTestDeviceStatus = tupleTestDeviceStatus[0][0]
        if strTestDeviceStatus == "Up":
            if AdbOperation.isDeviceExists(strTestDeviceID) or FastbootOperation.isDeviceExists(strTestDeviceID):
                return DEVICE_UP_IDLE
            else:
                return DEVICE_DOWN
        elif strTestDeviceStatus == "Busy":
            return DEVICE_UP_BUSY
        elif strTestDeviceStatus == "Down":
            return DEVICE_DOWN

        return DEVICE_INVALID

    def testDeviceStatusWatchdog(self):
        while True:
            for strTaskID in self.dictTestDevicePending:
                try:
                    strTestDeviceID = self.dictTestDevicePending[strTaskID]
                    if AdbOperation.isDeviceExists(strTestDeviceID) or FastbootOperation.isDeviceExists(strTestDeviceID):
                        queueTaskRequest.put((strTaskID, "UpdateTestDeviceStatus", {"Status": "Up", "DeviceID": strTestDeviceID}))
                        del self.dictTestDevicePending[strTaskID]
                except Exception:
                    logger.exception("Exception Caught")

            time.sleep(3000)

    def checkTestTaskNotRun(self):
        for strFolderName in os.listdir(strTestQueuepoolPath):
            try:
                strCurrentTestTempPath = os.path.join(strTestQueuepoolPath, strFolderName)
                strTaskID = strFolderName
                if DatabaseOperation.checkIsTaskExistsInTaskTable("task_test_info", strTaskID) is False:
                    logger.error("Task '%s' Not Exists, Will Delete This Test Folder" % strTaskID)
                    FileSystem.deleteDir(strCurrentTestTempPath)
                    continue

                strTaskStatus = DatabaseOperation.getDataFromTable("task_test_info", strFieldName="Status", strWhereClause="TaskID=\"%s\"" % strTaskID)[0][0]
                if strTaskStatus == "Finish":
                    logger.info("Task '%s' Already Finished, Will Delete This Test Folder" % strTaskID)
                    FileSystem.deleteDir(strCurrentTestTempPath)
                    continue

                logger.info("Add Unfinished Test Task '%s' To Queue" % strTaskID)
                self.listTestRequest.append(strTaskID)
            except Exception:
                logger.exception("Exception Caught")
                FileSystem.deleteDir(strCurrentTestTempPath)

        strTaskID = queueTaskID.get()
        queueTaskID.task_done()
        queueTaskRequest.put((strTaskID, "UpdateTestDeviceStatus", {"Status": "Up", "DeviceID": "InitAll"}))

    def testTaskKiller(self, strTaskID):
        logger.info("Try To Abort Test Task '%s'" % strTaskID)
        if strTaskID in self.listTestRequest:
            logger.info("Test Task Found In Queue, Remove")
            self.listTestRequest.remove(strTaskID)
            dictTestTaskResult = {"Result": False, "ErrorMsg": "User Abort", "IssueList": {}, "TestSummarize": {}, "ReportImage": []}
            queueTaskRequest.put((strTaskID, "TestResult", dictTestTaskResult))

        if strTaskID in self.dictTestThreadID:
            logger.info("Test Task Is Ongoing, Kill Thread '%s'" % self.dictTestThreadID[strTaskID])
            try:
                self._async_raise(self.dictTestThreadID[strTaskID], SystemExit)
            except Exception:
                logger.exception("Exception Caught")
            else:
                logger.info("Test Task '%s' Should Be Joined In Seconds" % strTaskID)

    class TestThread(threading.Thread):
        """
        docstring for TestThread
        excute test script
        """

        def __init__(self, strTaskID, dictTestTaskData):
            self._running_flag = False
            super(TestManager.TestThread, self).__init__(target=self.testTaskRunner, name=strTaskID, args=(strTaskID, dictTestTaskData, ))

        def testTaskRunner(self, strTaskID, dictTestTaskData):
            try:
                strTestProjectKey = dictTestTaskData["TestProjectKey"]
                strTsetDeviceID = dictTestTaskData["TestDeviceID"]
                boolForceFlashBuild = dictTestTaskData["ForceFlashImage"]
                dictTestBuildInfo = dictTestTaskData["TestBuildInfo"]
                strTestBuildPath = dictTestTaskData["TestBuildPath"]
                listFlashImageIgnore = dictTestTaskData["FlashImageIgnore"]
                listTestScriptList = dictTestTaskData["TestScriptList"]
                dictTestParameterList = dictTestTaskData["TestParameterList"]
                dictTestTaskResult = {"DeviceID": strTsetDeviceID, "Result": False, "ErrorMsg": None, "TestSummarize": {}, "IssueList": {}, "ReportImage": [], "TestDetails": {}}
                strTestTempPath = os.path.join(strTestQueuepoolPath, strTaskID)

                rwl.acquire_read()
                dictCurrentMountedPathInfoList = dictMountedPathInfoList.copy()
                rwl.release()

                strTestBuildPath = strTestBuildPath.replace('\\', '/')
                for dictMountedPathInfo in dictCurrentMountedPathInfoList.get("BuildShare", []):
                    if strTestBuildPath.startswith(dictMountedPathInfo["ExternalPath"]):
                        strTestBuildPath = strTestBuildPath.replace(dictMountedPathInfo["ExternalPath"], dictMountedPathInfo["InternalPath"])
                        break

                if boolForceFlashBuild is True:
                    if FlashImageOperation.flashJenkinsBuild(strTsetDeviceID, strTestBuildPath, listFlashImageIgnore) is False:
                        dictTestTaskResult["ErrorMsg"] = "Failed To Flash Test Build"
                        logger.error(dictTestTaskResult["ErrorMsg"])

                if dictTestTaskResult["ErrorMsg"] is None:
                    queueTaskRequest.put((strTaskID, "UpdateTestDeviceInfo", {"DeviceID": strTsetDeviceID, "DeviceInfo": dictTestBuildInfo}))
                    try:
                        logger.info("Make Sure No Unicode")
                        logger.info("Script: %s, Variable: %s" %(listTestScriptList, dictTestParameterList))
                        listTestScriptList = CodecUtil.transferUnicodeToStr(listTestScriptList)
                        dictTestParameterList = CodecUtil.transferUnicodeToStr(dictTestParameterList)
                        logger.info("transferUnicode-Script: %s, Variable: %s" %(listTestScriptList, dictTestParameterList))

                        currentTestTask = TestOperation.TestRunner(strTestTempPath, strTsetDeviceID, strTestProjectKey, listTestScriptList, dictTestParameterList, strTestScriptPoolPath) 
                        boolResult, strLastError, dictTestSummarize, dictIssueList, listReportImage, dictTestDetails = currentTestTask.runTest()    # excute test script
                    except Exception, e:
                        logger.exception("Exception Caught")
                        dictTestTaskResult["ErrorMsg"] = str(e)
                    else:
                        dictTestTaskResult["Result"] = boolResult
                        dictTestTaskResult["ErrorMsg"] = strLastError
                        dictTestTaskResult["TestSummarize"] = dictTestSummarize
                        dictTestTaskResult["IssueList"] = dictIssueList
                        dictTestTaskResult["ReportImage"] = listReportImage
                        dictTestTaskResult["TestDetails"] = dictTestDetails
            except SystemExit:
                logger.info("User Abort The Test")
                dictTestTaskResult["ErrorMsg"] = "User Abort"
            except Exception, e:
                logger.exception("Exception Caught")
                dictTestTaskResult["ErrorMsg"] = str(e)
            finally:
                logger.info("Test Finished")
                queueTaskRequest.put((strTaskID, "TestResult", dictTestTaskResult))
                self._running_flag = False


class ScheduleManager(threading.Thread):
    """docstring for ScheduleManager"""

    def __init__(self):
        threading.Thread.__init__(self)
        self.stopped = threading.Event()

    def scheduleTaskTicker(self):
        intNextSecsGap = 0
        while not self.stopped.wait(intNextSecsGap):
            logger.info("Time To Do Schedule Check")
            self.scheduleTaskCheck()

            datetimeNow = datetime.datetime.today()
            intNextDay = datetimeNow.day
            intNextHour = datetimeNow.hour + 1
            while intNextHour > 23:
                intNextHour -= 24
                intNextDay += 1

            datetimeNextOclock = datetimeNow.replace(day=intNextDay, hour=intNextHour, minute=0, second=0, microsecond=0)
            deltaGap = datetimeNextOclock - datetimeNow
            intNextSecsGap = deltaGap.seconds + 1
            logger.info("Next Schedule Check On %s" % str(datetimeNextOclock))

    def scheduleTaskCheck(self):
        listScheduleNum = []
        tupleScheduleNum = DatabaseOperation.getDataFromTable("schedule_task_test", strFieldName="ID")
        for tupleNum in tupleScheduleNum:
            listScheduleNum.append(tupleNum[0])

        logger.info("There %s Task Scheduled" % len(listScheduleNum))
        for intNum in listScheduleNum:
            try:
                logger.info("Get Schedule Status Info")
                tupleScheduleStatusData = DatabaseOperation.getDataFromTable("schedule_task_test", strFieldName="Status, ScheduleCycle, NextPoint", strWhereClause="ID=\"%s\"" % intNum)
                strScheduleStatus = tupleScheduleStatusData[0][0]
                intScheduleCycle = tupleScheduleStatusData[0][1]
                datetimeNextPoint = tupleScheduleStatusData[0][2]

                datetimeCurrentPoint = datetime.datetime.now()
                if strScheduleStatus == "Active" and datetimeCurrentPoint >= datetimeNextPoint:
                    logger.info("Schedule Task ID-%s Active And Time To Run" % intNum)
                    datetimeNextPoint = datetimeNextPoint + datetime.timedelta(hours=intScheduleCycle)
                    dictInfo = {"Submitter": None, "Purpose": None, "MailList": None, "TestScriptList": None, "TestParameterList": None, "ProjectName": None}
                    dictBuild = {"BuildPath": None, "ForceFlash": True}
                    dictDevice = {"DeviceID": None, "ProductArchitecture": None, "ProjectCode": None}

                    logger.info("Get Schedule Basic Info")
                    tupleScheduleBasicData = DatabaseOperation.getDataFromTable("schedule_task_test", strFieldName="Submitter, Purpose, MailList", strWhereClause="ID=\"%s\"" % intNum)
                    dictInfo["Submitter"] = tupleScheduleBasicData[0][0]
                    dictInfo["Purpose"] = tupleScheduleBasicData[0][1]
                    dictInfo["MailList"] = [] if tupleScheduleBasicData[0][2] is None else json.loads(tupleScheduleBasicData[0][2], encoding='utf-8', strict=False)

                    logger.info("Get Schedule Test Info")
                    tupleScheduleTestData = DatabaseOperation.getDataFromTable("schedule_task_test", strFieldName="ProjectName, TestScriptList, TestParameterList", strWhereClause="ID=\"%s\"" % intNum)
                    dictInfo["ProjectName"] = tupleScheduleTestData[0][0]
                    dictInfo["TestScriptList"] = [] if tupleScheduleTestData[0][1] is None else json.loads(tupleScheduleTestData[0][1], encoding='utf-8', strict=False)
                    dictInfo["TestParameterList"] = {} if tupleScheduleTestData[0][2] is None else json.loads(tupleScheduleTestData[0][2], encoding='utf-8', strict=False)

                    logger.info("Get Schedule Build Info")
                    tupleScheduleBuildData = DatabaseOperation.getDataFromTable("schedule_task_test", strFieldName="BuildPath, FlashMode, DeviceID", strWhereClause="ID=\"%s\"" % intNum)
                    strScheduleBuildPath = tupleScheduleBuildData[0][0]
                    strScheduleFlashMode = tupleScheduleBuildData[0][1]
                    strScheduleDeviceID = tupleScheduleBuildData[0][2]

                    if strScheduleFlashMode.lower() == "forcelatest":
                        logger.info("Falsh Mode 'ForceLatest', Get Latest Build From '%s'" % strScheduleBuildPath)
                        rwl.acquire_read()
                        dictCurrentMountedPathInfoList = dictMountedPathInfoList.copy()
                        rwl.release()

                        strScheduleBuildPath = strScheduleBuildPath.replace('\\', '/')
                        strExternalBuildRoot = None
                        strInternalBuildRoot = None

                        for dictMountedPathInfo in dictCurrentMountedPathInfoList.get("BuildShare", []):
                            if strScheduleBuildPath.startswith(dictMountedPathInfo["ExternalPath"]):
                                strExternalBuildRoot = dictMountedPathInfo["ExternalPath"]
                                strInternalBuildRoot = dictMountedPathInfo["InternalPath"]
                                break

                        if strInternalBuildRoot is not None:
                            strInternalScheduleBuildPath = strScheduleBuildPath.replace(strExternalBuildRoot, strInternalBuildRoot)
                            strInternalScheduleBuildPath = strInternalScheduleBuildPath.encode("utf-8")
                            listBuildName = os.listdir(strInternalScheduleBuildPath)
                            listBuildName.sort(key=lambda x: os.stat(os.path.join(strInternalScheduleBuildPath, x)).st_ctime)
                            while len(listBuildName) > 0:
                                strLatestBuildPath = os.path.join(strInternalScheduleBuildPath, listBuildName.pop())
                                if os.path.isdir(strLatestBuildPath) and strLatestBuildPath.endswith("_user"):
                                    strScheduleBuildPath = strLatestBuildPath.replace(strInternalBuildRoot, strExternalBuildRoot)
                                    logger.info("Find The Latest Valid Build '%s'" % strScheduleBuildPath)
                                    break
                        else:
                            logger.warning("Build Path '%s' Not Mounted Locally, Will Use The Original Path Directly")

                        dictBuild["BuildPath"] = strScheduleBuildPath

                    if strScheduleFlashMode.lower() == "forcefixed":
                        logger.info("Falsh Mode 'ForceFixed', Use Build From '%s'" % strScheduleBuildPath)
                        dictBuild["BuildPath"] = strScheduleBuildPath

                    if strScheduleFlashMode.lower() == "selfflashed":
                        logger.info("Falsh Mode 'SelfFlashed', Use Build From '%s' Without Force Flash" % strScheduleBuildPath)
                        dictBuild["BuildPath"] = strScheduleBuildPath
                        dictBuild["ForceFlash"] = False

                    if strScheduleDeviceID is None:
                        logger.info("Test DeviceID Not Specified, Get Test Project Info From Build Path")
                        ma = re.match(r'.+/([^/]+)/([^/]+)/([^/]+)/([^/]+)/*([^/]*)/(.+)/(.+)$', strScheduleBuildPath)
                        if ma:
                            dictDevice["ProductArchitecture"] = ma.group(1)
                            dictDevice["ProjectCode"] = ma.group(2)
                    else:
                        logger.info("Test DeviceID '%s' Specified" % strScheduleDeviceID)
                        dictDevice["DeviceID"] = strScheduleDeviceID
                else:
                    logger.info("Schedule Task ID-%s Not Active Or Not Reach The Time To Run" % intNum)
                    continue
            except Exception:
                logger.exception("Exception Caught")
            else:
                logger.info("Send Task Request To Server")
                strTaskID = queueTaskID.get()
                queueTaskID.task_done()
                dictParameters = {"info": dictInfo, "build": dictBuild, "device": dictDevice}
                queueTaskRequest.put((strTaskID, "TestRequest", dictParameters))

                logger.info("Update Next Running Point For Task ID-%s" % intNum)
                queueTaskRequest.put((strTaskID, "UpdateScheduleTaskPoint", {"NextPoint": str(datetimeNextPoint), "ID": intNum}))



class MultiHandler(logging.Handler):

    def __init__(self, dirname, maxBytes=5 * 1024 * 1024, backupCount=10):
        super(MultiHandler, self).__init__()
        self.maxBytes = maxBytes
        self.backupCount = backupCount
        self.files = {"MainThread": codecs.open(os.path.join(strCurrentFileDirPath, "ScoketDaemon.log"), "a", "utf-8-sig")}
        self.files["TestManagerDaemon"] = codecs.open(os.path.join(strCurrentFileDirPath, "TestManagerDaemon.log"), "a", "utf-8-sig")
        self.files["ScheduleManagerDaemon"] = codecs.open(os.path.join(strCurrentFileDirPath, "ScheduleManagerDaemon.log"), "a", "utf-8-sig")
        self.rwls = {"MainThread": RWLock(), "TestManagerDaemon": RWLock(), "ScheduleManagerDaemon": RWLock()}
        self.dirname = dirname
        if not os.access(dirname, os.W_OK):
            raise Exception("Directory %s not writeable" % dirname)

    def flush(self):
        self.acquire()
        try:
            for key in self.files:
                self.rwls[key].acquire_write()
                self.files[key].flush()
                self.rwls[key].release()
        finally:
            self.release()

    def _get_key(self, record):
        key = record.threadName
        if key.startswith("Thread-"):
            key = "MainThread"

        if key not in self.rwls:
            self.rwls[key] = RWLock()

        return key

    def _get_fp_or_open(self, key):
        "Get the file pointer for the given key, or else open the file"
        self.acquire()
        try:
            if key in self.files:
                return self.files[key]
            else:
                fp = open(os.path.join(self.dirname, "%s.log" % key), "a")
                self.files[key] = fp
                return fp
        finally:
            self.release()

    def emit(self, record):
        """
        choose log file with threadName
        """
        try:
            key = self._get_key(record)
            rwl = self.rwls[key]

            rwl.acquire_write()
            fp = self._get_fp_or_open(key)

            if self.shouldRollover(record, fp):
                self.doRollover(key, fp)

            msg = self.format(record)
            if isinstance(msg, str) is False:
                msg = msg.encode('utf-8')

            fp = self.files[key]
            fp.write('%s\n' % msg)
            rwl.release()
            self.flush()
        except (KeyboardInterrupt, SystemExit):
            raise
        except:
            self.handleError(record)

    def shouldRollover(self, record, fp):
        if self.maxBytes > 0:  # are we rolling over?
            msg = "%s\n" % self.format(record)
            fp.seek(0, 2)  # due to non-posix-compliant Windows feature
            if fp.tell() + len(msg) >= self.maxBytes:
                return True

        return False

    def doRollover(self, key, fp):
        """
        Do a rollover.
        """

        baseFilename = fp.name

        if fp:
            fp.close()
            self.files[key] = None

        if self.backupCount > 0:
            for i in range(self.backupCount - 1, 0, -1):
                sfn = "%s.%d" % (baseFilename, i)
                dfn = "%s.%d" % (baseFilename, i + 1)
                if os.path.exists(sfn):
                    if os.path.exists(dfn):
                        os.remove(dfn)
                    os.rename(sfn, dfn)

            dfn = baseFilename + ".1"
            if os.path.exists(dfn):
                os.remove(dfn)

            if os.path.exists(baseFilename):
                os.rename(baseFilename, dfn)

        self.files[key] = open(baseFilename, "a")


class RWLock(object):
    """
    A simple reader-writer lock Several readers can hold the lock
    simultaneously, XOR one writer. Write locks have priority over reads to
    preven
    """

    def __init__(self):
        self.rwlock = 0
        self.writers_waiting = 0
        self.monitor = threading.Lock()
        self.readers_ok = threading.Condition(self.monitor)
        self.writers_ok = threading.Condition(self.monitor)

    def acquire_read(self):
        """
        Acquire a read lock. Several threads can hold this typeof lock.
        It is exclusive with write locks.
        """

        self.monitor.acquire()
        while self.rwlock < 0 or self.writers_waiting:
            self.readers_ok.wait()
        self.rwlock += 1
        self.monitor.release()

    def acquire_write(self):
        """
        Acquire a write lock. Only one thread can hold this lock, and
        only when no read locks are also held.
        """

        self.monitor.acquire()
        while self.rwlock != 0:
            self.writers_waiting += 1
            self.writers_ok.wait()
            self.writers_waiting -= 1
        self.rwlock = -1
        self.monitor.release()

    def promote(self):
        """
        Promote an already-acquired read lock to a write lock
        WARNING: it is very easy to deadlock with this method
        """

        self.monitor.acquire()
        self.rwlock -= 1
        while self.rwlock != 0:
            self.writers_waiting += 1
            self.writers_ok.wait()
            self.writers_waiting -= 1
        self.rwlock = -1
        self.monitor.release()

    def demote(self):
        """Demote an already-acquired write lock to a read lock"""

        self.monitor.acquire()
        self.rwlock = 1
        self.readers_ok.notifyAll()
        self.monitor.release()

    def release(self):
        """Release a lock, whether read or write."""

        self.monitor.acquire()
        if self.rwlock < 0:
            self.rwlock = 0
        else:
            self.rwlock -= 1
        wake_writers = self.writers_waiting and self.rwlock == 0
        wake_readers = self.writers_waiting == 0
        self.monitor.release()
        if wake_writers:
            self.writers_ok.acquire()
            self.writers_ok.notify()
            self.writers_ok.release()
        elif wake_readers:
            self.readers_ok.acquire()
            self.readers_ok.notifyAll()
            self.readers_ok.release()


def sendSocketCMD(strCMD, strHostName, intHostPort=16600):
    proto = socket.getprotobyname("tcp")
    strExcuteReturn = ""
    t = 1

    while True:
        sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM, proto)
        sock.settimeout(10)
        try:
            sock.connect((strHostName, intHostPort))
            sock.send(strCMD + '\n')
            strExcuteReturn = sock.recv(1024, 0)
            sock.close()
            break
        except socket.error, e:
            sock.close()
            if t > 3:
                logger.error(e)
                break
            else:
                t = t + 1
                time.sleep(1)
        except Exception:
            logger.exception("Exception Caught")
            break

    return strExcuteReturn


def getHostIpAddress(strHostname):
    listIpAddr = []

    try:
        ais = socket.getaddrinfo(strHostname, 0, 0, 0, 0)
        for result in ais:
            listIpAddr.append(result[-1][0])
        listIpAddr = list(set(listIpAddr))
    except Exception:
        logger.exception("Exception Caught")

    return listIpAddr


def getValidHostFromDb(strHostType, boolReturnIp=False):
    listValidHostIp = []

    tupleHostName = DatabaseOperation.getDataFromTable(strHostType, strFieldName="HostName", strWhereClause="Status=\"Up\"")
    for hostName in tupleHostName:
        if boolReturnIp is True:
            listValidHostIp.extend(getHostIpAddress(hostName[0]))
        else:
            listValidHostIp.append(hostName[0])

    return listValidHostIp


def clientInfoUpdate():
    """
    get client info from database
    """
    rwl.acquire_write()
    logger.info("Get Portal Server Host Name")
    del listPortalClientHostName[:]
    listPortalClientHostName.extend(getValidHostFromDb("client_portal"))

    logger.info("Get Build Client Host Name")
    del listBuildClientHostName[:]
    listBuildClientHostName.extend(getValidHostFromDb("client_build"))
    rwl.release()


def mountedPathInfoUpdate():
    rwl.acquire_write()
    logger.info("Get Mounted Path Info")
    dictMountedPathInfoList.clear()
    tupleMountedPathInfo = DatabaseOperation.getDataFromTable("map_path_mounted", strFieldName="Type, InternalPath, ExternalPath")
    tupleMountedPathInfo = CodecUtil.transferUnicodeToStr(tupleMountedPathInfo)
    for mountedPathInfo in tupleMountedPathInfo:
        dictMountedPathInfoList.setdefault(mountedPathInfo[0], []).append({"InternalPath": mountedPathInfo[1], "ExternalPath": mountedPathInfo[2]})
    rwl.release()


def taskIdGenerate():
    while True:
        queueTaskID.join()
        strTimestamp = str(int(time.time()))
        for x in xrange(100, 200):
            strTaskID = strTimestamp + str(x)
            queueTaskID.put(strTaskID)


if __name__ == '__main__':
    strThreadLogPath = os.path.join(strCurrentFileDirPath, "log")
    if os.path.isdir(strThreadLogPath) is False:
        os.makedirs(strThreadLogPath)

    strTestQueuepoolPath = os.path.join(strCurrentFileDirPath, "testqueuepool")
    if os.path.isdir(strTestQueuepoolPath) is False:
        os.makedirs(strTestQueuepoolPath)

    strTestScriptPoolPath = os.path.join(strCurrentFileDirPath, "testscript")

    queueTaskRequest = Queue.Queue()
    queueTestCheck = Queue.Queue()
    queueTaskID = Queue.Queue()

    logger = logging.getLogger("AbtsSocket")
    log_format = '%(asctime)s - %(name)s [%(levelname)s]: %(message)s'
    stderr_handler = logging.StreamHandler()
    stderr_handler.setFormatter(logging.Formatter(log_format))

    multi_handler = MultiHandler(strThreadLogPath)
    multi_handler.setFormatter(logging.Formatter(log_format))
    logging.getLogger().addHandler(multi_handler)

    logger.setLevel(logging.INFO)
    logger.info("Run initiated")

    rwl = RWLock()
    listPortalClientHostName = []
    listBuildClientHostName = []
    dictMountedPathInfoList = {}

    clientInfoUpdate()
    mountedPathInfoUpdate()
    if len(listPortalClientHostName) == 0:
        logger.warning("No Portal Client Exists, Cannot Handle Any Request")
    if len(listBuildClientHostName) == 0:
        logger.warning("No Build Client Exists, Cannot Handle Any Build Request")

    logger.info("Start Task ID Generate")
    thrTaskIdGenerate = threading.Thread(target=taskIdGenerate)
    thrTaskIdGenerate.daemon = True
    thrTaskIdGenerate.start()

    logger.info("Start Socket Listener")
    HOST, PORT = "", 16600
    serverReceiver = SocketServer.ThreadingTCPServer((HOST, PORT), ThreadedTCPRequestHandler, False)
    serverReceiver.allow_reuse_address = True
    serverReceiver.server_bind()
    serverReceiver.server_activate()
    serverThread = threading.Thread(target=serverReceiver.serve_forever)
    serverThread.daemon = True
    serverThread.start()

    logger.info("Start Test Host Status Watchdog")
    myTestManager = TestManager()
    thrTestHostWatcher = threading.Thread(target=myTestManager.testDeviceStatusWatchdog, name="TestManagerDaemon")
    thrTestHostWatcher.daemon = True
    thrTestHostWatcher.start()

    logger.info("Start Test Manager Listener")
    thrTestTaskListener = threading.Thread(target=myTestManager.testTaskListener, name="TestManagerDaemon")
    thrTestTaskListener.daemon = True
    thrTestTaskListener.start()

    logger.info("Start Schedule Manager Listener")
    myScheduleManager = ScheduleManager()
    thrScheduleTaskTicker = threading.Thread(target=myScheduleManager.scheduleTaskTicker, name="ScheduleManagerDaemon")
    thrScheduleTaskTicker.daemon = True
    thrScheduleTaskTicker.start()

    logger.info("Start Request Listener")
    myTaskFarmer = TaskFarmer()
    myTaskFarmer.requestListener()

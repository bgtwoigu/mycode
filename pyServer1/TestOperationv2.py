import os
import imp
import json
import logging
import AdbOperation
import DeviceUtil
import CodecUtil
from JiraOperation import JiraRest
import Remote
import DatabaseOperation


logger = logging.getLogger("TestOperation")
logger.setLevel(logging.INFO)


class TestRunner(object):

    """docstring for TestRunner"""

    def __init__(self, strTempPath, strDeviceID, strProjectKey, listTestScriptList, dictTestParameterList, strTestScriptPoolPath, RemoteIp):
        self.strTestResultPath = strTempPath
        self.strDeviceID = strDeviceID
        self.strProjectKey = strProjectKey
        self.listTestScriptList = listTestScriptList
        self.dictTestParameterList = dictTestParameterList
        self.strTestScriptPoolPath = strTestScriptPoolPath
        self.strRemoteIP = RemoteIp
        self.strshareExternalPath = DatabaseOperation.getDataFromTable("map_path_mounted", strFieldName="ExternalPath", strWhereClause="Type=\"TempShare\"" )[0][0]
        self.strshareInternalPath = DatabaseOperation.getDataFromTable("map_path_mounted", strFieldName="InternalPath", strWhereClause="Type=\"TempShare\"" )[0][0]
        #self.strResultInternalPath = DatabaseOperation.getDataFromTable("map_path_mounted", strFieldName="InternalPath", strWhereClause="Type=\"ResultShare\"" )[0][0]
        
        if self.strRemoteIP:
            url = 'http://%s:%s' %(self.strRemoteIP, Remote.remotePort)
            self.remote = Remote.XmlRpcRemoteClient(url)

        if os.path.isdir(self.strTestResultPath) is False:
            os.makedirs(self.strTestResultPath)

        #add remote deviceinit add by libin859
        if self.strRemoteIP:
            status, ret = self.remote.run_method('DeviceUtil', 'initTestDevice', self.strDeviceID)
            if not (status and ret):
                raise Exception("Init Test Device Failed: %s, %s" %(status, ret))
            self.remote.run_method('AdbOperation', 'stopAdbServer')     #kill adb server add by libin859
        else:
            if not DeviceUtil.initTestDevice():
                raise Exception("Init Test Device Failed")
            AbdOperation.stopAdbServer()    #kill adb server add by libin859

    def dictValueTransform(self, objectFromXml):
        if isinstance(objectFromXml, basestring):
            if objectFromXml == "True":
                objectFromXml = True
            elif objectFromXml == "False":
                objectFromXml = False
            elif objectFromXml == "":
                objectFromXml = None
        elif isinstance(objectFromXml, dict):
            for key in objectFromXml:
                objectFromXml[key] = self.dictValueTransform(
                    objectFromXml[key])
        elif isinstance(objectFromXml, list):
            for item in objectFromXml:
                item = self.dictValueTransform(item)

        return objectFromXml
    
    #move to DeviceUtil
    # def initTestDevice(self):
    #     if AdbOperation.isDeviceExists(self.strDeviceID) is False:
    #         logger.error("Cannot Connect To Test Device")
    #         return False
    # 
    #     logger.info("Unlock Screen")
    #     DeviceUtil.unlockScreen(self.strDeviceID)
    # 
    #     logger.info("Config Provision")
    #     DeviceUtil.configProvision(self.strDeviceID)
    # 
    #     logger.info("Set Screen Stay On")
    #     DeviceUtil.setScreenStayOn(self.strDeviceID)
    # 
    #     logger.info("Config Wifi")
    #     DeviceUtil.configWifi(self.strDeviceID)
    # 
    #     logger.info("Remove Asserttip")
    #     DeviceUtil.removeAsserttip(self.strDeviceID)
    # 
    #     logger.info("Reboot Device")
    #     return DeviceUtil.rebootDevice(self.strDeviceID)

    def resultAnalysis(self, strTestTempPath):
        """
        Analysis files under ~/abts/mnt/test_result/
        """
        listReporAttachment = []     #return Attachment list add by libin859 
        listReportImage = []
        dictIssueList = {}
        dictTestResult = {"Pass": None, "Fail": None,
                          "Skip": None, "Abort": None}
        for key in dictTestResult:
            dictTestResult[key] = {"TotalNum": 0, "TestCase": []}

        strTestResultFilePath = os.path.join(
            strTestTempPath, "testresult.json")
        if os.path.isfile(strTestResultFilePath) is False:
            logger.warning("Canot Find Test Result File Under '%s'" %
                           strTestResultFilePath)
            return (dictTestResult, dictIssueList, listReportImage, listReporAttachment)

        try:
            # fileResultXml = open(strTestResultFilePath, 'r')
            # strResultXml = fileResultXml.read()

            # ordereddictResult = XmlToDict.parse(strResultXml)
            # ordereddictResult = ordereddictResult.get("TestResult", {})
            fileResultJson = open(strTestResultFilePath, 'r')
            strResultJson = fileResultJson.read()
            dictResult = json.loads(strResultJson, encoding="utf-8")

            logger.info("Make Sure No Unicode")
            dictResult = CodecUtil.transferUnicodeToStr(dictResult)
        except Exception, e:
            logger.exception("Exception Caught: %s" %str(e))
        else:
            listTestDetailsInfo = dictResult.get("TestDetails", [])
            listReportImage = dictResult.get("ReportImage", [])
            listReporAttachment = dictResult.get("ReportAttachment", [])

            logger.info("Analysis On Test Test Details")
            for dictCurrentTestDetailInfo in listTestDetailsInfo:
                strCurrentTestResult = dictCurrentTestDetailInfo.get(
                    "Result", "Unknown").capitalize()
                if strCurrentTestResult not in dictTestResult:
                    logger.warning(
                        "Test Result Not Valid, Use 'Abort' Instead")
                    strCurrentTestResult = "Abort"

                strCurrentTestName = dictCurrentTestDetailInfo.get(
                    "TestName", "Unknown")
                strCurrentTestErrorMsg = dictCurrentTestDetailInfo.get(
                    "ErrorMsg", "")

                dictTestResult[strCurrentTestResult][
                    "TestCase"].append(strCurrentTestName)
                dictTestResult[strCurrentTestResult]["TotalNum"] += 1

                if strCurrentTestResult != "Pass":
                    logger.info("Test Case '%s' %s, Analysis" %
                                (strCurrentTestName, strCurrentTestResult))
                    dictCurrentIssueJiraTicketInfo = dictCurrentTestDetailInfo.get(
                        "JiraTicket", None)
                    listCurrentIssueJiraAttachment = dictCurrentTestDetailInfo.get(
                        "JiraAttachment", [])
                    strCurrentJiraTicketUrl = None

                    if dictCurrentIssueJiraTicketInfo is not None:
                        logger.info("Submit A Jira Ticket")
                        (boolJiraCreatedResult, dictJiraTicketInfo) = JiraRest.creatIssue(
                            self.strProjectKey, "Bug", dictCurrentIssueJiraTicketInfo)
                        if boolJiraCreatedResult is False:
                            strCurrentJiraTicketUrl = "Failed To Create"
                            logger.error("Failed to create Jira ticket")
                        else:
                            strJiraKey = dictJiraTicketInfo.get("key", "")
                            logger.info(
                                "Jira Ticket Created, Key: '%s'" % strJiraKey)
                            strCurrentJiraTicketUrl = "https://pjira.oneplus.cn/jira/browse/%s" % strJiraKey
                            for strJiraAttachmentPath in listCurrentIssueJiraAttachment:
                                logger.info(
                                    "Upload Attachment '%s' To Jira Ticket" % strJiraAttachmentPath)
                                strJiraAttachmentPath = strJiraAttachmentPath.replace(self.strshareExternalPath, self.strshareInternalPath)   #return server local path add by libin859
                                JiraRest.addAttachment(
                                    strJiraKey, strJiraAttachmentPath)

                    logger.info("Add Info To Issue List")
                    dictIssueList[strCurrentTestName] = {}
                    dictIssueList[strCurrentTestName][
                        "ErrorMsg"] = strCurrentTestErrorMsg
                    dictIssueList[strCurrentTestName][
                        "IssueType"] = strCurrentTestResult
                    dictIssueList[strCurrentTestName][
                        "JiraTicket"] = strCurrentJiraTicketUrl

            logger.info("Analysis On Test Report Image")
            for dictReportImageInfo in listReportImage:
                strCurrentImagePath = dictReportImageInfo.get("Path", " ").replace("\\", os.sep).replace('/', os.sep)
                strCurrentImagePath = strCurrentImagePath.replace(self.strshareExternalPath, self.strshareInternalPath)   #return server local path add by libin859
                if strCurrentImagePath is " " or not os.path.isfile(strCurrentImagePath):
                    logger.warning(
                        "Report Image '%s' Not Valid, Remove" % str(strCurrentImagePath))
                    listReportImage.remove(dictReportImageInfo)
                else:
                    dictReportImageInfo['Path'] = strCurrentImagePath      #replace share path to local path add by libin859
            
            #return attachment list add by libin859 
            logger.info("Analysis On Test Attachment Image")
            for dictReportAttachmentInfo in listReporAttachment:
                strCurrentAttachmentPath = dictReportAttachmentInfo.get("Path", " ").replace("\\", os.sep).replace('/', os.sep)
                strCurrentAttachmentPath = strCurrentAttachmentPath.replace(self.strshareExternalPath, self.strshareInternalPath)  #return server local path add by libin859
                if strCurrentAttachmentPath is " " or not os.path.isfile(strCurrentAttachmentPath):
                    logger.warning(
                        "Report Attachment '%s' Not Valid, Remove" % str(strCurrentAttachmentPath))
                    listReporAttachment.remove(dictReportAttachmentInfo)
                else:
                    dictReportAttachmentInfo['Path'] = strCurrentAttachmentPath       #replace share path to local path add by libin859
            
        finally:
            fileResultJson.close()
        
        return (dictTestResult, dictIssueList, listReportImage, listReporAttachment)

    def runTest(self):
        boolResult = True
        strLastError = ""
        dictTestSummarize = {
            "Total": {"Pass": 0, "Fail": 0, "Skip": 0, "Abort": 0}}
        dictTestDetails = {}
        dictIssueList = {}
        listReportImage = []
        listReporAttachment = []   #return attachment list add by libin859 

        for strTestScriptName in self.listTestScriptList:
            logger.info("Run Test Script '%s'" % strTestScriptName)
            try:
                strCurrentTestTempPath = os.path.join(
                    self.strTestResultPath, os.path.splitext(strTestScriptName)[0])
                if os.path.isdir(strCurrentTestTempPath) is False:
                    os.makedirs(strCurrentTestTempPath)
                       
                # execute remote test script add by libin859   
                if self.strRemoteIP:
                    strShareTestTempPath = strCurrentTestTempPath.replace(self.strshareInternalPath, self.strshareExternalPath)
                    logger.info("Convert tempPath to: %s" %strShareTestTempPath)
                    status, ret = self.remote.run_method(strTestScriptName.split('.')[0], 'main', self.strDeviceID, strShareTestTempPath, self.dictTestParameterList.get(strTestScriptName, {}))
                    if not status:
                         dictIssueList[strTestScriptName] = {
                            "All": {"ErrorMsg": ret, "IssueType": "Script Run Fail at Remote: %s" %self.strRemoteIP}}
                    self.remote.run_method('AdbOperation', 'stopAdbServer')
                else:
                    CurrentTestScript = imp.load_source('CurrentTestScript', os.path.join(
                        self.strTestScriptPoolPath, strTestScriptName))
                    CurrentTestScript.main(self.strDeviceID, strCurrentTestTempPath,
                                           self.dictTestParameterList.get(strTestScriptName, {}))
                    AdbOperation.stopAdbServer()
            except Exception, e:
                logger.exception("Exception Caught")
                boolResult = False
                strLastError = str(e)
                dictTestSummarize[strTestScriptName] = {
                    "Pass": 0, "Fail": 0, "Skip": 0, "Abort": 0}
                dictIssueList[strTestScriptName] = {
                    "All": {"ErrorMsg": strLastError, "IssueType": "Exception"}}
            else:
                logger.info("Test Script '%s' Over, Analysis The Result" %
                            strTestScriptName)
                dictCurrentTestResult, dictCurrentIssueList, listCurrentReportImage, listCurrentReporAttachment = self.resultAnalysis(
                    strCurrentTestTempPath)
                for keyResult in dictCurrentTestResult:
                    intCurrentResultNum = dictCurrentTestResult[
                        keyResult].get("TotalNum", 0)
                    dictTestSummarize.setdefault(strTestScriptName, {})[
                        keyResult] = intCurrentResultNum
                    dictTestSummarize["Total"][
                        keyResult] += intCurrentResultNum
                    dictTestDetails.setdefault(strTestScriptName, {})[
                        keyResult] = dictCurrentTestResult[keyResult].get("TestCase", [])[:]

                if len(dictCurrentIssueList) > 0:
                    dictIssueList[
                        strTestScriptName] = dictCurrentIssueList.copy()

                listReportImage.extend(listCurrentReportImage)
                listReporAttachment.extend(listCurrentReporAttachment)
            finally:
                CurrentTestScript = None

        logger.info("Make Sure No Unicode")
        dictIssueList = CodecUtil.transferUnicodeToStr(dictIssueList)
        listReportImage = CodecUtil.transferUnicodeToStr(listReportImage)
        dictTestDetails = CodecUtil.transferUnicodeToStr(dictTestDetails)
        listReporAttachment = CodecUtil.transferUnicodeToStr(listReporAttachment)

        return (boolResult, strLastError, dictTestSummarize, dictIssueList, listReportImage, dictTestDetails, listReporAttachment)


if __name__ == '__main__':
    import sys
    hdlr = logging.StreamHandler(sys.stdout)
    hdlr.setFormatter(logging.Formatter(
        '%(asctime)s - %(name)s [%(levelname)s]: %(message)s'))
    logger.addHandler(hdlr)
    aaa = TestRunner("/home/rafe/abts/testqueuepool/1464943670101/", "327aaf8c", "DEMO",
                     ["TestDemo.py"], {"TestDemo.py": {"Num": 1, "": ""}}, "/home/rafe/abts/testscript/")
    # aaa = TestRunner("E:\\Project\\ABTS\\lib\\TestTemp", "327aaf8c", "DEMO", ["TestDemo.py"], {"TestDemo.py": {"Num": 1, "": ""}}, "E:\\Project\\ABTS\\lib\\testscript")
    print aaa.runTest()

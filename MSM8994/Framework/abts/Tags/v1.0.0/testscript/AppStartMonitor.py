#!/usr/bin/env python
# -*- coding: utf-8 -*-
import os
import sys
import re
import time
import csv
import json
import datetime
import threading
import subprocess
import logging


strCurrentFileDirPath = os.path.dirname(os.path.abspath(__file__))
strAbtsRootDirPath = os.path.dirname(strCurrentFileDirPath)
strResultCsvPath = os.path.join(strCurrentFileDirPath, "AppStartMonitor", "Result.csv")
sys.path.append(os.path.join(strAbtsRootDirPath, "lib"))
import AdbOperation
import DeviceUtil
import UiAutomator


logger = logging.getLogger("AppStartMonitor")
logger.setLevel(logging.INFO)


def systraceAnalysis(strTraceHtmlPath):
    # Todo+
    return 0
    # Todo-


def recordResultToCsv(dictAppStartTimeInfo):
    listOldResult = []

    logger.info("Get Field Names From CSV")
    try:
        if os.path.isfile(strResultCsvPath) is False:
            open(strResultCsvPath, 'w').close()
            listFieldNames = ["Date"]
            listFieldNames.extend(dictAppStartTimeInfo.keys())
        else:
            readerCsv = csv.DictReader(open(strResultCsvPath))
            listFieldNames = readerCsv.fieldnames
            for strAppName in dictAppStartTimeInfo:
                if strAppName not in listFieldNames:
                    listFieldNames.append(strAppName)

            for row in readerCsv:
                listOldResult.append(row)
    except Exception:
        logger.exception("Exception Caught")
        return False

    logger.info("Write Results To CSV")
    try:
        dictwriterCsv = csv.DictWriter(file(strResultCsvPath, 'wb'), fieldnames=listFieldNames)
        dictwriterCsv.writeheader()
        if len(listOldResult) > 0:
            dictwriterCsv.writerows(listOldResult)

        dictAppStartTimeInfo["Date"] = datetime.datetime.today()
        dictwriterCsv.writerow(dictAppStartTimeInfo)
    except Exception:
        logger.exception("Exception Caught")
        return False

    return True


def logReader(stdoutRead, strAppPackageName, dictTime=None):
    try:
        while True:
            Msg = stdoutRead()
            if Msg:
                ma = re.match(
                    r'(\d+-\d+)\s*(\d+:\d+:\d+\.\d+)\s*(\d+\s*\d+)\s*I\s*Input\s*:\s*injectMotionEvent:\s*MotionEvent\s*{\s*action=ACTION_UP,.*}', Msg, re.IGNORECASE)
                if ma:
                    # print ma.group(2)
                    timeStampButtonRelease = datetime.datetime.strptime(
                        ma.group(2), '%H:%M:%S.%f')
                    dictTime["ButtonRelease"] = timeStampButtonRelease

                ma = re.match(
                    r'(\d+-\d+)\s*(\d+:\d+:\d+\.\d+)\s*(\d+\s*\d+)\s*I\s*WindowManager\s*:\s*SURFACE SHOW.*%s.*}' % strAppPackageName, Msg, re.IGNORECASE)
                if ma:
                    # print ma.group(2)
                    timeStampAppStart = datetime.datetime.strptime(
                        ma.group(2), '%H:%M:%S.%f')
                    dictTime["AppStart"] = timeStampAppStart
                    break
            else:
                break
    except Exception:
        logger.exception("Exception Caught")
        return


def main(strDeviceID, strTestTempPath, dictTestParameter):
    if DeviceUtil.unlockScreen(strDeviceID) is False:
        raise Exception("Unlock The Device Failed")

    dictAppName = dictTestParameter.get("AppList", [])
    if len(dictAppName) == 0:
        raise Exception("No App Specified")

    DeviceUtil.closeIntroIfExists(strDeviceID)
    DeviceUtil.switchAirplaneMode(strDeviceID)
    dictAppStartTimeInfo = {}
    listTestResult = []

    for strAppName in dictAppName:
        intCurrentAppStartTime = 0
        dictCurrentAppTimeInfo = {}
        strCurrentAppPackageName = dictAppName[strAppName]
        if isinstance(strAppName, str):
            strAppName = strAppName.decode("utf-8")

        logger.info("Test On App '%s'" % strAppName)
        dictCurrentTestResult = {"TestName": strAppName, "Result": "Pass"}
        try:
            controlCurrentApp = UiAutomator.getControl(UiAutomator.dumpView(strDeviceID), {'text': strAppName})
            if controlCurrentApp is not None:
                processLogcat = subprocess.Popen(['adb', '-s', strDeviceID, 'shell', 'logcat'], stdout=subprocess.PIPE, stderr=subprocess.STDOUT, creationflags=subprocess.CREATE_NEW_PROCESS_GROUP)
                stdoutLogcatReader = threading.Thread(target=logReader, args=(processLogcat.stdout.readline, strCurrentAppPackageName, dictCurrentAppTimeInfo))
                stdoutLogcatReader.daemon = True
                logger.info("Start logcat Catch")
                stdoutLogcatReader.start()

                logger.info("Start Systrace Catch")
                strCurrentTraceHtmlPath = os.path.join(strTestTempPath, "trace_%s.html" % strAppName.encode("gbk"))
                processSystrace = subprocess.Popen(['python', os.path.join(strAbtsRootDirPath, "devtools/systrace/systrace.py"), '-o', strCurrentTraceHtmlPath, '-e', strDeviceID, '-t', "5", 'gfx', 'input', 'view', 'wm', 'am'], creationflags=subprocess.CREATE_NEW_PROCESS_GROUP)
                time.sleep(1)

                logger.info("Start App '%s'" % strAppName)
                UiAutomator.clickControl(strDeviceID, controlCurrentApp)
                processSystrace.wait()
                time.sleep(5)
                processLogcat.kill()
                stdoutLogcatReader.join()

                AdbOperation.executeAdbCmd(["input", "keyevent ", "3"], strDeviceID, True)
                # intCurrentAppStartTime = systraceAnalysis(strCurrentTraceHtmlPath)
                if dictCurrentAppTimeInfo.get("AppStart", None) is None or dictCurrentAppTimeInfo.get("ButtonRelease", None) is None:
                    intCurrentAppStartTime = 0
                else:
                    intCurrentAppStartTime = dictCurrentAppTimeInfo.get("AppStart", 0) - dictCurrentAppTimeInfo.get("ButtonRelease", 0)
                    logger.info("Current App Start Time Use %s sec" % str(intCurrentAppStartTime))

                if intCurrentAppStartTime == 0:
                    dictCurrentTestResult["Result"] = "Fail"
                    dictCurrentTestResult["ErrorMsg"] = "Failed To Analysis The Trace File"
            else:
                logger.error("Cannot Find App '%s', Skip" % strAppName)
                dictCurrentTestResult["Result"] = "Skip"
                dictCurrentTestResult["ErrorMsg"] = "Cannot Find App"
        except Exception, e:
            logger.exception("Exception Caught")
            dictCurrentTestResult["Result"] = "Fail"
            dictCurrentTestResult["ErrorMsg"] = str(e)

        dictAppStartTimeInfo[strAppName] = intCurrentAppStartTime
        listTestResult.append(dictCurrentTestResult)

    logger.info("All App Tested, Record Result To CSV")
    recordResultToCsv(dictAppStartTimeInfo)

    logger.info("Store Test In Json Format")
    dictResult = {"TestDetails": listTestResult}
    strJson = json.dumps(dictResult, ensure_ascii=False)

    fileResultInJson = open(os.path.join(strTestTempPath, "testresult.json"), 'w')
    fileResultInJson.write(strJson)
    fileResultInJson.close()


if __name__ == '__main__':
    hdlr = logging.StreamHandler(sys.stdout)
    hdlr.setFormatter(logging.Formatter('%(asctime)s - %(name)s [%(levelname)s]: %(message)s'))
    logger.addHandler(hdlr)

    main("f92a2700", "E:\\Project\\ABTS\\testscript\\tmp", {"AppList": {"电话": "com.android.contacts"}})

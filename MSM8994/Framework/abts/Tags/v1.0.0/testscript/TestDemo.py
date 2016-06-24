#!/usr/bin/env python
# -*- coding: utf-8 -*-
import os
import collections
import json
import shutil


def dictValueTransform(objectFromXml):
    if isinstance(objectFromXml, basestring):
        if objectFromXml == "True":
            objectFromXml = True
        elif objectFromXml == "False":
            objectFromXml = False
        elif objectFromXml == "":
            objectFromXml = None
    elif isinstance(objectFromXml, dict):
        for key in objectFromXml:
            objectFromXml[key] = dictValueTransform(objectFromXml[key])
    elif isinstance(objectFromXml, list):
        for item in objectFromXml:
            item = dictValueTransform(item)

    return objectFromXml


def returnUnicodeObj(objValue):
    if isinstance(objValue, str):
        return objValue.decode('utf-8')
    if isinstance(objValue, list):
        listNew = []
        while len(objValue) > 0:
            v = objValue.pop()
            listNew.append(returnUnicodeObj(v))

        return listNew[:]
    if isinstance(objValue, dict):
        dictNew = {}
        while len(objValue) > 0:
            (k, v) = objValue.popitem()
            dictNew[returnUnicodeObj(k)] = returnUnicodeObj(v)

        return dictNew.copy()

    return objValue


def ordereddictToDict(objInput):
    if isinstance(objInput, list):
        listNew = []
        while len(objInput) > 0:
            v = objInput.pop()
            listNew.append(ordereddictToDict(v))

        return listNew[:]
    if isinstance(objInput, collections.OrderedDict):
        dictNew = {}
        while len(objInput) > 0:
            (k, v) = objInput.popitem()
            dictNew[ordereddictToDict(k)] = ordereddictToDict(v)

        return dictNew.copy()

    return objInput


# def returnUnicodeObj(objValue):
#     if isinstance(objValue, str):
#         return objValue.decode('utf-8')
#     if isinstance(objValue, list):
#         for subObjValue in objValue:
#             subObjValue = returnUnicodeObj(subObjValue)
#     if isinstance(objValue, dict):
#         for key in objValue:
#             objValue[returnUnicodeObj(key)] = returnUnicodeObj(objValue.pop(key))

#     return objValue


def main(strDeviceID, strTestTempPath, dictTestParameter):
    strCurrentFileDirPath = os.path.dirname(os.path.abspath(__file__))
    strCurrentDemoDirPath = os.path.join(strCurrentFileDirPath, "TestDemo")

    dictJiraTicket1 = {}
    dictJiraTicket1["reporter"] = {"name": "abts_admin"}
    dictJiraTicket1["components"] = [{"name": "Camera"}]
    dictJiraTicket1["customfield_10201"] = {"value": "Camera"}
    dictJiraTicket1["summary"] = "ABTS 演示"
    dictJiraTicket1["customfield_10102"] = {"value": "1"}
    dictJiraTicket1["customfield_10101"] = "123"
    dictJiraTicket1[
        "description"] = ' ! " # $ % & ( ) * + , - . / 0 1 2 3 4 5 6 7 8 9 : ; < = > @'

    strJpgPath = os.path.join(strCurrentDemoDirPath, "你瞅啥.jpg")
    strJpgBPath = os.path.join(strCurrentDemoDirPath, "SoWhat.gif")
    strLogPath = os.path.join(strCurrentDemoDirPath, "test.log")

    strJpgCpPath = os.path.join(strTestTempPath, "你瞅啥.jpg")
    strJpgBCpPath = os.path.join(strTestTempPath, "SoWhat.gif")
    strLogCpPath = os.path.join(strTestTempPath, "test.log")

    try:
        shutil.copy(strJpgPath, strJpgCpPath)
        shutil.copy(strJpgBPath, strJpgBCpPath)
        shutil.copy(strLogPath, strLogCpPath)
    except Exception:
        import traceback
        print traceback.print_exc()

    listTestResult = []
    dictTestResult1 = {"TestName": "主板温度测试", "Result": "Pass"}
    dictTestResult2 = {"TestName": "电流大小测试", "Result": "Fail", "ErrorMsg": "待机电流大于0.5A",
                       "JiraTicket": dictJiraTicket1, "JiraAttachment": [strJpgCpPath, strLogCpPath]}
    dictTestResult3 = {"TestName": "Camera API Test",
                       "Result": "Skip", "ErrorMsg": "No Camera HW"}
    listTestResult.append(dictTestResult1)
    listTestResult.append(dictTestResult2)
    listTestResult.append(dictTestResult3)

    listReportImage = []
    dictReportImage1 = {"Title": "你瞅啥", "Path": strJpgCpPath}
    dictReportImage2 = {"Title": "So What", "Path": strJpgBCpPath}
    listReportImage.append(dictReportImage1)
    listReportImage.append(dictReportImage2)

    dictParameters = {"TestDetails": listTestResult,
                      "ReportImage": listReportImage}
    strJson = json.dumps(dictParameters, ensure_ascii=False)

    fileParametersInXml = open(os.path.join(
        strTestTempPath, "testresult.json"), 'w')
    fileParametersInXml.write(strJson)
    fileParametersInXml.close()


if __name__ == '__main__':
    # main("123", "E:\\Project\\ABTS\\lib\\TestTemp", {})
    main("123", "/home/rafe/abts/testqueuepool/temp", {})

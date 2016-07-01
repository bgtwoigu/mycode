#!/usr/bin/env python
# -*- coding: utf-8 -*-
import os
import re
import json
import ssl
import urllib2
import pycurl
import cStringIO
import logging
import CodecUtil


ONEPLUS_JIRA_URL = "https://pjira.oneplus.cn/jira"
ONEPLUS_JIRA_SOAP_URL = "https://pjira.oneplus.cn/jira/rpc/soap/jirasoapservice-v2?wsdl"
ONEPLUS_JIRA_REST_ISSUE_URL = "https://pjira.oneplus.cn/jira/rest/api/2/issue"
ONEPLUS_JIRA_REST_SEARCH_URL = "https://pjira.oneplus.cn/jira/rest/api/2/search"
ONEPLUS_JIRA_USER = "abts_admin"
ONEPLUS_JIRA_PWD = "Abcd)98765"


logger = logging.getLogger("JiraOperation")
logger.setLevel(logging.INFO)


class JiraRest():

    """docstring for JiraSoapConstruct"""

    @staticmethod
    def discoveringMetaDataForCreatingIssues(dictCondition=None):
        strUrl = ONEPLUS_JIRA_REST_ISSUE_URL + "/createmeta"
        if dictCondition is not None and len(dictCondition) > 0:
            strUrl += "?"
            for strKey in dictCondition:
                strUrl += "%s=%s&" % (strKey, dictCondition[strKey])

        intAttempts = 1
        while intAttempts <= 3:
            (output, errors) = JiraRest.sendRequest(strUrl)
            if errors is None:
                return JiraRest.jsonDecode(output)
            else:
                JiraRest.errorHandler(output, errors)
                logger.error(
                    "Attempting to recover (try %s out of 3)" % intAttempts)
                intAttempts += 1

        return None

    @staticmethod
    def discoveringMetaDataForEditingIssues(strIssueKey):
        strUrl = ONEPLUS_JIRA_REST_ISSUE_URL + "/%s/editmeta" % strIssueKey

        intAttempts = 1
        while intAttempts <= 3:
            (output, errors) = JiraRest.sendRequest(strUrl)
            if errors is None:
                return JiraRest.jsonDecode(output)
            else:
                JiraRest.errorHandler(output, errors)
                logger.error(
                    "Attempting to recover (try %s out of 3)" % intAttempts)
                intAttempts += 1

        return None

    @staticmethod
    def creatIssue(strProjectKey, strIssueTypeName, dictIssueData):
        dictCondition = {"projectKeys": strProjectKey,
                         "issuetypeNames": strIssueTypeName, "expand": "projects.issuetypes.fields"}
        dictMetaData = JiraRest.discoveringMetaDataForCreatingIssues(
            dictCondition)
        if dictMetaData is None:
            logger.error("Failed to get meta data using %s, %s" %
                         (strProjectKey, strIssueTypeName))
            return (False, None)

        listProjects = dictMetaData.get('projects', [])
        if len(listProjects) != 1:
            logger.error("Project %s not found or not unique" % strProjectKey)
            return (False, None)

        listIssueType = listProjects[0].get('issuetypes', [])
        if len(listIssueType) != 1:
            logger.error("Issue type %s not found or not unique" %
                         strIssueTypeName)
            return (False, None)

        dictIssueMetaData = listIssueType[0]
        dictIssueData.update(
            {"project": {"key": strProjectKey}, "issuetype": {"name": strIssueTypeName}})
        dictBuildedIssueData = JiraRest.buildIssueData(
            dictIssueData, dictIssueMetaData.get('fields', {}))
        if dictBuildedIssueData is None:
            logger.error("Issue data provided is not valid")
            return (False, None)

        strJsonData = JiraRest.jsonEncode({"fields": dictBuildedIssueData})
        logger.debug(strJsonData)

        intAttempts = 1
        while intAttempts <= 3:
            (output, errors) = JiraRest.sendRequest(
                ONEPLUS_JIRA_REST_ISSUE_URL, "POST", strJsonData)
            if errors is None:
                return (True, JiraRest.jsonDecode(output))
            else:
                JiraRest.errorHandler(output, errors)
                logger.error(
                    "Attempting to recover (try %s out of 3)" % intAttempts)
                intAttempts += 1

        return (False, None)

    @staticmethod
    def editIssue(strIssueKey, dictIssueData):
        dictIssueMetaData = JiraRest.discoveringMetaDataForEditingIssues(
            strIssueKey)
        if dictIssueMetaData is None:
            logger.error("Failed to get meta data using %s" % strIssueKey)
            return (False, None)

        dictBuildedIssueData = JiraRest.buildIssueData(
            dictIssueData, dictIssueMetaData.get('fields', {}), True)
        if dictBuildedIssueData is None:
            logger.error("Issue data provided is not valid")
            return (False, None)

        strJsonData = JiraRest.jsonEncode({"fields": dictBuildedIssueData})
        logger.debug(strJsonData)

        intAttempts = 1
        while intAttempts <= 3:
            (output, errors) = JiraRest.sendRequest(
                ONEPLUS_JIRA_REST_ISSUE_URL + "/%s" % strIssueKey, "PUT", strJsonData)
            if errors is None:
                return (True, output)
            else:
                JiraRest.errorHandler(output, errors)
                logger.error(
                    "Attempting to recover (try %s out of 3)" % intAttempts)
                intAttempts += 1

        return (False, None)

    @staticmethod
    def queryIssues(dictJqlCmd):
        strJsonData = JiraRest.jsonEncode(dictJqlCmd)
        logger.debug(strJsonData)

        intAttempts = 1
        while intAttempts <= 3:
            (output, errors) = JiraRest.sendRequest(
                ONEPLUS_JIRA_REST_SEARCH_URL, "POST", strJsonData)
            if errors is None:
                return (True, JiraRest.jsonDecode(output))
            else:
                JiraRest.errorHandler(output, errors)
                logger.error(
                    "Attempting to recover (try %s out of 3)" % intAttempts)
                intAttempts += 1

        return (False, None)

    @staticmethod
    def addComment(strIssueKey, strCommentBody, dictVisibility=None):
        dictCommentData = {"body": strCommentBody}
        if dictVisibility is not None:
            dictCommentData["visibility"] = dictVisibility

        strJsonData = JiraRest.jsonEncode(dictCommentData)
        logger.debug(strJsonData)

        intAttempts = 1
        while intAttempts <= 3:
            (output, errors) = JiraRest.sendRequest(
                ONEPLUS_JIRA_REST_ISSUE_URL + "/%s/comment" % strIssueKey, "POST", strJsonData)
            if errors is None:
                return (True, JiraRest.jsonDecode(output))
            else:
                JiraRest.errorHandler(output, errors)
                logger.error(
                    "Attempting to recover (try %s out of 3)" % intAttempts)
                intAttempts += 1

        return (False, None)

    @staticmethod
    def addAttachment(strIssueKey, strFilePath):
        if isinstance(strFilePath, str):
            strFilePath = strFilePath.decode("utf-8")

        if os.path.isfile(strFilePath) is False:
            logger.error("Attachment not exists")
            return (False, None)

        intAttempts = 1
        while intAttempts <= 3:
            # print os.path.getsize(strFilePath)
            (output, errors) = JiraRest.sendRequest(ONEPLUS_JIRA_REST_ISSUE_URL +
                                                    "/%s/attachments" % strIssueKey, "POST", open(strFilePath, 'rb'))
            if errors is None:
                return (True, JiraRest.jsonDecode(output))
            else:
                JiraRest.errorHandler(output, errors)
                logger.error(
                    "Attempting to recover (try %s out of 3)" % intAttempts)
                intAttempts += 1

        return (False, None)

    @staticmethod
    def buildIssueData(dictIssueData, dictIssueFields, boolIgnoreRequired=False):
        dictBuildedIssueData = {}

        try:
            if boolIgnoreRequired is False:
                logger.info("Check required data in fields")
                for strKey in dictIssueFields:
                    dictCurrFieldMeta = dictIssueFields[strKey]
                    if dictCurrFieldMeta.get('required', False) is True and dictCurrFieldMeta.get('hasDefaultValue', False) is False:
                        if strKey not in dictIssueData:
                            logger.error(
                                "Key '%s' is required, but not provided" % strKey)
                            return None

            logger.info("Check provided issue data valid or not")
            for strKey in dictIssueData:
                if strKey not in dictIssueFields:
                    logger.warning("Key '%s' is not support, ignore" % strKey)
                else:
                    dictCurrFieldMeta = dictIssueFields[strKey]
                    # strCurrFieldType = dictCurrFieldMeta.get('type', '')
                    # ma = re.match(r'priority|user|string|array|project|issuetype|date', strCurrFieldType, re.IGNORECASE)
                    listCurrFiledAllowedValues = dictCurrFieldMeta.get(
                        'allowedValues', [])
                    currIssueDataValue = dictIssueData[strKey]
                    if JiraRest.checkDataValid(currIssueDataValue, listCurrFiledAllowedValues) is True:
                        logger.info("Add data '%s', Value: '%s'" %
                                    (strKey, currIssueDataValue))
                        dictBuildedIssueData[strKey] = currIssueDataValue
                    else:
                        logger.warning("Data '%s' not valid, ignore" % strKey)

            return dictBuildedIssueData
        except Exception:
            logger.exception("Exception Caught")
            return None

    @staticmethod
    def checkDataValid(objIssueData, listAllowedValues):
        if len(listAllowedValues) > 0:
            try:
                if isinstance(objIssueData, str):
                    return False
                if isinstance(objIssueData, dict):
                    boolValid = False
                    for dictCurrFiledAllowedValues in listAllowedValues:
                        currDiff = DictDiffer(
                            dictCurrFiledAllowedValues, objIssueData)
                        if len(currDiff.changed()) == 0 and len(currDiff.removed()) == 0:
                            boolValid = True
                            break
                    return boolValid
                if isinstance(objIssueData, list):
                    for objData in objIssueData:
                        if JiraRest.checkDataValid(objData, listAllowedValues) is False:
                            return False
                    return True

                return False
            except Exception:
                logger.exception("Exception Caught")
                return False

        return True

    @staticmethod
    def jsonDecode(strData):
        dictData = None

        try:
            dictData = json.loads(strData)
        except Exception:
            logger.exception("Exception Caught")

        return dictData

    @staticmethod
    def jsonEncode(dictData):
        strData = None

        try:
            strData = json.dumps(dictData)
        except Exception:
            logger.exception("Exception Caught")

        return strData

    @staticmethod
    def errorHandler(objOutput, tupleErrors):
        # Todo
        intErrorCode, strErrorMsg = tupleErrors
        if intErrorCode is None:
            logger.error("This is an exception, can not handle")
            return False
        if intErrorCode == 401:
            logger.info(strErrorMsg)
            return True
        if intErrorCode == 400:
            logger.info(objOutput)
            return True

        logger.error("Cannot handle this error: %s:%s" %
                     (intErrorCode, strErrorMsg))
        return False

    @staticmethod
    def sendRequest(strUrl, strRequestMethod="GET", objData=None):
        bufBodyResult = cStringIO.StringIO()
        bufHeaderResullt = cStringIO.StringIO()
        curlRequest = pycurl.Curl()
        curlRequest.setopt(curlRequest.URL, strUrl)
        curlRequest.setopt(curlRequest.SSL_VERIFYPEER, False)
        # curlRequest.setopt(curlRequest.FAILONERROR, True)
        curlRequest.setopt(curlRequest.USERPWD, "%s:%s" %
                           (ONEPLUS_JIRA_USER, ONEPLUS_JIRA_PWD))
        curlRequest.setopt(curlRequest.HTTPHEADER, [
                           'Content-Type: application/json'])
        curlRequest.setopt(curlRequest.WRITEFUNCTION, bufBodyResult.write)
        curlRequest.setopt(curlRequest.HEADERFUNCTION, bufHeaderResullt.write)
        if strRequestMethod != "GET":
            curlRequest.setopt(curlRequest.CUSTOMREQUEST, strRequestMethod)
        if objData is not None:
            if isinstance(objData, str):
                curlRequest.setopt(curlRequest.POSTFIELDS, objData)
            elif isinstance(objData, file):
                curlRequest.setopt(curlRequest.HTTPHEADER, [
                                   'X-Atlassian-Token: nocheck'])
                curlRequest.setopt(curlRequest.HTTPPOST, [('file', (pycurl.FORM_BUFFER, CodecUtil.returnUtfStr(
                    os.path.basename(objData.name)), pycurl.FORM_BUFFERPTR, objData.read())), ])
                objData.close()

        try:
            curlRequest.perform()
        except Exception, e:
            logger.exception("Exception Caught")
            return (None, e)
        else:
            intReturnCode = curlRequest.getinfo(curlRequest.HTTP_CODE)
            if intReturnCode < 400:
                return (bufBodyResult.getvalue(), None)
            else:
                strStatusLine = bufHeaderResullt.getvalue().splitlines()[0]
                ma = re.match(r'HTTP\/\S*\s*\d+\s*(.*?)\s*$', strStatusLine)
                if ma:
                    return (bufBodyResult.getvalue(), (intReturnCode, ma.group(1)))
                else:
                    return (bufBodyResult.getvalue(), (intReturnCode, "Unknown error"))

    @staticmethod
    def __optionHandler(dictOptions):
        listOptions = []
        for key in dictOptions:
            listOptions.append("--%s" % key)
            if dictOptions[key] is not None and dictOptions[key] != "":
                listOptions.append(str(dictOptions[key]))

        return listOptions

    @staticmethod
    def __sendRequest(strUrl, jsonData=None):
        # reqManager = urllib2.HTTPPasswordMgrWithDefaultRealm()
        # reqManager.add_password(None, strUrl, ONEPLUS_JIRA_USER, ONEPLUS_JIRA_PWD)
        # handlerCertification = urllib2.HTTPBasicAuthHandler(reqManager)

        handlerCertification = urllib2.HTTPBasicAuthHandler()
        handlerCertification.add_password(
            realm='PDQ Application', uri=strUrl, user=ONEPLUS_JIRA_USER, passwd=ONEPLUS_JIRA_PWD)

        if sys.version_info >= (2, 7, 9):
            ctxCertification = ssl.create_default_context()
            ctxCertification.check_hostname = False
            ctxCertification.verify_mode = ssl.CERT_NONE
            handlerHttps = urllib2.HTTPSHandler(context=ctxCertification)
            reqOpener = urllib2.build_opener(
                handlerHttps, handlerCertification)
        else:
            reqOpener = urllib2.build_opener(handlerCertification)

        reqRequest = urllib2.Request(
            strUrl, jsonData, {"Content-Type": "application/json"})
        reqResult = reqOpener.open(reqRequest)

        print reqResult.info()['Content-Length']
        print reqResult.read()
        return (None, None)


class JiraSoap(object):

    """docstring for JiraSoap"""

    def __init__(self, arg):
        super(JiraSoap, self).__init__()
        self.arg = arg


class DictDiffer(object):

    """
    Calculate the difference between two dictionaries as:
    (1) items added
    (2) items removed
    (3) keys same in both but changed values
    (4) keys same in both and unchanged values
    """

    def __init__(self, current_dict, past_dict):
        self.current_dict, self.past_dict = current_dict, past_dict
        self.set_current, self.set_past = set(
            current_dict.keys()), set(past_dict.keys())
        self.intersect = self.set_current.intersection(self.set_past)

    def added(self):
        return self.set_current - self.intersect

    def removed(self):
        return self.set_past - self.intersect

    def changed(self):
        return set(o for o in self.intersect if self.past_dict[o] != self.current_dict[o])

    def unchanged(self):
        return set(o for o in self.intersect if self.past_dict[o] == self.current_dict[o])


if __name__ == '__main__':
    import sys
    hdlr = logging.StreamHandler(sys.stdout)
    hdlr.setFormatter(logging.Formatter(
        '%(asctime)s - %(name)s [%(levelname)s]: %(message)s'))
    logger.addHandler(hdlr)
    dictCreatTmp = {}
    dictCreatTmp["reporter"] = {"name": "abts_admin"}
    dictCreatTmp["components"] = [{"name": "Camera"}]
    dictCreatTmp["customfield_10201"] = {"value": "Camera"}
    dictCreatTmp["summary"] = "特殊字符测试"
    dictCreatTmp["customfield_10102"] = {"value": "1"}
    dictCreatTmp["customfield_10101"] = "123"
    # print JiraRest.creatIssue("DEMO", "Bug", dictCreatTmp)
    dictEditTmp = {}
    dictEditTmp[
        "description"] = ' ! " # $ % & ( ) * + , - . / 0 1 2 3 4 5 6 7 8 9 : ; < = > @'
    # print JiraRest.editIssue("DEMO-56", dictEditTmp)
    # print JiraRest.addComment("DEMO-56", "中英文混搭Comment")
    dictQueryTmp = {}
    dictQueryTmp["jql"] = "project=DEMO"
    dictQueryTmp["startAt"] = 0
    dictQueryTmp["maxResults"] = 2
    dictQueryTmp["fields"] = ["id", "key"]
    # print JiraRest.queryIssues(dictQueryTmp)
    print JiraRest.addAttachment("DEMO-56", "中文附件测试.jpg")

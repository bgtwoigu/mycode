#!/usr/bin/env python
# -*- coding: utf-8 -*-
import os
import re
import cgi
import uuid
import collections
import smtplib
import logging
import traceback
import HTML
from email.mime.text import MIMEText
from email.mime.image import MIMEImage
from email.mime.multipart import MIMEMultipart


__version__ = '0.9.0'


logger = logging.getLogger("MailOperation")
logger.setLevel(logging.INFO)


class MailConstruct(object):

    """docstring for MailConstruct"""

    def __init__(self, dictTaskInfo, dictBuildInfo, dictTestTask):
        self.strSmtpHost = "smtp.oneplus.cn"
        self.strAdminMailAddress = "abts_admin@oneplus.cn"
        self.strAdminPassword = "asia+123"
        self.intSmtpPort = 25
        self.dictTaskInfo = dictTaskInfo
        self.dictBuildInfo = dictBuildInfo
        self.dictTestTask = dictTestTask
        self.msgRoot = MIMEMultipart('related')
        self.msgBody = ""

        try:
            self.initHandler()
            self.mailBodyGenerate()
        except Exception:
            raise Exception(traceback.format_exc())

    def initHandler(self):
        logger.info("Generate Mail Title")
        # strTaskPurpose = re.sub(r'(\w)([A-Z])', r'\1 \2', self.dictTaskInfo.pop("Purpose", "N/A"))
        self.msgRoot['Subject'] = "OnePlus ABTS Report - Purpose: %s, Project: %s" % (
            self.dictTaskInfo.pop("Purpose", "N/A"), self.dictTaskInfo.pop("ProjectName", "N/A"))

        logger.info("Generate Mail Address")
        self.msgRoot[
            'From'] = "Automatic Build & Test System <abts_admin@oneplus.cn>"
        self.listMailTo = self.dictTaskInfo.pop("MailList")
        self.msgRoot['To'] = ";".join(self.listMailTo)
        self.listMailTo.append("zhangbin@oneplus.cn")
        # self.listMailTo.append(self.strAdminMailAddress)

    def sendMail(self):
        try:
            smtpHanlder = smtplib.SMTP()
            logger.info("Connect To Smtp Server: %s:%s" %
                        (self.strSmtpHost, self.intSmtpPort))
            smtpHanlder.connect(self.strSmtpHost, self.intSmtpPort)
            logger.info("Login Smtp Server As User: %s" %
                        self.strAdminMailAddress)
            smtpHanlder.login(self.strAdminMailAddress, self.strAdminPassword)
            logger.info("Send Mail To '%s'" % self.msgRoot['To'])
            smtpHanlder.sendmail(self.strAdminMailAddress,
                                 self.listMailTo, self.msgRoot.as_string())
        except Exception:
            logger.exception("Exception Caught")
            logger.error("Failed To Send Mail Report")
        else:
            logger.info("Mail Report Has Been Sent Out")
        finally:
            smtpHanlder.quit()

    def tableColumnsMerge(self, strTable):
        strTableNew = ""
        strCurrentTestLogName = ""

        try:
            for strLine in strTable.split("\n"):
                ma = re.search(
                    r'(.*)<td(.*)>\[TLog\](.*)\[(\d*)\]</td>(.*)', strLine, re.IGNORECASE)
                if ma:
                    if ma.group(3) != strCurrentTestLogName:
                        strCurrentTestLogName = ma.group(3)
                        strTableNew += "%s<td rowspan=\"%s\"%s>%s</td>%s\n" % (
                            ma.group(1), ma.group(4), ma.group(2), strCurrentTestLogName, ma.group(5))

                    continue

                strTableNew += "%s\n" % strLine
        except Exception:
            logger.exception("Exception Caught")
            strTableNew = strTable
        else:
            strTableNew = strTableNew.rstrip("\n")

        return strTableNew

    def buttonCreater(self, strBtnTxt, strButtonURL="", strBackgroundColor="#556270", strFontColor="#ffffff", strBorderColor="#1e3650", intButtonWidth=100, intButtonHeight=30, intBorderRadius=4, strBackgroundImageLink=None):
        strHtml = '<div><!--[if mso]>'
        strHtml += '<v:roundrect xmlns:v="urn:schemas-microsoft-com:vml" xmlns:w="urn:schemas-microsoft-com:office:word" '
        strHtml += 'href="%s" ' % strButtonURL

        strHtml += 'style="height:%dpx;v-text-anchor:middle;width:%dpx;" ' % (
            intButtonHeight, intButtonWidth)
        strHtml += 'arcsize="%d%%" strokecolor="%s" fill="t">' % (
            intBorderRadius * 3.5, strBorderColor)

        strHtml += '<v:fill type="tile" '
        if strBackgroundImageLink is not None:
            strHtml += 'src="%s" ' % strBackgroundImageLink
        strHtml += 'color="%s" />' % strBackgroundColor

        strHtml += '<w:anchorlock/>'
        strHtml += '<center style="color:%s;font-family:sans-serif;font-size:13px;font-weight:bold;">%s</center>' % (
            strFontColor, strBtnTxt)
        strHtml += '</v:roundrect>'
        strHtml += '<![endif]-->'

        strHtml += '<a href="%s" ' % strButtonURL
        strHtml += 'style="background-color:%s;' % strBackgroundColor
        if strBackgroundImageLink is not None:
            strHtml += 'background-image:url(%s);' % strBackgroundImageLink

        strHtml += 'border:1px solid %s;border-radius:%dpx;' % (
            strBorderColor, intBorderRadius)
        strHtml += 'color:%s;display:inline-block;font-family:sans-serif;font-size:13px;font-weight:bold;' % strFontColor
        strHtml += 'line-height:%dpx;text-align:center;text-decoration:none;width:%dpx;' % (
            intButtonHeight, intButtonWidth)
        strHtml += '-webkit-text-size-adjust:none;mso-hide:all;">'
        strHtml += '%s</a></div>' % strBtnTxt

        return strHtml

    def mailBodyGenerate(self):
        msgAlternative = MIMEMultipart('alternative')
        self.msgRoot.attach(msgAlternative)
        dictColors = {'Pass': 'Green', 'Fail': 'Red',
                      'Skip': 'RoyalBlue', 'Abort': 'Gray', 'Unknown': 'Black'}

        logger.info("Generate Mail Body - Title & Task Basic Info")
        msgTitle = '<font face="verdana" size="4"><b><u>OnePlus Automatic Build & Test System %s</u></b></font>' % __version__
        msgBody = '<font face="verdana" size="2"><br>'
        msgBody += '<br><b><u>Task Info</u></b><br>'
        for key in self.dictTaskInfo:
            msgBody += '<b>%s : </b>%s<br>' % (key, self.dictTaskInfo[key])

        logger.info("Generate Mail Body - Build Info")
        msgBody += '<br><b><u>Build Info</u></b><br>'
        for key in self.dictBuildInfo:
            if key.lower() == "result":
                strCurrentResult = self.dictBuildInfo[key]
                strColorName = dictColors.get(strCurrentResult, 'Black')
                msgBody += '<b>%s : </b><span style="color:%s">%s</span><br>' % (
                    key, strColorName, strCurrentResult)
            elif key.lower().endswith("link"):
                strLinkInHtml = '<a href="%s">%s</a>' % (
                    self.dictBuildInfo[key], self.dictBuildInfo[key])
                msgBody += '<b>%s : </b>%s<br>' % (key, strLinkInHtml)
            elif key.lower().endswith("path"):
                strSharePathInHtml = '<a href="file://///%s">%s</a>' % (
                    self.dictBuildInfo[key].lstrip('/').lstrip('\\'), self.dictBuildInfo[key])
                msgBody += '<b>%s : </b>%s<br>' % (key, strSharePathInHtml)
            else:
                msgBody += '<b>%s : </b>%s<br>' % (key,
                                                   self.dictBuildInfo[key])

        logger.info("Generate Mail Body - Test Info")
        msgBody += '<br><b><u>Test Info</u></b><br>'
        if self.dictTestTask is None:
            msgBody += 'No Test Been Scheduled<br>'
        else:
            dictTestInfo = self.dictTestTask.get("TestInfo", {})
            dictTestSummarize = self.dictTestTask.get("TestSummarize", {})
            dictIssueList = self.dictTestTask.get("IssueList", {})
            listReportImage = self.dictTestTask.get("ReportImage", [])

            for key in dictTestInfo:
                if key.lower() == "result":
                    strCurrentResult = dictTestInfo[key]
                    strColorName = dictColors.get(strCurrentResult, 'Black')
                    msgBody += '<b>%s : </b><span style="color:%s">%s</span><br>' % (
                        key, strColorName, strCurrentResult)
                elif key.lower().endswith("link"):
                    strLinkInHtml = '<a href="%s">%s</a>' % (
                        dictTestInfo[key], dictTestInfo[key])
                    msgBody += '<b>%s : </b>%s<br>' % (key, strLinkInHtml)
                elif key.lower().endswith("path"):
                    strSharePathInHtml = '<a href="file://///%s">%s</a>' % (
                        dictTestInfo[key].lstrip('/').lstrip('\\'), dictTestInfo[key])
                    msgBody += '<b>%s : </b>%s<br>' % (key, strSharePathInHtml)
                else:
                    msgBody += '<b>%s : </b>%s<br>' % (key, dictTestInfo[key])

            logger.info("Generate Mail Body - Test Summarize")
            if len(dictTestSummarize) > 0:
                listResultStatus = ["Pass", "Fail", "Skip", "Abort"]
                tableTestResult = HTML.Table(header_row=[
                                             "TestSuiteName"] + listResultStatus, col_align=['left', 'center', 'center', 'center', 'center'])
                listTestResultTotal = ["Total"]
                for strTestName in dictTestSummarize:
                    if strTestName.lower() == "total":
                        for strStatus in listResultStatus:
                            listTestResultTotal.append(
                                str(dictTestSummarize[strTestName].get(strStatus, 0)))
                    else:
                        listCurrentTestResult = [strTestName]
                        for strStatus in listResultStatus:
                            listCurrentTestResult.append(
                                str(dictTestSummarize[strTestName].get(strStatus, 0)))

                        tableTestResult.rows.append(HTML.TableRow(
                            listCurrentTestResult, col_styles=['font-size:87%'] * 5))

                for intIndex in xrange(len(listTestResultTotal)):
                    strResultValue = listTestResultTotal[intIndex]
                    listTestResultTotal[intIndex] = HTML.TableCell(
                        strResultValue, width='20%', bgcolor='Silver')
                tableTestResult.rows.append(HTML.TableRow(
                    listTestResultTotal, col_styles=['font-size:87%'] * 5))

                msgBody += '<br><b><u>Test Summarize</u></b><br>'
                msgBody += str(tableTestResult)

            logger.info("Generate Mail Body - Test Issue List")
            msgBody += '<br><b><u>Issue List</u></b><br>'
            if len(dictIssueList) > 0:
                tableTestIssue = HTML.Table(header_row=[
                                            "TestSuiteName", "TestName", "IssueType", "ErrorMsg", "JiraTicket"], col_align=['center'] * 5)
                for strTestSuiteName in dictIssueList:
                    orderedDictIssueInfo = collections.OrderedDict(
                        sorted(dictIssueList[strTestSuiteName].items()))
                    boolIsFirstRow = True
                    for strTestName in orderedDictIssueInfo:
                        listIssueInfo = []
                        intColumnsNum = 4
                        if boolIsFirstRow is True:
                            listIssueInfo.append(HTML.TableCell(strTestSuiteName, attribs={
                                                 "rowspan": len(orderedDictIssueInfo)}))
                            boolIsFirstRow = False
                            intColumnsNum = 5

                        listIssueInfo.append(strTestName)
                        strIssueType = orderedDictIssueInfo[
                            strTestName].get("IssueType", "Unknown")
                        strErrorMsg = orderedDictIssueInfo[
                            strTestName].get("ErrorMsg", "")
                        strJiraTicketLink = orderedDictIssueInfo[
                            strTestName].get("JiraTicket", "")
                        strJiraTicketLink = '<a href="%s">%s</a> ' % (
                            strJiraTicketLink, strJiraTicketLink) if strJiraTicketLink != "" else "N/A"
                        listIssueInfo.append(HTML.TableCell(
                            strIssueType, bgcolor=dictColors.get(strIssueType, 'White')))
                        # replace("\n", "<br />")
                        listIssueInfo.append(strErrorMsg)
                        listIssueInfo.append(strJiraTicketLink)
                        tableTestIssue.rows.append(HTML.TableRow(
                            listIssueInfo, col_styles=['font-size:87%'] * intColumnsNum))

                # msgBody += self.tableColumnsMerge(repr(tableTestRealIssue))
                msgBody += str(tableTestIssue)
            else:
                msgBody += "<span style='background:green;mso-highlight:green'>No issues in test</span>"

            logger.info("Generate Mail Body - Test Report Image")
            if len(listReportImage) > 0:
                msgBody += '<br><b><u>Report Image</u></b><br>'
                for dictReportImageInfo in listReportImage:
                    strReportImageTitle = dictReportImageInfo.get(
                        "Title", "No Title")
                    strReportImagePath = dictReportImageInfo.get("Path", None)
                    strReportImageCid = str(uuid.uuid4())

                    if strReportImagePath is not None and os.path.isfile(strReportImagePath):
                        try:
                            with open(strReportImagePath, "rb") as fileImg:
                                strImageBinary = fileImg.read()
                        except Exception:
                            logger.exception("Exception Caught")
                        else:
                            msgBody += '<b>%s</b><br>' % strReportImageTitle
                            msgBody += '<div dir="ltr">'
                            msgBody += '<img src="cid:%s" alt="%s"><br></div><br>' % (
                                strReportImageCid, cgi.escape(strReportImageTitle, quote=True))

                            fileImg.close()
                            msgImage = MIMEImage(
                                strImageBinary, name=os.path.basename(strReportImagePath))
                            self.msgRoot.attach(msgImage)
                            msgImage.add_header(
                                'Content-ID', '<%s>' % strReportImageCid)
            else:
                msgBody += "<span style='background:green;mso-highlight:green'>No report image</span>"

        logger.info("Generate Mail Body - Contact Info")
        msgBody += "<br><br>This report is generated by OnePlus-ABTS"
        msgBody += "<br>For any assistance or query, Please contact me at libin859@oneplus.cn"
        self.msgBody = msgTitle + msgBody

        logger.info("Transform Mail Body To Html")
        msgHtml = MIMEText(msgTitle + msgBody,
                           _subtype='html', _charset='utf-8')
        msgAlternative.attach(msgHtml)


if __name__ == '__main__':
    import sys
    hdlr = logging.StreamHandler(sys.stdout)
    hdlr.setFormatter(logging.Formatter(
        '%(asctime)s - %(name)s [%(levelname)s]: %(message)s'))
    logger.addHandler(hdlr)

    listTo = ["libin859@oneplus.cn"]
    dictTaskInfo = {"TaskID": "0123456789", "CreateTime": "2015-05-22-16-50", "Submitter": "abtsadmin",
                    "Project": "RAIN", "Purpose": "DailyBuildTest", "SharePath": "", "MailList": listTo}
    dictBuildInfo = {"Result": "Pass", "Revision": "160526_0300_user", "Branch": "Develop",
                     "SharePath": "//172.21.106.141/sw_release/MSM8996/15801/DailyBuild/DEV_ROM/HYDROGEN_SIGNED/MSM_15801_160526_0300_user", "ErrorMsg": None}
    dictTaskTest = {"Result": "Pass", "ErrorMsg": None, "DeviceID": "327aaf8c"}
    dictTestSummarize = {"冒烟测试": {"Fail": 2, "Skip": 0, "Abort": 0, "Pass": 10}, "Camera API Test": {
        "Fail": 1, "Skip": 0, "Abort": 0, "Pass": 50}, "Total": {"Fail": 3, "Skip": 0, "Abort": 0, "Pass": 60}}
    dictIssueList = {}
    dictIssueList["冒烟测试"] = {'主板温度': {'ErrorMsg': '待机温度大于36°C', "IssueType": "Fail",
                                      "JiraTicket": "https://pjira.oneplus.cn/jira/browse/DEMO-56"}, '电流大小': {'ErrorMsg': '待机电流大于0.5A', "IssueType": "Fail"}}
    dictIssueList["Camera API Test"] = {"Take Photo": {
        'ErrorMsg': 'Failed to Setting HDR', "IssueType": "Skip"}}
    listReportImage = [{"Title": "测试", "Path": "中文附件测试.jpg"}, {
        "Title": "Test", "Path": "2-play-video.png"}]
    dictTestTask = {"TestInfo": dictTaskTest, "TestSummarize": dictTestSummarize,
                    "IssueList": dictIssueList, "ReportImage": listReportImage}
    myMail = MailConstruct(dictTaskInfo, dictBuildInfo, dictTestTask)
    myMail.sendMail()

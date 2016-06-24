#!/usr/bin/env python
# -*- coding: utf-8 -*-
import time
import json
import random
import MySQLdb
import logging
import warnings


logger = logging.getLogger("DatabaseOperation")
logger.setLevel(logging.INFO)

warnings.filterwarnings('ignore', category=MySQLdb.Warning)


def insertDataToTaskTable(strTaskID, strTableName, dictParameters):
    listKey = []
    listValue = []

    for key in dictParameters:
        listKey.append(key)
        listValue.append(dictParameters[key])

    strSqlCmd = "INSERT INTO %s (TaskID, " % strTableName
    for key in listKey:
        strSqlCmd += "%s, " % key

    strSqlCmd = strSqlCmd[0:-2] + ") VALUES (\"%s\", " % strTaskID
    for value in listValue:
        if value is None:
            strSqlCmd += "NULL, "
        elif isinstance(value, (int, long)):
            strSqlCmd += "%s, " % value
        elif isinstance(value, (dict, list, tuple)):
            try:
                jsValue = json.dumps(value, ensure_ascii=False)
            except Exception:
                logger.warning("Failed To Dump Data Into Json Format")
                strSqlCmd += "\"%s\", " % str(value).replace('"', '\\"')
            else:
                strSqlCmd += "'%s', " % jsValue.replace("'", "\\'")
        else:
            strSqlCmd += "\"%s\", " % value.replace('"', '\\"')

    strSqlCmd = strSqlCmd[0:-2] + ")"
    intEffectedRows, tupleExecuteResult = executeSqlCmd(strSqlCmd)
    return intEffectedRows


def updateDataToTaskTable(strTaskID, strTableName, dictUpdateParameters):
    strUpdateParameters = ""

    for key in dictUpdateParameters:
        value = dictUpdateParameters[key]
        if value is None:
            strUpdateParameters += "%s=NULL, " % key
        elif isinstance(value, (int, long)):
            strUpdateParameters += "%s=%s, " % (key, value)
        elif isinstance(value, (dict, list, tuple)):
            try:
                jsValue = json.dumps(value, ensure_ascii=False)
            except Exception:
                logger.warning("Failed To Dump Data Into Json Format")
                strUpdateParameters += "%s=\"%s\", " % (
                    key, str(value).replace('"', '\\"'))
            else:
                strUpdateParameters += "%s='%s', " % (
                    key, jsValue.replace("'", "\\'"))
        else:
            strUpdateParameters += "%s=\"%s\", " % (
                key, value.replace('"', '\\"'))
    strUpdateParameters = strUpdateParameters[0:-2]

    strSqlCmd = "UPDATE %s SET %s WHERE TaskID=\"%s\"" % (
        strTableName, strUpdateParameters, strTaskID)
    intEffectedRows, tupleExecuteResult = executeSqlCmd(strSqlCmd)
    return intEffectedRows


def deleteDataFromTaskTable(strTaskID, listTableName=["task_test_build", "task_test_device", "task_test_info", "task_test_result"]):
    for strTableName in listTableName:
        strSqlCmd = "DELETE FROM %s WHERE TaskID=\"%s\"" % (
            strTableName, strTaskID)
        executeSqlCmd(strSqlCmd)


def insertDataToTestDeviceTable(dictParameters):
    listKey = []
    listValue = []

    for key in dictParameters:
        listKey.append(key)
        listValue.append(dictParameters[key])

    strSqlCmd = "INSERT INTO device_test ("
    for key in listKey:
        strSqlCmd += "%s, " % key

    strSqlCmd = strSqlCmd[0:-2] + ") VALUES ("
    for value in listValue:
        if value is None:
            strSqlCmd += "NULL, "
        elif isinstance(value, (int, long)):
            strSqlCmd += "%s, " % value
        else:
            strSqlCmd += "\"%s\", " % value.replace('"', '\\"')

    strSqlCmd = strSqlCmd[0:-2] + ")"
    intEffectedRows, tupleExecuteResult = executeSqlCmd(strSqlCmd)
    return intEffectedRows


def updateDataToTestDeviceTable(dictUpdateParameters, dictWhereParameters):
    strUpdateParameters = ""
    strWhereParameters = ""

    for key in dictUpdateParameters:
        value = dictUpdateParameters[key]
        if value is None:
            strUpdateParameters += "%s=NULL, " % key
        elif isinstance(value, (int, long)):
            strUpdateParameters += "%s=%s, " % (key, value)
        else:
            strUpdateParameters += "%s=\"%s\", " % (
                key, value.replace('"', '\\"'))
    strUpdateParameters = strUpdateParameters[0:-2]

    if len(dictWhereParameters) > 0:
        for key in dictWhereParameters:
            value = dictWhereParameters[key]
            if value is None:
                strWhereParameters += "%s=NULL AND " % key
            elif isinstance(value, (int, long)):
                strWhereParameters += "%s=%s AND " % (key, value)
            else:
                strWhereParameters += "%s=\"%s\" AND " % (
                    key, value.replace('"', '\\"'))
        strWhereParameters = strWhereParameters[0:-5]

    strSqlCmd = "UPDATE device_test SET %s" % strUpdateParameters
    if len(strWhereParameters) > 0:
        strSqlCmd += " WHERE %s" % strWhereParameters

    intEffectedRows, tupleExecuteResult = executeSqlCmd(strSqlCmd)
    return intEffectedRows


def deleteDataFromTestDeviceTable(dictWhereParameters):
    strWhereParameters = ""

    for key in dictWhereParameters:
        value = dictWhereParameters[key]
        if value is None:
            strWhereParameters += "%s=NULL AND " % key
        elif isinstance(value, (int, long)):
            strWhereParameters += "%s=%s AND " % (key, value)
        else:
            strWhereParameters += "%s=\"%s\" AND " % (
                key, value.replace('"', '\\"'))
    strWhereParameters = strWhereParameters[0:-5]

    strSqlCmd = "DELETE FROM device_test WHERE %s" % strWhereParameters
    intEffectedRows, tupleExecuteResult = executeSqlCmd(strSqlCmd)
    return intEffectedRows


def insertDataToTestScheduleTable(dictParameters):
    listKey = []
    listValue = []

    for key in dictParameters:
        listKey.append(key)
        listValue.append(dictParameters[key])

    strSqlCmd = "INSERT INTO schedule_task_test ("
    for key in listKey:
        strSqlCmd += "%s, " % key

    strSqlCmd = strSqlCmd[0:-2] + ") VALUES ("
    for value in listValue:
        if value is None:
            strSqlCmd += "NULL, "
        elif isinstance(value, (int, long)):
            strSqlCmd += "%s, " % value
        elif isinstance(value, (dict, list, tuple)):
            try:
                jsValue = json.dumps(value, ensure_ascii=False)
            except Exception:
                logger.warning("Failed To Dump Data Into Json Format")
                strSqlCmd += "\"%s\", " % str(value).replace('"', '\\"')
            else:
                strSqlCmd += "'%s', " % jsValue.replace("'", "\\'")
        else:
            strSqlCmd += "\"%s\", " % value.replace('"', '\\"')

    strSqlCmd = strSqlCmd[0:-2] + ")"
    intEffectedRows, tupleExecuteResult = executeSqlCmd(strSqlCmd)
    return intEffectedRows


def updateDataToTestScheduleTable(strID, dictUpdateParameters):
    strUpdateParameters = ""
    strWhereParameters = ""

    for key in dictUpdateParameters:
        value = dictUpdateParameters[key]
        if value is None:
            strUpdateParameters += "%s=NULL, " % key
        elif isinstance(value, (int, long)):
            strUpdateParameters += "%s=%s, " % (key, value)
        elif isinstance(value, (dict, list, tuple)):
            try:
                jsValue = json.dumps(value, ensure_ascii=False)
            except Exception:
                logger.warning("Failed To Dump Data Into Json Format")
                strUpdateParameters += "%s=\"%s\", " % (
                    key, str(value).replace('"', '\\"'))
            else:
                strUpdateParameters += "%s='%s', " % (
                    key, jsValue.replace("'", "\\'"))
        else:
            strUpdateParameters += "%s=\"%s\", " % (
                key, value.replace('"', '\\"'))
    strUpdateParameters = strUpdateParameters[0:-2]

    strSqlCmd = "UPDATE schedule_task_test SET %s WHERE ID=\"%s\"" % (
        strUpdateParameters, strID)
    if len(strWhereParameters) > 0:
        strSqlCmd += " WHERE %s" % strWhereParameters

    intEffectedRows, tupleExecuteResult = executeSqlCmd(strSqlCmd)
    return intEffectedRows


def deleteDataToTestScheduleTable(strID):
    strSqlCmd = "DELETE FROM schedule_task_test WHERE ID=\"%s\"" % strID
    executeSqlCmd(strSqlCmd)


def getDataFromTable(strTableName, strFieldName="*", strWhereClause=None):
    strSqlCmd = "SELECT %s FROM %s" % (strFieldName, strTableName)
    if strWhereClause is not None:
        strSqlCmd += " WHERE %s" % strWhereClause

    intEffectedRows, tupleExecuteResult = executeSqlCmd(strSqlCmd)
    return tupleExecuteResult


def checkIsTaskExistsInTaskTable(strTableName, strTaskID):
    strSqlCmd = "SELECT * FROM %s WHERE TaskID=\"%s\"" % (
        strTableName, strTaskID)
    intEffectedRows, tupleExecuteResult = executeSqlCmd(strSqlCmd)

    if intEffectedRows == 0:
        return False
    return True


def executeSqlCmd(strSqlCmd, strHostName="172.21.8.38", strUserName="abtsadmin", strPasswd="123456", strDbName="oneplusabts_dev", charset='utf8'):
    intEffectedRows = 0
    tupleExecuteResult = ()
    tupleExecuteWarning = ()

    try:
        logger.info("Connect To Mysql Host: '%s', DB: '%s'" %
                    (strHostName, strDbName))
        conn = MySQLdb.connect(host=strHostName, user=strUserName,
                               passwd=strPasswd, db=strDbName, charset=charset)
        cur = conn.cursor()
    except Exception:
        logger.exception("Exception Caught")
    else:
        try:
            logger.info("Execute Sql Cmd '%s'" % strSqlCmd)
            intEffectedRows = cur.execute(strSqlCmd)
            tupleExecuteResult = cur.fetchall()
            conn.commit()
        except Exception:
            logger.exception("Exception Caught")
            conn.rollback()
        else:
            cur.execute("SHOW WARNINGS")
            tupleExecuteWarning = cur.fetchall()
            if len(tupleExecuteWarning) > 0:
                for warning in tupleExecuteWarning:
                    logger.warning("Mysql %s, %s, %s" % warning)
    finally:
        logger.info("Disconnect From Mysql")
        cur.close()
        conn.close()

    return (intEffectedRows, tupleExecuteResult)


if __name__ == '__main__':
    import sys
    hdlr = logging.StreamHandler(sys.stdout)
    hdlr.setFormatter(logging.Formatter(
        '%(asctime)s - %(name)s [%(levelname)s]: %(message)s'))
    logger.addHandler(hdlr)
    timestamp = str(int(time.time()))
    # strRandomString = ''.join(random.choice(string.ascii_letters) for i in range(10))

    while True:
        strRandomString = str(random.random())
        if len(strRandomString) > 5:
            strRandomString = timestamp + strRandomString[-3:]
            break

    aa = getDataFromTable("task_test_info", strFieldName="ProjectName",
                          strWhereClause="TaskID=\"1465202347101\"")[0][0]
    listCurrentTestScriptList = getDataFromTable(
        "task_test_result", strFieldName="ReportImage", strWhereClause="TaskID=\"1465202347101\"")[0][0]
    # listCurrentTestScriptList = json.loads(listCurrentTestScriptList, encoding="utf-8", strict=False)
    print type(aa)
    print aa
    print type(listCurrentTestScriptList)
    print listCurrentTestScriptList
    # print type(aa.encode("utf-8"))

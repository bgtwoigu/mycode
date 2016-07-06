import re
import os
import logging
import subprocess


logger = logging.getLogger("AdbOperation")
logger.setLevel(logging.INFO)


def startAdbServer():
    """
    Start ADB server
    """

    os.system("adb start-server")


def getFistAdbDevice():
    """
    Get first device ID in ADB

    @return: Str of phone's device ID
    """

    getAdbDevices = subprocess.Popen(
        ['adb', 'devices'], stdout=subprocess.PIPE, stderr=subprocess.PIPE)
    (output, error) = getAdbDevices.communicate()
    #for deviceInfo in getAdbDevices.stdout.read().split('\n'):
    for deviceInfo in output.split('\n'):
        if re.search("device$", deviceInfo.strip(), re.IGNORECASE):
            return deviceInfo.split("\t")[0]
    return None


def isDeviceExists(strDeviceID):
    """
    Check the device is connected to computer with specified ID

    @type strDeviceID: str
    @param strDeviceID: Str of phone's device ID
    @return: True if device is connected
    """

    getAdbDevices = subprocess.Popen(
        ['adb', 'devices'], stdout=subprocess.PIPE, stderr=subprocess.PIPE)
    (output, error) = getAdbDevices.communicate()
    #for deviceInfo in getAdbDevices.stdout.read().split('\n'):
    for deviceInfo in output.split('\n'):
        if re.search("device$", deviceInfo.strip(), re.IGNORECASE):
            if re.match(strDeviceID, deviceInfo.split("\t")[0]):
                return True
    return False


def executeAdbCmd(listCmd, strDeviceID=getFistAdbDevice, boolShellCmd=False):
    listAdbCmd = ["adb", "-s", strDeviceID]
    if boolShellCmd is True:
        listAdbCmd.append("shell")

    listCmd = [x for x in listCmd if x.strip() != ""]
    listAdbCmd.extend(listCmd)

    if strDeviceID is not None and isDeviceExists(strDeviceID) is True:
        try:
            logger.info("Executing Adb Cmd: %s" % ' '.join(listAdbCmd))

            objProcess = subprocess.Popen(
                listAdbCmd, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
            outputs, errors = objProcess.communicate()
            logger.debug("Adb Cmd Output: %s" % outputs)

            if objProcess.returncode or errors:
                return (outputs, errors)
        except Exception, e:
            logger.error("Exception Caught: %s" %e)
            return (None, e)
        else:
            return (outputs, None)

    return (None, "Devices %s not exists" % strDeviceID)

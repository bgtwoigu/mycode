import re
import time
import logging
import subprocess


logger = logging.getLogger("FastbootOperation")
logger.setLevel(logging.INFO)


def getFistFastbootDevice():
    """
    Get first device ID in Fastboot

    @return: Str of phone's device ID
    """

    getAdbDevices = subprocess.Popen(
        ['fastboot', 'devices'], stdout=subprocess.PIPE)
    for deviceInfo in getAdbDevices.stdout.read().split('\n'):
        if re.search("fastboot$", deviceInfo.strip(), re.IGNORECASE):
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
        ['fastboot', 'devices'], stdout=subprocess.PIPE)
    for deviceInfo in getAdbDevices.stdout.read().split('\n'):
        if re.search("fastboot$", deviceInfo.strip(), re.IGNORECASE):
            if re.match(strDeviceID, deviceInfo.split("\t")[0]):
                return True
    return False


def fastbootWaitForDevice(strDeviceID):
    while isDeviceExists(strDeviceID) is False:
        time.sleep(1)

    return True


def executeFastbootCmd(listCmd, strDeviceID=getFistFastbootDevice):
    listAdbCmd = ["fastboot", "-s", strDeviceID]
    listCmd = [x for x in listCmd if x.strip() != ""]
    listAdbCmd.extend(listCmd)

    if strDeviceID is not None and isDeviceExists(strDeviceID) is True:
        try:
            logger.info("Executing Fastboot Cmd: %s" % ' '.join(listAdbCmd))

            objProcess = subprocess.Popen(
                listAdbCmd, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
            outputs, errors = objProcess.communicate()
            logger.debug("Fastboot Cmd Output: %s" % outputs)

            if objProcess.returncode:
                return (outputs, errors)
        except Exception, e:
            logger.exception("Exception Caught")
            return (None, e)
        else:
            outputs += errors
            return (outputs, None)

    return (None, "Devices %s not exists" % strDeviceID)


if __name__ == '__main__':
    import sys
    hdlr = logging.StreamHandler(sys.stdout)
    hdlr.setFormatter(logging.Formatter(
        '%(asctime)s - %(name)s [%(levelname)s]: %(message)s'))
    logger.addHandler(hdlr)
    executeFastbootCmd(["reboot", ""], "327aaf8c")

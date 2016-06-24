import os
import re
import logging
import FastbootOperation
import DeviceUtil


ONEPLUS_BUILD_SERVER_NAME = '//172.21.106.141/sw_release'
ONEPLUS_BUILD_SERVER_USER = "share"
ONEPLUS_BUILD_SERVER_PWD = "share"
# Ex. OnePlus3_16_A.01_160510
ONEPLUS_BUILD_NAME_RULE = r'^OnePlus(\d+)_(.+)_(\d{6})$'

logger = logging.getLogger("FlashImageOperation")
logger.setLevel(logging.INFO)


def flashJenkinsBuild(strDeviceID, strBuildPath, listImageIgnore=[]):
    logger.info("Flash jenkins build at '%s'" % strBuildPath)
    strBuildDebugPath = os.path.join(strBuildPath, "Debug")
    strBuildImgPath = None

    for strFileName in os.listdir(strBuildPath):
        ma = re.match(ONEPLUS_BUILD_NAME_RULE, strFileName)
        if ma:
            strBuildImgPath = os.path.join(strBuildPath, strFileName)

    if strBuildImgPath is None:
        logger.error("Failed to find image path at '%s'" % strBuildPath)
        return False

    dictImageConfig = getImageConfigFromFile(
        os.path.join(strBuildImgPath, "md5checksumtool.ini"))
    if len(dictImageConfig) == 0:
        logger.error("Failed to get image config")
        return False

    if "userdata" not in dictImageConfig and os.path.isfile(os.path.join(strBuildImgPath, "userdata.img")):
        dictImageConfig["userdata"] = os.path.join(
            strBuildImgPath, "userdata.img")

    if os.path.isfile(os.path.join(strBuildDebugPath, "bootdebug.img")):
        dictImageConfig["boot"] = os.path.join(
            strBuildDebugPath, "bootdebug.img")

    if FastbootOperation.isDeviceExists(strDeviceID) is False:
        DeviceUtil.rebootDevice(strDeviceID, DeviceUtil.BOOT_MODE_FASTBOOT)

    for strImgName in dictImageConfig:
        if strImgName in listImageIgnore or dictImageConfig[strImgName] in listImageIgnore:
            logger.info("User ignore to flash image '%s', skip" % strImgName)
            continue
        if fastbootFlashImg(strDeviceID, strImgName, os.path.join(strBuildImgPath, dictImageConfig[strImgName]), 3) is False:
            logger.error("Failed to flash image '%s'" % strImgName)
            return False

    logger.info("All images have been flashed")
    return DeviceUtil.rebootDevice(strDeviceID)


def flashErrorHandler(strErrors, strDeviceID=None):
    try:
        listErrors = strErrors.strip().split("\n")
        for error in listErrors:
            logger.debug("Flash Image Error Handle On: %s" % error)

            ma = re.search(
                r'^FAILED \(remote: Critical partition flashing is not allowed\)', error, re.IGNORECASE)
            if ma:
                # some partition not allowed to flash, so do critical unlock
                # first
                logger.info(
                    "Critical partition is not allowed to flash, do critical unlock")
                DeviceUtil.unlockBootloaderCritical(strDeviceID)
                DeviceUtil.rebootDevice(
                    strDeviceID, DeviceUtil.BOOT_MODE_FASTBOOT)
                return True

            ma = re.search(
                r'^FAILED \(remote: Partition flashing is not allowed\)', error, re.IGNORECASE)
            if ma:
                # bootloader not unlock, so do oem unlock first
                logger.info("Bootloader not unlock, do oem unlock")
                DeviceUtil.unlockBootloader(strDeviceID)
                DeviceUtil.rebootDevice(
                    strDeviceID, DeviceUtil.BOOT_MODE_FASTBOOT)
                return True

        logger.error("Can not handle this error %s" % strErrors)
        return False
    except Exception:
        logger.exception("Exception Caught")
        return False


def fastbootFlashImg(strDeviceID, strImgName, strImgPath, intAttempts=1):
    logger.info("Flash image '%s' using '%s'" % (strImgName, strImgPath))
    while intAttempts > 0:
        (outputs, errors) = FastbootOperation.executeFastbootCmd(
            ["flash", strImgName, strImgPath], strDeviceID)
        if errors is None:
            return True
        else:
            logger.error("Shell returns error while flash image: %s" % errors)
            flashErrorHandler(errors, strDeviceID)
            logger.info("Attempting to recover (try %s out of 20)" %
                        intAttempts)
            intAttempts -= 1

    return False


def getImageConfigFromFile(strConfigFilePath):
    dictConfig = {}

    try:
        fileConfigFile = open(strConfigFilePath, 'r')
        while True:
            strLine = fileConfigFile.readline()
            if not strLine:
                break

            if strLine.startswith("#") is False and strLine.strip() != "":
                listArgument = strLine.split()
                dictConfig[listArgument[1].strip()] = listArgument[-1].strip()
    except Exception:
        logger.exception("Exception Caught")
    finally:
        fileConfigFile.close()

    return dictConfig


if __name__ == '__main__':
    import sys
    hdlr = logging.StreamHandler(sys.stdout)
    hdlr.setFormatter(
        logging.Formatter('%(asctime)s - %(name)s [%(levelname)s]: %(message)s'))
    logger.addHandler(hdlr)
    flashJenkinsBuild(
        "327aaf8c", "MSM8996/15801/DailyBuild/DEV_ROM/HYDROGEN_SIGNED/MSM_15801_160519_1500_user")
    # DeviceUtil.rebootDevice("327aaf8c")

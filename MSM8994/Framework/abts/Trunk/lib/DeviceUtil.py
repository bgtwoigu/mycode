import StringIO
import re
import time
import logging
import subprocess
import UiAutomator
import AdbOperation
import FastbootOperation
from PIL import Image


BOOT_MODE_NORMAL = ""
BOOT_MODE_FASTBOOT = "bootloader"
BOOT_MODE_SAHARA = "edl"

SWITCH_MODE_ON = "1"
SWITCH_MODE_OFF = "0"


logger = logging.getLogger("DeviceUtil")
logger.setLevel(logging.INFO)


def rootDevice(strDeviceID):
    """
    Root and Remount device

    @type strDeviceID: str
    @param strDeviceID: Str of phone's device ID
    @return: True if device rooted and mounted with no issue
    """

    logger.info("Root device")
    (outputs, errors) = AdbOperation.executeAdbCmd(['root'], strDeviceID)
    if errors:
        logger.error("Adb returns error while root device: %s" % errors)
        return False

    adbWaitForDevice(strDeviceID, False)

    logger.info("Remount device")
    (outputs, errors) = AdbOperation.executeAdbCmd(['remount'], strDeviceID)
    if errors:
        logger.error("Adb returns error while remount device: %s" % errors)
        return False

    adbWaitForDevice(strDeviceID, False)

    return True


def rebootDevice(strDeviceID, strBootMode=BOOT_MODE_NORMAL):
    """
    Reboot device

    @type strDeviceID: str
    @param strDeviceID: Str of phone's device ID
    @return: True if device reboot with no issue
    """

    logger.info("Reboot device")
    if FastbootOperation.isDeviceExists(strDeviceID):
        (outputs, errors) = FastbootOperation.executeFastbootCmd(
            ['reboot', strBootMode], strDeviceID)
    else:
        (outputs, errors) = AdbOperation.executeAdbCmd(
            ['reboot', strBootMode], strDeviceID)

    if errors:
        logger.error(
            "Adb/Fastboot returns error while reboot device: %s" % errors)
        return False

    time.sleep(1)

    if strBootMode == BOOT_MODE_NORMAL:
        return adbWaitForDevice(strDeviceID)

    if strBootMode == BOOT_MODE_FASTBOOT:
        return FastbootOperation.fastbootWaitForDevice(strDeviceID)

    if strBootMode == BOOT_MODE_SAHARA:
        return True

    return True


def adbWaitForDevice(strDeviceID, boolTillBootCompleted=True):
    """
    Wait device up

    @type strDeviceID: str
    @param strDeviceID: Str of phone's device ID
    @return: True if device up with no issue
    """

    try:
        logger.info("Wait adb device...")
        subprocess.check_output(['adb', '-s', strDeviceID, 'wait-for-device'])
    except Exception:
        logger.exception("Excuption Caught")
        return False
    else:
        if boolTillBootCompleted is True:
            logger.info("Wait device boot completed...")
            while True:
                (outputs, errors) = AdbOperation.executeAdbCmd(
                    ['getprop', 'sys.boot_completed'], strDeviceID, True)
                if errors:
                    logger.error(
                        "Shell returns error while getprop: %s" % errors)
                    return False

                if outputs.strip() != "1":
                    time.sleep(5)
                    continue
                
                time.sleep(10)      # delay to start system completely
                return True


def unlockScreen(strDeviceID):
    (outputs, errors) = AdbOperation.executeAdbCmd(
        ['dumpsys', 'power'], strDeviceID, True)
    
    if errors:
        logger.error("Shell returns error while dumpsys: %s" % errors)
        return False
    
    (size, errors) = AdbOperation.executeAdbCmd(
        ['wm', 'size'], strDeviceID, True)                # get Physical size
    
    if errors:
        logger.error("Shell returns error while wm size: %s" % errors)
        return False    

    try:
        listOutputs = outputs.strip().split("\n")
        for strOutput in listOutputs:
            ma = re.search(r'^Display\s+Power:\s+state=(\w+)',
                           strOutput, re.IGNORECASE)
            if ma:
                if ma.group(1) == "OFF":
                    logger.info("Screen is off. Turning on")
                    AdbOperation.executeAdbCmd(
                        ['input', 'keyevent', '26'], strDeviceID, True)      # power

                break

        # AdbOperation.executeAdbCmd(
        #     ['input', 'keyevent', '82'], strDeviceID, True)            # menu (only unlock H2 locking Screen)
        x, y = size.split(" ")[-1].split("x")
        AdbOperation.executeAdbCmd(
            ['input', 'swipe', str(int(x)/2), str(int(y)*4/5), str(int(x)/2), str(int(y)/5), '500'], strDeviceID, True)    #  swipe to unlock screen
        logger.info("Unlock screen successful")

    except Exception, e:
        logger.exception("Excuption Caught: %s" %e)
        return False

    return True

def closeApp(strDeviceID, packageName):
    logger.info("Close App: %s" %packageName)
    (output, error) = AdbOperation.executeAdbCmd(
            ['pm', 'clear', packageName], strDeviceID, True)
    if output != "Success":
        return False
    return True


def setScreenStayOn(strDeviceID):
    (outputs, errors) = AdbOperation.executeAdbCmd(
        ['svc', 'power', 'stayon', 'true'], strDeviceID, True)
    if errors:
        logger.error("Shell returns error while svc: %s" % errors)
        return False

    return True


def configWifi(strDeviceID, ssid="Oneplus-free", psk="oneplus818"):
    # cn: ssid="Oneplus-free" psk="oneplus818"
    # tw: ssid="LAB_CTS" psk="haha54088"
    listCmd = ['echo', '-e', '"network={ \nssid=\\"%s\\"\npsk=\\"%s\\"\nkey_mgmt=WPA-PSK\nsim_num=1\npriority=1\n}\n"' % (
        ssid, psk), '>>', '/data/misc/wifi/wpa_supplicant.conf']

    logger.info("Config wifi")
    (outputs, errors) = AdbOperation.executeAdbCmd(listCmd, strDeviceID, True)

    if errors:
        logger.error(
            "Shell returns error while edit wifi config file: %s" % errors)
        return False

    for switch in ['disable', 'enable']:
        logger.info("%s wifi" % switch)
        (outputs, errors) = AdbOperation.executeAdbCmd(
            ['svc', 'wifi', switch], strDeviceID, True)
        if errors:
            logger.error("Shell returns error while %s wifi: %s" %
                         (switch, errors))
            return False

    return True


def switchAirplaneMode(strDeviceID, strSwitchMode=SWITCH_MODE_ON):
    logger.info("Turn %s airplane mode" %
                ("on" if strSwitchMode == SWITCH_MODE_ON else "off"))

    (outputs, errors) = AdbOperation.executeAdbCmd(
        ["settings", "put", "global", "airplane_mode_on", strSwitchMode], strDeviceID, True)
    if errors:
        logger.error("Shell returns error while put settings: %s" % errors)
        return False

    return True


def unlockBootloader(strDeviceID):
    if FastbootOperation.isDeviceExists(strDeviceID) is False:
        rebootDevice(strDeviceID, BOOT_MODE_FASTBOOT)

    dictBootloaderInfo = getBootloaderInfo(strDeviceID)
    if dictBootloaderInfo.get("Device unlocked", "fasle") == "true":
        logger.info("Device already unlocked")
        return True

    if AdbOperation.isDeviceExists(strDeviceID) is False:
        rebootDevice(strDeviceID, BOOT_MODE_NORMAL)

    logger.info("Enable Rpmb")
    # Todo: Check Secure boot +
    # Todo: Check Secure boot -

    (outputs, errors) = AdbOperation.executeAdbCmd(
        ['dumpsys', 'android.security.keystore', '--enable_rpmb'], strDeviceID, True)
    if errors:
        logger.error("Shell returns error while enable rpmb: %s" % errors)
        return False

    logger.info("OEM Unlock")
    # Todo: Do not use UiAutomator +
    unlockScreen(strDeviceID)
    (outputs, errors) = AdbOperation.executeAdbCmd(
        ["am", "start", "-n" '"com.android.settings/.DevelopmentSettings"'], strDeviceID, True)
    if errors:
        logger.error(
            "Shell returns error while start DevelopmentSettings: %s" % errors)
        return False

    while True:
        controlOemUnlock = UiAutomator.getControl(UiAutomator.dumpView(strDeviceID), {
                                                  'text': re.compile(r'OEM.*')})
        if controlOemUnlock is not None:
            UiAutomator.clickControl(strDeviceID, controlOemUnlock)
            time.sleep(2)
            controlOemConfirm = UiAutomator.getControl(UiAutomator.dumpView(
                strDeviceID), {'class': 'android.widget.Button', 'index': '1'})
            if controlOemConfirm is None:
                continue
            UiAutomator.clickControl(strDeviceID, controlOemConfirm)
            time.sleep(1)
            break
        AdbOperation.executeAdbCmd(
            ["input", "swipe", "500 800 500 100 1000"], strDeviceID, True)
    # Todo: Do not use UiAutomator -

    rebootDevice(strDeviceID, BOOT_MODE_FASTBOOT)
    (outputs, errors) = FastbootOperation.executeFastbootCmd(
        ['oem', 'unlock'], strDeviceID)
    if errors:
        logger.error("Shell returns error while oem unlock: %s" % errors)
        return False

    # Todo: Confirm oem unlock +
    # Todo: Confirm oem unlock -

    return adbWaitForDevice(strDeviceID)


def unlockBootloaderCritical(strDeviceID):
    if FastbootOperation.isDeviceExists(strDeviceID) is False:
        rebootDevice(strDeviceID, BOOT_MODE_FASTBOOT)

    dictBootloaderInfo = getBootloaderInfo(strDeviceID)
    if dictBootloaderInfo.get("Device critical unlocked", "fasle") == "true":
        logger.info("Device critical already unlocked")
        return True

    if dictBootloaderInfo.get("Device unlocked", "fasle") == "false":
        if unlockBootloader(strDeviceID) is False:
            logger.error("OEM unlock failed")
            return False

    if FastbootOperation.isDeviceExists(strDeviceID) is False:
        rebootDevice(strDeviceID, BOOT_MODE_FASTBOOT)

    (outputs, errors) = FastbootOperation.executeFastbootCmd(
        ["flashing", "unlock_critical"], strDeviceID)
    if errors:
        logger.error("Shell returns error while unlock critical: %s" % errors)
        return False

    # Todo: Confirm critical unlock +
    # Todo: Confirm critical unlock -

    return adbWaitForDevice(strDeviceID)


def getBootloaderInfo(strDeviceID):
    dictBootloaderInfo = {}
    if FastbootOperation.isDeviceExists(strDeviceID) is False:
        rebootDevice(strDeviceID, BOOT_MODE_FASTBOOT)

    (outputs, errors) = FastbootOperation.executeFastbootCmd(
        ["oem", "device-info"], strDeviceID)
    if errors:
        logger.error("Shell returns error while oem unlock: %s" % errors)
        return dictBootloaderInfo

    for strBootloaderInfo in outputs.split("\n"):
        try:
            ma = re.match(r'^\(bootloader\)\s+(.*): (.*)',
                          strBootloaderInfo.strip())
            if ma:
                dictBootloaderInfo[ma.group(1)] = ma.group(2)
        except Exception:
            logger.exception("Exception Caught")

    return dictBootloaderInfo


def configProvision(strDeviceID):
    logger.info("Disable provision")
    (outputs, errors) = AdbOperation.executeAdbCmd(
        ['pm', 'list', 'package'], strDeviceID, True)
    if errors:
        logger.error("Shell returns error while get package list: %s" % errors)
        return False

    strProvisionPackageName = None
    for strPackageName in outputs.split("\n"):
        ma = re.match(
            r'^package:(com\.oneplus\.provision|com\.google\.android\.setupwizard)', strPackageName.strip())
        if ma:
            strProvisionPackageName = ma.group(1)
            break

    if strProvisionPackageName is None:
        logger.info("Failed to find provision package")
        return False

    (outputs, errors) = AdbOperation.executeAdbCmd(
        ['pm', 'disable', strProvisionPackageName], strDeviceID, True)
    if errors:
        logger.error(
            "Shell returns error while disable provision: %s" % errors)
        return False

    dictProvisionSetting = {"device_provisioned": "global", "user_setup_complete": "secure",
                            "device_provisioned_oneplus": "global", "user_setup_complete_oneplus": "secure"}
    for key in dictProvisionSetting:
        (outputs, errors) = AdbOperation.executeAdbCmd(
            ['settings', 'put', dictProvisionSetting[key], key, '1'], strDeviceID, True)
        if errors:
            logger.error(
                "Shell returns error while disable provision: %s" % errors)
            return False

    return True


def closeIntroIfExists(strDeviceID):
    logger.info("Close intro")
    # Todo: Do not use UiAutomator +
    rePatternBtn = re.compile(
        r'.*(intro_done_button|close_edit_guideline|camera_wizard_simple_modes_switch_ok|cling_dismiss_longpress_info|cling_dismiss_quick_page_welcome_info|cling_dismiss_quick_page_resize_info)')
    btnlIntroClose = UiAutomator.getControl(UiAutomator.dumpView(strDeviceID), {
                                            'resource-id': rePatternBtn})
    if btnlIntroClose is not None:
        return UiAutomator.clickControl(strDeviceID, btnlIntroClose)

    return True
    # Todo: Do not use UiAutomator -


def approvePermissionRequestIfExists(strDeviceID):
    logger.info("Approve permission request")
    # Todo: Do not use UiAutomator +
    while UiAutomator.getControl(UiAutomator.dumpView(strDeviceID), {'resource-id': re.compile(r'.*(permission_message)')}) is not None:
        # print titlePermissionRequest.attrib
        btnApprovePermissionRequest = UiAutomator.getControl(UiAutomator.dumpView(
            strDeviceID), {'resource-id': re.compile(r'.*(permission_allow_button)')})
        if btnApprovePermissionRequest is not None:
            UiAutomator.clickControl(strDeviceID, btnApprovePermissionRequest)
            continue
        return False

    return True
    # Todo: Do not use UiAutomator -


def removeAsserttip(strDeviceID):
    rootDevice(strDeviceID)
    (outputs, errors) = AdbOperation.executeAdbCmd(
        ['rm', '/system/bin/asserttip'], strDeviceID, True)
    if errors:
        logger.error("Shell returns error while remove file: %s" % errors)
        return False

    return True


def takeSnapshot(strDeviceID, rotationDegree):
    """
    Capture screenshot

    @type strDeviceID: str
    @param strDeviceID: Str of phone's device ID
    @type rotationDegree: int
    @param rotationDegree: Degree of the picture to rotate
    @return: img
    """

    logger.info("Take screen snapshot")
    (outputs, errors) = AdbOperation.executeAdbCmd(
        ['screencap', '-p'], strDeviceID, True)
    if errors:
        logger.error("Shell returns error while screencap: %s" % errors)
        return None

    try:
        # http://blog.shvetsov.com/2013/02/grab-android-screenshot-to-computer-via.html
        outputs = outputs.replace('\x0D\x0D\x0A', '\x0A')

        tmpBuff = StringIO.StringIO()
        tmpBuff.write(outputs)
        tmpBuff.seek(0)

        img = Image.open(tmpBuff)
        img = img.rotate(rotationDegree)
    except Exception:
        logger.exception("Excuption Caught")
        return None
    else:
        return img


if __name__ == '__main__':
    import sys
    hdlr = logging.StreamHandler(sys.stdout)
    hdlr.setFormatter(logging.Formatter(
        '%(asctime)s - %(name)s [%(levelname)s]: %(message)s'))
    logger.addHandler(hdlr)
    #switchAirplaneMode("327aaf8c", SWITCH_MODE_ON)
    unlockScreen('73ffec58')

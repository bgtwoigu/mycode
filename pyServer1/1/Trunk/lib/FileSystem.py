import os
import subprocess
import platform
import tarfile
import hashlib
import logging

logger = logging.getLogger("FileSystem")
logger.setLevel(logging.INFO)


def deleteDir(strDir):
    # Only works on Linux or Cygwin
    try:
        objProcess = subprocess.Popen(
            ['rm', '-f', '-r', strDir], stdout=subprocess.PIPE, stderr=subprocess.PIPE)
        output, errors = objProcess.communicate()

        if objProcess.returncode or errors:
            logger.error(errors)
            return False
        return True
    except Exception:
        logger.exception("Exception Caught")
        return False


def copyDir(strSourceDir, strTargetDir):
    # Only works on Linux or Cygwin
    try:
        objProcess = subprocess.Popen(
            ['cp', '-R', strSourceDir, strTargetDir], stdout=subprocess.PIPE, stderr=subprocess.PIPE)
        output, errors = objProcess.communicate()

        if objProcess.returncode or errors:
            logger.error(errors)
            return False
        return True
    except Exception:
        logger.exception("Exception Caught")
        return False


def createTarArchiveFromDirectory(strSrcDir, strTarName, strCmdArgs=""):
    if os.path.isdir(strSrcDir) is False:
        logger.error("Supplied Path %s is not a directory" % strSrcDir)
        return False

    try:
        logger.info("Create Tar Archive From Directory: %s" % strSrcDir)

        with tarfile.open(strTarName, "w:%s" % strCmdArgs) as tar:
            for fileName in os.listdir(strSrcDir):
                tar.add(os.path.join(strSrcDir, fileName), arcname=fileName)
    except Exception:
        logger.exception("Exception Caught")
        return False
    else:
        return True


def extractTarArchiveToDirectory(strTarName, strTargetDir):
    if os.path.isfile(strTarName) is False:
        logger.error("Supplied Tar Archive %s not Exists" % strTarName)
        return False

    if os.path.isdir(strTargetDir) is False:
        os.makedirs(strTargetDir)

    try:
        logger.info("Extract Tar Archive To Directory: %s" % strTargetDir)

        with tarfile.open(strTarName, "r") as tar:
            tar.extractall(path=strTargetDir, members=None)
    except Exception:
        logger.exception("Exception Caught")
        return False
    else:
        return True


def getFileMd5sum(listFilePath, strLogFilePath=None):
    def _hashfile(afile, hasher, blocksize=65536):
        buf = afile.read(blocksize)
        while len(buf) > 0:
            hasher.update(buf)
            buf = afile.read(blocksize)
        return hasher.hexdigest()

    dictMd5sum = {}
    for strfileName in listFilePath:
        if os.path.isfile(strfileName) is False:
            logger.warning("Supplied File %s not Exists" % strfileName)
            dictMd5sum[strfileName] = "Invalid File"
        else:
            logger.info("Get File %s MD5" % strfileName)

            try:
                # strMd5 = _hashfile(afile=open(strfileName, 'rb'), hasher=hashlib.sha256())
                strMd5 = _hashfile(afile=open(
                    strfileName, 'rb'), hasher=hashlib.md5())
            except Exception:
                logger.exception("Exception Caught")
                dictMd5sum[strfileName] = "Invalid File"
            else:
                dictMd5sum[strfileName] = strMd5

    if strLogFilePath is not None:
        try:
            fileLog = open(strLogFilePath, 'a')
            for key in dictMd5sum:
                fileLog.write("%s  %s\n" %
                              (dictMd5sum[key], os.path.basename(key)))
        except Exception:
            logger.exception("Exception Caught")
        finally:
            fileLog.close()

    return dictMd5sum


def mountNetworkShareWithAuth(strSharePath, strUsername=None, strPassword=None):
    current_os = platform.system()
    strMountPath = None

    if current_os == 'Windows':
        # import win_unc
        try:
            # Mount another directory with authorization to any available local
            # drive.
            strSharePath = strSharePath.replace("/", "\\")
            listCmd = ["NET", "USE", '\"Z:\"', '\"%s\"' % strSharePath,
                       '\"%s\"' % strPassword, '/USER:\"%s\"' % strUsername]
            # creds = win_unc.UncCredentials(strUsername, strPassword)
            # authz_unc = win_unc.UncDirectory(strSharePath, creds)
            # mnt = win_unc.UncDirectoryMount(authz_unc)
            # mnt.mount()

            objProcess = subprocess.Popen(
                " ".join(listCmd), stdout=subprocess.PIPE, stderr=subprocess.PIPE)
            outputs, errors = objProcess.communicate()
            logger.debug("Cmd Output: %s" % outputs)

            if objProcess.returncode or errors:
                logger.error("Error during mount: %s" % errors)
        except Exception:
            logger.exception("Exception Caught")
        else:
            # strMountPath = mnt.disk_drive
            strMountPath = "Z:"

    if current_os in ['Linux', 'Darwin'] or current_os.startswith('CYGWIN'):
        strSharePath = strSharePath.replace("\\", "/")
        strMountPath = "/mnt/tmp"
        listCmd = ["mount -t smbfs %s %s" % (strSharePath, strMountPath)]

        if strPassword:
            listCmd.append("-o password=%s,username=%s" %
                           (strPassword, strUsername))

        try:
            # os.system(" ".join(listCmd))
            objProcess = subprocess.Popen(
                listCmd, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
            outputs, errors = objProcess.communicate()
            logger.debug("Cmd Output: %s" % outputs)

            if objProcess.returncode or errors:
                logger.error("Error during mount: %s" % errors)
                strMountPath = None
        except Exception:
            logger.exception("Exception Caught")
            strMountPath = None

    return strMountPath


def unmountNetworkShare(strMountPath):
    current_os = platform.system()

    if current_os == 'Windows':
        # import win_unc
        # from win_unc.query import get_connection_for_disk_drive
        try:
            # mnt = get_connection_for_disk_drive(win_unc.DiskDrive(strMountPath))
            # mnt.unmount()
            # os.system('NET USE "%s" /DELETE /YES' % strMountPath)
            objProcess = subprocess.Popen(
                ["NET", "USE", strMountPath, "/DELETE", "/YES"], stdout=subprocess.PIPE, stderr=subprocess.PIPE)
            outputs, errors = objProcess.communicate()
            logger.debug("Cmd Output: %s" % outputs)

            if objProcess.returncode or errors:
                logger.error("Error during unmount: %s" % errors)
                return False
        except Exception:
            logger.exception("Exception Caught")
            return False

    if current_os in ['Linux', 'Darwin'] or current_os.startswith('CYGWIN'):
        try:
            # os.system("umount %s" % strMountPath)
            objProcess = subprocess.Popen(
                ["umount", strMountPath], stdout=subprocess.PIPE, stderr=subprocess.PIPE)
            outputs, errors = objProcess.communicate()
            logger.debug("Cmd Output: %s" % outputs)

            if objProcess.returncode or errors:
                logger.error("Error during unmount: %s" % errors)
                return False
        except Exception:
            logger.exception("Exception Caught")
            return False

    return True


if __name__ == '__main__':
    import sys
    hdlr = logging.StreamHandler(sys.stdout)
    hdlr.setFormatter(logging.Formatter(
        '%(asctime)s - %(name)s [%(levelname)s]: %(message)s'))
    logger.addHandler(hdlr)
    mountNetworkShareWithAuth('//172.21.106.141/sw_release', "share", "share")

import os
import re
import subprocess
import logging
import FileSystem

logger = logging.getLogger("GitOperation")
logger.setLevel(logging.INFO)


def performGetCodeOperation(strWorkingCopyPath, strUsername, strManifestUrl, strManifestName=None, strManifestBranch=None):
    intAttempts = 1
    dictOptions = {
        'manifest-url': 'ssh://%s@%s' % (strUsername, strManifestUrl)}

    if strManifestName is not None:
        dictOptions['manifest-name'] = strManifestName

    if strManifestBranch is not None:
        dictOptions['manifest-branch'] = strManifestBranch

    if os.path.isdir(strWorkingCopyPath) is False:
        os.makedirs(strWorkingCopyPath)

    while intAttempts <= 20:
        (output, errors) = executeRepoCmd(strCmd="init", listOptions=optionHandler(
            dictOptions), strWorkingCopyPath=strWorkingCopyPath)
        if errors is None:
            return performRepoSyncOperation(strWorkingCopyPath)
        else:
            if repoErrorHandler(errors) is False:
                logger.info("Force delete repo config folder")
                removeRepoConfigDirectory(strWorkingCopyPath)

            logger.error("Attempting to recover (try %s out of 20)" %
                         intAttempts)
            intAttempts += 1

    return False


def performRepoSyncOperation(strWorkingCopyPath):
    intAttempts = 1
    # dictOptions = {'quiet': None}

    if os.path.isdir(strWorkingCopyPath) is False:
        logger.warning(
            "Supplied Working Copy Path %s is not a directory" % strWorkingCopyPath)
        return False

    performRepoResetOperation(strWorkingCopyPath)

    while intAttempts <= 20:
        (output, errors) = executeRepoCmd(
            strCmd="sync", strWorkingCopyPath=strWorkingCopyPath)
        if errors is None:
            return True
        else:
            repoErrorHandler(errors)
            logger.error("Attempting to recover (try %s out of 20)" %
                         intAttempts)
            intAttempts += 1

    return False


def performRepoResetOperation(strWorkingCopyPath):
    if os.path.isdir(strWorkingCopyPath) is False:
        logger.warning(
            "Supplied Working Copy Path %s is not a directory" % strWorkingCopyPath)
        return False

    try:
        logger.info("Unversioned files to be removed from %s" %
                    strWorkingCopyPath)
        strCmd = "repo forall -c git reset --hard HEAD"

        objProcess = subprocess.Popen(
            strCmd, stdout=subprocess.PIPE, stderr=subprocess.PIPE, shell=True, cwd=strWorkingCopyPath)
        output, errors = objProcess.communicate()
        logger.debug("Repo Cmd output: %s" % output)

        if objProcess.returncode or errors:
            logger.warning(errors)
            return False
        return True
    except Exception:
        logger.exception("Excuption Caught")
        return False


def removeDirectory(strDirToDelete):
    if os.path.isfile(strDirToDelete):
        strDirToDelete = os.path.dirname(strDirToDelete)

    logger.info("Deleting %s" % strDirToDelete)
    FileSystem.deleteDir(strDirToDelete)


def removeRepoConfigDirectory(strWorkingCopyPath):
    if os.path.isfile(strWorkingCopyPath):
        strWorkingCopyPath = os.path.dirname(strWorkingCopyPath)

    strDirToDelete = os.path.join(strWorkingCopyPath, ".repo/")

    logger.info("Deleting %s" % strDirToDelete)
    FileSystem.deleteDir(strDirToDelete)


def optionHandler(dictOptions):
    listOptions = []
    for key in dictOptions:
        listOptions.append("--%s" % key)
        if dictOptions[key] is not None and dictOptions[key] != "":
            listOptions.append(str(dictOptions[key]))

    return listOptions


def repoErrorHandler(strErrors):
    try:
        listErrors = strErrors.strip().split("\n")
        for error in listErrors:
            logger.info("Repo Error Handle On: %s" % error)

            ma = re.search(r'^repo: Failed to add file \'(.*)\':',
                           error, re.IGNORECASE)
            if ma:
                # repo will not overwrite the file, so we have to manually
                # remove the entire directory since most likely the svn db is
                # corrupted
                logger.info(
                    "Repo failed to add '%s' since it already exists, so delete it" % ma.group(1))
                removeDirectory(ma.group(1))
                return True

        logger.error("Can not handle this error %s" % strErrors)
        return False
    except Exception:
        logger.exception("Exception Caught")
        return False


def executeRepoCmd(strCmd, listOptions=None, listArgument=None, strWorkingCopyPath=None):
    listCmd = ["repo2", strCmd]
    if listOptions is not None and len(listOptions) > 0:
        listCmd.extend(listOptions)

    if listArgument is not None and len(listArgument) > 0:
        listCmd.extend(listArgument)

    try:
        logger.info("Executing Repo Cmd: %s" % ' '.join(listCmd))

        objProcess = subprocess.Popen(
            listCmd, stdout=subprocess.PIPE, stderr=subprocess.PIPE, cwd=strWorkingCopyPath)
        output, errors = objProcess.communicate()
        logger.debug("Repo Cmd Output: %s" % output)

        if objProcess.returncode:
            return (output, errors)
    except Exception, e:
        logger.exception("Exception Caught")
        return (None, e)
    else:
        return (output, None)


def executeGitCmd(strCmd, listOptions=None, listArgument=None):
    listCmd = ["git", strCmd]
    if listOptions is not None and len(listOptions) > 0:
        listCmd.extend(listOptions)

    if listArgument is not None and len(listArgument) > 0:
        listCmd.extend(listArgument)

    try:
        logger.info("Executing Repo Cmd: %s" % ' '.join(listCmd))

        objProcess = subprocess.Popen(
            listCmd, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
        output, errors = objProcess.communicate()
        logger.debug("Repo Cmd Output: %s" % output)

        if objProcess.returncode or errors:
            return (output, errors)
    except Exception, e:
        logger.exception("Exception Caught")
        return (None, e)
    else:
        return (output, None)


if __name__ == '__main__':
    import sys
    hdlr = logging.StreamHandler(sys.stdout)
    hdlr.setFormatter(logging.Formatter(
        '%(asctime)s - %(name)s [%(levelname)s]: %(message)s'))
    logger.addHandler(hdlr)
    performGetCodeOperation("/home/rafe/test/code", "fengyingzhen",
                            "172.17.101.52:29418/QCOM/manifest/msm8996", "qc8996.xml")

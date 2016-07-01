import os
import time
import subprocess
import logging


logger = logging.getLogger("CompileOperation")
logger.setLevel(logging.INFO)


def makeCompile(strWorkingDir, dictOptions, dictArgument):
    if os.path.isdir(strWorkingDir) is False:
        logger.warning(
            "Supplied Working Copy Path %s is not a directory" % strWorkingDir)
        return False

    logger.info("Change Working Dir To: %s" % strWorkingDir)
    os.chdir(strWorkingDir)

    makeClean()

    return executeCompileCmd(listOptions=optionHandler(dictOptions), listArgument=argumentHandler(dictArgument), boolStoreOutput=True)


def makeClean(strProject=None):
    if strProject is not None:
        return executeCompileCmd(strCmd="clean", listArgument=["PROJECT=%s" % strProject])

    return executeCompileCmd(strCmd="clean")


def argumentHandler(dictArgument):
    listArgument = []
    for key in dictArgument:
        listArgument.append("%s=%s" % (key.upper(), str(dictArgument[key])))

    return listArgument


def optionHandler(dictOptions):
    listOptions = []
    for key in dictOptions:
        if len(key) == 1:
            listOptions.append("-%s" % key)
            if dictOptions[key] is not None and dictOptions[key] != "":
                listOptions.append(str(dictOptions[key]))
        else:
            listOptions.append("--%s" % key)
            if dictOptions[key] is not None and dictOptions[key] != "":
                listOptions[-1] = "%s=%s" % (listOptions[-1],
                                             str(dictOptions[key]))

    return listOptions


def executeCompileCmd(strCompileCmd="make", strCmd=None, listOptions=None, listArgument=None, strWorkingDir=None, boolStoreOutput=False):
    """
    - lunch:   lunch <product_name>-<build_variant>
    - tapas:   tapas [<App1> <App2> ...] [arm|x86|mips|armv5|arm64|x86_64|mips64] [eng|userdebug|user]
    - croot:   Changes directory to the top of the tree.
    - m:       Makes from the top of the tree.
    - mm:      Builds all of the modules in the current directory, but not their dependencies.
    - mmm:     Builds all of the modules in the supplied directories, but not their dependencies.
               To limit the modules being built use the syntax: mmm dir/:target1,target2.
    - mma:     Builds all of the modules in the current directory, and their dependencies.
    - mmma:    Builds all of the modules in the supplied directories, and their dependencies.
    """

    listCmd = [strCompileCmd]

    if strCmd is not None and len(strCmd) > 0:
        listCmd.append(strCmd)

    if listOptions is not None and len(listOptions) > 0:
        listCmd.extend(listOptions)

    if listArgument is not None and len(listArgument) > 0:
        listCmd.extend(listArgument)

    if strWorkingDir is not None and os.path.isdir(strWorkingDir):
        logger.info("Change Working Dir To: %s" % strWorkingDir)
        os.chdir(strWorkingDir)

    if boolStoreOutput is True:
        strTimeNow = time.strftime("%Y-%m-%d", time.localtime())
        fileStdout = open("%s_makeoutput.log" % strTimeNow, 'w')
        fileStderr = open("%s_makeerror.log" % strTimeNow, 'w')
    else:
        fileStdout = subprocess.PIPE
        fileStderr = subprocess.PIPE

    try:
        logger.info("Executing Make Cmd: %s" % ' '.join(listCmd))

        objProcess = subprocess.Popen(
            listCmd, stdout=fileStdout, stderr=fileStderr)
        output, errors = objProcess.communicate()

        if boolStoreOutput is True:
            fileStdout.close()
            fileStderr.close()
            if objProcess.returncode:
                logger.error(
                    "Pls check error in '%s_makeerror.log'" % strTimeNow)
                return False
        else:
            logger.debug("Make Cmd Output: %s" % output)
            if objProcess.returncode or errors:
                logger.error(errors)
                return False
    except Exception:
        logger.exception("Exception Caught")
        return False
    else:
        return True

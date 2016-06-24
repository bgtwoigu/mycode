import re
import logging
import AdbOperation
import xml.etree.cElementTree as cET


logger = logging.getLogger("UiAutomator")
logger.setLevel(logging.INFO)


def dumpView(strDeviceID):
    logger.info("Dump UI view")
    (outputs, errors) = AdbOperation.executeAdbCmd(
        ['uiautomator', 'dump'], strDeviceID, True)
    if errors:
        logger.error("Shell returns error while dump UI: %s" % errors)
        return None

    try:
        ma = re.search(r'^UI hierchary dumped to: (.*\.xml)',
                       outputs.strip(), re.IGNORECASE)
        if ma:
            (outputs, errors) = AdbOperation.executeAdbCmd(
                ['cat', ma.group(1)], strDeviceID, True)
            if errors:
                logger.error(
                    "Shell returns error while cat dump file: %s" % errors)
                return None
            return outputs

        logger.error("UI hierchary dump failed")
        return None
    except Exception:
        logger.exception("Excuption Caught")
        return None


def getControl(strUiView, dictAttrib):
    try:
        rootUiView = cET.fromstring(strUiView)
        intControlLevel = dictAttrib.pop("level", 0)
        listElementRoot = [rootUiView]
        listElementNodeToSearch = []

        for x in xrange(intControlLevel):
            listSubElementNode = []
            for elementRoot in listElementRoot:
                for subElementNode in elementRoot.findall("node"):
                    listSubElementNode.append(subElementNode)

            listElementRoot = listSubElementNode[:]

        if intControlLevel == 0:
            for elementNode in rootUiView.iter("node"):
                listElementNodeToSearch.append(elementNode)
        else:
            listElementNodeToSearch = listElementRoot

        for subElementNode in listElementNodeToSearch:
            boolElementFound = True
            for key in dictAttrib:
                patternAttribValue = dictAttrib[key]
                if isinstance(patternAttribValue, re._pattern_type) is False:
                    patternAttribValue = re.compile(
                        r'^%s$' % patternAttribValue)

                if not patternAttribValue.match(subElementNode.attrib.get(key, None)):
                    boolElementFound = False
                    break

            if boolElementFound is True:
                return subElementNode

        return None
    except Exception:
        logger.exception("Exception Caught")
        return None


def getControlAttrib(elementNode, strKey):
    try:
        return _formatAttribValue(elementNode.attrib.get(strKey, None))
    except Exception:
        logger.exception("Exception Caught")
        return None


def clickControl(strDeviceID, elementNode):
    (x, y) = (None, None)
    strBounds = getControlAttrib(elementNode, "bounds")
    if strBounds is None:
        logger.error("Failed to get control bounds")
        return False

    try:
        ma = re.match(r'^\[(\d+),(\d+)\]\[(\d+),(\d+)\]', strBounds)
        if ma:
            x = (int(ma.group(1)) + int(ma.group(3))) / 2
            y = (int(ma.group(2)) + int(ma.group(4))) / 2
    except Exception:
        logger.exception("Exception Caught")
        return False

    if x is None or y is None:
        logger.error("Failed to get x, y from bounds '%s'" % strBounds)
        return False

    (outputs, errors) = AdbOperation.executeAdbCmd(
        ['input', 'tap', str(x), str(y)], strDeviceID, True)
    if errors:
        logger.error("Shell returns error while input tap: %s" % errors)
        return False

    return True


def _formatAttribValue(strValue):
    try:
        if re.match(r'\d+', strValue):
            return int(strValue)

        if re.match(r'true|false', strValue):
            return True if strValue == "true" else False

        if re.match(r'\d+\.\d+', strValue):
            return float(strValue)

        if strValue == "":
            return None

        return strValue
    except Exception:
        logger.exception("Exception Caught")
        return None


if __name__ == '__main__':
    import sys
    hdlr = logging.StreamHandler(sys.stdout)
    hdlr.setFormatter(logging.Formatter(
        '%(asctime)s - %(name)s [%(levelname)s]: %(message)s'))
    logger.addHandler(hdlr)
    aa = getControl(dumpView("327aaf8c"), {
                    'text': re.compile(r'USB*', re.IGNORECASE)})
    print aa.attrib
    clickControl("327aaf8c", aa)

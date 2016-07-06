def transferUnicodeToStr(objInput):
    if isinstance(objInput, unicode):
        return objInput.encode('utf-8')
    if isinstance(objInput, list):
        listNew = []
        while len(objInput) > 0:
            v = objInput.pop()
            listNew.append(transferUnicodeToStr(v))

        return listNew[:]
    if isinstance(objInput, dict):
        dictNew = {}
        while len(objInput) > 0:
            (k, v) = objInput.popitem()
            dictNew[transferUnicodeToStr(k)] = transferUnicodeToStr(v)

        return dictNew.copy()
    if isinstance(objInput, tuple):
        tupleNew = ()
        for v in objInput:
            tupleNew += (transferUnicodeToStr(v),)

        return tupleNew

    return objInput


def returnUtfStr(objValue):
    if isinstance(objValue, str):
        return objValue.encode('utf-8')
    if isinstance(objValue, int):
        return str(objValue).encode('utf-8')
    if isinstance(objValue, float):
        return str(objValue).encode('utf-8')
    if isinstance(objValue, complex):
        return str(objValue).encode('utf-8')

    try:
        return objValue.encode('utf-8')
    except TypeError:
        try:
            return str(objValue).encode('utf-8')
        except AttributeError:
            return objValue
    except AttributeError:
        return objValue

    return objValue

#!/usr/bin/env python
# -*- coding: utf-8 -*-
from SimpleXMLRPCServer import SimpleXMLRPCServer, SimpleXMLRPCRequestHandler
from SocketServer import ThreadingMixIn
import os
import logging
import time
import sys


"""
log_path
"""
logpath = os.path.join(os.getcwd(), 'log')
if not os.path.exists(logpath):
    os.makedirs(logpath)


"""
set log information
"""
logfile = os.path.join(logpath, time.strftime('%Y%m%d%H%M%S') + '.log')
logging.basicConfig()
log = logging.getLogger("RemoteClient")
log.setLevel(logging.INFO)
fs = logging.FileHandler(logfile)
#cs = logging.StreamHandler()
formatter = logging.Formatter('%(asctime)s - %(name)s - %(levelname)s - %(message)s')
fs.setFormatter(formatter)
#cs.setFormatter(formatter)
log.addHandler(fs)
#log.addHandler(cs)


"""
libriary path
"""
libPath = os.path.join(os.getcwd(), 'lib')
libs = os.listdir(libPath)
sys.path.append(libPath)

scriptPath = os.path.join(os.getcwd(), 'testscript')
scripts = os.listdir(scriptPath)
sys.path.append(scriptPath)

libs.extend(scripts)

"""
load libs automatically
"""
log.info("load libraries")
dictlibs = {}
if len(libs) > 0:
    for mlib in libs:
        if not mlib.startswith("_") and (mlib.endswith(".py") or mlib.endswith(".pyc")):
            try:
                lib_name = mlib.split(".")[0]
                # import_module = 'import ' + lib_name
                # exec import_module
                module = __import__(lib_name)
                dictlibs[lib_name] = module
            except Exception, e:
                log.warning(e)

class RemoteServer(ThreadingMixIn, SimpleXMLRPCServer):
    """
    excute abts-server request
    """
    def __init__(self, host='127.0.0.1', port=8270):
        SimpleXMLRPCServer.__init__(self, (host, int(port)), requestHandler=SimpleXMLRPCRequestHandler, logRequests=True, allow_none=True, encoding='utf-8')
        self.register_function(self.run_method)
        self.register_function(self.run_method_expression)
    
    def _get_method(self, strmodule, function):
        """
        get method from lib by name
        """
        if strmodule in dictlibs:
            module = dictlibs[strmodule]
            return getattr(module, function, None)
        else:
            return None
        
    def run_method(self, module, function, *arg):
        """
        run method with args like (module, method. *arg)
        """
        log.info("run '%s.%s(%s)'" %(module, function, arg)) 
        try:
            method = self._get_method(module, function)
            if method != None:
                if arg:
                    return True, method(*arg)
                else:
                    return True, method()
            else:
                return False, '%s do not contain %s method' %(module, function)
        except Exception, e:
            log.warning(e)
            return False, e
        
    def run_method_expression(self, method, *arg):
        """
        run method with argslike (module.method, *arg)
        """
        module, fuction = self._parse_method(method)
        return self.run_method(module, fuction, *arg)
        
    def _parse_method(self, method):
        """
        module.function(*arg) ---> module, function, *arg
        """
        try:
            module, function = method.split('.')
        except Exception, e:
            log.warning(e)
        return module, function

if __name__ == '__main__':
    # s = SimpleXMLRPCServer(('172.21.8.144', 8080))
    # s.register_function([add, newFile])
    # s.serve_forever()
    #print s.run_method('testlib', 'add', 1, 2)
    try:
        s = RemoteServer('172.21.8.194', 8080)
        print s.run_method('AdbOperation', 'getFistAdbDevice')
        s.serve_forever()
    except Exception, e:
        raise(e)
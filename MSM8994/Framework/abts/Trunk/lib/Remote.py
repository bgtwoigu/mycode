#!/usr/bin/env python
# -*- coding: utf-8 -*-

import xmlrpclib
import socket
from xml.parsers.expat import ExpatError
import httplib

class XmlRpcRemoteClient(object):
    """
    control remote machine by connecting RemoteRpcServer
    """

    def __init__(self, uri, timeout=None):
        transport = TimeoutTransport(timeout=timeout)
        self._server = xmlrpclib.ServerProxy(uri, encoding='UTF-8',
                                             transport=transport)        

    def run_method(self, module, method, *args):
        #run_keyword_args = [module, method, args, kwargs] if kwargs else [module, method, args]
        try:
            return self._server.run_method(module, method, *args)
        except xmlrpclib.Fault as err:
            message = err.faultString
        except socket.error as err:
            message = 'Connection to remote server broken: %s' % err
        except ExpatError as err:
            message = ('Processing XML-RPC return value failed. '
                       'Most often this happens when the return value '
                       'contains characters that are not valid in XML. '
                       'Original error was: ExpatError: %s' % err)
        raise RuntimeError(message)

# Custom XML-RPC timeouts based on
# http://stackoverflow.com/questions/2425799/timeout-for-xmlrpclib-client-requests


class TimeoutTransport(xmlrpclib.Transport):

    def __init__(self, use_datetime=0, timeout=None):
        xmlrpclib.Transport.__init__(self, use_datetime)
        if not timeout:
            timeout = socket._GLOBAL_DEFAULT_TIMEOUT
        self.timeout = timeout

    def make_connection(self, host):
        if self._connection and host == self._connection[0]:
            return self._connection[1]
        chost, self._extra_headers, x509 = self.get_host_info(host)
        self._connection = host, httplib.HTTPConnection(chost, timeout=self.timeout)
        return self._connection[1]
    
if __name__ == '__main__':
    s = XmlRpcRemoteClient("http://127.0.0.1:8080", 10)
    #s = xmlrpclib.ServerProxy("http://127.0.0.1:8080")
    a = 0
    for i in range(5):
        print i
        print s.run_method("testlib", "add", a, i)
        sleep(5)
    sleep(15)
    print s.run_method('testlib', 'log', 'Done')
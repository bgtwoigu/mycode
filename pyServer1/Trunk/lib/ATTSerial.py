# -*- coding: utf-8 -*-
import serial
from serial import *
from serial import Win32Serial
import serial.tools.list_ports_linux
import serial.tools.list_ports_linux
import serial.tools.list_ports

import time
import inspect
import re
import ctypes
#import attlog as log
import logging as log

#from robot.version import get_version
#from robot import utils
#from initapp import IS_LOCAL
#from robot.libraries.BuiltIn import BuiltIn
import os


import threading
from threading import Condition
import string

class ATTSerial():

    ROBOT_LIBRARY_SCOPE = 'TEST_SUITE'
    #ROBOT_LIBRARY_VERSION = get_version()

    def __init__(self,alias=""):
        self.baudrate = 9600                      # 打开串口连接的波特率
        self.openCommand="A00101A2"               # 打开串口的命令
        self.closeCommand="A00100A1"               # 打开串口的命令

baudrate = 9600                      # 打开串口连接的波特率
openCommand="A00101A2"               # 打开串口的命令
closeCommand="A00100A1"               # 打开串口的命令

def getUsbSerialList():
    """
    功能描述: 获取所有的usb串口

    参数：""

    返回值：""

    """

    serial_com_list=[]
    baudrate = 9600

    port_list=list(serial.tools.list_ports.comports())

    if len(port_list) <= 0:
        print "The Serial port can't find!"
    else:
        print "The Serial port  find!"
        for port, desc, hwid in serial.tools.list_ports.comports():
           # print str(port)+" "+str(desc)+" "+str(hwid)
            if "QinHeng Electronics HL-340 USB" in str(desc):
                serial_com_list.append(port)
            if "USB-SERIAL CH340" in str(desc):
                serial_com_list.append(port)
    return serial_com_list
    
def openUsbSerialCom(com=1):
    """
    功能描述: 打开com口，可进行usb通讯

    参数：打开第几个串口,如果com=1，就打开多个串口中的第一个串口

    返回值：""

    """
    serial_com_list=getUsbSerialList()

    if len(serial_com_list)>0:
        if com <= len(serial_com_list):
            print "usb Port:"+str(serial_com_list[com-1])
            ser=serial.Serial(str(serial_com_list[com-1]),baudrate)

            if ser.isOpen() is True:
                ser.write(openCommand.decode("hex"))
                print "open com success"
            else:
                print "open com fail"

def closeUsbSerialCom(com=1):
    """
    功能描述: 关闭usb串口

    参数：关闭第几个usb串口

    返回值：""

    """
    serial_com_list=getUsbSerialList()

    if len(serial_com_list)>0:
        if com <= len(serial_com_list):
            print "usb Port:"+str(serial_com_list[com-1])
            ser=serial.Serial(str(serial_com_list[com-1]),baudrate)

            if ser.isOpen() is True:
                ser.write(closeCommand.decode("hex"))
                print "close com success"
            else:
                print "close com fail"

def closeAllUsbSerialCom():
    """
    功能描述: 关闭所有的usb串口

    参数：""

    返回值：""

    """
    serial_com_list=getUsbSerialList()

    for i in range(len(serial_com_list)):

        print "usb Port:"+str(serial_com_list[com-1])
        ser=serial.Serial(str(serial_com_list[com-1]),baudrate)       
        ser.write(closeCommand.decode("hex"))


if __name__ == '__main__':
    #closeUsbSerialCom(1)
    openUsbSerialCom(1)
    print "test end"

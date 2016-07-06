import time
import socket
import json


def _sendCMD(strCMD):
    """
    Internal method to send/receive socket CMD
    """

    proto = socket.getprotobyname("tcp")
    excuteStatus = None
    t = 1

    while True:
        sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM, proto)
        sock.settimeout(60)
        try:
            sock.connect(("172.21.10.126", 16600))
            sock.send(strCMD + '\n')
            excuteStatus = sock.recv(1024, 0)
            break
        except socket.error, e:
            if t > 3:
                print e
                break
            else:
                t = t + 1
                time.sleep(1)
        finally:
            sock.close()
    print excuteStatus

# dictInfo = {"Purpose": "ABTS Demo", "TestScriptList": ["TestDemo.py"], "ProjectName": "15801"}
# dictBuild = {"BuildPath": "//172.21.106.141/sw_release/MSM8996/15801/DailyBuild/DEV_ROM/HYDROGEN_SIGNED/MSM_15801_160607_0300_user", "ForceFlash": True}
# dictDevice = {"DeviceID": "73ffec58"}
# obj = {"TestRequest": {"info": dictInfo, "build": dictBuild, "device": dictDevice}}

dictInfo = {"Purpose": "AppStartMonitor",
            "TestScriptList": ["AppStartMonitor.py"],
                "ProjectName": "15801",
                "TestParameterList":{"AppStartMonitor.py":{"AppList":{"Phone":"com.android.dialer", "Messenger":"com.google.android.apps.messaging"}}},
                    'MailList':['zhangbin@oneplus.cn']}
dictBuild = {"BuildPath": "//172.21.106.141/sw_release/MSM8996/15801/DailyBuild/DEV_ROM/OXYGEN_SIGNED/MSM_15801_160630_0202_user", "ForceFlash": False}
dictDevice = {"DeviceID": "27a9c7b4"}
obj = {"TestRequest": {"info": dictInfo, "build": dictBuild, "device": dictDevice}}
#obj = {"UpdateTestDeviceInfo": {"DeviceID": '73ffec58', "Status": 'up', "Owner": 'libin859@oneplus.cn'}}
print _sendCMD(json.dumps(obj))

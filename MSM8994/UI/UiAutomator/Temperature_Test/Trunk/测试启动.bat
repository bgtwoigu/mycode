@echo off 
:start 
echo ��������Ҫ���Ե�ģ�飬����9���˳�����... 
echo [0]��������
echo [1]ͨ��
echo [2]΢��
echo [3]QQ
echo [4]��Ƶ
echo [5]����
echo [6]���
echo [7]�Ķ�
echo [8]��Ϸ
echo [9]�˳�
set /p n=�������ѡ��: 
if "%n%"=="" cls&goto :start
if "%n%"=="1" call :1 
if "%n%"=="2" call :2 
if "%n%"=="3" call :3
if "%n%"=="4" call :4 
if "%n%"=="5" call :5 
if "%n%"=="6" call :6 
if "%n%"=="7" call :7 
if "%n%"=="8" call :8 
if "%n%"=="9" call :9 
if "%n%"=="0" call :0 
if /i "%n%"=="n" exit 
pause 
goto :eof 

:0 
echo ��ǰ������������
adb shell uiautomator runtest UiTest.jar -c com.PowerRunner -e choice 100 --nohup
goto :eof 

:1 
echo ��ǰ����ģ��ĵ����--ͨ��
adb shell uiautomator runtest UiTest.jar -c com.PowerRunner -e choice 56 --nohup
goto :eof 

:2 
echo ��ǰ����ģ��ĵ����--΢��
adb shell uiautomator runtest UiTest.jar -c com.PowerRunner -e choice 57 --nohup
goto :eof 

:3 
echo ��ǰ����ģ��ĵ����--QQ
adb shell uiautomator runtest UiTest.jar -c com.PowerRunner -e choice 58 --nohup
goto :eof 

:4 
echo ��ǰ����ģ��ĵ����--��Ƶ
adb shell uiautomator runtest UiTest.jar -c com.PowerRunner -e choice 59 --nohup
goto :eof 

:5 
echo ��ǰ����ģ��ĵ����--������̨����
adb shell uiautomator runtest UiTest.jar -c com.PowerRunner -e choice 60 --nohup
goto :eof 

:6 
echo ��ǰ����ģ��ĵ����--���
adb shell uiautomator runtest UiTest.jar -c com.PowerRunner -e choice 61 --nohup
goto :eof 


:7 
echo ��ǰ����ģ��ĵ����--�Ķ���̨����
adb shell uiautomator runtest UiTest.jar -c com.PowerRunner -e choice 62 --nohup
goto :eof 


:8 
echo ��ǰ����ģ��ĵ����--��Ϸ�����ܿ�
adb shell uiautomator runtest UiTest.jar -c com.PowerRunner -e choice 63 --nohup
goto :eof 


:9 
echo �˳�
exit
goto :eof 
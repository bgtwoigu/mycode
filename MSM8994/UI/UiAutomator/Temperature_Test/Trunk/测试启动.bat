@echo off 
:start 
echo 请输入你要测试的模块，输入9则退出程序... 
echo [0]续航测试
echo [1]通话
echo [2]微信
echo [3]QQ
echo [4]视频
echo [5]上网
echo [6]相机
echo [7]阅读
echo [8]游戏
echo [9]退出
set /p n=输入测试选项: 
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
echo 当前进行续航测试
adb shell uiautomator runtest UiTest.jar -c com.PowerRunner -e choice 100 --nohup
goto :eof 

:1 
echo 当前进行模块耗电测试--通话
adb shell uiautomator runtest UiTest.jar -c com.PowerRunner -e choice 56 --nohup
goto :eof 

:2 
echo 当前进行模块耗电测试--微信
adb shell uiautomator runtest UiTest.jar -c com.PowerRunner -e choice 57 --nohup
goto :eof 

:3 
echo 当前进行模块耗电测试--QQ
adb shell uiautomator runtest UiTest.jar -c com.PowerRunner -e choice 58 --nohup
goto :eof 

:4 
echo 当前进行模块耗电测试--视频
adb shell uiautomator runtest UiTest.jar -c com.PowerRunner -e choice 59 --nohup
goto :eof 

:5 
echo 当前进行模块耗电测试--上网后台音乐
adb shell uiautomator runtest UiTest.jar -c com.PowerRunner -e choice 60 --nohup
goto :eof 

:6 
echo 当前进行模块耗电测试--相机
adb shell uiautomator runtest UiTest.jar -c com.PowerRunner -e choice 61 --nohup
goto :eof 


:7 
echo 当前进行模块耗电测试--阅读后台音乐
adb shell uiautomator runtest UiTest.jar -c com.PowerRunner -e choice 62 --nohup
goto :eof 


:8 
echo 当前进行模块耗电测试--游戏地铁跑酷
adb shell uiautomator runtest UiTest.jar -c com.PowerRunner -e choice 63 --nohup
goto :eof 


:9 
echo 退出
exit
goto :eof 
# coding=utf-8
# coding=gbk

import subprocess 
import threading
import os
import xlwt
import time
import xlrd
import matplotlib.pyplot as plt
import numpy as np
from matplotlib.ticker import  MultipleLocator



dicTotal={}#Total dic
dicPid={}#PID dic
resultLabel=[]  #x轴横坐标Lable信息
resultLabelPostion=[] #x轴横坐标位置信息
resultCols=[]
excel=xlwt.Workbook()
w_sheet=excel.add_sheet("AUTO-meminfo", cell_overwrite_ok=True)
w_sheet.write(0,0,"pid")
w_sheet.write(0,1,"total")
path="//172.21.8.112/abtsTempResult/1468549884104/AppStartMonitorDemo/hehe.xls"

#Total
def dumpmeminfo(com):
    meminfo = subprocess.Popen("adb shell dumpsys meminfo %s  | grep TOTAL | head -n 1" % com ,stdout=subprocess.PIPE, stderr=subprocess.STDOUT)  #
    tr=meminfo.stdout.readline()
    if tr:
        total=tr.split()[1]
        total=float(total)
        dicTotal["total"]=total
#PID
def getPid(com):
    ps = subprocess.Popen("adb shell ps | grep %s" % com, stdout=subprocess.PIPE, stderr=subprocess.STDOUT)  #
    tr=ps.stdout.readline()
    if tr:
        pid=tr.split()[1]
        dicPid["pid"]=pid
def readExcel(path):
    excel = xlrd.open_workbook(path) 
    sheet = excel.sheets()[0]
    excelColumn=sheet.ncols  #返回excel的列    
    excelRows = sheet.nrows #返回excel行数
    
    print "row:"+str(excelRows)+ " col:"+str(excelColumn)

    for i in range(excelColumn):
        values=[]
        for j in range(1,excelRows):
            if str(sheet.row_values(j)[i])!="":
                values.append(str(sheet.row_values(j)[i]))
            else:
                values.append("1")   
        resultCols.append(values)    
    print resultCols
# xlim、xticks和xticklabels,,,图表的范围、刻度位置、刻度标签等。
def draw_line(labels, values):
    length = len(labels)
    #print length
    xlabels = np.linspace(0, length, length)      
    fig=plt.figure(1,figsize=(12,7),frameon = False)
    ax = fig.add_subplot(1, 1, 1)  
    if length < 50:
        j = 2
    elif length < 100:
        j = 10
    elif length < 150:
        j = 15
    elif length < 200:
        j = 20
    elif length < 250:
        j = 30
    else:
        j=50
    xmajorLocator = MultipleLocator(j);
    ax.xaxis.set_major_locator(xmajorLocator)    #主轴的间距    
    
    #设置x轴信息
    ax.set_xticklabels(resultLabel,rotation=90)
    ax.set_xticks(resultLabelPostion)
      
    #plt.plot(xlabels, values, linestyle='-', label='software version')#折线label
    plt.plot(xlabels, values, linestyle='-' )#折线label
    
    plt.legend(loc='upper left',prop={'size':9}) #Lable位置
    ax.set_title('meminfo test') #标题  
    plt.xlabel("Test Package")#横标题 
    
    font = {'family' : 'serif','weight' : 'normal', 'size'   : 16} #指定图形的字体  
    plt.ylabel("mem - size",fontdict=font)#纵标题 
    plt.grid(True)
    plt.show()   

def getLablePosition(labelList):
    i=0
    lth=len(labelList)
    labelCount=[]
    for ch in labelList:
        #print ch
        #print labelList.count(ch)
        if ch not in resultLabel:
            resultLabel.append(ch)
            labelCount.append(labelList.count(ch))
    
    for i in range(len(labelCount)):
        if(i==0):
            resultLabelPostion.append(0.0)
        else:
            resultLabelPostion.append(resultLabelPostion[i-1]+labelCount[i-1])


if __name__=="__main__":
#     com="com.google.android.videos"
    strinput=raw_input("Please input test packagename:")
    for i in range(1,10):
        getPid(strinput) 
        dumpmeminfo(strinput)
        w_sheet.write(i,0,dicPid["pid"])
        w_sheet.write(i,1,dicTotal["total"])
        time.sleep(1)
    excel.save("//172.21.8.112/abtsTempResult/1468549884104/AppStartMonitorDemo/hehe.xls")
    readExcel(path)
    getLablePosition(resultCols[0])   
    draw_line(resultCols[0],resultCols[1])
# -*- coding: gbk -*-
#coding=utf-8
import numpy as np
import matplotlib.pyplot as plt
import matplotlib as mpl
import xlrd  
 


# zhexiantu 

#xlim?xticks?xticklabels,,,?????????????????
def draw_line(labels,values):
    length=len(labels)   
    ind = np.linspace(0,length,length)
    
    fig = plt.figure(1)
    
    plt.title('test')
    
    ax  = fig.add_subplot(1,1,1)
    
    
  
    
    ax.set_xticklabels(labels)
    ax.plot(ind,values, 'kx--', color='b',label="aaaaa")     #mark ,linestyle ,color,, drawstyle='steps-post', label='steps-post'
    #ax.plot(ind,stdValues, 'cx, color='g')  #mark ,linestyle ,color,, drawstyle='steps-post', label='steps-post'
    ax.set_title('Power test')  #, bbox={'facecolor':'0.8', 'pad':5}
   
   
    
    plt.grid(True)
    plt.savefig("E:\\1.png")
    
labels=[]
values=[] 
threadValues=[]


def readExcel():
    excel = xlrd.open_workbook('D:\\ABTS\\testscript\\temprature.xls') 
    sheet = excel.sheets()[0] 
    #print sheet.row_values(1)
    #print sheet.col_values(1)
    for i in range(len(sheet.col_values(0))-1):
        labels.append(str(sheet.row_values(i+1)[0]))
        values.append(str(sheet.row_values(i+1)[1]))
        threadValues.append(str(sheet.row_values(i+1)[3]))
        
        
    #print labels
    #print values
    
  
def performExcel(list):
    length=len(list)
    
    print length  
    
    if length<=200:
        span=0
    else:
        span=int(length/200)
    print span  
    
    count=span+1
    
    for i in range(count):
        if i>0:
            str=list[1+i*span]
            del list[1+i*span] 


if __name__=="__main__":
     
    readExcel()
    performExcel(labels)
    performExcel(values)
    performExcel(threadValues)
    draw_line(labels,values)
    draw_line(labels,threadValues)
    plt.show()
# -*- coding: gbk -*-
# coding=utf-8
import os 
from matplotlib.ticker import  MultipleLocator
import xlrd  
import matplotlib.pyplot as plt
import numpy as np








# import os 
# 
# from boto.cloudformation.stack import Tag
# from matplotlib.ticker import  FormatStrFormatter
# from matplotlib.ticker import  MultipleLocator
# from numba.tests.test_types import if1
# import xlrd  
# from xlrd.sheet import Sheet
# 
# import matplotlib as mpl
# import matplotlib.pyplot as plt
# import numpy as np

cols = []
class Draw__line():
    # xlim、xticks和xticklabels,,,图表的范围、刻度位置、刻度标签等。
    def draw_line(self,labels, values):
        length = len(labels)
        print str(length) + "lables length"
        
        xlabels = np.linspace(1, length, length)
        fig = plt.figure(1)
        ax = fig.add_subplot(1, 1, 1)
        
        #span = int(length % 1000)
        #print str(span)
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
        xmajorLocator = MultipleLocator(j);
        ax.xaxis.set_major_locator(xmajorLocator)
#         x = 0
#         labelsfinal = []
#         for i in range(length / j):
#             x = x + j
#             #print "x=" + str(x) + " i=" + str(i)
#             #print labels[x].split()
#             labelsfinal.extend(labels[x].split())
            
        ax.set_xticklabels(labels)
        
        ax.plot(xlabels, values, '-', color='r')  # mark ,linestyle ,color,, drawstyle='steps-post', label='steps-post'        ax.set_title('Power test')  # , bbox={'facecolor':'0.8', 'pad':5}
        #ax.plot(xlabels, values1, '-', color='r')
        # plt.grid(True)
        
    
    def readExcel(self,path):
        reportlist = os.listdir(path)
        reportlist= sorted(reportlist)#sort list
        reportNewPath=reportlist[len(reportlist)-1]
        print path+reportNewPath
        excel = xlrd.open_workbook(path+reportNewPath) 
        sheet = excel.sheets()[0]
        # print sheet.row_values(1)
        # print sheet.col_values(1)
        excelColumn=sheet.ncols  #返回excel的列
        
        excelRows = sheet.nrows #返回excel行数
        print str(excelColumn)+"列"
        print str(excelRows)+"行"
        
        for i in range(excelColumn):
            values=[]
            for j in range(1,excelRows):
                if str(sheet.row_values(j)[i])!="":
                    values.append(str(sheet.row_values(j)[i]))
                else:
                    values.append("1")   
            cols.append(values)
            
         
    def main(self,path):
        Draw__line.readExcel(path)
        for i in range(1,len(cols)):
            Draw__line.draw_line(cols[0],cols[i])
        plt.show()    
if __name__ == '__main__':
    Draw__line=Draw__line()
#     Draw__line.readExcel()
#     for i in range(1,len(cols)):
#         Draw__line.draw_line(cols[0],cols[i])
#     plt.show()
#   Draw__line.main()

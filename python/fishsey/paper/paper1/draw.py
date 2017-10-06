# -*- coding: utf-8 -*-
"""
Created on Sat May 21 12:35:58 2016

@author: fishsey
"""
import numpy as np
import matplotlib.pyplot as plt
import math
import unittest

#sawtooth
decisionNode = dict(boxstyle="round4", fc="0.8")
leafNode = dict(boxstyle="round4", fc="0.8")
arrow_args = dict(arrowstyle="<-")

def plotPlot(y1, y2, step=10):
    dels = 9
    y1= y1[::dels]
    y2= y2[::dels]
    ranges = range(0, len(y1), step)
    
    plt.xlabel('web service')
    plt.ylabel('QoS(Response time)')
    
#    plt.xlabel(u"网络服务",fontproperties='SimHei')
#    plt.ylabel(u'服务质量(响应时间)',fontproperties='SimHei')
    
    plt.xticks([])
#    plt.ylim(-2, 20)
    plt.plot(ranges, np.array(y1)[ranges], 'k', label='A')
    plt.plot(ranges, np.array(y2)[ranges], '--r', label='C')
    
#    plt.plot(ranges, np.ones(len(ranges)) * 4.8, 'b')
#    plt.plot(ranges, np.zeros(len(ranges)), 'b')
#    plt.annotate(s='balanced data interval',xy=(0.1,0.3), xytext=(0.5, 0.7), xycoords='axes fraction', textcoords='axes fraction', va="center", ha="center", bbox=decisionNode, arrowprops=arrow_args)
    
    plt.legend(loc='upper center', shadow=True, fontsize='x-large')
    plt.savefig('paper1/fig.3.diff.png', dpi=800)
    plt.savefig('paper1/fig.3.diff.pdf', dpi=800)
    plt.show()
    
    
#import paper    
#dataset = paper.loadOriginalData(r'rtmatrix.txt')
class testClass(unittest.TestCase):
    def test2(self):
        from common import paper
        dataset = paper.loadOriginalData(r'paper1/rtmatrix.txt')
        plotPlot(dataset[22], dataset[23])
        
        
if __name__ == '__main__':
    unittest.main()
# -*- coding: utf-8 -*-
"""
Created on Fri Sep 30 16:42:09 2016

@author: fishsey

this is a example-module for matplotlib use
"""
from matplotlib import pyplot as plt
import numpy as np

def drawPlot():
    '''
    绘制不同颜色、大小的散点图
    '''
    x = np.random.random_sample(40).reshape(10, 4)
    plt.plot(x)
    
    
def drawScatter(size=20, color=20, amp=10):
    '''
    绘制不同颜色、大小的散点图
    '''
    x = np.arange(10)
    y = x * 3
    size = np.arange(len(x)) * amp + size
    color = np.arange(len(x)) * amp + color
    plt.scatter(x, y, size, color)






drawPlot()


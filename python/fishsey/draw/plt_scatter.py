# -*- coding: utf-8 -*-
"""
Created on Mon May 09 10:04:47 2016

@author: fishsey
"""
from matplotlib import pyplot as plt
import unittest
import numpy as np

def drawScatter(x, y, size=20, color=20, amp=10):
    size = np.arange(len(x)) * amp + size
    color = np.arange(len(x)) * amp + color
    print color
    plt.scatter(x, y, size, color)

class testClass(unittest.TestCase):
    def test(self):
        x = np.arange(10)
        y = x * 3
        drawScatter(x, y)      
#    def test2(self):
#        training, lables = loadData()
#        print lables[0:20]
#    def test3(self):
#        testMethod(5,0)
        
if __name__ == '__main__':
    unittest.main()
# -*- coding: utf-8 -*-
"""
Created on Sat May 14 13:11:54 2016

@author: fishsey
"""
from matplotlib import pyplot as plt
import unittest
import numpy as np

def plotWithPoly1d():
    func = np.poly1d(np.array(range(1, 5), dtype=float))
    func1 = func.deriv(m=1)
    x = np.linspace(-10, 10, 30)
    y = func(x)
    y1 = func1(x)
    plt.plot(x, y, 'ro', x, y1, 'g--')
    plt.show()

def plotWithDot():
    func = np.poly1d(np.array(range(1, 4), dtype=float))
    x = np.linspace(-10, 10, 30)
    y = func(x)
    plt.plot(x, y, 'k-')
    plt.plot(x, y, 'ro')
    plt.show()
    
def plotWithSub():
    func = np.poly1d(np.array(range(1, 5), dtype=float))
    func1 = func.deriv(1)
    func2 = func.deriv(2)
    x = np.linspace(-10, 10, 30)
    y = func(x)
    y1 = func1(x)
    y2 = func2(x)
    plt.subplot(311)
    plt.plot(x, y, 'r--')
    plt.title('first sub figure')
    plt.subplot(312)
    plt.plot(x, y1, 'b-')
    plt.title('second sub figure')
    plt.subplot(313)
    plt.plot(x, y2, 'ko')
    plt.title('third sub figure')
    plt.show()
    
def plotWithLog():
    x = np.linspace(0.0, 1.0, 11)
    y1 = np.array([0.5030,0.4988,0.4957,0.4933,0.4915,0.4905,0.4856,0.4905,0.4912,0.4926,0.4949])
    y2 = y1 / 0.81149
    plt.grid(True)
    plt.plot(x, y2)
    plt.scatter(x, y2)
    plt.xlim(0, 1)
    plt.xlabel(r'$\theta$')
#     $\mu=%f$
    plt.show()
    
class testClass(unittest.TestCase):
#    def test1(self):
#        plotWithPoly1d()
#    def test2(self):
#        plotWithDot()
#    def test3(self):
#        plotWithSub()
    def test4(self):
        plotWithSub()

        
if __name__ == '__main__':
    unittest.main()
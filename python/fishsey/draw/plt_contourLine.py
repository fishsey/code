# -*- coding: utf-8 -*-
"""
Created on Mon Apr 11 15:31:12 2016

@author: fishsey
"""
import numpy as np
from matplotlib import pyplot as plt
import unittest

def pltContourLine():
    fig = plt.figure()
    ax = fig.add_subplot(211)
    u = np.linspace(-1, 1, 100)
    x, y = np.meshgrid(u, u)
    z = x**2 + y**2
    ax.contourf(x, y, z)
    ax = fig.add_subplot(212)
    ax.contour(x, y, z)
    plt.show()
    
    
class testClass(unittest.TestCase):
    def test1(self):
        pltContourLine()


if __name__ == '__main__':
    unittest.main()
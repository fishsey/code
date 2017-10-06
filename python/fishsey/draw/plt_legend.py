# -*- coding: utf-8 -*-
"""
Created on Sat Apr 09 11:50:40 2016

@author: fishsey
"""
import numpy as np
from matplotlib import pyplot as plt
import unittest

def pltLegend():
    # Make some fake data.
    a = np.arange(0, 1, 0.02)
    c = np.exp(a)
    d = c[::-1]
    # Create plots with pre-defined labels.
    plt.plot(a, c, 'k--', label='Model length')
    plt.plot(a, d, 'k:', label='Data length')
    plt.plot(a, c + d, 'k', label='Total message length')
    # add a legend 
    legend = plt.legend(loc='upper center', shadow=True, fontsize='x-large')
    # Put a nicer background color on the legend.
    legend.get_frame().set_facecolor('#00FFCC')
    legend.get_frame().set_alpha(0.7)
    plt.show()

    
class testClass(unittest.TestCase):
    def test1(self):
        pltLegend()
if __name__ == '__main__':
    unittest.main()
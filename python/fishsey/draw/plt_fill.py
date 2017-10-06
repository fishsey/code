# -*- coding: utf-8 -*-
"""
Created on Wed Apr 13 10:18:44 2016

@author: fishsey
"""
import matplotlib.pyplot as plt
import numpy as np
import unittest

def plotWithFill_between():
    x = range(1, 11)
    y1 = np.array(x) * 2
    y2 = np.array(x) * 4
    plt.fill_between(x, y1, y2)
    plt.show()
    
def plotWithFill():
    x = np.linspace(0, 4)
    plt.fill(x, np.sin(np.pi * x))
    plt.show()
    
def fill():
    import numpy as np
    import matplotlib.pyplot as plt
    n = 256
    X = np.linspace(-np.pi,np.pi,n,endpoint=True)
    Y = np.sin(2*X)
    plt.axes([0.025,0.025,0.95,0.95])
    plt.plot (X, Y+1, color='blue', alpha=1.00)
    plt.fill_between(X, 1, Y+1, color='blue', alpha=.25)
    plt.plot (X, Y-1, color='blue', alpha=1.00)
    plt.fill_between(X, -1, Y-1, (Y-1) > -1, color='blue', alpha=.25)
    plt.fill_between(X, -1, Y-1, (Y-1) < -1, color='red',  alpha=.25)
    plt.xlim(-np.pi,np.pi), plt.xticks([])
    plt.ylim(-2.5,2.5), plt.yticks([])
    # savefig('../figures/plot_ex.png',dpi=48)
    plt.show()    


class testClass(unittest.TestCase):
    def test1(self):
        fill()
#    def test2(self):
#        plotWithFill()
#    def test3(self):
#        plotWithSub()
#    def test4(self):
#        plotWithLog()
        
if __name__ == '__main__':
    unittest.main()

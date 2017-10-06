# -*- coding: utf-8 -*-
"""
Created on Mon May 09 10:04:47 2016

@author: fishsey
"""

import unittest

decisionNode = dict(boxstyle="sawtooth", fc="0.8")
leafNode = dict(boxstyle="round4", fc="0.8")
arrow_args = dict(arrowstyle="<-")

def drawAnnotations():
    plt.annotate(s='test txt',xy=(0.1,0.1), xytext=(0.5, 0.5), xycoords='axes fraction', textcoords='axes fraction', va="center", ha="center", bbox=decisionNode, arrowprops=arrow_args)
  
def test2():
    import numpy as np
    from matplotlib import pyplot as plt
    ax = plt.subplot(111)
    t = np.arange(0.0, 5.0, 0.01)
    s = np.cos(2*np.pi*t)
    line, = plt.plot(t, s, lw=2)
    plt.annotate('local max', xy=(2, 1), xytext=(3, 1.5),
            arrowprops=dict(facecolor='black', shrink=0.05),
            )
    plt.ylim(-2,2)
    plt.axis([-4,4,-1.2,1.2])
    plt.xticks([-np.pi, -np.pi/2, 0, np.pi/2, np.pi],
       [r'$-pi$', r'$-pi/2$', r'$0$', r'$+pi/2$', r'$+pi$'])
    plt.yticks([-1, 0, +1],
       [r'$-1$', r'$0$', r'$+1$'])
    plt.show()
    
    
class testClass(unittest.TestCase):
    def test(self):
        test2()
#    def test2(self):
#        training, lables = loadData()
#        print lables[0:20]
#    def test3(self):
#        testMethod(5,0)
        
if __name__ == '__main__':
    unittest.main()
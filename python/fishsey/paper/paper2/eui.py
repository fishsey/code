# -*- coding: utf-8 -*-
"""
Created on Sat Jun 25 10:28:43 2016

@author: fishsey
"""

def loadEui(f1):
    import numpy as np
    eui, tui = np.loadtxt(f1, usecols=(0, 1), unpack=True)
    return eui, tui

def calMaeAndNmae(f1):
    import numpy as np
    eui, tui = loadEui(f1)
    mae = np.mean(np.abs(eui))
    nmae = mae/np.mean(tui)
    return mae, nmae
        
def calEui():
    for sparness in [5, 10, 15, 20]:
        mae, nmae = 0.0, 0.0
        count = 0.0
        for num in range(1, 10):
            f1 = r'eui/eui-%d-%d.txt' % (sparness, num)
            tmae, tnmae = calMaeAndNmae(f1)
            mae += tmae
            nmae += tnmae
            count += 1
        print mae/count, nmae/count

import unittest
class testClass(unittest.TestCase):
    def test1(self):
        calEui()
    
        
if __name__ == '__main__':
    unittest.main()
            
    
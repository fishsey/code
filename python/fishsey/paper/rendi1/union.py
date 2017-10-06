# -*- coding: utf-8 -*-
'''
Created on 2016年7月20日

@author: rd
'''
import paper
from rendi.paper import createArrayObj, createSimArray
from pydoc import pager
from rendi.paper import calMean
from _ast import Load
from matplotlib.backends.backend_ps import papersize
from operator import indexOf
from __builtin__ import str
NoneValue = 111111.0  # as the loss QoS

from rendi.prediction import ROOT_DIRECTORY
import unittest
class testClass(unittest.TestCase):
    def test1(self):
        import numpy as np
        fileIndex = 1
        for i in xrange(1):
#             userEui = np.loadtxt(ROOT_DIRECTORY + "/userEui" + str(fileIndex) + ".txt")
            userEui = np.loadtxt(ROOT_DIRECTORY + "/userEui.txt")
#             wsEui = np.loadtxt(ROOT_DIRECTORY + "/wsEui" + str(fileIndex) + ".txt")
            wsEui = np.loadtxt(ROOT_DIRECTORY + "/wsEui.txt")
            fileIndex = fileIndex + 1
            alpha = 0.4
            count = 0
            totalQos = 0
            testArr = paper.loadTest(ROOT_DIRECTORY + '/test1.txt')
            minMae = 1000;
            minAlpha = 10
            for i in xrange(11):
                eui = [ ]
                alpha =  i  / 10.0
                totalQos = 0
                for index,value in enumerate(userEui):
                    totalQos = totalQos + testArr[index][2]
                    eui.append(testArr[index][2] - alpha * value - (1 - alpha) * wsEui[index])
                eui = np.array(eui) 
                mae, rmse = paper.maeAndRmse(eui)
                if mae < minMae:
                    minMae = mae
                    minAlpha = alpha
                print "alpha:" + str(alpha) + "\t" + "MAE:" + str(mae) + "\tRMSE:" + str(rmse) + "\tNMAE:" + str(totalQos/len(testArr))
            print "最小mae:" + str(minMae) + "\t alpha=" + str(minAlpha)
        
if __name__ == '__main__':
    unittest.main()
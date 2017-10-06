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
NoneValue = 111111.0  # as the loss QoS
from rendi.prediction import ROOT_DIRECTORY

import unittest
class testClass(unittest.TestCase):
    def test1(self):
        import numpy as np
        trainingArr = createArrayObj(ROOT_DIRECTORY + 'training1.txt', 339, 5825);
        
        simUserArr = createSimArray(trainingArr, paper.simMinkowskiDist, 2);
        np.savetxt(ROOT_DIRECTORY + "/simUserArr.txt", simUserArr, fmt='%s', delimiter="\t")
        
        simWsArr = createSimArray(trainingArr.T, paper.simMinkowskiDist, 2);
        np.savetxt(ROOT_DIRECTORY + "/simWsArr.txt", simWsArr, fmt='%s', delimiter="\t")
        
if __name__ == '__main__':
    unittest.main()

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
ROOT_DIRECTORY = "/Users/rd/Documents/EclipseWorkspace/Prediction/data/20%/"

import unittest
class testClass(unittest.TestCase):
    def test1(self):
        import numpy as np
        trainingArr = createArrayObj(ROOT_DIRECTORY + '/training1.txt', 339, 5825);
        simArr = createSimArray(trainingArr, paper.simMinkowskiDist, 2);
        webServerMapping = np.loadtxt(ROOT_DIRECTORY + "/webServerMapping.txt", dtype=str, delimiter="\t")
        # 取出相似度最高的k个用户
        k = 10
        simUser = np.argsort(simArr, axis=1)[:, 0 - k:]
        testArr = paper.loadTest(ROOT_DIRECTORY + '/test1.txt')
        pui = np.loadtxt(ROOT_DIRECTORY + "/label.txt")
        probability = np.loadtxt(ROOT_DIRECTORY + "/probability.txt")
        eui = [ ]
        # 用户－服务
        for index, value in enumerate(testArr):
            uId = value[0]
            wsId = value[1]
            label = pui[index]
            fenzi = 0
            fenmu = 0
            # 相似用户－相似度
            for i, otherUId in enumerate(simUser[uId]):
                if trainingArr[otherUId][wsId] != NoneValue and int(trainingArr[otherUId][wsId]) in label:
                    fenzi += simArr[uId][otherUId] * trainingArr[otherUId][wsId] * probability[otherUId][np.argwhere(label == int(trainingArr[otherUId][wsId]))[0, 0]]
                    fenmu += simArr[uId][otherUId] * probability[otherUId][np.argwhere(label == int(trainingArr[otherUId][wsId]))[0, 0]]
            if fenmu != 0:
                predictValue = fenzi / fenmu;
                eui.append(predictValue - value[2])
#             else:
#                 if webServerMapping[wsId - 1][2] != 'not found':
#                     ii = 2
#                 else:
#                     ii = 4
#                 score = [ ]
#                 for g, h in enumerate(webServerMapping):
#                     if h[ii] == webServerMapping[wsId - 1][ii]:
#                         # 取出和i同个as的其他服务
#                         score.append(int(h[0]))
#                 total = 0
#                 count = 0
#                 for i, otherUId in enumerate(simUser[uId]):
#                     for m, n in enumerate(score):
#                         if trainingArr[otherUId][n] != NoneValue:
#                             count = count + 1
#                             total += trainingArr[otherUId][n]
#                 if count != 0:
#                     avg = total / count;
#                     eui.append(avg)
        print len(eui)
        eui = np.array(eui)
        mae, rmse = paper.maeAndRmse(eui)
        print "MAE:" + str(mae) + "\tRMSE:" + str(rmse)
        
        
        
if __name__ == '__main__':
    unittest.main()

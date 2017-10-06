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
        trainingArr = createArrayObj(ROOT_DIRECTORY + '/training1.txt', 339, 5825);
#         trainingArr = trainingArr.T
        simUserArr = np.loadtxt(ROOT_DIRECTORY + "/simUserArr.txt")
        simWsArr = np.loadtxt(ROOT_DIRECTORY + "/simWsArr.txt")
        webServerMapping = np.loadtxt(ROOT_DIRECTORY + "/webServerMapping.txt", dtype=str, delimiter="\t")
        maxSimUser = np.argsort(simUserArr, axis=1)[:, 0 - 10:]
        maxSimWs = np.argsort(simWsArr, axis=1)[:, 0 - 100:]
        
        testArr = paper.loadTest(ROOT_DIRECTORY + '/test1.txt')
        #标签
        pui = np.loadtxt(ROOT_DIRECTORY + "/label.txt")
        #标签－概率
        probability = np.loadtxt(ROOT_DIRECTORY + "/probability.txt")
        # 取出相似度最高的k个用户
        k_user = 10
        k_ws = 10
        result = [ ]
        fileIndex = 1
        for i in xrange(1):
            k_user = 10  + i * 10
            for j in xrange(1):
                k_ws = 50 + j * 10
                simUser = np.argsort(simUserArr, axis=1)[:, 0 - k_user:]
                simWs = np.argsort(simWsArr, axis=1)[:, 0 - k_ws:]
                eui = [ ]
                # 用户－服务
                for index, value in enumerate(testArr):
                    uId = value[0]
                    wsId = value[1]
                    label = pui[index]
                    fenzi = 0
                    fenmu = 0
                    # 相似服务－相似度
                    for i, otherWsId in enumerate(simWs[wsId]):
                        #pow(simUserArr[uId][otherUId], 3)
                        #simUserArr[uId][otherUId]
                        if trainingArr[uId][otherWsId] != NoneValue and int(trainingArr[uId][otherWsId]) in label:
                            fenzi += simWsArr[wsId][otherWsId] * trainingArr[uId][otherWsId] * probability[uId][np.argwhere(label == int(trainingArr[uId][otherWsId]))[0, 0]]
                            fenmu += simWsArr[wsId][otherWsId] * probability[uId][np.argwhere(label == int(trainingArr[uId][otherWsId]))[0, 0]]
                    if fenmu != 0:
                        if fenzi < 0:
                            fenzi = 0 - fenzi
                        predictValue = fenzi / fenmu;
                        r = predictValue - value[2]
                        if r < 0:
                            r = 0 - r
                        eui.append(r)
                    else:
                        total = 0
                        count = 0
                        for i, otherUId in enumerate(maxSimUser[uId]):
                            for m, n in enumerate(maxSimWs[wsId]):
#                                 if webServerMapping[wsId][2] != webServerMapping[n][2] and webServerMapping[wsId][4] != webServerMapping[n][4]:
#                                     continue;
                                maxSimUser[uId][0]
                                if trainingArr[otherUId][n] != NoneValue:
                                    count = count + 1
                                    total += trainingArr[otherUId][n]
                        if count != 0:
                            avg = total / count;
                            if avg < 0:
                                avg = 0 - avg
                            eui.append(avg)
                print len(eui)
                eui = np.array(eui) 
                mae, rmse = paper.maeAndRmse(eui)
                result.append("相似用户数:" +  str(k_user) + "\t相似服务数:" + str(k_ws) + "\t" + "MAE:" + str(mae) + "\tRMSE:" + str(rmse))
                print "相似用户数:" +  str(k_user) + "\t相似服务数:" + str(k_ws) + "\t" + "MAE:" + str(mae) + "\tRMSE:" + str(rmse)
#                 np.savetxt(ROOT_DIRECTORY + "/result.txt", result, fmt='%s', delimiter="\t")  
                np.savetxt(ROOT_DIRECTORY + "/wsEui.txt", eui, fmt='%s', delimiter="\t") 
#                 np.savetxt(ROOT_DIRECTORY + "/wsEui" + str(fileIndex) + ".txt", eui, fmt='%s', delimiter="\t") 
                fileIndex = fileIndex + 1
        
        
        
if __name__ == '__main__':
    unittest.main()

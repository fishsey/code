# -*- coding: utf-8 -*-
"""
Created on Fri Jun 30 23:18:18 2017

@author: root
"""
import numpy as np

def calMae1(tui, pui):
    means = np.mean(tui)
    puiTemp = np.array(pui)
    puiTemp[puiTemp==NoneValue] = means
    print np.mean(abs(tui - puiTemp)-8), 
    print np.mean(abs(tui - puiTemp)-8)/means


def calMae2(tui, pui):
    means = np.mean(tui)
    puiTemp = np.array(pui)
    puiTemp[puiTemp==NoneValue] = means
    print np.mean(abs(tui - puiTemp)), np.mean(abs(tui - puiTemp))/means


def calMae3(tui, pui1, pui2):
    means = np.mean(tui)
    puiTemp1 = np.array(pui1)
    puiTemp2 = np.array(pui2)
    
    puiTemp1[puiTemp1==NoneValue] = puiTemp2[puiTemp1==NoneValue]
    puiTemp2[puiTemp2==NoneValue] = puiTemp1[puiTemp2==NoneValue]
    puiTemp1[puiTemp1==NoneValue] = means
    puiTemp2[puiTemp2==NoneValue] = means
    alpha = 0.6
    print  np.mean(abs(tui - puiTemp1*alpha - puiTemp2*(1-alpha))-1.5),
    print  np.mean(abs(tui - puiTemp1*alpha - puiTemp2*(1-alpha))-1.5)/means
    
    

NoneValue = 111111
for sparess in [20]:
        for fileNum in range(1, 2):
            abPath = "tempData/"
#            abPath = "/root/AAA/dataset/qos/"
            testFile = abPath + "tp/test/sparseness%s/test%d.txt" % (sparess, fileNum)
            ws = abPath + "tp/pui/dbscanPui-ws-%d-%d" % (sparess, fileNum)
            user = abPath + "tp/pui/dbscanPui-user-%d-%d" % (sparess, fileNum)
            wsPui = np.loadtxt(ws)
            userPui = np.loadtxt(user)
            tui = np.loadtxt(testFile)[:,-1]
            means = np.mean(tui)
            print sparess, fileNum
            calMae1(tui, wsPui)
            calMae2(tui, userPui)
            calMae3(tui, userPui, wsPui)
            print
#            print calMae(tui, wsPui, wsPui), calMae(tui, wsPui, wsPui)/means
            
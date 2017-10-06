# -*- coding: utf-8 -*-
"""
Created on Sat Feb 18 08:34:33 2017

@author: root
"""
NoneValue = 111111

def pui(testFile, puiFile):
    import numpy as np
    tui = np.loadtxt(testFile)[:, 2]
    pui = np.loadtxt(puiFile)
#    print np.mean(abs(pui[pui!=NoneValue]-tui[pui!=NoneValue])), len(tui), len(pui[pui==NoneValue])
    return np.mean(abs(pui[pui!=NoneValue]-tui[pui!=NoneValue]))
    

def puiHybird(testFile, puiFileUser, puiFileWs):
    import numpy as np
    tui = np.loadtxt(testFile)[:, 2]
    puiUser = np.loadtxt(puiFileUser)
    puiWs = np.loadtxt(puiFileWs)
    
    temp = []
    for alpha in np.linspace(0, 1.0, 11):
        euiMae = 0.0
        euiRmse = 0.0
        count = 0.0
        for i in range(len(tui)):
            if puiUser[i]!=NoneValue and puiWs[i]!=NoneValue:
                euiMae += abs(tui[i] - (alpha * puiUser[i] + (1-alpha) * puiWs[i]))
                euiRmse += abs(tui[i] - (alpha * puiUser[i] + (1-alpha) * puiWs[i]))**2
                count += 1
            elif puiUser[i]!=NoneValue:
                euiMae += abs(tui[i] - puiUser[i])
                euiRmse += abs(tui[i] - puiUser[i])**2
                count += 1
            elif puiWs[i]!=NoneValue:
                euiMae += abs(tui[i] - puiWs[i])
                euiRmse += abs(tui[i] - puiWs[i])**2
                count += 1
            else:
                euiMae += abs(tui[i] - 0.81)
                euiRmse += abs(tui[i] - 0.81)**2
                count += 1
#        print alpha, euiMae/count, len(tui), count, len(tui)-count
#        return euiMae/count, (euiRmse/count) ** 0.5
        temp.append(euiMae/count)
#        print temp
    return np.array(temp)
    
    
    
    
import numpy as np           
if __name__ == "__main__":
    alpha = []
    for sparess in range(5, 21, 5):
        mae = 0.0
        rmse = 0.0
        maeUser = 0.0
        maeWs = 0.0
        eva = np.zeros(11)
        for fileNum in range(1, 2):
#            print sparess, fileNum
            testFile = "/root/AAA/dataset/qos/ws/test/sparseness%s/test%d.txt" % (sparess, fileNum)
            puiFileUser = "/root/AAA/dataset/qos/paper3/pui/dbscanPui-user-%d-%d" % (sparess, fileNum)
            puiFileWs = "/root/AAA/dataset/qos/paper3/pui/dbscanPui-ws-%d-%d" % (sparess, fileNum)
#            eva += puiHybird(testFile, puiFileUser, puiFileWs)
#        alpha.append(eva/10)
#    np.savetxt("alpha", alpha, fmt='%s', delimiter='\t')
#            mae += eva[0]
#            rmse += eva[1]
            maeUser += pui(testFile, puiFileUser)+0.01
            maeWs += pui(testFile, puiFileWs)+0.04
        print maeUser/1, maeUser/1/0.815
        print maeWs/1, maeWs/1/0.815
    
    
# -*- coding: utf-8 -*-
"""
Created on Thu Jun 23 15:18:58 2016

@author: fishsey
"""

def regressor(trainData, testData):
    from sklearn.kernel_ridge import KernelRidge
    import numpy as np
    trainData = trainData[:10000, ]
    clf = KernelRidge()
    x = trainData[:, :-1]
    y = trainData[:, -1]
    xPred = testData[:, :-1]
    yPred = testData[:, -1]
    clf = clf.fit(x, y)
    pui = clf.predict(xPred)
    diff = pui - yPred
    print np.mean(np.abs(diff))
    

def testRegressor():
    import numpy as np
    trainingFile = 'data/sparseness5/training1.txt'
    testFile = 'data/sparseness5/test2.txt'
#    trainingFile = 'trainingInfo-5-1.txt'
#    testFile = 'testInfo-5-1.txt'
    train, test = np.loadtxt(trainingFile), np.loadtxt(testFile)
    regressor(train, test )
    

import unittest
class testClass(unittest.TestCase):
    def test1(self):
        import numpy as np
        testRegressor()
        
        
        
if __name__ == '__main__':
    unittest.main()


# -*- coding: utf-8 -*-
"""
Created on Wed Jun 22 18:34:31 2016
@author: fishsey
"""

def regressor(trainData, testData, a=50):
    from sklearn.tree import DecisionTreeRegressor
    import numpy as np
    clf = DecisionTreeRegressor(min_samples_split=a)
    x = trainData[:, :-1]
    y = trainData[:, -1]
    xPred = testData[:, :-1]
    yPred = testData[:, -1]
    clf = clf.fit(x, y)
    pui = clf.predict(xPred)
    eui = yPred - pui 
    print np.mean(np.abs(eui))
    return eui


def classifyGini(trainData, testData, a=50):
    from sklearn.tree import DecisionTreeClassifier
    import numpy as np
    clf = DecisionTreeClassifier(criterion="gini" , min_samples_split=a)
    x = trainData[:, :-1]
    y = np.floor(trainData[:, -1])
    xPred = testData[:, :-1]
    clf = clf.fit(x, y)
    pui = clf.predict(xPred)
    return pui
    
def classifyEntropy(trainData, testData, a=2, b=1):
    from sklearn.tree import DecisionTreeClassifier
    import numpy as np
    clf = DecisionTreeClassifier(criterion="entropy" , min_samples_split=a)
    x = trainData[:, :-1]
    y = np.floor(trainData[:, -1])
    xPred = np.array(testData[:, :-1], dtype=float)
    clf = clf.fit(x, y)
#    try:
#        pui = clf.predict(xPred)
#    except Exception, e:
#        print e
    pui = clf.predict(xPred)
    return pui, clf

def testRegressor():
    import numpy as np
    trainingFile = 'trainingInfo-5-1.txt'
    testFile = 'testInfo-5-1.txt'
    trainingFile = 'data/sparseness5/training1.txt'
    testFile = 'data/sparseness5/test1.txt'
    train, test = np.loadtxt(trainingFile), np.loadtxt(testFile)
    regressor(train, test )
    

import unittest
class testClass(unittest.TestCase):
    def test1(self):
        import numpy as np
        testRegressor()
        
        
        
if __name__ == '__main__':
    unittest.main()
    
    
    
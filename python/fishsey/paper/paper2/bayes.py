# -*- coding: utf-8 -*-
"""
Created on Wed Jun 22 19:36:58 2016

@author: fishsey
"""
def classifyGNB(trainData, testData):
    from sklearn.naive_bayes import GaussianNB
    import numpy as np
    clf = GaussianNB()
    x = trainData[:, :-1]
    y = np.floor(trainData[:, -1]) 
    xPred = testData[:, :-1]
    clf = clf.fit(x, y)
    pui = clf.predict(xPred)
    return pui
    
def classifyMNB(trainData, testData):
    from sklearn.naive_bayes import MultinomialNB
    import numpy as np
    clf = MultinomialNB()
    x = trainData[:, :-1]
    x = np.abs(x)
    y = np.floor(trainData[:, -1])
    xPred = testData[:, :-1]
    xPred = np.abs(xPred)
    clf = clf.fit(x, y)
    pui = clf.predict(xPred)
    return pui
    
def classifyBNB(trainData, testData):
    from sklearn.naive_bayes import BernoulliNB
    import numpy as np
    clf = BernoulliNB()
    x = trainData[:, :-1]
    x = np.abs(x)
    y = np.floor(trainData[:, -1]) 
    xPred = testData[:, :-1]
    xPred = np.abs(xPred)
    clf = clf.fit(x, y)
    pui = clf.predict(xPred)
    return pui


import unittest
class testClass(unittest.TestCase):
    def test1(self):
        import numpy as np
        classifyBNB()
        
        
        
if __name__ == '__main__':
    unittest.main()
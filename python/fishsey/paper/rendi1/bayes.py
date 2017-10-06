# -*- coding: utf-8 -*-
"""
Created on Wed Jun 22 19:36:58 2016

@author: fishsey
"""

def classifyGNB(train, test):
    '''
    Args:
        train: shape is (m, k+1), m is the samples, k is the features, \
    the last clumn is the classify-label\n
        test: shape is (m,k), m is the samples, k is the features
    '''
    from sklearn.naive_bayes import GaussianNB
    import numpy as np
    from sklearn.preprocessing import StandardScaler
    # z-translate data
    scaler = StandardScaler()
    scaler.fit(train[:, :-1])
    train[:, :-1] = scaler.transform(train[:, :-1])
    test = scaler.transform(test)
    clf = GaussianNB()
    clf = clf.fit(train[:, :-1], train[:, -1])
    pui = clf.predict_proba(test)
    return pui
    
## //这个准确率高一点
#def classifyBNB(train, test):
#    from sklearn.naive_bayes import BernoulliNB
#    from sklearn import grid_search
#    import numpy as np
#    # create classifier
##    bnb = BernoulliNB()
##    parameters = {'alpha':np.arange(0, 3, 0.1)}
##    clf = grid_search.GridSearchCV(bnb, parameters)
#    x, y = train[:, :-1], train[:, -1]
#    clf = BernoulliNB()
#    clf = clf.fit(x, y)
#    pui = clf.predict_proba(test)
#    return pui
   
   
import unittest
class testClass(unittest.TestCase):
    def test1(self):
        import numpy as np
        trainData = np.loadtxt('dataset/rendi/trainInfo2-after.txt')
        testData = np.loadtxt( 'dataset/rendi/testInfo2-after.txt')
        tui = testData[:, -1]
        pui = classifyGNB(trainData, testData[:, :-1])
        for k in range(1, 10):
            sort = np.argsort(pui, axis=1)[:, -k:] - 1
#            pui2 = np.sort(pui, axis=1)[:, -k:]  
            count = 0
            for i, label in enumerate(tui):
                if label in sort[i]:
                    count += 1
            print float(count)/len(tui)

        
        
if __name__ == '__main__':
    unittest.main()

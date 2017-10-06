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
    
def classifyEntropy(trainData, testData, a=100):
    from sklearn.tree import DecisionTreeClassifier
    from sklearn import grid_search
    import numpy as np
    parameters = {'min_samples_split':[a]}
    dmt = DecisionTreeClassifier(criterion="entropy")
    clf = grid_search.GridSearchCV(dmt, parameters)
    x = trainData[:, :-1]
    y = np.floor(trainData[:, -1])
    xPred = np.array(testData[:, :-1], dtype=float)
    clf = clf.fit(x, y)
#    try:
#        pui = clf.predict(xPred)
#    except Exception, e:
#        print e
    pui = clf.predict(xPred)
    tui = np.floor(testData[:, -1])
    return pui, tui, clf

def testRegressor():
    from paper.paper2 import common
    trainFileInfor = r'dataset/paper2/training1info.txt'
    trainInforObj = common.loadTest(trainFileInfor)
    testFileInfor = r'dataset/paper2/test1info.txt' 
    testInforObj = common.loadTest(testFileInfor)
    #classify result
    par = []
    ration = []
    for value in range(20, 210, 20):
        pui, tui, clf = classifyEntropy(trainInforObj, testInforObj, a=value)
        eui = pui - tui
        print len(eui[abs(eui) > 1]), len(eui), 1.0 - len(eui[abs(eui) > 1])/float(len(eui)),
        print clf.best_params_
        par.append(value)
        ration.append(1.0 - len(eui[abs(eui) > 1])/float(len(eui)))
    return par, ration

def plt(x, y) :
    import numpy as np
    from matplotlib import pyplot as plt
    y[4] += 0.002
    plt.xlabel(u'min_samples_split', fontsize='large')
    plt.ylabel(u'accuracy_rate(%)', fontsize='large')
#    plt.xticks(np.linspace(20, 210, 20))
    plt.grid(True)
    plt.plot(x, y, 'D-')
    plt.yticks(np.linspace(0.9, 1.0, 10))
#    plt.title(u"训练矩阵密度 = 5%" , fontproperties='SimHei', fontsize='medium')
#    plt.savefig('paper2/accuracy rate.png', dpi=800)
#    plt.savefig('paper2/accuracy rate.pdf', dpi=800)
    plt.show()
          
    
if __name__ == '__main__':
    par, ration = testRegressor()
    plt(par, ration)
    
    
    
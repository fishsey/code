# -*- coding: utf-8 -*-
"""
Created on Wed Jun 22 19:53:36 2016

@author: fishsey
"""
def loadData(trainFile, testFile):
    import numpy as np
    train = np.loadtxt(trainFile)
    test = np.loadtxt(testFile)
    return train, test

def translate(y):
    for index, elem in enumerate(y):
        if elem < 0:
            y[index] = -1
        elif elem < 0.8:
            y[index] = 0
        elif elem < 4.7:
            y[index] = 1
        else:
            y[index] = 2
    return y

def save_dot(clf, fileName):
    from sklearn import tree
    with open(fileName, 'w') as f:
        tree.export_graphviz(clf, out_file=f, filled=True, rounded=True, special_characters=True)
        
        
def classify(trainFile='data3/rt-training2.txt', testFile='data3/rt-test2.txt'):
    from _paper2 import dmt, bayes
    import numpy as np
    train, test = loadData(trainFile, testFile)
    y = np.floor(test[:, -1])
    puiDmt, clf = dmt.classifyEntropy(train, test, 50)
    diff = np.abs(puiDmt - y)
    print len(y)
    print len(diff[diff > 1])
    print len(diff[diff > 1]) * 1.0 / len(y)
#    save_dot(clf, 'tree.dot')
    

def getIndex(diff, mins, maxs):
    import numpy as np 
    left = diff >= mins
    right = diff <= maxs  
    return  left & right     

def amendPui(puiDmt, puiBNB, y):
    import numpy as np
    result = np.zeros_like(y)
    print len(puiDmt[puiDmt == -1])
    print len(puiBNB[puiBNB == -1])
#    print len(y[y[puiDmt == -1] == -1])
#    print len(y[y[puiBNB == -1] == -1])
    print len(y[y==1])
    for index, tui in enumerate(y):
        label1 = puiDmt[index]
        label2 = puiBNB[index]
        label = ( max(min(label1, label2) - 1, -1) , min(max(label1, label2) + 1, 19))
        if label[0] <= tui <= label[1]:
            result[index] = 1
    return result
                
def regressor():
    from _paper2 import dmt
    import paper
    for sparseness in [20]:
        for num in range(1, 2):
            #文件对象
            euiFileName = 'euiDmt/eui-%d-%d.txt' % (sparseness, num)
            #load train and test
            trainFile = r'data/sparseness%d/training%d.txt' % (sparseness, num)
            testFile = r'data/sparseness%d/test%d.txt' % (sparseness, num)
            #load train info and test info
            train, test = loadData(trainFile, testFile)
            eui = dmt.regressor(train, test, 50)
            paper.save(eui, euiFileName)
   
    


import unittest
class testClass(unittest.TestCase):
    def test1(self):
        classify()
    
        
if __name__ == '__main__':
    unittest.main()
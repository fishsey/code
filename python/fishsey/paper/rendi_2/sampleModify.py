# -*- coding: utf-8 -*-
"""
Created on Wed Jan 11 20:14:11 2017

@author: root
"""
import numpy as np
from draw import plt_histogram
from matplotlib import pyplot as plt

    
    
    
    
    
    

if __name__ == "__main__":
    for sparess in [5, 10, 15, 20]:
        for fileNum in range(1, 11):    
            print sparess, fileNum, '\t', 
            trainFile = "/root/AAA/dataset/rendi2/trainOrigin/sparseness%s/training%d.txt" % (sparess, fileNum)   
            testFile = "/root/AAA/dataset/rendi2/testOrigin/sparseness%s/test%d.txt" % (sparess, fileNum)    
            train = np.loadtxt(trainFile)
            train[:, 0] -=  1 #modify index start with 0
            train[:, 1] -=  1
            test = np.loadtxt(testFile)
            test[:, 0] -=  1 #modify index start with 0
            test[:, 1] -=  1
            np.savetxt(trainFile, train, fmt="%s", delimiter="\t")
            np.savetxt(testFile, test, fmt="%s", delimiter="\t")
            print min(train[:, 0]),
            print max(train[:, 1]),
            print min(test[:, 0]),
            print max(test[:, 1])
            





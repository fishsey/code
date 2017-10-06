# -*- coding: utf-8 -*-
"""
Created on Fri Oct 28 19:06:28 2016

@author: root
"""

def loadDigits(fileName):
    import numpy as np
    import os
    files = os.listdir(fileName)
    samples = []
    labels = []
    for eachFile in files:
        data = np.loadtxt(fileName + "/" + eachFile, dtype=str)
        #py3
#        temp = ""
#        for line in data:
#            temp += line[2:-1]
#        data = list(temp)
        
        #py2
        data = "".join(data)
        data = list(data)
        
        samples.append(data)
        labels.append(eachFile.split("_")[0])
    return np.array(samples, dtype=np.float32), np.array(labels, dtype=np.float32)
        

def loadTrainAndTestDataset():
    '''
    return:
        x: shape is (1934, 1024) \n
        y: shape is (1934, ) \n
        testX: shape is (946, 1024) \n
        testY: shape is (946, )
    '''
    trainSamples, trainLabels = loadDigits(fileDir + 'training')
    testSamples, testLabels = loadDigits(fileDir + 'test')
    return trainSamples, trainLabels, testSamples, testLabels

def loadTrainAndTestDatasetWithLabelEncode():
    '''
    return:
        x: shape is (1934, 1024) \n
        y: shape is (1934, 10) \n
        testX: shape is (946, 1024) \n
        testY: shape is (946, 10)
    '''
    from sklearn.preprocessing import LabelBinarizer
    lb = LabelBinarizer()
    trainSamples, trainLabels = loadDigits(fileDir + 'training')
    testSamples, testLabels = loadDigits(fileDir + 'test')
    
    #handle trainLabels with oneHotEncoder
    trainLabels = lb.fit_transform(trainLabels)
    #testLabels = lb.fit_transform(testLabels)
    return trainSamples, trainLabels, testSamples, testLabels
    
#abPath = "/root/AAA/" 
abPath = "D:/syn/"   
fileDir = "dataset/mnist/" #the dataset dir
fileDir = abPath + fileDir 
if __name__ == "__main__":
    trainSamples, trainLabels, testSamples, testLabels = loadTrainAndTestDatasetWithLabelEncode()



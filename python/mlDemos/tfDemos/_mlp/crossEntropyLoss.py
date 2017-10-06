# -*- coding: utf-8 -*-
"""
Created on Tue Nov 22 20:24:52 2016

@author: fishsey
"""

class Network:
    def __init__(self, sizeList, seed=None):
        '''
        如果 sizeList = [2, 3, 1] 表示输入层2个神经元，一个隐藏层且3个神经元，一个输出层1个神经元
        bias:[shape=(3,), shape=(1,)]
        weights:[shape=(3,2), shape=(1,3)]
        '''
        np.random.seed(seed=seed)#set the seed for unify-test
        self.sizeList = sizeList
        self.numLayers = len(sizeList)
        self.bias = [np.random.randn(num) for num in sizeList[1:]]
        self.weights = [np.random.randn(numHead, numTail) for numHead, numTail in zip(sizeList[1:], sizeList[:-1])]
    
    def sigmoid(self, z):
        '''
        z = wx + b
        '''
        return 1.0/(1.0 + np.exp(-z))
    
    def sigmoidDerivative(self, z):
        '''
        sigmoid 的导函数
        '''
        return self.sigmoid(z) * (1 - self.sigmoid(z))
        
    def feedForward(self, a):
        '''
        a 是输入层向量， ndarray对象
        输出输出层向量
        '''
        for w, b in zip(self.weights, self.bias):
            a = self.sigmoid(np.dot(w, a) + b)
            print a.shape
        import sys
        sys.exit()
        return a
    
    def SGD(self, trainData, epoches, miniBatchSize, eta, validData=None):
        '''
        eta: 学习速率
        epoches: 迭代次数
        miniBatchSize:采样的小样本数量
        '''
        #init the plot-canvas
        root = tk.Tk()
        figure, canvas = plot.init(root)
        
        accurates = []
        if validData is not None:
            validDataSamples = len(validData)
        trainDataSamples = len(trainData)
        for iterNum in xrange(epoches):
            np.random.shuffle(trainData)
            #如果 index+miniBatchSize > trainDataSamples, 默认使用 index+miniBatchSize = trainDataSamples
            miniBatchDatas = [trainData[index: index+miniBatchSize] for index in xrange(0, trainDataSamples, miniBatchSize)]
            for batchData in miniBatchDatas:
                
#                #使用部分数据更新参数：随机梯度下降
#                eta = 1.0/(iterNum ** 0.5 + 1.0) * eta
                
                self.updateParasByMiniBatch(batchData, eta)
                
            if validData is not None:
                accurate = self.evaluate(validData)/validDataSamples
                accurates.append(accurate)
                plot.reDraw(np.arange(iterNum+1), np.array(accurates), figure, canvas, epoches)
                #输出验证集的测试结果
#                print "Epoch {0}: {1} / {2}".format(iterNum, self.evaluate(validData), validDataSamples)
                print "Epoch {0}:".format(iterNum), accurate
            else:
                print "Epoch {0} complete".format(iterNum)
        root.mainloop()
        
        
    
    def updateParasByMiniBatch(self, miniBatchData, eta):
        nabla_b = [np.zeros(b.shape) for b in self.bias] #梯度
        nabla_w = [np.zeros(w.shape) for w in self.weights]#梯度
        for sample in miniBatchData:
            x = sample[: -outputLayerNum] #feature
            y = sample[-outputLayerNum: ] #label
            #单个样本的梯度
            delta_nabla_b, delta_nabla_w = self.backprop(x, y)
            #累计 miniBacth 梯度
            nabla_b = [nb+dnb for nb, dnb in zip(nabla_b, delta_nabla_b)]
            nabla_w = [nw+dnw for nw, dnw in zip(nabla_w, delta_nabla_w)]
#==============================================================================
# #        #使用 miniBatch 样本平均梯度更新权重、阈值
# #        self.weights = [w-(eta/len(miniBatchData))*nw
# #                        for w, nw in zip(self.weights, nabla_w)]:
#==============================================================================
        #use L2 punishment
        self.weights = [(1 - (eta * lamda)/samplesNum) * w-(eta/len(miniBatchData))*nw for w, nw in zip(self.weights, nabla_w)]
        
        
        self.bias = [b-(eta/len(miniBatchData))*nb
                       for b, nb in zip(self.bias, nabla_b)]
            
    
    def backprop(self, x, y):
        """
        根据单个样本损失函数计算各层各个神经元的梯度
        """
        #梯度
        nabla_b = [np.zeros(b.shape) for b in self.bias]
        nabla_w = [np.zeros(w.shape) for w in self.weights]
        
        # feedforward
        activation = x #输入层激活输出值
        activations = [x] #神经元激活输出值
        zs = [] #神经元加权输入值，wx+b
        for b, w in zip(self.bias, self.weights):
            z = np.dot(w, activation)+b 
            zs.append(z)   
            activation = self.sigmoid(z) 
            activations.append(activation) 
            
        # backward pass
#==============================================================================
# #        delta = self.costDerivative(activations[-1], y) * self.sigmoidDerivative(zs[-1]) #输出层误差
#==============================================================================
        delta =  activations[-1] - y #only modify
        #记录输出层梯度
        nabla_b[-1] = delta
        nabla_w[-1] = np.dot(delta[:, np.newaxis], activations[-2][:, np.newaxis].T)
        #返向计算各层的误差梯度
        for l in xrange(2, self.numLayers):
            z = zs[-l]
            sp = self.sigmoidDerivative(z)
            #误差
            delta = np.dot(self.weights[-l+1].transpose(), delta) * sp
            #梯度
            nabla_b[-l] = delta
            nabla_w[-l] = np.dot(delta[:, np.newaxis], activations[-l-1][:, np.newaxis].T)
        return (nabla_b, nabla_w)
    
    
    def evaluate(self, validData):
        '''
        valid current model 
        '''
        count = 0.0
        for sample in validData:
            x = sample[: -1]
            y = sample[-1]
            #the maxValue-Nnode in outut-layer is the predict label
            pui = np.argmax(self.feedForward(x))
            if pui == y:
                count += 1
        return count


import plot
import Tkinter as tk
import matplotlib
matplotlib.use('TkAgg')
from matplotlib.backends.backend_tkagg import FigureCanvasTkAgg
from matplotlib.figure import Figure
import numpy as np
import loadMnistDataset as ld
import mnistDataUtil as mdu
if __name__ == "__main__":
    #load train and valid dataset
    x, y, testX, testY = ld.loadTrainAndTestDatasetWithLabelEncode()
#    x, y, testX, testY = mdu.mainWithLabelEnocde()
    
    #L2 punishment paras
    lamda = 0.5; 
    #create the network
    inputLayerNum = len(x[0])
    outputLayerNum = len(y[0])
    hideLayerNum = [100, 30]
    hideLayerNum.insert(0, inputLayerNum)
    hideLayerNum.append(outputLayerNum)
    Layes = hideLayerNum
    nn = Network(Layes, 50)
    
    
    #combination feature and labels 
    samplesNum = len(x) * 1.0  #the numbers of training-samples
    train = np.hstack((x, y))
    valid = np.hstack((testX, testY[:, np.newaxis]))
    #training model
    nn.SGD(train, epoches=4, miniBatchSize=10, eta=0.5, validData=valid)



















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
        self.inputLayerNum = sizeList[0]
        self.outputLayerNum = sizeList[-1]        
        
    
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
        return a
    
    def SGD(self, trainData, epoches, miniBatchSize, eta, lamda, validData=None):
        '''
        eta: 学习速率
        epoches: 迭代次数
        miniBatchSize:采样的小样本数量
        lamda:惩罚系数
        '''
        
        self.samplesNum = len(trainData) 
        
        #init the plot-canvas
#        root = tk.Tk()
#        figure, canvas = plot.init(root)
#        accurates = []
        
        if validData is not None:
            validDataSamples = len(validData)
        trainDataSamples = len(trainData)
        
        
        for iterNum in range(epoches):
            np.random.shuffle(trainData)
            #如果 index+miniBatchSize > trainDataSamples, 默认使用 index+miniBatchSize = trainDataSamples
            miniBatchDatas = [trainData[index: index+miniBatchSize] for index in range(0, trainDataSamples, miniBatchSize)]
            
            for batchData in miniBatchDatas:
                self.updateParasByMiniBatch(batchData, eta, lamda)
                
            if validData is not None:
                accurate = self.evaluate(validData)/validDataSamples
                
#                accurates.append(accurate)
#                plot.reDraw(np.arange(iterNum+1), np.array(accurates), figure, canvas, epoches)
                
                #输出验证集的测试结果
#                print "Epoch {0}: {1} / {2}".format(iterNum, self.evaluate(validData), validDataSamples)
                print ("Epoch {0}:".format(iterNum), accurate)
            else:
                print ("Epoch {0} complete".format(iterNum))
                
#        root.mainloop()
    
    def updateParasByMiniBatch(self, miniBatchData, eta, lamda):
        nabla_b = [np.zeros(b.shape) for b in self.bias] #梯度
        nabla_w = [np.zeros(w.shape) for w in self.weights]#梯度
        
        #多进程并行计算
        pool = multiprocessing.Pool(processes=4)
        result = []
        for sample in miniBatchData:
            x = sample[: -self.outputLayerNum] #feature
            y = sample[-self.outputLayerNum: ] #label
            result.append(pool.apply_async(self.backprop, (x, y)))
        pool.close()
        pool.join()   
        
        #单个样本的梯度
        for res in result:
            delta_nabla_b, delta_nabla_w = res.get()
            #累计 miniBacth 梯度
            nabla_b = [nb+dnb for nb, dnb in zip(nabla_b, delta_nabla_b)]
            nabla_w = [nw+dnw for nw, dnw in zip(nabla_w, delta_nabla_w)]
            
        #use L2 punishment
        self.weights = [(1 - (eta * lamda)/self.samplesNum) * w-(eta/len(miniBatchData))*nw for w, nw in zip(self.weights, nabla_w)]
        self.bias = [b-(eta/len(miniBatchData))*nb for b, nb in zip(self.bias, nabla_b)]
            
    
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
        delta = self.costDerivative(activations[-1], y) * self.sigmoidDerivative(zs[-1]) #输出层误差
        
        #记录输出层梯度
        nabla_b[-1] = delta
        nabla_w[-1] = np.dot(delta[:, np.newaxis], activations[-2][:, np.newaxis].T)
        #返向计算各层的误差梯度
        for l in range(2, self.numLayers):
            z = zs[-l]
            sp = self.sigmoidDerivative(z)
            #误差
            delta = np.dot(self.weights[-l+1].transpose(), delta) * sp
            #梯度
            nabla_b[-l] = delta
            nabla_w[-l] = np.dot(delta[:, np.newaxis], activations[-l-1][:, np.newaxis].T)
        return nabla_b, nabla_w
        
    def costDerivative(self, outputActivations, y):
        """
        output_activations: 输出激活值(vector)
        使用二次代价函数， 1/2 * (outputActivations - y)^2
        返回损失函数对于激活输出值 outputActivations 的导数
        """
        return (outputActivations-y)
    
    
    def evaluate(self, validData):
        '''
        the index of max-value in output-layer is the prediction value
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
    
    def save(self, filename):
        """Save the neural network to the file ``filename``."""
        import json
        data = {"sizeList": self.sizeList,
                "weights": [w.tolist() for w in self.weights],
                "bias": [b.tolist() for b in self.bias]}
        f = open(filename, "w")
        json.dump(data, f)
        f.close()
    
    
def load(filename):
    """Load a neural network from the file ``filename``.  Returns an
    instance of Network.
    """
    import json
    f = open(filename, "r")
    data = json.load(f)
    f.close()
    net = Network(data["sizeList"])
    net.weights = [np.array(w) for w in data["weights"]]
    net.bias = [np.array(b) for b in data["bias"]]
    return net
    
       
#import Tkinter as tk
#import matplotlib
#matplotlib.use('TkAgg')
#from matplotlib.backends.backend_tkagg import FigureCanvasTkAgg
#from matplotlib.figure import Figure
import numpy as np
import multiprocessing
import time

#from P2 import loadData_mnistSelf as ld
from mlTools import mnistFileReader as ld
#from mlTools  import plot


if __name__ == "__main__":
    start = time.time()
    #load train and valid dataset
    x, y, testX, testY = ld.loadTrainAndTestDatasetWithLabelEncode()
    
    #create the network
    inputLayerNum = len(x[0])
    outputLayerNum = len(y[0])
    hideLayerNum = [40, 20, 40]
    hideLayerNum.insert(0, inputLayerNum)
    hideLayerNum.append(outputLayerNum)
    Layes = hideLayerNum
    
    nn = Network(Layes)
    
    #combination feature and labels 
    train = np.hstack((x, y))
    valid = np.hstack((testX, testY[:, np.newaxis]))
    print (train.shape, valid.shape)
    
    #training model
    nn.SGD(train, epoches=2, miniBatchSize=500, eta=2.0, lamda=0.1, validData=valid)
    
#    nn.save("model")
    
    end = time.time()
    print  ('during time', end - start)


















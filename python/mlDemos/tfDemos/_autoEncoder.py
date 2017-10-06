# -*- coding: utf-8 -*-
"""
Created on Tue Mar 14 15:51:01 2017

@author: root
"""
import numpy as np
import tensorflow as tf
from sklearn import preprocessing as prep
import loadData_mnistSelf as ld
import loadData_mnist as ld2

def xavier_init(inNums, outNums, constant=1):
    '''
    init weights-paras: shape(inNums,outNums)
    '''
    low = -constant * np.sqrt(6.0/(inNums + outNums))
    high = constant * np.sqrt(6.0/(inNums + outNums))
    return tf.random_uniform([inNums, outNums], minval=low, maxval=high, dtype=tf.float32)


class AdditiveGaussianNoiseAutoencoder(object):
    '''
    Args:
        n_input: the node-nums of input-layer \n
        n_hidden: the node-nums of hidden-layer \n
        transfer_function: the activate function \n
        scale: for generate noise data
    '''
    def __init__(self, n_input, n_hidden, transfer_function = tf.nn.softplus, optimizer = tf.train.AdamOptimizer(), scale = 0.1):
        self.n_input = n_input
        self.n_hidden = n_hidden
        self.transfer = transfer_function
        self.scale = tf.placeholder(tf.float32)
        self.training_scale = scale
        network_weights = self._initialize_weights()
        self.weights = network_weights
        # model
        ###input data of input-layer
        self.x = tf.placeholder(tf.float32, [None, self.n_input])
        ###output data of hidden-layer
        self.hidden = self.transfer(tf.add(tf.matmul(self.x + scale * tf.random_normal((n_input,)), self.weights['w1']), self.weights['b1']))
        ###input-data of output-layer
        self.reconstruction = tf.add(tf.matmul(self.hidden, self.weights['w2']), self.weights['b2'])
        ###loss function: order to keep same of input and output layer
        self.cost = 0.5 * tf.reduce_sum(tf.pow(tf.sub(self.reconstruction, self.x), 2.0))
        self.optimizer = optimizer.minimize(self.cost)
        init = tf.global_variables_initializer()
        self.sess = tf.Session()
        self.sess.run(init)
        
    def _initialize_weights(self):
        all_weights = dict()
        all_weights['w1'] = tf.Variable(xavier_init(self.n_input, self.n_hidden))
        all_weights['b1'] = tf.Variable(tf.zeros([self.n_hidden], dtype = tf.float32))
        all_weights['w2'] = tf.Variable(tf.zeros([self.n_hidden, self.n_input], dtype = tf.float32))
        all_weights['b2'] = tf.Variable(tf.zeros([self.n_input], dtype = tf.float32))
        return all_weights


    def partial_fit(self, X):
        '''
        minBatch samples training
        '''
        cost, opt = self.sess.run((self.cost, self.optimizer), feed_dict = {self.x: X, self.scale: self.training_scale})
        return cost

    def calc_total_cost(self, X):
        '''
        execute for test
        '''
        return self.sess.run(self.cost, feed_dict = {self.x: X, self.scale: self.training_scale})

    def transform(self, X):
        '''
        the output of hidden-layer
        '''
        return self.sess.run(self.hidden, feed_dict = {self.x: X, self.scale: self.training_scale})

    def generate(self, hidden = None):
        '''
        the input data of output-layer and in autoEncoder is also the output data of output-layer\n
        hidden-layer is fixed or random generate
        '''
        if hidden is None:
            hidden = np.random.normal(size = self.weights["b1"])
        return self.sess.run(self.reconstruction, feed_dict = {self.hidden: hidden})

    def reconstruct(self, X):
        '''
        transform and generate
        '''
        return self.sess.run(self.reconstruction, feed_dict = {self.x: X,self.scale: self.training_scale})

    def getWeights(self):
        return self.sess.run(self.weights['w1'])

    def getBiases(self):
        return self.sess.run(self.weights['b1'])
        
        
def nextBatch(x, minBatch=50):
    indices = np.random.randint(0, len(x), minBatch)
    return x[indices]

if __name__ == "__main__":
#    true_x, true_y, tx, ty = ld.loadTrainAndTestDatasetWithLabelEncode()
    true_x, true_y, tx, ty = ld2.mainWithLabelEnocde()
    featureNums = len(true_x[0]) #the number of features
    labelNums = len(true_y[0]) #the number of labels
    clf = prep.StandardScaler()
    clf = clf.fit(true_x)
    true_x = clf.transform(true_x)
    tx = clf.transform(tx)
    alpha = 0.001
    n_hidden = 200
    scale = 0.01
    bacthSize = 128
    epochs = 20
    #autoEncoder machine
    autoEncoder = AdditiveGaussianNoiseAutoencoder(n_input=featureNums, n_hidden=n_hidden, transfer_function = tf.nn.softplus, optimizer = tf.train.AdamOptimizer(alpha), scale=scale)
    #training
    avgCost = 0.0
    totalBatches = int(len(true_x)/bacthSize)
    with tf.device("/cpu:0"):
        for i in range(epochs*totalBatches):
            batch_x = nextBatch(true_x, bacthSize)
            cost = autoEncoder.partial_fit(batch_x)
            avgCost = (avgCost*i*bacthSize + cost)/(bacthSize*(i+1))
            if i % 10 == 0:
                print i, avgCost
    print 'test', autoEncoder.calc_total_cost(tx)
    

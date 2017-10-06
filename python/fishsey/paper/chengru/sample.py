# -*- coding: utf-8 -*-
"""
Created on Tue Mar  7 15:15:27 2017

@author: root
"""
import numpy as np
import tensorflow as tf
import random

def sample():
    train = open(trainFile, 'w')
    test = open(testFile, 'w')
    
    count = 0
    with open(originFile) as pf:
        for line in pf:
            count += 1
            print count
            if random.randint(1,10) >= 8:
                train.write(line)
            else:
                test.write(line)
                
    train.close()
    test.close()
                

if __name__ == "__main__":
    originFile = "/root/AAA/dataset/ml/ml-1m/ratings.dat"
    trainFile = "/root/AAA/dataset/ml/ml-1m/traing"
    testFile = "/root/AAA/dataset/ml/ml-1m/test"
    sample()
    
    

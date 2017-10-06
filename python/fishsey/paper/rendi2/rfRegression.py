# -*- coding: utf-8 -*-
"""
Created on Wed Nov  9 16:36:08 2016

@author: root
"""

from paper.rendi2 import loadDataset as ld
from sklearn.ensemble import AdaBoostClassifier, AdaBoostRegressor, RandomForestRegressor, RandomForestClassifier
from sklearn.model_selection import GridSearchCV
from sklearn.tree import DecisionTreeClassifier, DecisionTreeRegressor
from sklearn.naive_bayes import GaussianNB
import numpy as np
import time
start = time.time()


#load the prob dataset
trainProb = "dataset/rendi2/training-0501-after-prob"
testProb =  "dataset/rendi2/test-0501-after-prob"
x = ld.loadTestList(trainProb)
testX = ld.loadTestList(testProb)

#load the qos dataset
train = "dataset/rendi2/training1.txt"
test =  "dataset/rendi2/test1.txt"
y = ld.loadTestList(train)[:, -1]
testY = ld.loadTestList(test)[:, -1]

clf = RandomForestRegressor(oob_score=True, n_jobs=20, n_estimators=100, max_features=0.1, min_samples_split=10)
clf.fit(x, y.astype(float))
    
preY = clf.predict(testX)

print np.mean(abs(preY - testY))


            
#end modeling
print "during time ... " , time.time() - start
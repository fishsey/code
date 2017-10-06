# -*- coding: utf-8 -*-
"""
Created on Sun Mar 06 10:17:13 2016

@author: fishsey
"""  
#缺省值设定为 111
def normAyyayByRow(arrayObj):
    arrayObjCopy = np.array(copy.deepcopy(arrayObj), dtype=float)
    rowNum, columnNum = arrayObjCopy.shape
    for index in range(rowNum):
        row = arrayObjCopy[index]
        maxValue = calMax(row)
        minValue = calMin(row)
        arrayObjCopy[index] = norm(row, maxValue, minValue)
    return arrayObjCopy
    	
def calMax(row):
    row = np.extract(row != 111, row)
    if len(row) == 0:
        return 1.0
    return np.max(row)
    
def calMin(row):
    row = np.extract(row != 111, row)
    if len(row) == 0:
        return 0.0
    return np.min(row)

def norm(row, maxValue, minValue):
    indice = np.where(row!=111)
    row[indice] = (row[indice] - minValue) / float(maxValue - minValue)
    return row


import numpy as np
import copy
if __name__ == '__main__': 
    a = np.array([[1, 2, 3, 4, 111], [5, 6, 111, 7, 8]])
    print normAyyayByRow(a)
   
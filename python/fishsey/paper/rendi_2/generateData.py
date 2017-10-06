# -*- coding: utf-8 -*-
"""
Created on Wed Jan 11 21:15:34 2017

@author: root
"""
import numpy as np
NoneValue = 111111.0
import random

def createArrayObj(fileName=r'sample/training10-1.txt', userNum=339, wsNum=5825):
    trainObj = np.loadtxt(fileName)
    userLs, wsLs, rt  = trainObj[:, 0], trainObj[:, 1], trainObj[:, 2]
    userLs = np.array(userLs, dtype=int) - 1
    wsLs = np.array(wsLs, dtype=int) - 1
    rt = np.array(rt, dtype=float)
    arrayObj = np.empty((userNum, wsNum))
    arrayObj.fill(NoneValue)
    arrayObj[userLs, wsLs] = rt 
    return arrayObj  
    
    
    
def randomNum(row, column):
    row = row[row != NoneValue]
    if len(row) != 0:
        return random.choice(row)
    column = column[column != NoneValue]
    if len(column) != 0:
        return random.choice(column)
    return np.random.normal(2.7, 3.4)

def randomNum2(data):
   return random.choice(data)
   

def fullfill(dataArray):
    d2 = dataArray[dataArray!=NoneValue]
    for userNum in range(339):
        print userNum
        for wsNum in range(5825):
            if dataArray[userNum, wsNum] != NoneValue:
                continue
            else:
                if np.random.randint(0, 10) < 1:
                    dataArray[userNum, wsNum] = randomNum2(d2)
#                    dataArray[userNum, wsNum] = randomNum(dataArray[userNum], dataArray[:, wsNum])


def saveToFile(dataArray):
    temp = []
    for userNum in range(339):
        print userNum
        for wsNum in range(5825):
            if dataArray[userNum, wsNum] == NoneValue:
                continue
            else:
                temp.append([userNum, wsNum, dataArray[userNum, wsNum]])
    np.savetxt("/root/AAA/dataset/rendi2/t3-5-1", np.array(temp, str), delimiter='\t', fmt='%s')
                
    
if __name__ == "__main__":
    fn = "/root/AAA/dataset/rendi2/temp25-1"
    dataArray = createArrayObj(fn)
    fullfill(dataArray)
    saveToFile(dataArray)
    
# -*- coding: utf-8 -*-
"""
Created on Mon Nov  7 10:44:26 2016

@author: root
"""
NoneValue = 111111
#创建矩阵   
def loadArrayObj(fileName, userNum=339, wsNum=5825, modify=True):
    import numpy as np
    trainObj = np.loadtxt(fileName)
    userLs, wsLs, rt  = trainObj[:, 0], trainObj[:, 1], trainObj[:, 2]
    
    userLs = np.array(userLs, dtype=int)
    wsLs = np.array(wsLs, dtype=int)
    
    if modify:
        userLs -= 1
        wsLs -= 1
        
    rt = np.array(rt, dtype=float)
    arrayObj = np.empty((userNum, wsNum))
    arrayObj.fill(NoneValue)
    arrayObj[userLs, wsLs] = rt 
    return arrayObj 
    
def loadTestList(fileName, modify=True):
    import numpy as np
    result = np.loadtxt(fileName)
    if modify:
        result[:, 0] -=  1 #modify index start with 0
        result[:, 1] -=  1
    return result   
    
def generateIndexArray(fileName, userNum=339, wsNum=5825, modify=True):
    import numpy as np
    data = loadTestList(fileName, modify=modify)
    result = np.empty((userNum, wsNum))
    result.fill(NoneValue)
    for index, line in enumerate(data):
        user, ws, tui = line
        user = int(user)
        ws = int(ws)
        result[user, ws] = index
    return result
    
    
#==============================================================================
# with labelEncoder hander
#==============================================================================
def loadData(trainFile, testFile):
    import numpy as np
    from sklearn.preprocessing import LabelEncoder
    train = np.loadtxt(trainFile, dtype=str, delimiter='\t')
    test = np.loadtxt(testFile, dtype=str, delimiter='\t')
    le = LabelEncoder()
    for i in range(len(train[0]) - 1):
        le.fit(train[:, i])
        train[:, i] = le.transform(train[:, i])
        test[:, i] = le.transform(test[:, i])
    return train[:, :-1].astype(float), train[:, -1].astype(float), test[:, :-1].astype(float), test[:, -1].astype(float)


#==============================================================================
# with labelEnocdr and OneHotEncoder Hander
#==============================================================================
def loadDataWithOneHot(trainFile, testFile):
    from sklearn.preprocessing import OneHotEncoder, LabelEncoder
    import numpy as np
    train = np.loadtxt(trainFile, dtype=str, delimiter='\t')
    test = np.loadtxt(testFile, dtype=str, delimiter='\t')
    onc = OneHotEncoder()
    le = LabelEncoder()
    for i in range(len(train[0]) - 1):
#        le.fit(train[:, i])
        le.fit(np.hstack((train[:, i], test[:, i])))
        train[:, i] = le.transform(train[:, i])
        test[:, i] = le.transform(test[:, i])

#    onc.fit(train[:, :-1])
    onc.fit(np.vstack((train[:, :-1], test[:, :-1])))
    trainX = onc.transform(train[:, :-1])
    testX = onc.transform(test[:, :-1])
    return trainX.astype(float), train[:, -1].astype(float), testX.astype(float), test[:, -1].astype(float)
    
if __name__ == "__main__":
    train = "dataset/rendi2/training-0501-after"
    test =  "dataset/rendi2/test-0501-after"
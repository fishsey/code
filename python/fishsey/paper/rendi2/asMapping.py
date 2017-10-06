# -*- coding: utf-8 -*-
"""
Created on Mon Nov  7 10:25:06 2016

@author: root
"""


def loadMapping():
    '''
    shape is (n, 2) ---(n, (AS, Country))
    '''
    import numpy as np
    userAsAndCountry = np.loadtxt(userInfos, dtype=str, usecols=(2, 4), delimiter="\t")
    wsAsAndCountry = np.loadtxt(wsInfos, dtype=str, usecols=(2, 4), delimiter="\t")
    return userAsAndCountry, wsAsAndCountry
    

def clusterAsOrCountry(infos, index):
    import numpy as np
    indexSet = set(infos[:, index])
    result = {}
    for elem in indexSet:
        result[elem] = np.argwhere(infos[:, index] == elem)[:, -1]
    return result
    
userInfos = "dataset/rendi2/userMapping.txt"
wsInfos = "dataset/rendi2/webServerMapping.txt"
if __name__ == "__main__":
    userAsAndCountry, wsAsAndCountry = loadMapping()
#    userAsClusters = clusterAsOrCountry(userAsAndCountry, 0)
#    userCountryClusters = clusterAsOrCountry(userAsAndCountry, 1)
    wsAsClusters = clusterAsOrCountry(wsAsAndCountry, 0)
#    wsCountryClusters = clusterAsOrCountry(wsAsAndCountry, 1)
    

    
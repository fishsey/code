# -*- coding: utf-8 -*-
"""
Created on Wed Mar 23 16:38:55 2016

@author: fishsey
"""
def createClusters(fileName):
    '''进行 as\city\country 聚类, 如果该id的as\city\country = "not found" 则放入'not found'聚类中\n
    {as: [id, id, ...], ...}\n
    {city: [id, id, ...], ...}\n
    {country: [id, id, ...], ...}\n
    id: int
    '''
    asClusters = {}
    cityCluseters = {}
    countryCluseters = {}
    with open(fileName, 'r') as pf:
        for line in pf:
            IdNum, Ip, asNum, city, country = line.strip().split('\t')
            IdNum = int(IdNum)
            asClusters.setdefault(asNum,[])
            cityCluseters.setdefault(city,[])
            countryCluseters.setdefault(country,[])
            asClusters[asNum].append(IdNum)
            cityCluseters[city].append(IdNum)
            countryCluseters[country].append(IdNum) 
    return asClusters, cityCluseters, countryCluseters

 
def createMapping(fileName, numbers):
    '''创建 id 与 AS\city\country 的映射：[[as, city, country], ...]
    返回嵌套的列表\n
        Args:
            numbers: int, 用户/服务的数目
    '''
    listObj = [[]] * numbers
    with open(fileName, 'r') as pf:
        for line in pf:
            IdNum, Ip, asNum, city, country = line.strip().split('\t')
            IdNum = int(IdNum)
            temp = [asNum, city, country]
            listObj[IdNum] = temp
    return listObj
    
def loadUserData(userNum=339, userMappingFile = r'dataSet/userMapping.txt'):
    userMapping = createMapping(userMappingFile, userNum)
    userAsClusters, userCityCluster, userCountryCluster = createClusters(userMappingFile)
    return userMapping, userAsClusters, userCityCluster, userCountryCluster
    
def loadWsData(wsNum=5825, wsMappingFile = r'dataSet/webServerMapping.txt'):
    wsMapping = createMapping(wsMappingFile, wsNum)
    wsAsClusters, wsCityCluster, wsCountryCluster = createClusters(wsMappingFile) 
    return wsMapping, wsAsClusters, wsCityCluster, wsCountryCluster
  


  

 



    

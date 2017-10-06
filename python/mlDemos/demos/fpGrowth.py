'''
Created on Jun 14, 2011
FP-Growth FP means frequent pattern
the FP-Growth algorithm needs: 
1. FP-tree (class treeNode)
2. header table (use dict)

This finds frequent itemsets similar to apriori but does not 
find association rules.  

@author: Peter
'''
from timer import timerFunc
class treeNode:
    def __init__(self, nameValue, numOccur, parentNode):
        self.name = nameValue
        self.count = numOccur
        self.nodeLink = None  #link to samle node
        self.parent = parentNode  #needs to be updated
        self.children = {} 
    def inc(self, numOccur):
        self.count += numOccur
    def disp(self, ind=1):
        print '  '*ind, self.name, ' ', self.count
        for child in self.children.values():
            child.disp(ind+1)
        
def ascendTree(leafNode, prefixPath): #ascends from leaf node to root
    if leafNode.parent != None:
        prefixPath.append(leafNode.name)
        ascendTree(leafNode.parent, prefixPath)
    
def findPrefixPath(basePat, treeNode): #treeNode comes from header table
    condPats = {}
    while treeNode != None:
        prefixPath = []
        ascendTree(treeNode, prefixPath)
        if len(prefixPath) > 1: 
            condPats[frozenset(prefixPath[1:])] = treeNode.count
        treeNode = treeNode.nodeLink
    return condPats
    
def mineTree(inTree, headerTable, minSup, preFix, freqItemList):
    #sort header table:ascend
    bigL = [v[0] for v in sorted(headerTable.items(), key=lambda p: p[1])]
    #start from bottom of header table
    for basePat in bigL: 
        newFreqSet = preFix.copy()
        newFreqSet.add(basePat)
        freqItemList.append(newFreqSet)
        #get preFixPath for basePat
        condPattBases = findPrefixPath(basePat, headerTable[basePat][1])
        #construct FP-tree
        myCondTree, myHead = createTree(condPattBases, minSup)
        #recur mine FP-tree
        if myHead != None: 
            mineTree(myCondTree, myHead, minSup, newFreqSet, freqItemList)

def createInitSet(dataSet):
    retDict = {}
    for trans in dataSet:
        retDict[frozenset(trans)] = 1 # immutable set
    return retDict
    
def createTree(dataSet, minSup=1): 
    headerTable = {}
    #go over dataSet twice
    ##first: 
    ###counts occurance frequency of each item
    for trans in dataSet:
        for item in trans:
            headerTable[item] = headerTable.get(item, 0) + dataSet[trans]
    ###remove items less than minSup(not frequently item)
    for k in headerTable.keys():
        if headerTable[k] < minSup: 
            del(headerTable[k])
            
    freqItemSet = set(headerTable.keys())
    if len(freqItemSet) == 0: 
        return None, None  
    for k in headerTable:
        #reformat[counts, nodelink] headerTable to use Node link 
        headerTable[k] = [headerTable[k], None] 
        
    #FP tree: init as a null node
    retTree = treeNode('Null Set', 1, None) #create tree
    
    #second: go through dataset 2nd time
    for tranSet, count in dataSet.items():  
        localD = {}
        #sort each line
        for item in tranSet:  
            if item in freqItemSet:
                localD[item] = headerTable[item][0]
        if len(localD) > 0:
            orderedItems = [v[0] for v in sorted(localD.items(), key=lambda p: p[1], reverse=True)]
            updateTree(orderedItems, retTree, headerTable, count)
    return retTree, headerTable #return tree and header table
    
def updateTree(items, inTree, headerTable, count):
    #check if orderedItems[0] in retTree.children
    if items[0] in inTree.children:
        inTree.children[items[0]].inc(count) #incrament count
    else:   #add items[0] to inTree.children
        inTree.children[items[0]] = treeNode(items[0], count, inTree)
        if headerTable[items[0]][1] == None: #update header table 
            headerTable[items[0]][1] = inTree.children[items[0]]
        else:
            updateHeader(headerTable[items[0]][1], inTree.children[items[0]])
    #call updateTree() with remaining ordered items
    if len(items) > 1:
        updateTree(items[1::], inTree.children[items[0]], headerTable, count)
        
def updateHeader(nodeToTest, targetNode):   
    while (nodeToTest.nodeLink != None):    
        nodeToTest = nodeToTest.nodeLink
    nodeToTest.nodeLink = targetNode

@timerFunc
def cal():
    #load dataset
    dataFile = "D:/baiduYun/syn/Github/dataset/fpgrowth.dat"
    data = [line.strip().split() for line in open(dataFile).readlines()]
    #trans each line into a immutable set, no duplication elem
    initTree = createInitSet(data)
    retTree, headerTable = createTree(initTree, 1e5)
    fqItems = [] #result of  feqItems
    preFix = set([]) #for recur mini to record preFixPath
    mineTree(retTree, headerTable, 1e5, preFix, fqItems)
    return fqItems
    
if __name__ == "__main__":
    fqItems = cal()
    
    

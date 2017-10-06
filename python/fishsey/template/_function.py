# -*- coding: utf-8 -*-
"""
Created on Mon Dec 26 21:13:34 2016

@author: root
"""

def selfReduce(function, lists, initial=None):
    '''
    self definition Reduce
    '''
    result = initial
    if result is None:
        result = lists[0]
    for elem in lists[1:]:
        result = function(result, elem)
    return result



class filterHelper:
    def __init__(self, filterValue):
        self.filterValue = filterValue
    def isContain(self, value):
        return value - self.filterValue  #false if equal, else true
def selfFilter(lst, filterValue):
    '''
    use closure(sideFilter) instead of lamda-function
    filter the specify value
    '''
    sideFilter = filterHelper(filterValue).isContain
    return filter(sideFilter, lst)
    
def f1():
    '''
    is a another example of closure fucntion
    '''
    global n
    n = 1
    #is a closure, define in a function and use(but can not change) his var
    #if must modify, use global
    def inner():
        global n
        n = 3 #if not global, will cover the var(outer function) 
        print n
    inner()
    print n
    n = 2
    inner()

if __name__ == "__main__":
    import numpy as np
    import functools
    print selfReduce(lambda x, y: x+y, range(10))
    print reduce(np.add, range(10))
    
    print selfFilter(range(10), 5)
    
    f1()
    
    newFunc = functools.partial(selfFilter, filterValue=4)
    print newFunc(range(10))
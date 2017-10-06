# -*- coding: utf-8 -*-
"""
Created on Tue Dec 27 11:27:48 2016

@author: root
"""
import unittest
class selfRange(object):
    def __init__(self, *args):
        if len(args) == 1: 
            self.begin = 0
            self.end = args[0]
        else:
            self.begin = args[0]
            self.end = args[1]   
    
    def __iter__(self):
        print '__iter__ called'
        return self
    
    def next(self):
        print "__next__ called"
        if self.begin >= self.end:
            raise StopIteration
        else:
            self.begin += 1
            return self.begin - 1
    
    def __call__(self):
        print "__call__ called"
#        return next(self)
        if self.begin >= self.end:
            raise StopIteration
        else:
            self.begin += 1
            return self.begin - 1

class selfRange2(object):
    def __init__(self, *args):
        if len(args) == 1: 
            self.begin = 0
            self.end = args[0]
        else:
            self.begin = args[0]
            self.end = args[1]   
    
    def __getitem__(self, index):
        print '__getitem__ called'
        if index  >= self.end - self.begin:
            raise IndexError
        else:
            return index + self.begin
    
 
class testClass(unittest.TestCase): 
    def testSelfRange0(self):
        print '... 0'
        var = selfRange(-2, 2)
#        ite = iter(var, 4)
        ite = iter(var)
        for i in ite:
            print i
       
    def testSelfRange1(self):
        print '... 1'
        var = selfRange(-2, 2)
        var()
        print '---------------'
        print 
        
    def testSelfRange2(self):
        print '... 2'
        var = selfRange2(-2, 2)
        for i in var:
            print i
        

if __name__ == '__main__':
    unittest.main()
    
    
    
    
    
        
    

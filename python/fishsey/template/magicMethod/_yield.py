# -*- coding: utf-8 -*-
"""
Created on Wed Dec 28 09:53:12 2016

@author: root
"""


def testYield():
    print 's1'
    yield 1
    print 's2'
    yield 2












import unittest
class testClass(unittest.TestCase): 
    def testSelfRange0(self):
        #yd is a generator and has next() method
        yd = testYield()
        print yd.next()#first call next(), code begin execute until yield(include)
        print 
        print yd.next()#from last-position code to next yield(include)
        
        

if __name__ == '__main__':
    unittest.main()
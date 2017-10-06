# -*- coding: utf-8 -*-
"""
Created on Tue Jan 12 14:06:50 2016

@author: fishsey
"""
import unittest

def testMethod(a=5,b=4):
    print a / b

class testClass(unittest.TestCase):
    def test(self):
        testMethod(2,3)
    def test2(self):
        testMethod('hello',3)
    def test3(self):
        testMethod(5,0)
    def setUp(self): #在每个测试开始之前各执行一次
        print 'test is begining:'
    def tearDown(self):#在每个测试结束之后各执行一次
        print 'test is end'
        
# unittest.main()会首先实例化所有unittest.TestCase的子类，
# 然后执行所有以test开头的方法（包括test（））
# setUp()和tearDown（）方法在每个测试的开始前和结合后各执行一次
if __name__ == '__main__':
    unittest.main()
   
    
    
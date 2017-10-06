# -*- coding: utf-8 -*-
"""
Created on Wed Feb 15 19:32:42 2017

@author: root
"""

def comb(m, n):
    upper = 1.0
    bottom = 1.0
    line = m-n+1
    
    while m >= line:
        upper *= m
        m -= 1.0
    
    while n > 0:
        bottom *= n
        n -= 1.0
        
    return upper/bottom



num = 339.0
k = 17.0
print comb(num, k)
#print (num ** 2 - comb(num, k)*comb(num-k, k))/(num ** 2)
        
        
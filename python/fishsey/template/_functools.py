# -*- coding: utf-8 -*-
"""
Created on Tue Dec 27 10:10:45 2016

@author: root
"""
import functools
import doctest


@functools.total_ordering
#must implement __eq__ and less one of (__le__,lt, ge, gt)
class obj:
    '''
    with ELLIPSIS, use ... for ignore 
    >>> o4 = obj(4); o4 #doctest: +ELLIPSIS
    <__main__.obj instance at 0x...>
    >>> print o4 >= 4 
    True
    >>> print o4 <= 4 
    True
    >>> print o4 == 4
    True
    >>> print o4 > 4
    False
    >>> print o4 < 4
    False
    '''
    def __init__(self, num):
        self.num = num
        
    def __eq__(self, nums):
        return self.num == nums
        
    def __le__(self, nums):
        return self.num <= nums 
    


if __name__ == "__main__":
#    python -m doctest moduleName.py -v
    doctest.testmod()


    
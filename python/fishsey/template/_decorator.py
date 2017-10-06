# -*- coding: utf-8 -*-
"""
Created on Tue Dec 27 09:42:32 2016

@author: root
"""
import functools

def newNow(func):
    @functools.wraps(func)#make the __name__ of func is origin-name, not wrapper
    def wrapper(*args, **kw):
        print "this is new function for ", func.__name__
        return func(*args, **kw)
    return wrapper

def newNow2(text):
    def decorator(func):
        @functools.wraps(func) #make the __name__ of func is origin-name, not wrapper
        def wrapper(*args, **kw):
            print "this is a decorator with paras for ", func.__name__, text
            return func(*args, **kw)
        return wrapper
    return decorator
    
@newNow
def now():
    '''
    newNow is a decorator,  now  equal newNow(now)
    '''
    import time
    print time.ctime(time.time())

@newNow2("test")
def now2():
    '''
    newNow2 is a decorator,   now2 equal newNow2("text")(now2)
    '''
    import time
    print time.ctime(time.time())

    
    
if __name__ == "__main__":
    now()
    print now.__name__
    print 
    now2()
    print now2.__name__
    

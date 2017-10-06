# -*- coding: utf-8 -*-
"""
Created on Wed Dec 28 14:31:27 2016

@author: root
"""

def timerFunc(func):
    '''
    return the runtime of function-func
    '''
    import functools
    @functools.wraps(func)
    def wrapper(*args, **keywords):  
        import time
        start = time.time()
        func(*args) #origin function
        end = time.time()
        print  ('during time', end - start)
    return wrapper


@timerFunc
def func1(*args, **keywords):
    for i in range(pow(args[0], args[1])):
        pass


if __name__ == "__main__":
    func1(10, 7)
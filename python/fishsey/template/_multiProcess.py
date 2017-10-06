# -*- coding: utf-8 -*-
"""
Created on Fri Oct  6 12:53:09 2017

@author: fishsey
"""

import multiprocessing
import time
from timer import  timerFunc

def func(msg):
  for i in range(3):
    print msg
    time.sleep(1)
  return msg, msg+"2"

@timerFunc
def main_1():
    for i in range(10):
        msg = "hello %d" % (i)
        func(msg)
        
@timerFunc
def main_2():
    pool = multiprocessing.Pool(processes=4)
    result = []
    for i in range(2):
        msg = "hello %d" % (i)
        result.append(pool.apply_async(func, (msg, )))
    pool.close()
    pool.join()
    for res in result:
        print res
        a, b = res.get()
    print "Sub-process(es) done."
    
if __name__ == "__main__":
    main_2()
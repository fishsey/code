# -*- coding: utf-8 -*-
"""
Created on Fri Jan 13 11:05:37 2017

@author: root
"""



def mae1():
    import numpy as np
    alpha = 0.5
    for sparess in [5, 10, 15, 20]:
        eui = []
        for fileNum in range(1, 11): 
            euiSlope = "/root/AAA/dataset/rendi2/eui/euiSlopeone-%d-%d" % (sparess, fileNum)
            euiAdaboost = "/root/AAA/dataset/rendi2/eui/euiAdaboost-%d-%d" % (sparess, fileNum)
            euiSlope = np.loadtxt(euiSlope)
            euiAdaboost = np.loadtxt(euiAdaboost)
            print np.mean(abs(euiSlope)),
            print np.mean(abs(euiAdaboost)),
            mae =  np.mean(abs(euiAdaboost * alpha + euiSlope * (1 - alpha)))
            lens = len(euiSlope)
            eui.append([mae, lens])
            print mae
        np.savetxt("/root/AAA/dataset/rendi2/eui/eui2-%d" % (sparess), np.array(eui, dtype=str), fmt='%s', delimiter='\t')
#        break

def mae2():
    import numpy as np
    for sparess in [5, 10, 15, 20]:
        eui1 = "/root/AAA/dataset/rendi2/eui/eui-%d" % (sparess)
        eui2 = "/root/AAA/dataset/rendi2/eui/eui2-%d" % (sparess)
        eui1 = np.loadtxt(eui1)
        eui2 = np.loadtxt(eui2)
        maeSums = eui1[:, 0] * eui1[:, 1] + eui2[:, 0] * eui2[:, 1]
        nums = eui1[:, 1] +  eui2[:, 1]
#        print maeSums/nums
        print np.mean(maeSums/nums)
#        print nums
        print 
#        break
    
if __name__ == "__main__":
#    mae1()
    mae2()
    
            

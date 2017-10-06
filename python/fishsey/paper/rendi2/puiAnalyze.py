# -*- coding: utf-8 -*-
"""
Created on Fri Nov 11 18:44:10 2016

@author: root
"""
NoneValue = 111111

if __name__ == "__main__":
    from paper.rendi2 import loadDataset as ld
    import numpy as np
    import time
    start = time.time()
    for sparess in [5, 10, 15, 20]:
        for fileNum in range(1, 11):  
            euPuiFile = "dataset/rendi2/pui/euPui%d-%d" % (sparess, fileNum)
            euPui = np.loadtxt(euPuiFile)
            dbscanPuiFile = "dataset/rendi2/pui/dbscanPui%d-%d" % (sparess, fileNum)
            dbscanPui = np.loadtxt(dbscanPuiFile)
            testFile = "dataset/rendi2/test/sparseness%s/test%d.txt" % (sparess, fileNum)      
            
            testData = ld.loadTestList(testFile)
            
#            euPui[euPui==NoneValue] = dbscanPui[euPui==NoneValue]
#            dbscanPui[dbscanPui==NoneValue] = euPui[dbscanPui==NoneValue]
            #cal 
            puiIng = []
            eui = 0.0
            count = 0.0
            alpha = 0.5
            for index, line in enumerate(testData):
                user, ws, tui = line
                user = int(user)
                ws = int(ws)
                if euPui[index] == NoneValue and dbscanPui[index] == NoneValue:
                    puiIng.append([user+1, ws+1, tui]) #modify from 1
                else:
                    count += 1.0
                    if euPui[index] == NoneValue:
                        pui = dbscanPui[index]
                    elif dbscanPui[index] == NoneValue:
                        pui = euPui[index]
                    else:
                        pui = alpha * euPui[index] + (1-alpha) * dbscanPui[index]
                    eui += abs(tui - pui)
            puiIng = np.array(puiIng, dtype=float)
            np.savetxt('dataset/rendi2/puiAnalyze/puiAnalyze-%d-%d' % (sparess, fileNum), puiIng, delimiter='\t', fmt='%f')
            print sparess, fileNum, '\t', len(testData) - count, count, '\t', eui/count
#            break
#        break
            
#            print np.mean(puiIng[:, -1]), '\t', 
#            print len(puiIng[puiIng == -1]), '\t', len(puiIng)
        print 
                        
                    
                    
                    
                
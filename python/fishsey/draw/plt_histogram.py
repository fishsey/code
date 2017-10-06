# -*- coding: utf-8 -*-
"""
Created on Thu Mar 24 13:58:33 2016

@author: fishsey
"""
PATH = 'F:/baiduYun/Syn/dataset'

def drawHistogramWithBestFitLine(dataset, num_bins, mu=0.811, std=1.967):
    import numpy as np
  
    #分别返回 y坐标、x坐标、绘图信息列表
    plt.hist(dataset, num_bins, normed=1, facecolor='green', alpha=0.5)
    
    plt.xlabel(u"服务质量(响应时间)",fontproperties='SimHei')
    plt.ylabel(u'概率',fontproperties='SimHei')
    plt.title(u'均值 = %.3f, 标准差 = %.3f' % (mu, std),fontproperties='SimHei')
    
#    plt.xlabel(u"QoS(Response Time)")
#    plt.ylabel(u'Probability')
#    plt.title(r'$\mu=%.3f$, $\sigma=%.3f$' % (mu, std))
    
#    plt.savefig('paper1/dataDistru_1.png',dpi=1600)
#    plt.show()

import numpy as np
import matplotlib.pyplot as plt
import math
if __name__ == '__main__':
    sparseness = 'sparseness20'
    i = 1
    fileName =  PATH + '/paper1/rt/%s/test%d.txt' % (sparseness,i)
    dataset = np.loadtxt(fileName)[:,-1]
    mu = np.mean(np.array(dataset))
    std = np.std(np.array(dataset))
    drawHistogramWithBestFitLine(dataset, 50, mu, std)
    
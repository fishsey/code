# -*- coding: utf-8 -*-
"""
Created on Tue Jun 28 09:26:03 2016

@author: fishsey
"""


def plt_MinSample():
    from matplotlib import pyplot as plt 
    import numpy as np
    fileName=r'eui/leaf.txt'
    data = np.loadtxt(fileName)[:, 1].reshape(4, 10)
    x = np.arange(20, 201, 20)
    i = 0
    y = data[i]
    y = y / 0.879
    #plt chiness
    plt.grid(True)
    plt.plot(x, y, 'D-', label=u'Response Time')
#    plt.legend(loc='center right', shadow=True, fontsize='x-large', prop={'family':'SimHei'})
#    plt.legend(loc='center right', shadow=True, fontsize='large')
    plt.xlabel(u'最小分割样本数',fontproperties='SimHei', fontsize='medium')
    plt.ylabel(u'归一化平均绝对误差', fontproperties='SimHei', fontsize='medium')
    plt.xticks(np.linspace(20, 200, 10))
    plt.yticks(np.linspace(np.min(y)-0.05, np.max(y)+0.05, 11))
    plt.title(u"训练矩阵密度 = 5%" , fontproperties='SimHei', fontsize='medium')
    plt.savefig('sample-5.png', dpi=1600)
#    plt.subplots_adjust(left=0.2)
#    plt.axis('auto')
    plt.show()
    
def plt_sparess_rt(fileName=r'eui/spar.txt'):
    from matplotlib import pyplot as plt 
    import numpy as np
    width = 0.005
    rt = np.loadtxt(fileName)[:, 1].reshape(3, 10)/ 0.811
    x = np.linspace(0.02, 0.20, 10)
    plt.xticks(x)
    plt.xlim(0, 0.22)
    plt.ylim(0, 0.9)
    plt.bar(x-width-0.003, rt[0], width=width, color='black',label='min_samples_split = 80',alpha=1.0)
    plt.bar(x-0.003, rt[1], width=width, color='blue',label='min_samples_split = 100',alpha=0.8)
    plt.bar(x+width-0.003, rt[2], width=width, color='lightslategrey',label='min_samples_split = 120',alpha=0.3)
    plt.legend(loc='upper right', shadow=True, fontsize='medium')
    plt.xlabel(u'训练矩阵密度', fontproperties='SimHei')
    plt.ylabel(u'归一化平均绝对误差', fontproperties='SimHei')
    plt.savefig('spra2.png', dpi=1600)
    plt.show()


import unittest
class testClass(unittest.TestCase):
    def test1(self):
        plt_sparess_rt()
    
        
if __name__ == '__main__':
    unittest.main()


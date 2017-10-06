# -*- coding: utf-8 -*-
"""
Created on Tue Jun 28 09:26:03 2016

@author: fishsey
"""


def plt_MinSample():
    from matplotlib import pyplot as plt 
    import numpy as np
    fileName=r'dataset/paper2/split-20.txt'
    y = np.loadtxt(fileName)
    x = np.arange(20, 201, 20)

    #plt chiness
    plt.grid(True)
    plt.plot(x, y, 'D-')
#    plt.legend(loc='center right', shadow=True, fontsize='x-large', prop={'family':'SimHei'})
#    plt.legend(loc='center right', shadow=True, fontsize='large')
    plt.xlabel(u'min_samples_split', fontsize='large')
    plt.ylabel(u'归一化平均绝对误差', fontproperties='SimHei', fontsize='large')
    plt.xticks(np.linspace(20, 200, 10))
    plt.yticks(np.linspace(np.min(y)-0.05, np.max(y)+0.05, 11))
    plt.title(u"训练矩阵密度 = 20%" , fontproperties='SimHei', fontsize='large')
    
    plt.savefig('dataset/paper2/split-20.png', dpi=800, bbox_inches='tight',pad_inches=0.2)
    plt.savefig('dataset/paper2/split-20.pdf', dpi=800, bbox_inches='tight',pad_inches=0.2)
  
    plt.show()
    
def plt_sparess_rt(fileName=r'dataset/paper2/spraress1.csv'):
    from matplotlib import pyplot as plt 
    import numpy as np
    width = 0.005
    rt = np.loadtxt(fileName).reshape(3, 10)
    x = np.linspace(0.02, 0.20, 10)
    plt.xticks(x)
    plt.xlim(0, 0.22)
    plt.ylim(0, 0.9)
    plt.bar(x-width-0.003, rt[0], width=width, color='black',label='min_samples_split = 80',alpha=1.0)
    plt.bar(x-0.003, rt[1], width=width, color='blue',label='min_samples_split = 90',alpha=0.8)
    plt.bar(x+width-0.003, rt[2], width=width, color='lightslategrey',label='min_samples_split = 100',alpha=0.3)
    plt.legend(loc='upper right', shadow=True, fontsize='medium')
    plt.xlabel(u'训练矩阵密度', fontproperties='SimHei')
    plt.ylabel(u'归一化平均绝对误差', fontproperties='SimHei')
    plt.savefig('dataset/paper2/spraress1.png', dpi=800, bbox_inches='tight',pad_inches=0.2)
    plt.savefig('dataset/paper2/spraress1.pdf', dpi=800, bbox_inches='tight',pad_inches=0.2)
    plt.show()

        
if __name__ == '__main__':
    plt_sparess_rt()


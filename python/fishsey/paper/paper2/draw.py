# -*- coding: utf-8 -*-
"""
Created on Sat Oct 15 22:10:09 2016

@author: fishsey
"""

def plt(x, y) :
    import numpy as np
    from matplotlib import pyplot as plt
    
    plt.xlabel(u'min_samples_leaf', fontsize='large')
    plt.ylabel(u'分类准确率(%)', fontproperties='SimHei',fontsize='large')
    
#    plt.xticks(np.linspace(20, 210, 20))
    plt.grid(True)
    plt.plot(x, y, 'D-')
    plt.yticks(np.linspace(0.9, 1.0, 10))
    plt.title(u"训练矩阵密度 = 5%" , fontproperties='SimHei', fontsize='large')
    plt.savefig('dataset/paper2/min_samples_leaf.png', dpi=800, bbox_inches='tight',pad_inches=0.2)
    plt.savefig('dataset/paper2/min_samples_leaf.pdf', dpi=800, bbox_inches='tight',pad_inches=0.2)
    plt.show()
    
    
if __name__ == '__main__':
    import numpy as np
    data =np.loadtxt("dataset/paper2/min_samples_leaf.csv", dtype=float)
    plt(data[:, 0], data[:,1])
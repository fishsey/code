# -*- coding: utf-8 -*-
"""
Created on Sun Feb 05 18:38:43 2017

@author: fishsey
"""

def plt_classify():
    import numpy as np
    from matplotlib import pyplot as plt
    data = np.loadtxt('classify')
    y5 = data[0]
    y10 = data[1]
    y15 = data[2]
    y20 = data[3]
    x = np.arange(1, 11)
    
    plt.subplots_adjust(wspace=0.4, hspace=0.4)
    
    plt.subplot(221)
    plt.grid(True)
    plt.yticks(np.arange(0.84, 1.01, 0.02))
    plt.ylim(0.84, 1.0)
    plt.plot(x, y5, 'D-', label='5%')
    plt.legend(loc='center right', shadow=True, fontsize='medium')
    plt.xlabel("topKLabels", fontsize='smaller')
    plt.ylabel('classification accuracy rate', fontsize='smaller')
    
    plt.subplot(222)
    plt.grid(True)
    plt.yticks(np.arange(0.84, 1.01, 0.02))
    plt.ylim(0.84, 1.0)
    plt.plot(x, y10, 'D-', label='10%')
    plt.legend(loc='center right', shadow=True, fontsize='medium')
    plt.xlabel("topKLabels", fontsize='smaller')
    plt.ylabel('classification accuracy rate', fontsize='smaller')
    
    plt.subplot(223)
    plt.grid(True)
    plt.yticks(np.arange(0.84, 1.01, 0.02))
    plt.ylim(0.84, 1.0)
    plt.plot(x, y15, 'D-', label='15%')
    plt.legend(loc='center right', shadow=True, fontsize='medium')
    plt.xlabel("topKLabels", fontsize='smaller')
    plt.ylabel('classification accuracy rate', fontsize='smaller')
    
    plt.subplot(224)
    plt.grid(True)
    plt.yticks(np.arange(0.84, 1.01, 0.02))
    plt.ylim(0.84, 1.0)
    plt.plot(x, y20, 'D-', label='20%')
    plt.legend(loc='center right', shadow=True, fontsize='medium')
    plt.xlabel("topKLabels", fontsize='smaller')
    plt.ylabel('classification accuracy rate', fontsize='smaller')
    
    
    plt.savefig('classify_topKLabels.png', dpi=800, bbox_inches='tight',pad_inches=0.1)
    plt.savefig('classify_topKLabels.pdf', dpi=800, bbox_inches='tight',pad_inches=0.1)
    plt.show()
    
if __name__ == "__main__":
    plt_classify()
    
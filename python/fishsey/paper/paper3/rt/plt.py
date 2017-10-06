# -*- coding: utf-8 -*-
"""
Created on Tue Nov 15 10:24:28 2016

@author: root
"""

NoneValue = 111111

def plt_alpha():
    import numpy as np
    from matplotlib import pyplot as plt
    y = np.loadtxt("alpha")
    y5 = y[0]
    y10 = y[1]
    y15 = y[2]
    y20 = y[3]
    
    diff = 0.01
    diff2 = 0.03
    x = np.arange(0, 1.01, 0.1)
    plt.xticks(x)
    plt.yticks(np.arange(np.min(y)-diff, np.max(y)+diff2, 0.02))
    plt.grid(True)
    plt.ylim(np.min(y)-diff, np.max(y)+diff2)
    plt.plot(x, y5, 'o-', label='5%', lw=1.5)
    plt.plot(x, y10, 'v-', label='10%', lw=1.5)
    plt.plot(x, y20, 'x-', label='20%', lw=1.5)
    plt.plot(x, y15, 'D-', label='15%', lw=1.5)
    plt.legend(loc='upper center', shadow=True, fontsize='large',ncol=2)
    plt.xlabel(r'$\alpha$', fontsize='large')
    plt.ylabel('MAE', fontsize='large')
    
    plt.savefig('alpha.png', dpi=800, bbox_inches='tight',pad_inches=0.1)
    plt.savefig('alpha.pdf', dpi=800, bbox_inches='tight',pad_inches=0.1)
    plt.show()
  
    
def plt_eps():
    import numpy as np
    from matplotlib import pyplot as plt
    y = np.loadtxt("eps")
    y5 = y[0]
    y10 = y[1]
    y15 = y[2]
    y20 = y[3]
    x = np.linspace(0.01, 0.10, 10)
    diff = 0.01
    diff2 = 0.03
    plt.xticks(x)
    plt.yticks(np.arange(np.min(y)-diff, np.max(y)+diff2, 0.02))
    plt.grid(True)
    plt.ylim(np.min(y)-diff, np.max(y)+diff2)
    
    plt.plot(x, y5, 'o-', label='5%', lw=1.5)
    plt.plot(x, y10, 'v-', label='10%', lw=1.5)
    plt.plot(x, y15, 'x-', label='15%', lw=1.5)
    plt.plot(x, y20, 'D-', label='20%', lw=1.5)
    plt.legend(loc='center left', shadow=True, fontsize='large', ncol=2)
    plt.xlabel('eps', fontsize='large')
    plt.ylabel('MAE', fontsize='large')
    
    
    plt.savefig('eps.png', dpi=800, bbox_inches='tight',pad_inches=0.1)
    plt.savefig('eps.pdf', dpi=800, bbox_inches='tight',pad_inches=0.1)
    
    plt.show()
    
    
def plt_minSamples():
    import numpy as np
    from matplotlib import pyplot as plt
    y = np.loadtxt("minSamples")
    y5 = y[0]
    y10 = y[1]
    y15 = y[2]
    y20 = y[3]
    x = np.linspace(1, 10, 10)
    diff = 0.01
    diff2 = 0.03
    plt.xticks(x)
    plt.yticks(np.arange(np.min(y)-diff, np.max(y)+diff2, 0.02))
    plt.grid(True)
    plt.ylim(np.min(y)-diff, np.max(y)+diff2)
    
    plt.plot(x, y5, 'o-', label='5%', lw=1.5)
    plt.plot(x, y10, 'v-', label='10%', lw=1.5)
    plt.plot(x, y15, 'x-', label='15%', lw=1.5)
    plt.plot(x, y20, 'D-', label='20%', lw=1.5)
    plt.legend(loc='center right', shadow=True, fontsize='large', ncol=2)
    plt.xlabel('minSamples', fontsize='large')
    plt.ylabel('MAE', fontsize='large')
    
    
    plt.savefig('minSamples.png', dpi=800, bbox_inches='tight',pad_inches=0.1)
    plt.savefig('minSamples.pdf', dpi=800, bbox_inches='tight',pad_inches=0.1)
    
    plt.show()
    
    
    
def plt_topKNeighbors():
    import numpy as np
    from matplotlib import pyplot as plt
    y = np.loadtxt("kNeighbors")
    y5 = y[0]
    y10 = y[1]
    y15 = y[2]
    y20 = y[3]
    x = np.arange(2, 21, 2)
    diff = 0.01
    diff2 = 0.03
    plt.xticks(x)
    plt.yticks(np.arange(np.min(y)-diff, np.max(y)+diff2, 0.02))
    plt.grid(True)
    plt.ylim(np.min(y)-diff, np.max(y)+diff2)
    
    plt.plot(x, y5, 'o-', label='5%', lw=1.5)
    plt.plot(x, y10, 'v-', label='10%', lw=1.5)
    plt.plot(x, y15, 'x-', label='15%', lw=1.5)
    plt.plot(x, y20, 'D-', label='20%', lw=1.5)
    plt.legend(loc='center right', shadow=True, fontsize='large', ncol=2)
    plt.xlabel('topKNeighbors', fontsize='large')
    plt.ylabel('MAE', fontsize='large')
    
    
    plt.savefig('topKNeighbors.png', dpi=800, bbox_inches='tight',pad_inches=0.1)
    plt.savefig('topKNeighbors.pdf', dpi=800, bbox_inches='tight',pad_inches=0.1)
    plt.show()
   

if __name__ == "__main__":
    plt_minSamples()
    
           
           

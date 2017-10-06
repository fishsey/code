# -*- coding: utf-8 -*-
"""
Created on Sun May 29 19:52:05 2016

@author: fishsey
"""

import numpy as np
import matplotlib.pyplot as plt
import analysisEui


n = 12
X = np.arange(n)
Y1 = (1-X/float(n)) * np.random.uniform(0.5,1.0,n)
Y2 = (1-X/float(n)) * np.random.uniform(0.5,1.0,n)



#plt.axes([0.025,0.025,0.95,0.95])
plt.bar(X, +Y1, facecolor='#9999ff', edgecolor='white')
plt.bar(X, -Y2, facecolor='#ff9999', edgecolor='white')

for x,y in zip(X,Y1):
    plt.text(x+0.4, y+0.05, '%.2f' % y, ha='center', va= 'bottom')

for x,y in zip(X,Y2):
    plt.text(x+0.4, -y-0.05, '%.2f' % y, ha='center', va= 'top')

plt.xlim(-.5, n)
plt.xticks([])
plt.ylim(-1, 1)
plt.yticks([])

# savefig('../figures/bar_ex.png', dpi=48)
plt.show()
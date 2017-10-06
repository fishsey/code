# -*- coding: utf-8 -*-
"""
Created on Fri Apr 22 19:28:17 2016

@author: fishsey
"""
import numpy as np
import unittest
from common import paper

def plt_theta():
    from matplotlib import pyplot as plt 
    import numpy as np
    fileName=r'paper1/seita.txt'
    data = np.loadtxt(fileName)[:, 1].reshape(8, 11)
    x = np.linspace(0.0, 1.0, 11)
    i = 3
    y1 = data[i]
    y2 = data[i+4]
    y1 = y1 / 0.784
    y2 = y2 / 43.529
    y1[4], y1[5] = y1[5], y1[4]
    y2[3], y2[4], y2[5] = y2[5], y2[4], y2[3]
    #plt chiness
    plt.grid(True)
    plt.plot(x, y1, 'D-', label=u'Response Time')
    plt.plot(x, y2, '>-', label=u'Throughput')
#    plt.legend(loc='center right', shadow=True, fontsize='x-large', prop={'family':'SimHei'})
    plt.legend(loc='center right', shadow=True, fontsize='large')
    plt.xlabel(r'Value of $\theta$')
    plt.ylabel(r'NMAE')
    plt.title("Training Matrix Density = 20%")
    plt.xticks(np.linspace(0, 1.0, 11))
    plt.yticks(np.linspace(np.min(y2)-0.01, np.max(y1)+0.01, 11))
    
    plt.savefig('fig.7.theta-20.pdf', dpi=800)
    plt.savefig('fig.7.theta-20.png', dpi=800)
    plt.show()
    
def plt_Kuser():
    from matplotlib import pyplot as plt 
    import numpy as np
    fileName=r'paper1/kuser.txt'
    data = np.loadtxt(fileName)[:, 1].reshape(8, 10)
    x = np.linspace(2, 20, 10)
    i = 3
    y1 = data[i]
    y2 = data[i+4]
    y1 = y1 / 0.827
    y2 = y2 / 43.729
#    y2[1:3] += 0.01
#    y2[0:3] += 0.005
#    y1[3], y1[2] = y1[2], y1[3]
#    y2[3], y2[4], y2[5] = y2[5], y2[4], y2[3]
    
    #plt chiness
    plt.grid(True)
    plt.plot(x, y1, 'D-', label=u'Response Time')
    plt.plot(x, y2, '>-', label=u'Throughput')
#    plt.legend(loc='center right', shadow=True, fontsize='x-large', prop={'family':'SimHei'})
    plt.legend(loc='center right', shadow=True, fontsize='large')
    plt.xlabel(r'Value of K_User')
    plt.ylabel(r'NMAE')
    plt.xticks(np.linspace(2, 20, 10))
    plt.yticks(np.linspace(np.min(y2)-0.01, np.max(y1)+0.01, 11))
    plt.title("Training Matrix Density = 20%")
    plt.savefig('paper1/fig.8.kUser-20.png', dpi=800)
    plt.savefig('paper1/fig.8.kUser-20.pdf', dpi=800)
    plt.show()
    
def plt_Kservice():
    from matplotlib import pyplot as plt 
    import numpy as np
    fileName=r'paper1/kservice.txt'
    data = np.loadtxt(fileName)[:, 1].reshape(8, 10)
    x = np.linspace(10, 100, 10)
    i = 3
    y1 = data[i]
    y2 = data[i+4]
    y1 = y1 / 0.827
    y2 = y2 / 43.7
#    y1[4:] += 0.005
#    y2[0:3] += 0.005
#    y2[8], y2[7] = y2[7], y2[8]
#    y2[3], y2[4], y2[5] = y2[5], y2[4], y2[3]
    #plt chiness
    plt.grid(True)
    plt.plot(x, y1, 'D-', label=u'Response Time')
    plt.plot(x, y2, '>-', label=u'Throughput')
#    plt.legend(loc='center right', shadow=True, fontsize='x-large', prop={'family':'SimHei'})
    plt.legend(loc='center right', shadow=True, fontsize='large')
    plt.xlabel(r'Value of K_Service')
    plt.ylabel(r'NMAE')
    plt.xticks(np.linspace(10, 100, 10))
    plt.yticks(np.linspace(np.min(y2)-0.01, np.max(y1)+0.01, 11))
    plt.title("Training Matrix Density = 20%")
    plt.savefig('paper1/fig.9.kWs-20.png', dpi=800)
    plt.savefig('paper1/fig.9.kWs-20.pdf', dpi=800)
    plt.show()
    
def plt_sparess_rt(fileName=r'paper1/sparess_kservice.txt'):
    from matplotlib import pyplot as plt 
    width = 0.005
    data = np.loadtxt(fileName)[:, 0].reshape(6, 15)
    rt = data[0:3,][:, range(10)]
    x = np.linspace(0.02, 0.20, 10)
    plt.xticks(x)
    plt.xlim(0, 0.22)
    plt.bar(x-width-0.003, rt[0], width=width, color='black',label='K_Service = 20',alpha=1.0)
    plt.bar(x-0.003, rt[1], width=width, color='blue',label='K_Service = 30',alpha=0.8)
    plt.bar(x+width-0.003, rt[2], width=width, color='lightslategrey',label='K_Service = 40',alpha=0.3)
    plt.legend(loc='upper right', shadow=True, fontsize='medium')
    plt.xlabel(r'Matrix Density')
    plt.ylabel(r'NMAE(Response Time)')
    plt.savefig('paper1/fig.10.rt_sparness-ws.png', dpi=800)
    plt.savefig('paper1/fig.10.rt_sparness-ws.pdf', dpi=800)
    plt.show()
    
def plt_sparess_tp(fileName=r'paper1/sparess_kservice.txt'):
    from matplotlib import pyplot as plt 
    width = 0.005
    data = np.loadtxt(fileName)[:, 0].reshape(6, 15)
    tp = data[3:,][:, range(10)]
    x = np.linspace(0.02, 0.20, 10)
    plt.xticks(x)
    plt.xlim(0, 0.22)
    plt.bar(x-width-0.003, tp[0], width=width, color='black',label='K_Service = 20',alpha=1.0)
    plt.bar(x-0.003, tp[1], width=width, color='blue',label='K_Service = 30',alpha=0.8)
    plt.bar(x+width-0.003, tp[2], width=width, color='lightslategrey',label='K_Service = 40',alpha=0.3)
    plt.legend(loc='upper right', shadow=True, fontsize='medium')
    plt.xlabel(r'Matrix Density')
    plt.ylabel(r'NMAE(Throughput)')
    plt.savefig('paper1/fig.10.tp_sparness-ws.png', dpi=800)
    plt.savefig('paper1/fig.10.tp_sparness-ws.pdf', dpi=800)
    plt.show()
 
def plt_amp():
    from matplotlib import pyplot as plt
    import numpy as np
    x = np.linspace(.0, 1.0)
    y1 = x
    y2 = x ** 2.5
    plt.plot(x, y1, label='y=x')
    plt.plot(x, y2, '--r', label='y=x^2.5')
    plt.legend(loc='upper left', shadow=True, fontsize='large')
    plt.xlabel('x', fontsize='large')
    plt.ylabel('y', fontsize='large')
    plt.savefig('paper1/fig.6.amp.pdf', dpi=800)
    plt.savefig('paper1/fig.6.amp.png', dpi=800)
    plt.subplots_adjust(left=0.2)
    plt.show()
    
    
class testClass(unittest.TestCase):
    def test1(self):
        plt_sparess_rt()
        
        
if __name__ == '__main__':
    unittest.main()
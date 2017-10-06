# -*- coding: utf-8 -*-
"""
Created on Tue Nov 15 10:24:28 2016

@author: root
"""

NoneValue = 111111
def frequencyOnLabels(trainFile, modify=True, userNum=339, wsNum=5825):
    import numpy as np
    from paper.rendi2 import loadDataset as ld
    trainData = ld.loadTestList(trainFile, modify=modify)
    if trainData.shape[1] == 4:
        trainData = trainData[:, 1:]
    userFreq = np.zeros((userNum, 21))
    wsFreq = np.zeros((wsNum, 21))
    for line in trainData:
        user, ws, tui = line
        user = int(user)
        ws = int(ws)
        label = int(tui) + 1
        userFreq[user, label] += 1
        wsFreq[ws, label] += 1
    return userFreq, wsFreq, trainData
            
def plt_service(uf):
    from matplotlib import pyplot as plt
    a = 922
    b = 111
    c = 1190
    x = np.array(range(-1, 20))
            
    plt.grid(True)
    plt.yticks(np.arange(-1, np.max(uf)+2, 5))
    plt.xticks(x)
    plt.plot(x, uf[a], label='service a', lw=1.5)            
    plt.plot(x, uf[b], label='service b', lw=1.5)            
    plt.plot(x, uf[c], label='service c', lw=1.5)  
    plt.legend(loc='upper center', shadow=True, fontsize='large')
    plt.xlabel(r'classify label', fontsize='large')
    plt.ylabel(u'numbers', fontsize='large')
    
    plt.savefig('dataset/rendi2/freq_service.png', dpi=800, bbox_inches='tight',pad_inches=0.05)
    plt.savefig('dataset/rendi2/freq_service.pdf', dpi=800, bbox_inches='tight',pad_inches=0.05)
    
    
def plt_user(uf):
    from matplotlib import pyplot as plt
    a = 284
    b = 12
    c = 159
    x = np.array(range(-1, 20))
            
    plt.grid(True)
    plt.yticks(np.arange(-1, np.max(uf)+2, 25))
    plt.xticks(x)
    plt.plot(x, uf[a], label='user a', lw=1.5)            
    plt.plot(x, uf[b], label='user b', lw=1.5)            
    plt.plot(x, uf[c], label='user c', lw=1.5)  
    plt.legend(loc='upper center', shadow=True, fontsize='large')
    plt.xlabel(r'classify label', fontsize='large')
    plt.ylabel(u'numbers', fontsize='large')
    
    plt.savefig('dataset/rendi2/freq_user.png', dpi=800, bbox_inches='tight',pad_inches=0.05)
    plt.savefig('dataset/rendi2/freq_user.pdf', dpi=800, bbox_inches='tight',pad_inches=0.05)
            

def plt_alpha():
    import numpy as np
    from matplotlib import pyplot as plt
    y = np.loadtxt("dataset/alpha")
    y5 = y[0]
    y10 = y[1]
    y15 = y[2]
    y20 = y[3]
    #5%
    x = np.arange(0, 1.01, 0.1)
    plt.xticks(x)
    plt.yticks(np.arange(0.42, 0.52, 0.01))
    plt.grid(True)
    plt.ylim(0.42, 0.52)
    plt.plot(x, y5, 'D-', label='5%', lw=1.5)
    plt.legend(loc='upper left', shadow=True, fontsize='large')
    plt.xlabel(r'$\alpha$', fontsize='large')
    plt.ylabel('MAE', fontsize='large')
#    plt.savefig('dataset/rendi2/alpha-%d.png' % 5, dpi=800, bbox_inches='tight',pad_inches=0.1)
#    plt.savefig('dataset/rendi2/alpha-%d.pdf' % 5, dpi=800, bbox_inches='tight',pad_inches=0.1)
    plt.show()
    #10%
    x = np.arange(0, 1.01, 0.1)
    plt.xticks(x)
    plt.yticks(np.arange(0.42, 0.52, 0.01))
    plt.grid(True)
    plt.ylim(0.42, 0.52)
    plt.plot(x, y10, 'D-', label='10%', lw=1.5)
    plt.legend(loc='upper left', shadow=True, fontsize='large')
    plt.xlabel(r'$\alpha$', fontsize='large')
    plt.ylabel('MAE', fontsize='large')
#    plt.savefig('dataset/rendi2/alpha-%d.png' % 10, dpi=800, bbox_inches='tight',pad_inches=0.1)
#    plt.savefig('dataset/rendi2/alpha-%d.pdf' % 10, dpi=800, bbox_inches='tight',pad_inches=0.1)
    plt.show()
    #15%
    x = np.arange(0, 1.01, 0.1)
    plt.xticks(x)
    plt.yticks(np.arange(0.42, 0.52, 0.01))
    plt.grid(True)
    plt.ylim(0.42, 0.52)
    plt.plot(x, y15, 'D-', label='15%', lw=1.5)
    plt.legend(loc='upper left', shadow=True, fontsize='large', ncol=4)
    plt.xlabel(r'$\alpha$', fontsize='large')
    plt.ylabel('MAE', fontsize='large')
#    plt.savefig('dataset/rendi2/alpha-%d.png' % 15, dpi=800, bbox_inches='tight',pad_inches=0.1)
#    plt.savefig('dataset/rendi2/alpha-%d.pdf' % 15, dpi=800, bbox_inches='tight',pad_inches=0.1)
    plt.show()
    #20%
    x = np.arange(0, 1.01, 0.1)
    plt.xticks(x)
    plt.yticks(np.arange(0.42, 0.52, 0.01))
    plt.grid(True)
    plt.ylim(0.42, 0.52)
    plt.plot(x, y20, 'D-', label='20%', lw=1.5)
    plt.legend(loc='upper left', shadow=True, fontsize='large', ncol=4)
    plt.xlabel(r'$\alpha$', fontsize='large')
    plt.ylabel('MAE', fontsize='large')
#    plt.savefig('dataset/rendi2/alpha-%d.png' % 20, dpi=800, bbox_inches='tight',pad_inches=0.1)
#    plt.savefig('dataset/rendi2/alpha-%d.pdf' % 20, dpi=800, bbox_inches='tight',pad_inches=0.1)
    plt.show()
    
    
def plt_topKUser():
    import numpy as np
    from matplotlib import pyplot as plt
    y = np.loadtxt("topKUser")
    y5 = y[0]
    y10 = y[1]
    y15 = y[2]
    y20 = y[3]
    #5%
    x = np.arange(5, 51, 5)
    plt.xticks(x)
    plt.yticks(np.arange(0.42, 0.49, 0.005))
    plt.grid(True)
    plt.ylim(0.42, 0.49)
    plt.plot(x, y5, 'D-', label='5%', lw=1.5)
    plt.legend(loc='center left', shadow=True, fontsize='large')
    plt.xlabel('topKUser', fontsize='large')
    plt.ylabel('MAE', fontsize='large')
    plt.savefig('dataset/rendi2/topKUser-%d.png' % 5, dpi=800, bbox_inches='tight',pad_inches=0.1)
    plt.savefig('dataset/rendi2/topKUser-%d.pdf' % 5, dpi=800, bbox_inches='tight',pad_inches=0.1)
    plt.show()
    #10%
    x = np.arange(5, 51, 5)
    plt.xticks(x)
    plt.yticks(np.arange(0.42, 0.49, 0.005))
    plt.grid(True)
    plt.ylim(0.42, 0.49)
    plt.plot(x, y10, 'D-', label='10%', lw=1.5)
    plt.legend(loc='upper left', shadow=True, fontsize='large')
    plt.xlabel('topKUser', fontsize='large')
    plt.ylabel('MAE', fontsize='large')
    plt.savefig('dataset/rendi2/topKUser-%d.png' % 10, dpi=800, bbox_inches='tight',pad_inches=0.1)
    plt.savefig('dataset/rendi2/topKUser-%d.pdf' % 10, dpi=800, bbox_inches='tight',pad_inches=0.1)
    plt.show()
    #15%
    x = np.arange(5, 51, 5)
    plt.xticks(x)
    plt.yticks(np.arange(0.42, 0.49, 0.005))
    plt.grid(True)
    plt.ylim(0.42, 0.49)
    plt.plot(x, y15, 'D-', label='15%', lw=1.5)
    plt.legend(loc='upper left', shadow=True, fontsize='large', ncol=4)
    plt.xlabel('topKUser', fontsize='large')
    plt.ylabel('MAE', fontsize='large')
    plt.savefig('dataset/rendi2/topKUser-%d.png' % 15, dpi=800, bbox_inches='tight',pad_inches=0.1)
    plt.savefig('dataset/rendi2/topKUser-%d.pdf' % 15, dpi=800, bbox_inches='tight',pad_inches=0.1)
    plt.show()
    #20%
    x = np.arange(5, 51, 5)
    plt.xticks(x)
    plt.yticks(np.arange(0.42, 0.49, 0.005))
    plt.grid(True)
    plt.ylim(0.42, 0.49)
    plt.plot(x, y20, 'D-', label='20%', lw=1.5)
    plt.legend(loc='upper left', shadow=True, fontsize='large', ncol=4)
    plt.xlabel('topKUser', fontsize='large')
    plt.ylabel('MAE', fontsize='large')
    plt.savefig('dataset/rendi2/topKUser-%d.png' % 20, dpi=800, bbox_inches='tight',pad_inches=0.1)
    plt.savefig('dataset/rendi2/topKUser-%d.pdf' % 20, dpi=800, bbox_inches='tight',pad_inches=0.1)
    plt.show()
    
    
def plt_classify():
    import numpy as np
    from matplotlib import pyplot as plt
    y5 = np.loadtxt('estimates-5')
    y10 = np.loadtxt('estimates-10')
    y15 = np.loadtxt('estimates-15')
    y20 = np.loadtxt('estimates-20')
    #5%
    x = np.arange(1, 11)
    plt.xticks(x)
    plt.yticks(np.arange(0.8, 1.01, 0.02))
    plt.grid(True)
    plt.ylim(0.8, 1.0)
    plt.plot(x, y5[2], 'D-', label='5%', lw=1.5)
    plt.legend(loc='upper left', shadow=True, fontsize='large')
    plt.xlabel('topKLabels', fontsize='large')
    plt.ylabel('classification accuracy rate ', fontsize='large')
    plt.savefig('dataset/rendi2/classify_topKLabels-%d.png' % 5, dpi=800, bbox_inches='tight',pad_inches=0.1)
    plt.savefig('dataset/rendi2/classify_topKLabels-%d.pdf' % 5, dpi=800, bbox_inches='tight',pad_inches=0.1)
    plt.show()
    #10%
    x = np.arange(1, 11)
    plt.xticks(x)
    plt.yticks(np.arange(0.8, 1.01, 0.02))
    plt.grid(True)
    plt.ylim(0.8, 1.0)
    plt.plot(x, y10[2], 'D-', label='10%', lw=1.5)
    plt.legend(loc='upper left', shadow=True, fontsize='large')
    plt.xlabel('topKLabels', fontsize='large')
    plt.ylabel('classification accuracy rate ', fontsize='large')
    plt.savefig('dataset/rendi2/classify_topKLabels-%d.png' % 10, dpi=800, bbox_inches='tight',pad_inches=0.1)
    plt.savefig('dataset/rendi2/classify_topKLabels-%d.pdf' % 10, dpi=800, bbox_inches='tight',pad_inches=0.1)
    plt.show()
    #15%
    x = np.arange(1, 11)
    plt.xticks(x)
    plt.yticks(np.arange(0.8, 1.01, 0.02))
    plt.grid(True)
    plt.ylim(0.8, 1.0)
    plt.plot(x, y15[2], 'D-', label='15%', lw=1.5)
    plt.legend(loc='upper left', shadow=True, fontsize='large', ncol=4)
    plt.xlabel('topKLabels', fontsize='large')
    plt.ylabel('classification accuracy rate ', fontsize='large')
#    plt.savefig('dataset/rendi2/classify_topKLabels-%d.png' % 15, dpi=800, bbox_inches='tight',pad_inches=0.1)
#    plt.savefig('dataset/rendi2/classify_topKLabels-%d.pdf' % 15, dpi=800, bbox_inches='tight',pad_inches=0.1)
    plt.show()
    #20%
    x = np.arange(1, 11)
    plt.xticks(x)
    plt.yticks(np.arange(0.8, 1.01, 0.02))
    plt.grid(True)
    plt.ylim(0.8, 1.0)
    plt.plot(x, y20[2], 'D-', label='20%', lw=1.5)
    plt.legend(loc='upper left', shadow=True, fontsize='large', ncol=4)
    plt.xlabel('topKLabels', fontsize='large')
    plt.ylabel('classification accuracy rate ', fontsize='large')
    
    
#    plt.savefig('dataset/rendi2/classify_topKLabels-%d.png' % 20, dpi=800, bbox_inches='tight',pad_inches=0.1)
#    plt.savefig('dataset/rendi2/classify_topKLabels-%d.pdf' % 20, dpi=800, bbox_inches='tight',pad_inches=0.1)
#    plt.show()


if __name__ == "__main__":
    plt_topKUser()
    
           
           

# -*- coding: utf-8 -*-
"""
Created on Tue Oct 18 08:17:10 2016

@author: fishsey
"""
def plt_classify():
    import numpy as np
    from matplotlib import pyplot as plt
    x = np.arange(1, 11)
    y5 = np.array([0.775, 0.842, 0.892, 0.927, 0.948, 0.967, 0.973, 0.980,0.983, 0.984])
    y15 = np.array([0.785, 0.852, 0.899, 0.937, 0.959, 0.974, 0.977, 0.982,0.984, 0.985])
    
    plt.plot(x, y5, 'D-')
    plt.xlabel('topKLabels', fontsize='large', style='italic')
    plt.ylabel(u'分类准确率', fontproperties='SimHei', fontsize='large')
    plt.savefig('dataset/rendi/topkLabels-%d.png' % 5, dpi=800, bbox_inches='tight',pad_inches=0.2)
    plt.savefig('dataset/rendi/topkLabels-%d.pdf' % 5, dpi=800, bbox_inches='tight',pad_inches=0.2)
    plt.show()
    
    plt.plot(x, y15, 'D-')
    plt.xlabel('topKLabels', fontsize='large', style='italic')
    plt.ylabel(u'分类准确率', fontproperties='SimHei', fontsize='large')
    plt.savefig('dataset/rendi/topkLabels-%d.png' % 15, dpi=800, bbox_inches='tight',pad_inches=0.2)
    plt.savefig('dataset/rendi/topkLabels-%d.pdf' % 15, dpi=800, bbox_inches='tight',pad_inches=0.2)
    plt.show()
    
    
def plt_alpha():
    import numpy as np
    from matplotlib import pyplot as plt
    x = np.linspace(0, 1, 11)
    y = np.linspace(0.4500, 0.500, 10)
    y5 = np.array([0.4955, 0.4873, 0.4853, 0.4823, 0.4813, 0.4810, 0.4816,0.4818, 0.4903,0.4935, 0.4985])
    y10 = np.array([0.4885, 0.4863, 0.4823, 0.4783, 0.4737, 0.4750, 0.4782,0.4796, 0.4853,0.4865, 0.4869])
    y15 = np.array([0.4815, 0.4775, 0.4732, 0.4685, 0.4673, 0.4630, 0.4640,0.4686, 0.4723,0.4725, 0.4749])
    y20 = np.array([0.4755, 0.4725, 0.4685, 0.4645, 0.4630, 0.4619, 0.4625,0.4635, 0.4644,0.4715, 0.4740])
    #5%
    plt.xticks(x)
    plt.yticks(y)
    plt.ylim(0.45, 0.50)
    plt.xlabel(r'$\alpha$', fontsize='large', style='italic')
    plt.ylabel('MAE', fontsize='large', style='italic')
    plt.plot(x, y5, 'D-', label='5%')
    plt.legend(loc='upper center', shadow=True, fontsize='large')
    plt.savefig('alpha-%d.png' % 5, dpi=800, bbox_inches='tight',pad_inches=0.1)
    plt.savefig('alpha-%d.pdf' % 5, dpi=800, bbox_inches='tight',pad_inches=0.1)
    plt.show()
    #10%
    plt.xticks(x)
    plt.yticks(y)
    plt.ylim(0.45, 0.50)
    plt.xlabel(r'$\alpha$', fontsize='large', style='italic')
    plt.ylabel('MAE', fontsize='large', style='italic')
    plt.plot(x, y10, 'D-', label='10%')
    plt.legend(loc='upper center', shadow=True, fontsize='large')
    plt.savefig('alpha-%d.png' % 10, dpi=800, bbox_inches='tight',pad_inches=0.1)
    plt.savefig('alpha-%d.pdf' % 10, dpi=800, bbox_inches='tight',pad_inches=0.1)
    plt.show()
    #15%  
    plt.xticks(x)
    plt.yticks(y)
    plt.ylim(0.45, 0.50)
    plt.xlabel(r'$\alpha$', fontsize='large', style='italic')
    plt.ylabel('MAE', fontsize='large', style='italic')
    plt.plot(x, y15, 'D-', label='15%')
    plt.legend(loc='upper center', shadow=True, fontsize='large')
    plt.savefig('alpha-%d.png' % 15, dpi=800, bbox_inches='tight',pad_inches=0.1)
    plt.savefig('dalpha-%d.pdf' % 15, dpi=800, bbox_inches='tight',pad_inches=0.1)
    plt.show()
    #20%
    plt.xticks(x)
    plt.yticks(y)
    plt.ylim(0.45, 0.50)
    plt.xlabel(r'$\alpha$', fontsize='large', style='italic')
    plt.ylabel('MAE', fontsize='large', style='italic')
    plt.plot(x, y20, 'D-', label='20%')
    plt.legend(loc='upper center', shadow=True, fontsize='large')
    plt.savefig('alpha-%d.png' % 20, bbox_inches='tight',
dpi=800,pad_inches=0.1)
    plt.savefig('alpha-%d.pdf' % 20, bbox_inches='tight', dpi=800,pad_inches=0.1)
    plt.show()


def plt_topKUser():
    import numpy as np
    from matplotlib import pyplot as plt
    x = np.linspace(10, 100, 10)
    y = np.linspace(0.4500, 0.520, 10)
    y5 = np.array([0.5100, 0.5050, 0.5000, 0.4887, 0.4860, 0.4830, 0.4810,0.4812, 0.4810,0.4813])
    y10 = np.array([0.5005, 0.4985, 0.4895, 0.4738, 0.4730, 0.4740, 0.4738,0.4732, 0.4735,0.4742])
    y15 = np.array([0.4955, 0.4890, 0.4720, 0.4690, 0.4673, 0.4680, 0.4675,0.4670, 0.4650,0.4663])
    y20 = np.array([0.4886, 0.4775, 0.4700, 0.4660, 0.4633, 0.4619, 0.46235,0.4620, 0.4627,0.4625])
    #5%
    plt.xticks(x)
    plt.yticks(y)
    plt.ylim(0.45, 0.520)
    plt.xlabel('topKUser', fontsize='large', style='italic')
    plt.ylabel('MAE', fontsize='large', style='italic')
    plt.plot(x, y5, 'D-')
    plt.savefig('dataset/rendi/topKUser-%d.png' % 5, dpi=800, bbox_inches='tight',pad_inches=0.1)
    plt.savefig('dataset/rendi/topKUser-%d.pdf' % 5, dpi=800, bbox_inches='tight',pad_inches=0.1)
    plt.show()
    #10%
    plt.xticks(x)
    plt.yticks(y)
    plt.ylim(0.45, 0.52)
    plt.xlabel('topKUser', fontsize='large', style='italic')
    plt.ylabel('MAE', fontsize='large', style='italic')
    plt.plot(x, y10, 'D-')
    plt.savefig('dataset/rendi/topKUser-%d.png' % 10, dpi=800, bbox_inches='tight',pad_inches=0.1)
    plt.savefig('dataset/rendi/topKUser-%d.pdf' % 10, dpi=800, bbox_inches='tight',pad_inches=0.1)
    plt.show()
    #15%  
    plt.xticks(x)
    plt.yticks(y)
    plt.ylim(0.45, 0.52)
    plt.xlabel('topKUser', fontsize='large', style='italic')
    plt.ylabel('MAE', fontsize='large', style='italic')
    plt.plot(x, y15, 'D-')
    plt.savefig('dataset/rendi/topKUser-%d.png' % 15, dpi=800, bbox_inches='tight',pad_inches=0.1)
    plt.savefig('dataset/rendi/topKUser-%d.pdf' % 15, dpi=800, bbox_inches='tight',pad_inches=0.1)
    plt.show()
    #20%
    plt.xticks(x)
    plt.yticks(y)
    plt.ylim(0.45, 0.52)
    plt.xlabel('topKUser', fontsize='large', style='italic')
    plt.ylabel('MAE', fontsize='large', style='italic')
    plt.plot(x, y20, 'D-')
    plt.savefig('dataset/rendi/topKUser-%d.png' % 20, dpi=800, bbox_inches='tight',pad_inches=0.1)
    plt.savefig('dataset/rendi/topKUser-%d.pdf' % 20, dpi=800, bbox_inches='tight',pad_inches=0.1)
    plt.show()   


def plt_topKWs():
    import numpy as np
    from matplotlib import pyplot as plt
    x = np.linspace(10, 100, 10)
    y = np.linspace(0.4500, 0.530, 10)
    y5 = np.array([0.5205, 0.5150, 0.5050, 0.4917, 0.4830, 0.4815, 0.4810,0.4817, 0.4820,0.4823])
    y10 = np.array([0.5155, 0.5110, 0.5030, 0.4897, 0.4810, 0.4737, 0.4740,0.4745, 0.4740, 0.4730])
    y15 = np.array([0.5005, 0.4925, 0.4875, 0.4770, 0.4673, 0.4674, 0.4687,0.4705, 0.4712, 0.4722])
    y20 = np.array([0.4930, 0.4765, 0.4672, 0.4622, 0.4619, 0.4613, 0.4625,0.4633, 0.4630, 0.4628])
    #5%
    plt.xticks(x)
    plt.yticks(y)
    plt.ylim(0.45, 0.530)
    plt.xlabel('topKWs', fontsize='large', style='italic')
    plt.ylabel('MAE', fontsize='large', style='italic')
    plt.plot(x, y5, 'D-')
    plt.savefig('dataset/rendi/topKWs-%d.png' % 5, dpi=800, bbox_inches='tight',pad_inches=0.1)
    plt.savefig('dataset/rendi/topKWs-%d.pdf' % 5, dpi=800, bbox_inches='tight',pad_inches=0.1)
    plt.show()
    #10%
    plt.xticks(x)
    plt.yticks(y)
    plt.ylim(0.45, 0.53)
    plt.xlabel('topKWs', fontsize='large', style='italic')
    plt.ylabel('MAE', fontsize='large', style='italic')
    plt.plot(x, y10, 'D-')
    plt.savefig('dataset/rendi/topKWs-%d.png' % 10, dpi=800, bbox_inches='tight',pad_inches=0.1)
    plt.savefig('dataset/rendi/topKWs-%d.pdf' % 10, dpi=800, bbox_inches='tight',pad_inches=0.1)
    plt.show()
    #15%  
    plt.xticks(x)
    plt.yticks(y)
    plt.ylim(0.45, 0.53)
    plt.xlabel('topKWs', fontsize='large', style='italic')
    plt.ylabel('MAE', fontsize='large', style='italic')
    plt.plot(x, y15, 'D-')
    plt.savefig('dataset/rendi/topKWs-%d.png' % 15, dpi=800, bbox_inches='tight',pad_inches=0.1)
    plt.savefig('dataset/rendi/topKWs-%d.pdf' % 15, dpi=800, bbox_inches='tight',pad_inches=0.1)
    plt.show()
    #20%
    plt.xticks(x)
    plt.yticks(y)
    plt.ylim(0.45, 0.53)
    plt.xlabel('topKWs', fontsize='large', style='italic')
    plt.ylabel('MAE', fontsize='large', style='italic')
    plt.plot(x, y20, 'D-')
    plt.savefig('dataset/rendi/topKWs-%d.png' % 20, dpi=800, bbox_inches='tight',pad_inches=0.1)
    plt.savefig('dataset/rendi/topKWs-%d.pdf' % 20, dpi=800, bbox_inches='tight',pad_inches=0.1)
    plt.show() 
    
if __name__ == '__main__':
    plt_alpha()

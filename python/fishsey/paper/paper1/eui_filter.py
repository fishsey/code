import paper
  
def sortedEui():
    euipf = r'euiAnalysis/eui.txt'
    result = []
    with open(euipf) as pf:
        for line in pf:
            info = line.strip().split()
            result.append(info)            
    result = sorted(result, key=lambda x: float(x[-1]))
    with open('euiAnalysis/euiSorted.txt','w') as pf:
        for line in result:
            for elem in line:
                pf.write(str(elem) + '\t')
            pf.write('\n')

def filterEuiWithNotGet():
    euipf = r'euiAnalysis/eui.txt'
    result = []
    with open(euipf) as pf:
        for line in pf:
            info = line.strip().split()
            if len(info) == 6:
                result.append(info)
    result = sorted(result, key=lambda x: float(x[-1]))
    with open('euiAnalysis/euiNotGet.txt','w') as pf:
        for line in result:
            for elem in line:
                pf.write(str(elem) + '\t')
            pf.write('\n')
            
def filterEuiWithSmallPui():
    euipf = r'euiAnalysis/eui.txt'
    result = []
    with open(euipf) as pf:
        for line in pf:
            info = line.strip().split()
            pui = float(info[-2])
            if pui < 0.5:
                result.append(info)
    result = sorted(result, key=lambda x: float(x[-1]))
    with open('euiAnalysis/euiSmallPui.txt','w') as pf:
        for line in result:
            for elem in line:
                pf.write(str(elem) + '\t')
            pf.write('\n')
           
def filterEuiWith_1():
    euipf = r'euiAnalysis/eui.txt'
    result = []
    with open(euipf) as pf:
        for line in pf:
            flag1 = 0
            flag2 = 0
            info = line.strip().split()
            if len(info) > 6:
                for index in range(0, len(info) - 5, 3):
                    if float(info[index + 2]) == -1:
                        flag1 = 1
                    if float(info[index]) == -1 and info[index] == info[index + 1] == info[index + 2]:
                        flag2 = 1
            if flag1 == 1 and flag2 != 1:
                result.append(info)
    result = sorted(result, key=lambda x: float(x[-1]))
    with open('euiAnalysis/euiWith-1-c1.txt','w') as pf:
        for line in result:
            for elem in line:
                pf.write(str(elem) + '\t')
            pf.write('\n')



def euiFilter(temp):
    euipf = r'euiAnalysis/eui.txt'
    result = []
    with open(euipf) as pf:
        for line in pf:
            info = line.strip().split()
            if not (int(info[-5]), int(info[-4])) in temp:
                result.append(info)
    result = sorted(result, key=lambda x: float(x[-1]))
    with open('euiAnalysis/euiFilter1.txt','w') as pf:
        for line in result:
            for elem in line:
                pf.write(str(elem) + '\t')
            pf.write('\n')
         
def filters():
    eui_c1 = r'euiAnalysis/euiWith-1-c1.txt'
    eui_NotGet = r'euiAnalysis/euiNotGet.txt'
    result = []
    with open(eui_c1) as pf:
        for line in pf:
            info = line.strip().split()
            u = int(info[-5])
            i = int(info[-4])
            result.append((u, i))
    with open(eui_NotGet) as pf:
        for line in pf:
            info = line.strip().split()
            u = int(info[-5])
            i = int(info[-4])
            result.append((u, i))
    euiFilter(result)   
            
            
if __name__ == '__main__':
    paper.maeAndRmse([-1, 1, 2])
    
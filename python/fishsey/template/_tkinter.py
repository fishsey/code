# -*- coding: utf-8 -*-
"""
Created on Tue Dec 06 21:07:30 2016

@author: fishsey
"""
from numpy import *
from Tkinter import *
import matplotlib
matplotlib.use('TkAgg')
from matplotlib.backends.backend_tkagg import FigureCanvasTkAgg
from matplotlib.figure import Figure


def reDraw(x, y):
    reDraw.f.clf()        # clear the figure
    reDraw.a = reDraw.f.add_subplot(111)
    reDraw.a.plot(x, y, linewidth=2.0) #use plot for yHat
    reDraw.canvas.show()
    
    
def drawNewTree():
    import numpy as np
    x = np.arange(10)
    y = x * 2 + 3
    reDraw(x, y)


root=Tk()

#matplotlib 画布
reDraw.f = Figure(figsize=(5,4), dpi=100) #create canvas
reDraw.canvas = FigureCanvasTkAgg(reDraw.f, master=root)
reDraw.canvas.show()
reDraw.canvas.get_tk_widget().grid(row=0, columnspan=3)

#label1
Label(root, text="tolN").grid(row=1, column=0)
tolNentry = Entry(root)
tolNentry.grid(row=1, column=1)
tolNentry.insert(0,'10')
#label2
Label(root, text="tolS").grid(row=2, column=0)
tolSentry = Entry(root)
tolSentry.grid(row=2, column=1)
tolSentry.insert(0,'1.0')
#按钮
Button(root, text="ReDraw", command=drawNewTree).grid(row=3, column=0, columnspan=1)
#复选框
chkBtnVar = IntVar()
chkBtn = Checkbutton(root, text="Model Tree", variable = chkBtnVar)
chkBtn.grid(row=3, column=1, columnspan=2)

    
root.wm_resizable(800, 400)  
#root.wm_state('zoomed') #全屏       
root.mainloop()
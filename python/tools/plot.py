# -*- coding: utf-8 -*-
"""
Created on Tue Dec 06 21:07:30 2016

@author: fishsey
"""

import matplotlib
matplotlib.use('TkAgg')
from matplotlib.backends.backend_tkagg import FigureCanvasTkAgg
from matplotlib.figure import Figure
   
def reDraw(x, y, figure, canvas, maxIter=100):
    import numpy as np
    figure.clf()        # clear the figure
    axis = figure.add_subplot(111)
    axis.grid(True)
    axis.set_xlim(0, maxIter)
    axis.set_ylim(np.min(y), 1)
    axis.set_yticks(np.linspace(np.min(y), 1, 21))
    axis.plot(x, y, linewidth=0.5) #use plot for yHat
    canvas.show()

def init(root):
    figure = Figure(figsize=(5,4), dpi=100) #create canvas
    canvas = FigureCanvasTkAgg(figure, master=root)
    canvas.show()
    canvas.get_tk_widget().grid(row=0, columnspan=3)
    canvas.show()
    return figure, canvas
    

if __name__ == "__main__":
    import time
    import numpy as np
    import Tkinter as tk
    root = tk.Tk()
    figure, canvas = init(root)    
    for i in range(1, 11):
        x = np.arange(i * 2 + 1)/100.0
        y = x
        reDraw(x, y, figure, canvas, 1)
        time.sleep(0.2)
    root.mainloop()
        
    
            
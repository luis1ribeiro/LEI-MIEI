import pandas as pd

# Give the location of the file 
data = pd.read_excel('paragem_autocarros_oeiras_processado_4.xlsx') 

paragens = pd.DataFrame(data)

for p in paragens.values:
    print('paragem(', end="")
    lg = len(p)
    i = 0
    for pi in p:
       if (i == 7):
           print('[', end="")
           print(pi, end="")
           print(']', end=", ")
       elif (i == 8 or i == 0 or i == 1 or i == 2):
           print(pi,end=", ")
       elif (i == lg-1):
           print('"',end="")
           print(pi,end='"')
       elif (i==3 or i==4 or i==5 or i==6 or i==9):
           print('"',end="")
           print(pi,end='", ')
       i = i+1
    print(').')
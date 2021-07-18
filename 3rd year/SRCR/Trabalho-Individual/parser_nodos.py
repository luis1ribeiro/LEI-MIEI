import pandas as pd
import os
import subprocess
import math as mt

# Give the location of the file 
xls = pd.ExcelFile('lista_adjacencias_paragens.xlsx')

sheet_to_df_map = {}
val1 = 0
val2 = 0
valt = 0
j = 0
arr = []

for sheet_name in xls.sheet_names:
    sheet_to_df_map[sheet_name] = xls.parse(sheet_name)

    arr = sheet_name + ".pl"
    f = open(arr, "w")

    sheet = sheet_to_df_map[sheet_name].values

    i = 0
    while (i < len(sheet)-1):
    	gid1 = sheet[i][0]
    	gid2 = sheet[i+1][0]
    	val1 = float(sheet[i][1])
    	val2 = float(sheet[i][2])
    	val3 = float(sheet[i+1][1])
    	val4 = float(sheet[i+1][2])
    	s_name = sheet[i][7]
    	dist = mt.sqrt(pow(val1-val3,2) + pow(val2-val4,2))
    	arr2 = "nodo(" + str(gid1) + ", " + str(gid2) + ", " + str(dist) + ", " + str(s_name) + ").\n"
    	i = i+1
    	f.write(arr2)	

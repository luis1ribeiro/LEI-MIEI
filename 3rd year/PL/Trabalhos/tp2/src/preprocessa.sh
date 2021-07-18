#!/bin/bash

EXT=$(echo $1 | rev | cut -f 1 -d '.')
NAME=$(basename $1 .${EXT})

cd pre
flex pre.fl
cc lex.yy.c -o pre
./pre < ../$1 > ../input/${NAME}_corrigido.txt
rm lex.yy.c pre
cd ..

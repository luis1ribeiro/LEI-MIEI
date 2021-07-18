#!/bin/bash

echo $1

if [ "$1" == "" ]; then
  echo "Falta argumento"
  exit -1
fi

filename=$(basename -- "$1")
#filename=$1
extension="${filename##*.}"
filename="${filename%.*}"

#echo $extension $filename
#aa=`basename $1 .l`
if [ "$extension" == "l" ]; then
  flex $1
  #echo "fim flex"
  if [ -e "./lex.yy.c" ]; then
    gcc -o $filename lex.yy.c
    ./$filename
  fi
fi
#flex $1



#!/bin/bash

if [ "$1" == "" ]; then
  echo "Falta argumento"
  exit -1
fi

#filename=$(basename -- "$1")
filename=$1
extension="${filename##*.}"
filename="${filename%.*}"

if [ "$extension" == "c" ]; then
  if [ -e $filename ]; then
    rm $filename
    echo "O executável foi apagado"
    echo "criando um novo..."
    sleep 2
    gcc -o $filename $1
    echo -e "DONE\n----------------\n\n"
    ./$filename
  else
    echo "compilando..."
    sleep 2
    gcc -o $filename $1
    echo -e "DONE\n\n"
    ./$filename
  fi
else
  echo "Não é possível compilar"
fi


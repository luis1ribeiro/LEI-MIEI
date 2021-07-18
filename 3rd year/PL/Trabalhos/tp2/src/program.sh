#!/bin/bash

make
if [[ $1 == "-p" ]]
then
    ./preprocessa.sh $2
    EXT=$(echo $2 | rev | cut -f 1 -d '.')
    NAME=$(basename $2 .${EXT})
    ./pl2 < input/${NAME}_corrigido.txt > output/${NAME}_corrigido.txt
else
    NAME=$(basename $1)
    ./pl2 < $1 > output/$NAME
fi
make clean

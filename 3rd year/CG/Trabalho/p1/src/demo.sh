#!/bin/bash

cd build
echo "Generating plane"
./generator plane 3 3 plane.3d
echo "Generating box"
./generator box 2 2 1 4 box.3d
echo "Generating sphere"
./generator sphere 1 50 50 sphere.3d
echo "Generating cone"
./generator cone 1 3 50 50 cone.3d
echo "Showing scene"
./engine

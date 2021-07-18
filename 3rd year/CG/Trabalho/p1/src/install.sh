#!/bin/bash

echo "Creating models directory"
mkdir -p models
touch config.xml
echo "Creating build directory"
mkdir build
echo "Building..."
cd build
cmake ..
echo "Making executables"
make

# CG

This is a work for University that generates points to draw figures and shows them.

## Installation

To install simply run the command:

```bash
$ ./install.sh
```
You can uninstall to:

```bash
$ ./uninstall.sh
```
But run this command with caution because this will remove your generated files and config.xml file.

## Usage

This will generate two executables on ./build directory.

```bash
$ ./generator sphere 1 10 10 sphere.3d             # this will generate a sphere with 1 radius, 10 slices and 10 stacks
$ ./engine                                        # this will draw all your models
```
You can generate:

```bash
plane Xdim Zdim filename
box Xdim Zdim Ydim divisions filename
sphere radius slices stacks filename
cone radius heigh slices stacks filename
```

On the engine, the program prints a little menu to help you with the key bindings.

If you want, we have a demo scene, just install and then run the following command:

```bash
$ ./demo.sh
```

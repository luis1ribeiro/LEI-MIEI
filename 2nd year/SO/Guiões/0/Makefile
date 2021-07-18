CC = gcc
CFLAGS = -g -Wall

program: program.o header.o

headers.o: header.h header.c

program.o: header.h program.c

clean:
	rm -f program *.o

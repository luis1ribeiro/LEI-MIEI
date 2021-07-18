SHELL=bash

pl2: pl2.y pl2.fl
	flex pl2.fl
	yacc -d pl2.y
	cc -o pl2 y.tab.c lex.yy.c -lm

install: pl2
	cp pl2 /usr/local/bin/

uninstall: pl2
	rm pl2 /usr/local/bin

clean:
	rm -f lex.yy.c y.tab.* pl2

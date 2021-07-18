# ver tambem.... https://natura.di.uminho.pt/~jj/pl-20/aulas
ex2:  ex2.y ex2.fl
	yacc ex2.y
	flex ex2.fl
	cc y.tab.c -o ex2

ex1:	ex1.y
	yacc ex1.y
	cc y.tab.c -o ex1


automato.pdf: ex1.y
	yacc -g ex1.y
	dot -Tpdf y.dot > automato.pdf


clean:
	rm -f y.tab.c lex.yy.c ex1 ex2 _.dot _.pdf


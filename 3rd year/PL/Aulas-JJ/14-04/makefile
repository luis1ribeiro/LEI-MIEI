## Ver texto introdutÃ³rio da folha2 (BB), exemplo.txt


# F2.35 gramÃ¡tica de XML (e analisador lexico...)

ex-xml:  ex-xml.fl ex-xml.y
	flex ex-xml.fl
	yacc -d ex-xml.y
	cc y.tab.c lex.yy.c -o ex-xml

#3 - alguma limpeza ...
clean:
	rm -f testaALex ex1-inc ex1-lp lex.yy.c y.tab.c y.tab.h


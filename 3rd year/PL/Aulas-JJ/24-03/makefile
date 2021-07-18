## Ver texto introdutÃ³rio da folha2 (BB), exemplo.txt

# compilaÃ§Ã£o separada, Linguagem dos parentesis / F2.32

ex1-lp:  ex1-lp.fl ex1-lp.y
	flex ex1-lp.fl
	yacc -d ex1-lp.y
	cc y.tab.c lex.yy.c -o ex1-lp

#3 - alguma limpeza ...
clean:
	rm -f testaALex ex1-inc ex1-lp lex.yy.c y.tab.c y.tab.h

# F2.35 gramÃ¡tica de XML (e analisador lexico...)

ex-xml:  ex-xml.fl ex-xml.y
	flex ex-xml.fl
	yacc -d ex-xml.y
	cc y.tab.c lex.yy.c -o ex-xml

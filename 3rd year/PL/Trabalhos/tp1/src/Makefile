CFLAGS=$(shell pkg-config --cflags glib-2.0)
LFLAGS=$(shell pkg-config --libs glib-2.0)

template: lex.vars.c lex.tmpl.c
	gcc -o mkfromtemplate $(CFLAGS) template.c lex.tmpl.c lex.vars.c $(LFLAGS)

lex.tmpl.c: filtro.fl
	flex filtro.fl

lex.vars.c: vars.fl
	flex vars.fl

clean:
	rm mkfromtemplate lex.vars.c lex.tmpl.c

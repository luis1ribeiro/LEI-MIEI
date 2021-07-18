CC = gcc
CFLAGS = -Wall -ggdb -ansi -O2 -D_GNU_SOURCE
GLIBFLAG = `pkg-config --cflags --libs glib-2.0`

main: catProd Produto Cliente CatCli Faturacao Filial Venda Queries Navegador
	$(CC) $(GLIBFLAG) $(CFLAGS) -o main main.c CatProds.o Produto.o Cliente.o CatCli.o Faturacao.o Filial.o Venda.o Queries.o Navegador.o

catProd:
	$(CC) $(GLIBFLAG) $(CFLAGS) -c CatProds.c

Produto:
	$(CC) $(GLIBFLAG) $(CFLAGS) -c Produto.c

Cliente:
	$(CC) $(GLIBFLAG) $(CFLAGS) -c Cliente.c

CatCli:
	$(CC) $(GLIBFLAG) $(CFLAGS) -c CatCli.c

Faturacao:
	$(CC) $(GLIBFLAG) $(CFLAGS) -c Faturacao.c

Filial:
	$(CC) $(GLIBFLAG) $(CFLAGS) -c Filial.c

Venda:
	$(CC) $(GLIBFLAG) $(CFLAGS) -c Venda.c

Queries:
	$(CC) $(GLIBFLAG) $(CFLAGS) -c Queries.c

Navegador:
	$(CC) $(GLIBFLAG) $(CFLAGS) -c Navegador.c

clean:
	rm main CatProds.o Produto.o CatCli.o Cliente.o Faturacao.o Venda.o Queries.o Filial.o Navegador.o

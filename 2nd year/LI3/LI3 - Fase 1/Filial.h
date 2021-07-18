#ifndef Filial_h
#define Filial_h

#include "Venda.h"
#include <glib.h>

typedef struct filial* Filial;

Filial inicializaFilial();
Filial insereFilV (Filial f, Venda v);
int nr_clientesCompraram(Filial f, int filial, int mes);
GArray* clientesCompraram(Filial f, int filial, int mes);
int nr_compraCli (Filial f, Cliente c, int mes, int filial);
int fat_compraCli (Filial f, Cliente c, int mes, int filial);
int cliente_nc(Filial f, Cat_Cli catc);
GArray* getCompT(Filial f, Cat_Cli catc);
int vendasProdutoFil(Filial f, Produto p, int mes, char tipo, int fil);
float fatProdutoFil(Filial f, Produto p, int mes, char tipo, int fil);
GArray* cliCompProd (Filial f, Produto p, int filial, char tipo);
GArray* tresProdutos (Filial f, Cliente c);
GArray* prodCompDec (Filial f, Cliente c, int mes);
GArray* prodNcomp (Filial f, int filial, int N);

#endif

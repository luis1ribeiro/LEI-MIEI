#ifndef Faturacao_h
#define Faturacao_h

#include "Venda.h"
#include <glib.h>

typedef struct faturacao* Faturacao;

Faturacao inicializaFaturacao();
Faturacao insereVenda(Faturacao fatp, Venda v);
int nrVendaProduto(Faturacao fatp, Produto p, int mes, char tipo);
float fatProduto(Faturacao fatp, Produto p, int mes, char tipo);
int atualizanc (Faturacao fatp, Cat_Prods catp);
int getNaoComprados_nr (Faturacao fatp);
GArray* getNaoComprados (Faturacao fatp);
int getVendasEntre(Faturacao fatp, int x, int y);
float getFatEntre(Faturacao fatp, int x, int y);
GArray* getNaoCompFil(Faturacao fatp, Cat_Prods catp, int filial);

#endif

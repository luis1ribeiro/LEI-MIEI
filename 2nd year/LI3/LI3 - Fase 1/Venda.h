#ifndef Venda_h
#define Venda_h

#include "CatProds.h"
#include "CatCli.h"

typedef struct vend* Venda;

Venda criaVenda (char* venda,Cat_Prods catp,Cat_Cli catc);
int getUniVenda (Venda v);
float getPrecoVenda (Venda v);
char * getProdVenda (Venda v);
int getMes (Venda v);
char getTipoVenda (Venda v);
char * getCliVenda (Venda v);
int getFilial (Venda v);


#endif

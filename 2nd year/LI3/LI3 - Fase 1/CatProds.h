#ifndef CatProds_h
#define CatProds_h

#include <glib.h>
#include "Produto.h"

typedef struct cat_prods* Cat_Prods;
typedef struct lst_prods* Lista_Prods;

Cat_Prods inicializa_CatProds();
Cat_Prods insereProd (Cat_Prods catp, Produto p);
int existeProd (Cat_Prods catp, Produto p);
int nrProds (Cat_Prods catp);
Lista_Prods produtosPorLetra (Cat_Prods catp, char letra);
GHashTable* getProdutosLetra (Lista_Prods lst, int i);

#endif

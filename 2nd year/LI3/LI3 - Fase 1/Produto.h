#ifndef Produto_h
#define Produto_h

typedef struct prod* Produto;

Produto criaProduto (char * produto);
char* getProduto (Produto p);
char primeiraLetra (Produto p);
char segundaLetra (Produto p);
int validaProduto (Produto p);

#endif

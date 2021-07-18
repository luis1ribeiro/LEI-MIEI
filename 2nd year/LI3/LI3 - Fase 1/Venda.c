#include "Venda.h"
#include <stdlib.h>
#include <stdio.h>
#include <string.h>

typedef struct vend{
  char* produto;
  float preco;
  int unidades;
  char compra;
  char *cliente;
  int mes;
  int filial;
}vend;

int validaVenda (char* campos [],Cat_Prods catp,Cat_Cli catc){
  Produto p = criaProduto(campos[0]);
  Cliente c = criaCliente(campos[4]);
  if (!existeCli(catc,c)) return 0;
  if (!existeProd(catp,p)) return 0;
  int unidades=atoi(campos[2]);
  float preco=atof(campos[1]);
  int mes=atoi(campos[5]);
  int filial=atoi(campos[6]);
  if (preco<0.0 || preco>999.99) return 0;
  if (unidades<1 || unidades>200) return 0;
  if (mes<1 || mes>12) return 0;
  if (filial<1 || filial>3) return 0;
  if (*campos[3]!='N' && *campos[3]!='P') return 0;
  return 1;
}

Venda criaVenda (char* linha_venda,Cat_Prods catp,Cat_Cli catc){
  int i=0;
  char* campos [10];
  char *token = strtok(linha_venda," ");
  while(token!=NULL){
    campos[i]=token;
    token=strtok(NULL," ");
    i++;
  }
  if (!validaVenda(campos,catp,catc)) return NULL;
  Venda v = malloc (sizeof (struct vend));
  v->produto=strdup(campos[0]);
  v->preco=atof(campos[1]);
  v->unidades=atoi(campos[2]);
  v->compra=*campos[3];
  v->cliente=strdup(campos[4]);
  v->mes=atoi(campos[5]);
  v->filial=atoi(campos[6]);
  return v;
}

int getUniVenda (Venda v){
  return v->unidades;
}

float getPrecoVenda (Venda v){
  return v->preco;
}

char* getProdVenda (Venda v){
  return v->produto;
}

int getMes (Venda v){
  return v->mes;
}

char getTipoVenda (Venda v){
  return v->compra;
}

char* getCliVenda (Venda v){
  return v->cliente;
}

int getFilial (Venda v){
  return v->filial;
}



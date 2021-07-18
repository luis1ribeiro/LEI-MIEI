#include "Produto.h"
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <ctype.h>

typedef struct prod{
  char* codigo;
}prod;

Produto criaProduto (char* produto){
  Produto p=malloc(sizeof(struct prod));
  p->codigo=strdup(produto);
  return p;
}

char* getProduto (Produto p){
  return p->codigo;
}

char primeiraLetra (Produto p){
  return p->codigo[0];
}

char segundaLetra (Produto p){
  return p->codigo[1];
}

int validaProduto (Produto p){
  if (p->codigo==NULL) return 0;
  char fst_letra=primeiraLetra(p);
  char snd_letra=segundaLetra(p);
  if (!(isalpha(fst_letra) && fst_letra>=65 && fst_letra<=90)) return 0;
  if (!(isalpha(snd_letra) && snd_letra>=65 && snd_letra<=90)) return 0;
  int n=atoi(p->codigo+2);
  if (n<1000 || n>9999) return 0;
  return 1;
}

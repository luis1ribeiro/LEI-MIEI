#include <stdio.h>
#include "CatProds.h"

typedef struct lst_prods{
  GHashTable* produtosLetra [26];
}lst_prods;

typedef struct cat_prods{
  GHashTable* produtos [26][26];
  int nr_produtos;
}cat_prods;

Cat_Prods inicializa_CatProds(){
  Cat_Prods new = malloc (sizeof (struct cat_prods));
  int i,j;
  for (i=0;i<26;i++){
    for (j=0;j<26;j++){
      new->produtos[i][j] = g_hash_table_new (g_str_hash,g_str_equal);
    }
  }
  new->nr_produtos=0;
  return new;
}

Cat_Prods insereProd (Cat_Prods catp, Produto p){
  char fst_letra, snd_letra;
  fst_letra=primeiraLetra(p);
  snd_letra=segundaLetra(p);
  int i=fst_letra-'A';
  int j=snd_letra-'A';
  g_hash_table_insert(catp->produtos[i][j],getProduto(p),getProduto(p));
  catp->nr_produtos++;
  return catp;
}

int existeProd (Cat_Prods catp, Produto p){
  char fst_letra, snd_letra;
  fst_letra=primeiraLetra(p);
  snd_letra=segundaLetra(p);
  int i=fst_letra-'A';
  int j=snd_letra-'A';
  if (g_hash_table_lookup(catp->produtos[i][j],getProduto(p))==NULL) return 0;
  return 1;
}

int nrProds (Cat_Prods catp){
  return catp->nr_produtos;
}

Lista_Prods produtosPorLetra (Cat_Prods catp, char letra){
  int j=letra-'A';
  Lista_Prods new = malloc (sizeof (struct lst_prods));
  int i;
  for (i=0;i<26;i++){
    new->produtosLetra[i]=catp->produtos[j][i];
  }
  return new;
}

GHashTable* getProdutosLetra (Lista_Prods lst, int i){
  return lst->produtosLetra[i];
}


#include "CatCli.h"
#include <stdio.h>

typedef struct cat_cli{
  GHashTable* clientes [26];
  int nr_clientes;
}cat_cli;

Cat_Cli inicializa_CatCli(){
  Cat_Cli new = malloc (sizeof (struct cat_cli));
  int i;
  for (i=0;i<26;i++)
    new->clientes[i] = g_hash_table_new (g_str_hash,g_str_equal);
  new->nr_clientes=0;
  return new;
}

Cat_Cli insereCli (Cat_Cli catc, Cliente c){
  char fst_letra;
  fst_letra=primeiraLetraCli(c);
  int i=fst_letra-'A';
  g_hash_table_insert(catc->clientes[i],getCliente(c),getCliente(c));
  catc->nr_clientes++;
  return catc;
}

int existeCli (Cat_Cli catc, Cliente c){
  char fst_letra;
  fst_letra=primeiraLetraCli(c);
  int i=fst_letra-'A';
  if (g_hash_table_lookup(catc->clientes[i],getCliente(c))==NULL) return 0;
  return 1;
}

GHashTable* clientesLetra(Cat_Cli catc,char p){
  int i=p-'A';
  return catc->clientes[i];
}

int nrCli (Cat_Cli catc){
  return catc->nr_clientes;
}

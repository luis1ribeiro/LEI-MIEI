#include "Cliente.h"
#include <string.h>
#include <stdlib.h>
#include <ctype.h>

typedef struct cli{
  char* codigo;
}cli;

Cliente criaCliente (char* cliente){
  Cliente c=malloc(sizeof(struct cli));
  c->codigo=strdup(cliente);
  return c;
}

char* getCliente (Cliente c){
  return c->codigo;
}

char primeiraLetraCli (Cliente c){
  return c->codigo[0];
}

int validaCliente (Cliente c){
  if (c->codigo==NULL) return 1;
  if (!(isalpha(c->codigo[0]) && c->codigo[0]>=65 && c->codigo[0]<=90)) return 0;
  int n=atoi(c->codigo+1);
  if (n<1000 || n>5000) return 0;
  return 1;
}

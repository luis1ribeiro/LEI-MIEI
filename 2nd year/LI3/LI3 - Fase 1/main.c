#include <glib.h>
#include "CatProds.h"
#include <stdio.h>
#include <string.h>
#include "CatCli.h"
#include "Faturacao.h"
#include "Queries.h"
#include "Navegador.h"
#include <time.h>
#include <unistd.h>

int main (){
  Cat_Prods catp=inicializa_CatProds();
  Cat_Cli catc=inicializa_CatCli();
  Faturacao fatp=inicializaFaturacao();
  Filial f=inicializaFilial();
  menuQueries(catp,catc,fatp,f);
  return 0;
}

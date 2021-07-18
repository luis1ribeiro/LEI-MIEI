#include "Queries.h"
#include <stdio.h>
#include <string.h>
#include <time.h>
#include <math.h>

int querieUmP(Cat_Prods catp){
  FILE *prods;
  int lidos=0;int validos=0;
  char buffer [10];
  prods=fopen("Produtos.txt","r");
  while(fgets(buffer,10,prods)){
    lidos++;
    strtok(buffer,"\n\n");
    Produto p =criaProduto(buffer);
    if (validaProduto(p)){
      insereProd(catp,p);
      validos++;
    }
  }
  printf("\n => Produtos.txt :  Dos %d produtos lidos, %d sao validos\n\n",lidos,validos);
  fclose(prods);
  return 0;
}

int querieUmC(Cat_Cli catc){
  FILE *cli;
  int lidos=0;int validos=0;
  char buffer [10];
  cli=fopen("Clientes.txt","r");
  while(fgets(buffer,10,cli)){
    strtok(buffer,"\n\n");
    lidos++;
    Cliente c = criaCliente(buffer);
    if (validaCliente(c)){
      validos++;
      insereCli(catc,c);
    }
  }
  printf("\n => Clientes.txt :  Dos %d clientes lidos, %d sao validos\n\n",lidos,validos);
  fclose(cli);
  return 0;
}

int querieUmV (Faturacao fatp, Filial f, Cat_Prods catp, Cat_Cli catc){
  FILE *vend;
  char buffer [64];
  int lidos=0;int validos=0;
  vend=fopen("Vendas_1M.txt","r");
  while(fgets(buffer,64,vend)){
    lidos++;
    strtok(buffer,"\n\n");
    Venda v = criaVenda(buffer,catp,catc);
    if (v){
      validos++;
      insereVenda(fatp,v);
      insereFilV(f,v);
    }
  }
  printf("\n => Vendas.txt :  Das %d vendas lidas, %d sao validas\n\n",lidos,validos);
  fclose(vend);
  return 0;
}

int querieTres (Faturacao fatp, Filial f, Produto p, int mes){
  system("clear");
  printf("\n                   Produto: %s\n", getProduto(p));
  printf("        Escolha o tipo de resultados:\n");
  printf(" 1 -> Filial 1\n 2 -> Filial 2\n 3 -> Filial 3\n 4 -> Global\n");
  int fil;
  scanf("%d",&fil);
  system("clear");
  printf("\n                   Produto: %s\n", getProduto(p));
  if (fil==4){
    printf("                       Resultados Globais:");
    printf("\n\n        >   Modo: Normal     Mes: %d   Produto: %s   Vendas: %d   Faturado: %.2f €\n",mes,getProduto(p),nrVendaProduto(fatp,p,mes,'N'),fatProduto(fatp,p,mes,'N'));
    printf("        >   Modo: Promocao   Mes: %d   Produto: %s   Vendas: %d   Faturado: %.2f €\n",mes,getProduto(p),nrVendaProduto(fatp,p,mes,'P'),fatProduto(fatp,p,mes,'P'));
  }
  if (fil>0 && fil<4){
    printf("                      Restulados da filial %d",fil);
    printf("\n\n         >   Modo: Normal     Mes: %d   Produto: %s   Vendas: %d   Faturado: %.2f €\n",mes,getProduto(p),vendasProdutoFil(f,p,mes,'N',fil),fatProdutoFil(f,p,mes,'N',fil));
    printf("         >   Modo: Promocao   Mes: %d   Produto: %s   Vendas: %d   Faturado: %.2f €\n",mes,getProduto(p),vendasProdutoFil(f,p,mes,'P',fil),fatProdutoFil(f,p,mes,'P',fil));
  }
  return 0;
}

gboolean guarda_arr (gpointer key, gpointer value, gpointer data){
  char* p = (char*) value;
  GArray* *arr=data;
  g_array_append_val(*arr,p);
  return FALSE;
}

int querieDois (Cat_Prods catp, char l, int x, int y){
  GArray* arr = g_array_new(FALSE,FALSE,sizeof(char*));
  Lista_Prods lst = produtosPorLetra(catp,l);
  int i;
  for(i=0;i<26;i++){
    GHashTable *sndl = getProdutosLetra(lst,i);
    g_hash_table_foreach(sndl,(GHFunc)guarda_arr,&arr);
  }
  int a,b;
  i=0;
  int page=1;
  while(i>=0 && i<arr->len){
    system("clear");
    printf("       # de produtos comecados por %c: %d\n\n",l,arr->len);
    printf("                  Pagina: %d\n\n",page);
    for (a=0;a<x;a++){
      for (b=0;b<y;b++){
        if(g_array_index(arr,char*,i)==NULL) break;
        printf("%s ",g_array_index(arr,char*,i++));
      }
      printf("\n");
    }
    printf("\n\n p -> Proxima\n a -> Anterior\n b-> Voltar\n");
    char c;
    scanf("%c",&c);
    while (c!='p' && c!='a' && c!='b'){
      if (c!='\n') perror("Letra invalida");
      scanf("%c",&c);
    }
    if (c=='p'){
      if (page<=ceil(arr->len/(x*y)))
        page++;
      else{
        i=i-(arr->len%(x*y));
      }
    }
    if (c=='a'){
      if (page>1){
        page--;
        i=i-(x*y*2);
      }
      else{
        i=i-(x*y);
      }
    }
    if (c=='b') break;
  }
  g_array_free(arr,FALSE);
  free(lst);
  return 0;
}

int querieQuatro (Faturacao fatp, Cat_Prods catp,int x, int y,int fil){
  int a,b;
  int i;
  i=0;
  int page=1;
  system("clear");
  GArray* arr=NULL;
  int len=0;
  if (fil==4){
    arr=getNaoComprados(fatp);
    len=getNaoComprados_nr(fatp);
  }
  if (fil>0 && fil<4){
    arr=getNaoCompFil(fatp,catp,fil);
    len=arr->len;
  }
  while(i>=0 && i<arr->len){
    system("clear");
    printf("       # de produtos nao vendidos: %d\n\n",len);
    printf("                  Pagina: %d\n\n",page);
    for (a=0;a<x;a++){
      for (b=0;b<y;b++){
        if(g_array_index(arr,char*,i)==NULL) break;
        printf("%s ",g_array_index(arr,char*,i++));
      }
      printf("\n");
    }
    printf("\n\n p -> Proxima\n a -> Anterior\n b-> Voltar\n");
    char c;
    scanf("%c",&c);
    while (c!='p' && c!='a' && c!='b'){
      if (c!='\n') perror("Letra invalida");
      scanf("%c",&c);
    }
    if (c=='p'){
      if (page<=ceil(len/(x*y)))
        page++;
      else{
        i=i-(len%(x*y));
      }
    }
    if (c=='a'){
      if (page>1){
        page--;
        i=i-(x*y*2);
      }
      else{
        i=i-(x*y);
      }
    }
    if (c=='b') break;
  }
  g_array_free(arr,FALSE);
  return 0;
}

int querieCinco (Filial f, Cat_Cli catc, int x, int y){
  int a,b;
  int i;
  i=0;
  GArray *arr = getCompT(f,catc);
  int len = arr->len;
  int page=1;
  while(i>=0 && i<arr->len){
    system("clear");
    printf("       # de clientes compraram todas as filiais: %d\n\n",len);
    printf("                  Pagina: %d\n\n",page);
    for (a=0;a<x;a++){
      for (b=0;b<y;b++){
        if(g_array_index(arr,char*,i)==NULL) break;
        printf("%s ",g_array_index(arr,char*,i++));
      }
      printf("\n");
    }
    printf("\n\n p -> Proxima\n a -> Anterior\n b-> Voltar\n");
    char c;
    scanf("%c",&c);
    while (c!='p' && c!='a' && c!='b'){
      if (c!='\n') perror("Letra invalida");
      scanf("%c",&c);
    }
    if (c=='p'){
      if (page<=ceil(len/(x*y)))
        page++;
      else{
        i=i-(len%(x*y));
      }
    }
    if (c=='a'){
      if (page>1){
        page--;
        i=i-(x*y*2);
      }
      else{
        i=i-(x*y);
      }
    }
    if (c=='b') break;
  }
  g_array_free(arr,FALSE);
  return 0;
}

int querieSeis(Filial f, Faturacao fatp, Cat_Cli catc){
  printf("\n\n          >   Numero de clientes que nao compraram: %d\n",cliente_nc(f,catc));
  printf("          >   Numero de produtos nao comprados: %d\n",getNaoComprados_nr(fatp));
  return 0;
}

int querieSete(Filial f, Cliente c){
  system("clear");
  printf("\n               Cliente: %s\n\n",getCliente(c));
  printf("Janeiro:   Filial 1-> %d  Filial 2-> %d  Filial 3-> %d\n\n",nr_compraCli(f,c,1,1),nr_compraCli(f,c,1,2),nr_compraCli(f,c,1,3));
  printf("Fevereiro:   Filial 1-> %d  Filial 2-> %d  Filial 3-> %d\n\n",nr_compraCli(f,c,2,1),nr_compraCli(f,c,2,2),nr_compraCli(f,c,2,3));
  printf("Marco:   Filial 1-> %d  Filial 2-> %d  Filial 3-> %d\n\n",nr_compraCli(f,c,3,1),nr_compraCli(f,c,3,2),nr_compraCli(f,c,3,3));
  printf("Abril:   Filial 1-> %d  Filial 2-> %d  Filial 3-> %d\n\n",nr_compraCli(f,c,4,1),nr_compraCli(f,c,4,2),nr_compraCli(f,c,4,3));
  printf("Maio:   Filial 1-> %d  Filial 2-> %d  Filial 3-> %d\n\n",nr_compraCli(f,c,5,1),nr_compraCli(f,c,5,2),nr_compraCli(f,c,5,3));
  printf("Junho:   Filial 1-> %d  Filial 2-> %d  Filial 3-> %d\n\n",nr_compraCli(f,c,6,1),nr_compraCli(f,c,6,2),nr_compraCli(f,c,6,3));
  printf("Julho:   Filial 1-> %d  Filial 2-> %d  Filial 3-> %d\n\n",nr_compraCli(f,c,7,1),nr_compraCli(f,c,7,2),nr_compraCli(f,c,7,3));
  printf("Agosto:   Filial 1-> %d  Filial 2-> %d  Filial 3-> %d\n\n",nr_compraCli(f,c,8,1),nr_compraCli(f,c,8,2),nr_compraCli(f,c,8,3));
  printf("Setembro:   Filial 1-> %d  Filial 2-> %d  Filial 3-> %d\n\n",nr_compraCli(f,c,9,1),nr_compraCli(f,c,9,2),nr_compraCli(f,c,9,3));
  printf("Outubro:   Filial 1-> %d  Filial 2-> %d  Filial 3-> %d\n\n",nr_compraCli(f,c,10,1),nr_compraCli(f,c,10,2),nr_compraCli(f,c,10,3));
  printf("Novembro:   Filial 1-> %d  Filial 2-> %d  Filial 3-> %d\n\n",nr_compraCli(f,c,11,1),nr_compraCli(f,c,11,2),nr_compraCli(f,c,11,3));
  printf("Dezembro:   Filial 1-> %d  Filial 2-> %d  Filial 3-> %d\n\n",nr_compraCli(f,c,12,1),nr_compraCli(f,c,12,2),nr_compraCli(f,c,12,3));
  return 0;
}

int querieOito (Faturacao fatp, int x, int y){
  system("clear");
  printf("\n\n     >    Numero de vendas entre [%d...%d]: %d\n",x,y,getVendasEntre(fatp,x,y));
  printf("     >    Total faturado entre [%d..%d]: %.2f €\n",x,y,getFatEntre(fatp,x,y));
  return 0;
}

int querieNove (Filial f, Produto p, int filial, char tipo, int x, int y){
  int a,b;
  int i;
  i=0;
  GArray *arr = cliCompProd(f,p,filial,tipo);
  int len = arr->len;
  system("clear");
  if (len==0) printf("\n\n          O produto %s nao foi vendido nessas condicoes\n",getProduto(p));
  int page=1;
  while(i>=0 && i<arr->len){
    system("clear");
    printf("       # de clientes compraram produto %s na filial %d do tipo %c: %d\n\n",getProduto(p),filial,tipo,len);
    printf("                  Pagina: %d\n\n",page);
    for (a=0;a<x;a++){
      for (b=0;b<y;b++){
        if(g_array_index(arr,char*,i)==NULL) break;
        if (i<len) printf("%s ",g_array_index(arr,char*,i++));
      }
      printf("\n");
    }
    printf("\n\n p -> Proxima\n a -> Anterior\n b-> Voltar\n");
    char c;
    scanf("%c",&c);
    while (c!='p' && c!='a' && c!='b'){
      if (c!='\n') perror("Letra invalida");
      scanf("%c",&c);
    }
    if (c=='p'){
      if (page<=ceil(len/(x*y)))
        page++;
      else{
        i=i-(len%(x*y));
      }
    }
    if (c=='a'){
      if (page>1){
        page--;
        i=i-(x*y*2);
      }
      else{
        i=i-(x*y);
      }
    }
    if (c=='b') break;
  }
  g_array_free(arr,FALSE);
  return 0;
}

int querieDez(Filial f, Cliente c, int mes){
  GArray *arr=prodCompDec(f,c,mes);
  system("clear");
  printf("\n       Produtos comprados pelo cliente %s no mes %d por ordem decrescente de quantidade\n\n\n",getCliente(c),mes);
  int i;
  for (i=0;i<arr->len;i++){
    printf("  %dº-> %s\n",i+1,g_array_index(arr,char*,i));
  }
  g_array_free(arr,FALSE);
  return 0;
}

int querieOnze(Filial f, int filial, int n){
  GArray *arr=prodNcomp(f,filial,n);
  system("clear");
  if (filial==0) printf("\n       Produtos mais vendidos globais: \n\n\n");
  else printf("\n        Produtos mais vendidos na filial %d:\n\n\n",filial);
  int i;
  for (i=0;i<arr->len;i++){
    printf("  %dº-> %s\n",i+1,g_array_index(arr,char*,i));
  }
  g_array_free(arr,FALSE);
  return 0;
}

int querieDoze (Filial f, Cliente c){
  GArray *arr = tresProdutos(f,c);
  system("clear");
  printf("\n        O cliente %s gastou mais dinheiro nestes 3 produtos\n",getCliente(c));
  printf("\n\n 1º-> %s\n",g_array_index(arr,char*,0));
  printf(" 2º-> %s\n",g_array_index(arr,char*,1));
  printf(" 3º-> %s\n",g_array_index(arr,char*,2));
  g_array_free(arr,FALSE);
  return 0;
}

#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include "Navegador.h"
#include "Queries.h"
#include <time.h>
#include <unistd.h>

int querieUmMenu (Cat_Prods catp, Cat_Cli catc,Faturacao fatp, Filial f){
  system("clear");
  printf("\n          ==> Escolha que ficheiros quer ler  <==  \n\n\n");
  printf("       > Produtos.txt                      1 \n\n");
  printf("       > Clientes.txt                      2 \n\n");
  printf("       > Vendas.txt                        3 \n\n");
  printf("       > Todos os ficheiros disponiveis    4 \n");
  printf("\n\nb: voltar ao menu inicial\n");
  int fich;
  scanf("%d",&fich);
  if (fich==1){
    querieUmP(catp);
  }
  if (fich==2){
      querieUmC(catc);
  }
  if (fich==3){
      querieUmV(fatp,f,catp,catc);
  }
  if (fich==4){
    querieUmP(catp);
    querieUmC(catc);
    querieUmV(fatp,f,catp,catc);
  }
  char c;
  scanf("%s",&c);
  return 0;
}

int querieDoisMenu(Cat_Prods catp){
  system("clear");
  printf("Que letra queres pesquisar?\n");
  char c;
  scanf("%s",&c);
  if (c<'A' || c>'Z'){
    perror("letra invalida");
    scanf("%s",&c);
  }
  printf("\nEscolhe o numero de linhas e colunas no formato [int int]\n");
  int x,y;
  scanf("%d %d",&x,&y);
  querieDois(catp,c,x,y);
  return 0;
}

int querieTresMenu(Faturacao fatp, Filial f){
  system("clear");
  printf("\n\n     > Escreva o produto e o mes validos que pretende no formato [produto mes] <\n\n");
  printf("\n\nb: voltar ao menu inicial\n\n");
  char produto [10];
  int mes;
  scanf("%s %d",produto,&mes);
  Produto p = criaProduto(produto);
  querieTres(fatp,f,p,mes);
  char c;
  scanf("%s",&c);
  return 0;
}

int querieQuatroMenu(Faturacao fatp,Cat_Prods catp){
  if (getNaoComprados_nr(fatp)==0)
    atualizanc(fatp,catp);
  system("clear");
  printf("\n\n        Escolha o tipo de resultados:\n");
  printf(" 1 -> Filial 1\n 2 -> Filial 2\n 3 -> Filial 3\n 4 -> Global\n");
  int fil;
  scanf("%d",&fil);
  printf("\nEscolhe o numero de linhas e colunas no formato [int int]\n");
  int x,y;
  scanf("%d %d",&x,&y);
  querieQuatro(fatp,catp,x,y,fil);
  char c;
  scanf("%s",&c);
  return 0;
}


int querieCincoMenu(Filial f,Cat_Cli catc){
  system("clear");
  printf("\nEscolhe o numero de linhas e colunas no formato [int int]\n");
  int x,y;
  scanf("%d %d",&x,&y);
  querieCinco(f,catc,x,y);
  char c;
  scanf("%s",&c);
  return 0;
}

int querieSeisMenu(Filial f, Faturacao fatp, Cat_Prods catp, Cat_Cli catc){
  system("clear");
  if (getNaoComprados_nr(fatp)==0)
    atualizanc(fatp,catp);
  querieSeis(f,fatp,catc);
  char c;
  scanf("%s",&c);
  return 0;
}

int querieSeteMenu(Filial f){
  system("clear");
  printf("\n\n         Escreve o cliente que queres resultados:\n");
  char cli [10];
  scanf("%s",cli);
  Cliente c = criaCliente(cli);
  querieSete(f,c);
  char e;
  scanf("%s",&e);
  return 0;
}

int querieNoveMenu(Filial f){
  system("clear");
  printf("\n\n        Escreve o produto que queres resultados:\n");
  char prod [10];
  scanf("%s",prod);
  Produto p = criaProduto(prod);
  system("clear");
  printf("\n      Produto:%s\n",prod);
  printf("\n  1-> Filial 1\n  2-> Filial 2\n  3-> Filial 3\n");
  int filial;
  scanf("%d",&filial);
  printf("\n  1-> Tipo Normal\n  2-> Tipo Promocao\n");
  int type;
  scanf("%d",&type);
  char tipo;
  if (type==1) tipo='N';
  if (type==2) tipo='P';
  printf("\nEscolhe o numero de linhas e colunas no formato [int int]\n");
  int x,y;
  scanf("%d %d",&x,&y);
  querieNove(f,p,filial,tipo,x,y);
  char e;
  scanf("%s",&e);
  return 0;
}

int querieOitoMenu (Faturacao fatp){
  system("clear");
  printf("\n\n        Escreva dois meses no formato 'int int' para calcular o numero de vendas e total faturado entre esses meses \n\n");
  int x,y;
  scanf("%d %d",&x,&y);
  querieOito(fatp,x,y);
  char c;
  scanf("%s",&c);
  return 0;
}

int querieDezMenu(Filial f){
  system("clear");
  printf("\n\n Escreva o codigo de cliente e o mes no formato [cliente mes]:\n\n");
  char cli [10];
  int mes;
  scanf("%s %d",cli,&mes);
  Cliente c = criaCliente(cli);
  querieDez(f,c,mes);
  char e;
  scanf("%s",&e);
  return 0;
}

int querieOnzeMenu (Filial f){
  system("clear");
  printf("\n Escreva o numero de produtos que quer apresentar:\n\n");
  int n;
  scanf("%d",&n);
  printf("\n  1-> Filial 1\n  2-> Filial 2\n  3-> Filial 3\n  0-> Globais\n");
  int filial;
  scanf("%d",&filial);
  querieOnze(f,filial,n);
  char e;
  scanf("%s",&e);
  return 0;
}

int querieDozeMenu (Filial f){
  system("clear");
  printf("\n\n Escreva o codigo de cliente que pretende obter resultados:\n\n");
  char cli [10];
  scanf("%s",cli);
  Cliente c = criaCliente(cli);
  querieDoze(f,c);
  char e;
  scanf("%s",&e);
  return 0;
}

int menuQueries (Cat_Prods catp, Cat_Cli catc, Faturacao fatp, Filial f){
  while(1){
    system("clear");
    printf("\n\n");
    printf("                -------------------------------------- \n");
    printf("                |   Sistema de Faturacao de Vendas   | \n");
    printf("                -------------------------------------- \n");
    printf("\n\n");
    printf("\n               ==>    Escolha uma opcao   <== \n\n");
    printf("     1 -> Ler os ficheiros\n");
    printf("     2 -> Lista  e numero de produtos começados por uma letra\n");
    printf("     3 -> Numero de vendas e faturacao com um produto em determinado mes\n");
    printf("     4 -> Lista e numero de produtos que nunca foram comprados\n");
    printf("     5 -> Clientes que realizaram compras em todas as filiais\n");
    printf("     6 -> Numero de clientes sem compras e produtos nao comprados\n");
    printf("     7 -> Tabela com cliente e total comprado em cada mes por filial\n");
    printf("     8 -> Numero de vendas e total faturado entre dois meses\n");
    printf("     9 -> Clientes que compraram um produto na filial\n");
    printf("     A -> Produtos mais comprados pelo cliente naquele mes\n");
    printf("     B -> Lista com N produtos mais vendidos do ano por filial\n");
    printf("     C -> Os 3 produtos em que o cliente gastou mais dinheiro\n");
    printf("\n\nq: fechar aplicacao\n");
    char esc;
    scanf("%s",&esc);
    if (esc=='1') querieUmMenu(catp,catc,fatp,f);
    if (esc=='2') querieDoisMenu(catp);
    if (esc=='3') querieTresMenu(fatp,f);
    if (esc=='4') querieQuatroMenu(fatp,catp);
    if (esc=='5') querieCincoMenu(f,catc);
    if (esc=='6') querieSeisMenu(f,fatp,catp,catc);
    if (esc=='7') querieSeteMenu(f);
    if (esc=='8') querieOitoMenu(fatp);
    if (esc=='9') querieNoveMenu(f);
    if (esc=='A') querieDezMenu(f);
    if (esc=='B') querieOnzeMenu(f);
    if (esc=='C') querieDozeMenu(f);
    if (esc=='q') return 0;
  }
  return -1;
}

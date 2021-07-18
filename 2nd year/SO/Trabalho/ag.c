#include <unistd.h>
#include <fcntl.h>
#include <stdlib.h>
#include <string.h>
#include <sys/wait.h>
#include <stdio.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <signal.h>
#include "lib.h"


int getAgregadas(){

  int fd = open("vendas.txt",O_RDONLY);

  int vendas_agregadas;
  read(fd,&vendas_agregadas,sizeof(int));

  close(fd);
  return vendas_agregadas;
}




int atualizaAgregadas(int vendas_agregadas){

  int fd=open("vendas.txt",O_WRONLY);
  write(fd,&vendas_agregadas,sizeof(int));

  close(fd);
  return 0;
}



int insereOrd (Venda *v, Venda* arr[]){
  int i,j;

  for(i=0;i<NUMERO_MAIS_VENDIDOS;i++)
    if (arr[i]->quant < v->quant) break;

  for (j=NUMERO_MAIS_VENDIDOS-1;j<i;j++)
    arr[j]=arr[j-1];

  Venda *a = malloc(sizeof(struct venda));
  a->cod=v->cod;
  a->fat=v->fat;
  a->quant=v->quant;
  arr[i]=a;

  return 0;
}


int inicializaVendidos(Venda* arr []){
  int i;
  for (i=0;i<NUMERO_MAIS_VENDIDOS;i++){
    Venda *v = malloc(sizeof(struct venda));
    v->cod=0;
    v->quant=0;
    v->fat=0;
    arr[i]=v;
  }
  return 0;
}



int escreveMaisVendidos(Venda* arr []){
  int i;
  int fd = open("./help/maisvendidos.txt",O_CREAT | O_WRONLY,0666);
  for (i=0;i<NUMERO_MAIS_VENDIDOS;i++){
    if(arr[i]->cod!=0){
      write(fd,&arr[i]->cod,sizeof(int));
    }
  }
  return 0;
}



int sendSignalSV (){
  int fda = open("artigos.txt",O_RDONLY);
  int pid_sv;
  read(fda,&pid_sv,sizeof(int));
  if (pid_sv!=0) kill(pid_sv,SIGUSR1);
  close(fda);
  return 0;
}


int nr_artigos (){
  int fda = open("artigos.txt",O_RDONLY);
  lseek(fda,sizeof(int),SEEK_SET);

  int artigos = lseek(fda,0,SEEK_END)/sizeof(struct artigo);

  close(fda);
  return artigos;
}






int numero_forks(int nr_vendas){
  int i=nr_vendas;
  int artigos=nr_artigos();
  while(i>1 && (nr_vendas%i!=0 || nr_vendas/i<artigos+1))
    i--;
  return i;
}





Venda* string2Vend (char* linha_venda){
  strtok(linha_venda,"\n");

  char* campos [ARGS_VENDA];
  char*token;
  int i=0;

  token=strtok(linha_venda," ");
  while(token!=NULL){
    campos[i++]=token;
    token=strtok(NULL," ");
  }

  if (i<3) return NULL;

  Venda *v = malloc(sizeof(struct venda));
  v->cod=atoi(campos[0]);
  v->quant=atoi(campos[1]);
  v->fat=atof(campos[2]);

  return v;
}







int convertStdIn2File (int argc){

  char buffer [MAX_BUFFER];
  int vendas_agregadas=getAgregadas();

  if (argc>1){
    lseek(0,sizeof(int),SEEK_SET);
    for(int c=0;c<vendas_agregadas;c++)
      readln(0,buffer,MAX_BUFFER);
  }

  int nr_vendas=0;

  int fd=open("ag_venda.txt",O_CREAT | O_WRONLY | O_APPEND, 0666);

  while(readln(0,buffer,MAX_BUFFER)>0){
    Venda *v = malloc(sizeof(struct venda));
    v=string2Vend(buffer);

    write(fd,v,sizeof(struct venda));
    nr_vendas++;
    vendas_agregadas++;
  }

  if (argc>1) atualizaAgregadas(vendas_agregadas);

  close(fd);
  return nr_vendas;
}






int agregaVendas(int f, int nr_vendas_fork){
  int fdv = open("ag_venda.txt",O_RDONLY);
  char name [MAX_BUFFER];
  sprintf(name,"ag_aux%d.txt",f);

  int fda = open(name,O_CREAT | O_RDWR,0666);

  /*
   Inicializa ficheiro auxiliar do respetivo filho, com todos os artigos iniciados a 0, para que o acesso, mais tarde, seja direto
  */

  int artigos=nr_artigos();
  for(int a=0;a<artigos;a++){
    Venda *v = malloc(sizeof(struct venda));
    v->cod=a+1;
    v->quant=0;
    v->fat=0;

    write(fda,v,sizeof(struct venda));
  }

  int offset = f * nr_vendas_fork * sizeof(struct venda);
  lseek(fdv,offset,SEEK_SET);
  int vendas_lidas=0;

  Venda *v = malloc(sizeof(struct venda));
  while(vendas_lidas<nr_vendas_fork && read(fdv,v,sizeof(struct venda))>0){

    int cod=v->cod;
    lseek(fda,(cod-1)*sizeof(struct venda),SEEK_SET);
    Venda *aux = malloc(sizeof(struct venda));
    read(fda,aux,sizeof(struct venda));
    aux->quant+=v->quant;
    aux->fat+=v->fat;
    lseek(fda,-sizeof(struct venda),SEEK_CUR);
    write(fda,aux,sizeof(struct venda));
    vendas_lidas++;
  }

  close(fdv);
  close(fda);
  return 0;
}




int juntaAg(int nr_forks){
  int fd = open("ag.txt",O_WRONLY | O_CREAT | O_APPEND,0666);
  int i,r=0;
  char name [MAX_BUFFER];

  for(i=0;i<nr_forks;i++){
    sprintf(name,"ag_aux%d.txt",i);
    int fdaux=open(name,O_RDONLY);

    Venda *v = malloc(sizeof(struct venda));
    while(read(fdaux,v,sizeof(struct venda))>0){
      if (v->quant!=0){
        write(fd,v,sizeof(struct venda));
        r++;
      }
    }

    close(fdaux);
    unlink(name);
  }

  unlink("ag_venda.txt");
  rename("ag.txt","ag_venda.txt");
  close(fd);
  return r;
}




int printAgregadas(){

  Venda* maisVendidos [NUMERO_MAIS_VENDIDOS];
  inicializaVendidos(maisVendidos);

  int fd = open("ag_venda.txt",O_RDONLY);
  char linha_agregada [MAX_BUFFER];

  Venda *v = malloc(sizeof(struct venda));

  while(read(fd,v,sizeof(struct venda))>0){

    if (v->quant!=0){

      insereOrd(v,maisVendidos);

      sprintf(linha_agregada,"%d %d %f\n",v->cod,v->quant,v->fat);
      write(1,linha_agregada,strlen(linha_agregada));

    }
  }

  escreveMaisVendidos(maisVendidos);

  close(fd);
  unlink("ag_venda.txt");
  return 0;
}





int ag (int nr_vendas){
  int nr_forks = numero_forks(nr_vendas);

  int nr_vendas_fork = nr_vendas / nr_forks;

  int i=0,status;

  while (i<nr_forks){

    if (fork()==0){
      agregaVendas(i,nr_vendas_fork);
      exit(0);
    }

    else
      i++;

  }
  while(wait(&status)>0);

  return (nr_vendas-juntaAg(nr_forks));
}




int main(int argc, char** argv){

  int nr_vendas = convertStdIn2File(argc);
  int mud=ag(nr_vendas);

  while(mud>0){
    nr_vendas=nr_vendas-mud;
    mud=ag(nr_vendas);
  }

  printAgregadas();

  sendSignalSV();

  return 0;
}

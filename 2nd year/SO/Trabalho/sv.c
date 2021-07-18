#include <unistd.h>
#include <fcntl.h>
#include <stdlib.h>
#include <string.h>
#include <sys/wait.h>
#include <stdio.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <signal.h>
#include <time.h>
#include "lib.h"


Venda* maisVendidos [NUMERO_MAIS_VENDIDOS];


int nr_artigos (){
  int fda = open("artigos.txt",O_RDONLY);
  lseek(fda,sizeof(int),SEEK_SET);

  int artigos = lseek(fda,0,SEEK_END)/sizeof(struct artigo);

  close(fda);
  return artigos;
}




int carregaArtigos (){
  int pid=getpid();
  int cod;

  int fda = open("artigos.txt",O_RDWR | O_CREAT, 0666);
  lseek(fda,0,SEEK_SET);
  write(fda,&pid,sizeof(int));

  int fdaux = open("./help/maisvendidos.txt",O_RDONLY);

  int i=0,help;

  if (fdaux>0){
    Art *art = malloc(sizeof (struct artigo));
    while(read(fdaux,&help,sizeof(int))){

      if (i<NUMERO_MAIS_VENDIDOS){
        lseek(fda,sizeof(int)+(help-1)*sizeof(struct artigo),SEEK_SET);
        read(fda,art,sizeof(struct artigo));
        Venda *v = malloc(sizeof(struct venda));
        v->cod=help;
        v->fat=art->preco;
        maisVendidos[i]=v;
      }

      i++;
    }
    free(art);
  }

  int artigos = nr_artigos();

  int fds=open("stock.txt",O_CREAT | O_RDWR,0666);

  for (cod=1;cod<=artigos;cod++){
    int offset = (cod-1)*(sizeof(int));

    if (offset>=lseek(fds,0,SEEK_END)){
      int new_stock;
      new_stock=0;
      write(fds,&new_stock,sizeof(int));
    }
  }

  close(fda);
  return 0;
}





void fst_sig_rec (int signum){
  carregaArtigos();
}





void sigusr_handler (int signum){
  if (fork()==0){

    time_t now;
    time(&now);
    struct tm *tnow = localtime(&now);
    int hours=tnow->tm_hour;
    int minutes=tnow->tm_min;
    int seconds=tnow->tm_sec;
    int day=tnow->tm_mday;
    int month=tnow->tm_mon+1;
    int year=tnow->tm_year+1900;

    char fich_name [MAX_BUFFER];
    sprintf(fich_name,"./agregacoes/%d-%02d-%02dT%02d:%02d:%02d.txt",year,month,day,hours,minutes,seconds);

    int fdin=open("vendas.txt",O_RDONLY);
    int fdout=open(fich_name,O_CREAT | O_WRONLY,0666);

    dup2(fdin,0);
    dup2(fdout,1);

    execl("ag","ag","1",NULL);
  }
}




double getPreco (int cod){
  double preco;
  int i;

  for(i=0;i<NUMERO_MAIS_VENDIDOS && maisVendidos[i]!=NULL;i++){
    if (maisVendidos[i]->cod==cod){
      preco=maisVendidos[i]->fat;
      break;
    }
  }

  if (maisVendidos[i]==NULL || i==NUMERO_MAIS_VENDIDOS){

    int fdp = open("artigos.txt",O_RDONLY);

    int offset = sizeof(int)+sizeof(struct artigo)*(cod-1);
    lseek(fdp,offset,SEEK_SET);

    Art *art = malloc(sizeof(struct artigo));
    read(fdp,art,sizeof(struct artigo));

    preco=art->preco;
    free(art);
    close(fdp);
  }

  return preco;
}




int registaVenda (int cod, int quant){

  int fdv=open("vendas.txt",O_CREAT | O_WRONLY,0666);
  int vendas_agregadas=0;
  if(lseek(fdv,0,SEEK_END)==0)
    write(fdv,&vendas_agregadas,sizeof(int));


  char venda [MAX_BUFFER];

  double preco=getPreco(cod);

  sprintf(venda,"%d %d %f\n",cod,quant,preco*quant);

  /*
  Vend *v = malloc(sizeof(struct venda));
  v->cod=cod;
  v->quant=quant;
  v->fat=preco * quant;
  */
  write(fdv,venda,strlen(venda));

  close(fdv);

  return 0;
}




int getStock(char** argv){
  int pid =atoi(argv[1]);
  int cod =atoi(argv[0]);

  char response_fifo [MAX_BUFFER];
  sprintf(response_fifo,"/tmp/response_%d",pid);

  int fdr=open(response_fifo,O_WRONLY);

  int fds=open("stock.txt",O_RDONLY);

  if (cod>nr_artigos()){
    char *err;
    err=strdup("Artigo nao existe\n");

    write(fdr,err,strlen(err));

    close(fdr);
    close(fds);
    return 0;
  }

  int offset = (atoi(argv[0])-1)*(sizeof(int));
  if (offset>=lseek(fds,0,SEEK_END)) return 0;

  lseek(fds,offset,SEEK_SET);

  int stock;
  read(fds,&stock,sizeof(int));

  char buffer [MAX_BUFFER];
  sprintf(buffer,"%d %f\n",stock,getPreco(cod));
  write(fdr,buffer,strlen(buffer));

  close(fds);
  close(fdr);
  return 0;
}





int atualizaStock(char** argv){
  int cod = atoi(argv[0]);
  int quant = atoi(argv[1]);
  int pid = atoi(argv[2]);

  char response_fifo [MAX_BUFFER];
  sprintf(response_fifo,"/tmp/response_%d",pid);

  int fdr=open(response_fifo,O_WRONLY);
  int fds=open("stock.txt",O_CREAT | O_RDWR,0666);

  if (cod>nr_artigos()){
    char *err;
    err=strdup("Artigo nao existe\n");

    write(fdr,err,strlen(err));

    close(fdr);
    close(fds);
    return 0;
  }

  int offset = (cod-1)*(sizeof(int));

  lseek(fds,offset,SEEK_SET);

  int updt_stock;
  read(fds,&updt_stock,sizeof(int));

  updt_stock+=quant;

  lseek(fds,-sizeof(int),SEEK_CUR);
  write(fds,&updt_stock,sizeof(int));

  char buffer [MAX_BUFFER];
  sprintf(buffer,"%d\n",updt_stock);
  write(fdr,buffer,strlen(buffer));

  if (quant<0) registaVenda(cod,abs(quant));

  close(fds);
  close(fdr);
  return 0;
}






int main (int argc, char** argv){
  char buffer [MAX_BUFFER];
  char* err;
  carregaArtigos();

  signal(SIGUSR1,fst_sig_rec);
  signal(SIGUSR2,sigusr_handler);

  mkfifo("/tmp/instructions_cv",0666);

  err=strdup("invalid request\n");

  while (1){

    int fd=open("/tmp/instructions_cv",O_RDONLY);

    while(readln(fd,buffer,MAX_BUFFER)>0){

      int i=0;
      char* args [ARGS_MAX_NUM];
      char *token = strtok(buffer,"\n");
      token = strtok(buffer," ");

      while(token!=NULL && i<ARGS_MAX_NUM){
        args[i]=token;
        token=strtok(NULL," ");
        i++;
      }

      switch(i){

        case 2:
          getStock(args);
          break;

        case 3:
          atualizaStock(args);
          break;

        default:
          write(2,err,strlen(err));
      }

    }

    close(fd);

  }

  free(err);
  return 0;
}

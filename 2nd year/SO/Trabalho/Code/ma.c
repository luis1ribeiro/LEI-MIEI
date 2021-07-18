#include <unistd.h>
#include <fcntl.h>
#include <stdlib.h>
#include <string.h>
#include <sys/wait.h>
#include <stdio.h>
#include <signal.h>
#include "lib.h"

#define MAX_BUFFER 1024
#define ARGS_NUM 2



int need_clean (){

  int tam_strings=0;
  int tam_total;

  int fda=open("artigos.txt",O_RDONLY);
  int fds=open("strings.txt",O_RDONLY);

  lseek(fda,sizeof(int),SEEK_SET);

  tam_total=lseek(fds,0,SEEK_END);

  Art *a = malloc(sizeof(struct artigo));

  while(read(fda,a,sizeof(struct artigo))){
    tam_strings+=a->tam_str;
  }

  return tam_strings/tam_total<=0.8;
}



int clean_strings (){

  int fda = open ("artigos.txt",O_RDWR);
  int fds = open ("strings.txt",O_RDONLY);
  int fdns = open ("new_strings.txt",O_CREAT | O_WRONLY,0666);

  Art *a = malloc(sizeof(struct artigo));

  lseek(fda,sizeof(int),SEEK_SET);

  while(read(fda,a,sizeof(struct artigo))){

    lseek(fds,a->string_nr,SEEK_SET);

    char* buffer [a->tam_str];
    if (read(fds,buffer,a->tam_str)<0) perror("reading string");

    int pos=lseek(fdns,0,SEEK_END);
    write(fdns,buffer,a->tam_str);

    a->string_nr=pos;
    lseek(fda,-sizeof(struct artigo),SEEK_CUR);
    write(fda,a,sizeof(struct artigo));

  }

  remove("strings.txt");
  rename("new_strings.txt","strings.txt");

  close(fda);
  close(fds);
  close(fdns);
  return 0;
}



int insere(char** argv){

  int fda = open ("artigos.txt",O_CREAT | O_RDWR,0666);
  int fds = open ("strings.txt",O_CREAT | O_WRONLY | O_APPEND,0666);
  int pid_sv=0;

  int pos=lseek(fda,0,SEEK_END);
  int codigo = lseek(fds,0,SEEK_END);

  if (pos==0){
    pid_sv=0;
    write(fda,&pid_sv,sizeof(int));
  }

  lseek(fda,0,SEEK_SET);
  if(read(fda,&pid_sv,sizeof(int))<=0) perror("reading");
  lseek(fda,0,SEEK_END);

  Art *new = malloc(sizeof(struct artigo));
  new->string_nr=codigo;
  new->preco=atof(argv[1]);
  new->tam_str=strlen(argv[0]);

  write(fda,new,sizeof(struct artigo));
  write(fds,argv[0],strlen(argv[0]));

  int id = pos/sizeof(struct artigo);
  char show_cod [MAX_BUFFER];
  sprintf(show_cod,"Codigo: %d\n",id+1);
  write(1,show_cod,strlen(show_cod));

  if (pid_sv!=0) kill(pid_sv,SIGUSR1);

  free(new);
  close(fda);
  close(fds);

  return 0;
}



int altera_nome (char** argv){

  int fda = open ("artigos.txt",O_RDWR);
  int fds = open ("strings.txt",O_WRONLY | O_APPEND);


  int offset = (atoi(argv[0])-1)*(sizeof(struct artigo));
  if (offset>=lseek(fda,0,SEEK_END)) return 0;

  lseek(fda,sizeof(int),SEEK_SET);
  lseek(fda,offset,SEEK_CUR);
  int codigo=lseek(fds,0,SEEK_END);

  Art *update = malloc(sizeof(struct artigo));
  read(fda,update,sizeof(struct artigo));

  update->string_nr=codigo;
  update->tam_str=strlen(argv[1]);

  lseek(fda,-sizeof(struct artigo),SEEK_CUR);
  write(fda,update,sizeof(struct artigo));
  write(fds,argv[1],strlen(argv[1]));

  if (need_clean()) clean_strings();


  free(update);
  close(fda);
  close(fds);

  return 0;
}





int get_preco(char** argv){

  int fda=open("artigos.txt",O_RDONLY);

  int offset = (atoi(argv[0])-1)*(sizeof(struct artigo));
  if (offset>=lseek(fda,1,SEEK_END)) return 0;

  lseek(fda,sizeof(int),SEEK_SET);
  lseek(fda,offset,SEEK_CUR);

  Art *update = malloc(sizeof(struct artigo));
  read(fda,update,sizeof(struct artigo));

  char buffer [MAX_BUFFER];
  sprintf(buffer,"%f\n",update->preco);
  write(1,buffer,strlen(buffer));

  free(update);
  close(fda);
  return 0;
}






int altera_preco (char** argv){
  int pid_sv;

  int fda = open ("artigos.txt",O_RDWR);

  int offset = (atoi(argv[0])-1)*(sizeof(struct artigo));
  if (offset>=lseek(fda,1,SEEK_END)) return 0;

  lseek(fda,0,SEEK_SET);
  read(fda,&pid_sv,sizeof(int));
  lseek(fda,offset,SEEK_CUR);


  Art *update = malloc(sizeof(struct artigo));
  read(fda,update,sizeof(struct artigo));

  update->preco=atof(argv[1]);

  lseek(fda,-sizeof(struct artigo),SEEK_CUR);
  write(fda,update,sizeof(struct artigo));

  if (pid_sv!=0) kill(pid_sv,SIGUSR1);

  free (update);
  close(fda);

  return 0;
}


int getpidsv (){
  int pid_sv;
  int fda=open("artigos.txt",O_RDONLY);
  read(fda,&pid_sv,sizeof(int));
  close(fda);
  return pid_sv;
}





int main(int argc,char**argv){
  char buffer [MAX_BUFFER];
  int pid_sv=getpidsv();

  while(readln(0,buffer,MAX_BUFFER)>0){

    strtok(buffer,"\n");

    int i=0;
    char* args [ARGS_NUM];
    char *token = strtok(buffer," ");
    token=strtok(NULL," ");

    while(token!=NULL && i<ARGS_NUM){
      args[i]=token;
      token=strtok(NULL," ");
      i++;
    }

    switch(buffer[0]){

      case 'i':
        insere(args);
        break;

      case 'n':
        altera_nome(args);
        break;

      case 'p':
        altera_preco(args);
        break;

      case 'g':
        get_preco(args);
        break;

      case 'a':
        kill(pid_sv,SIGUSR2);
        break;

      default:
        perror("invalid_request");
    }

  }
  return 0;
}

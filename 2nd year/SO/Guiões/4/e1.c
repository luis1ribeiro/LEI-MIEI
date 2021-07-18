#include <unistd.h>
#include <fcntl.h>
#include <stdio.h>

int main (){
  int stdi;
  int stdo;
  int err;


  stdi=open("/etc/passwd",O_RDONLY);
  stdo=open("saida.txt",O_CREAT | O_TRUNC | O_WRONLY,0666);
  err=open("erros.txt",O_CREAT | O_TRUNC | O_WRONLY,0666);

  dup2(stdi,0);
  dup2(stdo,1);
  dup2(err,2);

  close(stdi);
  close(stdo);
  close(err);

  char buffer [1024];
  ssize_t res=0;

  while((res=read(0,buffer,1024))!=0){
    write(1,buffer,res);
    write(2,buffer,res);

    printf("Sou irmao\n");
    fflush(stdout);
    printf("Tou\n");
    /*
       printfs em memoria(buffers) -> posso usar o fflush(stdout) -> escreve logo no ficheiro e não é copiado para a memoria
       posso usar o setbuf(stdout,NULL);
    */
  }

  return 0;
}


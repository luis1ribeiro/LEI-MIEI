#include <unistd.h>
#include <fcntl.h>
#include <stdio.h>
#include <sys/wait.h>

int main (){
  int stdi;
  int stdo;
  int err;
  int status;

  stdi=open("/etc/passwd",O_RDONLY);
  stdo=open("saida.txt",O_CREAT | O_TRUNC | O_WRONLY,0666);
  err=open("erros.txt",O_CREAT | O_TRUNC | O_WRONLY,0666);

  //Guardar os descritores de ficheiros para mais tarde
  int fin = dup(0);
  int fout = dup(1);
  int ferr = dup(2);

  dup2(stdi,0);
  dup2(stdo,1);
  dup2(err,2);

  close(stdi);
  close(stdo);
  close(err);

  char buffer [1024];
  ssize_t res=0;

  if(!fork()){
    while((res=read(0,buffer,1024))!=0){

      write(1,buffer,res);
      write(2,buffer,res);

      _exit(0);
      //printfs em memoria(buffers) -> posso usar o fflush(stdout) -> escreve logo no ficheiro e não é copiado para a memoria
    }
  }
  else {

    wait(&status);
    dup2(fin,0);
    dup2(fout,1);
    dup2(ferr,2);
    close(fin);
    close(fout);
    close(ferr);

    printf("Tou de volta irmaos\n");

  }

  return 0;
}



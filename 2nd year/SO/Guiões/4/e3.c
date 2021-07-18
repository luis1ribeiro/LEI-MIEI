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

  execlp("wc","wc",NULL);

  return 0;
}


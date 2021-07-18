/* Implemente, utilizando a função readln, um programa com funcionalidade similar ao comando nl.
* Este comando repete para o standard output as linhas recebidas do standard input ou de um (só) ficheiro especificado na sua linha de comando.
* Cada linha repetida é numerada sequencialmente.
*/

#include <fcntl.h>
#include <stdio.h>
#include <string.h>
#include <unistd.h>

#include "readln.c"


#define MAX_BUFFER 1024

int main (int argc, char *argv[]){
  int fd;
  int size = MAX_BUFFER;
  char buf [size];
  size_t res;
  int line=1;
  char lineN [12];

  if (argc>1) // dar a seguir ao ./e6
    fd=open(argv[1],O_RDONLY);
  else
    fd=0;

  while ((res=readln(fd,buf,size))>0){
    sprintf(lineN,"%d ",line++);
    write(1,lineN,strlen(lineN));
    write(1,buf,res);
  }

  return 0;
}

/* sprintf stands for “String print”. Instead of printing on console, it store output on char buffer which are specified in sprintf */


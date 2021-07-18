#include <unistd.h>
#include <fcntl.h>
#include <stdlib.h>
#include <string.h>
#include <sys/wait.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <stdio.h>
#include <signal.h>
#include <time.h>
#include "lib.h"


int main (int argc, char**argv){
  int pid=getpid();
  char fifo_name [MAX_BUFFER];
  int fdout=-1;

  sprintf(fifo_name,"/tmp/response_%d",pid);
  mkfifo(fifo_name,0666);

  char buffer [MAX_BUFFER];
  int rd;

  int fd=open("/tmp/instructions_cv",O_WRONLY);

  while (readln(0,buffer,MAX_BUFFER)>0){

    char *instruction = strtok(buffer,"\n");
    sprintf(instruction,"%s %d\n",buffer,pid);

    if (write(fd,instruction,strlen(instruction))<0) perror("writing");

    fdout=open(fifo_name,O_RDONLY);

    while ((rd=readln(fdout,buffer,MAX_BUFFER))>0){
      write(1,buffer,rd);
    }

    close(fdout);

  }

  close(fd);
  return 0;
}

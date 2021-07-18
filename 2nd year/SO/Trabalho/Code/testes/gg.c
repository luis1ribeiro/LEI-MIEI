#include <unistd.h>
#include <fcntl.h>
#include <stdlib.h>
#include <string.h>
#include <sys/wait.h>
#include <stdio.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <signal.h>


int main (){
  int fd = open("input_cv.txt",O_CREAT | O_WRONLY,0666);
  int i,t;
  for (i=1;i<=100000;i++){
    char buffer [256];
    sprintf(buffer,"%d 10\n",i);
    for (t=0;t<4;t++){
      write(fd,buffer,strlen(buffer));
    }
    sprintf(buffer,"%d 20\n",i);
    for (t=0;t<4;t++){
      write(fd,buffer,strlen(buffer));
    }
    sprintf(buffer,"%d 30\n",i);
    for (t=0;t<4;t++){
      write(fd,buffer,strlen(buffer));
    }
    sprintf(buffer,"%d 40\n",i);
    for (t=0;t<4;t++){
      write(fd,buffer,strlen(buffer));
    }
    sprintf(buffer,"%d 50\n",i);
    for (t=0;t<4;t++){
      write(fd,buffer,strlen(buffer));
    }
    sprintf(buffer,"%d -10\n",i);
    for (t=0;t<4;t++){
      write(fd,buffer,strlen(buffer));
    }
    sprintf(buffer,"%d -20\n",i);
    for (t=0;t<4;t++){
      write(fd,buffer,strlen(buffer));
    }
    sprintf(buffer,"%d -30\n",i);
    for (t=0;t<4;t++){
      write(fd,buffer,strlen(buffer));
    }
    sprintf(buffer,"%d -40\n",i);
    for (t=0;t<4;t++){
      write(fd,buffer,strlen(buffer));
    }
    sprintf(buffer,"%d -50\n",i);
    for (t=0;t<4;t++){
      write(fd,buffer,strlen(buffer));
    }
  }
  close(fd);
  return 0;
}

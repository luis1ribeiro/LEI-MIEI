#include <fcntl.h>
#include <unistd.h>
#include <stdio.h>
#include <string.h>
#include <stdlib.h>


#include "person.c"

int main (int argc, char *argv[]){

  if (argc<4){
    printf("Invalid operation");
    return 0;
  }

  //int fd=open("pessoas.txt", O_CREAT | O_APPEND | O_WRONLY); //nao preciso do lseek SEEK_END por causa do append

  if (strcmp(argv[1],"-i")==0){
    int age = atoi(argv[3]);
    char* name = argv[2];
    new_person (name,age);
  }

  if (strcmp(argv[1],"-u")==0){
    int age = atoi (argv[3]);
    char* nome = argv[2];
    //lseek(fd,sizeof(struct Person),SEEK_END);
    //read(fd,&p,sizeof(struct Person));
    person_change_age(nome,age);
  }
  return 0;
}


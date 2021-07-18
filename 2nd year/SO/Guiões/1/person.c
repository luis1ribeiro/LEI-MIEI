include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "person.h"

void new_person (char* name, int age){
  int fd=open("pessoas.txt", O_CREAT | O_APPEND | O_WRONLY); //nao preciso do lseek SEEK_END por causa do append
  Person p;
 // *p = malloc(sizeof(struct Person));
  p.idade = age;
  strcpy(p.nome, name);
  //printf ("%s\n",p.nome);
  write(fd,&p,sizeof(struct Person));
  close(fd);
}

void person_change_age (char* name, int age){
  Person p; int r;
  int fd = open("pessoas.txt",O_RDWR, 0600);
  while((r = read(fd,&p,sizeof(struct Person)))>0){

      printf("Read Person name %s age- %d\n",p.nome, p.idade);

      if (strcmp(name, p.nome)==0){
        p.idade = age;
        lseek(fd,-sizeof(struct Person),SEEK_CUR);//a função lseek devolve para onde se moveu (no ficheiro)
        write(fd,&p,sizeof(struct Person));
        close(fd);
      }
      //não preciso de um lseek porque o read faz offset
  }
}

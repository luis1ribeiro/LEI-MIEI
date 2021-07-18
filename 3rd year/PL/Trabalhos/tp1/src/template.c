#include <stdio.h>
#include <string.h>
#include <glib.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h>

char* name;
int varslex();
int tmpllex();

int changeVariables (char* temp){
  int in = open(temp,O_RDONLY);
  int out = open("pipe",O_CREAT | O_RDWR,0666);
  printf("Changing Variables...................\n");
  int fdout = dup(1);
  dup2(out,1);
  close(out);
  int fdin = dup(0);
  dup2(in,0);
  close(in);
  varslex();
  dup2(fdout,1);
  close(fdout);
  dup2(fdin,0);
  close(fdin);
  printf("Variables changed\n");
}

int makeTemplate (){
  printf("Reading from pipe....................\n");
  int in = open("pipe",O_RDONLY);
  int fdin=dup(0);
  dup2(in,0);
  close(in);
  tmpllex();
  dup2(fdin,0);
  close(fdin);
  printf("Template made\n");
}

int main (int argc, char* argv[]){
  char* temp = strdup(argv[2]);
  name = strdup(argv[1]);
  changeVariables(temp);
  makeTemplate();
  unlink("pipe");
  return 0;
}

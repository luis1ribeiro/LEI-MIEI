#include <unistd.h>
#include <sys/wait.h>
#include <stdio.h>
#include <string.h>


int main (int argc, char* argv[]){
  strcpy(argv[0],"./e4");
  execv("./e3",argv); // execv (char* path, char* comand)
}

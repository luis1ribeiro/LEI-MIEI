#include <unistd.h>
#include <fcntl.h>
#include <stdio.h>

/*
   Fork + Exec : interpretador de comandos
   - O processo pai cria um filho e o filho executa um novo programa via exec().

*/

int main(int argc, char** argv) {
  int ret = execl("/bin/ls","ola","-l",NULL); // "ola" é o argv[0]
  perror("reached return"); //só executa se der erro em cima, porque substitui este perror pelo exec
}


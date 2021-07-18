#include <unistd.h>
#include <sys/wait.h>
#include <stdio.h>
#include <string.h>


int main(int argc, char** argv){

  int exec_ret,i;

  for(i=1; i<argc; i++) {

    if(!fork()) {

      printf("Sou o filho %d e vou executar o comando %s\n",getpid(),argv[i]);

      exec_ret = execlp(argv[i],argv[i],NULL);
      perror("reached return");
      _exit(exec_ret);
    }
  }

  for (i=1; i<argc; i++) {
    printf("Souo pai e estou à espera dos filhos\n");
    wait(NULL);
  }

  printf("todos os filhos executaram\n");

  return 0;

}

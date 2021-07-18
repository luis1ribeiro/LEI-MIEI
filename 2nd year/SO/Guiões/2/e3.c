#include <sys/wait.h>
#include <unistd.h>
#include <stdio.h>


int main() {
    int i;
    for( i = 0; i < 10; i++){
        if(!fork()){
            printf("Sou filho, pid: %d, ppid: %d\n",getpid(),getppid());
            _exit(i+1); // para cada 1 dos filhos.
        }
        int status;
        wait(&status);
        if(WIFEXITED(status)){ // retorna um valor != 0 caso o filho terminou com exit
            printf("O código filho fez exit: %d\n",WEXITSTATUS(status)); // imprime qual filho foi. retorna o valor de argumento do exit.
        }
    }
  return 0;
}

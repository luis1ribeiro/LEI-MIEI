#include <sys/wait.h>
#include <unistd.h>
#include <stdio.h>

int main (){

      // neste primeiro ciclo cria se todos os filhos e faz se exit.
    for(int i = 0; i < 10; i++){
        if(!fork()){
            printf("Sou filho, pid: %d, ppid: %d\n",getpid(),getppid());
            _exit(i + 1); // termina o processo e passa como argumento qual.
        }
    }
    // crio dois ciclos for.
    for(int i = 0; i < 10; i++){
        int status;
        wait(&status);
        if(WIFEXITED(status)){
            printf("Filho fez exit: %d\n",WEXITSTATUS(status));
        }
    }
  return 0;
}


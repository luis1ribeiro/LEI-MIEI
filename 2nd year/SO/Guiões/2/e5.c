#include <unistd.h>
#include <sys/wait.h>
#include <stdio.h>
#include <stdlib.h>


int counter = 0;

int main(){
  int status; int cnt=0;
  for(int i=0; i<10; i++){
    if(!fork()){
        printf("Meu pid: %d, sou filho de %d, filho: %d\n", getpid(), getppid(), counter++);
        // nao posso dar _exit(0) para que este crie outro filho
    }
    else {
    wait(&status);
    _exit(0);
    }
    //printf("Meu pid: %d, sou filho de %d, filho: %d\n", getpid(), getppid(), counter++);
  }
  printf("saí do ciclo [pid]: %d\n",getpid());
  _exit(0);

  return 0;
}






#include <stdio.h>
#include <unistd.h>
#include <sys/wait.h>

int main(int argc, char** argv) {
  pid_t pid;
  int i = 0; // o endereço de memoria da variavel i nao vai ser partilhado (cópia de memória)

  if ((pid = fork())==0) {
      i++;
      printf("Sou o filho e tenho o pid: %d\nO meu pai tem pid: %d\n", getpid(),getppid());
      printf("%d\n\n",i);
      _exit(0);
  }
  else {
      i--;
      printf("Sou o pai e tenho o pid: %d\nO meu filho tem pid: %d\nO meu pai tem pid: %d\n", getpid(),pid,getppid());
      printf("%d\n\n",i);
  }
  return 0;

}

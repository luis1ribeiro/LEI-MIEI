#include <stdio.h>
#include <stdlib.h>
#include <signal.h>
#include <unistd.h>
#include <sys/types.h>

/*
   * 2 SIGUSR1 seguidos, o segundo espera 5 segundos até a execução do 1º
   * N SIGUSR1 seguidos, só um pode estar a fila de cada vez portanto vai ter o mesmo comportamento do de cima
   * SIGUSR1 + SIGUSR2 provocam 2 níveis de interrupção
*/

void signal_handler (int signum){
  printf("received %d\n", signum);
  sleep(5);
  printf("handled %d\n", signum);
}

int main (){
  pritnf("pid: %d\n", getpid());
  signal(SIGUSR1, signal_handler);
  signal(SIGUSR2, signal_handler);

  pause();

  printf("Continuing execution...\n");

  return 0;
}

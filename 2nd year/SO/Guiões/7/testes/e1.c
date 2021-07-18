#include <stdio.h>
#include <stdlib.h>
#include <signal.h>
#include <unistd.h>
#include <sys/types.h>

int cntrC_counter = 0;

void sigUhandler (int signum) {
    printf("Control-C\n");
    cntrC_counter++;
}


int main (int argc, char** argv) {

  if(signal(SIGINT, sigUhandler) == SIG_ERR) {
    perror ("SIGNINT failed");
  }

  while (cntrC_counter < 3) {
    printf("working...\n");
    sleep(1); // a chamada sleep é interrompida quando um sinal é recebido.
  }
  /* Por processo, apenas existe um sinal de cada tipo na fila de sinais, por processo */
  return 0;

}

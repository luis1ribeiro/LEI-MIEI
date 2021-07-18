#include <stdio.h>
#include <stdlib.h>
#include <signal.h>
#include <unistd.h>
#include <sys/types.h>

/* Para matar este processo tenho que fazer manualmente -> ps -> kill SIGINT pid */
void sig_handler (int signum) {
  printf("received %d signal\n", signum);
}

int main (int argc, char* argv[]) {
  /*
     * Registar handlers de sinais SIGINT, SIGQUIT.
     * O programa deixa de terminal com CTRL-C e CTRL-\
     * Para terminar o programa: kill -SIGKILL <pid>
  */
  signal(SIGQUIT, sig_handler);
  signal(SIGINT, sig_handler);

  //SIGKILL e SIGSTOP nunca são tratados pelo handler do user
  signal(SIGKILL,sig_handler);
  perror("SIGKILL");

  while(1) {
    pause();
    printf("pause interropted\n");
  }
  return 0;
}

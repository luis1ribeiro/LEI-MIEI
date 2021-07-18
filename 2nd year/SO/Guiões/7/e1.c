/*
 * Using SIGINT, SIGQUIT, and SIGALRM, write a program that counts the time
 * in seconds since it started and prints the time elapsed whenever the user
 * presses Ctrl-C. If the user presses Ctrl- your program
 * should indicate how many times the user has pressed Ctr-C and exit.
 */

#include <signal.h>
#include <stdio.h>
#include <sys/types.h>
#include <unistd.h>

typedef void (*sighandler_t)(int);

int time;
int qntsvezes;

void sigint_handler()
{
    printf("\n");
    printf("[Tempo decorrido até Control+C]: %d\n", time);
    qntsvezes++;
}

void sigquit_handler()
{
    printf("[nmr de Control+C]: %d\n", qntsvezes);
    _exit(0);
}

void sigalarm_handler()
{
    alarm(1); //os restantes alarmes são definidos aqui
    time++;
}

int main(int argc, char** argv)
{

    signal(SIGINT, sigint_handler);
    signal(SIGQUIT, sigquit_handler);
    signal(SIGALRM, sigalarm_handler);
    /*
       * O alarm manda um alarm de 1 em 1 seg
       * mal é registado o código fica em pause()
       * o cntr + C quebra o pause e volta ao alarm(1)
       * Se o antigo ainda não acabou então o novo substitiu-o e o alarm volta a 1 seg
    */
    alarm(1); // o primeiro alarme é definido aqui e retiramos o alarm do ciclo while para não haver reset
    /*
      *  Se um alarme já estiver registado, uma outra chamada à função irá reiniciar o temporizador.
    */
    while (1) {
        pause(); // Força o processo a entrar em modo de pause até que um sinal seja recebido
        printf("e q irmao\n");
    }
    return 0;
}


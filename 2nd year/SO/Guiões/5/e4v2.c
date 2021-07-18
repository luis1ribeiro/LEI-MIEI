#include <unistd.h>
#include <sys/wait.h>

/*
 * Escreva um programa que emule o funcionamento do interpretador de comandos na execução encadeada
 * de ls /etc | wc -l
 */
int main()
{
    int pfd[2];
    char buf[10];
    pipe(pfd);
    if (!fork()) { // outro filho
        dup2(pfd[0], 0); // std input
        close(pfd[1]); // fecha o descritor de escrita
        execlp("wc", "wc","-l", NULL);
    }
    if (!fork()) {
      dup2(pfd[1], 1); // std output
      close(pfd[0]); // fecha o descritor de leitura
      execlp("ls", "ls", "/etc", NULL);
    }

    close(pfd[0]);
    close(pfd[1]);

    for (int w; w<2; w++)
      wait(&w);

    return 0;
}
/* envia para o stdout o descritor de escrita. O stdin passa a ter o descritor de leitura.
 * Num processo executa o ls /etc e o outro processo recebe essa informação e executa o wc -l.
 * Como o stdout tem o descritor de escrita, vai imprimir o wc -l de ls /etc.
*/


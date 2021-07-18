#include <unistd.h>
#include <stdio.h>
#include <sys/wait.h>

/*
 * Escreva um programa que emule o funcionamento do interpretador de comandos na execução encadeada
 * de ls /etc | wc -l
 */
int main()
{
    char** c[4]; //string de comandos
    int n=4; int i=0; int r;

    char* grep[] = { "grep", "-v", "^#", "/etc/passwd", NULL };
    char* cut[] = { "cut", "-f7", "-d:", NULL };
    char* uniq[] = { "uniq", NULL };
    char* wc[] = { "wc", "-l", NULL };

    c[0] = grep; c[1] = cut; c[2] = uniq; c[3] = wc;

 // int fout = dup(1);
 // int fin = dup(0);
    //tenho q ter n-1 pipes
    int p[n-1][2];

    while(i < n) {
      //pipe(p[i]);

      if (i == 0) {
        if (pipe(p[i])!=0) {
          perror("pipe");
          return -1;
        }
        if(!fork()) {

          close(p[i][0]);

          dup2(p[i][1],1);
          close(p[i][1]);

          execvp(c[i][0],c[i]);
          _exit(0);
        }
        close(p[i][1]);

      }

      if ( i < n-1 ) {
        if (pipe(p[i])!=0) {
          perror("pipe");
          return -1;
        }

        if(!fork()) {
          dup2(p[i-1][0],0);
          dup2(p[i][1],1);
          close(p[i-1][0]);
          close(p[i][1]);

          execvp(c[i][0],c[i]);
          _exit(0);
        }
          close(p[i-1][0]);
          close(p[i][1]);
      }

      if ( i == n-1 ) {
        if (pipe(p[i])!=0) {
          perror("pipe");
          return -1;
        }
        if(!fork()) {

          close(p[i][1]);
          dup2(p[i-1][0],0);
          close(p[i-1][0]);

          execvp(c[i][0],c[i]);
          _exit(0);
        }
      }
       i++;
       wait(&r);
    }

    return 0;
}

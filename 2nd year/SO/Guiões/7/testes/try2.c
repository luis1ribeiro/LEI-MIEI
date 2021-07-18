/*
   * Considere o programagrep, que procura ocorrˆencias de uma dada palavra
   * num dado ficheiro.grep "palavra" ficheiro.txtImplemente o programamultigrepcom a mesma funcionalidade,
   * tirando partido do programagrep,mas que permite ter vários processos a procurar uma dada
   * palavra em vários ficheiros concorrentemente.
   * Certifique-se de que todos os processos terminam assim que um deles encontre a palavra.
*/

#include <signal.h>
#include <stdio.h>
#include <sys/types.h>
#include <unistd.h>
#include <sys/wait.h>


#define N 20

int main (int argv, char** argc){

  int i; int r;
  char buf[1024];
  char* palavra = "ola";
  int fd = open("triste.txt", O_CREAT | O_WRONLY | O_APPEND, 0666);

  for(i = 0; i < N; i++){
      if(!fork()){
          execl("/bin/grep","grep",palavra,"fic.txt","<","triste.txt",NULL);
          if((r=read(fd,&buf,1024))>0) {
            /*alarmall n sigquit all*/
            execl("/bin/rm","rm","-rf","triste.txt",NULL);
          }
          _exit(0);
      }
      int status; wait(&status);
  }
  return 0;
}

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

int spawngrep (char* word, char* file, int i) {

  int pid;
  if ((pid=fork()) == 0) {
    printf("process %d with pid %d exec(grep) for file %s\n", i, getpid(),file);
    sleep((i+1)*8); // para verificar que mata os outros processos corretamente

    // lembrar:  exec substitiu o processo corrente, ou seja o wexitstatus nao é do filho mas sim do exec!
    if(execlp("grep","grep",word,file,NULL) < 0)
      perror("exec");

    _exit(1);
  }

  return pid;
}


int main (int argc, char* argv[]) {
  if(argc < 3) {
    printf("Usage: multigrep <word> file1 file2 ...\n");
    return 1;
  }

  int files_count = argc - 2;
  int pids[files_count];
  char** files = argv + 2;

  for(int i = 0; i < files_count; i++){
    pids[i] = spawngrep(argv[1],files[i],i);
  }

  int found = 0;
  int status = 0;
  int pid;
  int pid_found = -1;

  while(!found && (pid = wait(&status)) > 0){
    //grep returns 0 if anything was found
    if (WIFEXITED(status)) {
      switch (WEXITSTATUS(status)) {
        case 0:
          printf("process %d found the word.\n", pid);
          found = 1;
          break;
        case 1:
          printf("process %d did not find the word.\n",pid);
          break;
      }
    }
  }

  if (found == 1) {

    for(int i = 0; i < files_count; i++) {
      printf ("killing process %d\n", pids[i]);

      // evitar a possibilidade de um kill -1 (mata tudo)
      if (pids[i]>0) {
        kill(pids[i],SIGKILL);
      }

      // mostra que processo foi interrompido
      waitpid(pids[i],&status,0);
      if(!WIFEXITED(status)) {
        printf("process %d was interrupted.\n", pids[i]);
      }
      else {
        printf("process %d ended correctly already.\n",pids[i]);
      }
    }
  }

  return !found;
}


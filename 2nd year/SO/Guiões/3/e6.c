#include <unistd.h>
#include <sys/wait.h>
#include <stdio.h>
#include <string.h>


int mysystem (char* command) {

  int fork_ret, exec_ret, wait_ret, res;

  char *exec_args[20];
  char *string;
  int status;
  int i = 0;

  fork_ret = fork();

  string=strtok(command," ");

  while(string!=NULL) {
    exec_args[i] = string;
    string=strtok(NULL," ");
    /*
       The first call to strtok must pass the C string to tokenize,
       and subsequent calls must specify NULL as the first argument,
       which tells the function to continue tokenizing the string you passed in first.
    */
    i++;
  }

  exec_args[i] = NULL;

  if(!fork_ret) {

    exec_ret=execvp(exec_args[0],exec_args);
    _exit(exec_ret);

  }
  else {
    if(fork_ret!=-1) {

      wait_ret = waitpid(fork_ret,&status,0);
      if (WIFEXITED(status)) {
        res=WEXITSTATUS(status);
      }
      else {
        res=-1;
      }
    }
    else {res=-1;}
  }

  return res;

}

int main (int argc, char** argv){
  char comando1[] = "ls -l -a -h";
  char comando2[] = "sleep 1";
  char comando3[] = "ps";
  int ret;

  printf("a executar mysystem para %s\n",comando1);
  ret = mysystem(comando1);

  printf("a executar mysystem para %s\n",comando2);
  mysystem(comando2);

  printf("a executar mysystem para %s\n",comando3);
  mysystem(comando3);

  return 0;

}


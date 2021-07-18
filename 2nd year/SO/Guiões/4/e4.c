#include <fcntl.h>
#include <stdio.h>
#include <sys/types.h>
#include <unistd.h>


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

/*
 * Escreva um programa redir que permita executar um comando, opcionalmente redireccionando a en-
 * trada e/ou a saı́da. O programa poderá ser invocado, com:
 * redir [-i fich_entrada] [-o fich_saida] comando arg1 arg2 ...
 */
int main(int argc, char** argv)
{

  int res=0;
  int commandindex=1;
  char command[1024];

  int ifd, ofd;

  bzero(command,1024);

  if(strcmp(argv[commandindex],"-i")==0){

    ifd = open(argv[commandindex+1],O_RDONLY);
    res = dup2(ifd,0);
    close(ifd);
    commandindex+=2;
  }
  if(strcmp(argv[commandindex],"-o")==0){

    ofd = open(argv[commandindex+1],O_CREAT | O_TRUNC | O_WRONLY, 0666);
    res = dup2(ofd,1);
    close(ofd);
    commandindex+=2;
  }

  while(commandindex<argc){
    strcat(command,argv[commandindex]);
    strcat(command," ");
    commandindex++;
  }

  mysystem(command);
  return 0;
}

/*
    > ./a.out -i /etc/password -o teste wc
    > cat teste
    110 297 6946
*/

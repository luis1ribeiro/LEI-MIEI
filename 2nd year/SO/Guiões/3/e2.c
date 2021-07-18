#include <unistd.h>
#include <stdio.h>
#include <sys/wait.h>

int main (int argc, char* argv[]) {
  int status;

  if(!fork()) {
    execl("/bin/ls","ls","-l",NULL);
    _exit(0);
  }
  wait(&status);
}

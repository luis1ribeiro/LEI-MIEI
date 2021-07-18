#include <unistd.h>
#include <stdio.h>
#include <sys/wait.h>
#include <string.h>

/*
 * Escreva um programa que crie um pipe anónimo e de seguida crie um processo filho. Relembre que as
 * associações dos descritores de ficheiros são preservadas na criação do processo filho. Experimente o pai
 * enviar uma linha de texto através do descritor de escrita do pipe, e o filho receber uma linha a partir do
 * respectivo descritor de leitura. Note que a informação foi transferida do pai para o filho. Experimente
 * de seguida provocar um atraso antes do pai enviar a linha de texto (p. ex., sleep(5)). Note agora que
 * a leitura do filho bloqueia enquanto o pai não realizar a operação de escrita no pipe. Experimente agora
 * inverter os papeis de modo à informação ser transmitida do filho para o pai
 */

 int main() {
 	int p[2]; int res; int status;
 	pid_t pid = fork();
    char buffer[20]; char line[]="linha1\n";

    if(pipe(p) == -1) { // crio o pipe neste if
    	perror("pipe");
    	return -1;
    }

 	switch (pid) {
 		case -1:
 			perror("fork");
 			return -1;
 		case 0: // filho
 			// fechar descritor de escrita no filho
 			close(p[1]);
 			res = read(p[0],&buffer,sizeof(buffer));
 			printf("[FILHO]: read %s from pipe\n", buffer);
 			close(p[0]);
 			_exit(0);
 		case 1: // pai
 			// fechar descritor de leitura no pai
 			close(p[0]);
 			sleep(5); // o filho bloqueia enquanto o pai ainda nao escreveu
 			write(p[1], &line, sizeof(line));
 			printf(" [PAI]: wrote line to pipe\n");
 			close(p[1]);
 			wait(&status);
 	}
 	return 0;
 }


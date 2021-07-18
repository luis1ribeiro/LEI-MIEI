#include <stdio.h>
#include <sys/types.h>
#include <unistd.h>
#include <fcntl.h>


#define BUFFER 1024

int main (int argv, char** args){

	char buff[BUFFER];
	int r;

	int reader=open(args[1], O_RDONLY);
	int writer=open(args[2], O_CREAT | O_TRUNC | O_WRONLY , 0777);

	if(argv<3) return -1;
	else {
		while((r=read(reader,buff,1024)) > 0){
			write(writer,buff,r);
		}
	}
	return 0;
}


//O_TRUNC --> Apaga tudo e escreve no inicio do ficheiro
//O_CREATE
//O_WRONLY
//O_RONLY
//O_APPEND

#include <fcntl.h>
#include <stdio.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <unistd.h>

#define MAX_LINE_SIZE 10

int main (int argc, char* argv[]) {
	int fd; int bytes_read;
	char buf [MAX_LINE_SIZE];

	if ((fd = open("fifo", O_WRONLY)) == -1) {
		perror ("open");
		return -1;
	} else {
		printf("opened fifo for writing\n");
	}


	while((bytes_read = read(0, &buf, MAX_LINE_SIZE))>0) {
		if(write(fd, &buf, bytes_read)==-1){
      perror("write");
    }
	}

	return 0;
}


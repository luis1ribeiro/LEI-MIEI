#include <fcntl.h>
#include <stdio.h>
#include <string.h>
#include <unistd.h>
#include <stdlib.h>

int main(int argc, char** argv)
{
    int fd = open("pip", O_WRONLY);
    char buff[1024] = "";
    if (fd < 0) {
        printf("[CLIENTE]: Server offline\n");
        return 1;
    } else {
        printf("[CLIENTE]: Opened pipe for writing\n");
    }

    for (int i = 1; i < argc; i++) {
        strcat(buff,argv[i]);
        strcat(buff," ");
        if (i = argc-1) {
          strcat(buff,argv[i]);
          strcat(buff,"\n");
        }
    }
    write(fd, &buff, 1024);
    exit(0);
    close(fd);

    return 0;
}


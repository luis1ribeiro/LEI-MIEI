#include <fcntl.h>
#include <stdio.h>
#include <unistd.h>

//#include "readln.c"

#define BUFSIZE 20


ssize_t readln(int fildes, void* buf, size_t nbyte)
{
    ssize_t size = 0;
    char c;
    char* buff = (char*)buf;
    while (size < nbyte && rlbuf(fildes, &buf[i]) > 0) {
      size++;
      if(((char*)buf)[i-1]=='\n')
        return size;
    }
    return size;
}


ssize_t rlbuf(int fd, char*c){

  char read_buffer[BUF_SIZE];
  int read_buff_pos = -1;
  int read_buff_end = 0;

  if(read_buff_pos == read_buf_end) {
     read_buff_end = read(fd,read_buf,MAX_READ_BUFFER); //leio para o buffer
  switch(read_buff_end) {
    case -1:
      perror(erro);
      return -1;
      break;
    case 0:
      return 0;
      break;
    default:
      read_buff_pos = 0;
  }

  *c = read_buffer[read_buff_pos++];

  }
/*
  }
  else {
    while(i>=0){
      read_buff_end = read(fd,read_buf,MAX_READ_BUFFER);
      if (c == EOF)
        return read_buff_end;
      if (read_buff_end == BUFSIZE && (c != '0' || c != '\n')) {
        read_buff_pos = read_buff_end;
        i=read_buff_end;
    }
      i=-1;
  }
*/
}




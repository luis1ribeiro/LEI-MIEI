#include <string.h>
#include "lib.h"


ssize_t readln (int fildes, void *buf, size_t nbyte){
  char c;
  int i;
  for (i=0;i<nbyte && read(fildes,&c,1)>0;i++){
    memcpy(buf+i,&c,1);
    if (c=='\n'){
      i++;
      break;
    }
  }
  return i;
}

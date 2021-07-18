#include <unistd.h>

#define MAX_BUFFER 1024
#define NUMERO_MAIS_VENDIDOS 20
#define ARGS_VENDA 3
#define ARGS_NUM 2
#define ARGS_MAX_NUM 3

typedef struct venda{
  int cod;
  int quant;
  double fat;
}Venda;

typedef struct artigo{
  int string_nr;
  double preco;
  int tam_str;
}Art;

ssize_t readln(int fildes, void *buf, size_t nbyte);

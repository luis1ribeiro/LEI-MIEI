#include "Faturacao.h"

typedef struct faturacao{
  /*
     N 0
     P 1
  */
  GTree* fat [12][2];
  GArray* nc;
}faturacao;

typedef struct fatnode{
  GPtrArray* fatvendas;
}fatnode;

typedef struct fat_str{
  int unidades;
  float preco;
  int filial;
}fat_str;

Faturacao inicializaFaturacao(){
  Faturacao new = malloc (sizeof(struct faturacao));
  int i;
  int j;
  for (i=0;i<12;i++){
    for (j=0;j<2;j++){
      new->fat[i][j]=g_tree_new((GCompareFunc)g_ascii_strcasecmp);
    }
  }
  new->nc=g_array_new(FALSE,FALSE,sizeof(char*));
 return new;
}

Faturacao insereVenda (Faturacao fatp, Venda v){
  int i=getMes(v)-1;
  int j;
  if (getTipoVenda(v)=='N') j=0;
  else j=1;
  fat_str *new = malloc(sizeof(struct fat_str));
  new->unidades=getUniVenda(v);
  new->preco=getPrecoVenda(v);
  new->filial=getFilial(v);
  gpointer ptr = g_tree_lookup(fatp->fat[i][j],getProdVenda(v));
  if ((struct fatnode *)ptr){
    fatnode *update = (struct fatnode *) ptr;
    g_ptr_array_add(update->fatvendas,new);
  }
  else{
    fatnode *nn = malloc (sizeof (struct fatnode));
    nn->fatvendas=g_ptr_array_new();
    g_ptr_array_add(nn->fatvendas,new);
    g_tree_insert(fatp->fat[i][j],getProdVenda(v),nn);
  }
  return fatp;
}


int vendaFil (Faturacao fatp,Produto p,int mes, char tipo, int filial){
  int i=mes-1,j;
  if (tipo=='N') j=0;
  else j=1;
  gpointer ptr = g_tree_lookup(fatp->fat[i][j],getProduto(p));
  if (!ptr) return 0;
  fatnode *fat = (struct fatnode *) ptr;
  int a;
  for (a=0;a<fat->fatvendas->len;a++){
    fat_str *str = (fat_str *) g_ptr_array_index(fat->fatvendas,a);
    if (str->filial==filial) return 1;
  }
  return 0;
}

int nrVendaProduto(Faturacao fatp, Produto p, int mes, char tipo){
  int i=mes-1,j;
  if (tipo=='N') j=0;
  else j=1;
  gpointer ptr = g_tree_lookup(fatp->fat[i][j],getProduto(p));
  if (!ptr) return 0;
  fatnode *fat = (struct fatnode *) ptr;
  return (fat->fatvendas)->len;
}

float fatProduto(Faturacao fatp, Produto p, int mes, char tipo){
  int i=mes-1,j;
  if (tipo=='N') j=0;
  else j=1;
  gpointer ptr = g_tree_lookup(fatp->fat[i][j],getProduto(p));
  if (!ptr) return 0;
  fatnode *fat = (struct fatnode *) ptr;
  int k;
  float t=0.0;
  for (k=0;k<(fat->fatvendas)->len;k++){
    fat_str *f = (fat_str*) g_ptr_array_index(fat->fatvendas,k);
    t+=f->preco*f->unidades;
  }
  return t;
}

gboolean conta_nc (gpointer key, gpointer value, gpointer data){
  char* prod = (char *) value;
  Produto p = criaProduto(prod);
  Faturacao* f = data;
  int mes;
  for(mes=1;mes<=12;mes++){
    if (nrVendaProduto(*f,p,mes,'N')!=0) break;
    if (nrVendaProduto(*f,p,mes,'P')!=0) break;
  }
  if (mes<=12) return FALSE;
  else{
    g_array_append_val((*f)->nc,key);
  }
  return FALSE;
}

int atualizanc(Faturacao fatp, Cat_Prods catp){
  if (getNaoComprados_nr(fatp)>0) return 0;
  char letra1,letra2;
  for(letra1='A';letra1<='Z';letra1++){
    for(letra2='A';letra2<='Z';letra2++){
      Lista_Prods lst = produtosPorLetra(catp,letra1);
      g_hash_table_foreach(getProdutosLetra(lst,letra2-'A'),(GHFunc)conta_nc,&fatp);
    }
  }
  return 0;
}

int getNaoComprados_nr (Faturacao fatp){
  return (fatp->nc)->len;
}

GArray* getNaoComprados (Faturacao fatp){
  return fatp->nc;
}

gboolean cvendas (gpointer key, gpointer value, gpointer data){
  int *t = data;
  fatnode *f = (struct fatnode *) value;
  *t+=(f->fatvendas)->len;
  return FALSE;
}

gboolean cfat (gpointer key, gpointer value, gpointer data){
  float *t = data;
  fatnode *f = (struct fatnode *) value;
  int i;
  for(i=0;i<(f->fatvendas)->len;i++){
    fat_str *fat = (fat_str*) g_ptr_array_index(f->fatvendas,i);
    *t+=fat->preco*fat->unidades;
  }
  return FALSE;
}

int getVendasEntre(Faturacao fatp, int x,int y){
  int total=0;
  x--;
  y--;
  while(y>=x){
    g_tree_foreach(fatp->fat[y][0],(GTraverseFunc)cvendas,&total);
    g_tree_foreach(fatp->fat[y][1],(GTraverseFunc)cvendas,&total);
    y--;
  }
  return total;
}

float getFatEntre(Faturacao fatp, int x, int y){
  float total=0;
  x--;
  y--;
  while(y>=x){
    g_tree_foreach(fatp->fat[y][0],(GTraverseFunc)cfat,&total);
    g_tree_foreach(fatp->fat[y][1],(GTraverseFunc)cfat,&total);
    y--;
  }
  return total;
}

typedef struct f_help{
  Faturacao fatp;
  int filial;
  GArray *arr;
}f_help;

gboolean conta_nc_fil (gpointer key, gpointer value, gpointer data){
  f_help* *h =data;
  char* prod= (char*) value;
  Produto p = criaProduto(prod);
  int mes;
  for(mes=1;mes<=12;mes++){
    if (vendaFil((*h)->fatp,p,mes,'N',(*h)->filial)!=0) break;
    if (vendaFil((*h)->fatp,p,mes,'P',(*h)->filial)!=0) break;
  }
  if (mes<=12) return FALSE;
  else{
    g_array_append_val((*h)->arr,prod);
  }
  return FALSE;
}

GArray* getNaoCompFil(Faturacao fatp, Cat_Prods catp, int filial){
  f_help *h = malloc(sizeof(struct f_help));
  h->arr=g_array_new(FALSE,FALSE,(sizeof(char*)));
  h->filial=filial;
  h->fatp=fatp;
  char letra1,letra2;
  for(letra1='A';letra1<='Z';letra1++){
    for(letra2='A';letra2<='Z';letra2++){
      Lista_Prods lst = produtosPorLetra(catp,letra1);
      g_hash_table_foreach(getProdutosLetra(lst,letra2-'A'),(GHFunc)conta_nc_fil,&h);
    }
  }
  return h->arr;
}

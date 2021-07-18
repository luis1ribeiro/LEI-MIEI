#include "Filial.h"
#include <stdio.h>

typedef struct filial{
  /*
    N 0
    A
    P 1
  */
  GTree* fil [3][12];
  GArray* compT;
  int nc;
}filial;

typedef struct fnode{
  GPtrArray* filvendas;
}fnode;

typedef struct fi_str{
  char* p;
  char tipo;
  int unidades;
  float preco;
}fi_str;

Filial inicializaFilial(){
  Filial new = malloc(sizeof(struct filial));
  int fil;
  int mes;
  for(fil=0;fil<3;fil++){
    for(mes=0;mes<12;mes++){
      new->fil[fil][mes]=g_tree_new((GCompareFunc)g_ascii_strcasecmp);
    }
  }
  new->compT=g_array_new(FALSE,FALSE,sizeof(char*));
  new->nc=0;
  return new;
}

Filial insereFilV (Filial f, Venda v){
  int mes=getMes(v)-1;
  int fil=getFilial(v)-1;
  fi_str *new = malloc (sizeof(struct fi_str));
  new->p=getProdVenda(v);
  new->unidades=getUniVenda(v);
  new->preco=getPrecoVenda(v);
  new->tipo=getTipoVenda(v);
  gpointer ptr = g_tree_lookup(f->fil[fil][mes],getCliVenda(v));
  if ((struct fnode *) ptr){
    fnode* update = (struct fnode*) ptr;
    g_ptr_array_add(update->filvendas,(gpointer)new);
  }
  else{
    fnode *nn = malloc(sizeof(struct fnode));
    nn->filvendas=g_ptr_array_new();
    g_ptr_array_add(nn->filvendas,(gpointer)new);
    g_tree_insert(f->fil[fil][mes],getCliVenda(v),nn);
  }
  return f;
}

int nr_compraCli (Filial f, Cliente c, int mes, int filial){
  int i=mes-1;
  int j=filial-1;
  gpointer ptr = g_tree_lookup(f->fil[j][i],getCliente(c));
  if (!ptr) return 0;
  fnode *n = (struct fnode *) ptr;
  int k,t=0;
  for (k=0;k<(n->filvendas)->len;k++){
    fi_str *str = (fi_str*) g_ptr_array_index(n->filvendas,k);
    t+=str->unidades;
  }
  return t;
}

int fat_compraCli (Filial f, Cliente c, int mes, int filial){
  int i=mes-1;
  int j=filial-1;
  gpointer ptr = g_tree_lookup(f->fil[j][i],getCliente(c));
  if (!ptr) return 0;
  fnode *n = (struct fnode *) ptr;
  int k;
  float t=0.0;
  for (k=0;k<(n->filvendas)->len;k++){
    fi_str *str = (fi_str*) g_ptr_array_index(n->filvendas,k);
    t+=str->preco*str->unidades;
  }
  return t;
}

int nr_clientesCompraram(Filial f, int filial, int mes){
  return g_tree_nnodes(f->fil[filial-1][mes-1]);
}

gboolean le_cli (gpointer key, gpointer value, gpointer data){
  char* cli = (char *) key;
  GArray* *clientes = data;
  g_array_append_val(*clientes,cli);
  return FALSE;
}

GArray* clientesCompraram(Filial f,int filial, int mes){
  GArray* clientes = g_array_new(FALSE,FALSE,sizeof(char *));
  g_tree_foreach(f->fil[filial-1][mes-1],(GTraverseFunc)le_cli,&clientes);
  return clientes;
}

gboolean cli_nc_nr(gpointer key, gpointer value, gpointer data){
  char* cli = (char*) value;
  int ct=0;
  Filial *f = data;
  int filial,mes;
  for (filial=0;filial<3;filial++){
    for (mes=0;mes<12;mes++){
      if (g_tree_lookup((*f)->fil[filial][mes],cli)==NULL)
        ct++;
    }
  }
  if (ct==36) (*f)->nc++;
  return FALSE;
}

int cliente_nc (Filial f, Cat_Cli catc){
  if (f->nc>0) return f->nc;
  char fst;
  for(fst='A';fst<='Z';fst++){
    g_hash_table_foreach(clientesLetra(catc,fst),(GHFunc)cli_nc_nr,&f);
  }
  return f->nc;
}

gboolean cptd (gpointer key, gpointer value, gpointer data){
  char* cli = (char *) value;
  Filial *f = data;
  int i;
  int f1,f2,f3;
  f1=f2=f3=0;
  for (i=0;i<12 && (f1==0 || f2==0 || f3==0);i++){
    if (f1==0 && g_tree_lookup((*f)->fil[0][i],cli)!=NULL) f1=1;;
    if (f2==0 && g_tree_lookup((*f)->fil[1][i],cli)!=NULL) f2=1;
    if (f3==0 && g_tree_lookup((*f)->fil[2][i],cli)!=NULL) f3=1;
  }
  if (f1==1 && f2==1 && f3==1){
    g_array_append_val((*f)->compT,cli);
  }
  return FALSE;
}

GArray* getCompT(Filial f,Cat_Cli catc){
  if (f->compT->len>0) return f->compT;
  char fst;
  for(fst='A';fst<='Z';fst++){
    g_hash_table_foreach(clientesLetra(catc,fst),(GHFunc)cptd,&f);
  }
  return f->compT;
}

typedef struct helper{
  char* p;
  char tipo;
  int t;
}helper;

gboolean conta_produto (gpointer key, gpointer value, gpointer data){
  helper* *h = data;
  fnode *lst = (struct fnode *) value;
  int i;
  for(i=0;i<(lst->filvendas)->len;i++){
    fi_str *str = (fi_str*)g_ptr_array_index(lst->filvendas,i);
    if (strcmp(str->p,(*h)->p)==0 && str->tipo==(*h)->tipo)
      (*h)->t++;
  }
  return FALSE;
}

int vendasProdutoFil (Filial f, Produto p, int mes, char tipo, int fil){
  helper *h=malloc(sizeof(struct helper));
  h->p=getProduto(p);
  h->tipo=tipo;
  h->t=0;
  g_tree_foreach(f->fil[fil-1][mes-1],(GTraverseFunc)conta_produto,&h);
  int r=h->t;
  free(h);
  return r;
}

gboolean fat_produto (gpointer key, gpointer value, gpointer data){
  helper* *h=data;
  fnode *lst = (struct fnode *) value;
  int i;
  for(i=0;i<(lst->filvendas)->len;i++){
    fi_str *str = (fi_str*) g_ptr_array_index(lst->filvendas,i);
    if (strcmp(str->p,(*h)->p)==0 && str->tipo==(*h)->tipo)
      (*h)->t+=str->unidades*str->preco;
  }
  return FALSE;
}

float fatProdutoFil (Filial f, Produto p, int mes, char tipo, int fil){
  helper *h=malloc(sizeof(struct helper));
  h->p=getProduto(p);
  h->tipo=tipo;
  h->t=0;
  g_tree_foreach(f->fil[fil-1][mes-1],(GTraverseFunc)fat_produto,&h);
  float fat = h->t;
  free(h);
  return fat;
}

typedef struct cliprodH{
  Filial f;
  Produto p;
  char tipo;
  GArray *arr;
}cliprodH;

int elemArr (GArray *arr, char * c){
  int i;
  for (i=0;i<arr->len;i++){
    if (strcmp(g_array_index(arr,char*,i),c)==0) return 1;
  }
  return 0;
}

gboolean cli_prod (gpointer key, gpointer value, gpointer data){
  cliprodH* *h =data;
  fnode *lst = (struct fnode *) value;
  char * cli = (char *) key;
  int i;
  for (i=0;i<lst->filvendas->len;i++){
    fi_str *str = (fi_str*) g_ptr_array_index(lst->filvendas,i);
    if (strcmp(str->p,getProduto((*h)->p))==0 && str->tipo==(*h)->tipo){
      if (!elemArr((*h)->arr,cli))
        g_array_append_val((*h)->arr,cli);
    }
  }
  return FALSE;
}

GArray* cliCompProd (Filial f, Produto p, int filial, char tipo){
  int mes;
  cliprodH *h = malloc (sizeof (struct cliprodH));
  h->f=f;
  h->p=p;
  h->tipo=tipo;
  h->arr= g_array_new (FALSE,FALSE,sizeof(char*));
  for (mes=0;mes<12;mes++){
    g_tree_foreach(f->fil[filial-1][mes],(GTraverseFunc)cli_prod,&h);
  }
  return h->arr;
}

typedef struct dech{
  char* p;
  int uni;
}dech;

int elem_ptrArrDec(GPtrArray *arr, char* p){
  int i;
  for(i=0;i<arr->len;i++){
    dech *h = (dech*) g_ptr_array_index(arr,i);
    if (strcmp(h->p,p)==0) return i;
  }
  return -1;
}

int cmp_uni (gpointer a, gpointer b){
  dech* *x = a;
  dech* *y = b;
  return ((*y)->uni)-((*x)->uni);
}

GArray* prodCompDec (Filial f, Cliente c, int mes){
  GPtrArray *arr = g_ptr_array_new();
  int filial;
  for(filial=0;filial<3;filial++){
    fnode* ptr = g_tree_lookup(f->fil[filial][mes-1],getCliente(c));
    if (ptr){
      int i;
      for (i=0;i<ptr->filvendas->len;i++){
        fi_str *str = (fi_str*) g_ptr_array_index(ptr->filvendas,i);
        int index;
        if ((index=elem_ptrArrDec(arr,str->p))==-1){
          dech *h = malloc (sizeof(struct dech));
          h->p=strdup(str->p);
          h->uni=str->unidades;
          g_ptr_array_add(arr,h);
        }
        else{
          dech* *h = g_ptr_array_index(arr,index);
          (*h)->uni+=str->unidades;
        }
      }
    }
  }
  g_ptr_array_sort(arr,(GCompareFunc)cmp_uni);
  GArray *a = g_array_new(FALSE,FALSE,sizeof(char*));
  int k;
  for (k=0;k<arr->len;k++){
    char* q = malloc(sizeof(char)*64);
    dech *h = (dech *) g_ptr_array_index(arr,k);
    sprintf(q,"%s ------- %d",h->p,h->uni);
    g_array_append_val(a,q);
  }
  g_ptr_array_free(arr,TRUE);
  return a;
}

typedef struct nh{
  char *p;
  int uni;
  GArray* cli;
}nh;

int cmp_n_h (gpointer a, gpointer b){
  nh* *x = a;
  nh* *y = b;
  return ((*y)->uni)-((*x)->uni);
}

gboolean prod_n (gpointer key, gpointer value, gpointer data){
  GTree* *t=data;
  char* cli = (char*) key;
  fnode * ptr = (fnode *) value;
  int i;
  for(i=0;i<ptr->filvendas->len;i++){
    fi_str* str = (fi_str*) g_ptr_array_index(ptr->filvendas,i);
    nh* n = (nh*) g_tree_lookup((*t),str->p);
    if(!n){
      nh *h = malloc(sizeof(struct nh));
      h->p=strdup(str->p);
      h->uni=str->unidades;
      h->cli=g_array_new(FALSE,FALSE,sizeof(char*));
      g_array_append_val(h->cli,cli);
      g_tree_insert((*t),str->p,h);
    }
    else{
      n->uni+=str->unidades;
      if (!elemArr(n->cli,cli))
        g_array_append_val(n->cli,cli);
    }
  }
  return FALSE;
}

gboolean guarda (gpointer key, gpointer value, gpointer data){
  GPtrArray* *arr = data;
  nh *h = (nh*) value;
  g_ptr_array_add(*arr,h);
  return FALSE;
}

GArray* prodNcomp (Filial f, int fil, int N){
  GTree* t = g_tree_new((GCompareFunc)g_ascii_strcasecmp);
  int mes;
  int filial;
  if (fil==0){
    for (filial=0;filial<3;filial++){
      for (mes=0;mes<12;mes++){
        g_tree_foreach(f->fil[filial][mes],(GTraverseFunc)prod_n,&t);
      }
    }
  }
  else{
    for (mes=0;mes<12;mes++){
      g_tree_foreach(f->fil[fil-1][mes],(GTraverseFunc)prod_n,&t);
    }
  }
  GPtrArray* arr = g_ptr_array_new();
  g_tree_foreach(t,(GTraverseFunc)guarda,&arr);
  g_ptr_array_sort(arr,(GCompareFunc)cmp_n_h);
  GArray *a = g_array_new(FALSE,FALSE,sizeof(char*));
  int k;
  for (k=0;k<N;k++){
    char* q = malloc(sizeof(char)*1024);
    nh *h = (nh *) g_ptr_array_index(arr,k);
    sprintf(q,"%s --- unidades: %d ---- clientes: %d",h->p,h->uni,h->cli->len);
    g_array_append_val(a,q);
  }
  g_ptr_array_free(arr,TRUE);
  g_tree_destroy(t);
  return a;
}

typedef struct treshelp{
  char* p;
  float fat;
}treshelp;

int elem_ptrArr(GPtrArray *arr, char* p){
  int i;
  for(i=0;i<arr->len;i++){
    treshelp *h = (treshelp*) g_ptr_array_index(arr,i);
    if (strcmp(h->p,p)==0) return i;
  }
  return -1;
}

int cmp_fat (gpointer a, gpointer b){
  treshelp* *x = a;
  treshelp* *y = b;
  return ((*y)->fat)-((*x)->fat);
}

GArray* tresProdutos (Filial f, Cliente c){
  int filial,mes;
  GPtrArray *arr = g_ptr_array_new();
  for(filial=0;filial<3;filial++){
    for(mes=0;mes<12;mes++){
      fnode* ptr = g_tree_lookup(f->fil[filial][mes],getCliente(c));
      if(ptr){
        int i;
        for (i=0;i<ptr->filvendas->len;i++){
          fi_str *str = (fi_str*) g_ptr_array_index(ptr->filvendas,i);
          int index;
          if ((index=elem_ptrArr(arr,str->p))==-1){
            treshelp *h = malloc(sizeof(struct treshelp));
            h->p=strdup(str->p);
            h->fat=str->unidades*str->preco;
            g_ptr_array_add(arr,h);
          }
          else{
            treshelp* *h = g_ptr_array_index(arr,index);
            (*h)->fat+=str->unidades*str->preco;
          }
        }
      }
    }
  }
  g_ptr_array_sort(arr,(GCompareFunc)cmp_fat);
  GArray *a = g_array_new(FALSE,FALSE,sizeof(char*));
  int k;
  for (k=0;k<3;k++){
    char* q = malloc(sizeof(char)*64);
    treshelp *h = (treshelp *) g_ptr_array_index(arr,k);
    sprintf(q,"%s ------- %.2f €",h->p,h->fat);
    g_array_append_val(a,q);
  }
  g_ptr_array_free(arr,TRUE);
  return a;
}

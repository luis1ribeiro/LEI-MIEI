%{
 int yylex();
 void yyerror(char*);
#define _GNU_SOURCE         /* See feature_test_macros(7) */
#include <stdio.h>
#include <string.h>
 typedef struct nodo { char * at; struct nodo *next; } *LIST;
 LIST push(LIST l, char * id);
 char* ramos(char*, LIST);
%}

%union{ char* s ; struct nodo *l; }

%token ID STR TXT AB BF
%type <s> ID STR TXT abrelem abrefechaelem elem filhos
%type <l> atribs

%%                 // AB = '</'  BF '/>'

xml :  elem                       { printf("digraph{\n%s\n}\n",$1);}
    ;

elem: abrelem filhos fechaelem    { FIXME }
    | abrefechaelem               { FIXME }
    ;

filhos : TXT filhos               { FIXME } // (TXT | elem)*
       | elem filhos              { asprintf(&$$,"%s\n%s",$1,$2);}
       |                          { FIXME }
       ;

abrelem: '<' ID atribs '>'        { $$=ramos($2,$3); }
       ;

abrefechaelem: '<' ID  atribs BF  { $$=ramos($2,$3); }
       ;

fechaelem: AB ID '>'
       ;

atribs :                          { $$=NULL;}         // (ID=STR)*
       | ID '=' STR atribs        { $$=push($4,$1);}
       ;
%%

int main(){
   yyparse();
   return 0;
}

void yyerror(char* s){
   extern int yylineno;
   extern char* yytext;
   fprintf(stderr, "Linha %d: %s (%s)\n",yylineno,s,yytext);
}

LIST push(LIST l, char * id){
   LIST aux  = malloc(sizeof( struct nodo));
   aux->at   = id;
   aux->next = l;
   return aux;
}

char* ramos(char* no, LIST nos) { char * aux = "";
   for(LIST l=nos; l ; l=l->next){
       asprintf(&aux,"%s\n\"%s\" -> \"%s\";",aux,no,l->at);}
   return aux;
}

/* TPC: completar os FIXME para construir o grafo completo */
/* TPC: gramÃ¡tica  incluir a declaraÃ§Ã£o inicial:  <?xml version="1.0"?> */


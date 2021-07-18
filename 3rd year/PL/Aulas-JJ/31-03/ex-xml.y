%{
 int yylex();
 void yyerror(char*);
#include <stdio.h>
  int n=0;
%}

%union{ char* s ; }

%token ID STR TXT AB BF
%type <s> ID STR TXT

%%                 // AB = '</'  BF '/>'

xml :  elem
    ;

elem: abrelem filhos fechaelem
    | abrefechaelem
    ;

filhos : TXT filhos               // (TXT | elem)*
       | elem filhos
       |
       ;

abrelem: '<' ID atribs '>'        {printf("â†’ %s\n",$2 );}
       ;

abrefechaelem: '<' ID atribs BF   {printf("â†’ %s\n",$2 );}
       ;

fechaelem: AB ID '>'
       ;

atribs :                           // (ID=STR)*
       | ID '=' STR atribs
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

/*  TPC: para cada  <x  a="v"   b="v"> ...   escrever:  x->a ; x->b   */
/*  TPC: gramÃ¡tica  incluir a declaraÃ§Ã£o inicial:  <?xml version="1.0"?> */


%code{
#define _GNU_SOURCE
#include <stdio.h>
#include <stdlib.h>
int yylex();
int yyerror(char* s);
}

%union {char* n;}
%token URI A STR
%type <n>  URI  elems arvd elem STR

%%     // IN:
       // OUT:

ontologia: ...

gtriplos : sub gpares '.'
         ;

gpares : pares
       | gpares ';' pares
       ;

pares : rel comps
         ;

comps : comp
      | comps ',' comp
      ;

comp : URI
     | STR ;

sub  : URI ;

rel  : A
     | URI ;

%%

#include "lex.yy.c"

int main(){
   yyparse();
   return 0;
}

int yyerror(char* s){
   printf("erro %d: %s junto a'%s'\n",yylineno,s,yytext);
}

//   a, b, c, d
// cps: cp , cps | cp         (a , ( b, (c , (d))))
// cps: cps , cp | cp         ((((a), b ), c) , d)


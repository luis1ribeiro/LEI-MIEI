%code{
#include <stdio.h>
int yylex();
int yyerror(char* s);
}

%union {int n;}
%token INT
%type <n>  INT  ints

%%     // (1,2,3)   ---> somar int

lista : '(' ints ')'  { printf("cheguei ao fim %d!\n",$2); return 0 ; }
      ;

ints : INT              { $$ = $1; }
     | INT ',' ints     { $$ = $1 + $3; }
     ;

%%

#include "lex.yy.c"

int main(){
   yyparse();
   return 0;
}
int yyerror(char* s){
   printf("erro: %s\n",s);
}


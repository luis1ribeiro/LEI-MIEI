%code{
#include <stdio.h>
int yylex();
int yyerror(char* s);
}

%union {int n;}
%token INT
%type <n>  INT  elems lista elem

%%     // (1,2,3)   ---> somar int

axiom : lista           { printf("soma=%d!\n",$1); }
      | axiom lista     { printf("soma=%d!\n",$2); }
      ;

lista : '(' elems ')'   { $$ = $2;}
      | '(' ')'         { $$ = 0 ; }
      ;

elems : elem            { $$ = $1; }
      | elem ',' elems  { $$ = $1 + $3; }
      ;

elem : INT              { $$ = $1; }
     | lista            { $$ = $1; }
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


%{
 int yylex();
 void yyerror(char*);
#include <stdio.h>
%}

%token TAG TXT BB

%%                 // BB = '</'

xml :  elem                     {printf("ok\n");}
    ;

elem: abrelem filhos fechaelem
    ;

filhos : TXT filhos                   // (TXT | elem)*
       | elem filhos
       |
       ;

abrelem: '<' TAG '>'
       ;

fechaelem: BB TAG '>'
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


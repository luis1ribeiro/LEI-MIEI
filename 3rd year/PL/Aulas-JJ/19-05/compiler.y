%{
#define _GNU_SOURCE

  int yylex();
  void yyerror(char*);

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>

int COUNT_VAR = 0;

struct simb{
  int add;
  int typ;  // 1=int 2=string 3=...
} TS[256];

#define _ad(x) TS[x].add
#define _ty(x) TS[x].typ

%}

%union{
    char c;    // id de variÃ¡vel
    char*s ;   // string
    int n;
    char* ass; // cÃ³digo assembly
    struct exp{ char* ass; int typ; } ex; // expressÃ£o com tipo
}

%token TSTR TINT LER ESCREVER
%token <c> VAR
%token <n> NUM
%token <s> STR

%type <ass> state prog decls decl
%type <ex> exp

%left '+' '-'
%left '/' '*'

%%
axioma: decls prog      {printf("%s\nstart\n%s\nstop\n",$1,$2);}
      ;

decls:                  {$$ = "";}
     | decls decl       {asprintf(&$$, "%s\n%s", $1, $2);}
     ;

prog: prog state        {asprintf(&$$, "%s\n%s", $1, $2);}
    | state             {$$ = $1;}
    ;

state: VAR '=' exp ';'  {asprintf(&$$, "%s\nstoreg %d //%c",$3.ass,_ad($1),$1);}
     | VAR '=' LER ';'  {asprintf(&$$, "read\natoi\nstoreg %d //%c",_ad($1),$1);}
     | ESCREVER exp ';' { // if $2.ty == 1
                          asprintf(&$$, "%s\nwritei\n",$2.ass);}
     ;

decl: TINT VAR ';'      {asprintf(&$$, "pushi 0//%c",$2);
                         _ad($2) = COUNT_VAR;
                         _ty($2) = 1;
                         COUNT_VAR++;} //ocupam 1 posição de memória
    | TSTR VAR ';'      {asprintf(&$$, "pushs \"\" //%c",$2);
                         _ad($2) = COUNT_VAR;
                         _ty($2) = 2;
                         COUNT_VAR++;}
    ;

exp: NUM                {asprintf(&($$.ass), "pushi %d",$1); $$.ty = 1;}
   | VAR                {asprintf(&($$.ass), "pushg %d // %c",_ad($1),$1);}
   | STR                {asprintf(&($$.ass), "pushs %s ",$1); $$.ty = 2;}
   | exp '+' exp        {asprintf(&($$.ass), "%s\n%s\nadd",$1.ass,$3.ass);}
   | exp '-' exp        {asprintf(&($$.ass), "%s\n%s\nsub",$1.ass,$3.ass);}
   | exp '*' exp        {asprintf(&($$.ass), "%s\n%s\nmul",$1.ass,$3.ass);}
   | exp '/' exp        {asprintf(&($$.ass), "%s\n%s\ndiv",$1.ass,$3.ass);}
   | '(' exp ')'        {$$.ass=$2.ass;}
   ;

%%

int main(int argc, char* argv[]){
    extern FILE *yyin;
    if(argc == 2){
       yyin = fopen(argv[1],"r");}
    yyparse();
    return 0;
}

void yyerror(char *s){
    extern int yylineno;
    extern char* yytext;
    fprintf(stderr, "linha %d: %s (%s)\n", yylineno, s, yytext);
}


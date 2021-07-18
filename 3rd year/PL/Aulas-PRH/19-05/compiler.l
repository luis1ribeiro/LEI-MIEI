%{
#include "y.tab.h"
#include <math.h>
void yyerror(char*);
%}
%option noyywrap yylineno

%%

(?i:begin)    {return BEGI;}
(?i:end)      {return END;}

[;:,\[\]]     {return yytext[0];}
(?i:var)      {return VAR;}
(?i:integer)  {return INTEGER;}

[a-zA-Z]      {yylval.c = toupper(yytext[0]); return id;}
[0-9]+        {yylval.num = atoi(yytext); return num;}

#[^#]+#       {;}
[\n\t ]       {;}
.             {yyerror("Caracter Inválido");}

%%




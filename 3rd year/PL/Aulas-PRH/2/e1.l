%option noyywrap yylineno

%{
#include <stdio.h>
  char *eu, *ele;
%}

%%

^(?i:eu)=[^.]+  {ECHO;eu=strdup(yytext+3);}
^(?i:ele)=[^.]+ {ECHO;ele=strdup(yytext+4);}
^(?i:eu):       {printf("%s:",eu);}
^(?i:ele):      {printf("%s:",ele);}


%%

int main()
{
  yylex();
 //printf("Vou gastar %d €, numa quantidade %d\n", precototal, acum);
  return 0;
}


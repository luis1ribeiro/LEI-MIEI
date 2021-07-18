%{
#define _GNU_SOURCE
 int yylex();
 void yyerror(char*);
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
char* base;
int useBase;
FILE *f;
%}

%union{
        char* s;
}

%token PAL NEWLINE ENSEP LETRA TRACO
%type <s> PAL palavraEN palavraPT en pt listpt linha
%left NEWLINE

%%

dic: dic linha NEWLINE          {printf("%s",$2);useBase=0;}
   | error NEWLINE              {yyerrok;}
   |
   ;

linha: LETRA                    {$$=strdup("");}
     | en ENSEP listpt          {if (useBase) asprintf(&$$,"EN %s\n+base %s\n%s\n",$1,base,$3); else asprintf(&$$,"EN %s\n%s\n",$1,$3);}
     | en ':'                   {base = strdup($1); $$ = strdup("");}
     | en ':' ENSEP listpt      {base=strdup($1); asprintf(&$$,"EN %s\n%s\n",$1,$4);}
     ;

listpt: pt                      {asprintf(&$$,"PT %s\n",$1);}
      | listpt ',' pt           {char* aux; asprintf(&aux,"PT %s\n",$3); strcat($$,aux);}
      | listpt ';' pt           {char* aux; asprintf(&aux,"PT %s\n",$3); strcat($$,aux);}

en: en palavraEN                {asprintf(&$$,"%s %s",$1,$2);}
  | palavraEN                   {$$ = strdup($1);}
  ;

pt: pt palavraPT                {asprintf(&$$,"%s %s",$1,$2);}
  | palavraPT                   {$$ = strdup($1);}
  ;

palavraEN: '-'                    {$$ = strdup(base);useBase=1;}
       | PAL                      {$$ = strdup($1);}
       | '(' en ')'               {asprintf(&$$,"(%s)",$2);}
       | ','                      {$$ = strdup(",");}
       | palavraEN NEWLINE palavraEN    {asprintf(&$$,"%s %s",$1,$3);}
       ;

palavraPT: PAL                    {$$ = strdup($1);}
         | '(' pt ')'             {asprintf(&$$,"(%s)",$2);}
         | '+'                    {$$ = strdup("+");}
         | ENSEP                  {$$ = strdup("");}
         | PAL TRACO palavraPT    {asprintf(&$$,"%s%s",$1,$3);}
         ;

%%

int main(){
    f = fopen("y.log","w");
    yyparse();
    fclose(f);
    return 0;
}

void yyerror(char* s){
   extern int yylineno;
   extern char* yytext;
   if (strcmp(yytext,"\n")==0)
        fprintf(f, "Linha %d: %s %s\n", yylineno-1,s,"\\n");
   else fprintf(f, "Linha %d: %s %s\n",yylineno,s,yytext);
}

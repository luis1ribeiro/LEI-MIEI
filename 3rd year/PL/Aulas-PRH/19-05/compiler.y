%{
#define _GNU_SOURCE         /* See feature_test_macros(7) */

//int asprintf(char **strp, const char *fmt, ...);

int yylex();
void yyerror(char*);

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>

int nextadd = 0;
int idtable[26];
%}


%union {char c; int n; char* str;}
%token VAR INTEGER id num BEGI END
%type<c> id
%type<n> num Indice
%type<str> Declaracoes Decls Instr Decl Tipo Vars Var1 Escrita Leitura Atrib Instrucao

%%

LPIS: Declaracoes BEGI Instr END     {printf("%s START\n %s STOP\n",$1,$2);}
    ;

Declaracoes: VAR Decls      {$$ = $2;}
           ;

Decls:                      {$$ = "";}
     | Decls Decl ';'       {asprintf(&$$, "%s %s",$1,$2);}
     ;

Decl: Vars ':' Tipo         {$$ = $1;}
    ;

Tipo: INTEGER
    ;

Vars: Var1                  {$$ = $1;}
    | Vars ',' Var1         {asprintf(&$$, "%s %s",$1,$3);}


Var1: id                    { if(idtable[$1 - 'A'] == -1){      // não posso declarar 2x a mesma variável
                                idtable[$1 - 'A'] = nextadd++;  //ocupam 1 posição de memória
                                asprintf(&$$,"PUSHI %d\n", 0);
                              } else {$$ = ""; yyeror("Error: Variável já declarada!");}
                            }
    | id '[' Indice ']'     { idtable[$1 - 'A'] = nextadd; nextadd + $3;
                              asprintf(&$$, "PUSHN %d\n", $3);
                            }
    ;

Indice: id                  {$$ = idtable[$1 - 'A'];}
      | num                 {$$ = $1;}
      ;

Inst:                       {$$ = "";}
    | Inst Intrucao ';'     {asprintf(&$$,"%s %s",$1,$2);}
    ;

Instrucao: Atrib            {$$ = $1;}
         | Escrita          {$$ = $1;}
         | Leitura          {$$ = $1;}
         ;

Atrib:                      {$$ = "";}
     ;

Escrita: num '!'            {asprintf(&$$,"PUSHI %d\nWRITEI\n",$1);}
       | Var1 '!'           {asprintf(&$$,"PUSHG %d\nWRITEI\n",idtable[atoi($1)-'A']);}
       ;

Leitura: Var1 '?'           {$$ = "";}
       ;
%%

int main(){

    for (int i=0; i<26; i++){
        idtable[i] = -1;
    }

    yyparse();

    for (int i=0; i<26; i++){
        printf("Nome da variável: %c - Endereço: %d\n", i+65,idtable[i]);
    }

    return 0;
}

void yyerror(char *s){
    extern int yylineno;
    extern char* yytext;
    fprintf(stderr, "linha %d: %s (%s)\n", yylineno, s, yytext);
}


%code{
int yylex();
int yyerror(char* s);
#define _GNU_SOURCE         /* See feature_test_macros(7) */
#include <stdio.h>
}

%union {char* n;}
%token ID
%type <n>  ID  elems arvd elem

%%     // dir1( file1, file2, dir2(file3, file3), dir3())
       // dir1 <ul> <li>file1</li> ...   </ul>

fs : arvd           { printf("%s\n",$1); }
   | fs arvd        { printf("%s\n",$2); }
   ;

arvd  : ID '(' elems ')'   { asprintf(&$$,"%s<ul>%s</ul>\n",$1,$3);}   // dir
      | ID '(' ')'         { asprintf(&$$,"%s<ul></ul>\n", $1);}   // dir
      ;

elems : elem           { $$ = $1; }
      | elem ',' elems { asprintf(&$$,"%s\n%s", $1,$3); }
      ;

elem : ID              { asprintf(&$$,"<li>%s</li>", $1); }    // ficheiros
     | arvd            { asprintf(&$$,"<li>%s</li>", $1); }   // dir
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


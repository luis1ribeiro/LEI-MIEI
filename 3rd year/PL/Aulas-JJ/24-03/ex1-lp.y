%{
 int yylex();
 void yyerror(char*);
#include <stdio.h>
%}


%%

texto : lp {printf("ok\n"); }

lp : bpe lp
   |
   ;                // bpe*
bpe: '(' lp ')'
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

// expreg ---> gramaticas
   //   a*            A : A a | ; // A : a A | ;
   //   a+            A : A a | a ;
   //   (a | b)*c     (1) A : Bc ; B : aB | bB | ;
   //                     ... ==> B : (a|b)B | ; => B : (a|b)*
   //                 (2) A : a A | b A | c
   //                 (3) A ; B A | c  ; B : a | b


// outras tentativas de lp
   // lp : '(' ')'                // ()

   // lp : '(' lp ')' |  ;        //  vazio ;  () ; (()) ;  ((()))
                            // nao inclui () () ((()))  ; (()()())   // bpe  ( ... )

   // lp : '(' ')'                // (), (()), ((()))  = rui \ vazio
      // | '(' lp ')'

   // ax : ( ax ) ax | ;          // vazio , (), ()()


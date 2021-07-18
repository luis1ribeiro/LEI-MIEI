%{
/* Declaracoes C diversas */
int acum = 0;
int preco = 0;
int precolinha = 0;
int precototal = 0;
int qtd = 0;
char prod[60];
%}

Abrev ER

%%

^[a-zA-Z]+      {sprintf(prod,"%s",yytext);}
[0-9]+/\ *\-    {qtd=atoi(yytext);acum+=qtd;}
\-[0-9]+\ */€   {
                  preco=atoi(yytext+1);
                  precolinha = preco*qtd;
                  precototal+=precolinha;
                  printf("Vou gastar %d € no produto %s\n", precolinha, prod);
                }
.|\n            {;}

%%
int yywrap()
{ return(1); }

int main()
{
  yylex();
  printf("Vou gastar %d €, numa quantidade %d\n", precototal, acum);
  return 0;
}


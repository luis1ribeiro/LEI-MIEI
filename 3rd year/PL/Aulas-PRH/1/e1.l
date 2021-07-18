%option noyywrap

%%

\\lp    {printf("Linguagem de Programação\n");}
\\lps   {printf("Linguagens de Programação\n");}
\\pl    {printf("Processamento de Linguagens\n");}

.|\n {}

%%

int main() {
  yylex();
  return 0;
}

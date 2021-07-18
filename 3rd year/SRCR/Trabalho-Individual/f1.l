%{
/* Declaracoes C diversas */
%}

Abrev ER
%x lista

%%

\[				{ECHO; BEGIN lista;}
<lista>{
	[0][0-9]+	{printf("%s",yytext+1);}
	[,][\]]		{printf("]");BEGIN INITIAL;}
	[,][,]+		{printf(",");}
	\]			{ECHO; BEGIN INITIAL;}
	.			{ECHO;}
}		
[,][\]]			{printf("]");}
[,][,]+			{printf(",");}
\"nan\"			{printf("nan");}
\)\.\n    		{printf("%s",yytext);} 
\n              	;
.			{ECHO;}

%%
int yywrap()
{ return(1); }

int main()
{
  yylex();
  return 0;
}

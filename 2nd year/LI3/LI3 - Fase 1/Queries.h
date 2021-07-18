#ifndef Queries_h
#define Queries_h

#include "CatProds.h"
#include "CatCli.h"
#include "Faturacao.h"
#include "Filial.h"

int querieUmP (Cat_Prods catp);
int querieUmC (Cat_Cli catc);
int querieUmV (Faturacao fatp, Filial f, Cat_Prods catp, Cat_Cli catc);
int querieDois (Cat_Prods catp, char l, int x, int y);
int querieTres (Faturacao fatp, Filial f, Produto p, int mes);
int querieCinco (Filial f, Cat_Cli catc, int x, int y);
int querieQuatro (Faturacao fatp, Cat_Prods catp, int x, int y, int fil);
int querieSeis (Filial f, Faturacao fatp, Cat_Cli catc);
int querieSete (Filial f, Cliente c);
int querieOito (Faturacao fatp,int x,int y);
int querieNove (Filial f, Produto p, int filial, char tipo, int x, int y);
int querieDez (Filial f, Cliente c, int mes);
int querieOnze (Filial f, int filial, int n);
int querieDoze (Filial f, Cliente c);

#endif

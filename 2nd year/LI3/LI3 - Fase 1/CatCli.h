#ifndef CatCli_h
#define CatCli_h

#include "Cliente.h"
#include <glib.h>

typedef struct cat_cli* Cat_Cli;

Cat_Cli inicializa_CatCli();
Cat_Cli insereCli(Cat_Cli catc, Cliente c);
int existeCli (Cat_Cli catc, Cliente c);
GHashTable* clientesLetra(Cat_Cli catc, char l);
int nrCli (Cat_Cli catc);

#endif

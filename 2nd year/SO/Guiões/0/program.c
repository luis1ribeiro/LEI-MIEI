#include <stdio.h>
#include "header.h"

#define SIZE_V 1024

int main(int argc, char* argv[]) {
	int *vector;
	vector = malloc(1024*sizeof(int));
	fill(vector,SIZE_V,5);
	int c = find(vector,SIZE_V,5);
       	printf ("%d\n",c);
	return 0;

}

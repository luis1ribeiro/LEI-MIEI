#include <stdio.h>
#include "headers.h"

#define SIZE_V 1024

int main(int argc, char* argv[]) {
	int vector[SIZE_V];
	fill(vector,SIZE_V,5);
	int c = find(vector,3,1);
       	printf ("%d\n",c);	
	return 0;

}

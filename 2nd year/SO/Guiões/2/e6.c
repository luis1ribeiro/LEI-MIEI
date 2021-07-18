#include <unistd.h>
#include <sys/wait.h>
#include <stdio.h>
#include <stdlib.h>
#include <stdio.h>

int procuraLinha(int key, int c, int l[c]){

    for(int i = 0; i < c; i++)
        if(l[i] == key) return 1;
    return 0;
}

int procura(int key, int l, int c, int** m){

    for(int i = 0; i < l; i++){
        if(!fork()){
            _exit(procuraLinha(key,c,m[i]));
        }
    }
    for(int j = 0; j < l; j++){
        int status;
        wait(&status);
        if(!WIFEXITED(status)) return -1;
        if(WEXITSTATUS(status)) return 1;
    }
    return 0;
}

int main(int argc, char* argv[]){

    int p = atoi(argv[1]);
    int cols = atoi(argv[2]);
    int rows = atoi(argv[3]);
    int** m;

    printf("%d\n",argc);

    m = (int**) malloc(sizeof(int*) * rows);
    //matrix[i] = (int*) malloc(sizeof(int) * cols);
    for(int l = 0; l < rows; l++){
        m[l] = (int*) malloc(sizeof(int) * cols);
        for(int c = 0; c < cols; c++){
            m[l][c] = rand()%10;
            printf("%d ",m[l][c]);
        }
        printf("\n");
    }

    if(procura(p,rows,cols,m)) printf("Encontrou\n");
    else printf("Não encontrou\n");
    return 0;
}


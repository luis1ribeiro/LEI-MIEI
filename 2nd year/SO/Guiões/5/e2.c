#include <string.h>
#include <unistd.h>
/*
 * Modifique o programa anterior de modo à leitura do pipe ser realizada enquanto não for detectada a
 * situação de end of file no descritor respectivo. Repare que esta situação acontece apenas quando nenhum
 * processo – neste caso, pai e filho – têm aberto o descritor de escrita do pipe
 */

int main()
{
    int pfd[2];
    char buf[10];
    pipe(pfd); // crio aqui o pipe

    if (!fork()) { // filho
        int n;
        close(pfd[1]); // fechar descritor de escrita no filho
        while ((n = read(pfd[0], buf, 10)) > 0) // enquanto estiver a ler alguma coisa (>0) != EOF
            write(1, buf, n);
        close(pfd[0]);
    } else { // pai
        close(pfd[0]); // fechar descritor de leitura no pai
        sleep(2);
        int n;
        write(pfd[1], "Hello Luke\n", 11);
        sleep(2);
        write(pfd[1], "I am your father\n", strlen("I am your father\n"));
        close(pfd[1]);
    }
    return 0;
}


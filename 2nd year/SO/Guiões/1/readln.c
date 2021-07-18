/* Implemente a leitura de uma linha numa função readln, cujo protótipo é compatı́vel com a chamada ao sistema read.
* Nesta versão, leia um caractere de cada vez.
* Nota — nos exercı́cios adicionais é-lhe proposto que implemente uma versão mais eficiente, com base no que aprendeu na alı́nea anterior.
*/

#include <fcntl.h>
#include <stdio.h>
#include <unistd.h>

ssize_t readln(int fildes, void* buf, size_t nbyte)
{
    ssize_t size = 0;
    char c;
    char* buff = (char*)buf;
    while (size < nbyte && read(fildes, &c, 1) == 1) { // vai lendo 1 a 1
        if (c == '\0')
            return size;
        buff[size++] = c; // adiciona ao buffer e incrementa o size
        if (c == '\n')
            return size;
    }
    return size;
}

int main(){
	char buf[10];
	readln(0,buf,10);
    printf("%s",buf);
}


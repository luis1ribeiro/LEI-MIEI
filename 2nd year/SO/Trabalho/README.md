# Sistemas Operativos

Trabalho realizado pelos elementos:

- [André Morais, A83899](https://github.com/Demorales1998)
- [Luís Ribeiro, A85954](https://github.com/luis1ribeiro)
- [Pedro Rodrigues, A84783](https://github.com/pedrordgs)

# Descrição
Simples programa de manutenção de stocks e vendas de artigos.

# Utilização
```
$ git clone https://github.com/pedrordgs/SO.git
$ cd SO
$ make
```

Antes de utilizar qualquer funcionalidade, corra o servidor.
```
$ ./sv &
```

## Manutenção de artigos
```
$ ./ma
```
#### Intruções permitidas
i -> insere artigo. n -> altera nome do artigo. p -> altera preço do artigo. ag -> agrega as vendas, criando um ficheiro na pasta /agregacoes com a data da agregação.
```
$ i massa 12.6   # i (nome do artigo) (preço unitario)
$ n 1 carne      # n (id do artigo) (novo nome do artigo)
$ p 1 9.99       # p (id artigo) (novo preço do artigo)
$ ag
```
## Cliente Venda
```
$ ./cv
```
#### Instruções permitidas
Verificar número de stock do artigo (ex. artigo 10):
```
$ 10
```

Adicionar ou retirar (i.e. vender) stock de um artigo (ex. artigo 10):
```
$ 10 20
$ 10 -20
```
## Agregador
```
$ ./ag
```
Apenas agrega linhas de venda recebidas pelo STDIN no formato [ id quantidade faturado ].

# Testes
Se desejar pode correr o script de testes que insere 100 000 artigos e faz 4 000 000 de instruções para entradas e saídas de stock para 3 clientes em simultâneo.
```
$ ./test.sh
```

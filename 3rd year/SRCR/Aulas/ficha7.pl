/**/
%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% SIST. REPR. CONHECIMENTO E RACIOCINIO - MiEI/3

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Programacao em logica estendida

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% SICStus PROLOG: Declaracoes iniciais

:- set_prolog_flag( discontiguous_warnings,off ).
:- set_prolog_flag( single_var_warnings,off ).
:- set_prolog_flag( unknown,fail ).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% SICStus PROLOG: definicoes iniciais

:- op( 900,xfy,'::' ).
:- dynamic jogo/3.
%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do meta-predicado nao: Questao -> {V,F}

nao( Questao ) :-
    Questao, !, fail.
nao( Questao ).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do meta-predicado demo: Questao,Resposta -> {V,F}

demo( Questao,verdadeiro ) :-
    Questao.
demo( Questao,falso ) :-
    -Questao.
demo( Questao,desconhecido ) :-
    nao( Questao ),
    nao( -Questao ).


%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do meta-predicado solucoes: Questao -> {V,F}
solucoes( X,Y,Z ) :-
    findall( X,Y,Z ).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do meta-predicado comprimento: Questao -> {V,F}
comprimento( S,N ) :-
    length( S,N ).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do meta-predicado evolucao: Questao -> {V,F}
evolucao( Termo ) :-
    solucoes( Invariante,+Termo::Invariante,Lista ),
    insercao( Termo ),
    teste( Lista ).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do meta-predicado insercao: Questao -> {V,F}
insercao( Termo ) :-
    assert( Termo ).
insercao( Termo ) :-
    retract( Termo ),!,fail.


%--------------------------------- - - - - - - - - - -  -  questão i
jogo(1,aa,500).
-jogo(J,1,C) :-
  nao(jogo(J,1,C)),
  nao(excecao(jogo(J,1,C))).


%--------------------------------- - - - - - - - - - -  -  questão ii-incerto
jogo(2,bb,xpto0).
excecao(jogo(J,A,C)) :- jogo(J,A,xpto0)


%--------------------------------- - - - - - - - - - -  -  questão iii-impreciso
excecao(jogo(3,cc,500)).
excecao(jogo(3,cc,2500)).


%--------------------------------- - - - - - - - - - -  -  questão iv-impreciso
excecao(jogo(4,dd,C)) :- C>=25O,C=<750.


%--------------------------------- - - - - - - - - - -  -  questão v-interdito
jogo(5,ee,xpto1).
nulo(interdito).
excecao(jogo(J,A,C)) :- jogo(J,A,interdito)


%--------------------------------- - - - - - - - - - -  -  questão vi-incerto
excepçao(jogo(6,ff,250)).
excecao(jogo(6,ff,C)) :- C>5000.


%--------------------------------- - - - - - - - - - -  -  questão vii-incerto
-jogo(7,gg,2500).
jogo(7,gg,xpto2).
excecao(jogo(J,A,C)):- jogo(J,A,xpto2).


%--------------------------------- - - - - - - - - - -  -  questão viii-impreciso
excecao(jogo(8,hh,C)):- cercade(C,1000).
cercade(X,Y) :- A is 0.9*Y, B is 1.1*Y, X>=A, X=<B.


%--------------------------------- - - - - - - - - - -  -  questão ix-impreciso
excecao(jogo(9,ii,C)) :- cercade(C,3000).

%--------------------------------- - - - - - - - - - -  -  questão xii
%solucoes (X, Y, Z) :- findall(X, Y, Z).

head ([H|T],H).

-seguido([]).
seguido([H|T]) :- head(T,P), H =:= P-1.
seguido([H|T]) :- head(T,P), H \= P-1, seguido(T).

+jogo(Jogo, Arbitro, Ajudas) :: (solucoes(Jogos, jogo(Jogos, Arbitro, _), S), nao(seguido(S))).

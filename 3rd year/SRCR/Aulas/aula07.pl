%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Programacao em logica estendida
% Representacao de conhecimento imperfeito

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


%--------------------------------- - - - - - - - - - -  -  questão i
%prefeito
jogo( 1, AlmeidaAntunes, 500 ).

%--------------------------------- - - - - - - - - - -  -  questão ii
%incerto
jogo( 2, BaltazarBorges, xpto1 ).
excecao( jogo( Jogo,Arbitro,Ajudas ) ) :-
    jogo( Jogo,Arbitro,xpto1 ).

%--------------------------------- - - - - - - - - - -  -  questão iii
%impreciso
excecao(jogo(3, CostaCarvalho, 500)).
excecao(jogo(3, CostaCarvalho, 2000)).

%--------------------------------- - - - - - - - - - -  -  questão iv
%impreciso
excecao(jogo(4, DuarteDurao, Ajudas)) :- Ajudas>=250, Ajudas=<750.

%--------------------------------- - - - - - - - - - -  -  questão v
%interdito
jogo(5, EdgarEsteves, xpto3).
excecao(jogo(Jogo, Arbitro, Ajudas)) :- jogo(Jogo, Arbitro, xpto3).

%--------------------------------- - - - - - - - - - -  -  questão vi
%incerto
jogo(6, FranciscoFranca, 250).
excecao(jogo(6, FranciscoFranca, Ajudas)) :- Ajudas > 5000.

%--------------------------------- - - - - - - - - - -  -  questão vii

-jogo(7, GuerraGodinho, 2500). %retira da base de conhecimento a possibilidade de ter recebido 2500euros
jogo(7, GuerraGodinho, xpto4).
excecao(jogo(Jogo, Arbitro, Ajudas) :- jogo(Jogo, Arbitro, xpto4).

%--------------------------------- - - - - - - - - - -  -  questão viii-impreciso
excecao(jogo(8,hh,C)):- cercade(C,1000).
cercade(X,Y) :- A is 0.9*Y, B is 1.1*Y, X>=A, X=<B.


%--------------------------------- - - - - - - - - - -  -  questão ix-impreciso
excecao(jogo(9,ii,C)) :- cercade(C,3000).

%--------------------------------- - - - - - - - - - -  -  questão xii
solucoes (X, Y, Z) :- findall(X, Y, Z).

head ([H|T],H).

-seguido([]).
seguido([H|T]) :- head(T,P), H =:= P-1.
seguido([H|T]) :- head(T,P), H \= P-1, seguido(T).

+jogo(Jogo, Arbitro, Ajudas) :: (solucoes(Jogos, jogo(Jogos, Arbitro, _), S), nao(seguido(S))).


%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% SIST. REPR. CONHECIMENTO E RACIOCINIO - MiEI/3

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Base de Conhecimento.

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% SICStus PROLOG: Declaracoes iniciais

:- set_prolog_flag( discontiguous_warnings,off ).
:- set_prolog_flag( single_var_warnings,off ).
:- set_prolog_flag( unknown,fail ).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do predicado par : X,[H|T] -> {V,F}

ispar(X) :- X mod 2 =:= 0.

par([],L,Res).
par([H|T],[X|Res],R) :- ispar(X), par(T,Res,R).
par([H|T],L,R) :- par(T,L,R).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
%Extensao de um predicado de um número impar

impar(X) :- X mod 2 \= 0.


%--------------------------------- - - - - - - - - - -  -  -  -  -   -
%Extensao de um predicado de um número naturais

naturais(0).
naturais(X) :-
  Z is X-1,
  Z>=0,
  naturais(M).

%count_down(Low,High) :-
%  between(Low,High,Y),
%  Z is High - Y,
%  write(Z),nl.



%--------------------------------- - - - - - - - - - -  -  -  -  -   -
%Extensao de um predicado de um número inteiros

inteiros(0).
inteiros(P) :-
  Z is P+1,
  Z=<0,
  inteiros(Z).
inteiros(P) :-
  Y is P-1,
  Y>=0,
  inteiros(Y).


%--------------------------------- - - - - - - - - - -  -  -  -  -   -
%Extensao de um predicado dos divisores de um número naturais

divisor(N, D) :-
    between(1, N, D),
    0 is N mod D.

divisores(N, Ds) :-
    setof(D, divisor(N, D), Ds).




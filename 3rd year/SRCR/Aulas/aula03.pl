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
% Extensao do predicado pertence : X,[H|T] -> {V,F}

pertence(X,[X|T]).
pertence(X,[H|T]) :- X\=H, pertence(X,T).


%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do predicado comprimento : [H|T],Leng -> {V,F}

comprimento([],0).
comprimento([H|T],Leng) :- comprimento(T,Rest), Leng is Rest + 1.

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do predicado diferentes : [H|T],Leng -> {V,F}

diferentes([],0).
diferentes([H|T],Leng) :- pertence(H,T), diferentes(T,Leng).
diferentes([H|T],Leng) :- diferentes(T,Rest), Leng is Rest + 1.

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do predicado apaga1 : [H|T]  -> {V,F}

apaga(X,[],[]).
apaga(X,[X|T],T).
apaga(X,[H|T],[H|Res]) :- X\= H, apaga(X,T,Res).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do predicado apagaT : [H|T]  -> {V,F}

apagaT(X,[],[]).
apagaT(X,[X|T],Res) :- apagaT(X,T,Res).
apagaT(X,[H|T],[H|Res]) :- X\= H, apagaT(X,T,Res).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do predicado adiciona : [H|T]  -> {V,F}

adiciona(X,L,L) :- pertence(X,[H|T]),!.
adiciona(X,L,[X|L]).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do predicado concat : [H|T]  -> {V,F}

concat([],List,List).
concat([H|T],List,[H|Rest]) :- concat(T,List,Rest).


/**/

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Programacao em logica estendida

:- set_prolog_flag(discontiguous_warnings,off).
:- set_prolog_flag(single_var_warnings,off).
:- set_prolog_flag(unknown,fail).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -




%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do meta-predicado si: Questao,Resposta -> {V,F}
%                            Resposta = { verdadeiro,falso,desconhecido }

si(Questao,verdadeiro) :-
    Questao.
si(Questao,falso) :-
    -Questao.
si(Questao,desconhecido) :-
    nao(Questao),
    nao(-Questao).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -




%---------------------------------- - - - - - - - - -  -  -  -  -   -
% Extensao do meta-predicado nao: Questao -> {V,F}

nao(Questao) :-
    Questao, !, fail.
nao(Questao).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -




%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do predicado voa: A -> {V,F,D}

voa(A) :- excecao(-voa(A)).
-voa(A) :- excecao(voa(A)).

voa(A) :- ave(A), nao((excecao(voa(A)))).
-voa(A) :- mamifero(A), nao((excecao(-voa(A)))).

excecao(voa(A)) :- pinguim(A).
excecao(-voa(A)) :- morcego(A).
excecao(voa(A)) :- avestruz(A).
-voa(tweety).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -




%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do predicado ave: A -> {V,F,D}

ave(pitigui).
ave(A) :- canario(A).
ave(A) :- periquito(A).
ave(A) :- avestruz(A).
ave(A) :- pinguim(A).
-ave(A) :- mamifero(A).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -




%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do predicado canario: A -> {V,F,D}

canario(piupiu).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -




%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do predicado mamifero: A -> {V,F,D}

mamifero(silvestre).
mamifero(A) :- cao(A).
mamifero(A) :- gato(A).
mamifero(A) :- morcego(A).
-mamifero(A) :- ave(A).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -




%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do predicado cao: A -> {V,F,D}

cao(boby).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -




%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do predicado avestruz: A -> {V,F,D}

avestruz(trux).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -




%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do predicado pinguim: A -> {V,F,D}

pinguim(pingu).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -




%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do predicado morcego: A -> {V,F,D}

morcego(batemene).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -

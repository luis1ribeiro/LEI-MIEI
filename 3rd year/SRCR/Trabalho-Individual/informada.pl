%-------------------------------- - - - - - - - - - -  -  -  -  -   -
% SIST. REPR. CONHECIMENTO E RACIOCINIO - MiEI
%
% Luís Mário Macedo Ribeiro - a85954
%

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% SICStus PROLOG: Declaracoes iniciais

:- set_prolog_flag( discontiguous_warnings,off ).
:- set_prolog_flag( single_var_warnings,off ).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% SICStus PROLOG: definicoes iniciais

:- include("paragens.pl").
:- include("carreiras/merged.pl").
:- dynamic paragens/11.
:- use_module(library(lists)).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do meta-predicado nao: Questao -> {V,F}

nao( Questao ) :-
    Questao, !, fail.
nao( _ ).

% Predicado comprimento:
% Lista, Comprimento -> {V,F}
comprimento(S, N) :- length(S, N).

% Predicado função sum
sum([], 0).
sum([H|T], N):-
    sum(T, X),
    N is X + H.

% Predicado max lista
%max of list
max_l([(_,Y)],Y) :- !, true.
max_l([(_,Y)|Xs], M):- max_l(Xs, M), M >= Y.
max_l([(_,Y)|Xs], Y):- max_l(Xs, M), Y >  M.

%Predicado min lista
min_l([(_,Y)],Y) :- !, true.
min_l([(_,Y)|Xs], M):- min_l(Xs, M), M =< Y.
min_l([(_,Y)|Xs], Y):- min_l(Xs, M), Y <  M.

% Predicado correspondencia
corres([],_,[]).
corres([(X,Y)],Y,[X]). 
corres([(X,Y)|Xs],Xt,[X|T]) :- Y == Xt, corres(Xs,Xt,T).
corres([(_,Y)|Xs],Xt,T) :- Y \== Xt, corres(Xs,Xt,T).

% Predicado myreverse
myreverse([],Z,Z).
myreverse([H|T],Z,Acc) :- myreverse(T,Z,[H|Acc]).

% Predicado remove_duplicates
remove_duplicates([H | T], List) :- 
      member(H, T),
      remove_duplicates( T, List).

% Predicado solucoes:
% Termo, Predicado, Lista -> {V,F}
solucoes(T,Q,S) :- findall(T,Q,S).


%--------------------------------- - - - - - - - - - -  -  -  -  -   -
%------------------------------ FUNÇÕES DE INFORMAÇÃO

% Predicado que retorna toda a informação de um nodo.
info_all(Nodo,ProxNodo,D,E):- 
	nodo(Nodo, ProxNodo,D,E).

% Predicado que verifica se existe nodo X a ProxNodo. O mesmo que adjacente.
info(Nodo,ProxNodo):- 
	nodo(Nodo,ProxNodo,_,_).

% Predicado do cálculo do estima, sendo esta, a distância euclidiana entre o Nodo Inicial e o Destino.
estima_dist(X,Y,R) 	:- paragem(X,L1,D1,_,_,_,_,_,_,_,_), paragem(Y,L2,D2,_,_,_,_,_,_,_,_), calc(L1,D1,L2,D2,R).
calc(L1,D1,L2,D2,R) :- R is sqrt((L2-L1)^2 + (D2-D1)^2).

% Predicado auxiliar da pesquisa Gulosa.	
inverso(Xs, Ys):-
	inverso(Xs, [], Ys).

% Predicado auxiliar da pesquisa Gulosa.
inverso([], Xs, Xs).
inverso([X|Xs],Ys, Zs):-
	inverso(Xs, [X|Ys], Zs).

% Predicado auxiliar da pesquisa Gulosa.
seleciona(E, [E|Xs], Xs).
seleciona(E, [X|Xs], [X|Ys]) :- seleciona(E, Xs, Ys).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -


%--------------------------------- - - - - - - - - - -  -  -  -  -   -
%------------------------------ TRABALHO - PESQUISA INFORMADA - PESQUISA GULOSA

%------------------------------------------ QUERIES ------------------------------------------------%
%                                                                                                   %
% -- q1: Calcular um trajeto entre dois pontos;                                                     %
% -- q2: Selecionar apenas algumas das operadoras de transporte para um determinado percurso;       %
% -- q3: Excluir um ou mais operadores de transporte para o percurso;                               %
% -- q4: Identificar quais as paragens com o maior número de carreiras num determinado percurso.    %
% -- q5: Escolher o menor percurso (usando critério menor número de paragens);                      %
% -- q6: Escolher o percurso mais rápido (usando critério da distância) -> 1m por 1km;              %
% -- q7: Escolher o percurso que passe apenas por abrigos com publicidade;                          %
% -- q8: Escolher o percurso que passe apenas por paragens abrigadas;                               %
% -- q9: Escolher um ou mais pontos intermédios por onde o percurso deverá passar.                  %
%                                                                                                   %
%---------------------------------------------------------------------------------------------------%


% -------------------- QUERIE 1 -------------------- 

% Predicado 'main' desta querie, que retorna um trajeto entre dois pontos usando o algoritmo da pesquisa Gulosa.
gulosaq1(Nodo,Destino) :- resolve_gulosa(Nodo,Destino,C), prntq1(C), !.

% Predicado que usa o write para facilitar a compreensao dos dados.
prntq1(H1/H2) :- 
	ignoraq6(H1,R), 																% -- Retiro o Destino do par (Nodo, Destino).
	write("Caminho -> "), write(R), 
	write("  Com Distância -> "), write(H2), 
	write(".\n").

% Todos os predicados que se seguem são relativos à implementação ajustada da pesquisa Gulosa obtida durante as aulas desta UC. Ajustada no sentido que em vez de um goal, tenho um Destino
% que é passado sempre como argumento, isto é o nodo em que estou e o Destino são sempre passados como argumento num par (Nodo,Destino). Quando o nodo for igual ao destino então cheguei ao fim.
adjacente([(Nodo,Destino)|Caminho]/Custo/_, [(ProxNodo,Destino),(Nodo,Destino)|Caminho]/NovoCusto/Est) :-
	nodo(Nodo, ProxNodo, PassoCusto,_),\+ member(ProxNodo, Caminho),
	NovoCusto is Custo + PassoCusto,
	estima_dist(ProxNodo, Destino, Est).	

resolve_gulosa(Nodo,Destino,Caminho/Custo)	:- estima_dist(Nodo,Destino,Estima),
									 		   agulosa([[(Nodo,Destino)]/0/Estima],InvCaminho/Custo/_),
									 		   inverso(InvCaminho,Caminho).

agulosa(Caminhos,Caminho):- obtem_melhor_g(Caminhos,Caminho),
							Caminho = [(Nodo,Destino)|_]/_/_,
							Nodo == Destino.
agulosa(Caminhos,SolucaoCaminho):- 	obtem_melhor_g(Caminhos,MelhorCaminho),
								  	seleciona(MelhorCaminho,Caminhos,OutrosCaminhos),
								  	expande_gulosa(MelhorCaminho,ExpCaminhos),
								  	append(OutrosCaminhos,ExpCaminhos,NovoCaminhos),
								  	agulosa(NovoCaminhos,SolucaoCaminho).


obtem_melhor_g([Caminho],Caminho):- !.
obtem_melhor_g([Caminho1/Custo1/Est1,_/_/Est2|Caminhos],MelhorCaminho):- Est1 =< Est2,
																			  !,
																			  obtem_melhor_g([Caminho1/Custo1/Est1|Caminhos],MelhorCaminho).																			
obtem_melhor_g([_|Caminhos],MelhorCaminho):- obtem_melhor_g(Caminhos,MelhorCaminho).

expande_gulosa(Caminho,ExpCaminhos):- findall(NovoCaminho,adjacente(Caminho,NovoCaminho),ExpCaminhos).


% -- Para verificar, chamar, por exemplo, ? - gulosaq1(21,23).
%--------------------------------- - - - - - - - - - -  -  -  -  -   -


% -------------------- QUERIE 5 --------------------

% Predicado 'main' desta querie, que retorna o menor trajeto entre dois pontos usando o critério do número de paragens. Para isto, uso a pesquisa Gulosa.
gulosaq5(Nodo,Destino) :- resolve_gulosaq5(Nodo,Destino,C), prntq5(C), !.

% Predicado que usa o write para facilitar a compreensao dos dados.
prntq5(H1/H2) :- 
	ignoraq6(H1,R), 																% -- Retiro o Destino do par (Nodo, Destino). 
	write("Caminho com menos paragens -> "), write(R), 
	length(R,TAM), write("  Com tamanho -> "), write(TAM),							% -- Número de paragens relativas ao menor número destas. 
	write("  Com Distância -> "), write(H2), 
	write(".\n").

% Todos os predicados que se seguem são relativos à implementação ajustada da pesquisa Gulosa obtida durante as aulas desta UC. Ajustada no sentido que em vez de um goal, tenho um Destino
% que é passado sempre como argumento, isto é o nodo em que estou e o Destino são sempre passados como argumento num par (Nodo,Destino). Quando o nodo for igual ao destino então cheguei ao fim.
resolve_gulosaq5(Nodo,Destino,Caminho/Custo) :- estima_dist(Nodo,Destino,Estima),
									 		   	agulosaq5([[(Nodo,Destino)]/0/Estima],InvCaminho/Custo/_),
									 		   	inverso(InvCaminho,Caminho).

agulosaq5(Caminhos,Caminho):- 	obtem_melhor_g5(Caminhos,Caminho),
								Caminho = [(Nodo,Destino)|_]/_/_,
								Nodo == Destino.
agulosaq5(Caminhos,SolucaoCaminho):- 	obtem_melhor_g5(Caminhos,MelhorCaminho),
								  		seleciona(MelhorCaminho,Caminhos,OutrosCaminhos),
								  		expande_gulosa(MelhorCaminho,ExpCaminhos),
								  		append(OutrosCaminhos,ExpCaminhos,NovoCaminhos),
								  		agulosaq5(NovoCaminhos,SolucaoCaminho).


obtem_melhor_g5([Caminho],Caminho):- !.
obtem_melhor_g5([Caminho1/Custo1/Est1,Cam2/_/_|Caminhos],MelhorCaminho):- length(Caminho1,X),
																		  length(Cam2,Y),
																		  X =< Y,
																		  !,
																		  obtem_melhor_g5([Caminho1/Custo1/Est1|Caminhos],MelhorCaminho).
obtem_melhor_g5([_|Caminhos],MelhorCaminho):- obtem_melhor_g5(Caminhos,MelhorCaminho).


% -- Para verificar, chamar, por exemplo, ? - gulosaq5(21,23).
%--------------------------------- - - - - - - - - - -  -  -  -  -   -


% -------------------- QUERIE 6 --------------------

% Predicado que usa o write para facilitar a compreensao dos dados.
prntq6(H/H1) :- 
	ignoraq6(H,R), 																% -- Retiro o Destino do par (Nodo, Destino). 
	write("Caminho -> "), write(R), 
	write("  Distância mínima -> "), write(H1), 
	write(".\n").

% Predicado que dado uma lista de pares, ignora o 2º elemento do par. Neste caso é nos útil por causa do nosso caminho ter sempre o par (NodoAtual,Destino).
ignoraq6([],[]).
ignoraq6([(H,_)|T],[H|TT]) :- ignoraq6(T,TT).

% Predicado 'main' desta querie, que retorna o menor trajeto entre dois pontos usando o critério da distância. Para isto, uso a pesquisa Gulosa.
gulosaq6(Nodo,Destino) :- resolve_gulosa(Nodo,Destino,C), prntq6(C), !.


% -- Para verificar, chamar, por exemplo, ? - gulosaq6(21,23).
%--------------------------------- - - - - - - - - - -  -  -  -  -   -


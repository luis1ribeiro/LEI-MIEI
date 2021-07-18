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
%------------------------------ PREDICADOS DE INFORMAÇÃO

% Retorna o Id, as Carreiras e a Rua de cada paragem do ficheiro paragens.pl
porid(S) :- solucoes((Id,Carr,Rua),paragem(Id,_,_,_,_,_,_,Carr,_,Rua,_),S).

% Retorna os destinos dando uma paragem, para isto vai ao ficheiro "merged.pl" que contém todo o conhecimento sobre os nodos.
destdeX(X,S) :- solucoes((X,Y,Dist,Carr),nodo(X,Y,Dist,Carr),S).

% Verifica se existe nodo.
adjacentenodo(X,Y) :- nodo(X,Y,_,_).
adjacentenodo(X,Y) :- nodo(Y,X,_,_).

% Verifica se existe X(Origem) e Y(Destino) ligado pela mesma carreira, e retorna a C uma lista das interseções das carreiras do X e Y.
adjacenteCarr(X,Y,C) :- solucoes((X,Y,C),nodo(X,Y,_,C),S), length(S,N), N>0.

% Dado um X(Origem) e uma Carreira devolve a próxima paragem.
nxtparagem(X,C,H) :- solucoes(Y,nodo(X,Y,_,C),[H|_]).

% Devolve a distância associada a cada nodo, dado um X(Origem) e uma Carreira.
distnodo(X,C,H) :- solucoes(Dist,nodo(X,_,Dist,C),[H|_]).

% Devolve a distância associada a cada nodo, dado um X(Origem) e um Y(Destino).
distnodo2(X,Y,H) :- solucoes(Dist,nodo(X,Y,Dist,_),[H|_]).

% Predicado que retorna toda a informação de um nodo.
info_all(Nodo,ProxNodo,D,E):- 
    nodo(Nodo, ProxNodo,D,E).

% Predicado que verifica se existe nodo X a ProxNodo. O mesmo que adjacente.
info(Nodo,ProxNodo):- 
    nodo(Nodo,ProxNodo,_,_).

% Predicado do cálculo do estima, sendo esta, a distância euclidiana entre o Nodo Inicial e o Destino.
estima_dist(X,Y,R)  :- paragem(X,L1,D1,_,_,_,_,_,_,_,_), paragem(Y,L2,D2,_,_,_,_,_,_,_,_), calc(L1,D1,L2,D2,R).
calc(L1,D1,L2,D2,R) :- R is sqrt((L2-L1)^2 + (D2-D1)^2).

% Predicado auxiliar da pesquisa A*.    
inverso(Xs, Ys):-
    inverso(Xs, [], Ys).

% Predicado auxiliar da pesquisa A*.
inverso([], Xs, Xs).
inverso([X|Xs],Ys, Zs):-
    inverso(Xs, [X|Ys], Zs).

% Predicado auxiliar da pesquisa A*.
seleciona(E, [E|Xs], Xs).
seleciona(E, [X|Xs], [X|Ys]) :- seleciona(E, Xs, Ys).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -

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

%------------------------------ PESQUISA NÃO INFORMADA - PROFUNDIDADE EM PRIMEIRO (Depth First)


% -------------------- QUERIE 1 --------------------

resolveq1(X,Y):- findall((P,D,E), (resolve1(X,Y,P,D,E)), L), prntq1(L),             % -- Findall adptada RESOLUÇÃO FICHA 11. Imprime toda a informação numa lista.
                write("\n"),
                length(L,TAM), write("Resultados encontrados: "), write(TAM), 
                write(".\n") ,!.                      

resolveqq1(X,Y):- findall((P,D,E), (resolve1(X,Y,P,D,E)), [H|_]), prntq1([H]),!.

info(X, Y, Dist, Carr) :- nodo(X, Y, Dist, Carr).                                   % -- Predicado info, que retorna a informação obtida de cada nodo.
% info(X, Y, Dist, Carr) :- nodo(Y, X, Dist, Carr).

% Predicado que usa o write para facilitar a compreensao dos dados.
prntq1([]).
prntq1([(P,D,E)|T]) :-                                                              
    write("Caminho -> "), write(P), 
    write(" // Distância percorrida -> "), write(D),
    write(" // Carreiras -> "), write(E), 
    write(".\n"),
    prntq1(T).

% Função 'main' da pesquisa em profundidade primeiro (querie 1)
resolve1(X, Y, [X|Caminho], Dist, Carr) :-
    resolve1_aux(X, Y, Caminho, [], Dist, Carr).                                    % -- Declara o array de Visitados inicialmente como vazio. Este array vai ser preeenchido à medida que percorro o "grafo",
                                                                                    % -- para que não haja repetidos dentro da mesma pesquisa.

% Auxiliar da resolve1 para que haja recursividade, para ir de X a Y.
resolve1_aux(Y, Y, [], _, 0, []).                                                   % -- Caso de paragem, aqui o predicado, no final, dá 'Redo' e vai adicionando ao Caminho e aos Carreiras.
resolve1_aux(X, Y, [Prox|Caminho], Visitado, Dist, Carr) :-                         % -- Predicado que percorre os nodos e preenche o array dos Visitados.
    X \== Y,                                                                        % -- Obrigatório para não dar conflito com o 'Redo', pois ao depois de chegar ao destino, podia dar conflito ao preencher o array Caminhos etc.
    info(X, Prox, D, E),                                                            % -- Pedricado info, que retorna a informação obtida de cada nodo. Também vai buscar adjacente a X --> Prox.
    \+ memberchk(Prox,Visitado),
    resolve1_aux(Prox, Y, Caminho, [X | Visitado], Rest, Carr2), 
    append([E], Carr2, Carr),                                                       % -- Ir juntando as listas de Carreiras.    
    Dist is Rest + D.                                                               % -- Soma das Distâncias.


% -- Para verificar, chamar, por exemplo, ? - resolveq1(21,23).
% -- Para apresentar apenas um resultado, chamar por exempo, ? - resolveqq1(21,23). 
%--------------------------------- - - - - - - - - - -  -  -  -  -   -


% -------------------- QUERIE 2 --------------------

resolveq2(X,Y,Op):- findall((P,D,E,Oper), (resolve2(X,Y,Op,P,D,E,Oper)), L),        % -- Findall adptada RESOLUÇÃO FICHA 11. Imprime toda a informação numa lista. 
                    prntq2(L), write("\n"),
                    length(L,TAM), write("Resultados encontrados: "), write(TAM),
                    write(".\n"), !.
        
% Predicado que retorna apenas a "Head" do findall
resolveqq2(X,Y,Op) :- findall((P,D,E,Oper), (resolve2(X,Y,Op,P,D,E,Oper)), [H|_]), prntq2([H]), !. 

% Predicado que usa o write para facilitar a compreensao dos dados.
prntq2([]).
prntq2([(P,D,E,Op)|T]) :-                                                              
    write("Caminho -> "), write(P), 
    write(" // Distância percorrida -> "), write(D),
    write(" // Carreiras -> "), write(E),
    write(" // Operadoras -> "), write(Op), 
    write(".\n"),
    prntq2(T).

info_paragem(Id, A, O) :- paragem(Id,_,_,_,_,A,O,_,_,_,_).                          % -- Predicado info_paragem, que retorna a informação obtida de cada paragem.

% Predicado 'main' da querie 2
resolve2(X, Y, OperL, [X|Caminho], Dist, Carr, Oper) :-
    info_paragem(X,_,O),
    member(O,OperL),                      
    resolve2_aux(X, Y, OperL, Caminho, [], Dist, Carr, Oper).                       % -- Declara o array de Visitados inicialmente como vazio. Este array vai ser preeenchido à medida que percorro o "grafo",
                                                                                    % -- para que não haja repetidos dentro da mesma pesquisa.
% Auxiliar da resolve2 para que haja recursividade, para ir de X a Y.
resolve2_aux(Y, Y, _, [], _, 0, [], []).
resolve2_aux(X, Y, L, [Prox|Caminho], Visitado, Dist, Carr, Operadoras) :-
    X \== Y,                                                                        % -- Obrigatório para não dar conflito com o 'Redo', pois ao depois de chegar ao destino, podia dar conflito ao preencher o array Caminhos etc.
    info(X, Prox, D, E),                                                            % -- Chama o predicado info para obter informação sobre o próximo(Prox) do nodo.
    \+ memberchk(Prox, Visitado),                                                   % -- Verificar se o Prox pertence ao array de Visitados.
    info_paragem(Prox, _, O),                                                       % -- Chama o predicado info_paragem para obter informação sobre a Operadora da paragem Prox.
    member(O, L),
    resolve2_aux(Prox, Y, L, Caminho, [X|Visitado], Rest, Carr2, Operadoras2),
    append([O], Operadoras2, Operadoras),                                           % -- Ir juntando as listas de Operadoras.
    append([E], Carr2, Carr),                                                       % -- Ir juntando as listas de Carreiras.       
    Dist is Rest + D.                                                               % -- Soma das distâncias.



% -- Para verificar, chamar, por exemplo, ? - resolveq2(21,23,["Carris"]).
% -- Para apresentar apenas um resultado, chamar por exempo, ? - resolveqq2(21,23,["Carris"]).
%--------------------------------- - - - - - - - - - -  -  -  -  -   -


% -------------------- QUERIE 3 --------------------

resolveq3(X,Y,Op):- findall((P,D,E,Oper), (resolve3(X,Y,Op,P,D,E,Oper)), L),    % -- Findall adptada RESOLUÇÃO FICHA 11. Imprime toda a informação numa lista. 
                    prntq2(L), write("\n"),
                    length(L,TAM), write("Resultados encontrados: "), write(TAM),
                    write(".\n").

% Predicado que retorna apenas a "Head" do findall
resolveqq3(X,Y,Op) :- findall((P,D,E,Oper), (resolve3(X,Y,Op,P,D,E,Oper)), [H|_]), prntq2([H]), !.

% Predicado 'main' da querie 3
resolve3(X, Y, OperL, [X|Caminho], Dist, Carr, Oper) :-
    info_paragem(X,_,O),
    \+ member(O,OperL),                        
    resolve3_aux(X, Y, OperL, Caminho, [], Dist, Carr, Oper).                       % -- Declara o array de Visitados inicialmente como vazio. Este array vai ser preeenchido à medida que percorro o "grafo",
                                                                                    % -- para que não haja repetidos dentro da mesma pesquisa.
% Auxiliar da resolve3 para que haja recursividade, para ir de X a Y.
resolve3_aux(Y, Y, _, [], _, 0, [], []).
resolve3_aux(X, Y, L, [Prox|Caminho], Visitado, Dist, Carr, Operadoras) :-
    X \== Y,                                                                        % -- Obrigatório para não dar conflito com o 'Redo', pois ao depois de chegar ao destino, podia dar conflito ao preencher o array Caminhos etc.
    info(X, Prox, D, E),                                                            % -- Chama o predicado info para obter informação sobre o próximo(Prox) do nodo.
    \+ memberchk(Prox, Visitado),                                                   % -- Verificar se o Prox pertence ao array de Visitados.
    info_paragem(Prox, _, O),                                                       % -- Chama o predicado info_paragem para obter informação sobre a Operadora da paragem Prox.
    \+ member(O, L),
    resolve3_aux(Prox, Y, L, Caminho, [X|Visitado], Rest, Carr2, Operadoras2),      
    append([O], Operadoras2, Operadoras),                                           % -- Ir juntando as listas de Operadoras.
    append([E], Carr2, Carr),                                                       % -- Ir juntando as listas de Carreiras.       
    Dist is Rest + D.                                                               % -- Soma das distâncias.


% -- Para verificar, chamar, por exemplo, ? - resolveq3(21,23,["Vimeca"]).
% -- Para apresentar apenas um resultado, chamar por exempo, ? - resolveqq3(21,23,["Vimeca"]).
%--------------------------------- - - - - - - - - - -  -  -  -  -   -


% -------------------- QUERIE 4 --------------------

% Função que para uma lista de lista, dado pelo totalq4, percorre cada elemento dessa lista, que é uma lista.
aplicar1([],[]).
aplicar1(H,[(T1,TT)]) :- max_l(H,T1), corres(H,T1,TT).
aplicar1([H|T],[(T1,TT)|S]) :- max_l(H,T1), corres(H,T1,TT), aplicar1(T,S).

% Predicado prntlst mas para a querie 4.
prntlst4([]).
prntlst4([(X,X1)|T]) :- write("Maior número de Carreiras -> "), write(X), 
    write(" // Lista de paragens associada -> "), write(X1), 
    write(".\n"), 
    prntlst4(T). 

resolveq4(X,Y):- findall(Carr, (resolve4(X,Y,_,_,_,Carr)), L),                      % -- Findall adptada RESOLUÇÃO FICHA 11. Imprime toda a informação numa lista.
    aplicar1(L,TT),
    prntlst4(TT), write("\n"),
    length(TT,TAM), write("Resultados encontrados: "), write(TAM),
    write(".\n"), !.

% Predicado que retorna apenas a "Head" do findall
resolveqq4(X,Y) :- resolve4(X,Y,_,_,_,Carr),
                aplicar1(Carr,TT),
                prntlst4(TT), !.                                                           

info_paragem2(Id, (Id,Nr)) :- paragem(Id,_,_,_,_,_,_,L,_,_,_), length(L, Nr).       % -- Predicado info_paragem2, que retorna um par (Id, Nr) onde este Nr é o numero de Carreiras que passa nessa paragem.

% Predicado 'main' da querie 4.
resolve4(X, Y, [X|Caminho], Dist, Carr, NrCarr) :-                        
    resolve4_aux(X, Y, Caminho, [], Dist, Carr, NrCarr).                            % -- Declara o array de Visitados inicialmente como vazio. Este array vai ser preeenchido à medida que percorro o "grafo",
                                                                                    % -- para que não haja repetidos dentro da mesma pesquisa.
% Auxiliar da resolve4 para que haja recursividade, para ir de X a Y.
resolve4_aux(Y, Y, [], _, 0, [], [H]) :- info_paragem2(Y,H).
resolve4_aux(X, Y, [Prox|Caminho], Visitado, Dist, Carr, NrCarr) :-
    X \== Y,                                                                        % -- Obrigatório para não dar conflito com o 'Redo', pois ao depois de chegar ao destino, podia dar conflito ao preencher o array Caminhos etc.
    info(X, Prox, D, E),                                                            % -- Chama o predicado info para obter informação sobre o próximo(Prox) do nodo.
    \+ memberchk(Prox, Visitado),                                                   % -- Verificar se o Prox pertence ao array de Visitados.
    info_paragem(Prox, _, _),                                                       % -- Chama o predicado info_paragem para obter informação sobre a Operadora da paragem Prox.
    info_paragem2(X, Nr),
    resolve4_aux(Prox, Y, Caminho, [X|Visitado], Rest, Carr2, NrCarr2),
    append([Nr], NrCarr2, NrCarr),                                                  % -- Ir juntando as listas de Operadoras.
    append([E], Carr2, Carr),                                                       % -- Ir juntando as listas de Carreiras.      
    Dist is Rest + D.                                                               % -- Soma das distâncias.



% -- Para verificar, chamar, por exemplo, ? - resolveq4(21,23).
% -- Para apresentar apenas um resultado, chamar por exempo, ? - resolveqq4(21,23).
%--------------------------------- - - - - - - - - - -  -  -  -  -   -


% -------------------- QUERIE 5 --------------------

% Predicado min lista, de modo a obter o minimo do tamanho presente na lista de Carreiras.
min_q5([((X,_),_,_)],X) :- !, true.
min_q5([((X,_),_,_)|Xs], M):- min_q5(Xs, M), M =< X.
min_q5([((X,_),_,_)|Xs], X):- min_q5(Xs, M), X <  M.

% Predicado correspondencia da querie 5, para filtrar os caminhos com o menor número de paragens.
corres_q5([],_,[]).
corres_q5([((X,Y),D,E)],X,[((X,Y),D,E)]). 
corres_q5([((X,Y),D,E)|Xs],Xt,[((X,Y),D,E)|T]) :- X == Xt, corres_q5(Xs,Xt,T).
corres_q5([((X,_),_,_)|Xs],Xt,T) :- X \== Xt, corres_q5(Xs,Xt,T).

% Predicado que apresenta para cada trio Caminho(Lista), Distancia e Carreiras, retorna o mesmo e, adicionalmente, o tamanho da Lista Carreira.
tamlist([],[]).
tamlist([(H,D,E)],[((X,H),D,E)]) :- length(H,X).
tamlist([(H,D,E)|T],[((X,H),D,E)|S]) :- length(H,X), tamlist(T,S).

% Predicado que usa o write para facilitar a compreensao dos dados.
prntq5([]).
prntq5([((M,P),D,E)|T]) :- 
    write("Menor número de paragens -> "), write(M),                                                             
    write(" // Caminho -> "), write(P), 
    write(" // Distância percorrida -> "), write(D),
    write(" // Carreiras -> "), write(E),
    write(".\n"),
    prntq5(T).

% Predicado 'main' desta querie.
resolveq5(X,Y) :- findall((P,D,E), (resolve1(X,Y,P,D,E)), L),
    tamlist(L,TT),                                                                  % -- Chama a função tamlist para obter a lenght de cada lista de paragens de X a Y.
    min_q5(TT,M),                                                                   % -- Dessas lengths descobre qual o menor.
    corres_q5(TT,M,FI),                                                             % -- Faz a correspondencia do menor à lista inicial, para ver qual dá match.
    prntq5(FI),                                                                     % -- Dou print às listas que contem o menor percurso.    
    write("\n"), length(FI,TAM),
    write("Resultados encontrados: "), write(TAM),
    write(".\n"), !.


% -- Para verificar, chamar, por exemplo, ? - resolveq5(460,486).
%--------------------------------- - - - - - - - - - -  -  -  -  -   -


% -------------------- QUERIE 6 --------------------

% Predicado min_dist, de modo a obter o minimo da Distância na lista de lista dada pelo findall.
min_dist_q6([(_,D,_)],D) :- !, true.
min_dist_q6([(_,D,_)|Xs], M):- min_dist_q6(Xs, M), M =< D.
min_dist_q6([(_,D,_)|Xs], D):- min_dist_q6(Xs, M), D <  M.

% Predicado de correspondencia, ajustado da querie 6, que filtra todos os elementos se tiver o minimo Distancia obtido em cima.
corres_q6([],_,[]).
corres_q6([(X,D,E)],D,[(X,D,E)]). 
corres_q6([(X,D,E)|Xs],Dt,[(X,D,E)|T]) :- D == Dt, corres_q6(Xs,Dt,T).
corres_q6([(X,_,_)|Xs],Dt,T) :- X \== Dt, corres_q6(Xs,Dt,T).

% Predicado que usa o write para facilitar a compreensao dos dados.
prntq6([]).
prntq6([(P,D,E)|T]) :-                                                              
    write("Caminho -> "), write(P), 
    write(" // Distância mínima percorrida -> "), write(D),
    write(" // Carreiras -> "), write(E), 
    write(".\n"),
    prntq6(T).

% Predicado 'main' desta querie. Esta chama a min_dist e a corres_q6, de modo a obter apenas os resultados com a menor distância do caminho de X a Y.
resolveq6(X,Y) :- findall((P,D,E), (resolve1(X,Y,P,D,E)), L),
    min_dist_q6(L,M),
    corres_q6(L,M,FI),
    prntq6(FI),
    write("\n"), length(FI,TAM),
    write("Resultados encontrados: "), write(TAM),
    write(".\n"), !.


% -- Para verificar, chamar, por exemplo, ? - resolveq6(460,486).
%--------------------------------- - - - - - - - - - -  -  -  -  -   -


% -------------------- QUERIE 7 --------------------

resolveq7(X,Y):- findall((P,D,E), (resolve7(X,Y,P,D,E,_)), L), prntq7(L),           % -- Findall adptada RESOLUÇÃO FICHA 11. Imprime toda a informação numa lista. 
                write("\n"),
                length(L,TAM), write("Resultados encontrados: "), write(TAM), 
                write(".\n") ,!.            

% Predicado que retorna apenas a "Head" do findall
resolveqq7(X,Y):- findall((P,D,E), (resolve7(X,Y,P,D,E,_)), [H|_]), prntq7([H]), !.

% Predicado que usa o write para facilitar a compreensao dos dados.
prntq7([]).
prntq7([(P,D,E)|T]) :-                                                              
    write("(Caminho,Abrigo) -> "), write(P), 
    write(" // Distância percorrida -> "), write(D),
    write(" // Carreiras -> "), write(E), 
    write(".\n"),
    prntq7(T).

% Predicado 'main' da querie 7
resolve7(X, Y, [(X,A)|Caminho], Dist, Carr, Oper) :- 
    info_paragem(X,A,_),
    A == "Yes",                       
    resolve7_aux(X, Y, Caminho, [], Dist, Carr, Oper).                              % -- Declara o array de Visitados inicialmente como vazio. Este array vai ser preeenchido à medida que percorro o "grafo",
                                                                                    % -- para que não haja repetidos dentro da mesma pesquisa.
% Predicado auxiliar da resolve7, para que haja recursividade relativamente ao Caminho.
resolve7_aux(Y, Y, [], _, 0, [], []).
resolve7_aux(X, Y, [(Prox,A)|Caminho], Visitado, Dist, Carr, Operadoras) :-
    X \== Y,                                                                        % -- Obrigatório para não dar conflito com o 'Redo', pois ao depois de chegar ao destino, podia dar conflito ao preencher o array Caminhos etc.
    info(X, Prox, D, E),                                                            % -- Chama o predicado info para obter informação sobre o próximo(Prox) do nodo.
    \+ memberchk(Prox, Visitado),                                                   % -- Verificar se o Prox pertence ao array de Visitados.
    info_paragem(Prox, A, O),                                                       % -- Chama o predicado info_paragem para obter informação sobre a Operadora da paragem Prox.
    A == "Yes",                                                                     % -- Filtra apenas as paragens com abrigo.
    resolve7_aux(Prox, Y, Caminho, [X|Visitado], Rest, Carr2, Operadoras2),
    append([O], Operadoras2, Operadoras),                                           % -- Ir juntando as listas de Operadoras.
    append([E], Carr2, Carr),                                                       % -- Ir juntando as listas de Carreiras.      
    Dist is Rest + D.                                                               % -- Soma das distâncias.



% -- Para verificar, chamar, por exemplo, ? - resolveq7(460,486).
% -- Para apresentar apenas um resultado, chamar por exempo, ? - resolveqq7(460,486).
%--------------------------------- - - - - - - - - - -  -  -  -  -   -


% -------------------- QUERIE 8 --------------------

resolveq8(X,Y):- findall((P,D,E), (resolve8(X,Y,P,D,E,_)), L), prntq8(L),           % -- Findall adptada RESOLUÇÃO FICHA 11. Imprime toda a informação numa lista. 
                write("\n"),
                length(L,TAM), write("Resultados encontrados: "), write(TAM), 
                write(".\n") ,!. 

% Predicado que retorna apenas a "Head" do findall
resolveqq8(X,Y):- findall((P,D,E), (resolve8(X,Y,P,D,E,_)), [H|_]), prntq8([H]), !. 

% Predicado que usa o write para facilitar a compreensao dos dados.
prntq8([]).
prntq8([(P,D,E)|T]) :-                                                              
    write("(Caminho,Tipo Abrigo) -> "), write(P), 
    write(" // Distância percorrida -> "), write(D),
    write(" // Carreiras -> "), write(E), 
    write(".\n"),
    prntq8(T). 

% Predicado relativo à informação de abrigo da paragem.
info_paragem_abrigo(Id, TA, A, O) :- paragem(Id,_,_,_,TA,A,O,_,_,_,_). 

% Predicado 'main' da querie 8
resolve8(X, Y, [(X,TA)|Caminho], Dist, Carr, Oper) :- 
    info_paragem_abrigo(X,TA,_,_),
    member(TA,["Aberto dos Lados", "Fechado dos Lados"]),                      
    resolve8_aux(X, Y, Caminho, [], Dist, Carr, Oper).                              % -- Declara o array de Visitados inicialmente como vazio. Este array vai ser preeenchido à medida que percorro o "grafo",
                                                                                    % -- para que não haja repetidos dentro da mesma pesquisa.
% Predicado auxiliar da resolve8, para que haja recursividade relativamente ao Caminho.
resolve8_aux(Y, Y, [], _, 0, [], []).
resolve8_aux(X, Y, [(Prox,TA)|Caminho], Visitado, Dist, Carr, Operadoras) :-
    X \== Y,                                                                        % -- Obrigatório para não dar conflito com o 'Redo', pois ao depois de chegar ao destino, podia dar conflito ao preencher o array Caminhos etc.
    info(X, Prox, D, E),                                                            % -- Chama o predicado info para obter informação sobre o próximo(Prox) do nodo.
    \+ memberchk(Prox, Visitado),                                                   % -- Verificar se o Prox pertence ao array de Visitados.
    info_paragem_abrigo(Prox, TA, _, O),                                            % -- Chama o predicado info_paragem_paragem.
    member(TA,["Aberto dos Lados", "Fechado dos Lados"]),                           % -- Verifica se é Aberto dos Lados ou Fechado dos Lados
    resolve8_aux(Prox, Y, Caminho, [X|Visitado], Rest, Carr2, Operadoras2),      
    append([O], Operadoras2, Operadoras),                                           % -- Ir juntando as listas de Operadoras.
    append([E], Carr2, Carr),                                                       % -- Ir juntando as listas de Carreiras.      
    Dist is Rest + D.                                                               % -- Soma das distâncias.


% -- Para verificar, chamar, por exemplo, ? - resolveq8(460,486).
% -- Para apresentar apenas um resultado, chamar por exempo, ? - resolveqq8(460,486).
%--------------------------------- - - - - - - - - - -  -  -  -  -   -


% -------------------- QUERIE 9 --------------------

% Predicado de filter, ajustado da querie 9, que testa os nodos em que passa, se passa ou não.
filterq9(_,[],[]).
filterq9(L,[(P,_,_)|T],FI) :- intersection(L,P,R),  L \== R, filterq9(L,T,FI).             
filterq9(L,[(P,D,E)|T],[(P,D,E)|FI]) :- intersection(L,P,R),  L == R, filterq9(L,T,FI).     % -- Verifico a interseção da lista de paragens que dou, com a total, e se o resultado da interseção
                                                                                            % -- for igual ao input dado, incluo esse trio na final.


% Predicado 'main' desta querie. Esta chama a filterq9, de modo a obter apenas os resultados que passem pelas paragens definidas em SL.
resolveq9(X,Y,SL) :- findall((P,D,E), (resolve1(X,Y,P,D,E)), L),
    filterq9(SL,L,FI),
    prntq1(FI),
    write("\n"),
    length(FI,TAM), write("Resultados encontrados: "), write(TAM), 
    write(".\n") ,!. 


% Predicado que retorna apenas a "Head" do findall.
resolveqq9(X,Y,SL) :- findall((P,D,E), (resolve1(X,Y,P,D,E)), L),
    filterq9(SL,L,[H|_]),
    prntq1([H]), !.


% -- Para verificar, chamar, por exemplo, ? - resolveq9(460,486,[485]).
% -- Para apresentar apenas um resultado, chamar por exempo, ? - resolveqq9(460,486,[485]).
%--------------------------------- - - - - - - - - - -  -  -  -  -   -



%--------------------------------- - - - - - - - - - -  -  -  -  -   -
%------------------------------ TRABALHO - PESQUISA INFORMADA - PESQUISA A*


% -------------------- QUERIE 1 -------------------- 

% Predicado 'main' desta querie, que retorna um trajeto entre dois pontos usando o algoritmo da pesquisa A*.
infoq1(Nodo,Destino) :- resolve_info(Nodo,Destino,C), prnti1(C), !.

% Predicado que usa o write para facilitar a compreensao dos dados.
prnti1(H1/H2) :- 
    ignorai6(H1,R),                                                                 % -- Retiro o Destino do par (Nodo, Destino).
    write("Caminho -> "), write(R), 
    write("  Com Distância -> "), write(H2), 
    write(".\n").

% Todos os predicados que se seguem são relativos à implementação ajustada da pesquisa A* obtida durante as aulas desta UC. Ajustada no sentido que em vez de um goal, tenho um Destino
% que é passado sempre como argumento, isto é o nodo em que estou e o Destino são sempre passados como argumento num par (Nodo,Destino). Quando o nodo for igual ao destino então cheguei ao fim.
adjacente([(Nodo,Destino)|Caminho]/Custo/_, [(ProxNodo,Destino),(Nodo,Destino)|Caminho]/NovoCusto/Est) :-
    nodo(Nodo, ProxNodo, PassoCusto,_),\+ member(ProxNodo, Caminho),
    NovoCusto is Custo + PassoCusto,
    estima_dist(ProxNodo, Destino, Est).    

resolve_info(Nodo,Destino,Caminho/Custo)    :- estima_dist(Nodo,Destino,Estima),
                                               ainfo([[(Nodo,Destino)]/0/Estima],InvCaminho/Custo/_),
                                               inverso(InvCaminho,Caminho).

ainfo(Caminhos,Caminho):- obtem_melhor_g(Caminhos,Caminho),
                            Caminho = [(Nodo,Destino)|_]/_/_,
                            Nodo == Destino.
ainfo(Caminhos,SolucaoCaminho):-    obtem_melhor_g(Caminhos,MelhorCaminho),
                                    seleciona(MelhorCaminho,Caminhos,OutrosCaminhos),
                                    expande_info(MelhorCaminho,ExpCaminhos),
                                    append(OutrosCaminhos,ExpCaminhos,NovoCaminhos),
                                    ainfo(NovoCaminhos,SolucaoCaminho).


obtem_melhor_g([Caminho],Caminho):- !.
obtem_melhor_g([Caminho1/Custo1/Est1,_/Custo2/Est2|Caminhos],MelhorCaminho):- Custo1 + Est1 =< Custo2 + Est2,
                                                                              !,
                                                                              obtem_melhor_g([Caminho1/Custo1/Est1|Caminhos],MelhorCaminho).                                                                            
obtem_melhor_g([_|Caminhos],MelhorCaminho):- obtem_melhor_g(Caminhos,MelhorCaminho).

expande_info(Caminho,ExpCaminhos):- findall(NovoCaminho,adjacente(Caminho,NovoCaminho),ExpCaminhos).


% -- Para verificar, chamar, por exemplo, ? - infoq1(21,23).
%--------------------------------- - - - - - - - - - -  -  -  -  -   -


% -------------------- QUERIE 5 --------------------

% Predicado 'main' desta querie, que retorna o menor trajeto entre dois pontos usando o critério do número de paragens. Para isto, uso a pesquisa A*.
infoq5(Nodo,Destino) :- resolve_infoq5(Nodo,Destino,C), prnti5(C), !.

% Predicado que usa o write para facilitar a compreensao dos dados.
prnti5(H1/H2) :- 
    ignorai6(H1,R),                                                                 % -- Retiro o Destino do par (Nodo, Destino). 
    write("Caminho com menos paragens -> "), write(R), 
    length(R,TAM), write("  Com tamanho -> "), write(TAM),                          % -- Número de paragens relativas ao menor número destas. 
    write("  Com Distância -> "), write(H2), 
    write(".\n").

% Todos os predicados que se seguem são relativos à implementação ajustada da pesquisa A* obtida durante as aulas desta UC. Ajustada no sentido que em vez de um goal, tenho um Destino
% que é passado sempre como argumento, isto é o nodo em que estou e o Destino são sempre passados como argumento num par (Nodo,Destino). Quando o nodo for igual ao destino então cheguei ao fim.
resolve_infoq5(Nodo,Destino,Caminho/Custo) :- estima_dist(Nodo,Destino,Estima),
                                              ainfoq5([[(Nodo,Destino)]/0/Estima],InvCaminho/Custo/_),
                                              inverso(InvCaminho,Caminho).

ainfoq5(Caminhos,Caminho):-     obtem_melhor_g5(Caminhos,Caminho),
                                Caminho = [(Nodo,Destino)|_]/_/_,
                                Nodo == Destino.
ainfoq5(Caminhos,SolucaoCaminho):-  obtem_melhor_g5(Caminhos,MelhorCaminho),
                                        seleciona(MelhorCaminho,Caminhos,OutrosCaminhos),
                                        expande_info(MelhorCaminho,ExpCaminhos),
                                        append(OutrosCaminhos,ExpCaminhos,NovoCaminhos),
                                        ainfoq5(NovoCaminhos,SolucaoCaminho).


obtem_melhor_g5([Caminho],Caminho):- !.
obtem_melhor_g5([Caminho1/Custo1/Est1,Cam2/_/_|Caminhos],MelhorCaminho):- length(Caminho1,X),
                                                                          length(Cam2,Y),
                                                                          X =< Y,
                                                                          !,
                                                                          obtem_melhor_g5([Caminho1/Custo1/Est1|Caminhos],MelhorCaminho).
obtem_melhor_g5([_|Caminhos],MelhorCaminho):- obtem_melhor_g5(Caminhos,MelhorCaminho).


% -- Para verificar, chamar, por exemplo, ? - infoq5(21,23).
%--------------------------------- - - - - - - - - - -  -  -  -  -   -


% -------------------- QUERIE 6 --------------------

% Predicado que usa o write para facilitar a compreensao dos dados.
prnti6(H/H1) :- 
    ignorai6(H,R),                                                              % -- Retiro o Destino do par (Nodo, Destino). 
    write("Caminho -> "), write(R), 
    write("  Distância mínima -> "), write(H1), 
    write(".\n").

% Predicado que dado uma lista de pares, ignora o 2º elemento do par. Neste caso é nos útil por causa do nosso caminho ter sempre o par (NodoAtual,Destino).
ignorai6([],[]).
ignorai6([(H,_)|T],[H|TT]) :- ignorai6(T,TT).

% Predicado 'main' desta querie, que retorna o menor trajeto entre dois pontos usando o critério da distância. Para isto, uso a pesquisa A*.
infoq6(Nodo,Destino) :- resolve_info(Nodo,Destino,C), prnti6(C), !.


% -- Para verificar, chamar, por exemplo, ? - infoq6(21,23).
%--------------------------------- - - - - - - - - - -  -  -  -  -   -

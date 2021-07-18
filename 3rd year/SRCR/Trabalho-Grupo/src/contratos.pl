/**/
%-------------------------------- - - - - - - - - - -  -  -  -  -   -
% SIST. REPR. CONHECIMENTO E RACIOCINIO - MiEI
%
% Grupo 15
%
% André Morais
% Luís Ribeiro
% José Pedro Silva
% Pedro Rodrigues

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Programacao em logica estendida

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% SICStus PROLOG: Declaracoes iniciais

:- set_prolog_flag( discontiguous_warnings,off ).
:- set_prolog_flag( single_var_warnings,off ).
:- set_prolog_flag( unknown,fail ).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% SICStus PROLOG: definicoes iniciais

:- op( 900,xfy,'::' ). % definição de um invariante
:- dynamic adjudicante/4. % (#idAd, Nome, NIF, Morada)
:- dynamic adjudicataria/4. % (#idAda, Nome, NIF, Morada)
:- dynamic contrato/10. % (#idCont, #idAd, #idAda, TipoDeContrato, TipoDeProcedimento, Descrição, Custo, Prazo, Local, Data)
:- dynamic data/3. % (dia, mes, ano)


%-------------------------------------------------------------------------------------------------
% Extensao do meta-predicado demo: Questao,Resposta -> {V, F, D}

demo( Questao,verdadeiro ) :- Questao.
demo( Questao, falso ) :- -Questao.
demo( Questao,desconhecido ) :-
    nao( Questao ),
    nao( -Questao ).


%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do meta-predicado nao: Questao -> {V,F}

nao( Questao ) :-
    Questao, !, fail.
nao( Questao ).

% Predicado comprimento:
% Lista, Comprimento -> {V,F}
comprimento(S, N) :- length(S, N).

sum([], 0).
sum([H|T], N):-
    sum(T, X),
    N is X + H.

% Predicado solucoes:
% Termo, Predicado, Lista -> {V,F}
solucoes(T,Q,S) :- findall(T,Q,S).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensão do predicado que permite a evolucao do conhecimento

evolucao(T) :- solucoes(Inv, +T::Inv, S),
               insercao(T),
               teste(S).

insercao(T) :- assert(T).
insercao(T) :- retract(T),!,fail. % !,fail == insucesso na prova

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensão do predicado que permite a involucao do conhecimento

involucao(T) :- T,
                solucoes(Inv, -T::Inv, S),
                remocao(T),
                teste(S).

remocao(T) :- retract(T).
remocao(T) :- assert(T),!,fail. % !,fail == insucesso na prova


%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensão do predicado teste : Lista -> {V, F}

teste([]).
teste([H|T]) :- H, teste(T).


%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Invariante insercao de adjudicante : - id e nif devem ser inteiros e unicos

+adjudicante(Id, _, NIF, _) :: (
                            integer(Id),
                            integer(NIF),
                            Id >= 0,
                            NIF > 0,
                            solucoes(Id, adjudicante(Id, _, _, _), S),
                            comprimento(S, N), N == 1,
                            solucoes(NIF, adjudicante(_, _, NIF, _), NS),
                            comprimento(NS, C), C == 1
                          ).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Invariante remocao de adjudicante : - nao pode ter contratos associado

-adjudicante(IdAd, _, _ , _) :: (
                           solucoes(IdAd, contrato(_ , IdAd, _, _, _, _, _, _ ,_ , _),S),
                           comprimento(S, N), N == 0
                         ).


%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Invariante insercao de adjudicataria : - id e nif devem ser inteiros e unicos

+adjudicataria(Id, _, NIF, _) :: (
                            integer(Id),
                            integer(NIF),
                            Id >= 0,
                            NIF > 0,
                            solucoes(Id, adjudicataria(Id, _, _, _), S),
                            comprimento(S, N), N == 1,
                            solucoes(NIF, adjudicataria(_, _, NIF, _), NS),
                            comprimento(NS, C), C == 1
                          ).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Invariante remocao de adjudicataria : - nao pode ter contratos associado

-adjudicataria(IdAda, _, _ , _) :: (
                           solucoes(IdAda, contrato(_ , _, IdAda, _, _, _, _, _ ,_ , _),S),
                           comprimento(S, N), N == 0
                         ).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Invariante insercao de contrato : - id's devem ser naturais e unicos e deve respeitar as restantes regras mencionadas

+contrato(IdC, IdAd, IdAda, TC, TP, _, C, P, _, Data) :: (
                          integer(IdC), IdC >= 0,
                          integer(IdAd), IdAd >= 0,
                          integer(IdAda), IdAda >= 0,
                          solucoes(IdC,contrato(IdC,_,_,_,_,_,_,_,_,_),LC),
                          comprimento(LC, NC), NC == 1,
                          solucoes(IdAd,adjudicante(IdAd,_,_,_),LAd),
                          comprimento(LAd, NAd), NAd == 1,
                          solucoes(IdAda,adjudicataria(IdAda,_,_,_),LAda),
                          comprimento(LAda, NAda), NAda == 1,
                          verificaTipo(TP,TC,P,C),
                          verificaData(Data),
                          ano(Data,A),
                          regraAnos(IdAd,IdAda,TC,C,A)
                        ).

% Restrição dos tipos de contrato e regras acerca do ajuste direto
verificaTipo("Consulta Prévia",_,_,_).
verificaTipo("Concurso Público",_,_,_).
verificaTipo("Ajuste Direto","Contrato de Aquisição",P,C) :- P =< 365, C =< 5000.
verificaTipo("Ajuste Direto","Locação de Bens Móveis",P,C) :- P =< 365, C =< 5000.
verificaTipo("Ajuste Direto","Aquisição de Serviços",P,C) :- P =< 365, C =< 5000.


% Restrição acerca do numero de contratos entre as mesmas entidades
regraAnos(IdAd, IdAda, TC, C, A) :-
                              solucoes(V,(contrato(_,IdAd,IdAda,TC,_,_,V,_,_,data(_,_,AS)),A-AS < 3),S),
                              sum(S,N), nao(N-C >= 75000).

% Ano de uma data
ano(data(D,M,A),A).

% Valida uma determinada data
verificaData(data(D,1,A)) :- D>0,D=<31,A>0.
verificaData(data(D,2,A)) :- D>0,A mod 4 =:= 0,D=<29,A>0.
verificaData(data(D,2,A)) :- D>0,A mod 4 =\= 0, D=<28,A>0.
verificaData(data(D,3,A)) :- D>0,D=<31,A>0.
verificaData(data(D,4,A)) :- D>0,D=<30,A>0.
verificaData(data(D,5,A)) :- D>0,D=<31,A>0.
verificaData(data(D,6,A)) :- D>0,D=<30,A>0.
verificaData(data(D,7,A)) :- D>0,D=<31,A>0.
verificaData(data(D,8,A)) :- D>0,D=<31,A>0.
verificaData(data(D,9,A)) :- D>0,D=<30,A>0.
verificaData(data(D,10,A)) :- D>0,D=<31,A>0.
verificaData(data(D,11,A)) :- D>0,D=<30,A>0.
verificaData(data(D,12,A)) :- D>0,D=<31,A>0.


%------------------------------------------------------------------------------------------------------------------------------------
%               Adição de conhecimento perfeito

registaAdjudicante(Id,Nome,NIF,Morada) :- evolucao(adjudicante(Id, Nome, NIF, Morada)).
registaAdjudicataria(Id, Nome, NIF, Morada) :- evolucao(adjudicataria(Id, Nome, NIF, Morada)).
realizaContrato(Id,IdAd,IdAda,TC,TP,D,C,P,L,Data) :- evolucao(contrato(Id,IdAd,IdAda,TC,TP,D,C,P,L,Data)).


%--------------------------------------------------------------------------------------------------------------------------------------
%
% Conhecimento negativo

-adjudicante(Id,Nome,NIF,Morada) :-
                    nao(adjudicante(Id,Nome,NIF,Morada)),
                    nao(excecao(adjudicante(Id,Nome,NIF,Morada))).

-adjudicataria(Id,Nome,NIF,Morada) :-
                    nao(adjudicataria(Id,Nome,NIF,Morada)),
                    nao(excecao(adjudicataria(Id,Nome,NIF,Morada))).

-contrato(Id,IdAd,IdAda,TC,TP,D,C,P,L,Data):-
                    nao(contrato(Id,IdAd,IdAda,TC,TP,D,C,P,L,Data)),
                    nao(excecao(contrato(Id,IdAd,IdAda,TC,TP,D,C,P,L,Data))).


% ------------------------------------------------------------------------------------------------------------------------------------
%                                                     """""""""""""""""""""""""""""""""""""""
%                                                     "       Conhecimento Imperfeito       "
%                                                     """""""""""""""""""""""""""""""""""""""
%
% Incerto
%
% Adjudicante --------------------------------------------------------------------------------------------------------------------------

% Nome desconhecido
excecao(adjudicante(Id,Nome,NIF,Morada)) :- adjudicante(Id,nomeDesconhecido,NIF,Morada).

% Morada desconhecido
excecao(adjudicante(Id,Nome,NIF,Morada)) :- adjudicante(Id,Nome,NIF,moradaDesconhecida).

% Nome e Morada desconhecidos
excecao(adjudicante(Id,Nome,NIF,Morada)) :- adjudicante(Id,nomeDesconhecido,NIF,moradaDesconhecida).


% Adjudicataria --------------------------------------------------------------------------------------------------------------------------

% Nome desconhecido
excecao(adjudicataria(Id,Nome,NIF,Morada)) :- adjudicataria(Id,nomeDesconhecido,NIF,Morada).

% Morada desconhecido
excecao(adjudicataria(Id,Nome,NIF,Morada)) :- adjudicataria(Id,Nome,NIF,moradaDesconhecida).

% Nome e Morada desconhecidos
excecao(adjudicataria(Id,Nome,NIF,Morada)) :- adjudicataria(Id,nomeDesconhecido,NIF,moradaDesconhecida).


% Contrato ------------------------------------------------------------------------------------------------------------------------------

% Valor desconhecido
excecao(contrato(Id,IdAd,IdAda,TC,TP,Desc,V,P,L,D)) :- contrato(Id,IdAd,IdAda,TC,TP,Desc,valorDesconhecido,P,L,D).

% Prazo desconhecido
excecao(contrato(Id,IdAd,IdAda,TC,TP,Desc,V,P,L,D)) :- contrato(Id,IdAd,IdAda,TC,TP,Desc,V,prazoDesconhecido,L,D).

% Data desconhecida
excecao(contrato(Id,IdAd,IdAda,TC,TP,Desc,V,P,L,D)) :- contrato(Id,IdAd,IdAda,TC,TP,Desc,V,P,L,dataDesconhecida).

% Local Desconhecida
excecao(contrato(Id,IdAd,IdAda,TC,TP,Desc,V,P,L,D)) :- contrato(Id,IdAd,IdAda,TC,TP,Desc,V,P,localDesconhecido,D).

% Data e local desconhecido
excecao(contrato(Id,IdAd,IdAda,TC,TP,Desc,V,P,L,D)) :- contrato(Id,IdAd,IdAda,TC,TP,Desc,V,P,localDesconhecido,dataDesconhecida).

% Prazo e valor desconhecido
excecao(contrato(Id,IdAd,IdAda,TC,TP,Desc,V,P,L,D)) :- contrato(Id,IdAd,IdAda,TC,TP,Desc,valorDesconhecido,prazoDesconhecido,L,D).

% Descrição desconhecida
excecao(contrato(Id,IdAd,IdAda,TC,TP,Desc,V,P,L,D)) :- contrato(Id,IdAd,IdAda,TC,TP,DescriçãoDesconhecida,V,P,L,D).

%-----------------------------------------------------------------------------------------------------------------------------------
% Registo de conhecimento incerto

% Contrato realizado, no entanto ainda não se sabe o valor do mesmo.
contrato(6,3,2,"Contrato de Aquisição","Concurso Público","Descrição",valorDesconhecido,365,"Braga",data(17,07,2020)).

% Contrato realizado, no entanto não se sabe a data em que foi realizado.
contrato(7,3,2,"Aquisição de Serviços","Ajuste Direto","Descrição",20,10,"Viseu",dataDesconhecida).


%-----------------------------------------------------------------------------------------------------------------------------------
% Impreciso

% Contrato realizado onde o prazo pode variar entre 20 a 30 dias.
excecao(contrato(9,1,3,"Aquisição de Serviços","Ajuste Direto","Descrição",40,P,"Barcelos",data(9,10,2020))) :- P>=20, P=<30.

% Contrato realizado onde não se sabe se o valor é 7000 ou 70000.
excecao(contrato(10,2,1,"Locação de Bens Móveis","Concurso Público","Descrição",7000,365,"Lisboa",data(12,12,2020))).
excecao(contrato(10,2,1,"Locação de Bens Móveis","COncurso Público","Descrição",70000,365,"Lisboa",data(12,12,2020))).

% Contrato realizado, mas a única coisa que se sabe em relação ao local é que não foi em Braga
contrato(8,2,3,"Locação de Bens Móveis","Consulta Prévia","Descrição",400,20,localDesconhecido,data(15,08,2021)).
-contrato(8,2,3,"Locação de Bens Móveis","Consulta Prévia","Descrição",400,20,"Braga",data(15,08,2021)).

%------------------------------------------------------------------------------------------------------------------------------------
% Interdito

% Ninguém pode saber a morada do adjudicante 4

adjudicante(4,"João Silva",775873,moradaInterdita).
excecao(adjudicante(Id,N,NIF,M)) :- adjudicante(Id,N,NIF,moradaInterdita).
nulo(moradaInterdita).
+adjudicante(Id,NA,NIF,Morada) :: (
                              solucoes((Id,NA,NIF,Morada),(adjudicante(4,"João Silva",775873,M),nao(nulo(M))),S),
                              comprimento(S,N),
                              N==0
                              ).

% Ninguém pode saber o nome do adjudicataria 4

adjudicataria(4,nomeInterdito,74830,"Taipas").
excecao(adjudicataria(Id,N,NIF,M)) :- adjudicataria(Id,nomeInterdito,NIF,M).
nulo(nomeInterdito).
+adjudicataria(Id,NA,NIF,M) :: (
                              solucoes((Id,NA,NIF,M),(adjudicataria(4,Nome,74830,"Taipas"),nao(nulo(Nome))),S),
                              comprimento(S,N),
                              N==0
                              ).

%-------------------------------------------------------------------------------------------------------------------------------------

adjudicante(1,"Pedro Rodrigues",12345,"Barcelos").
adjudicante(2,"Ribeiro",9876,"Braga").
adjudicante(3,"Bruno",89976,"Taipas").
adjudicante(5,"Mário",243647596,"Nazaré").
adjudicante(6,"Nanda",275436742,"Taipas").
adjudicante(7,"Tiago",210437961,"Cascais").
adjudicante(8,"Davide",224160310,"Lisboa").
adjudicante(9,"Nuno",243764597,"Fão").
adjudicante(10,"Bruno",214657342,"Viseu").
adjudicataria(1,"André Morais",98765,"Esposende").
adjudicataria(2,"Luís Ribeiro",45454,"Braga").
adjudicataria(3,"José Silva",913213,"Guimarães").
adjudicataria(5,"Fatrix Dipetas",214256289,"Taipas").
adjudicataria(6,"Ana Moura",220213200,"Barcelos").
adjudicataria(7,"Tiago Malavita",240123123,"Coimbra").
adjudicataria(8,"João Carlos",263236210,"Viana do Castelo").
adjudicataria(9,"Dinis Teleti",278259256,"Guimarães").
adjudicataria(10,"Alfredo Vski",222986643,"Porto").
contrato(1,1,1,"Aquisição de Serviços","Concurso Público","Descrição",70000,365,"Braga",data(10,02,2020)).
contrato(2,1,2,"Aquisição de Serviços","Ajuste Direto","Descrição",300,100,"Braga",data(10,05,2020)).
contrato(3,2,3,"Contrato de Aquisição","Consulta Prévia","Descrição",70000,90,"Braga",data(15,05,2021)).
contrato(4,2,1,"Locação de Bens Móveis","Ajuste Direto","Descrição",500,90,"Braga",data(10,11,2022)).
contrato(5,1,3,"Aquisição de Serviços","Consulta Prévia","Descrição",70000,90,"Braga",data(24,09,2022)).



adjudicantes(S) :- solucoes((Id,Nome,NIF,Morada),adjudicante(Id,Nome,NIF,Morada),S).
adjudicatarias(S) :- solucoes((Id,Nome,NIF,Morada),adjudicataria(Id,Nome,NIF,Morada),S).
contratos(S) :- solucoes((IdC,IdAd,IdAda,TC,TP,Desc,C,P,Local,Data),contrato(IdC,IdAd,IdAda,TC,TP,Desc,C,P,Local,Data),S).

contratosAdjudicante(IdAd,S) :- solucoes((IdC,IdAd,IdAda,TC,TP,Desc,C,P,Local,Data),contrato(IdC,IdAd,IdAda,TC,TP,Desc,C,P,Local,Data),S).
contratosAdjudicataria(IdAda,S) :- solucoes((IdC,IdAd,IdAda,TC,TP,Desc,C,P,Local,Data),contrato(IdC,IdAd,IdAda,TC,TP,Desc,C,P,Local,Data),S).

getIdAdjudicante(NIF,Id) :- solucoes((IdAd),adjudicante(IdAd,_,NIF,_),[Id|T]).
getIdAdjudicataria(NIF,Id) :- solucoes((IdAda),adjudicataria(IdAda,_,NIF,_),[Id|T]).

contratosNIFAdjudicante(NIF,S) :- getIdAdjudicante(NIF,Id),contratosAdjudicante(Id,S).
contratosNIFAdjudicataria(NIF,S) :- getIdAdjudicataria(NIF,Id),contratosAdjudicataria(Id,S).

contratosLocal(L,S) :- solucoes((IdC,IdAd,IdAda,TC,TP,Desc,C,P,L,Data),contrato(IdC,IdAd,IdAda,TC,TP,Desc,C,P,L,Data),S).
contratosAno(A,S) :- solucoes((IdC,IdAd,IdAda,TC,TP,Desc,C,P,L,data(D,M,A)),contrato(IdC,IdAd,IdAda,TC,TP,Desc,C,P,L,data(D,M,A)),S).

custoTotalAdjudicante(NIF,N) :- getIdAdjudicante(NIF,Id),solucoes((C),contrato(_,Id,_,_,_,_,C,_,_,_),S),sum(S,N).
custoTotalAdjudicanteAno(NIF,A,N) :- getIdAdjudicante(NIF,Id),solucoes((C),contrato(_,Id,_,_,_,_,C,_,_,data(_,_,A)),S),sum(S,N).

ganhoTotalAdjudicataria(NIF,N) :- getIdAdjudicataria(NIF,Id),solucoes((C),contrato(_,_,Id,_,_,_,C,_,_,_),S),sum(S,N).
ganhoTotalAdjudicatariaAno(NIF,A,N) :- getIdAdjudicataria(NIF,Id),solucoes((C),contrato(_,_,Id,_,_,_,C,_,_,data(_,_,A)),S),sum(S,N).

getAdjudicante(Id,Nome,NIF,Morada) :- solucoes((Id,Nome,NIF,Morada),adjudicante(Id,Nome,NIF,Morada),[(Id,Nome,NIF,Morada)|T]).
getAdjudicataria(Id,Nome,NIF,Morada) :- solucoes((Id,Nome,NIF,Morada),adjudicataria(Id,Nome,NIF,Morada),[(Id,Nome,NIF,Morada)|T]).
getContrato(Id, IdAd, IdAda, TipoDeContrato, TipoDeProcedimento, Descricao, Custo, Prazo, Local, Data) :- solucoes((Id, IdAd, IdAda, TipoDeContrato, TipoDeProcedimento, Descricao, Custo, Prazo, Local, Data),contrato(Id, IdAd, IdAda, TipoDeContrato, TipoDeProcedimento, Descricao, Custo, Prazo, Local, Data),[(Id, IdAd, IdAda, TipoDeContrato, TipoDeProcedimento, Descricao, Custo, Prazo, Local, Data)|T]).

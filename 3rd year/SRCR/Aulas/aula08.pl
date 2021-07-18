/**/
%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% SIST. REPR. CONHECIMENTO E RACIOCINIO - MiEI/3

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
:- dynamic servico/2.
:- dynamic ato/4.


%-------------------------------------------------
% Aplicação do PMF

-servico(Servico, Nome) :-
    nao(servico(Servico, Nome)),
    nao(excecao(servico(Servico, Nome))).

-ato(Ato, Prestador, Utente, Dia) :-
    nao(ato(Ato, Prestador, Utente, Dia)),
    nao(excecao(ato(Ato, Prestador, Utente, Dia))).

%--------------------------------- - - - - - - - - - -  -

servico(ortopedia, amelia).
servico(obstetricia, ana).



ato(penso,ana,joana,sabado).
ato(gesso,amelia,jose,domingo).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -

evolucao( Termo ) :-
    solucoes( Invariante,+Termo::Invariante,Lista ),
    insercao( Termo ),
    teste( Lista ).

insercao( Termo ) :-
    assert( Termo ).
insercao( Termo ) :-
    retract( Termo ),!,fail.

teste( [] ).
teste( [R|LR] ) :-
    R,
    teste( LR ).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensão do predicado que permite a involucao do conhecimento

involucao( Termo ) :-
    solucoes( Invariante,-Termo::Invariante,Lista ),
    remocao( Termo ),
    teste( Lista ).

remocao( Termo ) :-
    retract( Termo ).
remocao( Termo ) :-
    assert( Termo ),!,fail.

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do meta-predicado demo: Questao,Resposta -> {V,F}
%                            Resposta = { verdadeiro,falso,desconhecido }

demo( Questao,verdadeiro ) :-
    Questao.
demo( Questao,falso ) :-
    -Questao.
demo( Questao,desconhecido ) :-
    nao( Questao ),
    nao( -Questao ).



%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do meta-predicado nao: Questao -> {V,F}

nao( Questao ) :-
    Questao, !, fail.
nao( Questao ).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -

solucoes( X,Y,Z ) :-
    findall( X,Y,Z ).

comprimento( S,N ) :-
    length( S,N ).



pertence( X,[X|L] ).
pertence( X,[Y|L] ) :-
    X \= Y,
    pertence( X,L ).






%-------------------------------------------------------------------
% Extensão do predicado servico: servico, enfermeira -> {V,F,D}
% a)
%------ Conhecimento Perfeito

servico( "Ortopedia","Amelia" ).
servico( "Obstetricia","Ana" ).
servico( "Obstetricia","Maria" ).
servico( "Obstetricia","Mariana" ).
servico( "Geriatria","Sofia" ).
servico( "Geriatria","Susana" ).

%------ Conhecimento Imperfeito Incerto

excecao( servico( xpt007,"Teodora" ) ).

%------ Conhecimento Imperfeito Interdito

excecao( servico( np9,"Zulmira" ) ).
nulo( np9 ).

%Invariante
+servico( S,"Zulmira" ) :- (solucoes((SS,"Zulmira"),(servico(SS,"Zulmira"), nao( nulo( SS ))),L ),
    comprimento( L,C ),
    C==0).

%-------------------------------------------------------------------
% Extensão do predicado atoMedico: ato,enfermeira,utente,data -> {V,F,D}
% b)
%------ Conhecimento Perfeito

atoMedico( "Penso","Ana","Joana","sabado" ).
atoMedico( "Gesso","Amelia","Jose","domingo" ).


%------ Conhecimento Imperfeito Incerto

excecao( atoMedico( xpt017,"Mariana","Joaquina","domingo" ) ).

excecao( atoMedico( "Domicilio","Maria", xpt121, xpt251 ) ).

excecao( atoMedico( "Sutura",xpt313,"Josue","segunda" ) ).


%------ Conhecimento Imperfeito Impreciso
excecao( atoMedico( "Domicilio","Susana",X,"segunda" ) ) :-
                      (X="Joao";X="Jose").

excecao( atoMedico( "Sutura",X,"Josefa",Y ) ) :-
                      (X="Maria";X="Mariana"),
                      (Y="terca";Y="sexta").

excecao( atoMedico( "Penso","Ana","Jacinta",X ) ) :-
                      (X="segunda";X="terca";X="quarta";X="quinta";X="sexta").

%-------------------------------------------------------------------
% Impede registos de atos medicos em feriados
% c)

feriado( "domingo" ).

+atoMedico( A,M,U,D )::(solucoes((A,M,U,D),(atoMedico(A,M,U,D),feriado(D)),L),
                        comprimento( L,C ),
                        C==0).

%-------------------------------------------------------------------
% Impede remoção de profissionais com atos registados
%d)

-servico( S,E ) :: (solucoes( (A),atoMedico(A,E,_,_),L ),
                    comprimento( L,N ),
                    N==0).

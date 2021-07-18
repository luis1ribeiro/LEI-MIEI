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
%Invariante Estrutural: nao permitir a insercao de conhecimento repetido

+filho( F,P ) :: (solucoes( (F,P),(filho( F,P )),S ),
                  comprimento( S,N ),
                  N==1 ).
%Invariante Referencial: nao admitir mais do que 2 progenitores para um mesmo individuo

+filho( F,P ) :: (solucoes( (Ps),(filho( F,Ps )),S )
                  comprimento( S,N ),
                  N=<2 ).

%Invariante Referencial: não é possível remover filhos para os quais exista registo de idade
% -filho ( F,P ) --> remover da base de conhecimento

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
%

evolucao( Termo ) :-
  solucoes( Invariante,+Termo::Invariante,Lista ),
  insercao( Termo ),
  teste( Lista ).

insercao( Termo ) :-
  assert( Termo ).
insercao( Termo ) :-
  retract( Termo ),!,fail.

teste( [] ).
teste ( [R|LR] ) :-
  R,
  teste( LR ).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensão do predicado que permite a involucao do conhecimento




USE TestesClinicos;

/* VIEW DOS ATLETAS */

DROP VIEW IF EXISTS viewAtletas;

CREATE VIEW viewAtletas AS 
	SELECT A.id_atleta AS "Cartao de Cidadao",A.nome AS "Nome",A.nascimento AS "Data de Nascimento",
		   A.nacionalidade AS "Nacionalidade",A.morada AS "Morada", A.contacto AS "Contacto",
           A.clube AS "Clube", M.nome AS "Modalidade",A.apto AS "Apto"
    FROM atletas AS A
    INNER JOIN atletacategoria AS AMC ON AMC.id_atleta=A.id_atleta
    INNER JOIN modalidade AS M ON AMC.id_modalidade=M.id_modalidade
    ORDER BY A.nome ASC;





/* VIEW DOS TESTES CLINICOS AGENDADO */

DROP VIEW IF EXISTS viewTestesAgendados;

CREATE VIEW viewTestesAgendados AS
	SELECT T.id_testes AS "Indentificador de teste",T.id_atleta AS "Cartao de Cidadao", A.nome AS "Paciente", T.agendado AS "Data do teste", 
		   E.nome AS "Especialidade", M.nome AS "Medico",T.estado AS "Estado"
	FROM testes AS T
    INNER JOIN medicos AS M ON M.id_medico=T.id_medico
    INNER JOIN especialidades AS E ON E.id_especialidade=M.id_especialidade
    INNER JOIN atletas AS A ON A.id_atleta=T.id_atleta
    WHERE T.estado="Marcado"
    ORDER BY T.agendado ASC,A.nome ASC;



/* VIEW DOS TESTES CLINICOS REALIZADOS */

DROP VIEW IF EXISTS viewHistoricoTestes;

CREATE VIEW viewHistoricoTestes AS
	SELECT T.id_testes AS "Indentificador de teste",T.id_atleta AS "Cartao de Cidadao", A.nome AS "Paciente", T.agendado AS "Data do teste", 
		   E.nome AS "Especialidade", M.nome AS "Medico",T.estado AS "Resultado"
	FROM testes AS T
    INNER JOIN medicos AS M ON M.id_medico=T.id_medico
    INNER JOIN especialidades AS E ON E.id_especialidade=M.id_especialidade
    INNER JOIN atletas AS A ON A.id_atleta=T.id_atleta
    WHERE NOT T.estado="Marcado"
    ORDER BY T.agendado DESC,A.nome ASC;




/* VIEW MODALIDADES E CATEGORIAS */

DROP VIEW IF EXISTS viewCategorias;

CREATE VIEW viewCategorias AS 
	SELECT M.nome AS "Modalidade", C.nome AS "Categoria"
    FROM modalidade AS M
    LEFT JOIN modalidadecategoria AS MC ON MC.id_modalidade=M.id_modalidade
    LEFT JOIN categoria AS C ON MC.id_categoria=C.id_categoria
	ORDER BY M.nome ASC, C.nome ASC;
    
/* VIEW MEDICOS */
DROP VIEW IF EXISTS viewMedicos;

CREATE VIEW viewMedicos AS 
	SELECT M.nome AS "Nome", E.nome AS "Especialidade",T.nome AS "Turno",T.inicio AS "Inicio", T.fim AS "Fim"
    FROM medicos AS M
    INNER JOIN especialidades AS E ON E.id_especialidade=M.id_especialidade
    INNER JOIN turnos AS T ON T.id_turno=M.id_turno
	ORDER BY M.nome ASC, T.nome ASC;
    

SELECT * FROM viewcategorias;
SELECT * FROM viewTestesAgendados;
SELECT * FROM viewHistoricoTestes;
SELECT * FROM viewatletas;
SELECT * FROM viewMedicos;
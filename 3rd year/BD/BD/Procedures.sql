USE TestesClinicos;

/* INSERIR MODALIDADE */

DROP PROCEDURE IF EXISTS inserirModalidade;

DELIMITER $$

CREATE PROCEDURE inserirModalidade(IN nome VARCHAR(255))
BEGIN
	DECLARE erro BOOL DEFAULT 0;
    DECLARE existe INT;
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION SET erro = 1;
	
    START TRANSACTION;
    
    SET existe = (SELECT Count(M.nome) FROM modalidade AS M WHERE M.nome=nome);
    
    IF (NOT existe=0) THEN
        SET erro=1;
	END IF;
    
    INSERT INTO modalidade (nome) VALUES (nome);
    
    IF erro THEN 
    ROLLBACK;
	ELSE 
		COMMIT;
	END IF;
    
END $$
DELIMITER ;





/* INSERIR CATEGORIA */

DROP PROCEDURE IF EXISTS inserirCategoria;

DELIMITER $$

CREATE PROCEDURE inserirCategoria(IN nome VARCHAR(255), IN modalidade VARCHAR(255))
BEGIN
	DECLARE erro BOOL DEFAULT 0;
    DECLARE existe INT;
    DECLARE id_modalidade INT;
    DECLARE id_categoria INT;
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION SET erro = 1;
	
    START TRANSACTION;
    
    SET existe = (SELECT Count(C.nome) FROM categoria AS C WHERE C.nome=nome);
    SET id_modalidade = (SELECT M.id_modalidade FROM modalidade AS M WHERE M.nome=modalidade);
    
    IF (existe=0) THEN
        INSERT INTO categoria (nome) VALUES (nome);
	END IF;
    
    IF (id_modalidade=null) THEN
		SET erro=1;
	END IF;	
    
    SET id_categoria = (SELECT C.id_categoria FROM categoria AS C WHERE C.nome=nome);
    INSERT INTO modalidadeCategoria (id_categoria,id_modalidade) VALUES (id_categoria,id_modalidade);
    
    IF erro THEN 
    ROLLBACK;
	ELSE 
		COMMIT;
	END IF;
    
END $$
DELIMITER ;




/* INSERIR ATLETA */

DROP PROCEDURE IF EXISTS inserirAtleta;

DELIMITER $$

CREATE PROCEDURE inserirAtleta(IN id INT, IN nome VARCHAR(255), IN nascimento DATE, IN clube VARCHAR(255), IN nacionalidade VARCHAR(255), 
								IN morada VARCHAR(255), IN contacto VARCHAR(255), IN modalidade VARCHAR(255),
								IN categoria VARCHAR(255))
BEGIN
	DECLARE erro BOOL DEFAULT 0;
    DECLARE existe INT DEFAULT 0;
    DECLARE id_modalidade INT;
    DECLARE id_categoria INT;
    DECLARE id_atleta INT;
    
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION SET erro = 1;
	
    START TRANSACTION;
    
    SET existe = (SELECT Count(A.id_atleta) FROM atletas AS A WHERE A.id_atleta=id);
    
    IF (existe=0) THEN
        INSERT INTO atletas (id_atleta,nome,nascimento,clube,nacionalidade,morada,contacto,apto) VALUES (id,nome,nascimento,clube,nacionalidade,morada,contacto,"Nao");
	END IF;
    
    SET id_atleta = (SELECT A.id_atleta FROM atletas AS A WHERE A.id_atleta=id);
    SET id_modalidade = (SELECT M.id_modalidade FROM modalidade AS M WHERE M.nome=modalidade);
    SET id_categoria = (SELECT C.id_categoria FROM categoria AS C WHERE C.nome=categoria);
    SET existe = (SELECT Count(*) FROM modalidadecategoria AS M WHERE M.id_modalidade=id_modalidade AND M.id_categoria=id_categoria);
    INSERT INTO atletacategoria (id_atleta,id_categoria,id_modalidade) VALUES (id_atleta,id_categoria,id_modalidade);
    
    IF existe=0 OR id_categoria=null OR id_modalidade=null THEN
		SET erro=1;
	END IF;
    
    IF erro THEN 
    ROLLBACK;
	ELSE 
		COMMIT;
	END IF;
    
END $$
DELIMITER ;




/* INSERIR ESPECIALIDADE */

DROP PROCEDURE IF EXISTS inserirEspecialidade;

DELIMITER $$

CREATE PROCEDURE inserirEspecialidade(IN nome VARCHAR(255),IN preco DECIMAL(8,2))
BEGIN
	DECLARE erro BOOL DEFAULT 0;
    DECLARE existe INT;
	DECLARE id_especialidade INT;
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION SET erro = 1;
	
    START TRANSACTION;
    
    SET existe = (SELECT Count(E.nome) FROM especialidades AS E WHERE E.nome=nome);
    
    IF (existe=0) THEN
		INSERT INTO especialidades (nome,preco) VALUES (nome,preco);
	END IF;
    
    IF erro THEN 
    ROLLBACK;
	ELSE 
		COMMIT;
	END IF;
    
END $$
DELIMITER ;






/* ADICIONAR CATEGORIA A ATLETA */

DROP PROCEDURE IF EXISTS addCategoriaAtleta;

DELIMITER $$

CREATE PROCEDURE addCategoriaAtleta (IN atleta INT,IN modalidade VARCHAR(255),IN categoria VARCHAR(255))
BEGIN
    DECLARE erro BOOL DEFAULT 0;
    DECLARE existeAtleta INT;
    DECLARE existeCategoria INT;
    DECLARE id_categoria INT;
    DECLARE id_modalidade INT;
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION SET erro = 1;

	START TRANSACTION;
    
    SET existeAtleta = (SELECT (Count(A.id_atleta)) FROM atletas AS A WHERE A.id_atleta=atleta);
    SET id_categoria = (SELECT C.id_categoria FROM categoria AS C WHERE C.nome=categoria);
	SET id_modalidade = (SELECT M.id_modalidade FROM modalidade AS M WHERE M.nome=modalidade);
    SET existeCategoria = (SELECT (Count(MC.id_categoria)) FROM modalidadecategoria AS MC WHERE MC.id_categoria=id_categoria AND MC.id_modalidade=id_modalidade);
    
    INSERT INTO atletacategoria (id_atleta,id_modalidade,id_categoria) VALUES (atleta,id_modalidade,id_categoria);
    
    IF existe=0 OR id_categoria=null OR id_modalidade=null OR existeCategoria=0 THEN
		SET erro=1;
    END IF;
    
    IF erro THEN 
    ROLLBACK;
	ELSE 
		COMMIT;
	END IF;

END $$
DELIMITER ;





/* INSERIR TURNO */

DROP PROCEDURE IF EXISTS inserirTurno;

DELIMITER $$

CREATE PROCEDURE inserirTurno(IN nome VARCHAR(255),IN inicio TIME,IN fim TIME)
BEGIN
	DECLARE erro BOOL DEFAULT 0;
    DECLARE existe INT;
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION SET erro = 1;
	
    START TRANSACTION;
    
    SET existe = (SELECT Count(T.nome) FROM turnos AS T WHERE T.nome=nome);
    
    IF (existe=0) THEN
		INSERT INTO turnos (nome,inicio,fim) VALUES (nome,inicio,fim);
	END IF;
    
    IF erro THEN 
    ROLLBACK;
	ELSE 
		COMMIT;
	END IF;
    
END $$
DELIMITER ;






/* INSERIR MEDICO */

DROP PROCEDURE IF EXISTS inserirMedico;

DELIMITER $$

CREATE PROCEDURE inserirMedico(IN idMedico INT,IN nome VARCHAR(255),IN especialidade VARCHAR(255),IN turno VARCHAR(255))
BEGIN
	DECLARE erro BOOL DEFAULT 0;
    DECLARE existe INT;
	DECLARE idTurno INT;
    DECLARE idEspecialidade INT;
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION SET erro = 1;
	
    START TRANSACTION;
    
    SET existe = (SELECT Count(M.nome) FROM medicos AS M WHERE M.id_medico=idMedico);
    SET idTurno = (SELECT T.id_turno FROM turnos AS T WHERE T.nome=turno);
    SET idEspecialidade = (SELECT E.id_especialidade FROM especialidades AS E WHERE E.nome=especialidade);
    
    IF (existe=0) THEN
		INSERT INTO medicos (id_medico,nome,id_especialidade,id_turno) VALUES (idMedico,nome,idEspecialidade,idTurno);
	END IF;
    
    IF idTurno=null OR idEspecialidade=null THEN 
		SET erro=1;
	END IF;
    
    IF erro THEN 
    ROLLBACK;
	ELSE 
		COMMIT;
	END IF;
    
END $$
DELIMITER ;







/* AGENDAR TESTE */

DROP PROCEDURE IF EXISTS agendarTeste;

DELIMITER $$

CREATE PROCEDURE agendarTeste(IN agendado DATETIME,IN especialidade VARCHAR(255),IN atleta INT)
BEGIN
	DECLARE erro BOOL DEFAULT 0;
    DECLARE existe INT;
    DECLARE existeAtleta INT;
    DECLARE idMedico INT;
	DECLARE id_especialidade INT;
    DECLARE id_teste INT;
    DECLARE dataAtual DATETIME;
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION SET erro = 1;
	
    START TRANSACTION;
    
	SET dataAtual = (SELECT CURRENT_TIMESTAMP);
    SET id_especialidade = (SELECT E.id_especialidade FROM especialidades AS E WHERE E.nome=especialidade);
    SET existeAtleta = (SELECT Count(A.id_atleta) FROM atletas AS A WHERE A.id_atleta=atleta);
	SET existe = (SELECT Count(T.id_atleta) FROM testes AS T JOIN medicos AS M ON M.id_medico=T.id_medico
					WHERE T.agendado=agendado AND M.id_especialidade=id_especialidade AND T.id_atleta=atleta);
    SET idMedico = (SELECT M.id_medico FROM medicos AS M JOIN turnos AS TU ON TU.id_turno=M.id_turno
					WHERE M.id_especialidade=id_especialidade AND (CAST(agendado as TIME) between TU.inicio AND TU.fim) 
					AND M.id_medico NOT IN (SELECT T.id_medico FROM testes AS T WHERE T.agendado=agendado AND M.id_especialidade=id_especialidade)
                    ORDER BY numeroTestes(M.id_medico) ASC
                    LIMIT 1);
    
    INSERT INTO testes (agendado,estado,id_medico,id_atleta) VALUES (agendado,"Marcado",idMedico,atleta);
    
     IF id_especialidade = null OR idMedico=null OR existeAtleta=0 OR NOT existe=0 OR dataAtual>agendado OR (SELECT DAYOFWEEK(agendado))=1 THEN
		SET erro=1;
	END IF;
    
    IF erro THEN 
    ROLLBACK;
	ELSE 
		COMMIT;
	END IF;
    
END $$
DELIMITER ;





/* Concluir teste*/

DROP PROCEDURE IF EXISTS concluirTeste;

DELIMITER $$

CREATE PROCEDURE concluirTeste (IN teste INT, IN result VARCHAR(255))
BEGIN
    DECLARE erro BOOL DEFAULT 0;
    DECLARE existe INT;
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION SET erro = 1;

	START TRANSACTION;
    
    SET existe = (SELECT (Count(T.id_testes)) FROM testes AS T WHERE T.id_testes=teste AND T.estado="Marcado");
    
    UPDATE testes AS T SET T.estado=result WHERE T.id_testes=teste;
    
    IF NOT result="Aprovado" AND NOT result="Reprovado" THEN
		SET erro=1;
	END IF;
    
    IF existe=0 THEN
		SET erro=1;
    END IF;
    
    IF erro THEN 
    ROLLBACK;
	ELSE 
		COMMIT;
	END IF;

END $$
DELIMITER ; 





/* Cancelar teste*/

DROP PROCEDURE IF EXISTS cancelarTeste;

DELIMITER $$

CREATE PROCEDURE cancelarTeste (IN teste INT)
BEGIN
    DECLARE erro BOOL DEFAULT 0;
    DECLARE existe INT;
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION SET erro = 1;

	START TRANSACTION;
    
    SET existe = (SELECT (Count(T.id_testes)) FROM testes AS T WHERE T.id_testes=teste AND T.estado="Marcado");
    
    DELETE FROM testes AS T WHERE T.id_testes=teste;
    
    IF existe=0 THEN
		SET erro=1;
    END IF;
    
    IF erro THEN 
    ROLLBACK;
	ELSE 
		COMMIT;
	END IF;

END $$
DELIMITER ; 







/* ALTERAR DATA TESTE*/

DROP PROCEDURE IF EXISTS alterarDataTeste;

DELIMITER $$

CREATE PROCEDURE alterarDataTeste (IN teste INT, IN novadata DATETIME)
BEGIN
    DECLARE erro BOOL DEFAULT 0;
    DECLARE existe INT;
    DECLARE dataAtual DATETIME;
    DECLARE idMedico INT;
    DECLARE idEspecialidade INT;
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION SET erro = 1;

	START TRANSACTION;
    
    SET dataAtual = (SELECT CURRENT_TIMESTAMP);
    SET existe = (SELECT (Count(T.id_testes)) FROM testes AS T WHERE T.id_testes=teste AND T.estado="Marcado");
    SET idEspecialidade = (SELECT M.id_especialidade FROM testes AS T JOIN medicos AS M ON T.id_medico=M.id_medico WHERE T.id_testes=teste);
    SET idMedico =  (SELECT M.id_medico FROM medicos AS M JOIN turnos AS TU ON TU.id_turno=M.id_turno
					WHERE M.id_especialidade=id_especialidade AND (CAST(agendado as TIME) between TU.inicio AND TU.fim) 
					AND M.id_medico NOT IN (SELECT T.id_medico FROM testes AS T WHERE T.agendado=agendado AND M.id_especialidade=id_especialidade)
                    ORDER BY numeroTestes(M.id_medico) ASC
                    LIMIT 1);
    
    UPDATE testes AS T SET T.agendado=novadata, T.id_medico=idMedico WHERE T.id_testes=teste;
    
    IF existe=0 OR dataAtual>novadata OR idMedico = null OR idEspecialidade=null OR (SELECT DAYOFWEEK(agendado))=1 THEN
		SET erro=1;
    END IF;
    
    IF erro THEN 
    ROLLBACK;
	ELSE 
		COMMIT;
	END IF;

END $$
DELIMITER ;






/* ALTERAR ESPECIALIDADE TESTE*/

DROP PROCEDURE IF EXISTS alterarEspecialidadeTeste;

DELIMITER $$

CREATE PROCEDURE alterarEspecialidadeTeste (IN teste INT, IN especialidade VARCHAR(255))
BEGIN
    DECLARE erro BOOL DEFAULT 0;
    DECLARE existe INT;
    DECLARE idEspecialidade INT;
    DECLARE idMedico INT;
    DECLARE dataAgendado DATETIME;
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION SET erro = 1;

	START TRANSACTION;
    
    SET existe = (SELECT (Count(T.id_testes)) FROM testes AS T WHERE T.id_testes=teste AND T.estado="Marcado");
    SET idEspecialidade = (SELECT E.id_especialidade FROM especialidades AS E WHERE E.nome=especialidade);
    SET dataAgendado = (SELECT T.agendado FROM testes AS T WHERE T.id_testes=teste);
    SET idMedico =  (SELECT M.id_medico FROM medicos AS M JOIN turnos AS TU ON TU.id_turno=M.id_turno
					WHERE M.id_especialidade=id_especialidade AND (CAST(agendado as TIME) between TU.inicio AND TU.fim) 
					AND M.id_medico NOT IN (SELECT T.id_medico FROM testes AS T WHERE T.agendado=agendado AND M.id_especialidade=id_especialidade)
                    ORDER BY numeroTestes(M.id_medico) ASC
                    LIMIT 1);
    
    UPDATE testes AS T SET T.id_medico=idMedico WHERE T.id_testes=teste;
    
    IF existe=0 OR idEspecialidade=null OR idMedico=null OR dataAgendado=null THEN
		SET erro=1;
    END IF;
    
    IF erro THEN 
    ROLLBACK;
	ELSE 
		COMMIT;
	END IF;

END $$
DELIMITER ;





/* EDITAR CONTACTO */

DROP PROCEDURE IF EXISTS editarContacto;

DELIMITER $$

CREATE PROCEDURE editarContacto (IN atleta INT, IN contacto VARCHAR(255))
BEGIN
    DECLARE erro BOOL DEFAULT 0;
    DECLARE existe INT;
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION SET erro = 1;

	START TRANSACTION;
    
    SET existe = (SELECT (Count(A.id_atleta)) FROM atletas AS A WHERE A.id_atleta=atleta);
    UPDATE atletas AS A SET A.contacto=contacto WHERE A.id_atleta=atleta; 
    
    IF existe=0 THEN
		SET erro=1;
    END IF;
    
    IF erro THEN 
    ROLLBACK;
	ELSE 
		COMMIT;
	END IF;

END $$
DELIMITER ;




/* EDITAR MORADA */

DROP PROCEDURE IF EXISTS editarMorada;

DELIMITER $$

CREATE PROCEDURE editarMorada (IN atleta INT, IN morada VARCHAR(255))
BEGIN
    DECLARE erro BOOL DEFAULT 0;
    DECLARE existe INT;
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION SET erro = 1;

	START TRANSACTION;
    
    SET existe = (SELECT (Count(A.id_atleta)) FROM atletas AS A WHERE A.id_atleta=atleta);
    
    UPDATE atletas AS A SET A.morada=morada WHERE A.id_atleta=atleta;
    
    IF existe=0 THEN
		SET erro=1;
    END IF;
    
    IF erro THEN 
    ROLLBACK;
	ELSE 
		COMMIT;
	END IF;

END $$
DELIMITER ;






/* RESULTADO EXAME */

DROP PROCEDURE IF EXISTS resultadoTeste;

DELIMITER $$

CREATE PROCEDURE resultadoTeste (IN teste INT)
BEGIN
    DECLARE erro BOOL DEFAULT 0;
    DECLARE existe INT;
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION SET erro = 1;

	START TRANSACTION;
    
    SET existe = (SELECT (Count(T.id_testes)) FROM testes AS T WHERE T.id_testes=teste);
    
    SELECT T.estado FROM testes AS T WHERE T.id_testes=teste;
    
    IF existe=0 THEN
		SET erro=1;
    END IF;
    
    IF erro THEN 
    ROLLBACK;
	ELSE 
		COMMIT;
	END IF;

END $$
DELIMITER ;





/* DATAS DOS TESTES DE UM ATLETA */

DROP PROCEDURE IF EXISTS datasTestes;

DELIMITER $$

CREATE PROCEDURE datasTestes (IN atleta INT)
BEGIN
    DECLARE erro BOOL DEFAULT 0;
    DECLARE existe INT;
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION SET erro = 1;

	START TRANSACTION;
    
    SET existe = (SELECT (Count(A.id_atleta)) FROM atletas AS A WHERE A.id_atleta=atleta);
    
    SELECT T.id_testes AS "Identificador de teste",T.agendado,M.nome AS "Medico",E.nome AS "Especialidade" FROM testes AS T
    JOIN medicos AS M ON T.id_medico=M.id_medico
    JOIN especialidades AS E ON M.id_especialidade=E.id_especialidade
    WHERE T.id_atleta=atleta AND T.estado="Marcado";
    
    IF existe=0 THEN
		SET erro=1;
    END IF;
    
    IF erro THEN 
    ROLLBACK;
	ELSE 
		COMMIT;
	END IF;

END $$
DELIMITER ;





/* RESULTADOS DOS TESTES DE UM ATLETA */

DROP PROCEDURE IF EXISTS resultadosTestes;

DELIMITER $$

CREATE PROCEDURE resultadosTestes (IN atleta INT)
BEGIN
    DECLARE erro BOOL DEFAULT 0;
    DECLARE existe INT;
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION SET erro = 1;

	START TRANSACTION;
    
    SET existe = (SELECT (Count(A.id_atleta)) FROM atletas AS A WHERE A.id_atleta=atleta);
    
    SELECT T.agendado AS "Data",E.nome AS "Especialidade",T.estado AS "Resultado" FROM testes AS T
    JOIN medicos AS M ON M.id_medico=T.id_medico
    JOIN especialidades AS E ON E.id_especialidade=M.id_especialidade
    WHERE T.id_atleta=atleta AND NOT T.estado="Marcado";
    
    IF existe=0 THEN
		SET erro=1;
    END IF;
    
    IF erro THEN 
    ROLLBACK;
	ELSE 
		COMMIT;
	END IF;

END $$
DELIMITER ;







/* DADOS DE UM ATLETA */

DROP PROCEDURE IF EXISTS dadosAtleta;

DELIMITER $$

CREATE PROCEDURE dadosAtleta (IN atleta INT)
BEGIN
    DECLARE erro BOOL DEFAULT 0;
    DECLARE existe INT;
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION SET erro = 1;

	START TRANSACTION;
    
    SET existe = (SELECT (Count(A.id_atleta)) FROM atletas AS A WHERE A.id_atleta=atleta);
    
    SELECT A.id_atleta AS "Cartao de Cidadao",A.nome AS "Nome",A.nascimento AS "Data de Nascimento",
		   A.nacionalidade AS "Nacionalidade",A.morada AS "Morada", A.contacto AS "Contacto",
           A.clube AS "Clube", M.nome AS "Modalidade",A.apto AS "Apto"
    FROM atletas AS A
    INNER JOIN atletacategoria AS AMC ON AMC.id_atleta=A.id_atleta
    INNER JOIN modalidade AS M ON AMC.id_modalidade=M.id_modalidade
    WHERE A.id_atleta=atleta
    ORDER BY A.nome ASC;
    
    IF existe=0 THEN
		SET erro=1;
    END IF;
    
    IF erro THEN 
    ROLLBACK;
	ELSE 
		COMMIT;
	END IF;

END $$
DELIMITER ;





/* CATEGORIAS DE ATLETA */

DROP PROCEDURE IF EXISTS categoriasAtleta;

DELIMITER $$

CREATE PROCEDURE categoriasAtleta (IN atleta INT)
BEGIN
    DECLARE erro BOOL DEFAULT 0;
    DECLARE existe INT;
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION SET erro = 1;

	START TRANSACTION;
    
    SET existe = (SELECT (Count(A.id_atleta)) FROM atletas AS A WHERE A.id_atleta=atleta);
    
    SELECT M.nome AS "Modalidade", C.nome AS "Categoria"
    FROM atletacategoria AS AMC
    INNER JOIN modalidade AS M ON AMC.id_modalidade=M.id_modalidade
    INNER JOIN categoria AS C ON AMC.id_categoria=C.id_categoria
    WHERE AMC.id_atleta=atleta
    ORDER BY M.nome ASC,C.nome DESC;
    
    IF existe=0 THEN
		SET erro=1;
    END IF;
    
    IF erro THEN 
    ROLLBACK;
	ELSE 
		COMMIT;
	END IF;

END $$
DELIMITER ;
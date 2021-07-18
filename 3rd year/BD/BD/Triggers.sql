USE TestesClinicos;

/* ATUALIZACAO DA APTIDAO DO ATLETA AO INSERIR UM TESTE */

DROP TRIGGER IF EXISTS after_insert_teste;

DELIMITER $$

CREATE TRIGGER after_insert_teste
AFTER INSERT ON testes
FOR EACH ROW
BEGIN
	DECLARE especialidadesSemApto INT;
    DECLARE atleta INT;
	SET atleta = NEW.id_atleta;
	SET especialidadesSemApto = (SELECT Count(E.id_especialidade) FROM especialidades AS E
						WHERE E.id_especialidade NOT IN
							(SELECT M.id_especialidade FROM medicos AS M
							JOIN testes AS T ON T.id_medico=M.id_medico
							JOIN (	
								SELECT M.id_especialidade,max(T.agendado) AS agendado FROM medicos AS M
								JOIN testes AS T ON T.id_medico=M.id_medico
								WHERE T.id_atleta=atleta
								GROUP BY M.id_especialidade
									) AS H
							ON H.id_especialidade=M.id_especialidade AND H.agendado=T.agendado
							WHERE T.id_atleta=atleta AND T.estado="Aprovado"));
      
	IF especialidadesSemApto > 0 THEN
		UPDATE atletas AS A SET A.apto="Nao" WHERE A.id_atleta=atleta;
	ELSE 
		UPDATE atletas AS A SET A.apto="Sim" WHERE A.id_atleta=atleta;
	END IF;
END $$
DELIMITER ;



/* ATUALIZACAO DA APTIDAO DO ATLETA APOS REALIZACAO DO TESTE */

DROP TRIGGER IF EXISTS after_update_teste;

DELIMITER $$

CREATE TRIGGER after_update_teste
AFTER UPDATE ON testes
FOR EACH ROW
BEGIN
	DECLARE especialidadesSemApto INT;
    DECLARE atleta INT;
    
	IF OLD.estado <> NEW.estado THEN
    
		SET atleta = NEW.id_atleta;
		SET especialidadesSemApto = (SELECT Count(E.id_especialidade) FROM especialidades AS E
							WHERE E.id_especialidade NOT IN
								(SELECT M.id_especialidade FROM medicos AS M
								JOIN testes AS T ON T.id_medico=M.id_medico
								JOIN (	
									SELECT M.id_especialidade,max(T.agendado) AS agendado FROM medicos AS M
									JOIN testes AS T ON T.id_medico=M.id_medico
									WHERE T.id_atleta=atleta
									GROUP BY M.id_especialidade
									 ) AS H
								ON H.id_especialidade=M.id_especialidade AND H.agendado=T.agendado
								WHERE T.id_atleta=atleta AND T.estado="Aprovado"));
      
		IF especialidadesSemApto > 0 THEN
			UPDATE atletas AS A SET A.apto="Nao" WHERE A.id_atleta=atleta;
		ELSE 
			UPDATE atletas AS A SET A.apto="Sim" WHERE A.id_atleta=atleta;
		END IF;
	END IF;
END $$
DELIMITER ;




/* ATUALIZACAO DA APTIDAO DO ATLETA AO CANCELAR UM TESTE */

DROP TRIGGER IF EXISTS after_insert_teste;

DELIMITER $$

CREATE TRIGGER after_insert_teste
AFTER DELETE ON testes
FOR EACH ROW
BEGIN
	DECLARE especialidadesSemApto INT;
    DECLARE atleta INT;
	SET atleta = OLD.id_atleta;
	SET especialidadesSemApto = (SELECT Count(E.id_especialidade) FROM especialidades AS E
						WHERE E.id_especialidade NOT IN
							(SELECT M.id_especialidade FROM medicos AS M
							JOIN testes AS T ON T.id_medico=M.id_medico
							JOIN (	
								SELECT M.id_especialidade,max(T.agendado) AS agendado FROM medicos AS M
								JOIN testes AS T ON T.id_medico=M.id_medico
								WHERE T.id_atleta=atleta
								GROUP BY M.id_especialidade
									) AS H
							ON H.id_especialidade=M.id_especialidade AND H.agendado=T.agendado
							WHERE T.id_atleta=atleta AND T.estado="Aprovado"));
      
	IF especialidadesSemApto > 0 THEN
		UPDATE atletas AS A SET A.apto="Nao" WHERE A.id_atleta=atleta;
	ELSE 
		UPDATE atletas AS A SET A.apto="Sim" WHERE A.id_atleta=atleta;
	END IF;
END $$
DELIMITER ;
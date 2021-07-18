USE TestesClinicos;

SET GLOBAL log_bin_trust_function_creators = 1;

/* FUNCAO CALCULA NUMERO DE TESTES FEITOS POR UM MEDICO */

DROP FUNCTION IF EXISTS numeroTestes;

DELIMITER $$

CREATE FUNCTION numeroTestes (medico INT) RETURNS INT
BEGIN
	DECLARE numero INT;
    
    SET numero = (SELECT (Count(T.id_medico)) FROM testes AS T
				  WHERE T.id_medico=medico);
    
    RETURN numero;
END$$

DELIMITER ;

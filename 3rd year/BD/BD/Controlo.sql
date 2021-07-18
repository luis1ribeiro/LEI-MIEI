USE TestesClinicos;

DROP ROLE IF EXISTS Medico;
DROP ROLE IF EXISTS Administrador;
DROP ROLE IF EXISTS Funcionario;
DROP ROLE IF EXISTS Atleta;
DROP ROLE IF EXISTS Clube;

CREATE ROLE Medico;
CREATE ROLE Administrador;
CREATE ROLE Funcionario;
CREATE ROLE Atleta;
CREATE ROLE Clube;

### Administrador
GRANT ALL PRIVILEGES ON * TO Administrador;

### Funcionario
GRANT EXECUTE ON PROCEDURE agendarTeste TO Funcionario;
GRANT EXECUTE ON PROCEDURE cancelarTeste TO Funcionario;
GRANT EXECUTE ON PROCEDURE alterarDataTeste TO Funcionario;
GRANT EXECUTE ON PROCEDURE alterarEspecialidadeTeste TO Funcionario;
GRANT SELECT ON viewTestesAgendados TO Funcionario;
GRANT SELECT ON viewHistoricoTestes TO Funcionario;
GRANT SELECT ON viewatletas TO Funcionario;

### Medico
GRANT EXECUTE ON PROCEDURE concluirTeste TO Medico;
GRANT EXECUTE ON PROCEDURE cancelarTeste TO Medico;
GRANT EXECUTE ON PROCEDURE resultadosTestes TO Medico;
GRANT EXECUTE ON PROCEDURE dadosAtleta TO Medico;
GRANT EXECUTE ON PROCEDURE categoriasAtleta TO Medico;
GRANT SELECT ON viewTestesAgendados TO Medico;
GRANT SELECT ON viewHistoricoTestes TO Medico;

### Atleta
GRANT EXECUTE ON PROCEDURE editarContacto TO Atleta;
GRANT EXECUTE ON PROCEDURE editarMorada TO Atleta;
GRANT EXECUTE ON PROCEDURE resultadoTeste TO Atleta;
GRANT EXECUTE ON PROCEDURE datasTestes TO Atleta;
GRANT EXECUTE ON PROCEDURE resultadosTestes TO Atleta;
GRANT EXECUTE ON PROCEDURE dadosAtleta TO Atleta;
GRANT EXECUTE ON PROCEDURE categoriasAtleta TO Atleta;
GRANT SELECT ON viewcategorias TO Atleta;
GRANT SELECT ON viewatletas TO Atleta;

### Clube
GRANT EXECUTE ON PROCEDURE editarContacto TO Clube;
GRANT EXECUTE ON PROCEDURE editarMorada TO Clube;
GRANT EXECUTE ON PROCEDURE datasTestes TO Clube; 
GRANT EXECUTE ON PROCEDURE resultadosTestes TO Clube;
GRANT EXECUTE ON PROCEDURE dadosAtleta TO Clube;
GRANT EXECUTE ON PROCEDURE categoriasAtleta TO Clube;
GRANT EXECUTE ON PROCEDURE inserirAtleta TO Clube;
GRANT EXECUTE ON PROCEDURE addCategoriaAtleta TO Clube;
GRANT SELECT ON viewcategorias TO Clube;
GRANT SELECT ON viewatletas TO Clube;


DROP USER IF EXISTS 'funcionario1'@'localhost';
CREATE USER 'funcionario1'@'localhost' IDENTIFIED BY 'func1pwd';
GRANT Funcionario TO 'funcionario1'@'localhost';

DROP USER IF EXISTS 'atleta1'@'localhost';
CREATE USER 'atleta1'@'localhost' IDENTIFIED BY 'a1pwd';
GRANT Atleta TO 'atleta1'@'localhost';

DROP USER IF EXISTS 'medico1'@'localhost';
CREATE USER 'medico1'@'localhost' IDENTIFIED BY 'med1pwd';
GRANT Medico TO 'medico1'@'localhost';

DROP USER IF EXISTS 'clube1'@'localhost';
CREATE USER 'clube1'@'localhost' IDENTIFIED BY 'c1pwd';
GRANT Clube TO 'clube1'@'localhost';

FLUSH PRIVILEGES;

### ADICIONAR CENAS AO CLUBE, ADICIONAR VIEWS PARA VER HORARIOS DISPONIVEIS E TAL
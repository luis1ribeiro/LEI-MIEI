USE TestesClinicos;

CALL agendarTeste("2020-02-07 14:00:00","Osteopatia",2);
CALL agendarTeste("2020-02-07 14:00:00","Dentista",3);
CALL concluirTeste(13,"Aprovado");
CALL concluirTeste(16,"Aprovado");
CALL concluirTeste(17,"Aprovado");
CALL concluirTeste(18,"Aprovado");
CALL agendarTeste("2020-02-09 14:00:00","Osteopatia",1);
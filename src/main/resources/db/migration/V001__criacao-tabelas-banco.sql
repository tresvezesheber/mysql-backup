CREATE TABLE servidor (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(50) NOT NULL,
    endereco_ip VARCHAR(15),

    PRIMARY KEY (id)
);

ALTER TABLE servidor
    ADD CONSTRAINT uk_endereco_ip UNIQUE (endereco_ip);


CREATE TABLE banco (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(50),
    tipo VARCHAR(20) NOT NULL,
    nome_de_usuario VARCHAR(50) NOT NULL,
    senha VARCHAR(120) NOT NULL,
    servidor_id BIGINT NOT NULL,

    PRIMARY KEY (id)
);

ALTER TABLE banco ADD CONSTRAINT
    FOREIGN KEY fk_servidor_id (servidor_id) REFERENCES servidor (id);


CREATE TABLE backup_banco (
    id BIGINT NOT NULL AUTO_INCREMENT,
    data_de_criacao DATETIME NOT NULL,
    banco_id BIGINT NOT NULL,

    PRIMARY KEY (id)
);

ALTER TABLE backup_banco ADD CONSTRAINT
    FOREIGN KEY fk_banco_id (banco_id) REFERENCES banco (id);
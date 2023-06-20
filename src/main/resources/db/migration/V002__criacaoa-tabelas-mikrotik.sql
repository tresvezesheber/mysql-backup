CREATE TABLE mikrotik (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(50) NOT NULL,
    nome_de_usuario VARCHAR(50) NOT NULL,
    senha VARCHAR(120) NOT NULL,
    endereco_ip VARCHAR(15) NOT NULL,

    PRIMARY KEY (id)
);

CREATE TABLE backup_mikrotik (
    id BIGINT NOT NULL AUTO_INCREMENT,
    data_de_criacao DATETIME NOT NULL,
    mikrotik_id BIGINT NOT NULL,

    PRIMARY KEY (id)
);

ALTER TABLE backup_mikrotik ADD CONSTRAINT
    FOREIGN KEY fk_mikrotik_id (mikrotik_id) REFERENCES mikrotik (id);
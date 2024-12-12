/*

Banco de dados OracleSQL relacionado ao projeto "TaLigado"

Bianca Leticia Román Caldeira - RM 552267 - Turma : 2TDSPH
Charlene Aparecida Estevam Mendes Fialho - RM 552252 - Turma : 2TDSPH
Lais Alves Da Silva Cruz - RM 552258 - Turma : 2TDSPH
Fabrico Torres Antonio - RM 97916 - Turma : 2TDSPH
Lucca Raphael Pereira dos Santos - RM 99675 - Turma : 2TDSPZ -> PROFESSOR: Milton Goya

*/

-- DDL (DROP,CREATE, ALTER)
-- Comandos para definicao de dados, incluindo a remocao de tabelas existentes, criacoes, alteracoes e suas restricoes.

/* DROPS */
/* Remove as tabelas e quaisquer restrições que dependem delas */
DROP TABLE alerta CASCADE CONSTRAINTS;
DROP TABLE dispositivo CASCADE CONSTRAINTS;
DROP TABLE dispositivo_sensor CASCADE CONSTRAINTS;
DROP TABLE empresa CASCADE CONSTRAINTS;
DROP TABLE endereco CASCADE CONSTRAINTS;
DROP TABLE filial CASCADE CONSTRAINTS;
DROP TABLE historico CASCADE CONSTRAINTS;
DROP TABLE historico_sensor CASCADE CONSTRAINTS;
DROP TABLE regulacao_energia CASCADE CONSTRAINTS;
DROP TABLE sensor CASCADE CONSTRAINTS;

CREATE TABLE alerta (
    idalerta NUMBER(4) NOT NULL PRIMARY KEY, -- ID único do alerta
    descricao VARCHAR2(100),                 -- Descrição do alerta
    severidade VARCHAR2(20),                 -- Severidade (ex: Alta, Média, Baixa)
    data_alerta DATE,                        -- Data do alerta
    sensor_idsensor NUMBER(4) NOT NULL       -- (FK) ID do sensor que gerou o alerta
);

CREATE TABLE empresa (
    idempresa NUMBER(4) NOT NULL PRIMARY KEY, -- ID único da empresa
    nome VARCHAR2(50) NOT NULL,               -- Nome da empresa (ex: Empresa XYZ)
    email VARCHAR2(40),                       -- Endereço de e-mail da empresa
    cnpj VARCHAR2(20),                        -- CNPJ da empresa (ex: 12.345.678/0001-90)
    segmento VARCHAR2(40),                    -- Segmento de atuação da empresa (ex: Tecnologia, Varejo, etc)
    data_fundacao DATE                        -- Data de fundação da empresa
);

CREATE TABLE endereco (
    idendereco NUMBER(4) NOT NULL PRIMARY KEY,    -- ID único do endereço
    logadouro VARCHAR2(50),                       -- Nome da rua ou logradouro (ex: Avenida Brasil)
    cidade VARCHAR2(40),                          -- Cidade onde o endereço está localizado
    estado VARCHAR2(40),                          -- Estado onde o endereço está localizado
    cep VARCHAR2(20),                             -- CEP do endereço (ex: 12345-678)
    pais VARCHAR2(40)                             -- País onde o endereço está localizado (ex: Brasil)
);

CREATE TABLE filial (
    idfilial NUMBER(4) NOT NULL PRIMARY KEY,  -- ID único da filial
    nome  VARCHAR2(50),                       -- Nome da filial (ex: Filial SP)
    tipo VARCHAR2(40),                        -- Tipo de filial (ex: Loja, Escritório, etc)
    cnpj_filial VARCHAR2(20),                 -- CNPJ da filial (ex: 12.345.678/0001-90)
    area_operacional VARCHAR2(40),            -- Área operacional da filial (ex: Vendas, Logística, etc)
    empresa_idempresa NUMBER(4) NOT NULL,     -- (FK) ID da empresa à qual a filial pertence
    endereco_idendereco NUMBER(4) NOT NULL    -- (FK) ID do endereço da filial
);


CREATE TABLE dispositivo (
    iddispositivo NUMBER(4) NOT NULL PRIMARY KEY, -- ID único do dispositivo (ex: furadeira, ar-condicionado)
    nome VARCHAR2(50),                            -- Nome do dispositivo (ex: Ar-condicionado LG, Furadeira Bosch)
    tipo VARCHAR2(40),                            -- Tipo de dispositivo (ex: Equipamento elétrico, ar condicionado, furadeira)
    status VARCHAR2(20),                          -- Status do dispositivo (ex: Ativo, Inativo, Em manutenção)
    data_instalacao DATE,                         -- Data de instalação do dispositivo
    filial_idfilial NUMBER(4) NOT NULL,           -- (FK) ID da filial onde o dispositivo está localizado
    potencia_nominal NUMBER(10,2)                 -- Potência nominal do dispositivo (em kW, ex: 1.5 para ar-condicionado)
);

CREATE TABLE sensor (
    idsensor NUMBER(4) NOT NULL PRIMARY KEY, -- ID único do sensor
    tipo VARCHAR2(50),           -- Tipo do sensor (ex: Temperatura, Consumo de Energia)
    descricao VARCHAR2(100),      -- Descrição do sensor (ex: Sensor de Temperatura do Ar)
    unidade VARCHAR2(20),        -- Unidade de medida do sensor (ex: °C, kWh)
    valor_atual NUMBER(10, 2),   -- Valor atual medido pelo sensor
    tempo_operacao NUMBER(10, 2) -- Tempo de operação do sensor
);

CREATE TABLE regulacao_energia (
    idregulacao NUMBER(4) NOT NULL PRIMARY KEY,  -- ID único da regulação de energia
    tarifa_kwh NUMBER(10,2),                    -- Tarifa por kWh (ex: 0.50)
    nome_bandeira VARCHAR2(50),                  -- Nome da bandeira tarifária (ex: Verde, Amarela, Vermelha)
    tarifa_adicional_bandeira NUMBER(10,2),     -- Tarifa adicional por bandeira tarifária (ex: 0.20)
    data_atualizacao DATE                        -- Data da última atualização da regulação
);

CREATE TABLE historico (
    idhistorico NUMBER(4) NOT NULL PRIMARY KEY,         -- ID único do histórico
    data_criacao DATE,                                  -- Data em que o histórico foi gerado
    valor_consumo_kwh NUMBER(10, 2),                    -- Valor do consumo de energia em kWh
    intensidade_carbono NUMBER(10, 2),                  -- Intensidade de carbono associada ao consumo
    custo_energia_estimado NUMBER(10, 2),               -- Custo estimado de energia consumida
    regulacao_energia_idregulacao NUMBER(4) NOT NULL    -- (FK) ID da regulação de energia associada
);

CREATE TABLE dispositivo_sensor (
    dispositivo_iddispositivo NUMBER(4) NOT NULL, -- (FK) ID do dispositivo
    sensor_idsensor NUMBER(4) NOT NULL,           -- (FK) ID do sensor
    CONSTRAINT dispositivo_sensor_pk PRIMARY KEY (dispositivo_iddispositivo, sensor_idsensor) -- Chave primária composta
);

CREATE TABLE historico_sensor (
    historico_idhistorico NUMBER(4) NOT NULL,     -- (FK) ID do histórico
    sensor_idsensor NUMBER(4) NOT NULL,           -- (FK) ID do sensor
    CONSTRAINT historico_sensor_pk PRIMARY KEY (historico_idhistorico, sensor_idsensor) -- Chave primária composta
);

/* ALTERS TABLES */
/*ADD Chaves Extrangeiras - relacionamentos*/

/* Adiciona a chave estrangeira fk_alerta_sensor na tabela alerta */
ALTER TABLE alerta ADD CONSTRAINT fk_alerta_sensor FOREIGN KEY (sensor_idsensor) REFERENCES sensor (idsensor); 
/* Relaciona o dispositivo com a filial */
ALTER TABLE dispositivo ADD CONSTRAINT fk_dispositivo_filial FOREIGN KEY (filial_idfilial) REFERENCES filial (idfilial); 
/* Relaciona o dispositivo com o dispositivo_sensor */
ALTER TABLE dispositivo_sensor ADD CONSTRAINT fk_dispositivo_sensor_dispositivo FOREIGN KEY (dispositivo_iddispositivo) REFERENCES dispositivo (iddispositivo); 
/* Relaciona o sensor com o dispositivo_sensor */
ALTER TABLE dispositivo_sensor ADD CONSTRAINT fk_dispositivo_sensor_sensor FOREIGN KEY (sensor_idsensor) REFERENCES sensor (idsensor); 
/* Relaciona a filial com a empresa */
ALTER TABLE filial ADD CONSTRAINT fk_filial_empresa FOREIGN KEY (empresa_idempresa) REFERENCES empresa (idempresa); 
/* Relaciona a filial com o endereço */
ALTER TABLE filial ADD CONSTRAINT fk_filial_endereco FOREIGN KEY (endereco_idendereco) REFERENCES endereco (idendereco); 
/* Relaciona o histórico com a regulação de energia */
ALTER TABLE historico ADD CONSTRAINT fk_historico_regulacao_energia FOREIGN KEY (regulacao_energia_idregulacao) REFERENCES regulacao_energia (idregulacao); 
/* Relaciona o histórico_sensor com o histórico */
ALTER TABLE historico_sensor ADD CONSTRAINT fk_historico_sensor_historico FOREIGN KEY (historico_idhistorico) REFERENCES historico (idhistorico); 
/* Relaciona o histórico_sensor com o sensor */
ALTER TABLE historico_sensor ADD CONSTRAINT fk_historico_sensor_sensor FOREIGN KEY (sensor_idsensor) REFERENCES sensor (idsensor); 

------------------------------------------------ AUDITORIA ------------------------------------------------
-- Criando a tabela de auditoria para registrar transações (INSERT, UPDATE, DELETE) nas tabelas
DROP TABLE auditoria CASCADE CONSTRAINTS;
CREATE TABLE auditoria (
    id_auditoria NUMBER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY, -- ID unico da auditoria
    nome_tabela VARCHAR2(50), -- Nome da tabela afetada
    operacao VARCHAR2(10), -- Tipo de operação (INSERT, UPDATE, DELETE)
    usuario VARCHAR2(50), -- Nome do usuário que fez a operacao
    data_operacao DATE, -- Data e hora da operacao
    dados_antigos VARCHAR2(4000), -- Dados antes da operacao (para UPDATE e DELETE)
    dados_novos VARCHAR2(4000) -- Dados apos a operacao (para INSERT e UPDATE)
);

--------------------------------------------- TRIGGERS -----------------------------------------------------------
CREATE OR REPLACE TRIGGER trig_auditoria_empresa
AFTER INSERT OR UPDATE OR DELETE ON empresa
FOR EACH ROW
DECLARE
    v_usuario VARCHAR2(50) := USER;
    v_dados_antigos VARCHAR2(4000);
    v_dados_novos VARCHAR2(4000);
BEGIN
    -- Concatena os dados antigos, se disponíveis
    IF INSERTING THEN
        v_dados_antigos := NULL; -- Inserindo novos valores, por isso NÃO TEM DADOS ANTIGOS
        v_dados_novos := 'ID: ' || :NEW.idempresa || ', Nome: ' || :NEW.nome || 
                         ', Email: ' || :NEW.email || ', CNPJ: ' || :NEW.cnpj || 
                         ', Segmento: ' || :NEW.segmento || ', Data de Fundação: ' || TO_CHAR(:NEW.data_fundacao, 'DD/MM/YYYY');
        INSERT INTO Auditoria (nome_tabela, operacao, usuario, data_operacao, dados_antigos, dados_novos)
        VALUES ('empresa', 'INSERT', v_usuario, SYSDATE, v_dados_antigos, v_dados_novos);
        
    ELSIF DELETING THEN
        v_dados_antigos := 'ID: ' || :OLD.idempresa || ', Nome: ' || :OLD.nome || 
                           ', Email: ' || :OLD.email || ', CNPJ: ' || :OLD.cnpj || 
                           ', Segmento: ' || :OLD.segmento || ', Data de Fundação: ' || TO_CHAR(:OLD.data_fundacao, 'DD/MM/YYYY');
        v_dados_novos := NULL; -- os dados são DELETADOS, por isso NÃO TEM novos valores
        INSERT INTO Auditoria (nome_tabela, operacao, usuario, data_operacao, dados_antigos, dados_novos)
        VALUES ('empresa', 'DELETE', v_usuario, SYSDATE, v_dados_antigos, v_dados_novos);
        
    ELSIF UPDATING THEN
        v_dados_antigos := 'ID: ' || :OLD.idempresa || ', Nome: ' || :OLD.nome || 
                           ', Email: ' || :OLD.email || ', CNPJ: ' || :OLD.cnpj || 
                           ', Segmento: ' || :OLD.segmento || ', Data de Fundação: ' || TO_CHAR(:OLD.data_fundacao, 'DD/MM/YYYY');
        v_dados_novos := 'ID: ' || :NEW.idempresa || ', Nome: ' || :NEW.nome || 
                         ', Email: ' || :NEW.email || ', CNPJ: ' || :NEW.cnpj || 
                         ', Segmento: ' || :NEW.segmento || ', Data de Fundação: ' || TO_CHAR(:NEW.data_fundacao, 'DD/MM/YYYY');
        INSERT INTO Auditoria (nome_tabela, operacao, usuario, data_operacao, dados_antigos, dados_novos)
        VALUES ('empresa', 'UPDATE', v_usuario, SYSDATE, v_dados_antigos, v_dados_novos);
    END IF;
END;

CREATE OR REPLACE TRIGGER trig_auditoria_endereco
AFTER INSERT OR UPDATE OR DELETE ON endereco
FOR EACH ROW
DECLARE
    v_usuario VARCHAR2(50) := USER;
    v_dados_antigos VARCHAR2(4000);
    v_dados_novos VARCHAR2(4000);
BEGIN
    -- Concatena os dados antigos, se disponíveis
    IF INSERTING THEN
        v_dados_antigos := NULL; -- Inserindo novos valores, por isso NÃO TEM DADOS ANTIGOS
        v_dados_novos := 'ID: ' || :NEW.idendereco || ', Logradouro: ' || :NEW.logadouro || 
                         ', Cidade: ' || :NEW.cidade || ', Estado: ' || :NEW.estado || 
                         ', CEP: ' || :NEW.cep || ', País: ' || :NEW.pais;
        INSERT INTO Auditoria (nome_tabela, operacao, usuario, data_operacao, dados_antigos, dados_novos)
        VALUES ('endereco', 'INSERT', v_usuario, SYSDATE, v_dados_antigos, v_dados_novos);
        
    ELSIF DELETING THEN
        v_dados_antigos := 'ID: ' || :OLD.idendereco || ', Logradouro: ' || :OLD.logadouro || 
                           ', Cidade: ' || :OLD.cidade || ', Estado: ' || :OLD.estado || 
                           ', CEP: ' || :OLD.cep || ', País: ' || :OLD.pais;
        v_dados_novos := NULL; -- os dados são DELETADOS, por isso NÃO TEM novos valores
        INSERT INTO Auditoria (nome_tabela, operacao, usuario, data_operacao, dados_antigos, dados_novos)
        VALUES ('endereco', 'DELETE', v_usuario, SYSDATE, v_dados_antigos, v_dados_novos);
        
    ELSIF UPDATING THEN
        v_dados_antigos := 'ID: ' || :OLD.idendereco || ', Logradouro: ' || :OLD.logadouro || 
                           ', Cidade: ' || :OLD.cidade || ', Estado: ' || :OLD.estado || 
                           ', CEP: ' || :OLD.cep || ', País: ' || :OLD.pais;
        v_dados_novos := 'ID: ' || :NEW.idendereco || ', Logradouro: ' || :NEW.logadouro || 
                         ', Cidade: ' || :NEW.cidade || ', Estado: ' || :NEW.estado || 
                         ', CEP: ' || :NEW.cep || ', País: ' || :NEW.pais;
        INSERT INTO Auditoria (nome_tabela, operacao, usuario, data_operacao, dados_antigos, dados_novos)
        VALUES ('endereco', 'UPDATE', v_usuario, SYSDATE, v_dados_antigos, v_dados_novos);
    END IF;
END;

CREATE OR REPLACE TRIGGER trig_auditoria_filial
AFTER INSERT OR UPDATE OR DELETE ON filial
FOR EACH ROW
DECLARE
    v_usuario VARCHAR2(50) := USER;
    v_dados_antigos VARCHAR2(4000);
    v_dados_novos VARCHAR2(4000);
BEGIN
    -- Concatena os dados antigos, se disponíveis
    IF INSERTING THEN
        v_dados_antigos := NULL; -- Inserindo novos valores, por isso NÃO TEM DADOS ANTIGOS
        v_dados_novos := 'ID: ' || :NEW.idfilial || ', Nome: ' || :NEW.nome || 
                         ', Tipo: ' || :NEW.tipo || ', CNPJ: ' || :NEW.cnpj_filial || 
                         ', Área Operacional: ' || :NEW.area_operacional || 
                         ', Empresa ID: ' || :NEW.empresa_idempresa || 
                         ', Endereço ID: ' || :NEW.endereco_idendereco;
        INSERT INTO Auditoria (nome_tabela, operacao, usuario, data_operacao, dados_antigos, dados_novos)
        VALUES ('filial', 'INSERT', v_usuario, SYSDATE, v_dados_antigos, v_dados_novos);
        
    ELSIF DELETING THEN
        v_dados_antigos := 'ID: ' || :OLD.idfilial || ', Nome: ' || :OLD.nome || 
                           ', Tipo: ' || :OLD.tipo || ', CNPJ: ' || :OLD.cnpj_filial || 
                           ', Área Operacional: ' || :OLD.area_operacional || 
                           ', Empresa ID: ' || :OLD.empresa_idempresa || 
                           ', Endereço ID: ' || :OLD.endereco_idendereco;
        v_dados_novos := NULL; -- os dados são DELETADOS, por isso NÃO TEM novos valores
        INSERT INTO Auditoria (nome_tabela, operacao, usuario, data_operacao, dados_antigos, dados_novos)
        VALUES ('filial', 'DELETE', v_usuario, SYSDATE, v_dados_antigos, v_dados_novos);
        
    ELSIF UPDATING THEN
        v_dados_antigos := 'ID: ' || :OLD.idfilial || ', Nome: ' || :OLD.nome || 
                           ', Tipo: ' || :OLD.tipo || ', CNPJ: ' || :OLD.cnpj_filial || 
                           ', Área Operacional: ' || :OLD.area_operacional || 
                           ', Empresa ID: ' || :OLD.empresa_idempresa || 
                           ', Endereço ID: ' || :OLD.endereco_idendereco;
        v_dados_novos := 'ID: ' || :NEW.idfilial || ', Nome: ' || :NEW.nome || 
                         ', Tipo: ' || :NEW.tipo || ', CNPJ: ' || :NEW.cnpj_filial || 
                         ', Área Operacional: ' || :NEW.area_operacional || 
                         ', Empresa ID: ' || :NEW.empresa_idempresa || 
                         ', Endereço ID: ' || :NEW.endereco_idendereco;
        INSERT INTO Auditoria (nome_tabela, operacao, usuario, data_operacao, dados_antigos, dados_novos)
        VALUES ('filial', 'UPDATE', v_usuario, SYSDATE, v_dados_antigos, v_dados_novos);
    END IF;
END;

CREATE OR REPLACE TRIGGER trig_auditoria_regulacao_energia
AFTER INSERT OR UPDATE OR DELETE ON regulacao_energia
FOR EACH ROW
DECLARE
    v_usuario VARCHAR2(50) := USER;
    v_dados_antigos VARCHAR2(4000);
    v_dados_novos VARCHAR2(4000);
BEGIN
    -- Concatena os dados antigos, se disponíveis
    IF INSERTING THEN
        v_dados_antigos := NULL; -- Inserindo novos valores, por isso NÃO TEM DADOS ANTIGOS
        v_dados_novos := 'ID: ' || :NEW.idregulacao || ', Tarifa kWh: ' || :NEW.tarifa_kwh || 
                         ', Nome Bandeira: ' || :NEW.nome_bandeira || ', Tarifa Adicional Bandeira: ' || :NEW.tarifa_adicional_bandeira || 
                         ', Data Atualização: ' || TO_CHAR(:NEW.data_atualizacao, 'DD/MM/YYYY');
        INSERT INTO Auditoria (nome_tabela, operacao, usuario, data_operacao, dados_antigos, dados_novos)
        VALUES ('regulacao_energia', 'INSERT', v_usuario, SYSDATE, v_dados_antigos, v_dados_novos);
        
    ELSIF DELETING THEN
        v_dados_antigos := 'ID: ' || :OLD.idregulacao || ', Tarifa kWh: ' || :OLD.tarifa_kwh || 
                           ', Nome Bandeira: ' || :OLD.nome_bandeira || ', Tarifa Adicional Bandeira: ' || :OLD.tarifa_adicional_bandeira || 
                           ', Data Atualização: ' || TO_CHAR(:OLD.data_atualizacao, 'DD/MM/YYYY');
        v_dados_novos := NULL; -- os dados são DELETADOS, por isso NÃO TEM novos valores
        INSERT INTO Auditoria (nome_tabela, operacao, usuario, data_operacao, dados_antigos, dados_novos)
        VALUES ('regulacao_energia', 'DELETE', v_usuario, SYSDATE, v_dados_antigos, v_dados_novos);
        
    ELSIF UPDATING THEN
        v_dados_antigos := 'ID: ' || :OLD.idregulacao || ', Tarifa kWh: ' || :OLD.tarifa_kwh || 
                           ', Nome Bandeira: ' || :OLD.nome_bandeira || ', Tarifa Adicional Bandeira: ' || :OLD.tarifa_adicional_bandeira || 
                           ', Data Atualização: ' || TO_CHAR(:OLD.data_atualizacao, 'DD/MM/YYYY');
        v_dados_novos := 'ID: ' || :NEW.idregulacao || ', Tarifa kWh: ' || :NEW.tarifa_kwh || 
                         ', Nome Bandeira: ' || :NEW.nome_bandeira || ', Tarifa Adicional Bandeira: ' || :NEW.tarifa_adicional_bandeira || 
                         ', Data Atualização: ' || TO_CHAR(:NEW.data_atualizacao, 'DD/MM/YYYY');
        INSERT INTO Auditoria (nome_tabela, operacao, usuario, data_operacao, dados_antigos, dados_novos)
        VALUES ('regulacao_energia', 'UPDATE', v_usuario, SYSDATE, v_dados_antigos, v_dados_novos);
    END IF;
END;

CREATE OR REPLACE TRIGGER trig_auditoria_dispositivo
AFTER INSERT OR UPDATE OR DELETE ON dispositivo
FOR EACH ROW
DECLARE
    v_usuario VARCHAR2(50) := USER;
    v_dados_antigos VARCHAR2(4000);
    v_dados_novos VARCHAR2(4000);
BEGIN
    -- Concatena os dados antigos, se disponíveis
    IF INSERTING THEN
        v_dados_antigos := NULL; -- Inserindo novos valores, por isso NÃO TEM DADOS ANTIGOS
        v_dados_novos := 'ID: ' || :NEW.iddispositivo || ', Nome: ' || :NEW.nome || 
                         ', Tipo: ' || :NEW.tipo || ', Status: ' || :NEW.status || 
                         ', Data Instalação: ' || TO_CHAR(:NEW.data_instalacao, 'DD/MM/YYYY') ||
                         ', Potência Nominal: ' || :NEW.potencia_nominal;
        INSERT INTO Auditoria (nome_tabela, operacao, usuario, data_operacao, dados_antigos, dados_novos)
        VALUES ('dispositivo', 'INSERT', v_usuario, SYSDATE, v_dados_antigos, v_dados_novos);
        
    ELSIF DELETING THEN
        v_dados_antigos := 'ID: ' || :OLD.iddispositivo || ', Nome: ' || :OLD.nome || 
                           ', Tipo: ' || :OLD.tipo || ', Status: ' || :OLD.status || 
                           ', Data Instalação: ' || TO_CHAR(:OLD.data_instalacao, 'DD/MM/YYYY') ||
                           ', Potência Nominal: ' || :OLD.potencia_nominal;
        v_dados_novos := NULL; -- os dados são DELETADOS, por isso NÃO TEM novos valores
        INSERT INTO Auditoria (nome_tabela, operacao, usuario, data_operacao, dados_antigos, dados_novos)
        VALUES ('dispositivo', 'DELETE', v_usuario, SYSDATE, v_dados_antigos, v_dados_novos);
        
    ELSIF UPDATING THEN
        v_dados_antigos := 'ID: ' || :OLD.iddispositivo || ', Nome: ' || :OLD.nome || 
                           ', Tipo: ' || :OLD.tipo || ', Status: ' || :OLD.status || 
                           ', Data Instalação: ' || TO_CHAR(:OLD.data_instalacao, 'DD/MM/YYYY') ||
                           ', Potência Nominal: ' || :OLD.potencia_nominal;
        v_dados_novos := 'ID: ' || :NEW.iddispositivo || ', Nome: ' || :NEW.nome || 
                         ', Tipo: ' || :NEW.tipo || ', Status: ' || :NEW.status || 
                         ', Data Instalação: ' || TO_CHAR(:NEW.data_instalacao, 'DD/MM/YYYY') ||
                         ', Potência Nominal: ' || :NEW.potencia_nominal;
        INSERT INTO Auditoria (nome_tabela, operacao, usuario, data_operacao, dados_antigos, dados_novos)
        VALUES ('dispositivo', 'UPDATE', v_usuario, SYSDATE, v_dados_antigos, v_dados_novos);
    END IF;
END;

CREATE OR REPLACE TRIGGER trig_auditoria_sensor
AFTER INSERT OR UPDATE OR DELETE ON sensor
FOR EACH ROW
DECLARE
    v_usuario VARCHAR2(50) := USER;
    v_dados_antigos VARCHAR2(4000);
    v_dados_novos VARCHAR2(4000);
BEGIN
    -- Concatena os dados antigos, se disponíveis
    IF INSERTING THEN
        v_dados_antigos := NULL; -- Inserindo novos valores, por isso NÃO TEM DADOS ANTIGOS
        v_dados_novos := 'ID: ' || :NEW.idsensor || ', Tipo: ' || :NEW.tipo || 
                         ', Descrição: ' || :NEW.descricao || ', Unidade: ' || :NEW.unidade || 
                         ', Valor Atual: ' || :NEW.valor_atual || ', Tempo de Operação: ' || :NEW.tempo_operacao;
        INSERT INTO Auditoria (nome_tabela, operacao, usuario, data_operacao, dados_antigos, dados_novos)
        VALUES ('sensor', 'INSERT', v_usuario, SYSDATE, v_dados_antigos, v_dados_novos);
        
    ELSIF DELETING THEN
        v_dados_antigos := 'ID: ' || :OLD.idsensor || ', Tipo: ' || :OLD.tipo || 
                           ', Descrição: ' || :OLD.descricao || ', Unidade: ' || :OLD.unidade || 
                           ', Valor Atual: ' || :OLD.valor_atual || ', Tempo de Operação: ' || :OLD.tempo_operacao;
        v_dados_novos := NULL; -- os dados são DELETADOS, por isso NÃO TEM novos valores
        INSERT INTO Auditoria (nome_tabela, operacao, usuario, data_operacao, dados_antigos, dados_novos)
        VALUES ('sensor', 'DELETE', v_usuario, SYSDATE, v_dados_antigos, v_dados_novos);
        
    ELSIF UPDATING THEN
        v_dados_antigos := 'ID: ' || :OLD.idsensor || ', Tipo: ' || :OLD.tipo || 
                           ', Descrição: ' || :OLD.descricao || ', Unidade: ' || :OLD.unidade || 
                           ', Valor Atual: ' || :OLD.valor_atual || ', Tempo de Operação: ' || :OLD.tempo_operacao;
        v_dados_novos := 'ID: ' || :NEW.idsensor || ', Tipo: ' || :NEW.tipo || 
                         ', Descrição: ' || :NEW.descricao || ', Unidade: ' || :NEW.unidade || 
                         ', Valor Atual: ' || :NEW.valor_atual || ', Tempo de Operação: ' || :NEW.tempo_operacao;
        INSERT INTO Auditoria (nome_tabela, operacao, usuario, data_operacao, dados_antigos, dados_novos)
        VALUES ('sensor', 'UPDATE', v_usuario, SYSDATE, v_dados_antigos, v_dados_novos);
    END IF;
END;

CREATE OR REPLACE TRIGGER trig_auditoria_alerta
AFTER INSERT OR UPDATE OR DELETE ON alerta
FOR EACH ROW
DECLARE
    v_usuario VARCHAR2(50) := USER;
    v_dados_antigos VARCHAR2(4000);
    v_dados_novos VARCHAR2(4000);
BEGIN
    -- Concatena os dados antigos, se disponíveis
    IF INSERTING THEN
        v_dados_antigos := NULL; -- Inserindo novos valores, por isso NÃO TEM DADOS ANTIGOS
        v_dados_novos := 'ID: ' || :NEW.idalerta || ', Descrição: ' || :NEW.descricao || 
                         ', Severidade: ' || :NEW.severidade || ', Data do Alerta: ' || TO_CHAR(:NEW.data_alerta, 'DD/MM/YYYY') ||
                         ', Sensor ID: ' || :NEW.sensor_idsensor;
        INSERT INTO Auditoria (nome_tabela, operacao, usuario, data_operacao, dados_antigos, dados_novos)
        VALUES ('alerta', 'INSERT', v_usuario, SYSDATE, v_dados_antigos, v_dados_novos);
        
    ELSIF DELETING THEN
        v_dados_antigos := 'ID: ' || :OLD.idalerta || ', Descrição: ' || :OLD.descricao || 
                           ', Severidade: ' || :OLD.severidade || ', Data do Alerta: ' || TO_CHAR(:OLD.data_alerta, 'DD/MM/YYYY') ||
                           ', Sensor ID: ' || :OLD.sensor_idsensor;
        v_dados_novos := NULL; -- os dados são DELETADOS, por isso NÃO TEM novos valores
        INSERT INTO Auditoria (nome_tabela, operacao, usuario, data_operacao, dados_antigos, dados_novos)
        VALUES ('alerta', 'DELETE', v_usuario, SYSDATE, v_dados_antigos, v_dados_novos);
        
    ELSIF UPDATING THEN
        v_dados_antigos := 'ID: ' || :OLD.idalerta || ', Descrição: ' || :OLD.descricao || 
                           ', Severidade: ' || :OLD.severidade || ', Data do Alerta: ' || TO_CHAR(:OLD.data_alerta, 'DD/MM/YYYY') ||
                           ', Sensor ID: ' || :OLD.sensor_idsensor;
        v_dados_novos := 'ID: ' || :NEW.idalerta || ', Descrição: ' || :NEW.descricao || 
                         ', Severidade: ' || :NEW.severidade || ', Data do Alerta: ' || TO_CHAR(:NEW.data_alerta, 'DD/MM/YYYY') ||
                         ', Sensor ID: ' || :NEW.sensor_idsensor;
        INSERT INTO Auditoria (nome_tabela, operacao, usuario, data_operacao, dados_antigos, dados_novos)
        VALUES ('alerta', 'UPDATE', v_usuario, SYSDATE, v_dados_antigos, v_dados_novos);
    END IF;
END;

CREATE OR REPLACE TRIGGER trig_auditoria_dispositivo_sensor
AFTER INSERT OR UPDATE OR DELETE ON dispositivo_sensor
FOR EACH ROW
DECLARE
    v_usuario VARCHAR2(50) := USER;
    v_dados_antigos VARCHAR2(4000);
    v_dados_novos VARCHAR2(4000);
BEGIN
    -- Concatena os dados antigos, se disponíveis
    IF INSERTING THEN
        v_dados_antigos := NULL; -- Inserindo novos valores, por isso NÃO TEM DADOS ANTIGOS
        v_dados_novos := 'Dispositivo ID: ' || :NEW.dispositivo_iddispositivo || ', Sensor ID: ' || :NEW.sensor_idsensor;
        INSERT INTO Auditoria (nome_tabela, operacao, usuario, data_operacao, dados_antigos, dados_novos)
        VALUES ('dispositivo_sensor', 'INSERT', v_usuario, SYSDATE, v_dados_antigos, v_dados_novos);
        
    ELSIF DELETING THEN
        v_dados_antigos := 'Dispositivo ID: ' || :OLD.dispositivo_iddispositivo || ', Sensor ID: ' || :OLD.sensor_idsensor;
        v_dados_novos := NULL; -- os dados são DELETADOS, por isso NÃO TEM novos valores
        INSERT INTO Auditoria (nome_tabela, operacao, usuario, data_operacao, dados_antigos, dados_novos)
        VALUES ('dispositivo_sensor', 'DELETE', v_usuario, SYSDATE, v_dados_antigos, v_dados_novos);
        
    ELSIF UPDATING THEN
        v_dados_antigos := 'Dispositivo ID: ' || :OLD.dispositivo_iddispositivo || ', Sensor ID: ' || :OLD.sensor_idsensor;
        v_dados_novos := 'Dispositivo ID: ' || :NEW.dispositivo_iddispositivo || ', Sensor ID: ' || :NEW.sensor_idsensor;
        INSERT INTO Auditoria (nome_tabela, operacao, usuario, data_operacao, dados_antigos, dados_novos)
        VALUES ('dispositivo_sensor', 'UPDATE', v_usuario, SYSDATE, v_dados_antigos, v_dados_novos);
    END IF;
END;

CREATE OR REPLACE TRIGGER trig_auditoria_historico
AFTER INSERT OR UPDATE OR DELETE ON historico
FOR EACH ROW
DECLARE
    v_usuario VARCHAR2(50) := USER;
    v_dados_antigos VARCHAR2(4000);
    v_dados_novos VARCHAR2(4000);
BEGIN
    -- Concatena os dados antigos, se disponíveis
    IF INSERTING THEN
        v_dados_antigos := NULL; -- Inserindo novos valores, por isso NÃO TEM DADOS ANTIGOS
        v_dados_novos := 'ID: ' || :NEW.idhistorico || ', Data Criação: ' || TO_CHAR(:NEW.data_criacao, 'DD/MM/YYYY') ||
                         ', Consumo (kWh): ' || :NEW.valor_consumo_kwh || 
                         ', Intensidade Carbono: ' || :NEW.intensidade_carbono || 
                         ', Custo Estimado: ' || :NEW.custo_energia_estimado || 
                         ', Regulacao Energia ID: ' || :NEW.regulacao_energia_idregulacao;
        INSERT INTO Auditoria (nome_tabela, operacao, usuario, data_operacao, dados_antigos, dados_novos)
        VALUES ('historico', 'INSERT', v_usuario, SYSDATE, v_dados_antigos, v_dados_novos);
        
    ELSIF DELETING THEN
        v_dados_antigos := 'ID: ' || :OLD.idhistorico || ', Data Criação: ' || TO_CHAR(:OLD.data_criacao, 'DD/MM/YYYY') ||
                           ', Consumo (kWh): ' || :OLD.valor_consumo_kwh || 
                           ', Intensidade Carbono: ' || :OLD.intensidade_carbono || 
                           ', Custo Estimado: ' || :OLD.custo_energia_estimado || 
                           ', Regulacao Energia ID: ' || :OLD.regulacao_energia_idregulacao;
        v_dados_novos := NULL; -- os dados são DELETADOS, por isso NÃO TEM novos valores
        INSERT INTO Auditoria (nome_tabela, operacao, usuario, data_operacao, dados_antigos, dados_novos)
        VALUES ('historico', 'DELETE', v_usuario, SYSDATE, v_dados_antigos, v_dados_novos);
        
    ELSIF UPDATING THEN
        v_dados_antigos := 'ID: ' || :OLD.idhistorico || ', Data Criação: ' || TO_CHAR(:OLD.data_criacao, 'DD/MM/YYYY') ||
                           ', Consumo (kWh): ' || :OLD.valor_consumo_kwh || 
                           ', Intensidade Carbono: ' || :OLD.intensidade_carbono || 
                           ', Custo Estimado: ' || :OLD.custo_energia_estimado || 
                           ', Regulacao Energia ID: ' || :OLD.regulacao_energia_idregulacao;
        v_dados_novos := 'ID: ' || :NEW.idhistorico || ', Data Criação: ' || TO_CHAR(:NEW.data_criacao, 'DD/MM/YYYY') ||
                         ', Consumo (kWh): ' || :NEW.valor_consumo_kwh || 
                         ', Intensidade Carbono: ' || :NEW.intensidade_carbono || 
                         ', Custo Estimado: ' || :NEW.custo_energia_estimado || 
                         ', Regulacao Energia ID: ' || :NEW.regulacao_energia_idregulacao;
        INSERT INTO Auditoria (nome_tabela, operacao, usuario, data_operacao, dados_antigos, dados_novos)
        VALUES ('historico', 'UPDATE', v_usuario, SYSDATE, v_dados_antigos, v_dados_novos);
    END IF;
END;

CREATE OR REPLACE TRIGGER trig_auditoria_historico_sensor
AFTER INSERT OR UPDATE OR DELETE ON historico_sensor
FOR EACH ROW
DECLARE
    v_usuario VARCHAR2(50) := USER;
    v_dados_antigos VARCHAR2(4000);
    v_dados_novos VARCHAR2(4000);
BEGIN
    -- Concatena os dados antigos, se disponíveis
    IF INSERTING THEN
        v_dados_antigos := NULL; -- Inserindo novos valores, por isso NÃO TEM DADOS ANTIGOS
        v_dados_novos := 'Historico ID: ' || :NEW.historico_idhistorico || 
                         ', Sensor ID: ' || :NEW.sensor_idsensor;
        INSERT INTO Auditoria (nome_tabela, operacao, usuario, data_operacao, dados_antigos, dados_novos)
        VALUES ('historico_sensor', 'INSERT', v_usuario, SYSDATE, v_dados_antigos, v_dados_novos);
        
    ELSIF DELETING THEN
        v_dados_antigos := 'Historico ID: ' || :OLD.historico_idhistorico || 
                           ', Sensor ID: ' || :OLD.sensor_idsensor;
        v_dados_novos := NULL; -- os dados são DELETADOS, por isso NÃO TEM novos valores
        INSERT INTO Auditoria (nome_tabela, operacao, usuario, data_operacao, dados_antigos, dados_novos)
        VALUES ('historico_sensor', 'DELETE', v_usuario, SYSDATE, v_dados_antigos, v_dados_novos);
        
    ELSIF UPDATING THEN
        v_dados_antigos := 'Historico ID: ' || :OLD.historico_idhistorico || 
                           ', Sensor ID: ' || :OLD.sensor_idsensor;
        v_dados_novos := 'Historico ID: ' || :NEW.historico_idhistorico || 
                         ', Sensor ID: ' || :NEW.sensor_idsensor;
        INSERT INTO Auditoria (nome_tabela, operacao, usuario, data_operacao, dados_antigos, dados_novos)
        VALUES ('historico_sensor', 'UPDATE', v_usuario, SYSDATE, v_dados_antigos, v_dados_novos);
    END IF;
END;
------------------------------------------------ FUNCTIONS DE CALCULO E VALIDACAO (Empacotadas) ------------------------------------------------
/* PACOTE DE FUNCOES DE VALIDACAO E CALCULO */
CREATE OR REPLACE PACKAGE pkg_validacao_calculo IS
    FUNCTION calcular_consumo(potencia_nominal NUMBER, tempo_operacao NUMBER) RETURN NUMBER;

    FUNCTION calcular_custo_energia(tarifa NUMBER, consumo NUMBER, tarifa_adicional NUMBER) RETURN NUMBER;

    FUNCTION validar_email(p_email VARCHAR2) RETURN BOOLEAN;
END pkg_validacao_calculo;

/* CORPO pkg_validacao_calculo */
CREATE OR REPLACE PACKAGE BODY pkg_validacao_calculo IS
    FUNCTION calcular_consumo(potencia_nominal NUMBER, tempo_operacao NUMBER) RETURN NUMBER IS
        consumo NUMBER;
    BEGIN
        consumo := potencia_nominal * tempo_operacao;
        RETURN consumo;
    EXCEPTION
        WHEN VALUE_ERROR THEN
            DBMS_OUTPUT.PUT_LINE('Erro: A potencia nominal ou o tempo de operacao sao invalidos.');
            RETURN NULL;
        WHEN ZERO_DIVIDE THEN
            DBMS_OUTPUT.PUT_LINE('Erro: Divisao por zero detectada.');
            RETURN NULL;
        WHEN OTHERS THEN
            DBMS_OUTPUT.PUT_LINE('Erro desconhecido: ' || SQLERRM);
            RETURN NULL;
    END calcular_consumo;

    FUNCTION calcular_custo_energia(tarifa NUMBER, consumo NUMBER, tarifa_adicional NUMBER) RETURN NUMBER IS
        custo NUMBER;
    BEGIN
        custo := (tarifa * consumo) + tarifa_adicional;
        RETURN custo;
    EXCEPTION
        WHEN VALUE_ERROR THEN
            DBMS_OUTPUT.PUT_LINE('Erro: Tarifa, consumo ou tarifa adicional invalidos.');
            RETURN NULL;
        WHEN INVALID_NUMBER THEN
            DBMS_OUTPUT.PUT_LINE('Erro: Um dos parametros contém um valor nao numerico.');
            RETURN NULL;
        WHEN OTHERS THEN
            DBMS_OUTPUT.PUT_LINE('Erro desconhecido: ' || SQLERRM);
            RETURN NULL;
    END calcular_custo_energia;

    FUNCTION validar_email(p_email VARCHAR2) RETURN BOOLEAN IS
        v_regex VARCHAR2(100) := '^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$';
        v_count NUMBER;
    BEGIN
        SELECT COUNT(*) INTO v_count
        FROM dual
        WHERE REGEXP_LIKE(p_email, v_regex);

        IF v_count > 0 THEN
            RETURN TRUE;
        ELSE
            RETURN FALSE;
        END IF;
    EXCEPTION
        WHEN VALUE_ERROR THEN
            DBMS_OUTPUT.PUT_LINE('Erro: O e-mail nao pode ser nulo.');
            RETURN FALSE;
        WHEN NO_DATA_FOUND THEN
            DBMS_OUTPUT.PUT_LINE('Erro: Nao foi encontrado nenhum dado correspondente ao e-mail.');
            RETURN FALSE;
        WHEN OTHERS THEN
            DBMS_OUTPUT.PUT_LINE('Erro desconhecido: ' || SQLERRM);
            RETURN FALSE;
    END validar_email;
END pkg_validacao_calculo;

------------------------------------------------ PROCEDURES DE INSERTS (Empacotamento) ------------------------------------------------
/* PACOTE DE PROCEDURES DE INSERTS */
CREATE OR REPLACE PACKAGE pkg_insercao_dados IS
    PROCEDURE inserir_empresa(p_idempresa IN NUMBER, p_nome IN VARCHAR2, p_email IN VARCHAR2, p_cnpj IN VARCHAR2, p_segmento IN VARCHAR2, p_data_fundacao IN DATE);
    
    PROCEDURE inserir_endereco(p_idendereco IN NUMBER, p_logadouro IN VARCHAR2, p_cidade IN VARCHAR2, p_estado IN VARCHAR2, p_cep IN VARCHAR2, p_pais IN VARCHAR2);
    
    PROCEDURE inserir_filial(p_idfilial IN NUMBER, p_nome IN VARCHAR2, p_tipo IN VARCHAR2, p_cnpj_filial IN VARCHAR2, p_area_operacional IN VARCHAR2, p_empresa_idempresa IN NUMBER, p_endereco_idendereco IN NUMBER);
    
    PROCEDURE inserir_regulacao_energia(p_idregulacao IN NUMBER, p_tarifa_kwh IN NUMBER, p_nome_bandeira IN VARCHAR2, p_tarifa_adicional_bandeira IN NUMBER, p_data_atualizacao IN DATE);
    
    PROCEDURE inserir_dispositivo(p_iddispositivo IN NUMBER, p_nome IN VARCHAR2, p_tipo IN VARCHAR2, p_status IN VARCHAR2, p_data_instalacao IN DATE, p_filial_idfilial IN NUMBER, p_potencia_nominal IN NUMBER);
    
    PROCEDURE inserir_sensor(p_idsensor IN NUMBER, p_tipo IN VARCHAR2, p_descricao IN VARCHAR2, p_unidade IN VARCHAR2, p_valor_atual IN NUMBER, p_tempo_operacao IN NUMBER);
    
    PROCEDURE inserir_alerta(p_idalerta IN NUMBER, p_descricao IN VARCHAR2, p_severidade IN VARCHAR2, p_data_alerta IN DATE, p_sensor_idsensor IN NUMBER);
    
    PROCEDURE inserir_dispositivo_sensor(p_dispositivo_iddispositivo IN NUMBER, p_sensor_idsensor IN NUMBER);
    
    PROCEDURE inserir_historico(p_idhistorico IN NUMBER, p_data_criacao IN DATE, p_intensidade_carbono IN NUMBER, p_sensor_idsensor IN NUMBER, p_regulacao_energia_idregulacao IN NUMBER);
    
    PROCEDURE inserir_historico_sensor(p_historico_idhistorico IN NUMBER, p_sensor_idsensor IN NUMBER);
END pkg_insercao_dados;

/* CORPO pkg_insercao_dados */
CREATE OR REPLACE PACKAGE BODY pkg_insercao_dados IS
    PROCEDURE inserir_empresa(
        p_idempresa IN NUMBER,
        p_nome IN VARCHAR2,
        p_email IN VARCHAR2,
        p_cnpj IN VARCHAR2,
        p_segmento IN VARCHAR2,
        p_data_fundacao IN DATE
    ) IS
        v_email_valido BOOLEAN;
    BEGIN
        -- Valida o e-mail
        v_email_valido := pkg_validacao_calculo.validar_email(p_email);
    
        IF NOT v_email_valido THEN
            DBMS_OUTPUT.PUT_LINE('Erro: E-mail inválido.');
            RETURN; -- Não prossegue com a inserção
        END IF;
    
        -- Se o e-mail for válido, insere a empresa
        INSERT INTO empresa (
            idempresa, nome, email, cnpj, segmento, data_fundacao
        ) VALUES (
            p_idempresa, p_nome, p_email, p_cnpj, p_segmento, p_data_fundacao
        );
    
        COMMIT;
    EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
            DBMS_OUTPUT.PUT_LINE('Erro ao inserir empresa: Já existe uma empresa com este ID.');
        WHEN VALUE_ERROR THEN
            DBMS_OUTPUT.PUT_LINE('Erro ao inserir empresa: Verifique se os tipos de dados estão corretos.');
        WHEN OTHERS THEN
            DBMS_OUTPUT.PUT_LINE('Erro ao inserir empresa: ' || SQLERRM);
            ROLLBACK;
    END;
    
    PROCEDURE inserir_endereco(
        p_idendereco IN NUMBER,
        p_logadouro IN VARCHAR2,
        p_cidade IN VARCHAR2,
        p_estado IN VARCHAR2,
        p_cep IN VARCHAR2,
        p_pais IN VARCHAR2
    ) IS
    BEGIN
        INSERT INTO endereco (
            idendereco, logadouro, cidade, estado, cep, pais
        ) VALUES (
            p_idendereco, p_logadouro, p_cidade, p_estado, p_cep, p_pais
        );
        COMMIT;
    EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
            DBMS_OUTPUT.PUT_LINE('Erro ao inserir endereço: Já existe um endereço com este ID.');
        WHEN VALUE_ERROR THEN
            DBMS_OUTPUT.PUT_LINE('Erro ao inserir endereço: Verifique os tipos de dados.');
        WHEN OTHERS THEN
            DBMS_OUTPUT.PUT_LINE('Erro ao inserir endereço: ' || SQLERRM);
            ROLLBACK;
    END;
    
    PROCEDURE inserir_filial(
        p_idfilial IN NUMBER,
        p_nome IN VARCHAR2,
        p_tipo IN VARCHAR2,
        p_cnpj_filial IN VARCHAR2,
        p_area_operacional IN VARCHAR2,
        p_empresa_idempresa IN NUMBER,
        p_endereco_idendereco IN NUMBER
    ) IS
    BEGIN
        INSERT INTO filial (
            idfilial, nome, tipo, cnpj_filial, area_operacional, empresa_idempresa, endereco_idendereco
        ) VALUES (
            p_idfilial, p_nome, p_tipo, p_cnpj_filial, p_area_operacional, p_empresa_idempresa, p_endereco_idendereco
        );
        COMMIT;
    EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
            DBMS_OUTPUT.PUT_LINE('Erro ao inserir filial: Já existe uma filial com este ID.');
        WHEN VALUE_ERROR THEN
            DBMS_OUTPUT.PUT_LINE('Erro ao inserir filial: Verifique os tipos de dados.');
        WHEN OTHERS THEN
            DBMS_OUTPUT.PUT_LINE('Erro ao inserir filial: ' || SQLERRM);
            ROLLBACK;
    END;
    
    PROCEDURE inserir_regulacao_energia(
        p_idregulacao IN NUMBER,
        p_tarifa_kwh IN NUMBER,
        p_nome_bandeira IN VARCHAR2,
        p_tarifa_adicional_bandeira IN NUMBER,
        p_data_atualizacao IN DATE
    ) IS
    BEGIN
        INSERT INTO regulacao_energia (
            idregulacao, tarifa_kwh, nome_bandeira, tarifa_adicional_bandeira, data_atualizacao
        ) VALUES (
            p_idregulacao, p_tarifa_kwh, p_nome_bandeira, p_tarifa_adicional_bandeira, p_data_atualizacao
        );
        COMMIT;
    EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
            DBMS_OUTPUT.PUT_LINE('Erro ao inserir regulação de energia: Já existe uma regulação com este ID.');
        WHEN VALUE_ERROR THEN
            DBMS_OUTPUT.PUT_LINE('Erro ao inserir regulação de energia: Verifique os tipos de dados.');
        WHEN OTHERS THEN
            DBMS_OUTPUT.PUT_LINE('Erro ao inserir regulação de energia: ' || SQLERRM);
            ROLLBACK;
    END;
    
    PROCEDURE inserir_dispositivo(
        p_iddispositivo IN NUMBER,
        p_nome IN VARCHAR2,
        p_tipo IN VARCHAR2,
        p_status IN VARCHAR2,
        p_data_instalacao IN DATE,
        p_filial_idfilial IN NUMBER,
        p_potencia_nominal IN NUMBER
    ) IS
    BEGIN
        INSERT INTO dispositivo (
            iddispositivo, nome, tipo, status, data_instalacao, filial_idfilial, potencia_nominal
        ) VALUES (
            p_iddispositivo, p_nome, p_tipo, p_status, p_data_instalacao, p_filial_idfilial, p_potencia_nominal
        );
        COMMIT;
    EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
            DBMS_OUTPUT.PUT_LINE('Erro ao inserir dispositivo: Já existe um dispositivo com este ID.');
        WHEN VALUE_ERROR THEN
            DBMS_OUTPUT.PUT_LINE('Erro ao inserir dispositivo: Verifique os tipos de dados.');
        WHEN OTHERS THEN
            DBMS_OUTPUT.PUT_LINE('Erro ao inserir dispositivo: ' || SQLERRM);
            ROLLBACK;
    END;
    
    PROCEDURE inserir_sensor(
        p_idsensor IN NUMBER,
        p_tipo IN VARCHAR2,
        p_descricao IN VARCHAR2,
        p_unidade IN VARCHAR2,
        p_valor_atual IN NUMBER,
        p_tempo_operacao IN NUMBER
    ) IS
    BEGIN
        INSERT INTO sensor (
            idsensor, tipo, descricao, unidade, valor_atual, tempo_operacao
        ) VALUES (
            p_idsensor, p_tipo, p_descricao, p_unidade, p_valor_atual, p_tempo_operacao
        );
        COMMIT;
    EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
            DBMS_OUTPUT.PUT_LINE('Erro ao inserir sensor: Já existe um sensor com este ID.');
        WHEN VALUE_ERROR THEN
            DBMS_OUTPUT.PUT_LINE('Erro ao inserir sensor: Verifique os tipos de dados.');
        WHEN OTHERS THEN
            DBMS_OUTPUT.PUT_LINE('Erro ao inserir sensor: ' || SQLERRM);
            ROLLBACK;
    END;
    
    PROCEDURE inserir_alerta(
        p_idalerta IN NUMBER,
        p_descricao IN VARCHAR2,
        p_severidade IN VARCHAR2,
        p_data_alerta IN DATE,
        p_sensor_idsensor IN NUMBER
    ) IS
    BEGIN
        INSERT INTO alerta (
            idalerta, descricao, severidade, data_alerta, sensor_idsensor
        ) VALUES (
            p_idalerta, p_descricao, p_severidade, p_data_alerta, p_sensor_idsensor
        );
        COMMIT;
    EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
            DBMS_OUTPUT.PUT_LINE('Erro ao inserir alerta: Já existe um alerta com este ID.');
        WHEN VALUE_ERROR THEN
            DBMS_OUTPUT.PUT_LINE('Erro ao inserir alerta: Verifique os tipos de dados.');
        WHEN OTHERS THEN
            DBMS_OUTPUT.PUT_LINE('Erro ao inserir alerta: ' || SQLERRM);
            ROLLBACK;
    END;
    
    
    PROCEDURE inserir_dispositivo_sensor(
        p_dispositivo_iddispositivo IN NUMBER,
        p_sensor_idsensor IN NUMBER
    ) IS
    BEGIN
        INSERT INTO dispositivo_sensor (
            dispositivo_iddispositivo, sensor_idsensor
        ) VALUES (
            p_dispositivo_iddispositivo, p_sensor_idsensor
        );
        COMMIT;
    EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
            DBMS_OUTPUT.PUT_LINE('Erro ao inserir dispositivo_sensor: Já existe esse relacionamento.');
        WHEN VALUE_ERROR THEN
            DBMS_OUTPUT.PUT_LINE('Erro ao inserir dispositivo_sensor: Verifique os tipos de dados.');
        WHEN OTHERS THEN
            DBMS_OUTPUT.PUT_LINE('Erro ao inserir dispositivo_sensor: ' || SQLERRM);
            ROLLBACK;
    END;
    
    PROCEDURE inserir_historico(
        p_idhistorico IN NUMBER,
        p_data_criacao IN DATE,
        p_intensidade_carbono IN NUMBER,  
        p_sensor_idsensor IN NUMBER,
        p_regulacao_energia_idregulacao IN NUMBER  
    ) IS
        v_valor_consumo_kwh NUMBER(10, 2);
        v_custo_energia_estimado NUMBER(10, 2);
        v_tarifa_kwh NUMBER(10, 2);
        v_tarifa_adicional NUMBER(10, 2);
        v_potencia_nominal NUMBER(10, 2);
        v_tempo_operacao NUMBER(10, 2);
    BEGIN
        -- Consulta para calcular o consumo (kWh)
        SELECT d.potencia_nominal, s.tempo_operacao
        INTO v_potencia_nominal, v_tempo_operacao
        FROM dispositivo d
        JOIN dispositivo_sensor ds ON ds.dispositivo_iddispositivo = d.iddispositivo  
        JOIN sensor s ON s.idsensor = ds.sensor_idsensor  
        WHERE s.idsensor = p_sensor_idsensor;  
    
        -- Calcula o consumo de energia
        v_valor_consumo_kwh := pkg_validacao_calculo.calcular_consumo(v_potencia_nominal, v_tempo_operacao);
    
        -- Consulta para calcular o custo de energia
        SELECT r.tarifa_kwh, r.tarifa_adicional_bandeira
        INTO v_tarifa_kwh, v_tarifa_adicional
        FROM regulacao_energia r
        WHERE r.idregulacao = p_regulacao_energia_idregulacao; 
    
        -- Calcula o custo de energia
        v_custo_energia_estimado := pkg_validacao_calculo.calcular_custo_energia(v_tarifa_kwh, v_valor_consumo_kwh, v_tarifa_adicional);
    
        -- Insere o histórico calculado
        INSERT INTO historico (
            idhistorico, data_criacao, valor_consumo_kwh, intensidade_carbono, custo_energia_estimado, regulacao_energia_idregulacao
        ) VALUES (
            p_idhistorico, p_data_criacao, v_valor_consumo_kwh, p_intensidade_carbono, v_custo_energia_estimado, p_regulacao_energia_idregulacao
        );
    
        COMMIT;
    EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
            DBMS_OUTPUT.PUT_LINE('Erro ao inserir histórico: Já existe um histórico com este ID.');
        WHEN VALUE_ERROR THEN
            DBMS_OUTPUT.PUT_LINE('Erro ao inserir histórico: Verifique os tipos de dados.');
        WHEN OTHERS THEN
            DBMS_OUTPUT.PUT_LINE('Erro ao inserir histórico: ' || SQLERRM);
            ROLLBACK;
    END;
    
    
    
    PROCEDURE inserir_historico_sensor(
        p_historico_idhistorico IN NUMBER,
        p_sensor_idsensor IN NUMBER
    ) IS
    BEGIN
        INSERT INTO historico_sensor (
            historico_idhistorico, sensor_idsensor
        ) VALUES (
            p_historico_idhistorico, p_sensor_idsensor
        );
        COMMIT;
    EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
            DBMS_OUTPUT.PUT_LINE('Erro ao inserir historico_sensor: Já existe esse relacionamento.');
        WHEN VALUE_ERROR THEN
            DBMS_OUTPUT.PUT_LINE('Erro ao inserir historico_sensor: Verifique os tipos de dados.');
        WHEN OTHERS THEN
            DBMS_OUTPUT.PUT_LINE('Erro ao inserir historico_sensor: ' || SQLERRM);
            ROLLBACK;
    END;
        
END pkg_insercao_dados;


--------------------------------------
SET SERVEROUTPUT ON;
SET VERIFY OFF;
--------------------------------------

BEGIN
     -- Inserindo empresas com a procedure
    pkg_insercao_dados.inserir_empresa(1, 'Ford', 'contato@ford.com', '33030642000122', 'Automóveis', TO_DATE('1903-06-16', 'YYYY-MM-DD'));
    pkg_insercao_dados.inserir_empresa(2, 'Google', 'contato@google.com', '12345678000123', 'Tecnologia', TO_DATE('1998-09-04', 'YYYY-MM-DD'));
    pkg_insercao_dados.inserir_empresa(3, 'Microsoft', 'contato@microsoft.com', '12345678000124', 'Tecnologia', TO_DATE('1975-04-04', 'YYYY-MM-DD'));
    pkg_insercao_dados.inserir_empresa(4, 'Apple', 'contato@apple.com', '12345678000125', 'Tecnologia', TO_DATE('1976-04-01', 'YYYY-MM-DD'));
    pkg_insercao_dados.inserir_empresa(5, 'Amazon', 'contato@amazon.com', '12345678000126', 'E-commerce', TO_DATE('1994-07-05', 'YYYY-MM-DD'));
    pkg_insercao_dados.inserir_empresa(6, 'Tesla', 'contato@tesla.com', '12345678000127', 'Automóveis', TO_DATE('2003-07-01', 'YYYY-MM-DD'));
    pkg_insercao_dados.inserir_empresa(7, 'Coca-Cola', 'contato@cokecola.com', '12345678000128', 'Bebidas', TO_DATE('1892-05-08', 'YYYY-MM-DD'));
    pkg_insercao_dados.inserir_empresa(8, 'Nike', 'contato@nike.com', '12345678000129', 'Moda', TO_DATE('1964-01-25', 'YYYY-MM-DD'));
    pkg_insercao_dados.inserir_empresa(9, 'Samsung', 'contato@samsung.com', '12345678000130', 'Eletrônicos', TO_DATE('1938-03-01', 'YYYY-MM-DD'));
    pkg_insercao_dados.inserir_empresa(10, 'Intel', 'contato@intel.com', '12345678000131', 'Tecnologia', TO_DATE('1968-07-18', 'YYYY-MM-DD'));

    -- Inserindo enderecos com a procedure
    pkg_insercao_dados.inserir_endereco(11, 'Avenida Paulista, 1000', 'Sao Paulo', 'SP', '01310-100', 'Brasil');
    pkg_insercao_dados.inserir_endereco(12, 'Rua Rio de Janeiro, 50', 'Rio de Janeiro', 'RJ', '20010-000', 'Brasil');
    pkg_insercao_dados.inserir_endereco(13, 'Avenida Presidente Vargas, 200', 'Rio de Janeiro', 'RJ', '20210-002', 'Brasil');
    pkg_insercao_dados.inserir_endereco(14, 'Rua das Palmeiras, 123', 'Sao Paulo', 'SP', '05428-000', 'Brasil');
    pkg_insercao_dados.inserir_endereco(15, 'Rua XV de Novembro, 987', 'Curitiba', 'PR', '80010-000', 'Brasil');
    pkg_insercao_dados.inserir_endereco(16, 'Rua dos Três Irmaos, 456', 'Belo Horizonte', 'MG', '30120-030', 'Brasil');
    pkg_insercao_dados.inserir_endereco(17, 'Avenida Brigadeiro Faria Lima, 500', 'São Paulo', 'SP', '01452-000', 'Brasil');
    pkg_insercao_dados.inserir_endereco(18, 'Rua Sete de Setembro, 300', 'Salvador', 'BA', '40010-001', 'Brasil');
    pkg_insercao_dados.inserir_endereco(19, 'Rua 15 de Novembro, 850', 'Brasília', 'DF', '70000-000', 'Brasil');
    pkg_insercao_dados.inserir_endereco(20, 'Avenida Centenario, 1200', 'Fortaleza', 'CE', '60060-030', 'Brasil');

    -- Inserindo filiais com a procedure
    pkg_insercao_dados.inserir_filial(21, 'Ford Car Motors', 'Vendas e Pos-Venda', '00.123.456/0001-90', 'Atendimento ao Cliente', 1, 11);
    pkg_insercao_dados.inserir_filial(22, 'Google Brasil', 'Solucoes Tecnologicas', '00.234.567/0001-90', 'Inovacao Digital', 2, 12);
    pkg_insercao_dados.inserir_filial(23, 'Microsoft Hub', 'Consultoria TI', '00.345.678/0001-90', 'Solucoes e Capacitacao', 3, 13);
    pkg_insercao_dados.inserir_filial(24, 'Apple Rio', 'Vendas de Produtos', '00.456.789/0001-90', 'Suporte Apple Premium', 4, 14);
    pkg_insercao_dados.inserir_filial(25, 'Amazon Now', 'Logistica e E-commerce', '00.567.890/0001-90', 'Atendimento Digital', 5, 15);
    pkg_insercao_dados.inserir_filial(26, 'Tesla Mobilidade', 'Vendas de Veiculos Eletricos', '00.678.901/0001-90', 'Solucoes Sustentaveis', 6, 16);
    pkg_insercao_dados.inserir_filial(27, 'Coca-Cola Brasil', 'Producao e Bebidas', '00.789.012/0001-90', 'Distribuicao de Bebidas', 7, 17);
    pkg_insercao_dados.inserir_filial(28, 'Nike Brasil', 'Vendas e Suporte', '00.890.123/0001-90', 'Equipamentos Esportivos', 8, 18);
    pkg_insercao_dados.inserir_filial(29, 'Samsung Inovacao', 'Vendas de Eletronicos', '00.901.234/0001-90', 'Suporte a Eletronicos', 9, 19);
    pkg_insercao_dados.inserir_filial(30, 'Intel Brasil', 'Pesquisa e Desenvolvimento', '00.012.345/0001-90', 'Microprocessadores', 10, 20);

    -- Inserindo regulacoes de energia com a procedure
    pkg_insercao_dados.inserir_regulacao_energia(41, TO_NUMBER('1.50', '9999999.99'), 'Bandeira Verde', TO_NUMBER('1.00', '9999999.99'), TO_DATE('2024-01-01', 'YYYY-MM-DD'));
    pkg_insercao_dados.inserir_regulacao_energia(42, TO_NUMBER('0.60', '9999999.99'), 'Bandeira Laranja', TO_NUMBER('0.10', '9999999.99'), TO_DATE('2024-01-01', 'YYYY-MM-DD'));
    pkg_insercao_dados.inserir_regulacao_energia(43, TO_NUMBER('0.70', '9999999.99'), 'Bandeira Amarela', TO_NUMBER('0.20', '9999999.99'), TO_DATE('2024-01-01', 'YYYY-MM-DD'));
    pkg_insercao_dados.inserir_regulacao_energia(44, TO_NUMBER('0.85', '9999999.99'), 'Bandeira Vermelha 1', TO_NUMBER('0.30', '9999999.99'), TO_DATE('2024-01-01', 'YYYY-MM-DD'));
    pkg_insercao_dados.inserir_regulacao_energia(45, TO_NUMBER('1.00', '9999999.99'), 'Bandeira Vermelha 2', TO_NUMBER('0.40', '9999999.99'), TO_DATE('2024-01-01', 'YYYY-MM-DD'));
    pkg_insercao_dados.inserir_regulacao_energia(46, TO_NUMBER('0.45', '9999999.99'), 'Bandeira Azul', TO_NUMBER('0.22', '9999999.99'), TO_DATE('2024-01-01', 'YYYY-MM-DD'));
    pkg_insercao_dados.inserir_regulacao_energia(47, TO_NUMBER('0.40', '9999999.99'), 'Bandeira Roxa', TO_NUMBER('0.48', '9999999.99'), TO_DATE('2024-01-01', 'YYYY-MM-DD'));
    pkg_insercao_dados.inserir_regulacao_energia(48, TO_NUMBER('0.75', '9999999.99'), 'Bandeira Amarela Claro', TO_NUMBER('0.15', '9999999.99'), TO_DATE('2024-01-01', 'YYYY-MM-DD'));
    pkg_insercao_dados.inserir_regulacao_energia(49, TO_NUMBER('0.55', '9999999.99'), 'Bandeira Cinza', TO_NUMBER('0.05', '9999999.99'), TO_DATE('2024-01-01', 'YYYY-MM-DD'));
    pkg_insercao_dados.inserir_regulacao_energia(50, TO_NUMBER('1.10', '9999999.99'), 'Bandeira Marrom', TO_NUMBER('0.50', '9999999.99'), TO_DATE('2024-01-01', 'YYYY-MM-DD'));

    -- Inserindo dispositivo com a procedure
    pkg_insercao_dados.inserir_dispositivo(51, 'Computador Dell XPS', 'Computador', 'Ativo', TO_DATE('2023-01-15', 'YYYY-MM-DD'), 21, 500);
    pkg_insercao_dados.inserir_dispositivo(52, 'Servidor HP ProLiant', 'Servidor', 'Ativo', TO_DATE('2022-11-20', 'YYYY-MM-DD'), 22, 1000);
    pkg_insercao_dados.inserir_dispositivo(53, 'Roteador Cisco XR', 'Roteador', 'Ativo', TO_DATE('2024-03-10', 'YYYY-MM-DD'), 23, 200);
    pkg_insercao_dados.inserir_dispositivo(54, 'Switch Netgear', 'Switch', 'Ativo', TO_DATE('2023-05-05', 'YYYY-MM-DD'), 24, 150);
    pkg_insercao_dados.inserir_dispositivo(55, 'Impressora HP LaserJet', 'Impressora', 'Ativo', TO_DATE('2023-02-10', 'YYYY-MM-DD'), 25, 300);
    pkg_insercao_dados.inserir_dispositivo(56, 'Notebook Lenovo ThinkPad', 'Computador', 'Ativo', TO_DATE('2024-04-15', 'YYYY-MM-DD'), 26, 600);
    pkg_insercao_dados.inserir_dispositivo(57, 'Servidor Dell PowerEdge', 'Servidor', 'Manutencao', TO_DATE('2021-07-12', 'YYYY-MM-DD'), 27, 1200);
    pkg_insercao_dados.inserir_dispositivo(58, 'Monitor LG UltraWide', 'Monitor', 'Ativo', TO_DATE('2023-09-01', 'YYYY-MM-DD'), 28, 50);
    pkg_insercao_dados.inserir_dispositivo(59, 'Scanner Epson', 'Scanner', 'Ativo', TO_DATE('2024-01-20', 'YYYY-MM-DD'), 29, 80);
    pkg_insercao_dados.inserir_dispositivo(60, 'Projeto Beamer Epson', 'Projetor', 'Ativo', TO_DATE('2023-08-15', 'YYYY-MM-DD'), 30, 100);

    -- Inserindo sensores com a procedure
    pkg_insercao_dados.inserir_sensor(71, 'Sensor de Corrente', 'Monitoramento de Corrente Elétrica em Servidores', 'Ampere', TO_NUMBER('5.2', '9999999.99'), 7200);
    pkg_insercao_dados.inserir_sensor(72, 'Sensor de Tensao', 'Medicao de Tensao em Data Center', 'Volt', TO_NUMBER('230.5', '9999999.99'), 5400);
    pkg_insercao_dados.inserir_sensor(73, 'Sensor de Gas CO2', 'Deteccao de Emissoes de CO2 em Sala de Maquinas', 'PPM', TO_NUMBER('450.7', '9999999.99'), 3600);
    pkg_insercao_dados.inserir_sensor(74, 'Sensor de Potencia', 'Medicao de Consumo de Energia de Computadores', 'Watt', TO_NUMBER('150.3', '9999999.99'), 4800);
    pkg_insercao_dados.inserir_sensor(75, 'Sensor de Temperatura', 'Monitoramento de Temperatura em Transformadores', 'Celsius', TO_NUMBER('35.5', '9999999.99'), 6000);
    pkg_insercao_dados.inserir_sensor(76, 'Sensor de Corrente de Alta Precisao', 'Monitoramento de Picos de Corrente em Quadros Eletricos', 'Ampere', TO_NUMBER('15.6', '9999999.99'), 10800);
    pkg_insercao_dados.inserir_sensor(77, 'Sensor de Gas Metano', 'Monitoramento de Vazamento de CH4 em Areas Industriais', 'PPM', TO_NUMBER('120.8', '9999999.99'), 4000);
    pkg_insercao_dados.inserir_sensor(78, 'Sensor de Fator de Potencia', 'Analise de Fator de Potencia em Equipamentos', 'Unidade', TO_NUMBER('0.95', '9999999.99'), 9000);
    pkg_insercao_dados.inserir_sensor(79, 'Sensor de Emissao de Carbono', 'Monitoramento de Emissoes de Carbono de Geradores', 'PPM', TO_NUMBER('600.2', '9999999.99'), 7000);
    pkg_insercao_dados.inserir_sensor(80, 'Sensor de Temperatura Externa', 'Controle de Temperatura Ambiental em Subestacoes', 'Celsius', TO_NUMBER('29.4', '9999999.99'), 8500);

    -- Inserindo alertas com a procedure
    pkg_insercao_dados.inserir_alerta(81, 'Alerta de Corrente Alta em Servidores', 'Crítico', TO_DATE('2024-11-13', 'YYYY-MM-DD'), 71);
    pkg_insercao_dados.inserir_alerta(82, 'Alerta de Tensao Acima do Normal', 'Alto', TO_DATE('2024-11-13', 'YYYY-MM-DD'), 72);
    pkg_insercao_dados.inserir_alerta(83, 'Emissao de CO2 Acima do Limite', 'Moderado', TO_DATE('2024-11-13', 'YYYY-MM-DD'), 73);
    pkg_insercao_dados.inserir_alerta(84, 'Consumo de Energia Elevado', 'Alto', TO_DATE('2024-11-13', 'YYYY-MM-DD'), 74);
    pkg_insercao_dados.inserir_alerta(85, 'Temperatura Alta em Transformador', 'Critico', TO_DATE('2024-11-13', 'YYYY-MM-DD'), 75);
    pkg_insercao_dados.inserir_alerta(86, 'Pico de Corrente Detectado', 'Critico', TO_DATE('2024-11-13', 'YYYY-MM-DD'), 76);
    pkg_insercao_dados.inserir_alerta(87, 'Vazamento de Metano Identificado', 'Alto', TO_DATE('2024-11-13', 'YYYY-MM-DD'), 77);
    pkg_insercao_dados.inserir_alerta(88, 'Fator de Potencia Baixo', 'Baixo', TO_DATE('2024-11-13', 'YYYY-MM-DD'), 78);
    pkg_insercao_dados.inserir_alerta(89, 'Emissao de Carbono Crítica', 'Crítico', TO_DATE('2024-11-13', 'YYYY-MM-DD'), 79);
    pkg_insercao_dados.inserir_alerta(90, 'Temperatura Externa Elevada', 'Moderado', TO_DATE('2024-11-13', 'YYYY-MM-DD'), 80);

    -- Inserindo relacao entre dispositivos e sensor com a procedure
    pkg_insercao_dados.inserir_dispositivo_sensor(51, 71); 
    pkg_insercao_dados.inserir_dispositivo_sensor(52, 72); 
    pkg_insercao_dados.inserir_dispositivo_sensor(53, 73); 
    pkg_insercao_dados.inserir_dispositivo_sensor(54, 74);
    pkg_insercao_dados.inserir_dispositivo_sensor(55, 75); 
    pkg_insercao_dados.inserir_dispositivo_sensor(56, 76); 
    pkg_insercao_dados.inserir_dispositivo_sensor(57, 77);
    pkg_insercao_dados.inserir_dispositivo_sensor(58, 78);
    pkg_insercao_dados.inserir_dispositivo_sensor(59, 79);
    pkg_insercao_dados.inserir_dispositivo_sensor(60, 80);

    -- Inserindo historico com a procedure
    pkg_insercao_dados.inserir_historico(91, TO_DATE('2024-11-01', 'YYYY-MM-DD'), TO_NUMBER('0.5', '9999999.99'), 71, 41);
    pkg_insercao_dados.inserir_historico(92, TO_DATE('2024-11-02', 'YYYY-MM-DD'), TO_NUMBER('0.6', '9999999.99'), 72, 42);
    pkg_insercao_dados.inserir_historico(93, TO_DATE('2024-11-03', 'YYYY-MM-DD'), TO_NUMBER('0.7', '9999999.99'), 73, 43);
    pkg_insercao_dados.inserir_historico(94, TO_DATE('2024-11-04', 'YYYY-MM-DD'), TO_NUMBER('0.8', '9999999.99'), 74, 44);
    pkg_insercao_dados.inserir_historico(95, TO_DATE('2024-11-05', 'YYYY-MM-DD'), TO_NUMBER('0.9', '9999999.99'), 75, 45);
    pkg_insercao_dados.inserir_historico(96, TO_DATE('2024-11-06', 'YYYY-MM-DD'), TO_NUMBER('1.0', '9999999.99'), 76, 46);
    pkg_insercao_dados.inserir_historico(97, TO_DATE('2024-11-07', 'YYYY-MM-DD'), TO_NUMBER('1.1', '9999999.99'), 77, 47);
    pkg_insercao_dados.inserir_historico(98, TO_DATE('2024-11-08', 'YYYY-MM-DD'), TO_NUMBER('0.5', '9999999.99'), 78, 48);
    pkg_insercao_dados.inserir_historico(99, TO_DATE('2024-11-09', 'YYYY-MM-DD'), TO_NUMBER('0.6', '9999999.99'), 71, 49);
    pkg_insercao_dados.inserir_historico(100, TO_DATE('2024-11-10', 'YYYY-MM-DD'), TO_NUMBER('0.7', '9999999.99'), 72, 50);

    -- Inserindo relacao entre historico e sensor com a procedure
    pkg_insercao_dados.inserir_historico_sensor(91, 71);
    pkg_insercao_dados.inserir_historico_sensor(92, 72);
    pkg_insercao_dados.inserir_historico_sensor(93, 73);
    pkg_insercao_dados.inserir_historico_sensor(94, 74);
    pkg_insercao_dados.inserir_historico_sensor(95, 75);
    pkg_insercao_dados.inserir_historico_sensor(96, 76);
    pkg_insercao_dados.inserir_historico_sensor(97, 77);
    pkg_insercao_dados.inserir_historico_sensor(98, 78);
    pkg_insercao_dados.inserir_historico_sensor(99, 71);
    pkg_insercao_dados.inserir_historico_sensor(100, 72);
END;


SELECT * FROM alerta;
SELECT * FROM dispositivo;
SELECT * FROM dispositivo_sensor;
SELECT * FROM empresa;
SELECT * FROM endereco;
SELECT * FROM filial;
SELECT * FROM historico;
SELECT * FROM historico_sensor;
SELECT * FROM regulacao_energia;
SELECT * FROM sensor;

select * from auditoria;

-------------------------------------------------

CREATE OR REPLACE PACKAGE pkg_exportacao IS
    PROCEDURE gerar_json (v_json OUT CLOB);
END pkg_exportacao;

------------------------------------------------
CREATE OR REPLACE PACKAGE BODY pkg_exportacao IS

  PROCEDURE gerar_json (v_json OUT CLOB) IS
    v_max_iddispositivo NUMBER;
    v_first_record BOOLEAN := TRUE; 

    CURSOR dispositivos_cursor IS
      SELECT 
          d.iddispositivo,
          d.nome,
          d.tipo,
          d.status,
          d.potencia_nominal,
          s.idsensor,
          s.tipo AS sensor_tipo,
          h.idhistorico,
          h.valor_consumo_kwh,
          h.intensidade_carbono,
          h.custo_energia_estimado,  
          re.nome_bandeira AS regulacao_nome_bandeira,
          re.tarifa_kwh,
          re.tarifa_adicional_bandeira
      FROM dispositivo d
      JOIN dispositivo_sensor ds ON d.iddispositivo = ds.dispositivo_iddispositivo
      JOIN sensor s ON ds.sensor_idsensor = s.idsensor
      JOIN historico_sensor hs ON s.idsensor = hs.sensor_idsensor
      JOIN historico h ON hs.historico_idhistorico = h.idhistorico
      JOIN regulacao_energia re ON h.regulacao_energia_idregulacao = re.idregulacao;

  BEGIN
    -- Inicializa o JSON
    v_json := '[';

    -- Loop para percorrer o cursor e gerar todos os registros no JSON
    FOR dispositivo IN dispositivos_cursor LOOP
      -- Se não for o primeiro registro, adiciona a vírgula
      IF NOT v_first_record THEN
        v_json := v_json || ',' || CHR(10);
      END IF;

      -- Adiciona o registro atual ao JSON com indentação
      v_json := v_json || '  {' || CHR(10) ||
                '    "dispositivo_id": ' || dispositivo.iddispositivo || ',' || CHR(10) ||
                '    "nome": "' || dispositivo.nome || '",' || CHR(10) ||
                '    "tipo": "' || dispositivo.tipo || '",' || CHR(10) ||
                '    "status": "' || dispositivo.status || '",' || CHR(10) ||
                '    "potencia_nominal": ' || REPLACE(TO_CHAR(dispositivo.potencia_nominal, 'FM9999999990.00'), ',', '.') || ',' || CHR(10) ||
                '    "sensor": {' || CHR(10) ||
                '      "sensor_id": ' || dispositivo.idsensor || ',' || CHR(10) ||
                '      "tipo": "' || dispositivo.sensor_tipo || '"' || CHR(10) ||
                '    },' || CHR(10) ||
                '    "historico": {' || CHR(10) ||
                '      "historico_id": ' || dispositivo.idhistorico || ',' || CHR(10) ||
                '      "valor_consumo_kwh": ' || REPLACE(TO_CHAR(dispositivo.valor_consumo_kwh, 'FM9999999990.00'), ',', '.') || ',' || CHR(10) ||
                '      "intensidade_carbono": ' || REPLACE(TO_CHAR(dispositivo.intensidade_carbono, 'FM9999999990.00'), ',', '.') || ',' || CHR(10) ||
                '      "custo_energia_estimado": ' || REPLACE(TO_CHAR(dispositivo.custo_energia_estimado, 'FM9999999990.00'), ',', '.') || CHR(10) ||
                '    },' || CHR(10) ||
                '    "regulacao": {' || CHR(10) ||
                '      "nome_bandeira": "' || dispositivo.regulacao_nome_bandeira || '",' || CHR(10) ||
                '      "tarifa_kwh": ' || REPLACE(TO_CHAR(dispositivo.tarifa_kwh, 'FM9999999990.00'), ',', '.') || ',' || CHR(10) ||
                '      "tarifa_adicional_bandeira": ' || REPLACE(TO_CHAR(dispositivo.tarifa_adicional_bandeira, 'FM9999999990.00'), ',', '.') || CHR(10) ||
                '    }' || CHR(10) ||
                '  }';

      v_first_record := FALSE;
    END LOOP;

    -- Finaliza o JSON
    v_json := v_json || CHR(10) || ']';

  EXCEPTION
    WHEN NO_DATA_FOUND THEN
      v_json := '[]'; 
    WHEN OTHERS THEN
      v_json := '{"erro": "Ocorreu um erro ao gerar o JSON."}';  
  END gerar_json;

END pkg_exportacao;

----------------------------------------------
SET SERVEROUTPUT ON;
SET VERIFY OFF;

-- Inicia o Spool para direcionar a saída para um arquivo .json
SPOOL C:\Users\pclal\Downloads\teste_bd\reports.json;
DECLARE
  v_json CLOB;  -- Variável para armazenar o JSON gerado
BEGIN
  -- Chama a procedure que gera o JSON
  pkg_exportacao.gerar_json(v_json);

  -- Exibe o JSON gerado 
  DBMS_OUTPUT.PUT_LINE(v_json);
EXCEPTION
  WHEN OTHERS THEN
    DBMS_OUTPUT.PUT_LINE('Erro ao gerar JSON: ' || SQLERRM);
END;


-- Finaliza o Spool, gravando o conteúdo no arquivo
SPOOL OFF;

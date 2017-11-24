CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE EXTENSION  IF NOT EXISTS pgcrypto;

CREATE TABLE LOCK (
    id bigint NOT NULL,
    uuid uuid ,
    PRIMARY KEY (id)
);

CREATE TABLE STORAGE (
    id bigint NOT NULL,
    UUID uuid DEFAULT gen_random_uuid() NOT NULL,
    status varchar(32),
    model varchar(32),
    PRIMARY KEY (id)
);

CREATE TABLE NB (
    id bigint NOT NULL,
    uuid uuid DEFAULT gen_random_uuid() NOT NULL,
    storage_uuid uuid DEFAULT gen_random_uuid() NOT NULL,
    storage_id bigint NOT NULL,
    NAME VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE TABLE NODE (
    ID INT,
    UUID UUID NOT NULL DEFAULT uuid_generate_v4(),
    NAME VARCHAR(255),
    node_block_id bigint NOT NULL,
    flag1 bool default false,
    flag2 bool default false,
    flag3 bool default false,
    flag4 bool default false,
    STATE VARCHAR(10)
);

ALTER TABLE NB
    ADD FOREIGN KEY (storage_id)
    REFERENCES STORAGE (id)
    ON UPDATE NO ACTION
    ON DELETE NO ACTION DEFERRABLE INITIALLY DEFERRED
;

ALTER TABLE NODE
    ADD FOREIGN KEY (node_block_id)
    REFERENCES NB (id)
    ON UPDATE NO ACTION
    ON DELETE NO ACTION DEFERRABLE INITIALLY DEFERRED
;
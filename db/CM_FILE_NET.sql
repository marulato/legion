CREATE TABLE CM_FILE_NET
(
    FILE_NET_ID  BIGINT PRIMARY KEY AUTO_INCREMENT,
    FILE_NAME    VARCHAR(256)  NOT NULL,
    FILE_TYPE    VARCHAR(16)    NOT NULL,
    SIZE         BIGINT,
    STATUS       VARCHAR(16)   NOT NULL,
    STORAGE_TYPE VARCHAR(16)   NOT NULL,
    FILE_UUID    VARCHAR(128)  NOT NULL,
    PATH         VARCHAR(4000),
    SHA_512      VARCHAR(1024) NOT NULL,
    DATA         MEDIUMBLOB,
    CREATED_AT   DATETIME      NOT NULL,
    CREATED_BY   VARCHAR(32)   NOT NULL,
    UPDATED_AT   DATETIME      NOT NULL,
    UPDATED_BY   VARCHAR(32)   NOT NULL
);
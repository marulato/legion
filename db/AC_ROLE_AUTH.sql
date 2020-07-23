CREATE TABLE AC_ROLE_AUTH
(
    ROLE_ID         VARCHAR(6)    NOT NULL,
    URL             VARCHAR(1024) NOT NULL,
    INCLUDE_REQUEST VARCHAR(64),
    EXCLUDE_REQUEST VARCHAR(64),
    CREATED_AT      DATETIME      NOT NULL,
    CREATED_BY      VARCHAR(24)   NOT NULL,
    UPDATED_AT      DATETIME      NOT NULL,
    UPDATED_BY      VARCHAR(24)   NOT NULL
);
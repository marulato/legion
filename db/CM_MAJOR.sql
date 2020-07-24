CREATE TABLE CM_MAJOR
(
    MAJOR_ID           INT PRIMARY KEY AUTO_INCREMENT,
    MAJOR_CODE         VARCHAR(6)  NOT NULL UNIQUE,
    NAME               VARCHAR(32),
    TYPE               VARCHAR(6),
    SUBJECT_TYPE       CHAR(1),
    IS_KEY             CHAR(1),
    IS_MASTER_PROGRAM  CHAR(1),
    IS_DOCTOR_PROGRAM  CHAR(1),
    DIRECTOR_ID        VARCHAR(16) NOT NULL,
    DIRECTOR           VARCHAR(16) NOT NULL,
    NUMBER_OF_STUDENTS INT,
    NUMBER_OF_STAFFS   INT,
    CREATED_AT         DATETIME    NOT NULL,
    CREATED_BY         VARCHAR(24) NOT NULL,
    UPDATED_AT         DATETIME    NOT NULL,
    UPDATED_BY         VARCHAR(24) NOT NULL
);
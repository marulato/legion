CREATE TABLE STU_STUDENT
(
    STU_NO                     VARCHAR(16) PRIMARY KEY,
    ORDER_NO                   INT,
    COLLEGE                    VARCHAR(6)  NOT NULL,
    MAJOR                      VARCHAR(6)  NOT NULL,
    GRADE                      INT         NOT NULL,
    SEMESTER                   INT         NOT NULL,
    CLASS_NO                   VARCHAR(6)  NOT NULL,
    DEGREE_IN_STUDY            CHAR(3)     NOT NULL,
    IS_REGISTERED              CHAR(1),
    IS_STUDYING                CHAR(1),
    IS_GRADUATED               CHAR(1),
    IS_GRADUATION_POSTPONED    CHAR(1),
    HIGH_SCHOOL                VARCHAR(500),
    NEMT_SCORE                 INT,
    NEMT_AREA                  VARCHAR(6),
    ASPIRATION_NO              INT,
    EXPECTED_MAJOR             VARCHAR(6),
    IS_ADJUSTED                CHAR(1),
    EXPECTED_GRADUATE_YEAR     VARCHAR(4)  NOT NULL,
    ACTUAL_GRADUATE_YEAR       VARCHAR(4),
    ENTRANCE_YEAR              VARCHAR(4),
    REQUIRED_REGISTRATION_TIME DATE,
    ACTUAL_REGISTRATION_TIME   DATE,
    DORMITORY                  VARCHAR(16) NOT NULL,
    PROFILE                    VARCHAR(500),
    CREATED_AT                 DATETIME    NOT NULL,
    CREATED_BY                 VARCHAR(32) NOT NULL,
    UPDATED_AT                 DATETIME    NOT NULL,
    UPDATED_BY                 VARCHAR(32) NOT NULL

);

CREATE TABLE STU_STUDENT_H
(
    AUDIT_ID                   BIGINT PRIMARY KEY AUTO_INCREMENT,
    AUDIT_TYPE                 CHAR(2),
    AUDIT_TIME                 DATETIME,
    STU_NO                     VARCHAR(16),
    ORDER_NO                   INT,
    COLLEGE                    VARCHAR(6),
    MAJOR                      VARCHAR(6),
    GRADE                      INT,
    SEMESTER                   INT NOT NULL,
    CLASS_NO                   VARCHAR(6),
    DEGREE_IN_STUDY            CHAR(3),
    IS_REGISTERED              CHAR(1),
    IS_STUDYING                CHAR(1),
    IS_GRADUATED               CHAR(1),
    IS_GRADUATION_POSTPONED    CHAR(1),
    HIGH_SCHOOL                VARCHAR(500),
    NEMT_SCORE                 INT,
    NEMT_AREA                  VARCHAR(6),
    ASPIRATION_NO              INT,
    EXPECTED_MAJOR             VARCHAR(6),
    IS_ADJUSTED                CHAR(1),
    EXPECTED_GRADUATE_YEAR     VARCHAR(4),
    ACTUAL_GRADUATE_YEAR       VARCHAR(4),
    ENTRANCE_YEAR              VARCHAR(4),
    REQUIRED_REGISTRATION_TIME DATE,
    ACTUAL_REGISTRATION_TIME   DATE,
    DORMITORY                  VARCHAR(16),
    PROFILE                    VARCHAR(500),
    CREATED_AT                 DATETIME,
    CREATED_BY                 VARCHAR(32),
    UPDATED_AT                 DATETIME,
    UPDATED_BY                 VARCHAR(32)

);

DELIMITER ##
CREATE TRIGGER STU_STUDENT_TRG_AI
    AFTER INSERT
    ON STU_STUDENT
    FOR EACH ROW
BEGIN
    INSERT INTO STU_STUDENT_H(AUDIT_ID, AUDIT_TYPE, AUDIT_TIME,
                              STU_NO, ORDER_NO, COLLEGE, MAJOR, GRADE, SEMESTER,
                              CLASS_NO, DEGREE_IN_STUDY, IS_REGISTERED, IS_STUDYING, IS_GRADUATED,
                              IS_GRADUATION_POSTPONED, HIGH_SCHOOL, NEMT_SCORE, NEMT_AREA, ASPIRATION_NO,
                              EXPECTED_MAJOR, IS_ADJUSTED, EXPECTED_GRADUATE_YEAR, ACTUAL_GRADUATE_YEAR, ENTRANCE_YEAR,
                              REQUIRED_REGISTRATION_TIME, ACTUAL_REGISTRATION_TIME, DORMITORY, PROFILE, CREATED_AT,
                              CREATED_BY, UPDATED_AT, UPDATED_BY)
    VALUES (NULL, 'AI', NOW(),
            NEW.STU_NO, NEW.ORDER_NO, NEW.COLLEGE, NEW.MAJOR, NEW.GRADE, NEW.SEMESTER,
            NEW.CLASS_NO, NEW.DEGREE_IN_STUDY, NEW.IS_REGISTERED, NEW.IS_STUDYING, NEW.IS_GRADUATED,
            NEW.IS_GRADUATION_POSTPONED, NEW.HIGH_SCHOOL, NEW.NEMT_SCORE, NEW.NEMT_AREA, NEW.ASPIRATION_NO,
            NEW.EXPECTED_MAJOR, NEW.IS_ADJUSTED, NEW.EXPECTED_GRADUATE_YEAR, NEW.ACTUAL_GRADUATE_YEAR,
            NEW.ENTRANCE_YEAR,
            NEW.REQUIRED_REGISTRATION_TIME, NEW.ACTUAL_REGISTRATION_TIME, NEW.DORMITORY, NEW.PROFILE, NEW.CREATED_AT,
            NEW.CREATED_BY, NEW.UPDATED_AT, NEW.UPDATED_BY);
END ##
DELIMITER ;

DELIMITER ##
CREATE TRIGGER STU_STUDENT_TRG_BU
    BEFORE UPDATE
    ON STU_STUDENT
    FOR EACH ROW
BEGIN
    INSERT INTO STU_STUDENT_H(AUDIT_ID, AUDIT_TYPE, AUDIT_TIME,
                              STU_NO, ORDER_NO, COLLEGE, MAJOR, GRADE, SEMESTER,
                              CLASS_NO, DEGREE_IN_STUDY, IS_REGISTERED, IS_STUDYING, IS_GRADUATED,
                              IS_GRADUATION_POSTPONED, HIGH_SCHOOL, NEMT_SCORE, NEMT_AREA, ASPIRATION_NO,
                              EXPECTED_MAJOR, IS_ADJUSTED, EXPECTED_GRADUATE_YEAR, ACTUAL_GRADUATE_YEAR, ENTRANCE_YEAR,
                              REQUIRED_REGISTRATION_TIME, ACTUAL_REGISTRATION_TIME, DORMITORY, PROFILE, CREATED_AT,
                              CREATED_BY, UPDATED_AT, UPDATED_BY)
    VALUES (NULL, 'BU', NOW(),
            OLD.STU_NO, OLD.ORDER_NO, OLD.COLLEGE, OLD.MAJOR, OLD.GRADE, OLD.SEMESTER,
            OLD.CLASS_NO, OLD.DEGREE_IN_STUDY, OLD.IS_REGISTERED, OLD.IS_STUDYING, OLD.IS_GRADUATED,
            OLD.IS_GRADUATION_POSTPONED, OLD.HIGH_SCHOOL, OLD.NEMT_SCORE, OLD.NEMT_AREA, OLD.ASPIRATION_NO,
            OLD.EXPECTED_MAJOR, OLD.IS_ADJUSTED, OLD.EXPECTED_GRADUATE_YEAR, OLD.ACTUAL_GRADUATE_YEAR,
            OLD.ENTRANCE_YEAR,
            OLD.REQUIRED_REGISTRATION_TIME, OLD.ACTUAL_REGISTRATION_TIME, OLD.DORMITORY, OLD.PROFILE, OLD.CREATED_AT,
            OLD.CREATED_BY, OLD.UPDATED_AT, OLD.UPDATED_BY);
END ##
DELIMITER ;

DELIMITER ##
CREATE TRIGGER STU_STUDENT_TRG_AU
    AFTER UPDATE
    ON STU_STUDENT
    FOR EACH ROW
BEGIN
    INSERT INTO STU_STUDENT_H(AUDIT_ID, AUDIT_TYPE, AUDIT_TIME,
                              STU_NO, ORDER_NO, COLLEGE, MAJOR, GRADE, SEMESTER,
                              CLASS_NO, DEGREE_IN_STUDY, IS_REGISTERED, IS_STUDYING, IS_GRADUATED,
                              IS_GRADUATION_POSTPONED, HIGH_SCHOOL, NEMT_SCORE, NEMT_AREA, ASPIRATION_NO,
                              EXPECTED_MAJOR, IS_ADJUSTED, EXPECTED_GRADUATE_YEAR, ACTUAL_GRADUATE_YEAR, ENTRANCE_YEAR,
                              REQUIRED_REGISTRATION_TIME, ACTUAL_REGISTRATION_TIME, DORMITORY, PROFILE, CREATED_AT,
                              CREATED_BY, UPDATED_AT, UPDATED_BY)
    VALUES (NULL, 'AU', NOW(),
            NEW.STU_NO, NEW.ORDER_NO, NEW.COLLEGE, NEW.MAJOR, NEW.GRADE, NEW.SEMESTER,
            NEW.CLASS_NO, NEW.DEGREE_IN_STUDY, NEW.IS_REGISTERED, NEW.IS_STUDYING, NEW.IS_GRADUATED,
            NEW.IS_GRADUATION_POSTPONED, NEW.HIGH_SCHOOL, NEW.NEMT_SCORE, NEW.NEMT_AREA, NEW.ASPIRATION_NO,
            NEW.EXPECTED_MAJOR, NEW.IS_ADJUSTED, NEW.EXPECTED_GRADUATE_YEAR, NEW.ACTUAL_GRADUATE_YEAR,
            NEW.ENTRANCE_YEAR,
            NEW.REQUIRED_REGISTRATION_TIME, NEW.ACTUAL_REGISTRATION_TIME, NEW.DORMITORY, NEW.PROFILE, NEW.CREATED_AT,
            NEW.CREATED_BY, NEW.UPDATED_AT, NEW.UPDATED_BY);
END ##
DELIMITER ;

DELIMITER ##
CREATE TRIGGER STU_STUDENT_TRG_BD
    BEFORE DELETE
    ON STU_STUDENT
    FOR EACH ROW
BEGIN
    INSERT INTO STU_STUDENT_H(AUDIT_ID, AUDIT_TYPE, AUDIT_TIME,
                              STU_NO, ORDER_NO, COLLEGE, MAJOR, GRADE, SEMESTER,
                              CLASS_NO, DEGREE_IN_STUDY, IS_REGISTERED, IS_STUDYING, IS_GRADUATED,
                              IS_GRADUATION_POSTPONED, HIGH_SCHOOL, NEMT_SCORE, NEMT_AREA, ASPIRATION_NO,
                              EXPECTED_MAJOR, IS_ADJUSTED, EXPECTED_GRADUATE_YEAR, ACTUAL_GRADUATE_YEAR, ENTRANCE_YEAR,
                              REQUIRED_REGISTRATION_TIME, ACTUAL_REGISTRATION_TIME, DORMITORY, PROFILE, CREATED_AT,
                              CREATED_BY, UPDATED_AT, UPDATED_BY)
    VALUES (NULL, 'BD', NOW(),
            OLD.STU_NO, OLD.ORDER_NO, OLD.COLLEGE, OLD.MAJOR, OLD.GRADE, OLD.SEMESTER,
            OLD.CLASS_NO, OLD.DEGREE_IN_STUDY, OLD.IS_REGISTERED, OLD.IS_STUDYING, OLD.IS_GRADUATED,
            OLD.IS_GRADUATION_POSTPONED, OLD.HIGH_SCHOOL, OLD.NEMT_SCORE, OLD.NEMT_AREA, OLD.ASPIRATION_NO,
            OLD.EXPECTED_MAJOR, OLD.IS_ADJUSTED, OLD.EXPECTED_GRADUATE_YEAR, OLD.ACTUAL_GRADUATE_YEAR,
            OLD.ENTRANCE_YEAR,
            OLD.REQUIRED_REGISTRATION_TIME, OLD.ACTUAL_REGISTRATION_TIME, OLD.DORMITORY, OLD.PROFILE, OLD.CREATED_AT,
            OLD.CREATED_BY, OLD.UPDATED_AT, OLD.UPDATED_BY);
END ##
DELIMITER ;
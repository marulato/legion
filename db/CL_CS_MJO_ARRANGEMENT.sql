CREATE TABLE CL_CS_MJO_ARRANGEMENT
(
    COLLEGE_CODE VARCHAR(6),
    MAJOR_CODE   VARCHAR(6),
    CLASS_NO     VARCHAR(6),
    GRADE        INT,
    SEMESTER     INT,
    COURSE_CODE VARCHAR(16) NOT NULL ,
    SCHEDULE    VARCHAR(32) NOT NULL ,

);
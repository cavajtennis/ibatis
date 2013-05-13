DROP TABLE ENUMERATIONS CASCADE CONSTRAINTS;

CREATE TABLE ENUMERATIONS  (
   ENUM_ID              INTEGER                          NOT NULL,
   ENUM_DAY             INTEGER                          NOT NULL,
   ENUM_COLOR           INTEGER                          NOT NULL,
   ENUM_MONTH           INTEGER,
   CONSTRAINT PK_ENUMERATIONS PRIMARY KEY (ENUM_ID)
)
NOLOGGING 
NOCACHE 
NOPARALLEL;

INSERT INTO ENUMERATIONS VALUES(1, 1, 1, 128);
INSERT INTO ENUMERATIONS VALUES(2, 2, 2, 2048);
INSERT INTO ENUMERATIONS VALUES(3, 3, 4, 256);
INSERT INTO ENUMERATIONS VALUES(4, 4, 8, NULL);

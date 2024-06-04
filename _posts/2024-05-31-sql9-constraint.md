---
layout: single
title: 2024/05/31/ SQL- 09 - Constraint - Check-Unique -PK-FK
---
---
# Constraint 
---
## Check
---
- CONSTRAINT: Provides a feature to prevent abnormal values from being stored in columns - maintains data integrity
- Divided into column-level constraints (constraints set on the table's columns) and table-level constraints (constraints set on the table)

- CHECK: A constraint that provides a condition that must be true for a value to be stored in the column
- Can be set as a column-level constraint or a table-level constraint

- Creating the SAWON1 table - attributes: employee number (numeric), employee name (string), salary (numeric)
```
CREATE TABLE SAWON1(NO NUMBER(4), NAME VARCHAR2(20), PAY NUMBER);
DESC SAWON1;
```
- Inserting and storing rows in the SAWON1 table - any numeric value can be stored in the PAY column
```
INSERT INTO SAWON1 VALUES(1000, 'Paul Lee', 8000000);
INSERT INTO SAWON1 VALUES(2000, 'Tim Chae', 800000);
SELECT * FROM SAWON1;
COMMIT;
```
- Creating the SAWON2 table - attributes: employee number (numeric), employee name (string), salary (numeric - minimum salary: 5000000)
- Setting a CHECK constraint at the column level - only conditions using the respective column can be set (an error occurs if conditions using other columns are written)
```
CREATE TABLE SAWON2(NO NUMBER(4), NAME VARCHAR2(20), PAY NUMBER CHECK(PAY>=5000000));
```

- Inserting and storing rows in the SAWON2 table - only numeric values that satisfy the CHECK constraint can be stored in the PAY column
```
INSERT INTO SAWON2 VALUES(1000, 'Paul Lee', 8000000);
INSERT INTO SAWON2 VALUES(2000, 'Tim Chae', 800000);- Error occurs as the value violates the CHECK constraint on the PAY column
SELECT * FROM SAWON2;
COMMIT;
```

- USER_CONSTRAINTS: A dictionary that provides information on the constraints set on tables
- CONSTRAINT_NAME: A name to distinguish the constraint - if the name is not specified, it is automatically set in the format SYS_XXXXXXX
- CONSTRAINT_TYPE: The type of constraint - C(CHECK), U(UNIQUE), P(PRIMARY KEY), R(REFERENCE - FOREIGN KEY)
- SEARCH_CONDITION: The condition set by the CHECK constraint
```
SELECT CONSTRAINT_NAME, CONSTRAINT_TYPE, SEARCH_CONDITION FROM USER_CONSTRAINTS WHERE TABLE_NAME='SAWON2';
```
- When setting constraints, it is recommended to specify the name of the constraint for efficient management
- Creating the SAWON3 table - attributes: employee number (numeric), employee name (string), salary (numeric - minimum salary: 5000000)
```
CREATE TABLE SAWON3(NO NUMBER(4), NAME VARCHAR2(20), PAY NUMBER CONSTRAINT SAWON3_PAY_CHECK CHECK(PAY>=5000000));
```

- Checking the constraints set on the SAWON3 table
```
SELECT CONSTRAINT_NAME, CONSTRAINT_TYPE, SEARCH_CONDITION FROM USER_CONSTRAINTS WHERE TABLE_NAME='SAWON3';
```
- Creating the SAWON4 table - attributes: employee number (numeric), employee name (string), salary (numeric - minimum salary: 5000000)
- Setting a CHECK constraint at the table level - conditions using any column in the table can be set
```
CREATE TABLE SAWON4(NO NUMBER(4), NAME VARCHAR2(20), PAY NUMBER, CONSTRAINT SAWON4_PAY_CHECK CHECK(PAY>=5000000));
```

- Checking the constraints set on the SAWON4 table
```
SELECT CONSTRAINT_NAME, CONSTRAINT_TYPE, SEARCH_CONDITION FROM USER_CONSTRAINTS WHERE TABLE_NAME='SAWON4';
```

- NOT NULL: A constraint that does not allow NULL - a CHECK constraint without a condition
- A constraint to ensure that a value must be stored in the column where the NOT NULL constraint is set
- Can only be set as a column-level constraint

- Creating the DEPT1 table - attributes: department number (numeric), department name (string), department location (string)
```
CREATE TABLE DEPT1(DEPTNO NUMBER(2), DNAME VARCHAR2(14), LOC VARCHAR2(13));
DESC DEPT1;
```
- Inserting and storing rows in the DEPT1 table
```
INSERT INTO DEPT1 VALUES(10, 'General Affairs', 'Seoul');
INSERT INTO DEPT1 VALUES(20, NULL, NULL);- Explicit NULL use
INSERT INTO DEPT1(DEPTNO) VALUES(30);- Implicit NULL use
SELECT * FROM DEPT1;
COMMIT;
```
- Creating the DEPT2 table - attributes: department number (numeric - NOT NULL), department name (string - NOT NULL), department location (string - NOT NULL)
```
CREATE TABLE DEPT2(DEPTNO NUMBER(2) CONSTRAINT DEPT2_DEPTNO_NN NOT NULL, DNAME VARCHAR2(14) CONSTRAINT DEPT2_DNAME_NN NOT NULL, LOC VARCHAR2(13) CONSTRAINT DEPT2_LOC_NN NOT NULL);
DESC DEPT2;
```
- Checking the constraints set on the DEPT2 table
```
SELECT CONSTRAINT_NAME, CONSTRAINT_TYPE, SEARCH_CONDITION FROM USER_CONSTRAINTS WHERE TABLE_NAME='DEPT2';
```
- Inserting and storing rows in the DEPT2 table
```
INSERT INTO DEPT2 VALUES(10, 'General Affairs', 'Seoul');
INSERT INTO DEPT2 VALUES(20, NULL, NULL);- Error occurs as it violates the NOT NULL constraint
INSERT INTO DEPT2(DEPTNO) VALUES(30);- Error occurs as it violates the NOT NULL constraint
SELECT * FROM DEPT2;
COMMIT;
```
---
## Unique
---

- UNIQUE: A constraint to prevent duplicate values from being stored in a column
- Can be set as a column-level constraint or a table-level constraint
- UNIQUE constraint can be set multiple times in a table and allows NULL

- Creating the USER1 table - attributes: ID (string), name (string), phone number (string)
```
CREATE TABLE USER1(ID VARCHAR2(20), NAME VARCHAR2(30), PHONE VARCHAR2(15));
DESC USER1;
```
- Inserting and storing rows in the USER1 table
```
INSERT INTO USER1 VALUES('ABC', 'Paul Lee', '010-1234-5678');
INSERT INTO USER1 VALUES('ABC', 'Paul Lee', '010-1234-5678');
SELECT * FROM USER1;
COMMIT;
```
- Creating the USER2 table - attributes: ID (string - UNIQUE), name (string), phone number (string - UNIQUE)
- Setting constraints separately on the ID and phone number using column-level constraints
```
CREATE TABLE USER2(ID VARCHAR2(20) CONSTRAINT USER2_ID_UNIQUE UNIQUE, NAME VARCHAR2(30), PHONE VARCHAR2(15) CONSTRAINT USER2_PHONE_UNIQUE UNIQUE);
DESC USER2;
```
- Checking the constraints set on the USER2 table
```
SELECT CONSTRAINT_NAME, CONSTRAINT_TYPE FROM USER_CONSTRAINTS WHERE TABLE_NAME='USER2';
```

- Inserting and storing rows in the USER2 table
```
INSERT INTO USER2 VALUES('ABC', 'Paul Lee', '010-1234-5678');
INSERT INTO USER2 VALUES('ABC', 'Tim Chae', '010-4567-1234');- Error occurs as it violates the UNIQUE constraint on the ID column
INSERT INTO USER2 VALUES('XYZ', 'Tim Chae', '010-1234-5678');- Error occurs as it violates the UNIQUE constraint on the PHONE column
INSERT INTO USER2 VALUES('ABC', 'Tim Chae', '010-1234-5678');- Error occurs as it violates the UNIQUE constraint on both the ID and PHONE columns
INSERT INTO USER2 VALUES('XYZ', 'Tim Chae', '010-4567-1234');
SELECT * FROM USER2;
COMMIT;
```

- Unique constraints allow columns to accept NULL values for insertions or updates
```
INSERT INTO USER2 VALUES('OPQ','Paul Lee',NULL);
```
- Since NULL is not a value, inserting or updating with NULL does not violate the UNIQUE constraint
```
INSERT INTO USER2 VALUES('IJK','Paul Lee',NULL);
SELECT * FROM USER2;
COMMIT;
```

- Create USER3 table - attributes: ID (VARCHAR, UNIQUE), NAME (VARCHAR), PHONE (VARCHAR, UNIQUE)
- Use table-level constraints to separately set unique constraints on ID and PHONE
```
CREATE TABLE USER3(ID VARCHAR2(20),NAME VARCHAR2(30),PHONE VARCHAR2(15)
    ,CONSTRAINT USER3_ID_UNIQUE UNIQUE(ID),CONSTRAINT USER3_PHONE_UNIQUE UNIQUE(PHONE));
DESC USER3;
```

- Check the constraints set on the USER3 table
```
SELECT CONSTRAINT_NAME,CONSTRAINT_TYPE FROM USER_CONSTRAINTS WHERE TABLE_NAME='USER3';
```

- Insert rows into USER3 table
```
INSERT INTO USER3 VALUES('ABC','Paul Lee','010-1234-5678');
INSERT INTO USER3 VALUES('ABC','Tim Chae','010-4567-1234');- Error due to violation of UNIQUE constraint on ID column
INSERT INTO USER3 VALUES('XYZ','Tim Chae','010-1234-5678');- Error due to violation of UNIQUE constraint on PHONE column
INSERT INTO USER3 VALUES('ABC','Tim Chae','010-1234-5678');- Error due to violation of UNIQUE constraints on both ID and PHONE columns
INSERT INTO USER3 VALUES('XYZ','Tim Chae','010-4567-1234');
SELECT * FROM USER3;
COMMIT;
```

- Create USER4 table - attributes: ID (VARCHAR, UNIQUE), NAME (VARCHAR), PHONE (VARCHAR, UNIQUE)
- Use table-level constraint to set unique constraints on both ID and PHONE simultaneously
- Use table-level constraints to set UNIQUE constraint on multiple columns - error occurs only when all column values are duplicated
```
CREATE TABLE USER4(ID VARCHAR2(20),NAME VARCHAR2(30),PHONE VARCHAR2(15)
    ,CONSTRAINT USER4_ID_PHONE_UNIQUE UNIQUE(ID,PHONE));
DESC USER4;
```
- Check the constraints set on the USER4 table
```
SELECT CONSTRAINT_NAME,CONSTRAINT_TYPE FROM USER_CONSTRAINTS WHERE TABLE_NAME='USER4';
```

- Insert rows into USER4 table
```
INSERT INTO USER4 VALUES('ABC','Paul Lee','010-1234-5678');
INSERT INTO USER4 VALUES('ABC','Tim Chae','010-4567-1234');
INSERT INTO USER4 VALUES('XYZ','Sung Lee','010-1234-5678');
INSERT INTO USER4 VALUES('ABC','Eddie Lee','010-1234-5678');- Error due to violation of UNIQUE constraint set on both ID and PHONE columns
SELECT * FROM USER4;
COMMIT;
```

---
## Primary Key (PK)
---
- PRIMARY KEY (PK): A constraint to prevent duplicate values in a column
- Can be set at the column level or table level
- A table can have only one PRIMARY KEY constraint, and NULL values are not allowed
- It is recommended to set a PRIMARY KEY constraint to define relationships between tables

- Create MGR1 table - attributes: NO (NUMBER, PRIMARY KEY), NAME (VARCHAR), STARTDATE (DATE)
- Use column-level constraint to set PRIMARY KEY on NO
```
CREATE TABLE MGR1(NO NUMBER(4) CONSTRAINT MGR1_NO_PK PRIMARY KEY,NAME VARCHAR2(20),STARTDATE DATE);
DESC MGR1;
```

- Check the constraints set on the MGR1 table
```
SELECT CONSTRAINT_NAME,CONSTRAINT_TYPE FROM USER_CONSTRAINTS WHERE TABLE_NAME='MGR1';
```
- Insert rows into MGR1 table
```
INSERT INTO MGR1 VALUES(1000,'Paul Lee',SYSDATE);
INSERT INTO MGR1 VALUES(1000,'Sung Lee',SYSDATE);- Error due to violation of PRIMARY KEY constraint
INSERT INTO MGR1 VALUES(NULL,'Sung Lee',SYSDATE);- Error due to violation of PRIMARY KEY constraint
INSERT INTO MGR1 VALUES(2000,'Sung Lee',SYSDATE);
SELECT * FROM MGR1;
COMMIT;
```

- Create MGR2 table - attributes: NO (NUMBER, PRIMARY KEY), NAME (VARCHAR), STARTDATE (DATE)
- Use table-level constraint to set PRIMARY KEY on NO
```
CREATE TABLE MGR2(NO NUMBER(4),NAME VARCHAR2(20),STARTDATE DATE,CONSTRAINT MGR2_NO_PK PRIMARY KEY(NO));
DESC MGR2;
```

- Check the constraints set on the MGR2 table
```
SELECT CONSTRAINT_NAME,CONSTRAINT_TYPE FROM USER_CONSTRAINTS WHERE TABLE_NAME='MGR2';
```
- Insert rows into MGR2 table
```
INSERT INTO MGR2 VALUES(1000,'Paul Lee',SYSDATE);
INSERT INTO MGR2 VALUES(1000,'Sung Lee',SYSDATE);- Error due to violation of PRIMARY KEY constraint
INSERT INTO MGR2 VALUES(NULL,'Sung Lee',SYSDATE);- Error due to violation of PRIMARY KEY constraint
INSERT INTO MGR2 VALUES(2000,'Sung Lee',SYSDATE);
SELECT * FROM MGR2;
COMMIT;
```
- Create MGR3 table - attributes: NO (NUMBER, PRIMARY KEY), NAME (VARCHAR, PRIMARY KEY), STARTDATE (DATE)
- Use table-level constraint to simultaneously set PRIMARY KEY constraints on NO and NAME
```
CREATE TABLE MGR3(NO NUMBER(4),NAME VARCHAR2(20),STARTDATE DATE,CONSTRAINT MGR3_NO_NAME_PK PRIMARY KEY(NO,NAME));
DESC MGR3;
```

- Check the constraints set on the MGR3 table
```
SELECT CONSTRAINT_NAME,CONSTRAINT_TYPE FROM USER_CONSTRAINTS WHERE TABLE_NAME='MGR3';
```
- Insert rows into MGR3 table
```
INSERT INTO MGR3 VALUES(1000,'Paul Lee',SYSDATE);
INSERT INTO MGR3 VALUES(1000,'Sung Lee',SYSDATE);
INSERT INTO MGR3 VALUES(2000,'Paul Lee',SYSDATE);
INSERT INTO MGR3 VALUES(1000,'Paul Lee',SYSDATE);- Error due to violation of PRIMARY KEY constraint
```

---
## REFERENCES -Foreign Key (FK)
---

- FOREIGN KEY: A constraint to prevent abnormal values in a child table column by referencing values in a parent table column
- Can be set at the column level or table level
- A FOREIGN KEY constraint can only reference columns with PRIMARY KEY constraints in the parent table
- It is recommended to use FOREIGN KEY constraints to define relationships between tables

- Create SUBJECT1 table - attributes: SNO (NUMBER, PRIMARY KEY), SNAME (VARCHAR)
```
CREATE TABLE SUBJECT1(SNO NUMBER(2) CONSTRAINT SUBJECT1_SNO_PK PRIMARY KEY,SNAME VARCHAR2(20));
DESC SUBJECT1;
```

- Insert rows into SUBJECT1 table
```
INSERT INTO SUBJECT1 VALUES(10,'JAVA');
INSERT INTO SUBJECT1 VALUES(20,'JSP');
INSERT INTO SUBJECT1 VALUES(30,'SPRING');
SELECT * FROM SUBJECT1;
COMMIT;
```

- Create TRAINEE1 table - attributes: TNO (NUMBER, PRIMARY KEY), TNAME (VARCHAR), SCODE (NUMBER)
```
CREATE TABLE TRAINEE1(TNO NUMBER(4) CONSTRAINT TRAINEE1_TNO_PK PRIMARY KEY,TNAME VARCHAR2(20),SCODE NUMBER(2));
DESC TRAINEE1;
```

- Insert rows into TRAINEE1 table
```
INSERT INTO TRAINEE1 VALUES(1000,'Paul Lee',10);
INSERT INTO TRAINEE1 VALUES(2000,'Tim Chae',20);
INSERT INTO TRAINEE1 VALUES(3000,'Eddie Lee',30);
INSERT INTO TRAINEE1 VALUES(4000,'Sung Lee',40);
SELECT * FROM TRAINEE1;
COMMIT;
```

- Retrieve TNO, TNAME, and SNAME for all trainees in TRAINEE1 and SUBJECT1 tables
- Join condition: retrieve only rows where SCODE in TRAINEE1 matches SNO in SUBJECT1 - INNER JOIN
- Trainee with TNO 4000 will not be retrieved due to false join condition - retrieval error
```
SELECT TNO,TNAME,SNAME FROM TRAINEE1 JOIN SUBJECT1 ON SCODE=SNO;
```
- Create SUBJECT2 table - attributes: SNO (NUMBER, PRIMARY KEY), SNAME (VARCHAR)
```
CREATE TABLE SUBJECT2(SNO NUMBER(2) CONSTRAINT SUBJECT2_SNO_PK PRIMARY KEY,SNAME VARCHAR2(20));
DESC SUBJECT2;
```
- Insert rows into SUBJECT2 table
```
INSERT INTO SUBJECT2 VALUES(10,'JAVA');
INSERT INTO SUBJECT2 VALUES(20,'JSP');
INSERT INTO SUBJECT2 VALUES(30,'SPRING');
SELECT * FROM SUBJECT2;
COMMIT;
```

- Create TRAINEE2 table - attributes: TNO (NUMBER, PRIMARY KEY), TNAME (VARCHAR), SCODE (NUMBER, FOREIGN KEY)
- Set FOREIGN KEY constraint on SCODE column in TRAINEE2 table referencing SNO column in SUBJECT2 table
- Due to FOREIGN KEY constraint, SUBJECT2 table is set as the parent table and TRAINEE2 table as the child table
- Use column-level constraint to set FOREIGN KEY constraint
```
CREATE TABLE TRAINEE2(TNO NUMBER(4) CONSTRAINT TRAINEE2_TNO_PK PRIMARY KEY,TNAME VARCHAR2(20),SCODE NUMBER(2) CONSTRAINT TRAINEE2_SCODE_FK REFERENCES SUBJECT2(SNO));

DESC TRAINEE2;
```

- Check the constraints set on the TRAINEE2 table
```
SELECT CONSTRAINT_NAME,CONSTRAINT_TYPE,R_CONSTRAINT_NAME FROM USER_CONSTRAINTS WHERE TABLE_NAME='TRAINEE
```

- Update the course code of the trainee with trainee number 1000 in the TRAINEE2 table
```
SELECT * FROM TRAINEE2;
```
- Due to the FOREIGN KEY constraint, the column value in the child table must reference the column value in the parent table for insertions or updates
```
UPDATE TRAINEE2 SET SCODE=40 WHERE TNO=1000;- Error due to violation of FOREIGN KEY 
constraint - Cannot update as the parent table's column value cannot be referenced
```


- Delete the row with course code 10 from the SUBJECT2 table
- Rows in the parent table referenced by the column values in the child table cannot be deleted
```
DELETE FROM SUBJECT2 WHERE SNO=10;- Error due to violation of FOREIGN KEY constraint - Cannot delete as the row is referenced by the column value in the child table
```

-  Create the SUBJECT3 table - attributes: course code (numeric - PRIMARY KEY), course name (text) >> parent table
```
CREATE TABLE SUBJECT3(SNO NUMBER(2) CONSTRAINT SUBJECT3_SNO_PK PRIMARY KEY, SNAME VARCHAR2(20));
DESC SUBJECT3;
```
-  Insert and save rows into the SUBJECT3 table
```
INSERT INTO SUBJECT3 VALUES(10,'JAVA');
INSERT INTO SUBJECT3 VALUES(20,'JSP');
INSERT INTO SUBJECT3 VALUES(30,'SPRING');
SELECT * FROM SUBJECT3;
COMMIT;
```

-  Create the TRAINEE3 table - attributes: trainee number (numeric - PRIMARY KEY), trainee name (text), course code (numeric - FOREIGN KEY) >> child table
-  The FOREIGN KEY constraint set on the SCODE column of the TRAINEE3 table will reference the SNO column (PRIMARY KEY) of the SUBJECT3 table
-  Set the FOREIGN KEY constraint at the table level - can set the FOREIGN KEY constraint by grouping two or more columns
-  When setting the FOREIGN KEY constraint, the ON DELETE CASCADE option or the ON DELETE SET NULL option can be used
-  ON DELETE CASCADE: When a row in the parent table is deleted, the rows with column values in the child table that reference the parent table's row are also deleted
-  ON DELETE SET NULL: When a row in the parent table is deleted, the column values in the child table that reference the parent table's row are set to NULL
```
CREATE TABLE TRAINEE3(TNO NUMBER(4) CONSTRAINT TRAINEE3_TNO_PK PRIMARY KEY, TNAME VARCHAR2(20),SCODE NUMBER(2), CONSTRAINT TRAINEE3_SCODE_FK FOREIGN KEY(SCODE) REFERENCES SUBJECT3(SNO) ON DELETE CASCADE);
DESC TRAINEE3;
```
-  Insert and save rows into the TRAINEE3 table
```
INSERT INTO TRAINEE3 VALUES(1000, 'Paul Lee', 10);
INSERT INTO TRAINEE3 VALUES(2000, 'Tim Chae', 20);
INSERT INTO TRAINEE3 VALUES(3000, 'Eddie Lee', 30);
INSERT INTO TRAINEE3 VALUES(4000, 'Sung Lee', 40); -  Error due to violation of FOREIGN KEY constraint - Cannot insert as the parent table's column value cannot be referenced
SELECT * FROM TRAINEE3;
COMMIT;
```

-  Delete the row with course code 10 from the SUBJECT3 table
```
DELETE FROM SUBJECT3 WHERE SNO=10;
SELECT * FROM SUBJECT3;
```
-  Due to the ON DELETE CASCADE option of the FOREIGN KEY constraint, the row with the SCODE column value in the TRAINEE3 table (child table) referencing the SNO column value in the SUBJECT3 table (parent table) is also deleted
```
SELECT * FROM TRAINEE3;
COMMIT;
```

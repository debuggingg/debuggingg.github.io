---
layout: single
title:  "2024/05/30/ SQL- 06 - Insert-Update-Delete -Merge "
---

---
# Data Manipulation Language
---

- DML(DATA MANIPULATION LANGUAGE): Data Manipulation Language
- SQL commands that insert rows into a table, change column values of rows stored in a table, or delete rows stored in a table
- It is recommended to execute COMMIT (apply DML commands to the table) or ROLLBACK command (not apply DML commands to the table) after executing DML commands

---
## Insert
---

- INSERT: SQL command to insert and store rows into a table
- Format) INSERT INTO table_name VALUES(column_value, column_value, ...)
- The column values of the row to be inserted into the table must be listed in the order of the table attributes and must match the data type of the table attributes without omission

- Check the attributes (ATTRIBUTE - column name and data type) of the table
- Format) DESC table_name

``` DESC DEPT;```

- Insert and store a new row (department information) into the DEPT table

```
 INSERT INTO DEPT VALUES(50,'Accounting','Seoul');
SELECT * FROM DEPT;
COMMIT;
```

- An error occurs if the number of column values provided when inserting a row does not match the number of table columns

``` INSERT INTO DEPT VALUES(60,'General Affairs');``` - Error occurs because the number of provided column values is insufficient

``` INSERT INTO DEPT VALUES(60,'General Affairs','Suwon','Paldal-gu');``` - Error occurs because the number of provided column values is too many

- An error occurs if the data type of the column values provided when inserting a row does not match the table column data type

``` INSERT INTO DEPT VALUES('Sixty','General Affairs','Suwon');```- Insertion is possible due to automatic type conversion even though the provided column value is numeric

- An error occurs if the size of the column values provided when inserting a row is larger than the size of the table column

``` INSERT INTO DEPT VALUES(60,'General Affairs','Suwon Paldal-gu');```- Error occurs

- An error occurs if the provided column value violates the constraint set on the table column
- PRIMARY KEY (PK) constraint: Constraint to prevent duplicate values from being stored in the column
- Since the PK constraint is set on the DEPTNO column of the DEPT table, an error occurs if a duplicate column value is provided

``` INSERT INTO DEPT VALUES(50,'General Affairs','Suwon');```- Error occurs

- Rows can only be inserted if the column values do not violate table attributes and constraints

```
INSERT INTO DEPT VALUES(60,'General Affairs','Suwon');
SELECT * FROM DEPT;
COMMIT;
```

- When inserting a row into a table, if you do not want to store the column value of a row, insert it by providing NULL - explicit NULL use

``` 
INSERT INTO DEPT VALUES(70,'Sales',NULL);
SELECT * FROM DEPT;
COMMIT;
```

- An error occurs if NULL is provided to a column that does not allow NULL when inserting a row

```
INSERT INTO DEPT VALUES(NULL,'Materials','Incheon');- Error occurs due to violation of NOT NULL constraint
DESC DEPT;
```

- It is possible to insert rows by providing values only for specific columns of the table
- Format) INSERT INTO table_name(column_name, column_name,...) VALUE(column_value, column_value, ...)
- Insert rows by providing column values in the order listed in the table columns

```
INSERT INTO DEPT(DNAME,LOC,DEPTNO) VALUES('Materials','Incheon',80);
SELECT * FROM DEPT;
COMMIT;
```

- It is possible to omit table columns - If table columns are omitted, the default value set on the column is automatically used for insertion
- It is possible to set the default value to be stored in the column when creating the table - If the default value is not set for the column, NULL is automatically used as the default value

```
INSERT INTO DEPT(DEPTNO,DNAME) VALUES(90,'HR');- LOC column is automatically provided with NULL for insertion: implicit NULL use
SELECT * FROM DEPT;
COMMIT;
```

- Check the attributes of the EMP table

``` DESC EMP;```

- Insert and store a row (employee information) into the EMP table

``` 
INSERT INTO EMP VALUES(9000,'KIM','MANAGER',7298,'00/12/01',3500,1000,40);
SELECT * FROM EMP;
COMMIT;

```

- It is possible to insert rows by providing values using keywords instead of column values

``` 
INSERT INTO EMP VALUES(9001,'LEE','ANALYST',9000,SYSDATE,2000,NULL,40);
SELECT * FROM EMP;
COMMIT;
```

- INSERT command can insert rows using subquery (SUBQUERY)
- Format) INSERT INTO table_name1 SELECT target_column,... FROM table_name2 [WHERE condition]
- Insert rows into a table using the results of the subquery - copying table rows
- The number of columns and data types of the target columns in the subquery must match those of the table where rows will be inserted

- Check the attributes and rows of the BONUS table
DESC BONUS;```
SELECT * FROM BONUS;```

- Insert the names, jobs, salaries, and commissions of employees who have a commission from the EMP table into the BONUS table

``` 
INSERT INTO BONUS SELECT ENAME,JOB,SAL,COMM FROM EMP WHERE COMM IS NOT NULL;
SELECT * FROM BONUS;
COMMIT;
```

---
## Update
---

- UPDATE: SQL command to change column values of rows stored in a table
- Format) UPDATE table_name SET column_name=new_value, column_name=new_value, ... WHERE condition
- Change the column values of rows where the condition of the WHERE keyword is true (TRUE) in the table
- If the WHERE keyword condition is omitted, all row column values stored in the table are changed to the same value
- It is recommended to use columns with PK constraints in the condition of the WHERE keyword
- Generally, changing column values with PK constraints is not recommended

- Search department information with department number 50 in the DEPT table

```SELECT * FROM DEPT WHERE DEPTNO=50;```- Department name: Accounting, Location: Seoul

- Change the department name to [Accounting Dept] and the location to [Bucheon] for the department with department number 50 in the DEPT table

```
UPDATE DEPT SET DNAME='Accounting Dept',LOC='Bucheon' WHERE DEPTNO=50;
SELECT * FROM DEPT WHERE DEPTNO=50;- Department name: Accounting Dept, Location: Bucheon
COMMIT;
```

- The new value of the column can only be changed if it matches the column's data type, size, and constraints

```UPDATE DEPT SET LOC='Bucheon Wonmi-gu' WHERE DEPTNO=50;```- Error occurs because the new value is larger than the column size of the table

- In the UPDATE command, subqueries can be used as new values of the SET keyword or comparison values of the WHERE keyword condition
- Change the location (NULL) of the department with the name Sales in the DEPT table to the location (Suwon) of the department with the name General Affairs

```
SELECT * FROM DEPT;
UPDATE DEPT SET LOC=(SELECT LOC FROM DEPT WHERE DNAME='General Affairs') WHERE DNAME='Sales';
SELECT * FROM DEPT;
COMMIT;
```

- Increase the commission by 100 for employees in the BONUS table whose commission is less than that of employee KIM

```
SELECT * FROM BONUS;
UPDATE BONUS SET COMM=COMM+100 WHERE COMM<(SELECT COMM FROM BONUS WHERE ENAME='KIM');
SELECT * FROM BONUS;
COMMIT;
```

---
## Delete
---

- DELETE: SQL command to delete rows stored in a table
- Format) DELETE FROM table_name WHERE condition
- Delete rows where the condition of the WHERE keyword is true (TRUE) in the table
- If the WHERE keyword condition is omitted, all rows stored in the table are deleted
- It is recommended to use columns with PK constraints in the condition of the WHERE keyword

- Delete department information with department number 90 in the DEPT table

```
SELECT * FROM DEPT;
DELETE FROM DEPT WHERE DEPTNO=90;
SELECT * FROM DEPT;
COMMIT;
```

- Delete department information with department number 10 in the DEPT table
- Rows of the parent table referenced by the child table cannot be deleted due to FK constraints

```DELETE FROM DEPT WHERE DEPTNO=10;```- Error occurs

- FOREIGN KEY (FK) constraint: Constraint that provides functionality to insert or change the column values of the child table by referencing the column values of the parent table
- Error occurs when inserting or changing the row of the child table if the column values of the parent table cannot be referenced
- FK constraint is set on the DEPTNO column of the EMP table (child table) so that it references the DEPTNO column values of the DEPT table (parent table)

- Rows of the parent table referenced by the child table cannot be deleted due to FK constraints
- Search department numbers of all employees stored in the EMP table without duplicate column values

```
SELECT DISTINCT DEPTNO FROM EMP;- Search result: 10, 20, 30, 40 - Column values of the child table stored by referencing the column values of the parent table
DELETE FROM DEPT WHERE DEPTNO=20;- Error occurs
DELETE FROM DEPT WHERE DEPTNO=80;- Row can be deleted
SELECT * FROM DEPT;```
COMMIT;
```

- Subqueries can be used as comparison values of the WHERE keyword condition in the DELETE command
- Delete department information with the same location as the department with the name Sales in the DEPT table - including Sales

``` 
DELETE FROM DEPT WHERE LOC=(SELECT LOC FROM DEPT WHERE DNAME='Sales');
SELECT * FROM DEPT;
COMMIT;
```
---
## Merge
---

- MERGE : 원본 테이블의 행을 검색하여 타겟 테이블에 행으로 삽입하거나 타겟 테이블에 저장된 행의 컬럼값을 변경하는 SQL 명령
- 테이블의 행 병합
- 형식) MERGE INTO 타겟테이블명 USING 원본테이블명 ON (조건식)
        WHEN MATCHED THEN UPDATE SET 타겟컬럼명=원본컬럼명, 타겟컬럼명=원본컬럼명, ...
        WHEN NOT MATCHED THEN INSERT(타겟컬럼명,  타겟컬럼명, ...) VALUES (원본컬럼명, 원본컬럼명, ...)

-MERGE_DEPT 테이블 생성 - 속성 : 부서번호(숫자형), 부서이름(문자형), 부서위치(문자형)

```CREATE TABLE MERGE_DEPT(DEPTNO NUMBER(2),DNAME VARCHAR2(14),LOC VARCHAR2(13));
DESC MERGE_DEPT;
```

-MERGE_DEPT 테이블에 행을 삽입하여 저장
```
INSERT INTO MERGE_DEPT VALUES(30,'총무부','서울시');
INSERT INTO MERGE_DEPT VALUES(60,'자재부','수원시');
SELECT * FROM MERGE_DEPT;
COMMIT;
```

--DEPT 테이블(원본 테이블)의 저장된 모든 행(부서정보)을 검색하여 MERGE_DEPT 테이블(타켓 테이블)의 행으로 삽입하거나 행의 컬럼값 변경

```
SELECT * FROM DEPT;//원본 테이블 : 10, 20, 30, 40, 50
SELECT * FROM MERGE_DEPT;//타겟 테이블 : 30, 60
MERGE INTO MERGE_DEPT M USING DEPT D ON (M.DEPTNO=D.DEPTNO)
    WHEN MATCHED THEN UPDATE SET M.DNAME=D.DNAME,M.LOC=D.LOC
    WHEN NOT MATCHED THEN INSERT(M.DEPTNO,M.DNAME,M.LOC) VALUES(D.DEPTNO,D.DNAME,D.LOC);
SELECT * FROM MERGE_DEPT;//타겟 테이블 : 10, 20 30, 40, 50, 60
COMMIT;
```


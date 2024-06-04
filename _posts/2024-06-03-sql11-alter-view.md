---
layout: single
title: 2024/06/03/  SQL- 11 -Alter - View
---
## Alter Table
---

-  Change the attributes and constraints of a table
-  Format) ALTER TABLE table_name change_options
-  Attributes can be added, deleted, or changed according to the change options, and constraints can be added or deleted

-  Create the MGR table - attributes: employee number (numeric), employee name (string), phone number (string)
```
CREATE TABLE MGR(NO NUMBER(4), NAME VARCHAR2(20), PHONE VARCHAR2(15));
DESC MGR;
```
-  Insert a row into the MGR table and store it
```
INSERT INTO MGR VALUES(1000, 'Paul Lee', '010-1234-5678');
SELECT * FROM MGR;
COMMIT;
```
-  Add a new attribute to the table - can set default values and column-level constraints
-  Format) ALTER TABLE table_name ADD(column_name data_type[(size)] [DEFAULT default_value] [column_constraint])

-  Add the ADDRESS (string) attribute to the MGR table
```
ALTER TABLE MGR ADD(ADDRESS VARCHAR2(100)); 
DESC MGR;
```
-  Automatically change the newly added column of existing rows stored in the table to store the default value
```
SELECT * FROM MGR;
```
-  Change the newly added column of existing rows stored in the table to store the required column value instead of the default value
```
UPDATE MGR SET ADDRESS='Seoul Gangnam-gu' WHERE NO=1000;
SELECT * FROM MGR;
COMMIT;
```

-  Changing the attributes of a table - changing the column name or column data type and size
-  Rename a column of the table
-  Format) ALTER TABLE table_name RENAME COLUMN old_column_name TO new_column_name

-  Change the name of the PHONE column in the MGR table to HP
```
DESC MGR;
ALTER TABLE MGR RENAME COLUMN PHONE TO HP;
DESC MGR;
SELECT * FROM MGR;
```
-  Change the data type and size of a column in the table - can change the default value or add column-level constraints
-  Format) ALTER TABLE table_name MODIFY(column_name data_type[(size)] [DEFAULT default_value] [column_constraint])

-  Change the data type of the NO column in the MGR table from numeric to string
```
DESC MGR;
ALTER TABLE MGR MODIFY(NO VARCHAR2(4)); -  Error occurs: column data type cannot be changed if the MGR table has rows with column values stored
SELECT * FROM MGR;
```
-  Can change the data type of a column after initializing the table
```
TRUNCATE TABLE MGR;
SELECT * FROM MGR;
ALTER TABLE MGR MODIFY(NO VARCHAR2(4));
DESC MGR;
```
-  Insert and store rows into the MGR table
```
INSERT INTO MGR VALUES('1000', 'Paul Lee', '010-1234-5678', 'Seoul Gangnam-gu');
SELECT * FROM MGR;
COMMIT;
```

-  Change the size of the NAME column in the MGR table from 20 BYTES to 10 BYTES
```
DESC MGR;
ALTER TABLE MGR MODIFY(NAME VARCHAR2(10)); 
DESC MGR;
SELECT * FROM MGR;
```

-  Change the size of the NAME column in the MGR table from 10 BYTES to 5 BYTES
```
ALTER TABLE MGR MODIFY(NAME VARCHAR2(5)); -  Error occurs: cannot change size if the column value size stored in the MGR table rows is larger than the new size
```

-  Set a NOT NULL constraint on the NAME column in the MGR table - add constraint
```
ALTER TABLE MGR MODIFY(NAME VARCHAR2(10) CONSTRAINT MGR_NAME_NN NOT NULL);
SELECT CONSTRAINT_NAME, CONSTRAINT_TYPE FROM USER_CONSTRAINTS WHERE TABLE_NAME='MGR';
```

-  Delete table attributes - delete column values of all rows stored in the table
-  Format) ALTER TABLE table_name DROP COLUMN column_name

-  Delete the phone number attribute (HP column) from the MGR table
```
ALTER TABLE MGR DROP COLUMN HP;
DESC MGR;
SELECT * FROM MGR;
```

-  Set (add) table-level constraints on a column in the table
-  Format) ALTER TABLE table_name ADD [CONSTRAINT constraint_name] constraint

-  Set a PRIMARY KEY constraint on the NO column in the MGR table
```
ALTER TABLE MGR ADD CONSTRAINT MGR_NO_PK PRIMARY KEY(NO);
SELECT CONSTRAINT_NAME, CONSTRAINT_TYPE FROM USER_CONSTRAINTS WHERE TABLE_NAME='MGR';
```

-  Delete constraints set on the table - delete using the constraint name
-  Format) ALTER TABLE table_name DROP {PRIMARY KEY | CONSTRAINT constraint_name}

-  Delete the PRIMARY KEY constraint set on the MGR table
```
ALTER TABLE MGR DROP PRIMARY KEY;
SELECT CONSTRAINT_NAME, CONSTRAINT_TYPE FROM USER_CONSTRAINTS WHERE TABLE_NAME='MGR';
```

-  Delete the NOT NULL constraint set on the NAME column in the MGR table
```
ALTER TABLE MGR DROP CONSTRAINT MGR_NAME_NN;
SELECT CONSTRAINT_NAME, CONSTRAINT_TYPE FROM USER_CONSTRAINTS WHERE TABLE_NAME='MGR';
```

---
## View
---

-  View: a virtual table created based on a table - classified into simple views and complex views
-  A view is created by querying necessary column values from a table and is used to easily query necessary column values or to easily implement permission settings
-  Simple view: a view created by querying one table - can implement functions to insert, update, delete, and query rows in the table using the view
-  If a group function or DISTINCT keyword is used when creating a simple view, only the function to query rows in the table using the view can be implemented
-  Complex view: a view created by combining rows from two or more tables and querying the result - can implement the function to query rows in the table using the view

-  Create a view - create a view using the result of a subquery (SELECT)
-  Format) CREATE [OR REPLACE] [FORCE] VIEW view_name[(column_name, column_name, ...)]  AS SELECT query_target, ... FROM table_name [WHERE condition] [WITH CHECK OPTION] [WITH READ ONLY]
-  If using the CREATE VIEW command, only creating the view is possible - an error occurs if a view with the same name already exists
-  If using the CREATE OR REPLACE VIEW command, either create the view or replace an existing view with the same name
-  FORCE: provides functionality to create a view even if there are no rows queried by the subquery
-  WITH CHECK OPTION: an option to prevent changing the stored values of columns used in the condition of the subquery that created the view
-  WITH READ ONLY: an option to allow only querying rows in the table using the view - simple view

-  Create the DEPT_COPY table by querying all columns for all rows in the DEPT table and insert the queried rows into the DEPT2 table
```
CREATE TABLE DEPT_COPY AS SELECT * FROM DEPT;
DESC DEPT_COPY;
SELECT * FROM DEPT_COPY;
```

-  Create the DEPT_VIEW view by querying all columns for all rows in the DEPT_COPY table
```
CREATE VIEW DEPT_VIEW AS SELECT * FROM DEPT_COPY; -  Error occurs: view cannot be created due to lack of system privileges related to views
```

-  Connect to the DBMS server as the system administrator (SYSDBA - SYS account) and grant view-related system privileges to the current user (SCOTT account)
-  **GRANT CREATE VIEW TO SCOTT;**

-  After being granted the CREATE VIEW system privilege, it is possible to create a view
```
CREATE VIEW DEPT_VIEW AS SELECT * FROM DEPT_COPY; -  Simple view
```

-  USER_VIEWS: a dictionary to provide view information
```
SELECT VIEW_NAME, TEXT FROM USER_VIEWS;
```
-  Query all rows in the view - provides the same result as querying rows in the table: can simply write the SELECT command to query rows
```
SELECT * FROM DEPT_VIEW;
```

-  A simple view can also insert, update, and delete rows - implemented as inserting, updating, and deleting rows in the table
-  Columns that are not in the view or are omitted will use default values when inserting
```
INSERT INTO DEPT_VIEW(DEPTNO, DNAME) VALUES(50, 'General Affairs Department');
SELECT * FROM DEPT_VIEW;
SELECT * FROM DEPT_COPY; -  Insert rows into the table from which the view was created
```
-  Create the EMP_VIEW view by querying employee number, employee name, salary, and department name for employees in department 10 from the EMP and DEPT tables - complex view
```
CREATE VIEW EMP_VIEW AS SELECT EMPNO, ENAME, SAL, DNAME FROM EMP JOIN DEPT ON EMP.DEPTNO = DEPT.DEPTNO WHERE EMP.DEPTNO = 10;
SELECT * FROM EMP_VIEW;    
SELECT VIEW_NAME, TEXT FROM USER_VIEWS; -  Check the view
```
-  Create the EMP_VIEW view by querying employee number, employee name, salary, and department name for all employees from the EMP and DEPT tables
```
CREATE VIEW EMP_VIEW AS SELECT EMPNO, ENAME, SAL, DNAME FROM EMP JOIN DEPT ON EMP.DEPTNO = DEPT.DEPTNO; -  Error occurs: an object with the same name already exists
```
-  CREATE OR REPLACE:  If there is a view with the same name when creating a view, delete the view and create a new view - same as modifying the view
```
CREATE OR REPLACE VIEW EMP_VIEW AS SELECT EMPNO, ENAME, SAL, DNAME FROM EMP JOIN DEPT ON EMP.DEPTNO = DEPT.DEPTNO;
SELECT VIEW_NAME, TEXT FROM USER_VIEWS;
SELECT * FROM EMP_VIEW;
```

-  It is possible to create a view using a subquery without view-related system privileges - inline view
```
SELECT * FROM (SELECT EMPNO, ENAME, SAL, DNAME FROM EMP JOIN DEPT ON EMP.DEPTNO = DEPT.DEPTNO);
```

-  Delete a view
-  Format) DROP VIEW view_name

-  Delete the DEPT_VIEW view
```
SELECT VIEW_NAME, TEXT FROM USER_VIEWS;
DROP VIEW DEPT_VIEW;
SELECT VIEW_NAME, TEXT FROM USER_VIEWS;
```
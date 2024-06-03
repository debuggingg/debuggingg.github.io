---
layout: single
title: 2024/06/03/  SQL- 10 -CREATE -SubQuery-Drop
---
---
# Create-using by SubQuery 

-  Provides the functionality to create a new table by querying an existing table using a subquery and inserting the retrieved rows into the newly created table
-  Format) CREATE TABLE target_table_name [(column_name, column_name, ...)] AS SELECT columns_to_select, ... FROM source_table_name [WHERE condition]
-  The selected columns from the source table are implemented as attributes (column names and data types) of the target table, but the constraints of the source table are not implemented in the target table
-  The names of the selected columns from the source table are used as column names of the target table but can be changed - column data types and sizes cannot be changed

-  Create the EMP_COPY1 table (target table) by querying all columns of all employees from the EMP table (source table) and inserting the retrieved rows
```
CREATE TABLE EMP_COPY1 AS SELECT * FROM EMP;
```
-  Confirm the attributes of the EMP and EMP_COPY1 tables - the attributes (column names and data types) of the source table are the same as those of the target table
```
DESC EMP;
DESC EMP_COPY1;
```

-  Confirm the constraints set on the EMP and EMP_COPY1 tables - constraints on the source table are not present on the target table
```
SELECT CONSTRAINT_NAME, CONSTRAINT_TYPE FROM USER_CONSTRAINTS WHERE TABLE_NAME='EMP';
SELECT CONSTRAINT_NAME, CONSTRAINT_TYPE FROM USER_CONSTRAINTS WHERE TABLE_NAME='EMP_COPY1';
```
-  Confirm the rows in the EMP and EMP_COPY1 tables - rows of the source table are the same as those of the target table: rows of the source table are inserted into the target table
```
SELECT * FROM EMP;
SELECT * FROM EMP_COPY1;
```

-  Create the EMP_COPY2 table by querying employee numbers, employee names, and salaries of all employees from the EMP table and inserting the retrieved rows
```
CREATE TABLE EMP_COPY2 AS SELECT EMPNO, ENAME, SAL FROM EMP;
```

-  Confirm the attributes and rows of the EMP_COPY2 table
```
DESC EMP_COPY2;
SELECT * FROM EMP_COPY2;
```

-  Create the EMP_COPY3 table by querying employee numbers, employee names, and salaries of all employees from the EMP table and inserting the retrieved rows
-  Column names of the target table can be changed - column data types and sizes cannot be changed
```
CREATE TABLE EMP_COPY3(NO, NAME, PAY) AS SELECT EMPNO, ENAME, SAL FROM EMP;
```
-  Confirm the attributes and rows of the EMP_COPY3 table
```
DESC EMP_COPY3;
SELECT * FROM EMP_COPY3;
```
-  Create the EMP_COPY4 table by querying employee numbers, employee names, and salaries of employees with a salary of 2000 or more from the EMP table and inserting the retrieved rows
```
CREATE TABLE EMP_COPY4(NO, NAME, PAY) AS SELECT EMPNO, ENAME, SAL FROM EMP WHERE SAL>=2000;
```
-  Confirm the attributes and rows of the EMP_COPY4 table
```
DESC EMP_COPY4;
SELECT * FROM EMP_COPY4;
```
-  Create the EMP_COPY5 table by querying all attributes of all employees from the EMP table but do not insert any rows
```
CREATE TABLE EMP_COPY5 AS SELECT * FROM EMP WHERE 1=0; -  If the condition of the WHERE keyword is always false, no rows are retrieved
```
-  Confirm the attributes and rows of the EMP_COPY5 table
```
DESC EMP_COPY5;
SELECT * FROM EMP_COPY5;
```

---
## Drop Table - recyclebin-purge
---

-  Delete a table - delete all rows stored in the table
-  Format) DROP TABLE table_name

-  Confirm the list of tables 
```
SELECT TABLE_NAME FROM TABS WHERE TABLE_NAME LIKE 'USER%';
```

-  Delete the USER1 table
```
DROP TABLE USER1;
SELECT TABLE_NAME FROM TABS WHERE TABLE_NAME LIKE 'USER%';
```

-  Instead of querying the USER_TABLES dictionary to confirm the list of tables, you can confirm it by querying the TAB view
-  In Oracle, when a table is deleted, the table and related objects are moved to the recycle bin (RECYCLEBIN) and treated as deleted - deleted tables can be recovered
-  Tables that start with the name BIN in the TNAME column of the TAB view are tables moved to the recycle bin in Oracle
```
SELECT * FROM TAB;
```

-  Confirm the list of objects stored in the recycle bin
```
SHOW RECYCLEBIN;
```

-  Recover the deleted table stored in the recycle bin
-  Format) FLASHBACK TABLE table_name TO BEFORE DROP
```
FLASHBACK TABLE USER1 TO BEFORE DROP;
```

-  Confirm the recovery of the deleted table and query stored rows
```
SELECT TABLE_NAME FROM TABS WHERE TABLE_NAME LIKE 'USER%';
SELECT * FROM USER1;
```

-  Delete the USER2 table - when a table is deleted, the INDEX objects dependent on the table are also deleted
```
DROP TABLE USER2;
```

-  Confirm the recycle bin - confirm the deleted table and its dependent indexes
```
SHOW RECYCLEBIN;
```

-  Recover the USER2 table stored in the Oracle recycle bin - when a deleted table is recovered, the indexes dependent on the recovered table are also recovered
```
FLASHBACK TABLE USER2 TO BEFORE DROP;
```

-  Confirm the recycle bin and the list of tables
```
SHOW RECYCLEBIN;
SELECT TABLE_NAME FROM TABS WHERE TABLE_NAME LIKE 'USER%';
```

-  Delete all tables starting with USER
```
DROP TABLE USER1;
DROP TABLE USER2;
DROP TABLE USER3;
DROP TABLE USER4;
SELECT TABLE_NAME FROM TABS WHERE TABLE_NAME LIKE 'USER%';
SHOW RECYCLEBIN;
```

-  Delete a deleted table stored in the recycle bin so that it cannot be recovered - delete dependent objects as well
-  Format) PURGE TABLE table_name

-  Delete the USER4 table stored in the recycle bin
```
PURGE TABLE USER4;
SHOW RECYCLEBIN;
```

-  Delete all objects stored in the recycle bin - empty the recycle bin
```
PURGE RECYCLEBIN;
SHOW RECYCLEBIN;
```
-  Delete the MGR1 table
```
SELECT TABLE_NAME FROM TABS WHERE TABLE_NAME LIKE 'MGR%';
DROP TABLE MGR1; -  Logical deletion of the table: the table is moved to the recycle bin - the deleted table can be recovered
```
```
SHOW RECYCLEBIN;
PURGE RECYCLEBIN; -  Physical deletion of the table - the deleted table cannot be recovered
SHOW RECYCLEBIN;
```

-  Delete the MGR2 table - physically delete it so that it cannot be recovered without moving it to the recycle bin
-  Format) DROP TABLE table_name PURGE
```
DROP TABLE MGR2 PURGE;
SHOW RECYCLEBIN;
```

-  Initialize a table: command to restore a table to its initial state when it was created - delete all rows stored in the table
-  Format) TRUNCATE TABLE table_name;

-  Confirm all rows stored in the MGR3 table
```
SELECT * FROM MGR3;
```

-  Delete all rows stored in the MGR3 table
```
DELETE FROM MGR3; -  Delete all rows in the table but store the DELETE command in the transaction
SELECT * FROM MGR3; -  Provide query results with the DELETE command stored in the transaction applied
```

-  Initialize the transaction - delete the stored DELETE command from the transaction
```
ROLLBACK; -  Possible to recover rows using the ROLLBACK command
SELECT * FROM MGR3;
```

-  Initialize the MGR3 table
```
TRUNCATE TABLE MGR3; -  Initialize the table to delete rows
SELECT * FROM MGR3;
ROLLBACK; -  Rows cannot be recovered using the ROLLBACK command
SELECT * FROM MGR3;
```

-  Change the name of a table - change the name of the source table to the name of the target table
-  Format) RENAME source_table_name TO target_table_name

---
#### Rename 
-  Change the name of the BONUS table to COMM
```
SELECT TABLE_NAME FROM TABS WHERE TABLE_NAME IN('BONUS', 'COMM');
RENAME BONUS TO COMM;
SELECT TABLE_NAME FROM TABS WHERE TABLE_NAME IN('BONUS', 'COMM');
```

--- 

---
layout: single
title: 2024/06/04/ SQL - 13- Index - Synonym-User-DCL
---
---
# Index
---
--Index (INDEX): An object that provides functionality for faster searching of rows stored in a table 
--Setting an index on a column creates an index area to store search-related information, increasing the row search speed for the column 
--It is recommended to set an index on columns that are frequently used in condition expressions, especially when there are many stored rows

--Unique Index (UNIQUE INDEX): An index automatically created by the PRIMARY KEY constraint or the UNIQUE constraint 
--Non-Unique Index (NON-UNIQUE INDEX): An index manually set by the user on a column

--Create the HEWON table - Attributes: Member number (numeric - PRIMARY KEY), Member name (character type), Email (character type - UNIQUE)
```
CREATE TABLE HEWON(NO NUMBER(4) CONSTRAINT HEWON_NO_PK PRIMARY KEY,NAME VARCHAR2(20) ,EMAIL VARCHAR2(50) CONSTRAINT HEWON_EMAIL_UNIQUE UNIQUE); DESC HEWON;
```

--Check constraints set on the HEWON table 
```
SELECT CONSTRAINT_NAME,CONSTRAINT_TYPE FROM USER_CONSTRAINTS WHERE TABLE_NAME='HEWON';
```

--Check indexes set on columns in the HEWON table 
--Unique indexes are automatically created by PRIMARY KEY or UNIQUE constraints - the index name is the same as the constraint name 
--USER_INDEXES: Dictionary providing index information, USER_IND_COLUMNS: Dictionary providing index information set on columns 
```
SELECT C.INDEX_NAME,COLUMN_NAME,UNIQUENESS FROM USER_INDEXES I JOIN USER_IND_COLUMNS C ON I.INDEX_NAME=C.INDEX_NAME WHERE C.TABLE_NAME='HEWON';
```

--Create Index - Non-Unique Index (NON-UNIQUE INDEX) 
--Syntax) CREATE INDEX index_name ON table_name(column_name)

--Create a non-unique index and set it on the NAME column of the HEWON table 
```
CREATE INDEX HEWON_NAME_INDEX ON HEWON(NAME);
```

--Check indexes set on columns in the HEWON table
```
SELECT C.INDEX_NAME,COLUMN_NAME,UNIQUENESS FROM USER_INDEXES I JOIN USER_IND_COLUMNS C ON I.INDEX_NAME=C.INDEX_NAME WHERE C.TABLE_NAME='HEWON';
```

--Drop Index - Non-Unique Index (NON-UNIQUE INDEX)
--Syntax) DROP INDEX index_name

--Drop the non-unique index set on the NAME column of the HEWON table
```
DROP INDEX HEWON_NAME_INDEX; 
SELECT C.INDEX_NAME,COLUMN_NAME,UNIQUENESS FROM USER_INDEXES I JOIN USER_IND_COLUMNS C ON I.INDEX_NAME=C.INDEX_NAME WHERE C.TABLE_NAME='HEWON';
```

--Drop the unique index set on the EMAIL column of the HEWON table 
DROP INDEX HEWON_EMAIL_UNIQUE;--Error: Unique indexes cannot be dropped with the DROP INDEX command 
--Unique indexes are automatically dropped when the PRIMARY KEY or UNIQUE constraints that created them are dropped
```
ALTER TABLE HEWON DROP CONSTRAINT HEWON_EMAIL_UNIQUE; 
SELECT CONSTRAINT_NAME,CONSTRAINT_TYPE FROM USER_CONSTRAINTS WHERE TABLE_NAME='HEWON';
SELECT C.INDEX_NAME,COLUMN_NAME,UNIQUENESS FROM USER_INDEXES I JOIN USER_IND_COLUMNS C ON I.INDEX_NAME=C.INDEX_NAME WHERE C.TABLE_NAME='HEWON';
```

--Synonym (SYNONYM): An object that provides an alias for using Oracle objects 
--Private Synonym: A synonym that can be used only by a specific user - managed by a regular user 
--Public Synonym: A synonym that can be used by all users - managed by an administrator

--Create Synonym 
--Syntax) CREATE [PUBLIC] SYNONYM synonym_name FOR object_name 
--If the PUBLIC keyword is used, a public synonym is created; if the PUBLIC keyword is omitted, a private synonym is created

--The current user can use objects (tables, views, etc.) in another user's schema if they have the necessary privileges 
--Use objects in another user's schema in the format [user_schema.object_name] 
--The USER_TABLES dictionary is a view created by the administrator (SYS account - SYSDBA) 
--The USER_TABLES dictionary can be used to provide table information - it can be used because the user has privileges to query it 
```
SELECT TABLE_NAME FROM SYS.USER_TABLES; 
```
--Query table information using the public synonym for [SYS.USER_TABLES] set as [USER_TABLES] 
```
SELECT TABLE_NAME FROM USER_TABLES; 
```
--Query table information using the public synonym for [USER_TABLES] set as [TABS] 
```
SELECT TABLE_NAME FROM TABS;
```

--Create a private synonym for the COMM table named BONUS 
SELECT * FROM COMM; SELECT * FROM BONUS;--Error: Table or view does not exist, so search is not possible 
CREATE SYNONYM BONUS FOR COMM;--Error: Unable to use the CREATE SYNONYM command due to lack of related system privileges

--Connect to the DBMS server as a system administrator (SYSDBA - SYS account) and grant synonym-related system privileges to the SCOTT user 
	--GRANT CREATE SYNONYM TO SCOTT;

--After receiving the CREATE SYNONYM system privilege from the administrator, it is possible to create a private synonym 
```
CREATE SYNONYM BONUS FOR COMM; SELECT * FROM COMM; SELECT * FROM BONUS;
```

--USER_SYNONYMS: Dictionary providing information related to synonyms 
```
SELECT TABLE_NAME,SYNONYM_NAME,TABLE_OWNER FROM USER_SYNONYMS;
```

--Drop Synonym --Syntax) DROP [PUBLIC] SYNONYM synonym_name

--Drop the private synonym BONUS set on the COMM table 
```
DROP SYNONYM BONUS; 
SELECT TABLE_NAME,SYNONYM_NAME,TABLE_OWNER FROM USER_SYNONYMS; 
SELECT * FROM BONUS;--Error: Table or view does not exist, so search is not possible
```
--User (USER): A person authorized to use the DBMS - Account (ACCOUNT): A user that can be granted privileges 
--Account management (creation, modification, deletion, search) can only be performed by the system administrator (SYSDBA - SYS account)

--Create Account - The password for the account must be set 
--Syntax)CREATE USER account_name IDENTIFIED BY password

--Create the KIM account - Write and execute in an administrator connection environment 
--In Oracle version 12C or later, the [_ORACLE_SCRIPT] session information must be temporarily set to [TRUE] to manage accounts 
	--ALTER SESSION SET "_ORACLE_SCRIPT"=TRUE;
--Temporarily change session information 
	--CREATE USER KIM IDENTIFIED BY 1234;

--DBA_USERS: Dictionary providing account information 
	--SELECT USERNAME,ACCOUNT_STATUS,DEFAULT_TABLESPACE,CREATED FROM DBA_USERS WHERE USERNAME='KIM';

--Change account password - The password validity period is automatically set to 180 days 
--If the account password exceeds 180 days, the password is deactivated, and the DBMS server cannot be accessed 
--Syntax) ALTER USER account_name IDENTIFIED BY password

--Change the password of the KIM account 
	--ALTER USER KIM IDENTIFIED BY 5678;

--Change account status 
--Account status: OPEN (account active - Oracle server accessible), LOCK (account inactive - Oracle server not accessible) 
--If the account password is entered incorrectly 5 times when accessing the Oracle server, the account status is automatically changed to LOCK 
--Syntax) ALTER USER account_name ACCOUNT {LOCK|UNLOCK}

--Change the status of the KIM account from OPEN to LOCK 
	--SELECT USERNAME,ACCOUNT_STATUS,DEFAULT_TABLESPACE,CREATED FROM DBA_USERS WHERE USERNAME='KIM'; 
	--ALTER USER KIM ACCOUNT LOCK; 
	--SELECT USERNAME,ACCOUNT_STATUS,DEFAULT_TABLESPACE,CREATED FROM DBA_USERS WHERE USERNAME='KIM';

--Change the status of the KIM account from LOCK to OPEN 
	--ALTER USER KIM ACCOUNT UNLOCK; 
	--SELECT USERNAME,ACCOUNT_STATUS,DEFAULT_TABLESPACE,CREATED FROM DBA_USERS WHERE USERNAME='KIM';
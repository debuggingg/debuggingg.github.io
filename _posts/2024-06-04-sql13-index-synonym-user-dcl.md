---
layout: single
title: 2024/06/04/ SQL - 13- Index - Synonym-User-DCL
---
![](obsidian://open?vault=debuggingg.github.io&file=assets%2Fimages%2Fbear-ezgif.com-resize.gif)
---
# Index
---
- Index (INDEX): An object that provides functionality for faster searching of rows stored in a table 
- Setting an index on a column creates an index area to store search-related information, increasing the row search speed for the column 
- It is recommended to set an index on columns that are frequently used in condition expressions, especially when there are many stored rows

- Unique Index (UNIQUE INDEX): An index automatically created by the PRIMARY KEY constraint or the UNIQUE constraint 
- Non-Unique Index (NON-UNIQUE INDEX): An index manually set by the user on a column

- Create the HEWON table - Attributes: Member number (numeric - PRIMARY KEY), Member name (character type), Email (character type - UNIQUE)
```
CREATE TABLE HEWON(NO NUMBER(4) CONSTRAINT HEWON_NO_PK PRIMARY KEY,NAME VARCHAR2(20) ,EMAIL VARCHAR2(50) CONSTRAINT HEWON_EMAIL_UNIQUE UNIQUE); DESC HEWON;
```

- Check constraints set on the HEWON table 
```
SELECT CONSTRAINT_NAME,CONSTRAINT_TYPE FROM USER_CONSTRAINTS WHERE TABLE_NAME='HEWON';
```

- Check indexes set on columns in the HEWON table 
- Unique indexes are automatically created by PRIMARY KEY or UNIQUE constraints - the index name is the same as the constraint name 
- USER_INDEXES: Dictionary providing index information, USER_IND_COLUMNS: Dictionary providing index information set on columns 
```
SELECT C.INDEX_NAME,COLUMN_NAME,UNIQUENESS FROM USER_INDEXES I JOIN USER_IND_COLUMNS C ON I.INDEX_NAME=C.INDEX_NAME WHERE C.TABLE_NAME='HEWON';
```

- Create Index - Non-Unique Index (NON-UNIQUE INDEX) 
- Syntax) CREATE INDEX index_name ON table_name(column_name)

- Create a non-unique index and set it on the NAME column of the HEWON table 
```
CREATE INDEX HEWON_NAME_INDEX ON HEWON(NAME);
```

- Check indexes set on columns in the HEWON table
```
SELECT C.INDEX_NAME,COLUMN_NAME,UNIQUENESS FROM USER_INDEXES I JOIN USER_IND_COLUMNS C ON I.INDEX_NAME=C.INDEX_NAME WHERE C.TABLE_NAME='HEWON';
```

- Drop Index - Non-Unique Index (NON-UNIQUE INDEX)
- Syntax) DROP INDEX index_name

- Drop the non-unique index set on the NAME column of the HEWON table
```
DROP INDEX HEWON_NAME_INDEX; 
SELECT C.INDEX_NAME,COLUMN_NAME,UNIQUENESS FROM USER_INDEXES I JOIN USER_IND_COLUMNS C ON I.INDEX_NAME=C.INDEX_NAME WHERE C.TABLE_NAME='HEWON';
```

- Drop the unique index set on the EMAIL column of the HEWON table 
DROP INDEX HEWON_EMAIL_UNIQUE;- Error: Unique indexes cannot be dropped with the DROP INDEX command 
- Unique indexes are automatically dropped when the PRIMARY KEY or UNIQUE constraints that created them are dropped
```
ALTER TABLE HEWON DROP CONSTRAINT HEWON_EMAIL_UNIQUE; 
SELECT CONSTRAINT_NAME,CONSTRAINT_TYPE FROM USER_CONSTRAINTS WHERE TABLE_NAME='HEWON';
SELECT C.INDEX_NAME,COLUMN_NAME,UNIQUENESS FROM USER_INDEXES I JOIN USER_IND_COLUMNS C ON I.INDEX_NAME=C.INDEX_NAME WHERE C.TABLE_NAME='HEWON';
```

- Synonym (SYNONYM): An object that provides an alias for using Oracle objects 
- Private Synonym: A synonym that can be used only by a specific user - managed by a regular user 
- Public Synonym: A synonym that can be used by all users - managed by an administrator

- Create Synonym 
- Syntax) CREATE [PUBLIC] SYNONYM synonym_name FOR object_name 
- If the PUBLIC keyword is used, a public synonym is created; if the PUBLIC keyword is omitted, a private synonym is created

- The current user can use objects (tables, views, etc.) in another user's schema if they have the necessary privileges 
- Use objects in another user's schema in the format [user_schema.object_name] 
- The USER_TABLES dictionary is a view created by the administrator (SYS account - SYSDBA) 
- The USER_TABLES dictionary can be used to provide table information - it can be used because the user has privileges to query it 
```
SELECT TABLE_NAME FROM SYS.USER_TABLES; 
```
- Query table information using the public synonym for [SYS.USER_TABLES] set as [USER_TABLES] 
```
SELECT TABLE_NAME FROM USER_TABLES; 
```
- Query table information using the public synonym for [USER_TABLES] set as [TABS] 
```
SELECT TABLE_NAME FROM TABS;
```

- Create a private synonym for the COMM table named BONUS 
```
SELECT * FROM COMM; SELECT * FROM BONUS;- Error: Table or view does not exist, so search is not possible 
CREATE SYNONYM BONUS FOR COMM;- Error: Unable to use the CREATE SYNONYM command due to lack of related system privileges
```

- Connect to the DBMS server as a system administrator (SYSDBA - SYS account) and grant synonym-related system privileges to the SCOTT user 
	- GRANT CREATE SYNONYM TO SCOTT;

- After receiving the CREATE SYNONYM system privilege from the administrator, it is possible to create a private synonym 
```
CREATE SYNONYM BONUS FOR COMM; SELECT * FROM COMM; SELECT * FROM BONUS;
```

- USER_SYNONYMS: Dictionary providing information related to synonyms 
```
SELECT TABLE_NAME,SYNONYM_NAME,TABLE_OWNER FROM USER_SYNONYMS;
```

- Drop Synonym - Syntax) DROP [PUBLIC] SYNONYM synonym_name

- Drop the private synonym BONUS set on the COMM table 
```
DROP SYNONYM BONUS; 
SELECT TABLE_NAME,SYNONYM_NAME,TABLE_OWNER FROM USER_SYNONYMS; 
SELECT * FROM BONUS;- Error: Table or view does not exist, so search is not possible
```
- User (USER): A person authorized to use the DBMS - Account (ACCOUNT): A user that can be granted privileges 
- Account management (creation, modification, deletion, search) can only be performed by the system administrator (SYSDBA - SYS account)

- Create Account - The password for the account must be set 
- Syntax)CREATE USER account_name IDENTIFIED BY password

- Create the KIM account - Write and execute in an administrator connection environment 
- In Oracle version 12C or later, the [_ORACLE_SCRIPT] session information must be temporarily set to [TRUE] to manage accounts 
	- ALTER SESSION SET "_ORACLE_SCRIPT"=TRUE;
- Temporarily change session information 
	- CREATE USER KIM IDENTIFIED BY 1234;

- DBA_USERS: Dictionary providing account information 
	- SELECT USERNAME,ACCOUNT_STATUS,DEFAULT_TABLESPACE,CREATED FROM DBA_USERS WHERE USERNAME='KIM';

- Change account password - The password validity period is automatically set to 180 days 
- If the account password exceeds 180 days, the password is deactivated, and the DBMS server cannot be accessed 
- Syntax) ALTER USER account_name IDENTIFIED BY password

- Change the password of the KIM account 
	- ALTER USER KIM IDENTIFIED BY 5678;

- Change account status 
- Account status: OPEN (account active - Oracle server accessible), LOCK (account inactive - Oracle server not accessible) 
- If the account password is entered incorrectly 5 times when accessing the Oracle server, the account status is automatically changed to LOCK 
- Syntax) ALTER USER account_name ACCOUNT {LOCK|UNLOCK}

- Change the status of the KIM account from OPEN to LOCK 
	- SELECT USERNAME,ACCOUNT_STATUS,DEFAULT_TABLESPACE,CREATED FROM DBA_USERS WHERE USERNAME='KIM'; 
	- ALTER USER KIM ACCOUNT LOCK; 
	- SELECT USERNAME,ACCOUNT_STATUS,DEFAULT_TABLESPACE,CREATED FROM DBA_USERS WHERE USERNAME='KIM';

- Change the status of the KIM account from LOCK to OPEN 
	- ALTER USER KIM ACCOUNT UNLOCK; 
	- SELECT USERNAME,ACCOUNT_STATUS,DEFAULT_TABLESPACE,CREATED FROM DBA_USERS WHERE USERNAME='KIM';

- Change the default tablespace of an account
- TABLESPACE: The area where database objects (tables, views, etc.) are stored
- It is recommended to store administrator-related objects in the SYSTEM tablespace and general user related objects in the USERS tablespace
- Syntax: ALTER USER username DEFAULT TABLESPACE tablespace_name

- Change the default tablespace of the KIM account to USERS
	- SELECT USERNAME, ACCOUNT_STATUS, DEFAULT_TABLESPACE, CREATED FROM DBA_USERS WHERE USERNAME='KIM';
	- ALTER USER KIM DEFAULT TABLESPACE USERS;

- Change the physical storage size that an account can use in a tablespace - capacity limit
- Syntax: ALTER USER username QUOTA size_limit ON tablespace_name

- Change the physical storage size that the KIM account can use in the USERS tablespace to unlimited
	- ALTER USER KIM QUOTA UNLIMITED ON USERS;

- DBA_TS_QUOTAS: A dictionary to provide information about the physical storage size that an account can use in a tablespace
	- SELECT TABLESPACE_NAME, USERNAME, MAX_BYTES FROM DBA_TS_QUOTAS WHERE USERNAME='KIM';

- Change the physical storage size that the KIM account can use in the USERS tablespace to 20MB
	- ALTER USER KIM QUOTA 20M ON USERS;
	- SELECT TABLESPACE_NAME, USERNAME, MAX_BYTES FROM DBA_TS_QUOTAS WHERE USERNAME='KIM';

- Delete an account
- Syntax: DROP USER username

- Delete the KIM account
	- DROP USER KIM;
	- SELECT USERNAME, ACCOUNT_STATUS, DEFAULT_TABLESPACE, CREATED FROM DBA_USERS WHERE USERNAME='KIM';

---
## Data Control Language DCL
---

- DCL (DATA CONTROL LANGUAGE) - Data Control Language
- SQL commands to grant or revoke the necessary privileges to an account
- ORACLE PRIVILEGE: System privileges (managed by administrators) and object privileges (managed by general users)

- System Privileges: Privileges to use commands (DDL) to manage elements that make up the system
- Grant system privileges to an account
- Syntax: GRANT {PRIVILEGE|ROLE}, {PRIVILEGE|ROLE}, ... TO {username|PUBLIC} [WITH ADMIN OPTION] [IDENTIFIED BY password]
	-  WITH ADMIN OPTION: An option that allows the account that received the system privileges to grant or revoke those privileges to/from other accounts
- If the PUBLIC keyword is used instead of a username, the system privileges are granted to all accounts collectively
- If there is no account to receive the system privileges, the account is automatically created - a password must be set if the account is created

- Create the KIM account - write and execute the command in the administrator connection environment
	- CREATE USER KIM IDENTIFIED BY 1234;

- Connect to the Oracle server with the KIM account - use the SQLPLUS program
	- C:\Users\itwill> sqlplus KIM  >> Use the SQLPLUS program to connect to the Oracle server with the KIM account - enter the password
- Failed to connect to the Oracle server due to the lack of the CREATE SESSION system privilege
- SESSION: A client environment where you can write, send, and execute SQL commands while connected to the DBMS server

- The system administrator (SYSDBA - SYS account) grants the CREATE SESSION system privilege to the KIM account
	- GRANT CREATE SESSION TO KIM;

- Connect to the Oracle server with the KIM account - use the SQLPLUS program
	- C:\Users\itwill> sqlplus KIM  >> Use the SQLPLUS program to connect to the Oracle server with the KIM account - enter the password
- Able to connect to the Oracle server due to having the CREATE SESSION system privilege

- In the session connected with the KIM account, create the SAWON table - use the SQLPLUS program
- SAWON table - attributes: Employee Number (Numeric - PRIMARY KEY), Employee Name (String), Salary (Numeric)
	- SQL> CREATE TABLE SAWON(NO NUMBER(4) PRIMARY KEY, NAME VARCHAR2(20), PAY NUMBER);
- Unable to create (modify, delete) the table due to the lack of the CREATE TABLE system privilege

- The system administrator (SYSDBA - SYS account) grants the CREATE TABLE system privilege to the KIM account
	- GRANT CREATE TABLE TO KIM;

- In the session connected with the KIM account, create the SAWON table - use the SQLPLUS program
	- SQL> CREATE TABLE SAWON(NO NUMBER(4) PRIMARY KEY, NAME VARCHAR2(20), PAY NUMBER);
- Able to create (modify, delete) the table due to having the CREATE TABLE system privilege

- Object Privileges: Privileges to use commands (INSERT, UPDATE, DELETE, SELECT) for objects stored in a user schema
- Syntax: GRANT {ALL|PRIVILEGE, PRIVILEGE,...} ON object_name TO username [WITH GRANT OPTION]
	- ALL: A keyword to express the authority to use all commands for the object
	- PRIVILEGE: Use INSERT, UPDATE, DELETE, SELECT keywords to express object privileges
	- WITH GRANT OPTION: A function to provide the authority to grant or revoke the object privileges granted to an account to/from other accounts

- In the session connected with the KIM account, search all rows stored in the DEPT table of the SCOTT user schema - use the SQLPLUS program
	- SQL> SELECT * FROM SCOTT.DEPT;- Error occurred: Unable to search because the table or view does not exist - Unable to search due to the lack of object privileges

- The SCOTT account grants the SELECT object privilege on the DEPT table of the SCOTT user schema to the KIM account
```
GRANT SELECT ON DEPT TO KIM;
```

- In the session connected with the KIM account, search all rows stored in the DEPT table of the SCOTT user schema - use the SQLPLUS program
	- SQL> SELECT * FROM SCOTT.DEPT;

- USER_TAB_PRIVS_MADE: A dictionary to provide information about granted object privileges
```
SELECT * FROM USER_TAB_PRIVS_MADE;
```

- USER_TAB_PRIVS_RECD: A dictionary to provide information about received object privileges
```
SELECT * FROM USER_TAB_PRIVS_RECD;
```

- Revoke object privileges
- Syntax: REVOKE {ALL|PRIVILEGE, PRIVILEGE,...} ON object_name FROM username [WITH GRANT OPTION]

- The SCOTT account revokes the SELECT object privilege on the DEPT table of the SCOTT user schema from the KIM account
```
REVOKE SELECT ON DEPT FROM KIM;
```

- In the session connected with the KIM account, search all rows stored in the DEPT table of the SCOTT user schema - use the SQLPLUS program
	- SQL> SELECT * FROM SCOTT.DEPT;- Error occurred: Unable to search because the table or view does not exist - Unable to search due to the lack of object privileges

- Revoke system privileges - the account is not deleted even if all system privileges of the account are revoked
- Syntax: REVOKE {PRIVILEGE|ROLE}, {PRIVILEGE|ROLE}, ... FROM {username|PUBLIC} [WITH ADMIN OPTION]

- The system administrator (SYSDBA - SYS account) revokes the CREATE SESSION system privilege from the KIM account
	- REVOKE CREATE SESSION FROM KIM;

- Connect to the Oracle server with the KIM account - use the SQLPLUS program
	- C:\Users\itwill> sqlplus KIM  >> Use the SQLPLUS program to connect to the Oracle server with the KIM account - enter the password
- Able to connect to the Oracle server due to having the CREATE SESSION system privilege

- ROLE: A name for a group of system privileges to efficiently manage system privileges
- CONNECT: A basic group of system privileges - CREATE SESSION, CREATE TABLE, ALTER SESSION, etc.
- RESOURCE: A group of object-related system privileges - CREATE TABLE, CREATE SEQUENCE, CREATE TRIGGER, etc.

- The system administrator (SYSDBA - SYS account) grants CONNECT ROLE and RESOURCE ROLE to the LEE account
	- GRANT CONNECT, RESOURCE TO LEE IDENTIFIED BY 5678

- Connect to the Oracle server with the LEE account and create the SAWON table - use the SQLPLUS program
- SAWON table - attributes: Employee Number (Numeric - PRIMARY KEY), Employee Name (String), Salary (Numeric)
	- C:\Users\itwill> sqlplus LEE  
	- SQL> CREATE TABLE SAWON(NO NUMBER(4) PRIMARY KEY, NAME VARCHAR2(20), PAY NUMBER);

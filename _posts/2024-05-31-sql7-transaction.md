---
layout: single
title: 2024/05/31/ SQL- 07 - Transaction
---

---
# Tracsaction 
--- 

- TCL (TRANSACTION CONTROL LANGUAGE): Transaction Control Language
- SQL commands that apply the stored SQL commands in a transaction to the actual table or cancel them without applying to the table

- Transaction (TRANSACTION): A logical unit of work in a client connection environment (session - SESSION) where SQL commands are written and sent to the connected DBMS server
- The DBMS server receiving the SQL commands stores them in the transaction and uses them for querying without immediately applying them to the actual table

- To apply the SQL commands stored in the transaction to the actual table, commit (COMMIT) is needed - the transaction is initialized after commit
- 1. Automatic commit occurs if the current session is properly disconnected from the DBMS server
- 2. Automatic commit occurs if a DDL command or DCL command is written and sent to the DBMS server in the current session - Only DML commands are stored in the transaction
- 3. DML commands stored in the DBMS server's transaction are committed only when the COMMIT command is sent
- To clear the SQL commands stored in the transaction without applying them to the actual table, rollback (ROLLBACK) is needed
- 1. Automatic rollback occurs if the current session is abnormally disconnected from the DBMS server
- 2. DML commands stored in the DBMS server's transaction are rolled back only when the ROLLBACK command is sent
- Delete department information with department number 50 from the DEPT table

```
SELECT * FROM DEPT;
```

- When the DELETE command is sent to the DBMS server, the DELETE command is stored in the transaction without deleting the row from the table

```
DELETE FROM DEPT WHERE DEPTNO=50;
```

- Executing the SELECT command will provide the query results with the DELETE command stored in the transaction applied to the table rows

```
SELECT * FROM DEPT;
```

- Use the ROLLBACK command to perform a rollback
```
ROLLBACK;
SELECT * FROM DEPT;
```
- Delete department information with department number 50 from the DEPT table

``DELETE FROM DEPT WHERE DEPTNO=50;- The DELETE command is stored in the transaction

- Use the COMMIT command to perform a commit - the transaction is initialized after commit

```
COMMIT;- The DELETE command stored in the transaction is applied to the actual table
```

```
SELECT * FROM DEPT;
```

- Use transactions to rollback in case of erroneous DML commands issued in the current session to recover data 
- Data Integrity: Ensuring no abnormal values are stored in the table, providing correct query results


```
SELECT * FROM EMP;
DELETE FROM EMP;- Delete all rows stored in the EMP table
SELECT * FROM EMP;
ROLLBACK;
SELECT * FROM EMP;
```

- Using transactions to ensure that rows of the table being worked on in the current session are available for query in other sessions until commit
- Data Consistency: Providing the same query results to all clients connected to the DBMS server

- Change the salary of the employee named KIM in the BONUS table to 4000
```
SELECT * FROM BONUS;
UPDATE BONUS SET SAL=4000 WHERE ENAME='KIM';
SELECT * FROM BONUS;
```

- Until the current session commits, other sessions will get the existing row's query results - if the BONUS table is queried from another session, 
- the salary of the employee named KIM will appear as 3500

- When the COMMIT command is issued in the current session, the DML commands are applied to the actual table, and the updated query results will be available in other sessions
COMMIT;

- Using transactions to provide a data lock function
- DBMS provides a multi-user environment - server programs can be accessed by multiple clients using network functionality
- Clients connected to the DBMS server can manipulate rows in the same table if they have table access rights
- To prevent other clients from working on the same table rows being processed by a client, the transaction provides a data lock function

- Change the salary of the employee named ALLEN in the BONUS table to 2000
```
SELECT * FROM BONUS;
UPDATE BONUS SET SAL=2000 WHERE ENAME='ALLEN';- Activates the data lock function for the rows stored in the table due to the transaction
SELECT * FROM BONUS;
```
- Using another session, change the commission of the employee named ALLEN in the BONUS table to 50% of the salary
- UPDATE BONUS SET COMM=SAL*0.5 WHERE ENAME='ALLEN';
- If another session attempts to manipulate the rows being processed by the current session, the execution in the other session is temporarily halted due to the data lock function by the transaction 

- Only after committing or rolling back the commands stored in the transaction of the current session, the data lock function is deactivated, allowing execution of commands in other sessions
```
COMMIT;
```

- SAVEPOINT: A command to label (store position information) in the transaction
- Syntax: SAVEPOINT label_name
- Using the SAVEPOINT command to label the transaction allows rolling back only the desired DML commands

- Delete the employee named ALLEN from the BONUS table
```
SELECT * FROM BONUS;
DELETE FROM BONUS WHERE ENAME='ALLEN';
SELECT * FROM BONUS;
```
- Delete the employee named MARTIN from the BONUS table
```
DELETE FROM BONUS WHERE ENAME='MARTIN';
SELECT * FROM BONUS;
```
- Rollback processing
```
ROLLBACK;- Clear all DML commands stored in the transaction
SELECT * FROM BONUS;
```
- Delete the employee named ALLEN from the BONUS table
```
DELETE FROM BONUS WHERE ENAME='ALLEN';
SELECT * FROM BONUS;
SAVEPOINT ALLEN_DELETE_AFTER;
```
- Delete the employee named MARTIN from the BONUS table
```
DELETE FROM BONUS WHERE ENAME='MARTIN';
SELECT * FROM BONUS;
```
- Rollback using the label name provided by the SAVEPOINT command
- Syntax: ROLLBACK TO label_name
```
ROLLBACK TO ALLEN_DELETE_AFTER;- Delete the DML commands below the label provided by the SAVEPOINT command
SELECT * FROM BONUS;
```

- Clear all DML commands in the transaction
```
ROLLBACK;
SELECT * FROM BONUS;
```

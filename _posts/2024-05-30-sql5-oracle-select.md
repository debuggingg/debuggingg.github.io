---
layout: single
title:  "2024/05/30/ SQL- 05 -Select-SubQuery - Set"
---


# SubQuery type from Select
---
- Types of subqueries used in the SELECT command
- 1. Scalar Subquery: Subquery used in SELECT, WHERE, GROUP BY, HAVING, ORDER BY keywords
- It's an SQL command but internally treated the same as a function - functions return only one result even if given multiple values
- Scalar subqueries are also like functions, so they can be nested, but if the result of the subquery has more than one value or different data types, an error occurs
- Overuse of scalar subqueries for large data processing can cause performance degradation, so it's recommended to use table joins instead
- 2. Inline View Subquery: Subquery used in the FROM keyword
- Inline View: A virtual table (view) temporarily created using a subquery
- Reduces the number of table joins and adds procedural features

- Search for employee number, employee name, and salary of all employees stored in the EMP table

```SELECT EMPNO,ENAME,SAL FROM EMP;```

- Search using a subquery in the FROM keyword - inline view subquery

```SELECT EMPNO,ENAME,SAL FROM (SELECT * FROM EMP);```

```SELECT EMPNO,ENAME,SAL FROM (SELECT * FROM EMP WHERE DEPTNO=10);```

```SELECT * FROM (SELECT EMPNO,ENAME,SAL FROM EMP WHERE DEPTNO=10);```

- ROWNUM: A keyword that sequentially provides numerical values (indexes) to the searched rows - row number

```SELECT ROWNUM,EMPNO,ENAME,SAL FROM (SELECT EMPNO,ENAME,SAL FROM EMP);```

- Using the [*] symbol to represent all columns with other search targets results in an error

```SELECT ROWNUM,* FROM (SELECT EMPNO,ENAME,SAL FROM EMP);```

- It is possible to give a table alias to an inline view created by a subquery
- If you want to search for all columns of the table along with other search targets, use the format [table_name.*] to search for all columns of the table

```SELECT ROWNUM,TEMP.* FROM (SELECT EMPNO,ENAME,SAL FROM EMP) TEMP;```

- Search for employee number, employee name, and salary of all employees stored in the EMP table, sorted in descending order by salary

```SELECT EMPNO,ENAME,SAL FROM EMP ORDER BY SAL DESC;```

- Search for employee number, employee name, and salary of all employees stored in the EMP table, providing row numbers to the searched rows, sorted in descending order by salary

```SELECT ROWNUM,EMPNO,ENAME,SAL FROM EMP ORDER BY SAL DESC;```- Search error: providing row numbers to all rows and then sorting

- Use an inline view subquery to create an inline view with sorted member information and then provide row numbers to search

```SELECT ROWNUM,EMPNO,ENAME,SAL FROM (SELECT EMPNO,ENAME,SAL FROM EMP ORDER BY SAL DESC);```

```SELECT ROWNUM,TEMP.* FROM (SELECT EMPNO,ENAME,SAL FROM EMP ORDER BY SAL DESC) TEMP;```

- Search for employee number, employee name, and salary of the top 5 highest paid employees stored in the EMP table
- Sort by salary in descending order, then provide row numbers to use in the condition

```SELECT ROWNUM,TEMP.* FROM (SELECT EMPNO,ENAME,SAL FROM EMP ORDER BY SAL DESC) TEMP WHERE ROWNUM<=5;```

- Search for employee number, employee name, and salary of the 4th highest paid employee stored in the EMP table
- When using the ROWNUM keyword in the WHERE clause, conditions using < or <= operators can be written, but conditions using =, >, >= operators cannot be written - ROWNUM keyword sequentially provides row numbers

```SELECT ROWNUM,TEMP.* FROM (SELECT EMPNO,ENAME,SAL FROM EMP ORDER BY SAL DESC) TEMP WHERE ROWNUM=4;```- Search error

- Create an inline view with sorted rows and then provide row numbers to search
- Use the column alias instead of the ROWNUM keyword in the inline view to write conditions

```SELECT * FROM (SELECT ROWNUM RN,TEMP.* FROM (SELECT EMPNO,ENAME,SAL FROM EMP ORDER BY SAL DESC) TEMP) WHERE RN=4;```

- Search for employee number, employee name, and salary of the 6th to 10th highest paid employees stored in the EMP table
-  This is super important bc it is used paging ( you can see at  review pages number)

```SELECT * FROM (SELECT ROWNUM RN,TEMP.* FROM (SELECT EMPNO,ENAME,SAL FROM EMP ORDER BY SAL DESC) TEMP) WHERE RN BETWEEN 6 AND 10;```

---
# Set
---

- Set operators: Operators that provide set results using the search results of two SELECT commands
- Union (UNION), Intersection (INTERSECT), Difference (MINUS)

- Create the SUPER_HERO table - attributes: name (character type)

```CREATE TABLE SUPER_HERO(NAME VARCHAR2(20) PRIMARY KEY);```

- Insert and store rows in the SUPER_HERO table

```
INSERT INTO SUPER_HERO VALUES('Superman');
INSERT INTO SUPER_HERO VALUES('Ironman');
INSERT INTO SUPER_HERO VALUES('Batman');
INSERT INTO SUPER_HERO VALUES('Antman');
INSERT INTO SUPER_HERO VALUES('Spiderman');
SELECT * FROM SUPER_HERO;```
COMMIT;
```

- Create the MARVLE_HERO table - attributes: name (character type), grade (numeric type)

```CREATE TABLE MARVLE_HERO(NAME VARCHAR2(20) PRIMARY KEY,GRADE NUMBER(1));```

- Insert and store rows in the MARVLE_HERO table

```
INSERT INTO MARVLE_HERO VALUES('Ironman',3);
INSERT INTO MARVLE_HERO VALUES('Hulk',1);
INSERT INTO MARVLE_HERO VALUES('Spiderman',4);
INSERT INTO MARVLE_HERO VALUES('Thor',2);
INSERT INTO MARVLE_HERO VALUES('Antman',5);
SELECT * FROM MARVLE_HERO;```
COMMIT;

```
- UNION: An operator that provides the combined results of two SELECT commands - excludes duplicate rows
- Format) SELECT search_target,... FROM table_name1 UNION SELECT search_target,... FROM table_name2
- The rows searched by the two SELECT commands must match the number and data types of the search targets

```SELECT NAME FROM SUPER_HERO UNION SELECT NAME FROM MARVLE_HERO;```

- UNION ALL: An operator that provides the combined results of two SELECT commands - includes duplicate rows
- Format) SELECT search_target,... FROM table_name1 UNION ALL SELECT search_target,... FROM table_name2

```SELECT NAME FROM SUPER_HERO UNION ALL SELECT NAME FROM MARVLE_HERO;```

- INTERSECT: An operator that provides only the duplicate rows from the search results of two SELECT commands
- Format) SELECT search_target,... FROM table_name1 INTERSECT SELECT search_target,... FROM table_name2

```SELECT NAME FROM SUPER_HERO INTERSECT SELECT NAME FROM MARVLE_HERO;```

- MINUS: An operator that provides the remaining rows after excluding the rows searched by the second SELECT command from the rows searched by the first SELECT command
- Format) SELECT search_target,... FROM table_name1 MINUS SELECT search_target,... FROM table_name2

```SELECT NAME FROM SUPER_HERO MINUS SELECT NAME FROM MARVLE_HERO;```

- An error occurs if the number or data type of the search targets are different when using set operators

```SELECT NAME FROM SUPER_HERO UNION SELECT NAME,GRADE FROM MARVLE_HERO;```- Error due to different number of search targets

```SELECT NAME FROM SUPER_HERO UNION SELECT GRADE FROM MARVLE_HERO;```- Error due to different data types of search targets

- When the number of search targets is different, use arbitrary values of the same data type or NULL to search

```SELECT NAME,0 FROM SUPER_HERO UNION SELECT NAME,GRADE FROM MARVLE_HERO;```

```SELECT NAME,NULL FROM SUPER_HERO UNION SELECT NAME,GRADE FROM MARVLE_HERO;```

- When the data types of the search targets are different, use conversion functions to convert them to the same data type

```SELECT NAME FROM SUPER_HERO UNION SELECT TO_CHAR(GRADE,'0') FROM MARVLE_HERO;```

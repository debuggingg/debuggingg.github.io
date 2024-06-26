---
layout: category
title: 2024/05/28/ SQL- 03 -Select-Function - 환경설정(setting)
permalink: /categories/sql
taxonomy: SQL
---
![[bear-ezgif.com-resize.gif]]

# Function

  TOTAL SETTING-> TOOL(WINDOWS FRAME),환경설정,DATABASE,NLS 

  JUST SESSION CHANGE -> ALTER SESSION SET NLS_LANGUAGE='AMERICAN';

---
## CharacterFucntion
---
- Function: Provides the capability to process values passed as parameters and return the result
- Oracle's built-in functions: Classified into single-row functions and group functions
- Single-row functions: Functions that process and return a result for a single value passed as a parameter - character functions, numeric functions, date functions, conversion functions, general functions
- Group functions: Functions that process and return a result for multiple values passed as parameters

- Character functions: Functions that process and return a result for character values passed as parameters

- UPPER(character_value): Function that converts the character value to uppercase and returns it
- LOWER(character_value): Function that converts the character value to lowercase and returns it
  

``` SELECT ENAME,UPPER(ENAME),LOWER(ENAME) FROM EMP; ``` 

- Search employee number, employee name, salary of the employee whose name is ALLEN from EMP table
- Character values are compared case-sensitively

``` SELECT EMPNO,ENAME,SAL FROM EMP WHERE ENAME='ALLEN'; ``` 

``` SELECT EMPNO,ENAME,SAL FROM EMP WHERE ENAME='allen'; ``` 

- Use UPPER function or LOWER function to compare character values case-insensitively

``` SELECT EMPNO,ENAME,SAL FROM EMP WHERE UPPER(ENAME)=UPPER('allen'); ``` 

``` SELECT EMPNO,ENAME,SAL FROM EMP WHERE LOWER(ENAME)=LOWER('allen'); ``` 

- INITCAP(character_value): Function that converts only the first character to uppercase and the remaining characters to lowercase and returns it

``` SELECT ENAME,INITCAP(ENAME) FROM EMP; ``` 

- CONCAT(character_value, character_value): Function that concatenates and returns two character values passed as parameters
- || symbol can be used to concatenate character values, date values, numeric values

``` SELECT ENAME,JOB,CONCAT(ENAME,JOB),ENAME||JOB FROM EMP; ``` 

- SUBSTR(character_value, start_position, length): Function that extracts and returns a specified number of characters from the character value starting at the given position (index)

``` SELECT EMPNO,ENAME,JOB,SUBSTR(JOB,6,3) FROM EMP WHERE EMPNO=7499; ``` 

- LENGTH(character_value): Function that returns the number of characters in the character value

``` SELECT EMPNO,ENAME,JOB,LENGTH(JOB) FROM EMP WHERE EMPNO=7499; ``` 

- INSTR(character_value, search_character_value, start_position, occurrence): Function that searches for the search_character_value in the character value starting at the given position (index) and returns the position of the specified occurrence - returns 0 if the search_character_value is not found

``` SELECT EMPNO,ENAME,JOB,INSTR(JOB,'A',1,2) FROM EMP WHERE EMPNO=7499; ``` 

``` SELECT EMPNO,ENAME,JOB,INSTR(JOB,'XYZ',1,1) FROM EMP WHERE EMPNO=7499; ``` 

- LPAD(character_value, length, pad_character): Function that allocates space for the specified length, fills the right side with the character value, and fills the remaining space on the left with the pad_character and returns it
- RPAD(character_value, length, pad_character): Function that allocates space for the specified length, fills the left side with the character value, and fills the remaining space on the right with the pad_character and returns it

``` SELECT EMPNO,ENAME,SAL,LPAD(SAL,8,'*'),RPAD(SAL,8,'*') FROM EMP; ``` 

- TRIM({LEADING|TRAILING} trim_character FROM character_value):
  Function that deletes the leading or trailing trim_characters from the character value and returns it

``` SELECT EMPNO,ENAME,JOB,TRIM(LEADING 'S' FROM JOB),TRIM(TRAILING 'N' FROM JOB) FROM EMP WHERE EMPNO=7499; ``` 

- REPLACE(character_value, search_character_value, replace_character_value): Function that finds the search_character_value in the character value and replaces it with the replace_character_value and returns it

``` SELECT EMPNO,ENAME,JOB,REPLACE(JOB,'MAN','PERSON') FROM EMP WHERE EMPNO=7499; ``` 

---
## NumberFunction
---
- Numeric functions: Functions that process and return a result for numeric values passed as parameters

- ROUND(numeric_value, decimal_places): Function that rounds the numeric value to the specified number of decimal places and returns it
- DUAL table: A virtual table used when a  SELECT command does not reference any stored rows in a table

``` SELECT ROUND(45.582,2),ROUND(45.582,0),ROUND(45.582,-1) FROM DUAL; ``` 

- TRUNC(numeric_value, decimal_places): Function that truncates the numeric value to the specified number of decimal places and returns it

``` SELECT TRUNC(45.582,2),TRUNC(45.582,0),TRUNC(45.582,-1) FROM DUAL; ``` 

- CEIL(numeric_value): Function that returns the next integer greater than or equal to the given numeric value if it's a decimal value

``` SELECT CEIL(15.3),CEIL(-15.3) FROM DUAL; ``` 

- FLOOR(numeric_value): Function that returns the next integer less than or equal to the given numeric value if it's a decimal value

``` SELECT FLOOR(15.3),FLOOR(-15.3) FROM DUAL; ``` 

- MOD(numeric_value1, numeric_value2): Function that returns the remainder of numeric_value1 divided by numeric_value2

``` SELECT 20/8,MOD(20,8) FROM DUAL; ``` 

- POWER(numeric_value1, numeric_value2): Function that returns the result of numeric_value1 raised to the power of numeric_value2

``` SELECT 3*3*3*3*3,POWER(3,5) FROM DUAL; ``` 


---
## DateFunction
---
- Date functions: Functions that process and return a result for date values passed as parameters

- SYSDATE: Keyword that provides the current date and time of the system

- SELECT command is provided in the [RR/MM/DD] pattern

``` SELECT SYSDATE FROM DUAL; ``` 

- ADD_MONTHS(date_value, numeric_value): Function that returns the date value increased by the specified number of months

``` SELECT SYSDATE,ADD_MONTHS(SYSDATE,5) FROM DUAL; ``` 

- Using arithmetic operators + or - for date calculations

- date_value + numeric_value = date_value  >> increase in days

``` SELECT SYSDATE,SYSDATE+7 FROM DUAL; ``` 

- date_value + numeric_value/24 = date_value  >> increase in hours

``` SELECT SYSDATE,SYSDATE+100/24 FROM DUAL; ``` 

- date_value - numeric_value = date_value  >> decrease in days

``` SELECT SYSDATE,SYSDATE-7 FROM DUAL; ``` 

- date_value - numeric_value/24 = date_value  >> decrease in hours

``` SELECT SYSDATE,SYSDATE-100/24 FROM DUAL; ``` 

- date_value - date_value = numeric_value >> days (decimal value)
- Retrieve employee number, employee name, hire date, and duration of employment (current date - hire date) for all employees stored in the EMP table

``` SELECT EMPNO,ENAME,HIREDATE,SYSDATE-HIREDATE FROM EMP; ``` 

``` SELECT EMPNO,ENAME,HIREDATE,CEIL(SYSDATE-HIREDATE)||'일' AS Duration_of_employment FROM EMP; ``` 

- NEXT_DAY(date_value, day_of_week): Function that returns the date of the specified day of the week following the given date value

``` SELECT NEXT_DAY(SYSDATE, '토') FROM DUAL; ```  

- The language and date pattern, as well as currency units, can vary according to the client environment (session) connected to the Oracle server
- The language and date pattern, as well as currency units, can be changed according to the current client environment (session)

ALTER SESSION SET NLS_LANGUAGE='AMERICAN'; ``` - Change the language

``` SELECT NEXT_DAY(SYSDATE, 'SAT') FROM DUAL; ```  

ALTER SESSION SET NLS_LANGUAGE='KOREAN'; ``` - Change the language

``` SELECT NEXT_DAY(SYSDATE, '토') FROM DUAL; ```  

- TRUNC(date_value, range): Function that truncates the date value to the specified range and converts the remaining portion to the initial value, returning it

``` SELECT SYSDATE,TRUNC(SYSDATE,'MONTH'),TRUNC(SYSDATE,'YEAR') FROM DUAL; ``` 

---
## ConversionFunction
---
- Conversion functions: Functions that convert the value passed as a parameter to the desired data type and return it

- TO_NUMBER(character_value): Function that converts the character value to a numeric value and returns it
- If the character value passed as a parameter contains non-numeric characters, it cannot be converted to a numeric value and will cause an error

- Retrieve employee number, employee name, and salary for the employee with employee number 7499 from the EMP table

``` SELECT EMPNO,ENAME,SAL FROM EMP WHERE EMPNO=7499; ``` 

- If the data type of the comparison column is numeric and the comparison value is a character value, use the TO_NUMBER function to convert the character value to a numeric value for comparison - explicit type conversion

``` SELECT EMPNO,ENAME,SAL FROM EMP WHERE EMPNO=TO_NUMBER('7499'); ``` 

- If the data type of the comparison column is numeric and the comparison value is a character value, it is automatically converted to a numeric value for comparison - implicit type conversion

``` SELECT EMPNO,ENAME,SAL FROM EMP WHERE EMPNO='7499'; ``` 

- When performing arithmetic operations on character values, they are automatically converted to numeric values for the operation - implicit type conversion

``` SELECT 100+200 FROM DUAL; ``` 

``` SELECT '100'+'200' FROM DUAL; ``` 

- Retrieve employee number, employee name, salary, and post-tax salary (salary*0.9) for the employee with employee number 7839 from the EMP table

``` SELECT EMPNO,ENAME,SAL,SAL*0.9 FROM EMP WHERE EMPNO=7839; ``` 

``` SELECT EMPNO,ENAME,SAL,SAL*TO_NUMBER('0.9') FROM EMP WHERE EMPNO=7839; ``` 

``` SELECT EMPNO,ENAME,SAL,SAL*'0.9' FROM EMP WHERE EMPNO=7839; ``` 

- TO_DATE(character_value[, date_pattern]): Function that converts the character value to a date value according to the specified date pattern and returns it
- If no date pattern is specified, the default [RR/MM/DD] date pattern is used
- If the character value does not match the date pattern, an error occurs

- Retrieve employee number, employee name, and hire date for the employee who was hired on January 23, 1982, from the EMP table
- If the data type of the comparison column is date and the comparison value is a character value, use the TO_DATE function to convert the character value to a date value for comparison - explicit type conversion

``` SELECT EMPNO,ENAME,HIREDATE FROM EMP WHERE HIREDATE=TO_DATE('82/01/23'); ``` 

- Use the [YYYY-MM-DD] date pattern instead of the [RR/MM/DD] date pattern

``` SELECT EMPNO,ENAME,HIREDATE FROM EMP WHERE HIREDATE=TO_DATE('1982-01-23'); ``` 

- If the data type of the comparison column is date and the comparison value is a character value, it is automatically converted to a date value for comparison - implicit type conversion

``` SELECT EMPNO,ENAME,HIREDATE FROM EMP WHERE HIREDATE='82/01/23'; ``` 

``` SELECT EMPNO,ENAME,HIREDATE FROM EMP WHERE HIREDATE='1982-01-23'; ``` 

- If the character value does not match the default date pattern, an error occurs

``` SELECT EMPNO,ENAME,HIREDATE FROM EMP WHERE HIREDATE=TO_DATE('01-23-1982'); ``` 

``` SELECT EMPNO,ENAME,HIREDATE FROM EMP WHERE HIREDATE='01-23-1982'; ``` 

- Convert the character value to a date value by passing the matching date pattern as a parameter for comparison

``` SELECT EMPNO,ENAME,HIREDATE FROM EMP WHERE HIREDATE=TO_DATE('01-23-1982','MM-DD-YYYY'); ``` 

- Calculate the number of days a person born on January 1, 2000, has lived until today
- Using arithmetic operators on the search target will automatically convert the character value to a numeric value for calculation instead of a date value

``` SELECT SYSDATE-'2000-01-01' FROM DUAL; ``` - Error occurs if non-numeric characters are included

- Use the TO_DATE function to convert the character value to a date value for date calculations

``` SELECT SYSDATE-TO_DATE('2000-01-01') FROM DUAL; ``` 

``` SELECT CEIL(SYSDATE-TO_DATE('2000-01-01'))||'days' AS "Days lived until today" FROM DUAL; ``` 

- TO_CHAR({numeric_value|date_value}, pattern_character_value):
  Function that converts the numeric value or date value passed as a parameter to the desired pattern of character value and returns it

- Date pattern: RR(year), YYYY(year), MM(month), DD(day), HH24(hour-1~24), HH12(hour-1~12), MI(minute), SS(second)

``` SELECT SYSDATE,TO_CHAR(SYSDATE,'YYYY-MM-DD'),TO_CHAR(SYSDATE,'YYYY-MM-DD HH24:MI:SS') FROM DUAL; ``` 

- Retrieve employee number, employee name, and hire date for employees who were hired in 1981 from the EMP table
- Use the TO_CHAR function to convert the date value to the desired pattern of character value for comparison

``` SELECT EMPNO,ENAME,HIREDATE FROM EMP WHERE TO_CHAR(HIREDATE,'YYYY')='1981'; ``` 

- Numeric pattern: 9(numeric or space), 0(numeric), L(currency), $(dollar)

``` SELECT 10000000000,TO_CHAR(10000000000,'999,999,999,990') FROM DUAL; ``` 

- If the numeric value cannot be represented by the pattern character, it is converted and returned with the [#] symbol

``` SELECT 10000000000,TO_CHAR(10000000000,'9,999,999,990') FROM DUAL; ``` - Search error

- Retrieve employee number, employee name, and salary for the employee with employee number 7844 from the EMP table

``` SELECT EMPNO,ENAME,SAL FROM EMP WHERE EMPNO=7844; ``` 

``` SELECT EMPNO,ENAME,TO_CHAR(SAL,'999,990') SAL FROM EMP WHERE EMPNO=7844; ``` 

``` SELECT EMPNO,ENAME,TO_CHAR(SAL,'L99,990') SAL FROM EMP WHERE EMPNO=7844; ``` 

``` SELECT EMPNO,ENAME,TO_CHAR(SAL,'$99,990.00') SAL FROM EMP WHERE EMPNO=7844; ``` 

---
## GeneralFunctions
---

- General functions: Functions that convert and return the value passed as a parameter to a changed value if a certain condition is true (TRUE)

- NVL(original_value, replacement_value): Function that converts and returns the replacement value if the original value is NULL
- The replacement value can only be changed to a value of the same data type as the original value - an error occurs if the data type of the replacement value is different from that of the original value

- Retrieve employee number, employee name, and annual salary (salary*12) for all employees stored in the EMP table

``` SELECT EMPNO,ENAME,SAL*12 ANNUAL FROM EMP; ``` 

- Retrieve employee number, employee name, and annual salary ((salary+commission)*12) for all employees stored in the EMP table
- If the commission (COMM column value) is NULL, the calculation is not possible, and the result will be NULL

``` SELECT EMPNO,ENAME,(SAL+COMM)*12 ANNUAL FROM EMP; ``` - Search error

- Use the NVL function to return [0] if the commission (COMM column value) is NULL and perform the calculation

``` SELECT EMPNO,ENAME,(SAL+NVL(COMM,0))*12 ANNUAL FROM EMP; ``` 

- NVL2(original_value, replacement_value1, replacement_value2): Function that converts and returns replacement_value1 if the original_value is not NULL and converts and returns replacement_value2 if the original_value is NULL

- Retrieve employee number, employee name, and annual salary ((salary + commission) * 12) for all employees stored in the EMP table

``` SELECT EMPNO,ENAME,(SAL+NVL2(COMM,COMM,0))*12 ANNUAL FROM EMP; ``` 

``` SELECT EMPNO,ENAME,NVL2(COMM,COMM+SAL,SAL)*12 ANNUAL FROM EMP; ``` 

- DECODE(original_value, compare_value1, replacement_value1, compare_value2, replacement_value2,...[, default_value]): Function that compares the original_value with the compare_values in sequence and converts and returns the replacement_value if the original_value matches the compare_value
- If the original_value does not match any compare_values, it is converted to and returns the default_value, but if the default_value is omitted, it returns NULL

- Retrieve employee number, employee name, job, salary, and actual salary by job (actual salary to be paid based on the job of the employee) for all employees stored in the EMP table
- Actual salary by job: ANALYST: salary*1.1, CLERK: salary*1.2, MANAGER: salary*1.3, PRESIDENT: salary*1.4, SALESMAN: salary*1.5

``` SELECT EMPNO,ENAME,JOB,SAL,DECODE(JOB,'ANALYST',SAL*1.1,'CLERK',SAL*1.2,'MANAGER',SAL*1.3,'PRESIDENT',SAL*1.4,'SALESMAN',SAL*1.5,SAL) "Actual Salary by Job" FROM EMP; ``` 

- Retrieve employee number, employee name, job, and salary, but search the salary by job and return NULL for non-matching jobs

``` SELECT EMPNO,ENAME,JOB,SAL FROM EMP; ``` 

``` SELECT EMPNO,ENAME,DECODE(JOB,'ANALYST',SAL) ANALYST,DECODE(JOB,'CLERK',SAL) CLERK ,DECODE(JOB,'MANAGER',SAL) MANAGER,DECODE(JOB,'PRESIDENT',SAL) PRESIDENT,DECODE(JOB,'SALESMAN',SAL) SALESMAN FROM EMP; ``` 

---
## GroupFunction
---

- Group functions: Functions that process multiple values passed as parameters and return the result

- COUNT function, MAX function, MIN function, SUM function, AVG function, etc.

- COUNT(column_name): Function that receives the column values of the rows stored in the table as a parameter and returns the count of the column values

``` SELECT COUNT(EMPNO) FROM EMP; ``` 

- If the group function is used with other search targets and the number of returned values from the group function is different from the number of rows of the search target, an error occurs

``` SELECT COUNT(EMPNO),EMPNO FROM EMP; ``` - Error occurs

- Since NULL is not a value, group functions return the result excluding NULL

``` SELECT COUNT(EMPNO),COUNT(COMM) FROM EMP; ``` 

- The COUNT function can use the [*] symbol instead of the column name to return the count of search rows using all column values

``` SELECT COUNT(*) FROM EMP; ``` - Returns the count of search rows stored in the table

- MAX(column_name): Function that receives the column values of the rows stored in the table as a parameter and returns the maximum value

``` SELECT MAX(SAL) FROM EMP; ``` 

``` SELECT MAX(ENAME) FROM EMP; ``` 

``` SELECT MAX(HIREDATE) FROM EMP; ``` 

- MIN(column_name): Function that receives the column values of the rows stored in the table as a parameter and returns the minimum value

``` SELECT MIN(SAL) FROM EMP; ``` 

``` SELECT MIN(ENAME) FROM EMP; ``` 

``` SELECT MIN(HIREDATE) FROM EMP; ``` 

- SUM(column_name): Function that receives the numeric column values of the rows stored in the table as a parameter, calculates the sum, and returns it

``` SELECT SUM(SAL) FROM EMP; ``` 

- AVG(column_name): Function that receives the numeric column values of the rows stored in the table as a parameter, calculates the average, and returns it

``` SELECT AVG(SAL) FROM EMP; ``` 

``` SELECT ROUND(AVG(SAL),2) FROM EMP; ``` 

- Calculate and search the average commission by receiving the commission stored for all employees in the EMP table
- Group functions process using only column values that are not NULL, so search the average commission of employees whose commission is not NULL

``` SELECT AVG(COMM) FROM EMP; ``` - Search error

``` SELECT AVG(NVL(COMM,0)) FROM EMP; ``` 

``` SELECT CEIL(AVG(NVL(COMM,0))) "Average Commission" FROM EMP; ``` 

- Search the number of employees working in each department by distinguishing the rows (employee information) stored in the EMP table by department number

``` SELECT COUNT(*) FROM EMP WHERE DEPTNO=10; ``` - Search the number of employees working in department 10

``` SELECT COUNT(*) FROM EMP WHERE DEPTNO=20; ``` - Search the number of employees working in department 20

``` SELECT COUNT(*) FROM EMP WHERE DEPTNO=30; ``` - Search the number of employees working in department 30

- GROUP BY: Keyword that provides the functionality to divide multiple groups for searching by distinguishing rows by column values when using group functions in the search target
- If the column values of rows are the same, they are grouped together and searched - the number of search rows is the same as the number of groups
- Format) ``` SELECT group_function(column_name)[, search_target, ...] FROM table_name [WHERE condition]
     GROUP BY {column_name|expression|function}, {column_name|expression|function}, ...  ORDER BY {column_name|expression|function|alias|COLUMN_INDEX} {ASC|DESC}, ...

- Group the rows (employee information) stored in the EMP table by department number and search the number of employees

``` SELECT COUNT(*) FROM EMP GROUP BY DEPTNO; ``` 

- Expressions (column_name|expression|function) used in the GROUP BY keyword can also be used as search targets along with group functions

``` SELECT DEPTNO,COUNT(*) FROM EMP GROUP BY DEPTNO; ``` 

``` SELECT DEPTNO,COUNT(*) FROM EMP GROUP BY DEPTNO ORDER BY DEPTNO; ``` 

``` SELECT DEPTNO DNO,COUNT(*) FROM EMP GROUP BY DNO; ``` - Error occurs: Cannot set a group using aliases

- Group all employees stored in the EMP table by job and search the number of employees, total salary, and average salary

``` SELECT JOB,COUNT(*) CNT,SUM(SAL) SUM_SAL,CEIL(AVG(SAL)) AVG_SAL FROM EMP GROUP BY JOB; ``` 

``` SELECT JOB,COUNT(*) CNT,SUM(SAL) SUM_SAL,CEIL(AVG(SAL)) AVG_SAL FROM EMP GROUP BY JOB ORDER BY CNT; ``` 

- Group employees by job, excluding those whose job is PRESIDENT, and search the total salary

``` SELECT JOB,SUM(SAL) FROM EMP WHERE JOB<>'PRESIDENT' GROUP BY JOB; ``` 

- HAVING: Keyword that provides the functionality to search only groups where the condition is true (TRUE) from the groups created by the GROUP BY keyword
- Use the column name or group function used as the search target in the SELECT keyword to write the condition of the HAVING keyword
- Format) SELECT group_function(column_name)[, search_target, ...] FROM table_name [WHERE condition]
    GROUP BY {column_name|expression|function}, {column_name|expression|function}, ... HAVING group_condition     ORDER BY {column_name|expression|function|alias|COLUMN_INDEX} {ASC|DESC}, ...

- Group employees by job, excluding those whose job is PRESIDENT, and search the total salary

``` SELECT JOB,SUM(SAL) FROM EMP GROUP BY JOB HAVING JOB<>'PRESIDENT'; ``` 

- Group all employees stored in the EMP table by department number and search the department number and total salary for departments with a total salary of 9000 or more

``` SELECT DEPTNO,SUM(SAL) FROM EMP GROUP BY DEPTNO HAVING SUM(SAL)>=9000; ``` 


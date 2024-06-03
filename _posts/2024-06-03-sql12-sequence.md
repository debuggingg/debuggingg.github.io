---
layout: single
title: 2024/06/03/ SQL- Sequence
---
---
# Sequence
---
-- Sequence: an object for storing numeric values - can use automatically incremented numeric values

-- Creating a sequence
-- Format) CREATE SEQUENCE sequence_name [START WITH initial_value] [INCREMENT BY increment_value]  [MAXVALUE max_value] [MINVALUE min_value] [CYCLE] [CACHE count]
-- START WITH initial_value: sets the initial value stored in the sequence - omitted: NULL
-- INCREMENT BY increment_value: sets the automatically incremented numeric value by the sequence - omitted: 1
-- MAXVALUE max_value: the maximum value that can be stored in the sequence - omitted: the maximum value representable by Oracle
-- MINVALUE min_value: the minimum value that can be stored in the sequence - omitted: 1
-- CYCLE: an option to set the sequence to store values starting from the minimum value again if it exceeds the maximum value
-- CACHE count: sets the number of automatically incremented values that can be pre-generated and stored in temporary memory - omitted: 20

-- Creating the BOARD1 table - attributes: post number (numeric - PRIMARY KEY), writer (string), content (string)
```
CREATE TABLE BOARD1(NO NUMBER CONSTRAINT BOARD1_NO_PK PRIMARY KEY, WRITER VARCHAR2(50), CONTENT VARCHAR2(1000));
```
-- Inserting and storing rows in the BOARD1 table
```
INSERT INTO BOARD1 VALUES(1, 'Hong Gil-dong', 'This is the first post.');
SELECT * FROM BOARD1;
COMMIT;
```
-- Creating the BOARD1_SEQ sequence - created to provide automatically incremented values for the NO column in the BOARD1 table
```
CREATE SEQUENCE BOARD1_SEQ;
```
-- USER_SEQUENCES: a dictionary to provide information related to sequences
```
SELECT SEQUENCE_NAME, MAX_VALUE, MIN_VALUE, INCREMENT_BY FROM USER_SEQUENCES;
```

-- Checking the numeric value stored in the sequence - use the SELECT command
-- Format) SELECT sequence_name.CURRVAL FROM DUAL;

-- Checking the numeric value stored in the BOARD1_SEQ sequence
```
SELECT BOARD1_SEQ.CURRVAL FROM DUAL; -- Error occurs: error occurs because there are no numeric values stored in the sequence - NULL
```

-- Providing the next value using the numeric value stored in the sequence - the next value is automatically stored in the sequence
-- Format) sequence_name.NEXTVAL
-- If there are no numeric values stored in the sequence, the minimum value is automatically provided and the minimum value is stored in the sequence
```
SELECT BOARD1_SEQ.NEXTVAL FROM DUAL;
```

-- Inserting and storing rows in the BOARD1 table - used to insert rows by providing automatically incremented values from the sequence
```
INSERT INTO BOARD1 VALUES(BOARD1_SEQ.NEXTVAL, 'Im Gkeok-jeong', 'This is the second post.');
INSERT INTO BOARD1 VALUES(BOARD1_SEQ.NEXTVAL, 'Jeon Woo-chi', 'This is the third post.');
SELECT * FROM BOARD1;
COMMIT;
SELECT BOARD1_SEQ.NEXTVAL FROM DUAL;
```
```
SELECT SEQUENCE_NAME, MAX_VALUE, MIN_VALUE, INCREMENT_BY FROM USER_SEQUENCES;
ALTER SEQUENCE BOARD1_SEQ MAXVALUE 9999 INCREMENT BY 3;
ALTER SEQUENCE BOARD1_SEQ MAXVALUE 9999 INCREMENT BY 1;
INSERT INTO BOARD1 VALUES(BOARD1_SEQ.NEXTVAL, 'Il Ji-mae', 'This is the fourth post.');
INSERT INTO BOARD1 VALUES(BOARD1_SEQ.NEXTVAL, 'Jang Bo-go', 'This is the fifth post.');
SELECT * FROM BOARD1;
```

```
DROP SEQUENCE BOARD1_SEQ;
```
```
CREATE TABLE BOARD2 (NO NUMBER GENERATED ALWAYS AS IDENTITY CONSTRAINT BOARD2_NO_PK PRIMARY KEY, WRITER VARCHAR2(50), CONTENT VARCHAR2(1000));
INSERT INTO BOARD2 VALUES(1, 'Hong Gil-dong', 'This is the first post.');
INSERT INTO BOARD2(WRITER, CONTENT) VALUES('Hong Gil-dong', 'This is the first post.');
INSERT INTO BOARD2(WRITER, CONTENT) VALUES('Im Gkeok-jeong', 'This is the second post.');
INSERT INTO BOARD2(WRITER, CONTENT) VALUES('Jeon Woo-chi', 'This is the third post.');
SELECT * FROM BOARD2;
COMMIT;

SELECT SEQUENCE_NAME, MAX_VALUE, MIN_VALUE, INCREMENT_BY FROM USER_SEQUENCES;
```
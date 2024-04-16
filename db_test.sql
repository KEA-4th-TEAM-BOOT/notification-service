CREATE OR REPLACE PROCEDURE ShowBookNamePrice AS
BEGIN
For rec IN (SELECT bookname, price FROM book WHERE publisher ='이상미디어') LOOP
    DBMS_OUTPUT.PUT_LINE('도서이름: "|| rec.bookname ||', 가격: ||rec.price);
END LOOP;
END:

/
SET SERVEROUTPUT ON;
EXEC ShowBookNamePrice;
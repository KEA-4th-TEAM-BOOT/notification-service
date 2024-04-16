CREATE TABLE Orders(
orderid NUMBER(2),
custid NUMBER(2),
bookid NUMBER(2),
saleprice NUMBER(8),
PRIMARY KEY(custid),
FOREIGN KEY(custid,bookid) REFERENCES Customers(custid, bookid));

-- 조인
CREATE TABLE 김밥천국(
	메뉴명  NVARCHAR2(10) NOT NULL, 
	가격   NUMBER(4,0)
);

INSERT INTO 김밥천국  VALUES ('김밥',2500);
INSERT INTO 김밥천국  VALUES ('쫄면', 6000);
INSERT INTO 김밥천국  VALUES ('돈가스', 7000);
INSERT INTO 김밥천국  VALUES ('오징어덮밥', 7000);
INSERT INTO 김밥천국  VALUES ('돌솥비빔밥', 7000);
INSERT INTO 김밥천국  VALUES ('오므라이스', 6500);
INSERT INTO 김밥천국  VALUES ('잔치국수', 6000);


CREATE TABLE 김밥나라(
	메뉴명 NVARCHAR2(10) NOT NULL, 
	가격  NUMBER(4,0)
);

INSERT INTO 김밥나라  VALUES ('김밥', 2000);
INSERT INTO 김밥나라  VALUES ('쫄면', 5500);
INSERT INTO 김밥나라  VALUES ('오징어덮밥', 7000);
INSERT INTO 김밥나라  VALUES ('치즈쫄볶이', 4000);
INSERT INTO 김밥나라  VALUES ('순두부찌개', 6000);


CREATE TABLE 우리분식(
	메뉴명 NVARCHAR2(10) NOT NULL, 
	가격  NUMBER(4,0)
);

INSERT INTO 우리분식  VALUES ('라면', 3000);
INSERT INTO 우리분식  VALUES ('쫄면', 5500);
INSERT INTO 우리분식  VALUES ('잔치국수', 4500);
INSERT INTO 우리분식  VALUES ('순두부찌개', 6000);
INSERT INTO 우리분식  VALUES ('햄버거', 7000);
-- 여기는 userCBT 화면입니다.

CREATE TABLE tbl_cbt(
    cb_num	CHAR(5)		    PRIMARY KEY,
    cb_que	nVARCHAR2(125)	NOT NULL,	
    cb_ex1	nVARCHAR2(125)	NOT NULL,	
    cb_ex2	nVARCHAR2(125)	NOT NULL,	
    cb_ex3	nVARCHAR2(125)	NOT NULL,	
    cb_ex4	nVARCHAR2(125)	NOT NULL,	
    cb_ans	nVARCHAR2(125)		
);


SELECT * FROM tbl_cbt;

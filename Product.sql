create table product --상품테이블
(
	no			number primary key,
	p_name		varchar2(30) not null,
	u_price		varchar2(20) 	not null, --입고가
	s_price		varchar2(20)  not null, --판매가
	ea				number default 0 not null,
	thumbnail	clob,
	regdate     	date default sysdate
);

create table member --회원 테이블
(
	no				number primary key,
	c_id			varchar2(20) unique not null, --아이디
	c_name		varchar2(30)    not null, --회사명
	pwd			varchar2(20) 	not null,
	regdate     	date default sysdate
);
drop table offer;

create table offer --출고요청 테이블
(
	ordernum	number primary key,
	no				number	not null constraint offer
								references product(no),
	ea				number default 0 not null,
	user_id		varchar2(20)	not null,
	memo 		varchar2(100),
	regdate		date	default sysdate
);

create sequence offer_seq
minvalue 1
maxvalue 9999
increment by 1
start with 11
nocache;
commit;

create or replace view v_product
as
select no, p_name, s_price, ea, thumbnail, regdate
from product;

create or replace view v_offer
as
select ordernum, no, ea, user_id, memo, regdate
from offer;




create table product --��ǰ���̺�
(
	no			number primary key,
	p_name		varchar2(30) not null,
	u_price		varchar2(20) 	not null, --�԰�
	s_price		varchar2(20)  not null, --�ǸŰ�
	ea				number default 0 not null,
	thumbnail	clob,
	regdate     	date default sysdate
);

create table member --ȸ�� ���̺�
(
	no				number primary key,
	c_id			varchar2(20) unique not null, --���̵�
	c_name		varchar2(30)    not null, --ȸ���
	pwd			varchar2(20) 	not null,
	regdate     	date default sysdate
);
drop table offer;

create table offer --����û ���̺�
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




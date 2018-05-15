select * from tabs

drop table member

drop table login

create table member(
eMail varchar2(30) primary key,
password varchar2(16) not null,
birth date not null,
name varchar2(15) not null
);

create table login(
eMail varchar2(30) primary key
);

drop table ticket

create table ticket(
email varchar2(30),
title varchar2(200),
ticketDate varchar2(30),
theater varchar2(50),
screen varchar2(30),
cinemaTime varchar2(30),
seat varchar2(240),
price number
);

select * from member

delete from member where email='thefates@gmail.com'
delete from member where email='abc@abc.net'

alter session set nls_date_format = 'YYYY-MM-DD';


select * from login

delete from login


 set nls_date_format = 'YYYY-MM-DD'

  scope=spfile;
  
  
  delete from TICKET
  select * from TICKET;
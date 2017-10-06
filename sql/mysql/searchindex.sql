#创建数据库
create schema  if not exists `searchIndex`;
#使用数据库
use searchIndex;
#删除数据库
drop schema if exists `searchIndex`;
#创建表
drop table if exists urllist;
create table if not exists urllist
(
	urlid int unsigned NOT NULL auto_increment,
    url char(255) not null,
    primary key (urlid, url)
);
drop table if exists wordlist;
create table if not exists wordlist
(
	wordid int unsigned NOT NULL auto_increment,
    word char(50) not null,
    primary key (wordid, word)
);
drop table if exists wordlocation;
create table if not exists wordlocation
(
	urlid int unsigned NOT NULL ,
    wordid int unsigned NOT NULL,
    location int unsigned NOT NULL
);
drop table if exists link;
create table if not exists link
(
	linkid int unsigned NOT NULL auto_increment,
    fromid int unsigned NOT NULL,
    toid int unsigned NOT NULL,
    primary key (linkid)
);
drop table if exists linkwords;
create table if not exists linkwords
(
	wordid int unsigned not null,
	linkid int unsigned NOT NULL
);

use searchIndex;
select * from wordlocation;
select * from wordlist;
select * from urllist;
select * from link;
select * from linkwords;

truncate table wordlist;
truncate table urllist;
truncate table wordlocation;
truncate table link;
truncate table linkwords;


#select count(w1.urlid, w1.location, w2.location
select count(w1.urlid)
from wordlocation as w1, wordlocation as w2
where w1.urlid = w2.urlid 
and w1.wordid = 1
and w2.wordid = 2

select count(location)
from wordlocation

select wordid from wordlist where word='hello';

select concat(word, '(', wordid, ')') from wordlist where word='machine';




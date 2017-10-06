#创建数据库
create schema  if not exists `paper1`;
#使用数据库
use paper1;
#创建表
drop table if exists usersim;
create table if not exists usersim
(
	userid int unsigned NOT NULL,
    otherUserId int unsigned not null,
    sim float not null,
    primary key (userid, otherUserId),
    index(userid, otherUserId)
);


drop table if exists wssim;
create table if not exists wssim
(
	id int unsigned NOT NULL,
    otherid int unsigned not null,
    sim float not null,
    primary key (id, otherid),
    index(id, otherid)
);

drop table if exists usersimilarity2;
create table if not exists usersimilarity2
(
	id int unsigned NOT NULL,
    otherid int unsigned not null,
    sim float not null,
    primary key (id, otherid),
    index(id, otherid)
);

drop table if exists wssimilarity;
create table if not exists wssimilarity
(
	id int unsigned NOT NULL,
    otherid int unsigned not null,
    sim float not null,
    primary key (id, otherid),
    index(id, otherid)
);

use paper1;
#清空表中的数据
truncate table wssimilarity;
truncate table usersimilarity2;

#使用函数
select count(*) from usersimilarity;
select count(*) from usersimilarity where id=11;








use mahout;
#删除表
drop table test_preference;

#更改表的名称
alter table test_preference rename slopeone;

#增加一列
alter table test add column prefs float not null;

#增加主键
alter table test change id userid bigint not null;

#增加索引
alter table test add index(id);
alter table test add index(itemid);

#清空表中的数据
truncate table test;

#插入数据
insert into test(id, itemid, prefs)
values(1, 1, 2),
(1, 2, 5),
(2, 1, 3),
(2, 2, 2),
(2, 3, 5),
(3, 1, 4),
(3, 2, 4),
(3, 3, 3);



#存储过程
	#MySQL中的变量名以@开头
#删除存储过程
drop PROCEDURE if exists getMaxPref ;
delimiter //
CREATE PROCEDURE getMaxPref(in id int, out p1 int, out p2 int, out p3 float) 
comment "this is just a comment"
BEGIN

#存储过程函数
select userid, itemid, max(prefs)  from slopeone where userid=id into p1, p2, p3;
#select userid, itemid, max(prefs)  from slopeone group by userid into p1, p2, p3;
	
END//
delimiter ;
#调用存储过程，存储过程创建后被存储在服务器上供以后调用
call getMaxPref(1, @userId, @itemid, @prefs);
select @userId, @itemid, @prefs;




#存储过程中使用游标
drop PROCEDURE if exists getMaxPref2 ;
delimiter //
CREATE PROCEDURE getMaxPref2(out p1 int, out p2 int, out p3 float) 
comment "this is just a comment"
BEGIN

declare done boolean default 0;#声明一个控制变量
#声明一个游标
declare cur cursor for select userid, itemid, max(prefs)  from slopeone group by userid;

declare continue handler for sqlstate '02000' set done=1; #当读取到最后一行，抛出sqlstate '02000' 错误，执行set done=1 指令

open cur; #打开游标，游标只能在存储过程中使用
repeat
	fetch cur into p1, p2, p3;
until done end repeat;

close cur;#显式关闭游标，如果不显式关闭，则在end处隐式关闭；

END//
delimiter ;

#调用存储过程，存储过程创建后被存储在服务器上供以后调用
call getMaxPref2(@userId, @itemid, @prefs);
select @userId, @itemid, @prefs;




#存储过程中使用游标
drop PROCEDURE if exists getMaxPref3 ;
delimiter //
CREATE PROCEDURE getMaxPref3() 
BEGIN

select userid, itemid, max(prefs)  from slopeone group by userid;

END//
delimiter ;





#显示存储过程的状态， like 实现过滤
show  PROCEDURE status like 'getMaxPref';

#选择
#分组：选择每个用户prefs值最大的那一行
select userid, itemid, max(prefs) from slopeone group by userid;











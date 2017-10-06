use paper1;
#查询 select， 需要首先使用 use paper1
select distinct qos from train where userid = 0 order by qos desc limit 2, 5 ;
select distinct userid, wsid from train where userid = 0 limit 3 offset 2;
#完全限定的表名，不需要首先使用 use slopeone
select distinct slopeone.userid from mahout.slopeone; 

#使用 聚集函数(作用于分组或者这个检索结果表)
#group by 分组
#with rollup 汇总分组聚集函数结果
#过滤分组
select userid, wsid, count(*) as numbers, avg(qos) as avgqos from train where userid < 3 and wsid < 30 group by userid, wsid order by avgqos;
select userid, wsid, qos from train where userid < 3 and wsid <30 group by userid, wsid;
select userid, wsid, qos from train where userid < 3 and wsid <30 group by userid, wsid having qos > 0.2;
select userid, count(*) as numbers, avg(qos) as avgqos from train where userid < 3 group by userid with rollup;

#连接
#笛卡儿积
select t.userid, t.wsid, u.id, u.otherid, u.sim from train as t, usersimilarity as u
where t.userid < 3 and t.wsid < 3 and u.sim > 0.99 and u.sim != 1;
#inner join（内部联结， 等值联结）
#使用 on 代替 where（也可以使用 where）
select train.userid, train.wsid, usersimilarity.id, usersimilarity.otherid, usersimilarity.sim from train inner join usersimilarity 
on train.userid < 3 and train.wsid < 3 and usersimilarity.sim > 0.99 and usersimilarity.sim != 1;


#union
select qos from train where userid < 3 and wsid < 10;
select sim as qos from usersimilarity where id < 3 and otherid < 3;
select qos from train where userid < 3 and wsid < 10
union all
select sim as qos from usersimilarity where id < 3 and otherid < 3;

#视图 view
create view avgqoscheck as select userid, count(*) as numbers, avg(qos) as avgqos from train group by userid order by avgqos;
select sum(numbers) from avgqoscheck;




use searchindex;
#使用 like 通配符
select wordid, word from wordlist where word like 'so';
#使用 REGEXP 正在表达式
select wordid, word from wordlist where word regexp 'som';

#concat()， 字段拼接
#as, 别名
select concat(word, '(', wordid, ')') as list  from wordlist where word='machine';
select concat(word, '(', wordid, ')')   from wordlist where word='machine';


use mahout;
select max(prefs) from slopeone where itemid =1;
select userid from slopeone where itemid = 1 and prefs in (select max(prefs) from slopeone where itemid =1);
select userid from slopeone where itemid = 1 and prefs >= all (select prefs from slopeone where itemid =1);
select * from slopeone where itemid =1 ;



#存储过程
drop PROCEDURE if exists getMaxPref ;
delimiter //
CREATE PROCEDURE getMaxPref(in id int, out p1 int, out p2 int, out p3 float) 
comment "this is just a comment"
BEGIN
#存储过程函数
select userid, itemid, max(prefs)  from slopeone where userid=id into p1, p2, p3;
END//
delimiter ;
#调用存储过程，存储过程创建后被存储在服务器上供以后调用
#MySQL中的变量名以@开头
call getMaxPref(1, @userId, @itemid, @prefs);
select @userId, @itemid, @prefs;


#在存储过程中使用游标
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

#显示存储过程的状态， like 实现过滤
show  PROCEDURE status like 'getMaxPref';






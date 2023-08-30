package com.jieming.springbootconfig.userful;

/**
 * linux 启动mysql到使用sql之前的语句
 */
public class MysqlUse {

    /*
    * 1 本机启动安装的mysql：sudo systemctl start mysql;
    * 2 使用账户密码连接mysql： mysql -uroot -p
    * 3 show databases;
    * 4 create database databaseName;
    * 5 use databaseName;
    * 6 显示表结构： desc tableName
    */


    /*
    * 条件判断语句：
    * 1 if语句 IF ( expression 1, expression 2, expression 3)
    * eg：if（age>12,'xiao', 'da'）: 表示如果年龄大于12岁，就显示expression 2（‘xiao’），否则显示expression 3（‘da’）
    *
    */

    /*
    * 条件判断语句：
    * 2 ifnull语句 ：IFNULL (Expression1, Expression2)
    *   表示:如果Expression1值 不为空，返回表达式1 ，否则返回表达式2
    *   eg：ifnull（‘小明’，‘无名’）：表示如果 小明这个表达式不为空，所以返回小明，如果为空，则返回 无名，‘小明’肯定不为空，所以返回小明
    *   应用： 在sql查询的时候如果某个字段的之为null，就给一个默认值后。所以此时可以写ifnull（age，0）,
    *   表示如果测试age 字段为null，就返回0,否则返回age字段对应的值
    */
}

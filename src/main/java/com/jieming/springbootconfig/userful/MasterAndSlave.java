package com.jieming.springbootconfig.userful;


/*×
* mysql主从分离
* */
public class MasterAndSlave {
    /*
    * 数据库主从复制原理：
    * 1 主库的更新sql（insert，update，delete）被写入binLog日志
    * 2 从库发起链接，连接到主库
    * 3 主库发创建一个binlog dump线程，把binlog内容发送到从库
    * 4 从库接收到主库的binlog，然后重写binllg日志为从库的relay log
    * 5 从库创建一个sql线程，从relay log读取内容，从ExecMasterLog_Pos位置开始执行读取到的更新事件，将内容写入到从库db
    * */


    /*
    * 主主，主从，主备之间的区别
    * 主主：都是主数据库，两个主库之间数据存在双向同步。同时对外提供读写功能，客户端可以方位任意一个数据库
    * */

    /*
    * mysql如何保持主从库数据一致的？
    * 
    * */
}

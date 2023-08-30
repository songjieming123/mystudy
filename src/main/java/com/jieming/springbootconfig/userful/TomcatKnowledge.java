package com.jieming.springbootconfig.userful;

/**
 *面试官：一个 SpringBoot 项目能处理多少请求(小心有坑)
 */
public class TomcatKnowledge {
    /*
    * 坑1：没有说任何条件：1 项目具体是干嘛的？
    *                  2 项目有哪些配置
    *                  3 项目使用的web容器是什么
    *                  4 部署的服务器配置是什么
    *                  5 接口有哪些
    *                  6 接口响应平均时间是多少等
    */


    /*
    * 答案： 200次（web容器为tomcat）
    * 200次的由来：200次是springboot集成的tomcat容器的最大线程数，
    * tomcat的corePoolSize为10 ， maxiMumPoolSize 为 200
    * tomcat的 线程池的 执行线程 时过程和 jdk的线程池有点儿不一样。
    * tomcat在达到核心线程后，继续创建非核心线程，直到达到最大线程数，然后在启用队列
    *
    */

    /*
    * tomcat的线程执行过程：a 先提交给核心线程，
    *                    b 若核心线程书已满，则提交给诶核心线程
    *                    c 若非核心线程已满，则提交人物到阻塞队列
    *                    d 阻塞队列的最大值为： Integer.MAX_VALUE:2147483647
    */


    /*
    * 一个请求是如何提交到tomcat的线程池的：
    *  1 首先获得当前线程数，int c = ctl.get();
    *  2 比较 workCountOf（c）< corePoolsize
    *    a：如果为真，则调用 addWorker（command ， true）---创建线程
    *        若 addWorker（command ， true）返回true ，则结束
    *        若 addWorker（command ， true）返回false， 则更新 c 的值
    *  3 （执行到这里的前提条件是：当前线程数已经达到核心线程数（线程书 = corePoolsize）调用 isRunning（c） 和 workQueue.offer(command) 若
    *     isRunning（c） 返回为真，说明每个核心线程都在执行任务
    *     workQueue.offer(command) 返回真，说明工作队列可以加入任务（即队列还没满）
    *     两个方法返回均为真：
    *     调用 recheck = ctl.get()
    *
    *  4 （前提是队列已经满了，且当前所有线程都在执行中）调用addWorker（command ， false）返回false，说明创建线程失败
    *    则执行拒绝策略
    *
    *
    */



    /*
    * 关于 workQueue.offer(command) 逻辑
    *  关于offer（）方法：
    *  1 首先判断 parent == null的真假
    *    若为真：直接调用 supper.offer(command) 方法 ，返回该方法的结果（结果为真或者假）
    *    若为假，则跳到步骤2
    *  2 判断 parent.getPoolSizeNoLock() == parent.getMaximumPoolSize() 的真假
    *    该判断条件表示：线程池当前线程数 == 线程池的坐在最大线程数
    *    若为真：说明已达最大线程数，调用 supper.offer(command) 将任务添加到队列
    *    若为假，则跳转步骤3
    *  3 判断 parent.getSubmittedCount() < parent.getPoolSizeNoLock()
    *    该判断表示：提交的任务数 <= 线程池当前任务数
    *    说明：线程池的执行阶段是
    *    若为真：说明（当前线程池有空闲的线程可以执行任务，把任务放进队列，就会被空闲线程拿走去执行），调用 supper.offer(command) 将任务添加到队列
    *    若为假：跳转步骤4
    *  4 判断 parent.getPoolSizeNoLock() < parent.getMaximumPoolSize() 的真假
    *    若为真，说明还没达到最大线程数，直接返回false---返回false，就会执行addWorker（command，fasle），创建非核心线程，启动线程池的最大线程数
    *    若为假，跳转到步骤5
    *  5 直接调用supper.offer(command) 将任务添加到队列
     *
     * 注：在第一步：判断parent == null 若为真，则此方法逻辑结束了，需要parent不为空
     *    所以在何时给parent 设置值 taskqueue.setParent(executor) ，如果把这行代码注释掉，就是jdk的线程池执行过程
     *    这里 parent 设置的值就是tomcat的 线程池，估计理解为在tomcat场景下，parent不可能为null;
     *
     *
     *
     *
    */

    /*
    * 所以当面试官问：聊聊线程池的运行机制的时候----需要问明白是jdk的线程池 还是tomcat的线程池机制
    *
    * */

    /*
    *
    * 如果配置了 server.tomcat.max-connections=10，线程池最多执行多少个请求
    * 答案：10个
    * 原理：serverProperties.java文件中maxConnections 默认值为 8192
    *      tomcat的最大线程数为为 200 ，比8192 小，不受限制
    *      当设置server.tomcat.max-connections=10为19 后，tomcat最多接受10个连接，
    *      传给线程池的最多也就是10 个
    *
    * */
}

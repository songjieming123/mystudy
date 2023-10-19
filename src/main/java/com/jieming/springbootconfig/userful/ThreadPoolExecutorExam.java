package com.jieming.springbootconfig.userful;

import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

import java.util.concurrent.*;

/*
* 参考：https://blog.csdn.net/weiwenhou/article/details/128588865
* */
public class ThreadPoolExecutorExam {

    /*×
    * java线程池面试题
    * */

    /*
    * 线程池的工作队列：
    *    1 ArrayBlockingQueue:
    *      有解队列，用数组实现的有界阻塞队列
    *    2 LinkedBlockingQueue：
    *      可以设置容量的，基于链表的阻塞队列，容量可以选择设置，如果不设置容量大小，就是一个无边界队列，队列大小是Integer.MAX_VALUE。
    *      newFixedThreadPool就是使用该队列
    *    3 DelayQueue：
    *      延迟队列，是一个定时任务延迟执行的队列，根据指定的执行时间从小大小排序，如果没有指定执行时间，按照加入队列的顺序执行
    *      newScheduledThreadPool线程池使用该队列
    *    4 proitityQueue：
    *      具有优先级的无界队列
    *    5 SynchronousQueue
    *      同步队列，（不存储元素的阻塞队列）。每个插入操作必须等到另一个线程调用移除操作，否则插入操作一直处于阻塞状态，吞吐量高于LinkedBlockingQueue
    *      newCachedThreadPool队列就是使用该队列
    * */

    /*
    * 几种常用的线程池：
    *    1 newCachedThreadPool（可缓存线程池）：
    *       线程池特点：
    *           a 核心线程数为0
    *           b 线程最大空闲时间为60s
    *           c 使用SynchronousQueue阻塞队列
    *           d 线程池最大线程数为Integer.MAX_VALUE
    *    2 newFixedThreadPool(固定线程容量线程池)：
    *       线程池特点：
    *           a 最大线程数量和核心线程数量相同
    *           b 没有最大空闲时间，即keepAliveTime为0
    *           c 阻塞队列使用的是LinkedBlockingQueue
    *    3 newScheduledThreadPool(周期任务线程池)
    *       线程池特点：
    *           a 最大线程数是integer.MAX_VALUE
    *           b 阻塞队列为DelayWorkQueue
    *           c keepAliveTime为0
    *           d scheduleAtFixedRate() ：按某种速率周期执行
    *           f scheduleWithFixedDelay()：在某个延迟后执行
    *    4 newSingledThreadExecuotor(单线程线程池)
    *       线程池特点：
    *           a 核心线程数和最大线程数一样都是1
    *           b 阻塞队列是用LinkedBlockingQueue
    *           c 阻塞队列最大容量为Integer.MAX_VALUE
    * */


    /*
    * 线程池状态：RUNNING  SHUTDOWN STOP TIDYING TERMINATED 五个状态
    * 线程池各个状态见转换：
    *       a 线程池处于RUNNING状态调用shutdown（）方法后线程池处于 SHUTDOWN状态
    *       b 线程池处于RUNNING状态调用shutdownNow（）方法后线程池处于 STOP状态
    *       c 处于SHUTDOWN状态的线程池，如果队列为空，线程池中正在执行的任务也为空，线程池转为TIDYING状态
    *       d 处于STOP状态的线程池，如果线程池中正在执行的人呢无数量为0,线程池转为TIDYING状态
    *       e 处于TIDYING状态的线程池调用terminated（）方法后处于TERMINATED状态
    * 线程池各个装他介绍：
    *   RUNNING：
    *       该状态线程池会接收任务，且处理阻塞队列中的任务
    *       调用线程池的shutdown（）方法，线程池处于SHUTDOWN状态
    *       调用shotdownNow（）方法，线程处于STOP状态
    *   SHUTDOWN：
    *       该状态线程池不会接收新任务，但是会处理阻塞队列中的任务
    *       阻塞队列为空时，线程池处于TIDYING状态
    *   STOP状态：
    *       改状态的线程池既不会接受新任务，也不会处理阻塞队列中的任务，且会中断线程池中正在处理的任务
    *       线程池中正在执行的任务为空的时候，线程池处于TIDYING状态
    *   TIDYING：
    *       该状态表明所有的任务已经运行终止，记录的任务数量为0
    *       线程池调用terminated（）方法后，进入TERMINATED状态
    *   TERMINATED：
    *       表明线程池彻底处于停止状态
    *
    * */


    /*
    * 细数线程池的10个坑：
    *   1 线程池默认使用无界队列，任务过多造成oom
    *   2 线程过多导致oom
    *   3 共享线程池，次要逻辑拖跨主要逻辑
    *   4 线程池拒绝策略的坑
    *   5 spring内部线程池的坑
    *   6 使用线程池时，没有自定义线程名称
    *   7 线程池参数设置不合理的坑
    *   8 线程池异常处理的坑
    *   9 使用完线程池忘记关闭线程池
    *   10 ThreadLocal与线程池搭配，线程复用，导致信息错乱
    * */

    /*
    * 线程池使用第一个坑：默认使用无界队列，任务过多造成oom
    * 比如 newFixedThreadPool()线程池使用的是 LinkedBlockedQueue队列，就是无界队列，如果每个任务执行时间太长，而任务数量很多
    * 会把大量的任务存储工作队列，导致oom
    * */

    /*
    * 线程池使用第2个坑：创建太多的线程，导致oom
    * 比如使用 newCachedThreadPool（）线程池，没有核心线程，最大可以创建integer。MAX_VALUE个线程，线程树木太大，也会导致oom
    * */

    /*
    * 线程池使用第3个坑：避免不同业务公用线程池，导致一个一个业务拖跨另一个业务
    * 比如你用线程池A来做登录异步通知，又用线程池A来做对账，如果对账任务checkBillService响应时间过慢，
    * 会占据大量的线程池资源，可能直接导致没有足够的线程资源去执行loginNotifyService的任务，
    * 最后影响登录。就这样，因为一个次要服务，影响到重要的登录接口，显然这是绝对不允许的。
    * 因此，我们不能将所有的业务一锅炖，都共享一个线程池，因为这样做，风险太高了，犹如所有鸡蛋放到一个篮子里。应当做线程池隔离！
    * */

    /*
    * 线程池使用第4个坑：拒绝策略的坑
    * */

    /*
    * 线程池使用第5个坑：spring内部线程池的坑
    * */

    /*
    *线程池使用第6个坑：使用线程池时没有自定义线程池名称
    *
    * */

    /*
    * 线程池使用第7个坑：线程池定义的参数不对
    * 如何计算最优线程数
    * 最佳线程数目 = （（线程等待时间+线程CPU时间）/线程CPU时间 ）* CPU数目
    * */

    /*
    * 线程池使用第8个坑：线程池异常处理
    * */

    /*
     * 线程池使用第9个坑：使用后忘记关闭
     * */


    /*
     * 线程池使用第10个坑：ThreadLocal与线程池搭配，线程复用，导致信息错乱。
     * */

    public static void custimizeaPolName(){
        // 定义一个线程池，定义LinkedBlockingQueue的长度，自定义线程池名称，定义拒绝策略
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 1, 0, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(10), new CustomizableThreadFactory("jieming-threadPool")
                , new ThreadPoolExecutor.DiscardPolicy());
    }

    public static void main(String[] args) {
//        main1();
        main2();

    }
    /*该段代码第二个sout没打印出结果，虽然代码抛出了异常，但是线程池并没有抛出异常，我们无法感知程序出了什么问题*/
    public static void main1(){
        // 创建一个固定线程数量的线程池
        ExecutorService executorPool = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            executorPool.submit(() ->{
                System.out.println("current thread name:"+Thread.currentThread());
                Object object = null;
                System.out.println("result##:"+object.toString());  //这里肯定会报空指针异常
            });

        }

    }

    /*要感知异常，需要try/catch,抛出了异常，可以被外界感知*/
    public static void main2(){
        for (int i = 0; i < 5; i++) {
            ExecutorService threadPool = Executors.newFixedThreadPool(5);
            for (int j = 0; j < 5; j++) {
                threadPool.submit(() -> {
                    System.out.println("current thread name:"+Thread.currentThread());
                    try {
                        Object object = null;
                        System.out.println("result##:"+object.toString());  //这里肯定会报空指针异常
                    }catch (Exception e){
                        System.out.println("程序抛出异常了");
                    }
                });
            }
        }
    }


}


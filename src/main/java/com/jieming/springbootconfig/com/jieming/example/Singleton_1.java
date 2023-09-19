package com.jieming.springbootconfig.com.jieming.example;

/**
 * 单例模式--饱汉--双重判断
 */
public class Singleton_1 {

    /*
    * volatile 字段的作用
    * 防止重排序，因为对象创建的过程不是原子的，分三个步骤：
    * 1 new 创建一个对象
    * 2 对创建的对象进行初始化
    * 3 在内存中创建给创建的对象分配一块区域，且将创建的对象指向这块区域
    * 若没有 volatile 关键字，java虚拟机执行的时候发生指令重排序，步骤2 和步骤3 位置对调，
    * 创建的对象还没进行初始化，即只执行了1，3 2步，还没执行步骤2 ，其他线程获取到该对象进行使用时，会发生空指针异常。
    */
    private volatile static Singleton_1 INSTANCE;

    private Singleton_1() {

    }

    public static Singleton_1 getInstance() {
        // 双重判断
        if (null == INSTANCE) {
            // 第一重判断--减少进入同步代码块的次数，提高效率
            synchronized (Singleton_1.class) {
                if (null == INSTANCE) {
                    // 第二重判断--防止多次创建对象，确保创建的对象只有一个
                    INSTANCE = new Singleton_1();
                }
            }
        }
        return INSTANCE;
    }


}

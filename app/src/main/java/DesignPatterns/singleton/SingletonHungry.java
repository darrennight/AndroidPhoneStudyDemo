package DesignPatterns.singleton;

/**
 * 饿汉式
 * 这种方式在类加载时就完成了初始化，所以类加载较慢，但获取对象的速度快。 这种方式基于类加载机制避免了多线程的同步问题，
 * 但是也不能确定有其他的方式（或者其他的静态方法）导致类装载，这时候初始化instance显然没有达到懒加载的效果。
 * 依赖 JVM类加载机制，保证单例只会被创建1次，即 线程安全
 * JVM在类的初始化阶段(即 在Class被加载后、被线程使用前)，会执行类的初始化
 * 在执行类的初始化期间，JVM会去获取一个锁。这个锁可以同步多个线程对同一个类的初始化
 */
public class SingletonHungry {
     private static SingletonHungry instance = new SingletonHungry();
     private SingletonHungry(){
     }
     public static SingletonHungry getInstance() {
     return instance;  
     }  
 }
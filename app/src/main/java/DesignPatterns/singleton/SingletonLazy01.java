package DesignPatterns.singleton;

/**
 * 懒汉式线程安全型
 * 这种写法能够在多线程中很好的工作，但是每次调用getInstance方法时都需要进行同步，
 * 造成不必要的同步开销，而且大部分时候我们是用不到同步的，所以不建议用这种模式。
 * 使用同步锁 synchronized锁住 创建单例的方法 ，防止多个线程同时调用，从而避免造成单例被多次创建
 * 即，getInstance（）方法块只能运行在1个线程中
 * 若该段代码已在1个线程中运行，另外1个线程试图运行该块代码，则 会被阻塞而一直等待
 * 而在这个线程安全的方法里我们实现了单例的创建，保证了多线程模式下 单例对象的唯一性
 */
public class SingletonLazy01 {
    private static SingletonLazy01 instance;
    private SingletonLazy01(){
    }

    //同步方法
    public static synchronized SingletonLazy01 getInstance() {
        if (instance == null) {
            instance = new SingletonLazy01();
        }
        return instance;
    }
}
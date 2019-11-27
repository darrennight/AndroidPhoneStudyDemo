package DesignPatterns.singleton;

/**
 * 每次访问都要进行线程同步（即 调用synchronized锁)，造成过多的同步开销（加锁 = 耗时、耗能）
 * 实际上只需在第1次调用该方法时才需要同步，一旦单例创建成功后，就没必要进行同步
 */
class SingletonLazy03 {

    private static SingletonLazy03 instance = null;

    private SingletonLazy03(){
}

    public static SingletonLazy03 getInstance(){
        // 加入同步锁 同步代码块
        synchronized(SingletonLazy03.class) {
            if (instance == null)
                instance = new SingletonLazy03();
        }
        return instance;
    }
}
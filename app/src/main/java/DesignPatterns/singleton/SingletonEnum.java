package DesignPatterns.singleton;

/**
 * 枚举式
 * // 获取单例的方式：
 * Singleton singleton = Singleton.INSTANCE;
 */
public enum SingletonEnum {

    //定义1个枚举的元素，即为单例类的1个实例
    INSTANCE;

    // 隐藏了1个空的、私有的 构造方法
    // private Singleton () {}

}

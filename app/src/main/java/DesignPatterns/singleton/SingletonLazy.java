package DesignPatterns.singleton;

/**
 * 懒汉式非线程安全型
 * 懒汉模式申明了一个静态对象，在用户第一次调用时初始化，虽然节约了资源，
 * 但第一次加载时需要实例化，反映稍慢一些，而且在多线程不能正常工作。
 */
public class SingletonLazy {
      private static SingletonLazy instance;
      private SingletonLazy(){
      }   
      public static SingletonLazy getInstance() {
      if (instance == null) {  
          instance = new SingletonLazy();
      }  
      return instance;  
      }  
 }


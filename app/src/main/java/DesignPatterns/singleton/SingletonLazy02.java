package DesignPatterns.singleton;

/**
 * 懒汉式 双重锁 volatile
 */
public class SingletonLazy02 {
      private volatile static SingletonLazy02 instance;
      private SingletonLazy02(){
      }   
      public static SingletonLazy02 getInstance() {
      if (instance== null) {  
          synchronized (SingletonLazy02.class) {
          if (instance== null) {  
              instance= new SingletonLazy02();
          }  
         }  
     }  
     return instance;
     }  
 }
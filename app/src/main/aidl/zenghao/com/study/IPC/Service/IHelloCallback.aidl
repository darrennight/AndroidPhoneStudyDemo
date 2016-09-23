// IHelloCallback.aidl
package zenghao.com.study.IPC.Service;

import zenghao.com.study.IPC.Service.bean.Person;

// Declare any non-default types here with import statements
//回调 service回调数据给activity
 interface IHelloCallback {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

            void onString(String s);
            void onPerson(String Name);
            void onTest(in Person p);
}

//AIDL文件中 in类型out类型和inout数据的区别
//1.只有AIDL支持的数据类型不需要，其他类型的参数必须要加上参数。
//
//in：客户端的参数输入；
//
//解析：是把实参的值赋值给行参   那么对行参的修改，不会影响实参的值
//
//out：服务端的参数输入；
//
//解析：传递以后，行参和实参都是同一个对象，只是他们名字不同而已，对行参的修改将影响实参的值
//
//inout：这个可以叫输入输出参数，客户端可输入、服务端也可输入。客户端输入了参数到服务端后，服务端也可对该参数进行修改等，最后在客户端上得到的是服务端输出的参数。
//
//分析in类型和out类型以及调用Proxy#getBookList（）的运作流程
//假设getBookList()有in、out类型  如：getBookList(in Book book,out String[] str);
//
//当客户端远程调用此方法的时候
//
//①：创建该方法需要的输入类型Parcel对象_data（这里为Book），输出类型（这里为str），和返回值对象（这里为List），写入data中
//
//②：调用transact发起RPC（远程调用请求），挂起当前线程（客户端的）
//
//③：调用服务端的onTransact()，直到RPC返回结果，从_reply中取出PRC返回结果
//
//④：最后返回_reply的数据（个人认为这里就是将返回的_reply的值给客户端的str） 导致服务器端修改行参将影响实参的值

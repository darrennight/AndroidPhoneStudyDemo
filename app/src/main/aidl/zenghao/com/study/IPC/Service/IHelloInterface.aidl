// IHelloInterface.aidl
package zenghao.com.study.IPC.Service;

// Declare any non-default types here with import statements
import zenghao.com.study.IPC.Service.Person;
import zenghao.com.study.IPC.Service.IHelloCallback;

//绑定service后主动获取数据
interface IHelloInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

    void registerCallback(IHelloCallback cb);

    void unregisterCallback(IHelloCallback cb);

    String getString();

    Person getPerson();

}

package zenghao.com.study.IPC.Service;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * TODO
 *
 * @author zenghao
 * @since 16/9/18 下午5:50
 * 跨进程传递需要实现Parcelable
 * aidl还需要创建person到aidl文件
 */
public class Person implements Parcelable {

    public Person(){

    }

    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public Person(Parcel in) {
        name = in.readString();
        age = in.readInt();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(age);
    }


    public static final Parcelable.Creator<Person> CREATOR = new Parcelable.Creator<Person>() {
        public Person createFromParcel(Parcel in) {
            return new Person(in);
        }

        public Person[] newArray(int size) {
            return new Person[size];
        }
    };
}

package zenghao.com.study.DownLoadV2.db;

import android.os.Parcel;
import android.os.Parcelable;

public class CallArgs implements Parcelable {

    public String method;
    public Object[] methodArgs;
    public Object result;

    public CallArgs() {

    }

    protected CallArgs(Parcel in) {
        readFromParcel(in);
    }

    public static final Creator<CallArgs> CREATOR = new Creator<CallArgs>() {
        @Override
        public CallArgs createFromParcel(Parcel in) {
            return new CallArgs(in);
        }

        @Override
        public CallArgs[] newArray(int size) {
            return new CallArgs[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(method);
        dest.writeArray(methodArgs);
        dest.writeValue(result);
    }

    public void readFromParcel(Parcel in) {
        method = in.readString();
        methodArgs = in.readArray(CallArgs.class.getClassLoader());
        result = in.readValue(CallArgs.class.getClassLoader());
    }
}
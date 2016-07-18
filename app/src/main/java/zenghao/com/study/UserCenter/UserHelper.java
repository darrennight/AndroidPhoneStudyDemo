package zenghao.com.study.UserCenter;

import org.json.JSONObject;

import zenghao.com.study.MyApplication;
import zenghao.com.study.bean.User;

/**
 * Created by zenghao on 16/5/20.
 */
public class UserHelper {

    private User mUser;
    private UserAction mUserAction;
    private UserHelper(){
        mUserAction = new UserAction(MyApplication.getApplication());
        mUser = mUserAction.getUser();////mUser = DB读取
    };
    private static UserHelper mInstance;
    public static synchronized UserHelper getInstance(){
        if(mInstance == null){
            mInstance = new UserHelper();
        }
        return mInstance;
    }


    public User getUser(){
        return mUser;
    }

    public void setUser(User user){
        this.mUser = user;
    }

    public void saveUser(JSONObject json){
        //存储user到数据库
    }

    public void loginin(){

    }

    public boolean isLogin(){
        return true;
    }

    public void logout(){

    }

    public void isOwner(){

    }
}

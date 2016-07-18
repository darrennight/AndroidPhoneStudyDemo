package zenghao.com.study.UserCenter;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import zenghao.com.study.bean.User;

/**
 * Created by zenghao on 16/5/20.
 */
public class UserAction {

    private SQLiteDatabase database;
    private UserDbHelper helper;

    public  UserAction(Context context){
        this.helper = UserDbHelper.getInstance(context);
    }



    public void open() {
            database = helper.openDatabase();
    }

    public void close() {
        helper.closeDatabase();
    }

    /***
     * 获取User
     */
    public User getUser(){
        Cursor cursor = database.query(UserUnit.TABLE_USERS, null, null, null, null, null, null);
        return new User();
    }

    //判断表是否存在
    private boolean IsTableExist() {
        boolean isTableExist=true;
        Cursor c=database.rawQuery("SELECT count(*) FROM sqlite_master WHERE type='table' AND name='要查询的表名'", null);
        if (c.getInt(0)==0) {
            isTableExist=false;
        }
        c.close();
        return isTableExist;
    }
}

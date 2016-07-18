package zenghao.com.study.UserCenter;

/**
 * Created by zenghao on 16/5/20.
 */
public class UserUnit {
    public final static String TABLE_USERS = "users";
    public final static String FIELDS_ID = "_id";
    // The fields for users table
    public final static String FIELDS_USERS_ACCOUNT = "account";
    public final static String FIELDS_USERS_COOKIE = "cookie";
    public final static String FIELDS_USERS_USING = "isUsing";
    public final static String FIELDS_USERS_NETID = "netId";
    public final static String FIELDS_USERS_NAME = "userName";
    public final static String FIELDS_USERS_AVATAR_SMALL = "avatarSmall";
    public final static String FIELDS_USERS_AVATAR_NORM = "avatarNorm";
    public final static String FIELDS_USERS_IS_BIND_SINA = "isBindSina";
    // add by version 5
    public final static String FIELDS_USERS_IS_BIND_TENCENT = "isBindTencent";
    // add by version 6
    public final static String FIELDS_USERS_IS_BIND_QQ = "isBindQq";
    // add by version 12
    public final static String FIELDS_USERS_IS_BIND_WECHAT = "isBindWechat";
    // add by version 13 on 20151016 by gaowen
    public final static String FIELDS_USERS_IS_HUNTER = "isHunter";

    /**创建User表*/
    public static final String createUserTable = "CREATE TABLE " + TABLE_USERS + " ("
            + FIELDS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + FIELDS_USERS_ACCOUNT + " TEXT NOT NULL," + FIELDS_USERS_USING
            + " INTEGER NOT NULL," + FIELDS_USERS_NETID + " INTEGER,"
            + FIELDS_USERS_IS_BIND_SINA + " INTEGER,"
            + FIELDS_USERS_IS_BIND_TENCENT + " INTEGER,"
            + FIELDS_USERS_IS_BIND_QQ + " INTEGER,"
            + FIELDS_USERS_IS_HUNTER + " INTEGER,"
            + FIELDS_USERS_IS_BIND_WECHAT + " INTEGER," + FIELDS_USERS_NAME
            + " TEXT DEFAULT ''," + FIELDS_USERS_AVATAR_NORM
            + " TEXT DEFAULT ''," + FIELDS_USERS_AVATAR_SMALL
            + " TEXT DEFAULT ''," + FIELDS_USERS_COOKIE + " TEXT )";
}

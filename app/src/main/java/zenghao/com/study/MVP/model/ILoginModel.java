package zenghao.com.study.MVP.model;

import zenghao.com.study.MVP.User;

/**
 * Created by zenghao on 16/5/8.
 */
public interface ILoginModel {
    //登陆
    void login(String name,String pwd,String netListener);
    //数据库获取
    User load(String id);
}

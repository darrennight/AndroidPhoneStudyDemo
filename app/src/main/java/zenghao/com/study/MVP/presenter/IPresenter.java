package zenghao.com.study.MVP.presenter;

import zenghao.com.study.MVP.User;

/**
 * Created by zenghao on 16/5/8.
 */
public interface IPresenter {
    //登陆
    void login(String name,String pwd);
    //数据库获取
    User load(String id);
}

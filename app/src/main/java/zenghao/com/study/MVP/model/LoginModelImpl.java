package zenghao.com.study.MVP.model;

import zenghao.com.study.MVP.User;

/**
 * Created by zenghao on 16/5/8.
 */
public class LoginModelImpl implements ILoginModel {

    @Override
    public void login(String name, String pwd, String netListener) {
        //网络请求登陆 回调listener
    }

    @Override
    public User load(String id) {
        //数据库读取user
        return null;
    }
}

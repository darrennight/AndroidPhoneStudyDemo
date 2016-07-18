package zenghao.com.study.MVP.presenter;

import zenghao.com.study.MVP.User;
import zenghao.com.study.MVP.bizview.ILoginView;
import zenghao.com.study.MVP.model.ILoginModel;
import zenghao.com.study.MVP.model.LoginModelImpl;

/**
 * Created by zenghao on 16/5/8.
 */
public class LoginPresenterImpl implements IPresenter {
    /*Presenter作为中间层，持有View和Model的引用*/
    private ILoginModel mLoginModel;
    private ILoginView mLoginView;
    public LoginPresenterImpl(ILoginView view){
        this.mLoginView = view;
        mLoginModel = new LoginModelImpl();
    }

//PresenterImpl 控制器 控制model获取数据 控制view显示交互
    @Override
    public void login(String name, String pwd) {
            //进行网络请求 此类继承网络请求回调
        mLoginModel.login(name,pwd,"");
        //请求成功后 调用mLoginView 相关方法 进行相关的UI展示和交互
    }

    @Override
    public User load(String id) {
        return null;
    }
}

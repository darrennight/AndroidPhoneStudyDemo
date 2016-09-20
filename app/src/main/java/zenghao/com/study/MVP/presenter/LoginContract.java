package zenghao.com.study.MVP.presenter;

import zenghao.com.study.MVP.bizview.BaseView;
import zenghao.com.study.MVP.model.BaseModel;

/**
 * https://github.com/north2014/T-MVP/blob/master/app/src/main/java/com/ui/login/LoginActivity.java
 * //还需要添加Model到实现类 在activity中实例化 P调用setVM方法
 */
public interface LoginContract {
    interface Model extends BaseModel {
        void login(String name, String pass);
        void sign(String name, String pass);
    }

    interface View extends BaseView {
        void loginSuccess();
        void signSuccess();
        void showMsg(String  msg);
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        public abstract void login(String name, String pass);
        public abstract void sign(String name, String pass);
        @Override
        public void onStart() {}
    }

}
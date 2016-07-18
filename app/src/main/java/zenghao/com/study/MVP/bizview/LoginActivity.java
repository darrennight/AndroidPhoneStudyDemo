package zenghao.com.study.MVP.bizview;

import android.app.Activity;
import android.os.Bundle;

import zenghao.com.study.MVP.presenter.IPresenter;
import zenghao.com.study.MVP.presenter.LoginPresenterImpl;

/**
 * Created by zenghao on 16/5/8.
 * 在MVP模式里通常包含4个要素：

 (1)View:负责绘制UI元素、与用户进行交互(在Android中体现为Activity);

 (2)View interface:需要View实现的接口，View通过View interface与Presenter进行交互，降低耦合，方便进行单元测试;

 (3)Model:负责存储、检索、操纵数据(有时也实现一个Model interface用来降低耦合);

 (4)Presenter:作为View与Model交互的中间纽带，处理与用户交互的负责逻辑。


 */
public class LoginActivity extends Activity implements ILoginView{

    private IPresenter mLoginPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLoginPresenter = new LoginPresenterImpl(this);
        mLoginPresenter.login("","");
        mLoginPresenter.login("","");
    }

//    ILoginView 里面还可以添加其他方法  UI修改交互相关的

    @Override
    public String getName() {
        //返回文本框的名字
        return null;
    }

    @Override
    public String getPwd() {
        //返回文本框的秘密
        return null;
    }
}

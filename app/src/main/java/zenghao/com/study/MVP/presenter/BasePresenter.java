package zenghao.com.study.MVP.presenter;

import android.content.Context;

/***
 * 通用形式
 * presenter 用泛型 不用写多个IPresenter
 * @param <E>
 * @param <T>
 */
public abstract class BasePresenter<E, T> {
    public Context context;
    public E mModel;
    public T mView;

    public void setVM(T v, E m) {
        this.mView = v;
        this.mModel = m;
        this.onStart();
    }

    public abstract void onStart();

}
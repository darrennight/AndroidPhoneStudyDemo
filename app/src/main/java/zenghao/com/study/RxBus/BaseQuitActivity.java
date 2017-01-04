package zenghao.com.study.RxBus;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import rx.Subscriber;
import rx.subscriptions.CompositeSubscription;

/**
 *通过RXBus发送信息退出所有activity
 * @author zenghao
 * @since 16/12/15 下午6:15
 */
public class BaseQuitActivity extends AppCompatActivity {

    private CompositeSubscription _subscriptions;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _subscriptions = new CompositeSubscription();

        _subscriptions.add(RxBus.getDefault().toObservable(Quit.class).subscribe(new Subscriber<Quit>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Quit quit){
                //退出自己
                finish();
            }
        }));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        _subscriptions.unsubscribe();
    }
}

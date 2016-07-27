package zenghao.com.study.lazyFragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import zenghao.com.study.R;

/**
 * Created by zenghao on 16/7/25.
 */
public class LazyFirstFragment extends LazyBaseFragment {

    private TextView mTxt;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_lazy_first;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected void initView() {
            mTxt = findView(R.id.tv_txt);

    }

    @Override
    protected void initData() {
        mTxt.postDelayed(new Runnable() {
            @Override
            public void run() {
                mTxt.setText(""+System.currentTimeMillis());
            }
        },2000);
    }
}

package Interview.ActivityAndFragment;

import android.app.Fragment;
import android.content.Context;

/**
 * TODO
 *
 * @author zenghao
 * @since 2019-10-12 14:48
 */
public class DemoFragment02 extends Fragment {

    //MainFragment开放的接口
    public static interface FragmentListener{

        //跳到h5页面
        void toH5Page();

        //展示消息
        void showMsg(String str);
    }

    public FragmentListener fragmentListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof DemoActivity02){
            fragmentListener = ((DemoActivity02) context);
        }
    }
}

package Interview.ActivityAndFragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Handler;


/**
 *
 *
 * @author zenghao
 * @since 2019-10-12 14:43
 */
public class DemoFragment01 extends Fragment {

    private Handler mHandler;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof DemoActivity01){
            mHandler = ((DemoActivity01) context).mHandler;
        }
    }
}

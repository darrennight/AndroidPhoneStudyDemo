package Interview.ActivityAndFragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * TODO
 *
 * @author zenghao
 * @since 2019-10-12 14:55
 */
public class FragmentArgument extends Fragment {

    public FragmentArgument getInstance(String arg){
        FragmentArgument fragment = new FragmentArgument();
        Bundle bundle = new Bundle();
        bundle.putString("arg",arg);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        bundle.getString("arg");
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}

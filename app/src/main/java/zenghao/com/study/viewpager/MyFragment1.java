package zenghao.com.study.viewpager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import zenghao.com.study.R;

/**
 * Created by zenghao on 16/7/1.
 */
public class MyFragment1 extends Fragment {

    private TextView mTxt;
    public static MyFragment1 newInstance(String s){
        MyFragment1 instance = new MyFragment1();
        Bundle bundle = new Bundle();
        bundle.putString("txt",s);
        instance.setArguments(bundle);

        return instance;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_vp_fragment1,container,false);
        Bundle bundle = getArguments();
        String txt = bundle.getString("txt");
        mTxt = ((TextView) view.findViewById(R.id.tv_txt));
        mTxt.setText(txt);
        Log.e("====","onCreateViewonCreateView1");
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.e("====","onViewCreatedonViewCreated2");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e("====","onActivityCreatedonActivityCreated3");
    }

    public void updateTxt(String s){
        mTxt.setText(s);
    }
}

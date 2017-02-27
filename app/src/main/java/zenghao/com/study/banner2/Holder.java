package zenghao.com.study.banner2;

import android.content.Context;
import android.view.View;

public interface Holder<T>{
    View createView(Context context);
    void UpdateUI(Context context,int position,T data);
} 
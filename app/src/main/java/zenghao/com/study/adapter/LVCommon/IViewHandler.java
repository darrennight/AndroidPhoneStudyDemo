package zenghao.com.study.adapter.LVCommon;

import android.view.View;
import android.view.ViewGroup;
 
/** 
 * Display of different types of entries for processing ListView 
 */ 
public interface IViewHandler<T> { 
    /** 
     * Callback function used to process ListView entries display 
     * 
     * @param parent 
     * @param holder 
     * @param data 
     * @param position 
     */ 
    void handle(ViewGroup parent, ViewHolder holder, T data, int position);
 
    /** 
     * Returns the layout resource 
     * 
     * @return 
     */ 
    int getItemViewLayoutId(); 
 
    /** 
     * Set ItemViewType, used for ListView tag recovery 
     * 
     * @return 
     */ 
    String getItemViewType();

    void setOnClickListener(IClickCallback listener);
} 
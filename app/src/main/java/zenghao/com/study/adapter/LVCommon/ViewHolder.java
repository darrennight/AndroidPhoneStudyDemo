package zenghao.com.study.adapter.LVCommon;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ViewHolder {
    private final SparseArray<View> mViews;
    private View mConvertView;

    private ViewHolder(Context context, ViewGroup parent, int layoutId, int position) {
        this.mViews = new SparseArray<>();
        mConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        //setTag  
        mConvertView.setTag(this);
    }

    /**
     * 拿到一个ViewHolder对象
     */
    public static ViewHolder get(Context context, View convertView, ViewGroup parent, int layoutId,
            int position) {

        if (convertView == null) {
            return new ViewHolder(context, parent, layoutId, position);
        }
        return (ViewHolder) convertView.getTag();
    }

    /**
     * 通过控件的Id获取对于的控件，如果没有则加入views
     */
    public <T extends View> T getView(int viewId) {

        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public View getConvertView() {
        return mConvertView;
    }


    /**
     * 设置对应id的控件的文本内容
     *
     * @param viewResId
     * @param stringResId 字符串资源id
     * @return
     */
    public void setText( int viewResId, int stringResId) {
        TextView view = getView(viewResId);
        view.setText(stringResId);
    }

    public ViewHolder setText(int id, CharSequence sequence) {
        TextView view = getView(id);
        if (view != null)
            view.setText(sequence);
        return this;
    }

    public ViewHolder show(int id) {
        View view = getView(id);
        if (view != null)
            view.setVisibility(View.VISIBLE);
        return this;
    }

    public ViewHolder hide(int id) {
        View view = getView(id);
        if (view != null)
            view.setVisibility(View.GONE);
        return this;
    }

    public ViewHolder setOnClickListener(int resId,View.OnClickListener listener){
        View view = getView(resId);
        if(view !=null){
            view.setOnClickListener(listener);
        }
        return this;
    }

}  
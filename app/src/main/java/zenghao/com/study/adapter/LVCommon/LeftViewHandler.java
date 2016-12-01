package zenghao.com.study.adapter.LVCommon;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import zenghao.com.study.R;
import zenghao.com.study.bean.User;

public class LeftViewHandler implements IViewHandler<People> {

    private IClickCallback mListener;

    @Override 
    public void handle(ViewGroup parent, ViewHolder holder, People data, final int position) {
        TextView view = holder.getView(R.id.tv_list_left);
        view.setText("left"+position);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClick(v,position);
            }
        });
    }
 
    @Override 
    public int getItemViewLayoutId() {
        //使用item布局
        return R.layout.item_list_common_left;
    }

    @Override 
    public String getItemViewType() {
        //使用item布局
        return this.getClass().getSimpleName();
    }

    @Override
    public void setOnClickListener(IClickCallback listener) {
        this.mListener = listener;
    }
}
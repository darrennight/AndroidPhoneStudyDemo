package zenghao.com.study.adapter.LVCommon;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import zenghao.com.study.R;
import zenghao.com.study.bean.User;

public class RightViewHandler implements IViewHandler<People> {
    @Override
    public void handle(ViewGroup parent, ViewHolder holder, People data, int position) {
        TextView view = holder.getView(R.id.tv_list_right);
        view.setText("right"+position);
    }

    @Override
    public int getItemViewLayoutId() {
        //使用item布局
        return R.layout.item_list_common_right;
    }

    @Override
    public String getItemViewType() {
        //使用item布局
        return this.getClass().getSimpleName();
    }


    @Override
    public void setOnClickListener(IClickCallback listener) {

    }
}
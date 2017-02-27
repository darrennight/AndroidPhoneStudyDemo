package zenghao.com.study.videoList.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.ButterKnife;
import zenghao.com.study.videoList.model.BaseItem;

public abstract class BaseViewHolder<T extends BaseItem> extends RecyclerView.ViewHolder {
 
    public BaseViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    } 
 
    public abstract void onBind(int position, T iItem);
} 
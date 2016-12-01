package zenghao.com.study.adapter.LVCommon;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.List;

/**
 * listView通用Adapter
 *
 * @author zenghao
 * @since 16/11/24 下午5:38
 */
public abstract class ListCommonAdapter<T> extends BaseAdapter {

    protected Context mContext;
    protected List<T> mDatas;

    public ListCommonAdapter(Context context, List<T> list) {
        this.mContext = context;
        this.mDatas = list;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = getViewHolder(position, convertView, parent);
        convert(holder, mDatas.get(position), position,parent);
        return holder.getConvertView();
    }



    private ViewHolder getViewHolder(int position, View convertView, ViewGroup parent) {
        return ViewHolder.get(mContext, convertView, parent, getItemLayoutId(position), position);
    }

    protected abstract void convert(ViewHolder holder, T data, int position,ViewGroup parent);
    protected abstract int getItemLayoutId(int position);
}

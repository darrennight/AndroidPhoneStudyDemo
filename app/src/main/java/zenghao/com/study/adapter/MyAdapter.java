package zenghao.com.study.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import zenghao.com.study.R;

/**
 * Created by zenghao on 15/12/7.
 */
public class MyAdapter extends BaseAdapter{
    private List<String> mList;
    private Context mContext;
    public MyAdapter(Context context,List<String> list){
        this.mContext = context;
        this.mList = list;
    }

    public boolean flag = false;

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_main_list,parent,false);
            holder.txt = ((TextView)convertView.findViewById(R.id.tv_num));
            holder.icon = ((ImageView) convertView.findViewById(R.id.iv_icon));
            holder.root = ((LinearLayout) convertView.findViewById(R.id.layout_root));
            convertView.setTag(holder);
        }else{
            holder = ((ViewHolder) convertView.getTag());
        }
        holder.txt.setText(mList.get(position));

        if(flag){
            holder.icon.setVisibility(View.GONE);
            LinearLayout.LayoutParams params = ((LinearLayout.LayoutParams) holder.txt.getLayoutParams());
            params.leftMargin = 0;
            holder.root.setLayoutParams(params);
        }else{
            holder.icon.setVisibility(View.VISIBLE);
            LinearLayout.LayoutParams params = ((LinearLayout.LayoutParams) holder.txt.getLayoutParams());
            //params.leftMargin = -100;
            params.leftMargin = 0;
            holder.root.setLayoutParams(params);
        }

        return convertView;
    }

    class ViewHolder {
        TextView txt;
        ImageView icon;
        LinearLayout root;
    }
}

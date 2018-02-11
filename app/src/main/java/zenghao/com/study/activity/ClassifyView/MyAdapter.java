package zenghao.com.study.activity.ClassifyView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
 
import java.util.List;
import zenghao.com.study.R;
import zenghao.com.study.view.classify.SimpleAdapter;

/** 
 * <p/> 
 * Date: 16/6/7 16:40 
 * Author: zhendong.wu@shoufuyou.com 
 * <p/> 
 */ 
public class MyAdapter extends SimpleAdapter<Bean, MyAdapter.ViewHolder> {
 
 
    public MyAdapter(List<List<Bean>> mData) {
        super(mData);
    } 
 
 
 
 
    @Override 
    protected ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        return new MyAdapter.ViewHolder(view);
    } 
 
    @Override 
    public View getView(ViewGroup parent, int mainPosition, int subPosition) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_inner,parent,false);
        return view;
    } 
 
    @Override 
    protected void onItemClick(View view, int parentIndex, int index) {
        Toast.makeText(view.getContext(),"parentIndex: "+parentIndex+"\nindex: "+index,Toast.LENGTH_SHORT).show();
    } 
 
    static class ViewHolder extends SimpleAdapter.ViewHolder {
 
        public ViewHolder(View itemView) {
            super(itemView);
        } 
    } 
} 
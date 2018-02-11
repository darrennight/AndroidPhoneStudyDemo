package zenghao.com.study.activity.ClassifyView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
 
import com.squareup.picasso.Picasso;
 
import java.util.List;
import zenghao.com.study.R;
import zenghao.com.study.view.classify.SimpleAdapter;

/** 
 * <p/> 
 * Date: 16/6/12 14:38 
 * Author: zhendong.wu@shoufuyou.com 
 * <p/> 
 */ 
public class BookListAdapter extends SimpleAdapter<Book,BookListAdapter.ViewHolder> {
 
 
    public BookListAdapter(List<List<Book>> mData) {
        super(mData);
    } 
 
 
    @Override 
    protected ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book,parent,false);
        return new ViewHolder(view);
    } 
 
    @Override 
    public View getView(ViewGroup parent, int mainPosition, int subPosition) {
        ImageView imageView = (ImageView) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book_inner,parent,false);
        String url = mData.get(mainPosition).get(subPosition).imageUrl;
        Picasso.with(parent.getContext()).load(url).into(imageView);
        return imageView;
    } 
 
    @Override 
    protected void onBindMainViewHolder(ViewHolder holder, int position) {
        List<Book> books = mData.get(position);
        if(books.size()>1){
            holder.name.setText("");
        }else { 
            holder.name.setText(books.get(0).name);
        } 
    } 
 
    @Override 
    protected void onBindSubViewHolder(ViewHolder holder, int mainPosition, int subPosition) {
        holder.name.setText(mData.get(mainPosition).get(subPosition).name+"");
    } 
 
    public static class ViewHolder extends SimpleAdapter.ViewHolder {
        TextView name;
        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.text_name);
        } 
    } 
} 
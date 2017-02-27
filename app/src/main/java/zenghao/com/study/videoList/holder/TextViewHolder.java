package zenghao.com.study.videoList.holder;
 
import android.view.View;
import android.widget.TextView;
 

import butterknife.Bind;
import zenghao.com.study.R;
import zenghao.com.study.videoList.model.TextItem;

/** 
 * @author Wayne 
 */ 
public class TextViewHolder extends BaseViewHolder<TextItem> {
 
    @Bind(R.id.text_view)
    TextView mTextView;
 
    public TextViewHolder(View itemView) {
        super(itemView);
    } 
 
    @Override 
    public void onBind(int position, TextItem iItem) {
        mTextView.setText(String.format("%s - %s", iItem.getText(), position));
    } 
} 
package zenghao.com.study.videoList.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import com.bumptech.glide.Glide;
import zenghao.com.study.R;
import zenghao.com.study.videoList.model.PicItem;

public class PicViewHolder extends BaseViewHolder<PicItem> {

    @Bind(R.id.pic_image_view)
    ImageView mImageView;
 
    @Bind(R.id.pic_text_view)
    TextView mTextView;
 
    public PicViewHolder(View itemView) {
        super(itemView);
    } 
 
    @Override 
    public void onBind(int position, PicItem iItem) {
        Glide.with(itemView.getContext())
                .load(iItem.getCoverUrl())
                .into(mImageView);
 
        mTextView.setText(String.format("PicItem %s", position));
    } 
} 
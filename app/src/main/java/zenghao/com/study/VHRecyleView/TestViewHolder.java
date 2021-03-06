package zenghao.com.study.VHRecyleView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import zenghao.com.study.R;


/**
 * Created by florentchampigny on 28/08/15.
 */
public class TestViewHolder extends MaterialLeanBack.ViewHolder {

    protected TextView textView;
    protected ImageView imageView;

    public TestViewHolder(View itemView) {
        super(itemView);
        textView = (TextView) itemView.findViewById(R.id.textView);
        imageView = (ImageView) itemView.findViewById(R.id.imageView);
    }
}

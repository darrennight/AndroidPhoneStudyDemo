package zenghao.com.study.UI.widget;

import android.content.Context;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Toast;
import zenghao.com.study.R;

/**
 * Created by zenghao on 15/11/18.
 */
public class LinkClickableSpan extends ClickableSpan {

   private String str = null;
   private Context mContext;


    public LinkClickableSpan(String str,Context con) {
        this.str = str;
        this.mContext = con;
    }

    @Override
    public void onClick(View widget) {
        if(!str.startsWith("http://")){
            str = "http://"+str;
        }
       // WebViewActivity.launch(mContext,str);
        Toast.makeText(mContext,str,Toast.LENGTH_SHORT).show();
    }
    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);
        // 去掉超链接的下划线
        ds.setUnderlineText(false);
        ds.setColor(mContext.getResources().getColor(R.color.colorPrimary));
        ds.clearShadowLayer();
    }
}
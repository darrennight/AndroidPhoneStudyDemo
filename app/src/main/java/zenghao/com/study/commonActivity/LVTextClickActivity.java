package zenghao.com.study.commonActivity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import zenghao.com.study.R;
import zenghao.com.study.UI.widget.LinkClickableSpan;
import zenghao.com.study.UI.widget.LinkTextView;

/**
 * listView中item中TextView文字点击事件
 *
 * @author zenghao
 * @since 16/11/30 下午7:41
 */
public class LVTextClickActivity extends AppCompatActivity {

    private ListView mListView;
    private String txt = "哈哈哈http://breadtrip.com呵呵呵呵";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lv_txt_click);
        mListView = ((ListView) findViewById(R.id.lv_txt_click));
        mListView.setAdapter(new MyAdapter(this));
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

    }


    class MyAdapter extends BaseAdapter{

        private Context mContext;
        private List<String> mList;
        public MyAdapter(Context context , List<String> list){
            this.mContext = context;
            this.mList = list;
        }

        public MyAdapter(Context context ){
            this.mContext = context;
        }
        @Override
        public int getCount() {
            return 10;
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
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_lv_txt_click,parent,false);
                holder = new ViewHolder();
                holder.mText = ((LinkTextView) convertView.findViewById(R.id.tv_click_link));
                convertView.setTag(holder);
            }else{
                holder = ((ViewHolder) convertView.getTag());
            }
            holder.mText.setMovementMethod(LinkTextView.LocalLinkMovementMethod.getInstance());
            holder.mText.setText(setAutoLink(txt,mContext));
            return convertView;
        }

        class ViewHolder{
            LinkTextView mText;
        }
    }


    public static SpannableStringBuilder setAutoLink(String text, Context context) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(text);
        Pattern pattern3 = Pattern.compile("(?:(?:https|http|ftp)\\:\\/\\/)?(?:[0-9a-zA-Z]+\\.)?breadtrip\\.com[0-9a-zA-Z\\/]*");

        Matcher matcher = pattern3.matcher(spannableStringBuilder);
        matcher = pattern3.matcher(spannableStringBuilder);

        while (matcher.find()) {
            setClickable(spannableStringBuilder, matcher, context);
        }

        return spannableStringBuilder;
    }

    private static void setClickable(SpannableStringBuilder clickableHtmlBuilder, Matcher matcher, Context context) {

        int start = matcher.start();

        int end = matcher.end();

        clickableHtmlBuilder.setSpan(new LinkClickableSpan(matcher.group(), context), start, end,
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
    }
}

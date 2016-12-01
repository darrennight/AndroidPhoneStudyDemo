package zenghao.com.study.adapter.LVPartRefresh;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import zenghao.com.study.R;

/**
 *listView局部刷新
 * @author zenghao
 * @since 16/11/29 下午8:23
 */
public class LVPartRefreshActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_part_refresh);
    }

    /*
    局部刷新 通过Holder 不需要重新 findviewByID
    private void updateProgressPartly(int progress,int position){
        int firstVisiblePosition = listview.getFirstVisiblePosition();
        int lastVisiblePosition = listview.getLastVisiblePosition();
        if(position>=firstVisiblePosition && position<=lastVisiblePosition){
            View view = listview.getChildAt(position - firstVisiblePosition);
            if(view.getTag() instanceof ViewHolder){
                ViewHolder vh = (ViewHolder)view.getTag();
                vh.pb.setProgress(progress);
            }
        }
    }*/



    /*
    getFirstVisiblePosition()，该方法获取当前状态下list的第一个可见item的position。
    getLastVisiblePosition()，该方法获取当前状态下list的最后一个可见item的position。
    getItemAtPosition（int position），该方法返回当前状态下position位置上listView的convertView
    这个方法是google 2011年开发者大会上提出的方法——ListView单条更新 getView是adapter的
    private void updateSingleRow(ListView listView, long id) {

        if (listView != null) {
            int start = listView.getFirstVisiblePosition();
            for (int i = start, j = listView.getLastVisiblePosition(); i <= j; i++)
                if (id == ((Messages) listView.getItemAtPosition(i)).getId()) {
                    View view = listView.getChildAt(i - start);
                    getView(i, view, listView);
                    break;
                }
        }
    }*/
}

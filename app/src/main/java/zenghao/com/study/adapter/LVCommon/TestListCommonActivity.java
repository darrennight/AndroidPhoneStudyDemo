package zenghao.com.study.adapter.LVCommon;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import zenghao.com.study.R;

/**
 *还可以封装IViewHandler和当前activity的交互
 * @author zenghao
 * @since 16/11/24 下午6:15
 */
public class TestListCommonActivity extends Activity {

    private ListView mListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list_common);
        mListView = ((ListView) findViewById(R.id.lv_common));
        List<People> mlist = new ArrayList<>();
        for (int i=0;i<=10;i++){
            mlist.add(new People());
        }

        //mListView.setAdapter(new MyAdapter(this,mlist));

        //单类型使用
        /*mListView.setAdapter(new ListCommonAdapter<People>(this,mlist) {
            @Override
            protected void convert(ViewHolder holder, People data, int position,ViewGroup parent) {

                TextView view = holder.getView(R.id.tv_list_single);
                view.setText("hah"+position);
            }

            @Override
            protected int getItemLayoutId(int position) {
                return R.layout.item_list_common_single;
            }
        });*/



        //多类型使用
        List<People> mMultilist = new ArrayList<>();
        for (int i=0;i<=20;i++){
            if(i % 2 == 0){
                mMultilist.add(new student());
            }else{
                mMultilist.add(new Teacher());
            }

        }

        MutilTypleCommonAdapter adapter = new MutilTypleCommonAdapter(this,mMultilist);
        adapter.registTyple(student.class,LeftViewHandler.class);
        adapter.registTyple(Teacher.class,RightViewHandler.class);
        adapter.setOnClickListener(new IClickCallback() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(TestListCommonActivity.this,"click"+position,Toast.LENGTH_SHORT).show();
            }
        });

        mListView.setAdapter(adapter);
        //具体对UI的操作在 LeftViewHandler RightViewHandler中

    }




    class MyAdapter extends BaseAdapter{
        private Context mContext;
        private List<People> mList;
        public MyAdapter(Context mContext,List<People> list){
            this.mContext = mContext;
            this.mList = list;
        }
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
            MyViewHolder holder;
            if(convertView == null){
                holder = new MyViewHolder();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_list_common_right,parent,false);
                holder.mTV = ((TextView) convertView.findViewById(R.id.tv_list_right));
                convertView.setTag(holder);
            }else {
                holder = ((MyViewHolder) convertView.getTag());
            }

            return convertView;
        }
    }

    class MyViewHolder{
        TextView mTV;
    }
}

package zenghao.com.study.DownLoad;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import zenghao.com.study.DownLoad.db.DownLoadDBManger;
import zenghao.com.study.R;

/**
 * 下载框架测试
 * 6M :  http://download.chinaunix.net/down.php?id=10608&ResourceID=5267&site=1
 * 200M :http://down.tech.sina.com.cn/download/d_load.php?d_id=49535&down_id=1&ip=42.81.45.159
 * @author zenghao
 * @since 16/12/4 下午7:57
 */
public class DownLoadActivity extends AppCompatActivity implements DownLoadManger.TestCallback{

    private Button mStart;
    private Button mPause;
    private Button mReStart;
    private ListView mListView;
    private MyAdapter mAdapter;
    private List<IDownLoadInfo> mList;
    private String saveDir = Environment.getExternalStorageDirectory() + "/AigeStudio/";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down_load);
        initViews();
    }

    private void initViews(){
        mListView = ((ListView) findViewById(R.id.lv_down_task));
        mPause = ((Button) findViewById(R.id.btn_pause));
        mStart = ((Button) findViewById(R.id.btn_start));
        mReStart = ((Button) findViewById(R.id.btn_restart));
        mStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DownLoadInfo info = new DownLoadInfo();
                info.setFilePath(saveDir);
                info.setFileName("test.apk");
                info.setDownUrl("http://download.chinaunix.net/down.php?id=10608&ResourceID=5267&site=1");
                DownLoadManger.getInstance(DownLoadActivity.this).addInfo(info);



                /*DownLoadInfo info2 = new DownLoadInfo();
                info2.setFilePath(saveDir);
                info2.setFileName("test2.apk");
                info2.setDownUrl("http://download.chinaunix.net/down.php?id=10608&ResourceID=5267&site=1");
                DownLoadManger.getInstance(DownLoadActivity.this).addInfo(info2);*/

            }
        });
        mPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownLoadInfo info = new DownLoadInfo();
                info.setFilePath(saveDir);
                DownLoadManger.getInstance(DownLoadActivity.this).pauseDownLoad(info);
            }
        });

        mReStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownLoadInfo info = new DownLoadInfo();
                info.setFilePath(saveDir);
                DownLoadManger.getInstance(DownLoadActivity.this).reStart(info);
            }
        });

        mList = new ArrayList<>();

        mList.addAll(DownLoadDBManger.getInstance(this).getDownInfo());
        if(mList.isEmpty()){
            for (int i=0;i<2;i++){
                DownLoadInfo info = new DownLoadInfo();
                info.setFilePath(saveDir);
                info.setFileName("test"+i+".apk");
                info.setDownUrl("http://download.chinaunix.net/down.php?id=10608&ResourceID=5267&site=1");
                info.setStatus(DownStatus.WAIT);
                mList.add(info);
            }
        }


        mAdapter = new MyAdapter(this,mList);
        mListView.setAdapter(mAdapter);

        DownLoadManger.getInstance(DownLoadActivity.this).setCallBack(this);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                IDownLoadInfo info = mList.get(position);

                if(info.getStatus() == DownStatus.WAIT){
                    DownLoadManger.getInstance(DownLoadActivity.this).addInfo(info);
                }else if(info.getStatus() == DownStatus.STARTING){
                    DownLoadManger.getInstance(DownLoadActivity.this).pauseDownLoad(info);
                }else if(info.getStatus() == DownStatus.PAUSE){
                    DownLoadManger.getInstance(DownLoadActivity.this).reStart(info);
                }
            }
        });

    }



    class MyAdapter extends BaseAdapter{
        private Context mContext;
        private List<IDownLoadInfo> mList;
        public MyAdapter(Context context , List<IDownLoadInfo> list){
            this.mContext = context;
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

            ViewHolder holder;
            if(convertView == null){
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_down_task,parent,false);
                holder = new ViewHolder();
                holder.name = ((TextView) convertView.findViewById(R.id.tv_task_name));
                holder.status = ((TextView) convertView.findViewById(R.id.tv_task_status));
                holder.progress = ((ProgressBar) convertView.findViewById(R.id.pb_task_pro));
                convertView.setTag(holder);
            }else {
                holder = ((ViewHolder) convertView.getTag());
            }
            IDownLoadInfo info = mList.get(position);
            holder.progress.setMax(100);
            holder.progress.setProgress(info.getProgress());
            holder.name.setText(info.getFileName());
            holder.status.setText(info.getStatus().toString());

            return convertView;
        }

        class ViewHolder{
            TextView name;
            ProgressBar progress;
            TextView status;
        }
    }

    @Override
    public void onCall(IDownLoadInfo info) {
        Message msg = Message.obtain();
        msg.obj = info;
        mHandler.sendMessage(msg);
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            IDownLoadInfo info = ((IDownLoadInfo) msg.obj);
            if (mListView != null) {
                int start = mListView.getFirstVisiblePosition();
                for (int i = start, j = mListView.getLastVisiblePosition(); i <= j; i++)
                    if (info.getFileName().equalsIgnoreCase(((IDownLoadInfo)mListView.getItemAtPosition(i)).getFileName())) {
                        View view = mListView.getChildAt(i - start);
                        //getView(i, view, listView);
                        if(view.getTag() instanceof MyAdapter.ViewHolder){
                            MyAdapter.ViewHolder vh = (MyAdapter.ViewHolder)view.getTag();
                            int pro = (info.getStart()*100)/info.getTotalBytes();
                            vh.progress.setProgress(pro);
                            vh.status.setText(info.getStatus().toString());
                            mList.get(i - start).setProgress(pro);
                            mList.get(i - start).setStatus(info.getStatus());
                            Log.e("====","=="+pro);
                        }
                        break;
                    }
            }
        }
    };

}

package zenghao.com.study.DownLoadV2;

import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.BaseColumns;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import zenghao.com.study.DownLoad.DownLoadActivity;
import zenghao.com.study.DownLoad.DownLoadManger;
import zenghao.com.study.DownLoad.IDownLoadInfo;
import zenghao.com.study.DownLoadV2.db.DownLoadDBHelpV2;
import zenghao.com.study.DownLoadV2.provider.DownLoadProviderV2;
import zenghao.com.study.DownLoadV2.provider.MyCursorAdapter;
import zenghao.com.study.DownLoadV2.provider.SpecialLoaderV2;
import zenghao.com.study.DownLoadV2.service.DownLoadServiceV2;
import zenghao.com.study.Loader.SpecialLoader;
import zenghao.com.study.R;

/**
 * 下载升级V2
 * TODO 下载慢 存储数据库原因?
 * @author zenghao
 * @since 16/12/11 下午1:21
 */
public class DownLoadV2ActivityV2 extends AppCompatActivity implements DownLoadMangerV2.TestCallback ,LoaderManager.LoaderCallbacks<Cursor>{

    private Button mStart;
    private ListView mListView;
    private List<IDownLoadInfoV2> mList;
    private MyAdapter myAdapter;
    private ContentObserver mObserver;
    private LoaderManager manager = null;
    private MyCursorAdapter myCursorAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down_v2);
        mList = new ArrayList<>();
        manager = getLoaderManager();
        //第一个参数 id 随意  第二个参数Bundle onCreateLoader中接收
        manager.initLoader(1000, null, this);

        DownLoadMangerV2.getInstance(DownLoadV2ActivityV2.this).setCallBack(this);
        initView();
        mObserver = new ContentObserver(new Handler()) {
            @Override
            public void onChange(boolean selfChange, Uri uri) {
                super.onChange(selfChange, uri);
                getTaskFromDb();
                myAdapter.notifyDataSetChanged();
            }
        };
        //getContentResolver().registerContentObserver(DownLoadProviderV2.TASK_CONTENT_URI, true, mObserver);

    }

    private void initView(){
        mStart = ((Button) findViewById(R.id.btn_start_down));
        mListView = ((ListView) findViewById(R.id.lv_down_v2));
        getTaskFromDb();
        myAdapter = new MyAdapter(this,mList);
        Uri uri = DownLoadProviderV2.TASK_CONTENT_URI;
        Cursor cursor = getContentResolver().query(uri, new String[]{BaseColumns._ID,DownLoadDBHelpV2.DL_FILE_NAME,DownLoadDBHelpV2.DL_FILE_PROGRESS }, null, null, null);

        myCursorAdapter = new MyCursorAdapter(this,cursor);

        mListView.setAdapter(myCursorAdapter);



        mStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(new Intent(DownLoadV2ActivityV2.this, DownLoadServiceV2.class));
            }
        });

    }

    private void getTaskFromDb(){
        Uri uri = DownLoadProviderV2.TASK_CONTENT_URI;
        Cursor cursor = getContentResolver().query(uri, new String[]{BaseColumns._ID,DownLoadDBHelpV2.DL_FILE_NAME,DownLoadDBHelpV2.DL_FILE_PROGRESS }, null, null, null);

        if(cursor != null){
            mList.clear();
            while (cursor.moveToNext()){
                IDownLoadInfoV2 info = new DownLoadInfoV2();
                info.set_id(cursor.getInt(cursor.getColumnIndex(BaseColumns._ID)));
                info.setFileName(cursor.getString(cursor.getColumnIndex(DownLoadDBHelpV2.DL_FILE_NAME)));
                info.setFilePath(cursor.getString(cursor.getColumnIndex(DownLoadDBHelpV2.DL_FILE_PATH)));
                info.setStart(cursor.getInt(cursor.getColumnIndex(DownLoadDBHelpV2.DL_FILE_START)));
                info.setEnd(cursor.getInt(cursor.getColumnIndex(DownLoadDBHelpV2.DL_FILE_END)));
                info.setProgress(cursor.getInt(cursor.getColumnIndex(DownLoadDBHelpV2.DL_FILE_PROGRESS)));
                info.setDownUrl(cursor.getString(cursor.getColumnIndex(DownLoadDBHelpV2.DL_FILE_DWONURL)));
                info.setDisposition(cursor.getString(cursor.getColumnIndex(DownLoadDBHelpV2.DL_FILE_DISPOSITION)));
                info.setLocation(cursor.getString(cursor.getColumnIndex(DownLoadDBHelpV2.DL_FILE_LOCATION)));
                info.setMimeType(cursor.getString(cursor.getColumnIndex(DownLoadDBHelpV2.DL_FILE_MIMETYPE)));
                info.setTotalBytes(cursor.getInt(cursor.getColumnIndex(DownLoadDBHelpV2.DL_FILE_TOTALBYTES)));
                info.setStatus(changeStatus(cursor.getInt(cursor.getColumnIndex(DownLoadDBHelpV2.DL_FILE_STATUS))));
                mList.add(info);
            }
        }

    }



    class MyAdapter extends BaseAdapter {
        private Context mContext;
        private List<IDownLoadInfoV2> mList;
        public MyAdapter(Context context , List<IDownLoadInfoV2> list){
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
            IDownLoadInfoV2 info = mList.get(position);
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


    private int changeStatus(DownStatusV2 status){
        if(status == DownStatusV2.STARTING){
            return 0;
        }else if(status == DownStatusV2.PAUSE){
            return 1;
        }else if(status == DownStatusV2.WAIT){
            return 2;
        }else if(status == DownStatusV2.ERROR){
            return 3;
        }else if(status == DownStatusV2.FINISH){
            return 4;
        }
        return 4;
    }

    private DownStatusV2 changeStatus(int status){
        if(status == 0){
            return DownStatusV2.STARTING;
        }else if(status == 1){
            return DownStatusV2.PAUSE;
        }else if(status == 2){
            return DownStatusV2.WAIT;
        }else if(status == 3){
            return DownStatusV2.ERROR;
        }else if(status == 4){
            return DownStatusV2.FINISH;
        }
        return DownStatusV2.WAIT;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mObserver!=null){
            getContentResolver().unregisterContentObserver(mObserver);
        }

    }

    @Override
    public void onCall(IDownLoadInfoV2 info) {
        Message msg = Message.obtain();
        msg.obj = info;
        mHandler.sendMessage(msg);
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            IDownLoadInfoV2 info = ((IDownLoadInfoV2) msg.obj);
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

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        SpecialLoaderV2 loader1 = new SpecialLoaderV2(this);
        return loader1;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        myCursorAdapter.changeCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}

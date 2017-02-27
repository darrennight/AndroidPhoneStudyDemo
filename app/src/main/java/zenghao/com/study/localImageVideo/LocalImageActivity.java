package zenghao.com.study.localImageVideo;

import android.content.ContentResolver;
import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.GridView;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import zenghao.com.study.R;

/**
 * 读取系统数据库获取 图片视频 列表
 * @author zenghao
 * @since 16/10/20 上午10:47
 */
public class LocalImageActivity extends AppCompatActivity {

    private GridView mGridView;
    private HashMap<String, List<String>> mGruopMap = new HashMap<>();
    private List<ImageBean> list = new ArrayList<>();

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //adapter = new GroupAdapter(MainActivity.this, list = subGroupOfImage(mGruopMap), mGroupGridView);
            //mGroupGridView.setAdapter(adapter);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_image);
        mGridView = ((GridView) findViewById(R.id.gv_local_image));
        //initData();

        //init();

        //getThumbnail();

        getVideoAndImage();


        /*
        指定某个目录查询 有待验证
        //selection: 指定查询条件
        String selection = MediaStore.Images.Media.DATA + " like %?";
        //设定查询目录
        String path="/mnt/sdcard/youpicpath";
        //定义selectionArgs：
        String[] selectionArgs = {path+"%"};
        c = this.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null,
                selection, selectionArgs, null);*/
    }

    /**
     * 图片真实路径
     */
    private void initData() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                ContentResolver mContentResolver = LocalImageActivity.this.getContentResolver();

                //只查询jpeg和png的图片
                Cursor mCursor = mContentResolver.query(mImageUri, null,MediaStore.Images.Media.MIME_TYPE
                                + "=? or "
                                + MediaStore.Images.Media.MIME_TYPE
                                + "=?", new String[] { "image/jpeg", "image/png" },
                        MediaStore.Images.Media.DATE_MODIFIED);
                //MediaStore.Video.Media.MIME_TYPE

                if (mCursor == null) {
                    return;
                }

                while (mCursor.moveToNext()) {
                    //获取图片的路径
                    String path = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DATA));
                    Log.e("===","===="+path);
                    //没有缩略图路径
                    Log.e("===",""+ Arrays.toString(mCursor.getColumnNames()));
                    //获取该图片的父路径名
                    String parentName = new File(path).getParentFile().getName();


                    //根据父路径名将图片放入到mGruopMap中
                    if (!mGruopMap.containsKey(parentName)) {
                        List<String> chileList = new ArrayList<String>();
                        chileList.add(path);
                        mGruopMap.put(parentName, chileList);
                    } else {
                        mGruopMap.get(parentName).add(path);
                    }
                }

                //通知Handler扫描图片完成
                mHandler.sendEmptyMessage(1);
                mCursor.close();
            }
        }).start();
    }

    /**
     * 组装分组界面GridView的数据源，因为我们扫描手机的时候将图片信息放在HashMap中
     * 所以需要遍历HashMap将数据组装成List
     */
    private List<ImageBean> subGroupOfImage(HashMap<String, List<String>> mGruopMap) {
        if (mGruopMap.size() == 0) {
            return null;
        }
        List<ImageBean> list = new ArrayList<ImageBean>();

        Iterator<Map.Entry<String, List<String>>> it = mGruopMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, List<String>> entry = it.next();
            ImageBean mImageBean = new ImageBean();
            String key = entry.getKey();
            List<String> value = entry.getValue();

            mImageBean.setFolderName(key);
            mImageBean.setImageCounts(value.size());
            mImageBean.setTopImagePath(value.get(0));//获取该组的第一张图片

            list.add(mImageBean);
        }

        return list;
    }

    private Cursor cursor;

    /**
     * 获取视频 真实路径
     */
    private void init(){

        String[] thumbColumns = new String[]{
                MediaStore.Video.Thumbnails.DATA,
                MediaStore.Video.Thumbnails.VIDEO_ID
        };

        String[] mediaColumns = new String[]{
                MediaStore.Video.Media.DATA,
                MediaStore.Video.Media._ID,
                MediaStore.Video.Media.TITLE,
                MediaStore.Video.Media.MIME_TYPE
        };

        //首先检索SDcard上所有的video
        cursor = this.managedQuery(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, mediaColumns, null, null, null);

        ArrayList<VideoInfo> videoList = new ArrayList<>();

        if(cursor.moveToFirst()){
            do{
                VideoInfo info = new VideoInfo();

                info.filePath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
                info.mimeType = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.MIME_TYPE));
                info.title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.TITLE));

                Log.e("===path",""+info.filePath);
                Log.e("===mimeType",""+info.mimeType);
                Log.e("===title",""+info.title);

                //获取当前Video对应的Id，然后根据该ID获取其Thumb
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID));


                String selection = MediaStore.Video.Thumbnails.VIDEO_ID +"=?";
                String[] selectionArgs = new String[]{
                        id+""
                };
                Cursor thumbCursor = this.managedQuery(MediaStore.Video.Thumbnails.EXTERNAL_CONTENT_URI, thumbColumns, selection, selectionArgs, null);

                if(thumbCursor.moveToFirst()){
                    info.thumbPath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Thumbnails.DATA));
                    Log.e("===thumbPath",""+info.thumbPath);
                }

                //然后将其加入到videoList
                videoList.add(info);

            }while(cursor.moveToNext());
        }}



    /**
     * 得到缩略图，这里主要得到的是图片的ID值
     */
    private void getThumbnail() {
        ContentResolver mContentResolver = LocalImageActivity.this.getContentResolver();
        String[] projection = { MediaStore.Images.Thumbnails._ID, MediaStore.Images.Thumbnails.IMAGE_ID,
                MediaStore.Images.Thumbnails.DATA};
        Cursor cursor1 = MediaStore.Images.Thumbnails.queryMiniThumbnails(mContentResolver, MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI,
                MediaStore.Images.Thumbnails.MINI_KIND, projection);
        getThumbnailColumnData(cursor1);
        cursor1.close();
    }

    /**
     * 从数据库中得到缩略图
     * @param cur
     */
    private void getThumbnailColumnData(Cursor cur) {
        if (cur.moveToFirst()) {
            int image_id;
            String image_path;
            int image_idColumn = cur.getColumnIndex(MediaStore.Images.Thumbnails.IMAGE_ID);
            int dataColumn = cur.getColumnIndex(MediaStore.Images.Thumbnails.DATA);
            do {
                image_id = cur.getInt(image_idColumn);
                image_path = cur.getString(dataColumn);

                Log.e("====imageID","==="+image_id);
                Log.e("====image_path","==="+image_path);
                //thumbnailList.put("" + image_id, image_path);
            } while (cur.moveToNext());
        }
    }




    public static Bitmap getVideoThumbnail(Context context, ContentResolver cr, String Videopath) {
        ContentResolver testcr = context.getContentResolver();
        String[] projection = { MediaStore.Video.Media.DATA, MediaStore.Video.Media._ID, };
        String whereClause = MediaStore.Video.Media.DATA + " = '" + Videopath + "'";
        Cursor cursor = testcr.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, projection, whereClause,
                null, null);
        int _id = 0;
        String videoPath = "";
        if (cursor == null || cursor.getCount() == 0) {
            return null;
        }
        if (cursor.moveToFirst()) {

            int _idColumn = cursor.getColumnIndex(MediaStore.Video.Media._ID);
            int _dataColumn = cursor.getColumnIndex(MediaStore.Video.Media.DATA);
            do {
                _id = cursor.getInt(_idColumn);
                videoPath = cursor.getString(_dataColumn);
            } while (cursor.moveToNext());
        }
        cursor.close();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inDither = false;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        Bitmap bitmap = MediaStore.Video.Thumbnails.getThumbnail(cr, _id, MediaStore.Images.Thumbnails.MINI_KIND,
                options);
        return bitmap;
    }


    private void getVideoAndImage(){
        String[] projection = {
                MediaStore.Files.FileColumns._ID,
                MediaStore.Files.FileColumns.DATA,
                MediaStore.Files.FileColumns.DATE_ADDED,
                MediaStore.Files.FileColumns.MEDIA_TYPE,
                MediaStore.Files.FileColumns.MIME_TYPE,
                MediaStore.Files.FileColumns.TITLE
        };

        // Return only video and image metadata.
        String selection = MediaStore.Files.FileColumns.MEDIA_TYPE + "="
                + MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE
                + " OR "
                + MediaStore.Files.FileColumns.MEDIA_TYPE + "="
                + MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;


        /*
         并且条件
        selection = MediaStore.Files.FileColumns.MEDIA_TYPE + "="
                + MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE
                + " AND "
                + MediaStore.Files.FileColumns.PARENT + "=" +"14742"
                + " OR "
                + MediaStore.Files.FileColumns.MEDIA_TYPE + "="
                + MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO
                + " AND "
                + MediaStore.Files.FileColumns.PARENT + "=" +"14742";*/

        Uri queryUri = MediaStore.Files.getContentUri("external");

        CursorLoader cursorLoader = new CursorLoader(
                this,
                queryUri,
                projection,
                selection,
                null, // Selection args (none).
                MediaStore.Files.FileColumns.DATE_ADDED + " DESC" // Sort order.
        );

        Cursor cursor = cursorLoader.loadInBackground();

        if(cursor!=null){
            while (cursor.moveToNext()){
              String path =   cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA));
                String type = cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.MIME_TYPE));
                int mediatype = cursor.getInt(cursor.getColumnIndex(MediaStore.Files.FileColumns.MEDIA_TYPE));
                Log.e("===path",path);
                Log.e("===type",type);
                Log.e("===mediatype",""+mediatype);

            }
        }
    }
}

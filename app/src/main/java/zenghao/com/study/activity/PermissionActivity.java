package zenghao.com.study.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.renderscript.Allocation;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import zenghao.com.study.R;

/**
 * Created by zenghao on 15/12/7.
 * implements ActivityCompat.OnRequestPermissionsResultCallback
 */
public class PermissionActivity extends AppCompatActivity  {
    //新权限处理  也需要在清单文件中 注册所需要的 权限  常规权限不需要动态申请

    public static final int EXTERNAL_STORAGE_REQ_CODE = 10 ;
    private Button btn_write;
    ImageView img;
    TextView testtxt;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);
        initView();

         img = ((ImageView) findViewById(R.id.iv_blur));
        testtxt = ((TextView) findViewById(R.id.tv_test));
        img.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                img.getViewTreeObserver().removeOnPreDrawListener(this);
                img.buildDrawingCache();

                Bitmap bmp = img.getDrawingCache();
                blur(bmp, testtxt);
                return true;
            }
        });

    }

    private void initView() {
        btn_write = ((Button) this.findViewById(R.id.btn_writesdcard));
        btn_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermission();
            }
        });
    }
    //在sdcard上新建一个名为fileName的文件
    private void createFile(String fileName){
        File sdcard = Environment.getExternalStorageDirectory();
        File newFile = new File(sdcard,"/" + fileName) ;
        if(!newFile.exists()){
            try {
                newFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void requestPermission(){
        //判断当前Activity是否已经获得了该权限
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {

            //如果App的权限申请曾经被用户拒绝过，就需要在这里跟用户做出解释
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(this,"please give me the permission",Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        EXTERNAL_STORAGE_REQ_CODE);

//                Snackbar.make(mLayout, R.string.permission_camera_rationale,
//                        Snackbar.LENGTH_INDEFINITE)
//                        .setAction(R.string.ok, new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                ActivityCompat.requestPermissions(MainActivity.this,
//                                        new String[]{Manifest.permission.CAMERA},
//                                        REQUEST_CAMERA);
//                            }
//                        })
//                        .show();

            } else {
                //进行权限请求
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        EXTERNAL_STORAGE_REQ_CODE);
            }
        }else{
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    EXTERNAL_STORAGE_REQ_CODE);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case EXTERNAL_STORAGE_REQ_CODE: {
                // 如果请求被拒绝，那么通常grantResults数组为空
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //申请成功，进行相应操作
                    Toast.makeText(this,"yesyesyes",Toast.LENGTH_SHORT).show();
                    createFile("hello.txt");
                } else {
                    //申请失败，可以继续向用户解释。
                    Toast.makeText(this,"errorerror",Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void blur(Bitmap bkg, View view) {

        float scaleFactor = 1;
        float radius = 20;

        //scaleFactor = 8;
        //radius = 2;

        Bitmap overlay = Bitmap.createBitmap((int) (view.getMeasuredWidth() / scaleFactor),
                (int) (view.getMeasuredHeight() / scaleFactor), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(overlay);

        canvas.translate(-view.getLeft() / scaleFactor, -view.getTop() / scaleFactor);
        canvas.scale(1 / scaleFactor, 1 / scaleFactor);
        Paint paint = new Paint();
        paint.setFlags(Paint.FILTER_BITMAP_FLAG);
        canvas.drawBitmap(bkg, 0, 0, paint);

        RenderScript rs = RenderScript.create(this);

        Allocation overlayAlloc = Allocation.createFromBitmap(
                rs, overlay);

        ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(
                rs, overlayAlloc.getElement());

        blur.setInput(overlayAlloc);

        blur.setRadius(radius);

        blur.forEach(overlayAlloc);

        overlayAlloc.copyTo(overlay);

        view.setBackground(new BitmapDrawable(
                getResources(), overlay));

        rs.destroy();

    }

}

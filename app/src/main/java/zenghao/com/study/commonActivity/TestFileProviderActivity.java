package zenghao.com.study.commonActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import zenghao.com.study.FileProvider.FileProvider7;
import zenghao.com.study.R;

/**
 *7.0fileProvider
 * 解决兼容问题
 * https://github.com/hongyangAndroid/FitAndroid7
 * @author zenghao
 * @since 2017/7/4 下午3:41
 */
public class TestFileProviderActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_TAKE_PHOTO = 0x110;
    private static final int REQ_PERMISSION_CODE_SDCARD = 0X111;
    private static final int REQ_PERMISSION_CODE_TAKE_PHOTO = 0X112;

    private String mCurrentPhotoPath;
    private ImageView mIvPhoto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_provider);

        mIvPhoto = (ImageView) findViewById(R.id.id_iv);

    }

    public void installApk(View view) {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQ_PERMISSION_CODE_SDCARD);

        } else {
            installApk();
        }

    }


    private void installApk() {
        // 需要自己修改安装包路径
        File file = new File(Environment.getExternalStorageDirectory(),
                "huawei.apk");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        FileProvider7.setIntentDataAndType(this,
                intent, "application/vnd.android.package-archive", file, true);

        /*//直接使用报错
        intent.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive");*/
        startActivity(intent);
    }


    public void takePhotoNoCompress(View view) {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQ_PERMISSION_CODE_TAKE_PHOTO);

        } else {
            takePhotoNoCompress();
        }
    }


    private void takePhotoNoCompress() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            String filename = new SimpleDateFormat("yyyyMMdd-HHmmss", Locale.CHINA)
                    .format(new Date()) + ".png";
            File file = new File(Environment.getExternalStorageDirectory(), filename);
            mCurrentPhotoPath = file.getAbsolutePath();

            Uri fileUri = FileProvider7.getUriForFile(this, file);

            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            startActivityForResult(takePictureIntent, REQUEST_CODE_TAKE_PHOTO);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQ_PERMISSION_CODE_SDCARD) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                installApk();
            } else {
                // Permission Denied
                Toast.makeText(TestFileProviderActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
            return;
        } else if (requestCode == REQ_PERMISSION_CODE_TAKE_PHOTO) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                takePhotoNoCompress();
            } else {
                // Permission Denied
                Toast.makeText(TestFileProviderActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_TAKE_PHOTO) {
            mIvPhoto.setImageBitmap(BitmapFactory.decodeFile(mCurrentPhotoPath));
        }
        // else tip?

    }
}
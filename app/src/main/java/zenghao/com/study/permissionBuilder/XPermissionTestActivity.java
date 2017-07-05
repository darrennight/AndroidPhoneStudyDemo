package zenghao.com.study.permissionBuilder;

import android.Manifest;
import android.annotation.TargetApi;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import zenghao.com.study.R;
import zenghao.com.study.permissionBuilder.listener.XPermissionsListener;
import zenghao.com.study.permissionBuilder.util.Logger;

/**
 * https://github.com/Flyjun-Android/XPermission2.0
 *此框架可以作为开发使用 解决是否能弹出权限框和多个权限申请问题
 * @author zenghao
 * @since 17/2/27 上午11:01
 */
public class XPermissionTestActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xpermission);
        //此种方式判断是否勾选了不再提示 false则勾选 true没有勾选
        //但是从来没有申请过直接判断也是返回false 所以不能直接用这个判断
        //使用时可以先申请权限 用户判断后再用此方式检查是否可以还能弹出权限申请框
        boolean isShould= ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA);
        XPermissions.init(this);


        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                XPermissions.requestPermissions().setRequestCode(203)
                        .setShouldShow(false).setPermissions(new String[]{
                        //Manifest.permission.READ_PHONE_STATE
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA}).setOnXPermissionsListener(new XPermissionsListener() {
                    @Override
                    public void onXPermissions(int requestCode, int resultCode) {
                        Logger.log("resultCode:"+resultCode);
                        if (resultCode == XPermissions.PERMISSION_SUCCESS){
                            //权限申请成功，可以继续往下走
                            Log.e("===XPermissions","PERMISSION_SUCCESS");
                        }else{
                            //权限申请失败，此时应该关闭界面或者退出程序
                            Log.e("===XPermissions","PERMISSION_FAIL");
                        }
                    }
                }).builder();

            }
        });
    }

    public void test(){}


    @TargetApi(23)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        XPermissions.handlerPermissionResult(requestCode,permissions,grantResults);
    }
}
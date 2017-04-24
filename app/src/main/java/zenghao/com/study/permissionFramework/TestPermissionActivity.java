package zenghao.com.study.permissionFramework;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import android.widget.TextView;
import java.util.List;

import zenghao.com.study.R;

/**
 * Created by zenghao on 16/7/29.
 * 参考资料
 * https://github.com/anthonycr/Grant/blob/master/sample/src/main/java/com/anthonycr/sample/MainActivity.java
 * https://github.com/k0shk0sh/PermissionHelper/blob/master/permission/src/main/java/com/fastaccess/permission/base/PermissionHelper.java
 *https://github.com/googlesamples/android-RuntimePermissions/blob/master/Application/src/main/java/com/example/android/system/runtimepermissions/RuntimePermissionsFragment.java
 *https://github.com/lovedise/PermissionGen/blob/master/permissiongen/src/main/java/kr/co/namee/permissiongen/PermissionGen.java
 * https://github.com/baiiu/BlogCode/blob/master/app/src/main/java/com/baiiu/blogcode/MPermission/permission/EasyPermission.java
 *https://github.com/tbruyelle/RxPermissions/blob/master/lib/src/main/java/com/tbruyelle/rxpermissions/RxPermissions.java
 * https://github.com/a1018875550/PermissionDispatcher
 * https://github.com/lypeer/FcPermissions
 * https://github.com/holidaycheck/Permissify
 *
 * 这个兼容国产手机问题参考
 * https://github.com/yanzhenjie/AndPermission
 * http://www.jianshu.com/p/cdcbd3038902?utm_source=gank.io&utm_medium=email
 */
public class TestPermissionActivity extends AppCompatActivity{

    private Button mCamera;
    private TextView mCameraStatus;
    private Button mCheck;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_permission);
        mCamera = ((Button) findViewById(R.id.btn_test1));
        mCameraStatus = ((TextView) findViewById(R.id.tv_test1_status));
        mCheck = ((Button) findViewById(R.id.btn_test2));
        requestCamera();
        checkMethond();
    }

    private void checkMethond(){
        mCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              boolean flag =   BreadTripPermissionsManager.getInstance().canShowPermissionDialog(TestPermissionActivity.this,
                        Manifest.permission.CAMERA);
                //第一次check false
                //申请弹出dialog后拒绝 check true
                //不再询问 check false
                //允许 check false
                Log.e("=====","===flag"+flag);
            }
        });
    }


    private void requestCamera(){
        mCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BreadTripPermissionsManager.
                        getInstance().
                        requestPermissionsIfNecessaryForResult(TestPermissionActivity.this,
                                new String[]{Manifest.permission.CAMERA}, new PermissionsResultAction() {
                                    @Override
                                    public void onGranted(List<String> mDenieds) {//最后一个申请才成功 执行
                                        Log.e("=====","===onGranted");

                                    }

                                    @Override
                                    public void onDenied(boolean isLast,String permission) {
                                        Log.e("=====","===onDenied"+permission);

                                    }
                                });
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        BreadTripPermissionsManager.getInstance().notifyPermissionsChange(permissions,grantResults);
    }
}

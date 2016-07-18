package zenghao.com.study.activity;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import zenghao.com.study.R;
import zenghao.com.study.permission.BaseAppCompatActivity;

/**
 * Created by zenghao on 16/3/31.
 */
public class PermissionFrameworkActivity extends BaseAppCompatActivity implements View.OnClickListener{

    private Button mQAll;
    private Button mQSingle;
    private Button mCheck;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame_permission);
        mQAll = (Button) findViewById(R.id.btn_request_all);
        mQAll.setOnClickListener(this);

        mQSingle = (Button) findViewById(R.id.btn_request_single);
        mQSingle.setOnClickListener(this);
        
        mCheck = (Button) findViewById(R.id.btn_check_permission);
        mCheck.setOnClickListener(this);

        //PermissionsManager.getInstance().hasPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE);
        //PermissionsManager.getInstance().hasAllPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE});

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_check_permission:
               int status =  PermissionsManager.getInstance().checkPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE);

                //没有请求过此权限 返回false
                //请求过此权限 拒绝了返回true  拒绝不再显示此dialog返回false  同意返回false
                if(PermissionsManager.getInstance().canShowPermissionDialog(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                    //还能显示权限对话框
                    Log.e("====","status11111111");

                }else{
                    //不再显示权限对话框
                    Log.e("====","status22222");
                }
                Log.e("====","status"+status);

                //PermissionsManager.getInstance().openSettingsScreen(this);
                break;
            case R.id.btn_request_all:
                //PermissionsResultAction 用于在低版本不需要申请权限时回调
                PermissionsManager.getInstance().requestAllManifestPermissionsIfNecessary(this, new PermissionsResultAction() {
                    @Override
                    public void onGranted() {
                        Log.e("===","onGrantedonGrantedonGranted");
                    }

                    @Override
                    public void onDenied(String permission) {
                        Log.e("===","onDeniedonDeniedonDenied");
                    }

                });
                break;
            case R.id.btn_request_single:

                PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        new PermissionsResultAction() {
                            @Override
                            public void onGranted() {
                                Log.e("===","onGrantedonGrantedonGranted");
                            }

                            @Override
                            public void onDenied(String permission) {
                                Log.e("===","onDeniedonDeniedonDenied");
                            }
                        });
                break;
            default:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionsManager.getInstance().notifyPermissionsChange(permissions, grantResults);
        //switch (requestCode) 可以根据requestCode 判断是否需要 notify  同时多个请求的在次处理业务 单个的notify
        //拒绝后不再显示dialog 每次申请就是直接拒绝
        //同时多个权限请求会在最后一次 处理后 再回调这里

        for (String s:permissions){
            Log.e("===","===ppp"+s);
        }

        for (int s:grantResults){
            Log.e("===","===ppp"+s);
        }



    }
}

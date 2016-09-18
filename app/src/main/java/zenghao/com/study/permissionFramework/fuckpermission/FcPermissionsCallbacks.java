package zenghao.com.study.permissionFramework.fuckpermission;

import android.support.v4.app.ActivityCompat;

import java.util.List;

public interface FcPermissionsCallbacks extends
        ActivityCompat.OnRequestPermissionsResultCallback {

    void onPermissionsGranted(int requestCode, List<String> perms);
    void onPermissionsDenied(int requestCode, List<String> perms);
}
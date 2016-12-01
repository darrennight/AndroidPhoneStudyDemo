package zenghao.com.study.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;

/**
 * 相机功能，拍照和相册（裁剪）
 */
public class CameraUtil {
    public static final int REQUEST_CODE_TAKE_IMAGE_FROM_CAMERA = 10023;//拍照获取照片
    public static final int REQUEST_CODE_TAKE_IMAGE_FROM_ALBUM = 10024;//从相册获取照片
    public static final int REQUEST_CODE_IMAGE_CROP = 10025;//图片裁剪
    private static final String TAG = "CameraUtil";
    private int outputX = 200;//裁剪输出宽度
    private int outputY = 200;//裁剪输出高度
    private int aspectX = 1;//裁剪输出比例
    private int aspectY = 1;//裁剪输出比例
    private boolean isReturnData = true;//是否返回裁剪的bitmap，如果为false，指定裁剪返回的Uri
    private Uri outImageUri;//图片输出的路径
    private int requestId;//请求的id
    private static CameraUtil instance;
    private static final String IMAGEFOLDERPATH = "pic";

    private CameraUtil() {
        if (outImageUri == null) {
            File fileDir = new File(IMAGEFOLDERPATH);
            if (!fileDir.exists()) fileDir.mkdirs();
            File file = new File(fileDir, ImageUtil.getPhotoFileName());
            outImageUri = Uri.fromFile(file);
        }
    }

    public static CameraUtil getInstance() {
        if (instance == null) {
            instance = new CameraUtil();
        }
        File fileDir = new File(IMAGEFOLDERPATH);
        if (!fileDir.exists()) fileDir.mkdirs();
        return instance;
    }

    public CameraUtil setParamsAll(int outputX, int outputY, int aspectX, int aspectY, boolean isReturnData, Uri uri, int requestId) {
        this.outputX = outputX;
        this.outputY = outputY;
        this.aspectX = aspectX;
        this.aspectY = aspectY;
        this.isReturnData = isReturnData;
        this.outImageUri = uri;
        this.requestId = requestId;
        return this;
    }

    public CameraUtil setOutput(int outputX, int outputY) {
        this.outputX = outputX;
        this.outputY = outputY;
        return this;
    }

    public CameraUtil setAspect(int aspectX, int aspectY) {
        this.aspectX = aspectX;
        this.aspectY = aspectY;
        return this;
    }

    public CameraUtil IsReturnData(boolean isReturnData) {
        this.isReturnData = isReturnData;
        return this;
    }

    public CameraUtil setOutImageUri(Uri uri) {
        this.outImageUri = uri;
        return this;
    }

    public CameraUtil setRequestId(int requestId) {
        this.requestId = requestId;
        return this;
    }

    /**
     * 从activity里拍照
     *
     * @param activity
     */
    public void takePhotoFromActivity(Activity activity) {
        takePhoto(activity);
    }

    public void takePhotoFromFragment(Fragment fragment) {
        takePhoto(fragment);
    }

    /**
     * 拍照
     *
     * @param obj
     */
    private void takePhoto(Object obj) {
        if (requestId == 0) {
            requestId = REQUEST_CODE_TAKE_IMAGE_FROM_CAMERA;
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (outImageUri != null) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outImageUri);
        }
        Intent chooser = Intent.createChooser(intent, "拍照");
        if (obj instanceof Activity) {
            ((Activity) obj).startActivityForResult(chooser, requestId);
        } else if (obj instanceof Fragment) {
            ((Fragment) obj).startActivityForResult(chooser, requestId);
        }
    }

    /**
     * 从相册选择照片
     *
     * @param activity
     */
    public void goAlbumViewActivity(Activity activity) {
        goAlbumView(activity);
    }

    /**
     * 从相册选择照片
     *
     * @param fragment
     */
    public void goAlbumViewFragment(Fragment fragment) {
        goAlbumView(fragment);
    }

    private void goAlbumView(Object obj) {
        if (requestId == 0) {
            requestId = REQUEST_CODE_TAKE_IMAGE_FROM_ALBUM;
        }
        Uri uri = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        } else {
            uri = MediaStore.Images.Media.INTERNAL_CONTENT_URI;
        }

        boolean flag = false;
        if (uri != null) {
            try {
                Intent intentImage = new Intent(Intent.ACTION_GET_CONTENT, uri);
                intentImage.addCategory(Intent.CATEGORY_OPENABLE);
                intentImage.setType("image/*");
                Intent chooser = Intent.createChooser(intentImage, "选择照片");
                if (obj instanceof Activity) {
                    ((Activity) obj).startActivityForResult(chooser, requestId);
                } else if (obj instanceof Fragment) {
                    ((Fragment) obj).startActivityForResult(chooser, requestId);
                }
            } catch (RuntimeException e) {
                flag = true;
            }
        }
        if (obj instanceof Activity && flag) {
            Toast.makeText((Activity) obj, "未找到相册", Toast.LENGTH_LONG).show();
        } else if (obj instanceof Fragment && flag) {
            Toast.makeText(((Fragment) obj).getActivity(), "未找到相册", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 获取裁剪intent
     *
     * @return
     */
    public Intent createCropIntent(Uri uri) {
        return createCropIntent(null, uri);
    }

    public Intent createCropIntent(Intent intent, Uri uri) {
        if (intent == null) {
            intent = new Intent();
        }
        if (uri != null) {
            intent.setDataAndType(uri, "image/*");
        }
        intent.setAction("com.android.camera.action.CROP");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", aspectX);
        intent.putExtra("aspectY", aspectY);
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        //如果图片很小，拉伸图片
        intent.putExtra("scale", true);
        intent.putExtra("scaleUpIfNeeded", true);
        intent.putExtra("noFaceDetection", true);
        if (!isReturnData) {
            intent.putExtra("return-data", false);
            intent.putExtra("output", outImageUri);
        } else {
            intent.putExtra("return-data", true);
        }
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        return intent;
    }

    /**
     * 从activity里裁剪图片
     *
     * @param activity
     */
    public void startCropImageFromActivity(Activity activity, Uri dataUri) {
        startCropImage(activity, dataUri);
    }

    /**
     * 从fragment里裁剪图片
     *
     * @param fragment
     */
    public void startCropImageFromFragment(Fragment fragment, Uri dataUri) {
        startCropImage(fragment, dataUri);
    }

    private void startCropImage(Object obj, Uri dataUri) {
        if (requestId == 0) {
            requestId = REQUEST_CODE_IMAGE_CROP;
        }
        Intent intent = createCropIntent(dataUri);
        if (obj instanceof Activity) {
            ((Activity) obj).startActivityForResult(intent, requestId);
        } else if (obj instanceof Fragment) {
            ((Fragment) obj).startActivityForResult(intent, requestId);
        }
    }

    /**
     * 获取图片路径（4.4以上不一样）
     *
     * @param context
     * @param uri
     * @return
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getSelectPicPath(final Context context, final Uri uri) {
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };
                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // Return the remote address
            if (isGooglePhotosUri(uri)) {
                return uri.getLastPathSegment();
            }
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        } else {
            return selectImage(context, uri);
        }
        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    public static String selectImage(Context context, Uri uri) {
//      Log.e(TAG, selectedImage.toString());
        if (uri != null) {
            String uriStr = uri.toString();
            String path = uriStr.substring(10, uriStr.length());
            if (path.startsWith("com.sec.android.gallery3d")) {
                Log.e(TAG, "It's auto backup pic path:" + uri.toString());
                return null;
            }
        }
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri, filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();
        return picturePath;
    }

}
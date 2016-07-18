package zenghao.com.study.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.webkit.WebView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

public class ScreenshotTask extends AsyncTask<String, Void, Boolean> {
    private Context context;
    private ProgressDialog dialog;
    private WebView webView;
    private int windowWidth;
    private float contentHeight;
    private String title;
    private String path;
    public static final String SUFFIX_PNG = ".png";
 
 
    public ScreenshotTask(Context context, WebView webView) {
        this.context = context;
        this.dialog = null;
        this.webView = webView;
        this.windowWidth = 0;
        this.contentHeight = 0f;
        this.title = null;
        this.path = null;
    } 
 
 
    @Override 
    protected void onPreExecute() { 
        dialog = new ProgressDialog(context);
        dialog.setCancelable(false);
        dialog.setMessage("1111");
        dialog.show();
 
 
        windowWidth = ViewUnit.getWindowWidth(context);
        contentHeight = webView.getContentHeight() * ViewUnit.getDensity(context);
        //title = webView.getTitle();
        title = "ceshiaaa";
    } 
 
 
    @Override 
    protected Boolean doInBackground(String... params) {
        try { 
            Bitmap bitmap = ViewUnit.capture(webView, windowWidth, contentHeight, false, Bitmap.Config.RGB_565);
            path = screenshot(context, bitmap, title+"hhhh");
        } catch (Exception e) {
            path = null;
        } 
        return path != null && !path.isEmpty();
    } 
 
 
    @Override 
    protected void onPostExecute(Boolean result) {
        dialog.hide();
        dialog.dismiss();
 
 
        if (result) {
        	Toast.makeText(context, "成功咯", Toast.LENGTH_SHORT).show();
        } else { 
        	Toast.makeText(context, "失败啦", Toast.LENGTH_SHORT).show();
        } 
    } 
    
    private  String screenshot(Context context, Bitmap bitmap, String name) {
    	long first = System.currentTimeMillis();
        if (bitmap == null) {
            return null;
        }

        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        
        if(!dir.exists()){
        	dir.mkdirs();
        }
        
        if (name == null || name.trim().isEmpty()) {
            name = String.valueOf(System.currentTimeMillis());
        }
        name = name.trim();

        int count = 0;
        File file = new File(dir, name + SUFFIX_PNG);
        while (file.exists()) {
            count++;
            file = new File(dir, name + "." + count + SUFFIX_PNG);
        }

        
        try {
            FileOutputStream stream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
            stream.flush();
            stream.close();
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));
            System.out.println("======2222timetiemtiem"+(System.currentTimeMillis()-first));  
            return file.getAbsolutePath();
        } catch (Exception e) {
        	e.printStackTrace();
            return null;
        }
        
      
    }



} 

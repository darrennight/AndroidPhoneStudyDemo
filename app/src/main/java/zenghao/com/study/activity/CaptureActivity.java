package zenghao.com.study.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Picture;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import zenghao.com.study.R;
import zenghao.com.study.util.SimpleAsyncTask;
import zenghao.com.study.view.ScreenshotTask;

/**
 * Created by zenghao on 16/3/12.
 */
public class CaptureActivity extends AppCompatActivity {

    private Button mScreen;
    private WebView mWebView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //这个 必须要在setContent之前写  当app的targetsedversion大于19时 使用
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            WebView.enableSlowWholeDocumentDraw();
        }
//        if (Build.VERSION.SDK_INT >= 21) {
//            mWebView.enableSlowWholeDocumentDraw ();
//        }
        setContentView(R.layout.activity_capture);
        mScreen = (Button) this.findViewById(R.id.btn_screen);
        mWebView = (WebView) this.findViewById(R.id.wv_webview);
        initWebView();
        initWebSettings();
        mWebView.loadUrl("http://web.breadtrip.com/btrip/spot/2387718047/");

        mScreen.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ScreenshotTask task = new ScreenshotTask(CaptureActivity.this, mWebView);
                task.execute("");
            }
        });
    }

    private  void initWebView() {
        mWebView.setAlwaysDrawnWithCacheEnabled(true);
        mWebView.setAnimationCacheEnabled(true);
        mWebView.setDrawingCacheBackgroundColor(0x00000000);
        mWebView.setDrawingCacheEnabled(true);
        mWebView.setWillNotCacheDrawing(false);
        mWebView.setSaveEnabled(true);

//        mWebView.setBackground(null);
//        mWebView.getRootView().setBackground(null);
        //mWebView.setBackgroundColor(android.R.color.white);

        mWebView.setFocusable(true);
        mWebView.setFocusableInTouchMode(true);
        mWebView.setHorizontalScrollBarEnabled(false);
        mWebView.setVerticalScrollBarEnabled(false);
        mWebView.setScrollbarFadingEnabled(true);

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });
        mWebView.setWebChromeClient(new WebChromeClient());

    }

    private synchronized void initWebSettings() {
        WebSettings webSettings = mWebView.getSettings();
        //userAgentOriginal = webSettings.getUserAgentString();

        webSettings.setAllowContentAccess(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
//        webSettings.setAllowFileAccessFromFileURLs(true);
//        webSettings.setAllowUniversalAccessFromFileURLs(true);

        webSettings.setAppCacheEnabled(true);
        //webSettings.setAppCachePath(getCacheDir().toString());
        //webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setDatabaseEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setGeolocationDatabasePath(getFilesDir().toString());

        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);


        webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
        webSettings.setDefaultTextEncodingName("utf-8");
        //webSettings.setRenderPriority(RenderPriority.HIGH);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setSaveFormData(true);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        //mWebView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);
        String cacheDir = getDir("webCaCheDatabase", Context.MODE_PRIVATE).getPath();
        webSettings.setAppCacheMaxSize(1024 * 1024 * 20);
        webSettings.setAppCachePath(cacheDir);


        //webSettings.setDefaultTextEncodingName(BrowserUnit.URL_ENCODING);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webSettings.setLoadsImagesAutomatically(true);
        } else {
            webSettings.setLoadsImagesAutomatically(false);
        }
    }



    private String mBaseCache = Environment.getExternalStorageDirectory() +"/longpic" + "/";
    /**
     * webView截取长图
     *方式二
     */
    public void webViewScreenshots() {
        // 三星手机需要要用此方法 否则长图生成失败
        mWebView.setFocusable(true);
        mWebView.scrollTo(0, 0);
        mWebView.buildDrawingCache();
        final Picture picture = mWebView.capturePicture();

        new SimpleAsyncTask() {

            @Override
            public void preExcute() {


            }

            @Override
            public void postExcute() {


            }

            @Override
            public void doInBackground() {
                Bitmap bitmap = null;
                try {
                    if(picture.getWidth()>0&&picture.getHeight()>0){
                        bitmap = Bitmap.createBitmap(picture.getWidth(), picture.getHeight(), Bitmap.Config.RGB_565);
                        Canvas canvas = new Canvas(bitmap);
                        picture.draw(canvas);
                    }

                } catch (OutOfMemoryError e) {
                    e.printStackTrace();
                    if (bitmap != null) {
                        bitmap.recycle();
                        bitmap = null;
                    }
                }

                FileOutputStream fos = null;
                File file = null;
                File fileImage = null;
                if (bitmap != null) {
                    ByteArrayOutputStream mByteArrayOS = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 80, mByteArrayOS);

                    file = new File(mBaseCache);
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                    fileImage = new File(mBaseCache, createPictureName());
                    try {
                        fos = new FileOutputStream(fileImage);
                        byte[] imgData = mByteArrayOS.toByteArray();
                        if (imgData.length <= 0) {
                            fileImage.delete();
                        } else {
                            fos.write(mByteArrayOS.toByteArray());
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        if (fileImage != null) {
                            fileImage.delete();
                        }
                        e.printStackTrace();
                    } finally {
                        if (fos != null) {
                            try {
                                fos.flush();
                                fos.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        if (bitmap != null) {
                            bitmap.recycle();
                            bitmap = null;
                        }
                    }

                }

            }
        }.excute();

    }



    /**
     * webView 截图方式三
     * @param webView
     * @return
     */
    public static Bitmap screenshot(WebView webView) {
        try {
            float scale = webView.getScale();
            int height = (int) (webView.getContentHeight() * scale + 0.5);
            Bitmap bitmap = Bitmap.createBitmap(webView.getWidth(), height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            webView.draw(canvas);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 图片名
     *
     * @return
     */
    private String createPictureName() {
        return System.currentTimeMillis() + "image.jpg";
    }
}

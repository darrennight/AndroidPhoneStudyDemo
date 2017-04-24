package zenghao.com.study.retrofit;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import zenghao.com.study.R;
import zenghao.com.study.retrofit.download.DownloadProgressHandler;
import zenghao.com.study.retrofit.download.ProgressHelper;
import zenghao.com.study.retrofit.updateApk.UpdateManager;

/**
 * retrofit使用连接参考学习
 *http://www.jianshu.com/p/16994e49e2f6
 * @author zenghao
 * @since 2017/4/24 上午10:11
 */
public class TestRetrofitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
        findViewById(R.id.btn_check_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                      TestApi api = RequestBaseService.createService(TestApi.class);
                        Call<Object> call = api.getcheckUpdate();
                        call.enqueue(new Callback<Object>() {
                            @Override
                            public void onResponse(Call<Object> call, Response<Object> response) {
                                Log.e("======","okokokokok");
                            }

                            @Override
                            public void onFailure(Call<Object> call, Throwable t) {
                                Log.e("======","onFailureonFailure");
                            }
                        });
            }
        });

        findViewById(R.id.btn_down_load).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retrofitDownload();
            }
        });

        findViewById(R.id.btn_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new UpdateManager(TestRetrofitActivity.this).checkUpdate(false);
            }
        });
    }

    private void retrofitDownload(){
        //监听下载进度
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setProgressNumberFormat("%1d KB/%2d KB");
        dialog.setTitle("下载");
        dialog.setMessage("正在下载，请稍后...");
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setCancelable(false);
        dialog.show();

        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://msoftdl.360.cn");
        OkHttpClient.Builder builder = ProgressHelper.addProgress(null);
        TestApi retrofit = retrofitBuilder
                .client(builder.build())
                .build().create(TestApi.class);

        ProgressHelper.setProgressHandler(new DownloadProgressHandler() {
            @Override
            protected void onProgress(long bytesRead, long contentLength, boolean done) {
                Log.e("是否在主线程中运行", String.valueOf(Looper.getMainLooper() == Looper.myLooper()));
                Log.e("onProgress",String.format("%d%% done\n",(100 * bytesRead) / contentLength));
                Log.e("done","--->" + String.valueOf(done));
                dialog.setMax((int) (contentLength/1024));
                dialog.setProgress((int) (bytesRead/1024));

                if(done){
                    dialog.dismiss();
                }
            }
        });

        Call<ResponseBody> call = retrofit.retrofitDownload();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    InputStream is = response.body().byteStream();
                    File file = new File(Environment.getExternalStorageDirectory(), "12345.apk");
                    FileOutputStream fos = new FileOutputStream(file);
                    BufferedInputStream bis = new BufferedInputStream(is);
                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len = bis.read(buffer)) != -1) {
                        fos.write(buffer, 0, len);
                        fos.flush();
                    }
                    fos.close();
                    bis.close();
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }
}


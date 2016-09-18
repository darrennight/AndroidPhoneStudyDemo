package zenghao.com.study.retrofit;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zenghao on 16/8/5.
 */
public class RequestBaseService {

    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(Constant.APIHOST)
            .client(OkHttpClientManager.getInstance().getOkHttpClient())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            //设置 Json 转换器
            .addConverterFactory(GsonConverterFactory.create());


    public static <T> T createService(Class<T> serviceClass) {
        Retrofit retrofit = builder.build();
        return retrofit.create(serviceClass);
    }
}

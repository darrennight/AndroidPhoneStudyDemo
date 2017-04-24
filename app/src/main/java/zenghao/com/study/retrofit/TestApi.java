package zenghao.com.study.retrofit;

import java.util.Map;

import java.util.Objects;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by zenghao on 16/8/9.
 *
 * baseUrl http://api.breadtrip.com/
 */
public interface TestApi {

    @GET("repos/{owner}/{repo}/contributors")
    Call<ResponseBody> contributorsBySimpleGetCall(@Path("owner") String owner, @Path("repo") String repo);


    //Path
    // http://api.breadtrip.com/2016/01/15/retrofit

    @GET("2016/01/15/{retrofit}")
    Call<ResponseBody> getData(@Path("retrofit") String retrofit);


//    Query
//    类似这样链接：http://api.breadtrip.com/v1?ip=202.202.33.33&name=WuXiaolong

    @GET("v1")
    Call<ResponseBody> getData(@Query("ip") String ip, @Query("name") String name);


//    Field
//    表单提交，如登录

    @FormUrlEncoded
    @POST("v1/login")
    Call<ResponseBody> userLogin(@Field("phone") String phone, @Field("password") String password);



//    传json格式
//    如果参数是json格式，如：
//        {
//            "apiInfo": {
//            "apiName": "WuXiaolong",
//                    "apiKey": "666"
//        }
//        }

//    public class ApiInfo {
//        private ApiInfoBean apiInfo;
//
//        public ApiInfoBean getApiInfo() {
//            return apiInfo;
//        }
//
//        public void setApiInfo(ApiInfoBean apiInfo) {
//            this.apiInfo = apiInfo;
//        }
//
//        public class ApiInfoBean {
//            private String apiName;
//            private String apiKey;
//            //省略get和set方法
//        }
//    }

//    @POST("client/shipper/getCarType")
//    Call<ResponseBody> getData(@Body ApiInfo apiInfo);


//    代码调用
//    ```java
//    ApiInfo apiInfo = new ApiInfo();
//    ApiInfo.ApiInfoBean apiInfoBean = apiInfo.new ApiInfoBean();
//    apiInfoBean.setApiKey("666");
//    apiInfoBean.setApiName("WuXiaolong");
//    apiInfo.setApiInfo(apiInfoBean);
//    //调接口
//    getData(apiInfo);

//    传数组
    @GET("v1/enterprise/find")
    Call<ResponseBody> getData(@Query("id") String id, @Query("linked[]") String... linked);


//    传文件-单个
        @Multipart
        @POST("v1/create")
        Call<ResponseBody> create(@Part("pictureName") RequestBody pictureName, @Part MultipartBody.Part picture);

//    传文件-多个

    @Multipart
    @POST("v1/create")
    Call<ResponseBody> create(@Part("pictureName") RequestBody pictureName,   @PartMap Map<String, RequestBody> params);

//    equestBody pictureNameBody = RequestBody.create(MediaType.parse(AppConstants.CONTENT_TYPE_FILE), "pictureName");
//    File picture= new File(path);
//    RequestBody requestFile = RequestBody.create(MediaType.parse(AppConstants.CONTENT_TYPE_FILE), picture);
//    Map<String, RequestBody> params = new HashMap<>();
//    params.put("picture\"; filename=\"" + picture.getName() + "", requestFile);
//    //调接口
//    create(pictureNameBody, params);




    @GET("check_update/")
    Call<Object> getcheckUpdate();


    @GET("/mobilesafe/shouji360/360safesis/360MobileSafe_6.2.3.1060.apk")
    Call<ResponseBody> retrofitDownload();
}

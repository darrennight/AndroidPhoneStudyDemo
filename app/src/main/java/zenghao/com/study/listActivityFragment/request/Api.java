package zenghao.com.study.listActivityFragment.request;

import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;
import zenghao.com.study.listActivityFragment.model.BaseModel;
import zenghao.com.study.listActivityFragment.model.Benefit;

public interface Api {
    @GET("api/data/福利/{pageCount}/{pageIndex}")
    Call<BaseModel<ArrayList<Benefit>>> defaultBenefits(
            @Path("pageCount") int pageCount,
            @Path("pageIndex") int pageIndex
    ); 
 
    @GET("api/data/福利/{pageCount}/{pageIndex}")
    Observable<BaseModel<ArrayList<Benefit>>> rxBenefits(
            @Path("pageCount") int pageCount,
            @Path("pageIndex") int pageIndex
    ); 
} 
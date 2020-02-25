package cathay.hospital.example.model;

import java.util.HashMap;

import cathay.hospital.example.model.bean.LoginData;
import cathay.hospital.example.model.bean.ReturnData;
import io.reactivex.Single;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

public interface ApiModule {
    @GET("mlogin.jsp")
    Single<LoginData> getLoginData(@QueryMap HashMap<String, String> params);

    /**
     *  post 範例
     */
    @FormUrlEncoded
    @POST("patrol/yourApiPath.jsp")
    Single<ReturnData> getApiPathData(@FieldMap HashMap<String, String> params);

}

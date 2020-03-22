package cathay.hospital.hmfmsmobile.model;

import java.util.HashMap;

import cathay.hospital.hmfmsmobile.model.bean.LoginData;
import io.reactivex.Single;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiModule {
//    @GET("member/login")
//    Single<LoginData> getLoginData(@QueryMap HashMap<String, String> params);

    /**
     *  post 範例
     */
    @FormUrlEncoded
    @POST("member/login")
    Single<LoginData> getLoginData(@FieldMap HashMap<String, String> params);

}

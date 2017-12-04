package api;

import data.CsrfToken;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Krzysztof on 04.12.2017.
 */

public interface Authentication {

    @POST("/auth/")
    @FormUrlEncoded
    Call<CsrfToken> perform(@Field("username") String username, @Field("password") String password);
}

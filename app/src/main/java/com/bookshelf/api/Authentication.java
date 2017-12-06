package com.bookshelf.api;

import com.bookshelf.data.CsrfToken;
import com.bookshelf.data.Role;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Krzysztof on 04.12.2017.
 */

public interface Authentication {

    @POST("/auth/")
    @FormUrlEncoded
    Call<CsrfToken> perform(@Field("username") String username, @Field("password") String password);

    @GET("/api/roles/{id}")
    Call<Role> check(@Path("id") String id);

}

package com.bookshelf.api;

import com.bookshelf.data.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserService {

    @GET("/api/users/{id}")
    Call<User> getUserByID(@Path("id") Integer id);

    @POST("/api/users")
    Call<String> addUser(@Body User user);
}

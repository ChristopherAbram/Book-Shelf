package com.bookshelf.api;

import com.bookshelf.data.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Krzysztof on 11.12.2017.
 */

public interface UserService {

    @POST("/api/users")
    Call<String> addUser(@Body User user);
}

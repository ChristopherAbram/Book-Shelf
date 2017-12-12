package com.bookshelf.api;

import com.bookshelf.data.CurrentUser;
import com.bookshelf.data.Item;
import com.bookshelf.data.User;
import com.bookshelf.data.collection.Categories;
import com.bookshelf.data.collection.Users;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserService {

    @GET("/api/users/{id}")
    Call<User> getUserByID(@Path("id") Integer id);

    @GET("/api/?command=whoami")
    Call<CurrentUser> getCurrentUserID();

    @POST("/api/users")
    Call<String> addUser(@Body User user);
}

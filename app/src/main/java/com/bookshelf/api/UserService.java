package com.bookshelf.api;

import com.bookshelf.data.CurrentUser;
import com.bookshelf.data.FullUser;
import com.bookshelf.data.User;
import com.bookshelf.data.collection.FullUsers;

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

    @GET("/api/fullusers?transform=1")
    Call<FullUsers> getFullUsersByID(@Query("filter") String filter);

}

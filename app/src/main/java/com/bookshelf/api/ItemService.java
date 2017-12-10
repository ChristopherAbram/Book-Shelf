package com.bookshelf.api;

import com.bookshelf.data.Category;
import com.bookshelf.data.User;
import com.bookshelf.data.collection.Items;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Krzysztof on 07.12.2017.
 */

public interface ItemService {

    @GET("/api/items?transform=1")
    Call<Items> getItems();

    @GET("/api/items?transform=1")
    Call<Items> getItems(@Query("page") String paginator);

    @GET("/api/items?transform=1")
    Call<Items> getItemByFilter(@Query("filter") String category);

    @GET("/api/users/{id}")
    Call<User> getUserByID(@Path("id") Integer id);

    @GET("/api/categories/{id}")
    Call<Category> getCategoriesByID(@Path("id") Integer id);

}

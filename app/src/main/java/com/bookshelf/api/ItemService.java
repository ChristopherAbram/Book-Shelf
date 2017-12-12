package com.bookshelf.api;

import com.bookshelf.data.Category;
import com.bookshelf.data.Item;
import com.bookshelf.data.User;
import com.bookshelf.data.collection.Discounts;
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

    @GET("/api/items/{id}")
    Call<Item> getItemByID(@Path("id") Integer id);

    @GET("/api/items?transform=1")
    Call<Items> getItemByFilter(@Query("filter") String filter);

    @GET("/api/users/{id}")
    Call<User> getUserByID(@Path("id") Integer id);

    @GET("/api/categories/{id}")
    Call<Category> getCategoriesByID(@Path("id") Integer id);

    @GET("/api/discounts?transform=1")
    Call<Discounts> getDiscountsByUserID(@Query("filter[]") String filter,@Query("filter[]") String filter2,@Query("satisfy") String satisfy);

}

package com.bookshelf.api;

import com.bookshelf.data.Cart;
import com.bookshelf.data.Category;
import com.bookshelf.data.User;
import com.bookshelf.data.collection.Carts;
import com.bookshelf.data.collection.Items;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CartService {

    @GET("/api/carts?transform=1")
    Call<Carts> getCartsByFilter(@Query("filter") String category);

}

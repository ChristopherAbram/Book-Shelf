package com.bookshelf.api;

import com.bookshelf.data.collection.Items;
import com.bookshelf.data.collection.Orders;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by r9_bl on December 12 2017.
 */

public interface OrderService {

    @GET("/api/orders?transform=1")
    Call<Orders> getOrdersByFilter(@Query("filter") String filter);

}

package com.bookshelf.api;

import com.bookshelf.data.collection.Items;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Krzysztof on 07.12.2017.
 */

public interface ItemService {

    @GET("/api/items?transform=1")
    Call<Items> getItems(@Query("page") String paginator);

    @GET("/api/items?transform=1")
    Call<Items> getItemsSortedByColumn(@Query("order") String columnName, @Query("page") String paginator);

}

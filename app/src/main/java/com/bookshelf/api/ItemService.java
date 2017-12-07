package com.bookshelf.api;

import com.bookshelf.data.Item;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Krzysztof on 07.12.2017.
 */

public interface ItemService {

    @GET("/api/items?transform=1")
    Call<List<Item>> getItems(@Query("page") String paginator);

}

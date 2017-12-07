package com.bookshelf.api;

import retrofit2.http.GET;

/**
 * Created by Krzysztof on 07.12.2017.
 */

public interface ItemService {

    @GET("/api/items?page={pointer},{count}")
    Call<List<>>

}

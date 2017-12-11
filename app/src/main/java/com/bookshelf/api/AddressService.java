package com.bookshelf.api;

import com.bookshelf.data.Address;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Krzysztof on 11.12.2017.
 */

public interface AddressService {

    @POST("/api/addresses")
    Call<String> addAddress(@Body Address address);
}

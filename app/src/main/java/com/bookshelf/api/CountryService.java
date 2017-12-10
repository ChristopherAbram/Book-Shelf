package com.bookshelf.api;

import com.bookshelf.data.collection.Countries;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Krzysztof on 10.12.2017.
 */

public interface CountryService {

    @GET("/api/countries?transform=1")
    Call<Countries> getAllCountries();
}

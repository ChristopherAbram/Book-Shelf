package com.bookshelf.api;

import com.bookshelf.data.Item;
import com.bookshelf.data.collection.Categories;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CategoryService {

    @GET("/api/categories?transform=1&filter=id,gt,1&?order=name,desc")
    Call<Categories> getCategories();

}

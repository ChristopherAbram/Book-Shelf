package com.bookshelf.api;

import com.bookshelf.data.Role;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Krzysztof on 06.12.2017.
 */

public interface RoleService {

    @GET("/api/roles/{id}")
    Call<Role> getRoleById(@Path("id") Integer id);

}

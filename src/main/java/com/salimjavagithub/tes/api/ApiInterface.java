package com.salimjavagithub.tes.api;

import com.salimjavagithub.tes.model.Users;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("search/users")
    Call <Users> getUsers(@Query("q") String keyword, @Query("per_page") String perPage, @Query("page") Integer page);

}

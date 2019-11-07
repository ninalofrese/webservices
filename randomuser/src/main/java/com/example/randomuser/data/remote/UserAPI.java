package com.example.randomuser.data.remote;

import com.example.randomuser.model.User;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UserAPI {
    @GET("api/")
    Observable<User> getUser(@Query("results") int numero);
}

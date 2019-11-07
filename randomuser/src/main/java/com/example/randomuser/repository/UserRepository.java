package com.example.randomuser.repository;

import com.example.randomuser.model.User;

import io.reactivex.Observable;

import static com.example.randomuser.data.remote.RetrofitService.getApiService;

public class UserRepository {
    public Observable<User> getSingleUser(int numero) {
        return getApiService().getUser(numero);
    }
}

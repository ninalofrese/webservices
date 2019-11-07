package com.example.jsonlocalfilmes.repository;

import android.content.Context;
import android.content.res.AssetManager;

import com.example.jsonlocalfilmes.model.Result;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import io.reactivex.Observable;

public class LocalFilmesRepository {

    public Observable<Result> obterListaFilmes(Context context) {
        try {
            AssetManager manager = context.getAssets();

            InputStream inputStream = manager.open("filmes.json");

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            Gson gson = new Gson();
            Result result = gson.fromJson(reader, Result.class);

            return Observable.just(result);
        } catch (Exception ex) {
            ex.printStackTrace();
            return Observable.error(ex.getCause());
        }
    }
}

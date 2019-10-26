package com.example.json.repository;

import android.content.Context;
import android.content.res.AssetManager;

import com.example.json.model.NoticiaResposta;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import io.reactivex.Observable;

public class NoticiaRepository {

    public Observable<NoticiaResposta> obterListaNoticias(Context context) {

        try {
            //observa os assets no contexto da aplicação
            AssetManager manager = context.getAssets();

            //Abre o arquivo
            InputStream inputStream = manager.open("noticias.json");

            //Lê o arquivo e converte os dados para array de bytes
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            //converte os bytes em objeto Java
            Gson gson = new Gson();
            NoticiaResposta resposta = gson.fromJson(reader, NoticiaResposta.class);

            //retorna um único valor
            return Observable.just(resposta);

        } catch (Exception ex) {
            ex.printStackTrace();
            return Observable.error(ex.getCause());
        }

    }

}

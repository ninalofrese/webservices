package com.example.albumsapi.data.remote;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {
    // Url base da api
    private static final String BASE_URL = "https://theaudiodb.com/api/v1/json/";
    private static final String API_KEY = "195003/";

    // Instância que criaremos do retrofit
    private static Retrofit retrofit;

    private static Retrofit getRetrofit() {

        if (retrofit == null) {
            // configurações da conexão, onde podemos setar o timeout e outras opções para as requisições

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.readTimeout(30, TimeUnit.SECONDS);
            httpClient.connectTimeout(30, TimeUnit.SECONDS);
            httpClient.writeTimeout(30, TimeUnit.SECONDS);

            // Inicializamos o retrofit
            retrofit = new Retrofit.Builder()
                    // Url obrigatória
                    .baseUrl(BASE_URL + API_KEY)
                    // adapter para o uso do RXJava
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    // Conversor que transforma-rá o json recebido em classes Java
                    .addConverterFactory(GsonConverterFactory.create())
                    // Adicionamos as configurações de requisição configurado acima
                    .client(httpClient.build())
                    // Construímos o objeto retrofit
                    .build();
        }
        return retrofit;
    }

    // Retornamos a instância da API criada com o retrofit
    public static AudioAPI getApiService() {
        return getRetrofit().create(AudioAPI.class);
    }
}

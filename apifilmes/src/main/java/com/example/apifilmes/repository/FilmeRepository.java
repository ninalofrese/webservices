package com.example.apifilmes.repository;

import com.example.apifilmes.model.FilmeResult;

import io.reactivex.Observable;

import static com.example.apifilmes.data.remote.RetrofitService.getApiService;

public class FilmeRepository {
    //Conversa com os dados

    //retorna a instância da interface com o retrofit e o método que traz os filmes (da interface)
    public Observable<FilmeResult> getFilmes(String apiKey, String idioma, int pagina) {
        return getApiService().getAllFilmes(apiKey, idioma, pagina);
    }
}

package com.example.padraoarquiteturavalormvvm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

//extends AndroidViewModel (que oferece contexto para quando precisa) - geralmente usado
//extends ViewModel (não oferece contexto) - não é muito usado
public class MainViewModel extends AndroidViewModel {
    private MutableLiveData<Integer> valor = new MutableLiveData<>();

    //oferece o contexto
    public MainViewModel(@NonNull Application application) {
        super(application);
    }


    //método para ser utilizado na camada de view, para visualização
    //LiveData é leitura
    public LiveData<Integer> retornaValor(){
        return valor;
    }

    //altera o mutablelivedata
    //mutableLiveData é leitura e escrita
    public void somaValor(Integer valor){
        Integer soma = valor + 1;
        this.valor.setValue(soma);
    }

}

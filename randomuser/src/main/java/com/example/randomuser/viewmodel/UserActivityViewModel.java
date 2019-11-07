package com.example.randomuser.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.randomuser.model.User;
import com.example.randomuser.repository.UserRepository;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class UserActivityViewModel extends AndroidViewModel {
    private MutableLiveData<User> usuario = new MutableLiveData<>();
    private CompositeDisposable disposable = new CompositeDisposable();
    private UserRepository repository = new UserRepository();
    private MediatorLiveData<User> mediatorUsuario = new MediatorLiveData<>();

    public UserActivityViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<User> getUsuario() {
        return this.usuario;
    }

    public void obterUsuario() {
        disposable.add(
                repository.getSingleUser(1)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(user -> {
                            usuario.setValue(user);
                        }, throwable -> {
                            Log.i("USUARIO", "obterUsuario " + throwable.getMessage());
                        })
        );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}

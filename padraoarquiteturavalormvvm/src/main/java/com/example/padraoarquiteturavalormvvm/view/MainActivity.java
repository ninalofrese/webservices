package com.example.padraoarquiteturavalormvvm.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.padraoarquiteturavalormvvm.R;
import com.example.padraoarquiteturavalormvvm.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity {
    private TextView displayValor;
    private Button buttonValor;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        displayValor = findViewById(R.id.textview);
        buttonValor = findViewById(R.id.button_valor);

        //Inicializando o ViewModel
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        buttonValor.setOnClickListener(view -> {
            viewModel.somaValor(5);

            //vai pegar os valores por meio da observação do Livedata
            //o observe precisa de um owner (a view que vai retornar o valor) e vai exibir
            viewModel.retornaValor().observe(this, integer -> {
                displayValor.setText(integer.toString());
            });

        });
    }
}

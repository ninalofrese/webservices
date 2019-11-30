package com.example.mediaescolar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText bimestre1;
    private EditText bimestre2;
    private EditText bimestre3;
    private EditText bimestre4;
    private Button calcularButton;
    private TextView resultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    private void initViews() {
        bimestre1 = findViewById(R.id.bimestre1);
        bimestre2 = findViewById(R.id.bimestre2);
        bimestre3 = findViewById(R.id.bimestre3);
        bimestre4 = findViewById(R.id.bimestre4);
        calcularButton = findViewById(R.id.calcular);
        resultado = findViewById(R.id.resultado);


        calcularButton.setOnClickListener(view -> {

            Double nota1 = Double.parseDouble(bimestre1.getEditableText().toString());
            Double nota2 = Double.parseDouble(bimestre2.getEditableText().toString());
            Double nota3 = Double.parseDouble(bimestre3.getEditableText().toString());
            Double nota4 = Double.parseDouble(bimestre4.getEditableText().toString());

            Double media = calcularMedia(nota1, nota2, nota3, nota4);
            resultado.setText(String.format("Nota final: %s", media));
        });
    }

    public Double calcularMedia(Double nota1, Double nota2, Double nota3, Double nota4) {
        Double media = (nota1 + nota2 + nota3 + nota4) / 4;

        return media;
    }
}

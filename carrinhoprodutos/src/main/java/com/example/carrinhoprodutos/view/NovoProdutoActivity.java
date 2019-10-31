package com.example.carrinhoprodutos.view;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.carrinhoprodutos.R;
import com.example.carrinhoprodutos.model.Produto;
import com.example.carrinhoprodutos.viewmodel.NovoProdutoActivityViewModel;
import com.google.android.material.textfield.TextInputLayout;

import static com.example.carrinhoprodutos.view.ProdutosActivity.PRODUTO;

public class NovoProdutoActivity extends AppCompatActivity {
    private TextInputLayout inputNome;
    private TextInputLayout inputPreco;
    private Button buttonCancel;
    private Button buttonOK;

    private NovoProdutoActivityViewModel viewModel;
    private Produto produto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_produto);

        initViews();

        inputNome.requestFocus();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        if (getIntent() != null && getIntent().getExtras() != null) {
            produto = getIntent().getExtras().getParcelable(PRODUTO);
            //long id = getIntent().getExtras().getLong(PROD_ID);
            //produto = viewModel.buscarPorId(id);

            if (produto != null) {

                inputNome.getEditText().setText(produto.getNome());
                inputPreco.getEditText().setText(produto.getPreco().toString());
            }

            setTitle("Editar Produto");

        } else {
            setTitle("Novo Produto");
        }

        buttonOK.setOnClickListener(view -> {
            String nomeProduto = inputNome.getEditText().getText().toString();
            String precoProduto = inputPreco.getEditText().getText().toString().trim();

            if (!nomeProduto.isEmpty() && !precoProduto.isEmpty()) {
                inputNome.setErrorEnabled(false);
                inputPreco.setErrorEnabled(false);

                if (produto != null) {
                    Produto produtoExistente = produto;

                    produtoExistente.setNome(nomeProduto);
                    produtoExistente.setPreco(Double.parseDouble(precoProduto));

                    viewModel.atualizaProdutoBanco(produtoExistente);
                } else {
                    Produto novoProduto = new Produto(nomeProduto, Double.parseDouble(precoProduto));

                    viewModel.insereProdutoBancoDeDados(novoProduto);

                }

                finish();

            } else {
                inputNome.setError("Insira o nome do produto");
                inputPreco.setError("Insira o preço do produto");
            }
        });

        buttonCancel.setOnClickListener(view -> {
            finish();
        });
    }

    public void initViews() {
        inputNome = findViewById(R.id.input_produto_nome);
        inputPreco = findViewById(R.id.input_produto_preco);
        buttonOK = findViewById(R.id.button_produto_ok);
        buttonCancel = findViewById(R.id.button_produto_cancel);
        viewModel = ViewModelProviders.of(this).get(NovoProdutoActivityViewModel.class);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}

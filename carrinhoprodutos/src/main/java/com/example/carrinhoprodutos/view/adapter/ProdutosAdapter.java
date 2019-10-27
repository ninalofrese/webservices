package com.example.carrinhoprodutos.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carrinhoprodutos.R;
import com.example.carrinhoprodutos.model.Produto;

import java.util.List;

public class ProdutosAdapter extends RecyclerView.Adapter<ProdutosAdapter.ViewHolder> {
    private List<Produto> listaProdutos;

    public ProdutosAdapter(List<Produto> listaProdutos) {
        this.listaProdutos = listaProdutos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_produtos, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Produto produto = listaProdutos.get(position);
        holder.onBind(produto);

    }

    @Override
    public int getItemCount() {
        return listaProdutos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CheckBox checkBox;
        private TextView nomeProduto;
        private TextView precoProduto;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            checkBox = itemView.findViewById(R.id.checkBox);
            nomeProduto = itemView.findViewById(R.id.textView_produto_nome);
            precoProduto = itemView.findViewById(R.id.textView_produto_preco);
        }


        public void onBind(Produto produto) {
            nomeProduto.setText(produto.getNome());
            precoProduto.setText(produto.getPreco().toString());
        }
    }
}

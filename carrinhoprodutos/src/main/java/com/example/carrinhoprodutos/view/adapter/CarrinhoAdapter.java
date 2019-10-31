package com.example.carrinhoprodutos.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carrinhoprodutos.R;
import com.example.carrinhoprodutos.model.Produto;
import com.example.carrinhoprodutos.view.interfaces.RemoveFromCart;

import java.util.List;
import java.util.Locale;

public class CarrinhoAdapter extends RecyclerView.Adapter<CarrinhoAdapter.ViewHolder> {
    private List<Produto> produtosCarrinho;
    private RemoveFromCart removeListener;

    public CarrinhoAdapter(List<Produto> produtosCarrinho, RemoveFromCart removeListener) {
        this.produtosCarrinho = produtosCarrinho;
        this.removeListener = removeListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_carrinho, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Produto produto = produtosCarrinho.get(position);
        holder.onBind(produto);
    }

    @Override
    public int getItemCount() {
        return produtosCarrinho.size();
    }

    public void atualizaLista(List<Produto> produtos) {
        //produtosCarrinho.clear();
        produtosCarrinho = produtos;
        notifyDataSetChanged();
    }

    public void removerItem(int position) {
        produtosCarrinho.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, produtosCarrinho.size());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nomeProduto;
        private TextView valorProduto;
        private ImageButton removeCart;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nomeProduto = itemView.findViewById(R.id.textView_carrinho_nome);
            valorProduto = itemView.findViewById(R.id.textView_carrinho_preco);
            removeCart = itemView.findViewById(R.id.button_carrinho_remove);
        }

        public void onBind(Produto produto) {
            nomeProduto.setText(produto.getNome());
            valorProduto.setText(String.format(Locale.GERMAN, "R$ %.2f", produto.getPreco()));

            removeCart.setOnClickListener(view -> {
                removeListener.clickRemoveCart(produto);
            });

        }
    }
}

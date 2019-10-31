package com.example.carrinhoprodutos.view.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carrinhoprodutos.R;
import com.example.carrinhoprodutos.model.Produto;
import com.example.carrinhoprodutos.view.interfaces.DeleteItem;
import com.example.carrinhoprodutos.view.interfaces.ItemsToCart;
import com.example.carrinhoprodutos.view.interfaces.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProdutosAdapter extends RecyclerView.Adapter<ProdutosAdapter.ViewHolder> {
    private List<Produto> listaProdutos;
    private ItemsToCart cartListener;
    private OnItemClickListener clickListener;
    private DeleteItem deleteListener;

    public ProdutosAdapter(List<Produto> listaProdutos, ItemsToCart cartListener, OnItemClickListener clickListener, DeleteItem deleteListener) {
        this.listaProdutos = listaProdutos;
        this.cartListener = cartListener;
        this.clickListener = clickListener;
        this.deleteListener = deleteListener;
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

    public void atualizaLista(List<Produto> produtos) {
        listaProdutos.clear();
        listaProdutos = produtos;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nomeProduto;
        private TextView precoProduto;
        private ImageButton editButton;
        private ImageButton cartButton;
        private ImageButton deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nomeProduto = itemView.findViewById(R.id.textView_produto_nome);
            precoProduto = itemView.findViewById(R.id.textView_produto_preco);
            editButton = itemView.findViewById(R.id.button_edit);
            cartButton = itemView.findViewById(R.id.button_cart);
            deleteButton = itemView.findViewById(R.id.button_delete);
        }


        public void onBind(Produto produto) {
            nomeProduto.setText(produto.getNome());
            precoProduto.setText(String.format(Locale.GERMAN, "R$ %.2f", produto.getPreco()));

            editButton.setOnClickListener(view -> {
                if (clickListener != null) {
                    clickListener.onItemClick(produto);
                }
            });

            cartButton.setOnClickListener(view -> {
                if (cartListener != null) {
                    cartListener.addItemToCart(produto);
                }
            });

            deleteButton.setOnClickListener(view -> {
                if(deleteListener != null){
                    deleteListener.removeItemFromDB(produto);
                }
            });

        }


    }
}

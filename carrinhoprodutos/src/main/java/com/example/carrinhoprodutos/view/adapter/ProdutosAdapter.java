package com.example.carrinhoprodutos.view.adapter;

import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carrinhoprodutos.R;
import com.example.carrinhoprodutos.model.Produto;
import com.example.carrinhoprodutos.view.interfaces.ItemsToCart;
import com.example.carrinhoprodutos.view.interfaces.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class ProdutosAdapter extends RecyclerView.Adapter<ProdutosAdapter.ViewHolder> {
    private List<Produto> listaProdutos;
    private ItemsToCart listener;
    private OnItemClickListener clickListener;

    private SparseBooleanArray itensSelecionados;
    private List<Produto> produtosSelecionados;

    public ProdutosAdapter(List<Produto> listaProdutos, OnItemClickListener clickListener) {
        this.listaProdutos = listaProdutos;
        this.clickListener = clickListener;
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

    //Marca/ desmarca individualmente os itens
    public void toggleSelection(int position) {
        if (itensSelecionados.get(position, false)) {
            itensSelecionados.delete(position);
        } else {
            itensSelecionados.put(position, true);
        }
        notifyItemChanged(position);
    }

    //Limpa seleção de todos os itens
    public void clearSelections() {
        itensSelecionados.clear();
        notifyDataSetChanged();
    }

    public int getSelectedItemCount() {
        return itensSelecionados.size();
    }

    public List<Integer> getSelectedItems() {
        List<Integer> items = new ArrayList<>(itensSelecionados.size());

        for (int i = 0; i < itensSelecionados.size(); i++) {
            items.add(itensSelecionados.keyAt(i));
        }
        return items;
    }

    public void sendToCart(int position) {
        produtosSelecionados.add(listaProdutos.get(position));

        listener.itemsToCart(produtosSelecionados);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nomeProduto;
        private TextView precoProduto;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nomeProduto = itemView.findViewById(R.id.textView_produto_nome);
            precoProduto = itemView.findViewById(R.id.textView_produto_preco);
        }


        public void onBind(Produto produto) {
            nomeProduto.setText(produto.getNome());
            precoProduto.setText(produto.getPreco().toString());

            itemView.setOnClickListener(view -> {

                if(clickListener != null){
                    clickListener.onItemClick(produto);
                }
            });
        }
    }
}

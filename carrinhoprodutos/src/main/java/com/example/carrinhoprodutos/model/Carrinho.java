package com.example.carrinhoprodutos.model;

import java.util.List;

public class Carrinho {
    private long id;
    private List<Produto> produtosCarrinho;

    public Carrinho(long id, List<Produto> produtosCarrinho) {
        this.id = id;
        this.produtosCarrinho = produtosCarrinho;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Produto> getProdutosCarrinho() {
        return produtosCarrinho;
    }

    public void setProdutosCarrinho(List<Produto> produtosCarrinho) {
        this.produtosCarrinho = produtosCarrinho;
    }
}

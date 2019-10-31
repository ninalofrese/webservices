package com.example.carrinhoprodutos.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Carrinho implements Parcelable {
    private long id;
    private List<Produto> produtosCarrinho;

    public Carrinho(List<Produto> produtosCarrinho) {
        this.produtosCarrinho = produtosCarrinho;
    }

    protected Carrinho(Parcel in) {
        id = in.readLong();
        produtosCarrinho = in.createTypedArrayList(Produto.CREATOR);
    }

    public static final Creator<Carrinho> CREATOR = new Creator<Carrinho>() {
        @Override
        public Carrinho createFromParcel(Parcel in) {
            return new Carrinho(in);
        }

        @Override
        public Carrinho[] newArray(int size) {
            return new Carrinho[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeTypedList(produtosCarrinho);
    }
}

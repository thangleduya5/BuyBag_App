package com.example.bantuixach.model;

public class Cart {
    int idUser;
    int idProduct;
    int quantity;

    public Cart() {
    }

    public Cart(int idUser, int idProduct, int quantity) {
        this.idUser = idUser;
        this.idProduct = idProduct;
        this.quantity = quantity;
    }

    public int getIdUser() {
        return idUser;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

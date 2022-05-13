package com.example.bantuixach.model;

public class Bill_detail {
    int idBill;
    int idProduct;
    int quantity;
    float price;

    public Bill_detail() {
    }

    public Bill_detail(int idBill, int idProduct, int quantity, float price) {
        this.idBill = idBill;
        this.idProduct = idProduct;
        this.quantity = quantity;
        this.price = price;
    }

    public int getIdBill() {
        return idBill;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public int getQuantity() {
        return quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setIdBill(int idBill) {
        this.idBill = idBill;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}

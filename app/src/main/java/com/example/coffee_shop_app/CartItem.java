package com.example.coffee_shop_app;


import java.io.Serializable;

public class CartItem implements Serializable {
    private Product product;
    private int quantity;
    private int sugarLevel;


    public CartItem(Product product, int quantity, int sugarLevel) {
        this.product = product;
        this.quantity = quantity;
        this.sugarLevel = sugarLevel;
    }

    public Product getProduct() { return product; }
    public int getQuantity() { return quantity; }
    public int getSugarLevel() { return sugarLevel; }


    public void incrementQuantity() { this.quantity++; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

}
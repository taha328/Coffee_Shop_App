package com.example.coffee_shop_app;

// Remove 'implements Serializable' if no longer needed for Intents
public class Product {
    private String id;
    private String name;
    private double price;
    // --- CHANGE THIS ---
    // private String imageUrl; // Old field
    private int imageResourceId; // New field to hold R.drawable.xxx IDs
    // -------------------
    private String categoryId;

    // --- UPDATE CONSTRUCTOR ---
    public Product(String id, String name, double price, int imageResourceId, String categoryId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageResourceId = imageResourceId; // Assign to new field
        this.categoryId = categoryId;
    }

    // --- Getters ---
    public String getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    // --- UPDATE GETTER ---
    public int getImageResourceId() { return imageResourceId; } // Getter for the resource ID
    // -------------------
    public String getCategoryId() { return categoryId; }
}
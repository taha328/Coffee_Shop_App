package com.example.coffee_shop_app;


public class Category {
    private String id;
    private String name;
    private int iconDrawableId;

    // Updated constructor
    public Category(String id, String name, int iconDrawableId) {
        this.id = id;
        this.name = name;
        this.iconDrawableId = iconDrawableId;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public int getIconDrawableId() { return iconDrawableId; } // <<< New getter
}
package com.example.lab5_dialog__notification;


public class Product {
    private int id;
    private String name;
    private String category;
    private String price;

    public Product() {}

    public Product(int id, String name, String category, String price) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
    }

    public Product(String name, String category, String price) {
        this.name = name;
        this.category = category;
        this.price = price;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getPrice() { return price; }
    public void setPrice(String price) { this.price = price; }

    // Hàm này giúp in ra ListView đẹp mắt
    @Override
    public String toString() {
        return "Mã SP: " + id + " | " + name + "\nPhân loại: " + category + " - Giá: " + price + " VNĐ";
    }
}
package com.lovi_dev.service;

public class Product {
    private final String name;
    private final String price;

    public Product(String name, String price) {
        this.name = name;
        this.price = price;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }
}

package com.example.ecomate;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.stream.Stream;

public class Product extends ArrayList<Product> {

    private String image;

    public Product(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    @NonNull
    @Override
    public Stream<Product> stream() {
        return null;
    }
}
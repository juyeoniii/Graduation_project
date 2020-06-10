package com.example.ecomate;

import java.util.ArrayList;

public class Image extends ArrayList<Image> {

    private String image;

    public Image(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

}
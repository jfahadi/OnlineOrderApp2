package com.example.shan.onlineorderapp;

import androidx.appcompat.widget.RecyclerView;

/**
 * Created by Shan on 01-Apr-16.
 */
public class Product {

    int _ID;
    int item_code;
    String title;
    int quantity;
    int image;
    String description;
    int price;

    public Product(int _ID, int item_code, String title, int quantity, int image, String description, int price) {
        this._ID = _ID;
        this.item_code = item_code;
        this.title = title;
        this.quantity = quantity;
        this.image = image;
        this.description = description;
        this.price = price;
    }

    public int get_ID() {
        return _ID;
    }

    public void set_ID(int _ID) {
        this._ID = _ID;
    }

    public int getItem_code() {
        return item_code;
    }

    public void setItem_code(int item_code) {
        this.item_code = item_code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}

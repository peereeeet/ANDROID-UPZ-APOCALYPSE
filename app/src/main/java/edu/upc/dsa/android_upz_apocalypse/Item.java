package edu.upc.dsa.android_upz_apocalypse;

import android.net.Uri;

public class Item {
    int ID;
    String name;
    int price;
    String description;
    String image;

    public Item(int ID, String name, int price, String description, String image) {
        this.ID = ID;
        this.name = name;
        this.price = price;
        this.description = description;
        this.image = image;
    }

    public int getID() {
        return ID;
    }
    public void setID(int ID) {
        this.ID = ID;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
}

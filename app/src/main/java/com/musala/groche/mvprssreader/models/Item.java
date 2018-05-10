package com.musala.groche.mvprssreader.models;

import com.google.gson.annotations.SerializedName;

public class Item {

    @SerializedName("id")
    public int id;

    @SerializedName("label")
    public String label;

    @SerializedName("description")
    public String description;

    @SerializedName("imgurl")
    public String imgurl;

    public Item() {}

    public Item(int id, String label, String description, String imgurl) {
        this.id = id;
        this.label = label;
        this.description = description;
        this.imgurl = imgurl;
    }

    public int getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public String getDescription() {
        return description;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String toString() {
        return "ID: " + this.getId() + "\n" +
                "Label: " + this.getLabel() + "\n" +
                "Description: " + this.getDescription() + "\n" +
                "Image URL: " + this.getImgurl() + "\n";
    }
}

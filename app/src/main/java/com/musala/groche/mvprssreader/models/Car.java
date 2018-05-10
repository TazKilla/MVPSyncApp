package com.musala.groche.mvprssreader.models;

import com.google.gson.annotations.SerializedName;

public class Car {

    @SerializedName("id")
    public int id;

    @SerializedName("manufacturer")
    public int manufacturer;

    @SerializedName("model")
    public String model;

    @SerializedName("year")
    public int year;

    @SerializedName("price")
    public double price;

    @SerializedName("engine")
    public int engine;

    @SerializedName("fuel")
    public int fuel;

    @SerializedName("transmission")
    public int transmission;

    @SerializedName("description")
    public String description;

    @SerializedName("imgurl")
    public String imgurl;

    @SerializedName("favorite")
    public int favorite;

    public Car(int id, int manufacturer, String model, int year, float price, int engine,
               int fuel, int transmission, String description, String imgurl, int favorite) {
        this.id = id;
        this.manufacturer = manufacturer;
        this.model = model;
        this.year = year;
        this.price = price;
        this.engine = engine;
        this.fuel = fuel;
        this.transmission = transmission;
        this.description = description;
        this.imgurl = imgurl;
        this.favorite = favorite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(int manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getEngine() {
        return engine;
    }

    public void setEngine(int engine) {
        this.engine = engine;
    }

    public int getFuel() {
        return fuel;
    }

    public void setFuel(int fuel) {
        this.fuel = fuel;
    }

    public int getTransmission() {
        return transmission;
    }

    public void setTransmission(int transmission) {
        this.transmission = transmission;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }

    @Override
    public String toString() {
        return "ID: " + this.getId() + "\n" +
                "Manufacturer: " + this.getManufacturer() + "\n" +
                "Model: " + this.getModel() + "\n" +
                "Year: " + this.getYear() + "\n" +
                "Price: " + this.getPrice() + "\n" +
                "Engine: " + this.getEngine() + "\n" +
                "Fuel: " + this.getFuel() + "\n" +
                "Transmission: " + this.getTransmission() + "\n" +
                "Description: " + this.getDescription() + "\n" +
                "Image URL: " + this.getImgurl() + "\n" +
                "Favorite: " + (this.getFavorite() == 1 ? "Yes" : "No") + "\n";
    }
}

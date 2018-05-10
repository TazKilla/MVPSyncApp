package com.musala.groche.mvprssreader.models;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface SheetSuAPI {

    String BASE_URL = "https://sheetsu.com/apis/v1.0bu/";

    @GET("ae4f4d6937a6")
    Call<List<Car>> getCars();

    @GET("11d1dd1dee10")
    Call<List<Item>> getManufacturers();

    @GET("f66cd00eb483")
    Call<List<Item>> getEngines();

    @GET("df811056313b")
    Call<List<Item>> getFuels();

    @GET("6df98ce6dff0")
    Call<List<Item>> getTransmissions();
}
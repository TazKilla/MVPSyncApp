package com.musala.groche.mvprssreader.views;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.musala.groche.mvprssreader.models.Car;
import com.musala.groche.mvprssreader.models.ListWrapper;
import com.musala.groche.mvprssreader.models.SheetSuAPI;
import com.musala.groche.mvprssreader.presenters.MainContract;
import com.musala.groche.mvprssreader.presenters.MainPresenter;
import com.musala.groche.mvprssreader.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends BaseActivity implements MainContract.View, View.OnClickListener {

    private final static String TAG = "MainActivity";

    private TextView mTextView;
    private MainPresenter mPresenter;
    private SheetSuAPI sheetSuAPI;

    @Override
    protected int getContentResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void init(@Nullable Bundle state) {
        mTextView = findViewById(R.id.tvHello);
        mTextView.setOnClickListener(this);
        mPresenter = new MainPresenter();
        mPresenter.attach(this);
        mPresenter.loadHelloText();

        createSheetSuAPI();
        sheetSuAPI.getCars().enqueue(carsCallback);
    }

    @Override
    public void onTextLoaded(String text) {
        mTextView.setText(text);
    }

    @Override
    public void onClick(View view) {
        mPresenter.loadHelloText();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detach();
    }

    Callback<ListWrapper<Car>> carsCallback = new Callback<ListWrapper<Car>>() {
        @Override
        public void onResponse(@NonNull Call<ListWrapper<Car>> call, Response<ListWrapper<Car>> response) {
            if (response.isSuccessful()) {
                ListWrapper<Car> cars = response.body();
                if (cars != null) {
                    Log.d(TAG, "Cars list received:");
                    for (Car car : cars.elements) {
                        Log.d(TAG, car.toString());
                    }
                }
            } else {
                Log.d(TAG, "carsCallback -- Code: " + response.code() + " Message: " + response.message());
            }
        }

        @Override
        public void onFailure(@NonNull Call<ListWrapper<Car>> call, Throwable t) {
            Log.e(TAG, "carsCallback error -- " + t.toString());
        }
    };

    private void createSheetSuAPI() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss2")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SheetSuAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        sheetSuAPI = retrofit.create(SheetSuAPI.class);
    }
}

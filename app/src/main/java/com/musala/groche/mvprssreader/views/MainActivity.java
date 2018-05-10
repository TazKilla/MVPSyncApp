package com.musala.groche.mvprssreader.views;

import android.app.ProgressDialog;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.musala.groche.mvprssreader.models.Car;
import com.musala.groche.mvprssreader.presenters.MainContract;
import com.musala.groche.mvprssreader.presenters.MainPresenter;
import com.musala.groche.mvprssreader.R;

public class MainActivity extends BaseActivity implements MainContract.View {

    private final static String TAG = "MainActivity";

    private MainPresenter mPresenter;
    private ProgressDialog progressDialog;

    @Override
    protected int getContentResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void init(@Nullable Bundle state) {
        LinearLayout mainLayout;
        mainLayout = findViewById(R.id.car_layout);
        mPresenter = new MainPresenter();
        mPresenter.attach(this);
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Waiting for cars list...");
        progressDialog.setTitle("Loading data");
        progressDialog.show();
        mPresenter.loadData();
        mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Touching screen, send loadNewCar() request...");
                mPresenter.loadNewCar();
            }
        });
    }

    @Override
    public void onCarLoaded(Car car, String[] labels) {

        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        Log.d(TAG, "Car loaded, going to display it...");

        displayCar(car, labels);
    }

    @Override
    public void onCarLoadingError() {

        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        Toast.makeText(this, "Error when loading car, please try later", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onItemLoadingError() {

        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        Toast.makeText(this, "Error when loading some items, please try later", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detach();
    }

    private void displayCar(Car car, String[] labels) {

        TextView mTextView;

        mTextView = findViewById(R.id.detail_manufacturer);
        mTextView.setText(labels[0]);
        mTextView = findViewById(R.id.detail_model);
        mTextView.setText(car.getModel());
        mTextView = findViewById(R.id.detail_year);
        mTextView.setText(String.valueOf(car.getYear()));
        mTextView = findViewById(R.id.detail_price);
        mTextView.setText(String.valueOf(car.getPrice()));
        mTextView = findViewById(R.id.detail_engine);
        mTextView.setText(labels[1]);
        mTextView = findViewById(R.id.detail_fuel);
        mTextView.setText(labels[2]);
        mTextView = findViewById(R.id.detail_transmission);
        mTextView.setText(labels[3]);
        mTextView = findViewById(R.id.detail_description);
        mTextView.setText(car.getDescription());
        mTextView = findViewById(R.id.detail_imgurl);
        mTextView.setText(car.getImgurl());
        mTextView = findViewById(R.id.detail_favorite);
        mTextView.setText(labels[4]);
    }
}

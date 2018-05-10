package com.musala.groche.mvprssreader.views;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.musala.groche.mvprssreader.models.Car;
import com.musala.groche.mvprssreader.presenters.MainContract;
import com.musala.groche.mvprssreader.presenters.MainPresenter;
import com.musala.groche.mvprssreader.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class MainActivity extends BaseActivity implements MainContract.View {

    private final static String TAG = "MainActivity";

    private MainPresenter mPresenter;
    private ProgressDialog progressDialog;
    private ImageView imageView;

    @Override
    protected int getContentResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void init(@Nullable Bundle state) {
        LinearLayout mainLayout;
        imageView = findViewById(R.id.detail_img);
        mainLayout = findViewById(R.id.car_layout);
        mPresenter = new MainPresenter();
        mPresenter.attach(this);
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage(getString(R.string.toast_loading_cars));
        progressDialog.setTitle(getString(R.string.toast_title_loading));
        progressDialog.setCancelable(false);
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

        Log.d(TAG, "Car number " + car.getId() + " loaded, going to display it...");

        displayCar(car, labels);
    }

    @Override
    public void onCarLoadingError() {

        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        Toast.makeText(
                this,
                "Error when loading car, please try later",
                Toast.LENGTH_LONG
        ).show();
    }

    @Override
    public void onItemLoadingError() {

        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        Toast.makeText(
                this,
                "Error when loading some items, please try later",
                Toast.LENGTH_LONG
        ).show();
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
        Picasso.get().load(car.getImgurl()).placeholder(R.drawable.ic_if_sedan).into(target);
        mTextView = findViewById(R.id.detail_favorite);
        mTextView.setText(labels[4]);
    }

    private Target target = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            imageView.setImageBitmap(bitmap);
        }

        @Override
        public void onBitmapFailed(Exception e, Drawable errorDrawable) {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_if_sedan));
        }
    };
}

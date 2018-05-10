package com.musala.groche.mvprssreader.presenters;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.musala.groche.mvprssreader.models.Car;
import com.musala.groche.mvprssreader.models.Item;
import com.musala.groche.mvprssreader.models.SheetSuAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainPresenter extends BasePresenter<MainContract.View> implements MainContract.Presenter {

    private final static String TAG = "MainPresenter";
    private SheetSuAPI sheetSuAPI;
    private List<Car> carList;
    private List<Item> manufacturerList;
    private List<Item> engineList;
    private List<Item> fuelList;
    private List<Item> transmissionList;
    private int counter = 0;

    @Override
    public void loadData() {
        if (sheetSuAPI == null) {
            createSheetSuAPI();
        }
        sheetSuAPI.getCars().enqueue(carsCallback);
        sheetSuAPI.getManufacturers().enqueue(itemsCallback);
        sheetSuAPI.getEngines().enqueue(itemsCallback);
        sheetSuAPI.getFuels().enqueue(itemsCallback);
        sheetSuAPI.getTransmissions().enqueue(itemsCallback);
    }

    @Override
    public void loadNewCar() {
        if (counter >= carList.size()) {
            counter = 0;
        }
        Car newCar = carList.get(counter);
        counter++;
        getmView().onCarLoaded(newCar, getItemsLabel(newCar));
    }

    private String[] getItemsLabel(Car car) {
        String[] labels = new String[5];
        for (int i = 0; i < 4; i++) {
            labels[i] = getItemLabel(i, car);
        }
        if (car.getFavorite() == 1) {
            labels[4] = "Yes";
        } else {
            labels[4] = "No";
        }
        return labels;
    }

    private Callback<List<Car>> carsCallback = new Callback<List<Car>>() {
        @Override
        public void onResponse(@NonNull Call<List<Car>> call, @NonNull Response<List<Car>> response) {
            if (getmView() == null) {
                Log.d(TAG, "View have been destroyed, the asynctask will end");
                return;
            }
            if (response.isSuccessful()) {
                carList = response.body();
                if (manufacturerList != null && engineList != null && fuelList != null && transmissionList != null) {
                    getmView().onCarLoaded(carList.get(counter), getItemsLabel(carList.get(counter)));
                    counter++;
                }
            } else {
                Log.d(TAG, "carsCallback -- Code: " + response.code() + " Message: " + response.message());
                getmView().onCarLoadingError();
            }
        }

        @Override
        public void onFailure(@NonNull Call<List<Car>> call, Throwable t) {
            Log.e(TAG, "carsCallback error -- " + t.toString());
            getmView().onCarLoadingError();
        }
    };

    private Callback<List<Item>> itemsCallback = new Callback<List<Item>>() {
        @Override
        public void onResponse(@NonNull Call<List<Item>> call, @NonNull Response<List<Item>> response) {
            if (getmView() == null) {
                Log.d(TAG, "View have been destroyed, the asynctask will end");
                return;
            }
            if (response.isSuccessful()) {
                List<Item> tempList = response.body();
                if (tempList == null) {
                    getmView().onItemLoadingError();
                    return;
                }
                switch (tempList.get(0).getItemtype()) {
                    case 0:
                        manufacturerList = tempList;
                        break;
                    case 1:
                        engineList = tempList;
                        break;
                    case 2:
                        fuelList = tempList;
                        break;
                    case 3:
                        transmissionList = tempList;
                        break;
                }
                if (carList != null && manufacturerList != null && engineList != null && fuelList != null && transmissionList != null) {
                    getmView().onCarLoaded(carList.get(counter), getItemsLabel(carList.get(counter)));
                    counter++;
                }
            } else {
                Log.d(TAG, "itemsCallback -- Code: " + response.code() + " Message: " + response.message());
                getmView().onItemLoadingError();
            }
        }

        @Override
        public void onFailure(@NonNull Call<List<Item>> call, Throwable t) {
            Log.e(TAG, "itemsCallback error -- " + t.toString());
            getmView().onItemLoadingError();
        }
    };

    @Override
    public void createSheetSuAPI() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss2")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SheetSuAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        sheetSuAPI = retrofit.create(SheetSuAPI.class);
    }

    private String getItemLabel(int itemType, Car car) {
        List<Item> tempList;
        int tempId;
        String notFound = "Unknown item";
        switch (itemType) {
            case 0:
                tempList = manufacturerList;
                tempId = car.getManufacturer();
                break;
            case 1:
                tempList = engineList;
                tempId = car.getEngine();
                break;
            case 2:
                tempList = fuelList;
                tempId = car.getFuel();
                break;
            case 3:
                tempList = transmissionList;
                tempId = car.getTransmission();
                break;
            default:
                return notFound;
        }
        for (Item item : tempList) {
            if (item.getId() == tempId) {
                return item.getLabel();
            }
        }
        return notFound;
    }
}

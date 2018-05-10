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

public class MainPresenter extends BasePresenter<MainContract.View>
        implements MainContract.Presenter {

    private final static String TAG = "MainPresenter";
    private SheetSuAPI sheetSuAPI;
    private List<Car> carList;
    private List<Item> manufacturerList;
    private List<Item> engineList;
    private List<Item> fuelList;
    private List<Item> transmissionList;
    private int counter = 0;

    /**
     * Called on app start, asynchronously get all data from remote source
     */
    @Override
    public void loadData() {

        // Check for API instance
        if (sheetSuAPI == null) {
            createSheetSuAPI();
        }

        // Make different call for each element type
        sheetSuAPI.getCars().enqueue(carsCallback);
        sheetSuAPI.getManufacturers().enqueue(itemsCallback);
        sheetSuAPI.getEngines().enqueue(itemsCallback);
        sheetSuAPI.getFuels().enqueue(itemsCallback);
        sheetSuAPI.getTransmissions().enqueue(itemsCallback);
    }

    /**
     * Called every time user click on the screen, switch to the next car in the list
     */
    @Override
    public void loadNewCar() {

        // Check for counter position
        if (counter >= carList.size()) {
            counter = 0;
        }
        // get the next car on the list and increment the counter
        Car newCar = carList.get(counter);
        counter++;
        // Notify the View that the new car is ready
        getmView().onCarLoaded(newCar, getItemsLabel(newCar));
    }

    /**
     * Before to send a car to display, we need to change foreign keys to human readable values
     *
     * @param car the car that interface have to display
     * @return String[] string list that contains all needed labels
     */
    private String[] getItemsLabel(Car car) {

        String[] labels = new String[5];

        // Loop to get all item labels (manufacturer, engine, fuel and transmission)
        for (int i = 0; i < 4; i++) {
            labels[i] = getItemLabel(i, car);
        }
        // Check favorite status and store corresponding term
        if (car.getFavorite() == 1) {
            labels[4] = "Yes";
        } else {
            labels[4] = "No";
        }

        return labels;
    }

    /**
     * Retrofit call back, used to finish task when async part is done
     * It processes the response and notify the View, depending on the result
     */
    private Callback<List<Car>> carsCallback = new Callback<List<Car>>() {
        @Override
        public void onResponse(@NonNull Call<List<Car>> call,
                               @NonNull Response<List<Car>> response) {
            // Check if the View is still there. If not, simply return.
            if (getmView() == null) {
                Log.d(TAG, "View have been destroyed, the asynctask will end");
                return;
            }
            // View is still living, we check that the response is successful
            if (response.isSuccessful()) {
                carList = response.body();
                // Check if all the data have been loaded. If it is,
                // notify the View that data are ready to be displayed
                if (manufacturerList != null && engineList != null &&
                        fuelList != null && transmissionList != null) {

                    getmView().onCarLoaded(
                            carList.get(counter),
                            getItemsLabel(carList.get(counter))
                    );
                    counter++;
                }
            // Response is not successful, log some info and notify the View
            } else {
                Log.d(TAG, "carsCallback -- " +
                        "Code: " + response.code() + " " +
                        "Message: " + response.message());
                getmView().onCarLoadingError();
            }
        }

        // The asynctask failed, log some details and notify the View
        @Override
        public void onFailure(@NonNull Call<List<Car>> call, Throwable t) {
            Log.e(TAG, "carsCallback error -- " + t.toString());
            getmView().onCarLoadingError();
        }
    };

    /**
     * Very similar to carsCallback(), the main difference is the switch statement
     */
    private Callback<List<Item>> itemsCallback = new Callback<List<Item>>() {
        @Override
        public void onResponse(@NonNull Call<List<Item>> call,
                               @NonNull Response<List<Item>> response) {
            // Check if the View is still there. If not, simply return
            if (getmView() == null) {
                Log.d(TAG, "View have been destroyed, the asynctask will end");
                return;
            }
            // View is still living, we check that the response is successful
            if (response.isSuccessful()) {
                List<Item> tempList = response.body();
                // If the item list is empty, something went wrong,
                // so notify the View and return
                if (tempList == null) {
                    getmView().onItemLoadingError();
                    return;
                }
                // We got some data, so now check for the item type
                // in order to populate the right list
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
                // Check if all the data have been loaded. If it is,
                // notify the View that data are ready to be displayed
                if (carList != null && manufacturerList != null && engineList != null &&
                        fuelList != null && transmissionList != null) {

                    getmView().onCarLoaded(
                            carList.get(counter),
                            getItemsLabel(carList.get(counter))
                    );
                    counter++;
                }
            // Response is not successful, log some info and notify the View
            } else {
                Log.d(TAG, "itemsCallback -- " +
                        "Code: " + response.code() + " " +
                        "Message: " + response.message());
                getmView().onItemLoadingError();
            }
        }

        // The asynctask failed, log some details and notify the View
        @Override
        public void onFailure(@NonNull Call<List<Item>> call, Throwable t) {
            Log.e(TAG, "itemsCallback error -- " + t.toString());
            getmView().onItemLoadingError();
        }
    };

    /**
     * Initialize the API to communicate with remote data source
     */
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

    /**
     * Use car's foreign keys to get an item label, in order to display some readable data
     *
     * @param itemType int describing the item type
     * @param car Car the car that is going to be displayed
     *
     * @return String the item label
     */
    private String getItemLabel(int itemType, Car car) {

        // Declare/initialize variables
        List<Item> tempList;
        int tempId;
        String notFound = "Unknown item";

        // Check for the good item type and set variables up
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

        // Iterate into the selected list to find the corresponding label
        for (Item item : tempList) {
            if (item.getId() == tempId) {
                return item.getLabel();
            }
        }
        return notFound;
    }
}

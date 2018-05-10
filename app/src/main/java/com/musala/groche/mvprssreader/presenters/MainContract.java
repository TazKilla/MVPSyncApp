package com.musala.groche.mvprssreader.presenters;

import com.musala.groche.mvprssreader.models.Car;
import com.musala.groche.mvprssreader.views.BaseView;

public interface MainContract {

    // User actions, presenter will implements
    interface Presenter extends BaseMVPPresenter<View> {
        void createSheetSuAPI();
        void loadData();
        void loadNewCar();
    }

    // Action callbacks, activity/fragment will implement
    interface View extends BaseView {
        void onCarLoaded(Car car, String[] labels);
        void onCarLoadingError();
        void onItemLoadingError();
    }
}

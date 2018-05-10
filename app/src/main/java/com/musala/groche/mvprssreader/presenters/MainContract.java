package com.musala.groche.mvprssreader.presenters;

import com.musala.groche.mvprssreader.views.BaseView;

public interface MainContract {

    // User actions, presenter will implements
    interface Presenter extends BaseMVPPresenter<View> {
        void loadHelloText();
    }

    // Action callbacks, activity/fragment will implement
    interface View extends BaseView {
        void onTextLoaded(String text);
    }
}

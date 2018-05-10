package com.musala.groche.mvprssreader.presenters;

import com.musala.groche.mvprssreader.views.BaseView;

public class BasePresenter<V extends BaseView> implements BaseMVPPresenter<V> {

    /**
     * Attached view
     */
    private V mView;

    @Override
    public void attach(V view) {
        mView = view;
    }

    @Override
    public void detach() {
        mView = null;
    }

    @Override
    public boolean isAttached() {
        return mView != null;
    }

    V getmView() {
        return mView;
    }
}

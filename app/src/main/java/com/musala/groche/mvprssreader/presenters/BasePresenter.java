package com.musala.groche.mvprssreader.presenters;

import com.musala.groche.mvprssreader.views.BaseView;

public class BasePresenter<View extends BaseView> implements BaseMVPPresenter<View> {

    /**
     * Attached view
     */
    private View mView;

    @Override
    public void attach(View view) {
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

    View getmView() {
        return mView;
    }
}

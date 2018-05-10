package com.musala.groche.mvprssreader.presenters;

import com.musala.groche.mvprssreader.views.BaseView;

/**
 * Each presenter must implements this interface
 *
 * @param <View> View for the presenter
 */
public interface BaseMVPPresenter<View extends BaseView> {

    /**
     * Called when view is attached to presenter
     *
     * @param view the view to attach
     */
    void attach(View view);

    /**
     * Called when view is detached from presenter
     */
    void detach();

    /**
     * @return true if a view is attached to presenter
     */
    boolean isAttached();
}

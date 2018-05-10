package com.musala.groche.mvprssreader.presenters;

import com.musala.groche.mvprssreader.models.FakeModel;

import java.util.Random;

public class MainPresenter extends BasePresenter<MainContract.View> implements MainContract.Presenter {

    private FakeModel model = new FakeModel();

    @Override
    public void loadHelloText() {
        Random random = new Random();
        String[] helloTexts = model.getHellos();
        String hello = helloTexts[random.nextInt(helloTexts.length)];
        getmView().onTextLoaded(hello);
    }
}

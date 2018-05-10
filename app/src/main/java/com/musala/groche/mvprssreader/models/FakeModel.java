package com.musala.groche.mvprssreader.models;

public class FakeModel implements BaseModel {

    private String[] helloTexts = {"BONJOUR", "HOLA", "HALLO", "MERHABA", "HELLO", "CIAO", "KONNICHIWA"};

    @Override
    public String[] getHellos() {
        return helloTexts;
    }
}

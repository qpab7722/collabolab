package com.collabolab;


public class CardItem {

    private String id;
    private String mTitleResource;

    public CardItem(String title, String text) {
        mTitleResource = title;
        id = text;
    }

    public String getText() {
        return id;
    }

    public String getTitle() {
        return mTitleResource;
    }
}

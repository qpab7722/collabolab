package com.collabolab;


public class CardItem {

    private int mTypeResource;
    private int mTitleResource;

    public CardItem(int title, int text) {
        mTitleResource = title;
        mTypeResource = text;
    }

    public int getText() {
        return mTypeResource;
    }

    public int getTitle() {
        return mTitleResource;
    }
}

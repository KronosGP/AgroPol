package com.example.agropol.LayoutClasses;

public class AddPlantSpinnerItem {
    private String mText;
    private int mImage;

    public AddPlantSpinnerItem(String text, int image) {
        mText = text;
        mImage = image;
    }

    public String getText() {
        return mText;
    }

    public int getImage() {
        return mImage;
    }
}

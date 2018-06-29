package io.github.henryejemuta.journalapp.enums;

import android.graphics.Color;

public enum PriorityColor {
    DEFAULT(0, Color.rgb(255, 255, 255), "White", "#FFFFFF"),
    URGENT(1, Color.rgb(255, 0, 0), "Red", "#FF0000"),
    MEDIUM(2, Color.rgb(255,255,0), "Yellow", "#FFFF00"),
    LOW_MID(3, Color.rgb(128,0,128), "Purple", "#800080"),
    LOW_LOW(6, Color.rgb(0,0,255), "Blue", "#0000FF"),
    LOW(7, Color.rgb(0,255,0), "Lime", "#00FF00"),
    LEAST(8, Color.rgb(0, 128, 0), "Green", "#008000");

    private int mId;
    private int mColor;
    private String mTitle;
    private String mHexCode;

    PriorityColor(int id, int color, String title, String hexCode){
        mId = id;
        mColor = color;
        mTitle = title;
        mHexCode = hexCode;
    }

    public int getId() {
        return mId;
    }

    public int getColor() {
        return mColor;
    }

    public String getName() {
        return mTitle;
    }

    public String getHexCode() {
        return mTitle;
    }

    public static PriorityColor findLevel(int id) {
        if(id == DEFAULT.getId())
            return DEFAULT;
        for (PriorityColor c : PriorityColor.values()) {
            if (c.getId() == id)
                return c;
        }
        return DEFAULT;
    }

    @Override
    public String toString() {
        return getName();
    }
}

package com.collabolab;

import android.graphics.drawable.Drawable;
import android.util.Log;

import com.lukedeighton.wheelview.adapter.WheelArrayAdapter;

import java.util.ArrayList;
import java.util.List;

public class MyWheelAdapter extends WheelArrayAdapter {
    private ArrayList<TextDrawable> list = new ArrayList<>();

    public MyWheelAdapter(List items) {
        super(items);
        list.addAll(items);
        Log.d("MyWheelAdapter", "list: " + items.size());
    }

    @Override
    public Drawable getDrawable(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    public Object getItem(int position) {
        return list.get(position);
    }
}

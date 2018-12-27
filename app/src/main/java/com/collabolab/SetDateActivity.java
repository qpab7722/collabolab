package com.collabolab;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.lukedeighton.wheelview.WheelView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class SetDateActivity extends Activity {
    private static final String TAG = SetDateActivity.class.getSimpleName();

    Intent intent;

    WheelView wvMonth, wvDay;
    MyWheelAdapter waMonth, waDay;
    ArrayList<TextDrawable> listMonth, listDay;
    int month=12, day=27;

    TextView tvWeekday,tvWeekdayEng;
    Spinner spinTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);

        listMonth = new ArrayList<>();
        listMonth.add(new TextDrawable(String.valueOf(12)));
        listMonth.add(new TextDrawable(String.valueOf(1)));
        listMonth.add(new TextDrawable(String.valueOf(2)));
        wvMonth = findViewById(R.id.wv_month);
        waMonth = new MyWheelAdapter(listMonth);
        wvMonth.setAdapter(waMonth);
        wvMonth.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectListener() {
            @Override
            public void onWheelItemSelected(WheelView parent, Drawable itemDrawable, int position) {
                listDay = new ArrayList<>();
                Log.d(TAG, "onWheelItemSelected: " + listMonth.get(position).getText());
                switch (listMonth.get(position).getText()) {
                    case "12":
                        wvDay.setWheelItemCount(5);
                        listDay.add(new TextDrawable(String.valueOf(27)));
                        listDay.add(new TextDrawable(String.valueOf(28)));
                        listDay.add(new TextDrawable(String.valueOf(29)));
                        listDay.add(new TextDrawable(String.valueOf(30)));
                        listDay.add(new TextDrawable(String.valueOf(31)));
                        waDay = new MyWheelAdapter(listDay);
                        wvDay.setAdapter(waDay);
                        break;
                    case "1":
                        for (int i=1; i<32; i++) {
                            listDay.add(new TextDrawable(String.valueOf(i)));
                        }
                        waDay = new MyWheelAdapter(listDay);
                        wvDay.setAdapter(waDay);
                        break;
                    case "2":
                        for (int i=1; i<27; i++) {
                            listDay.add(new TextDrawable(String.valueOf(i)));
                        }
                        waDay = new MyWheelAdapter(listDay);
                        wvDay.setAdapter(waDay);
                        break;
                }
                month = Integer.valueOf(listMonth.get(position).getText());
                day = Integer.valueOf(listDay.get(wvDay.getSelectedPosition()).getText());
                Calendar c = Calendar.getInstance();
                c.set(2018, month-1, day);
                int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
                String[] weekday = getResources().getStringArray(R.array.weekday);
                String[] weekday_eng = getResources().getStringArray(R.array.weekday_eng);
                tvWeekday.setText(weekday[dayOfWeek-1]);
                tvWeekdayEng.setText(weekday_eng[dayOfWeek-1]);
            }
        });

        listDay = new ArrayList<>();
        listDay.add(new TextDrawable(String.valueOf(27)));
        listDay.add(new TextDrawable(String.valueOf(28)));
        listDay.add(new TextDrawable(String.valueOf(29)));
        listDay.add(new TextDrawable(String.valueOf(30)));
        listDay.add(new TextDrawable(String.valueOf(31)));
        wvDay = findViewById(R.id.wv_day);
        wvDay.setWheelItemCount(5);
        waDay = new MyWheelAdapter(listDay);
        wvDay.setAdapter(waDay);
        wvDay.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectListener() {
            @Override
            public void onWheelItemSelected(WheelView parent, Drawable itemDrawable, int position) {
                month = Integer.valueOf(listMonth.get(wvMonth.getSelectedPosition()).getText());
                day = Integer.valueOf(listDay.get(position).getText());
                Calendar c = Calendar.getInstance();
                c.set(2018, month-1, day);
                int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
                String[] weekday = getResources().getStringArray(R.array.weekday);
                String[] weekday_eng = getResources().getStringArray(R.array.weekday_eng);
                tvWeekday.setText(weekday[dayOfWeek-1]);
                tvWeekdayEng.setText(weekday_eng[dayOfWeek-1]);
            }
        });

        tvWeekday = findViewById(R.id.tv_weekday);
        tvWeekdayEng = findViewById(R.id.tv_weekday_eng);

        spinTime = findViewById(R.id.spinner_time);
        ArrayAdapter<String> TImeadapter = new ArrayAdapter<String>(this, R.layout.spinner_item_day_picker, getResources().getStringArray(R.array.time));
        spinTime.setAdapter(TImeadapter);

        Button btnNext = findViewById(R.id.btn_next);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                c.set(2018,month-1,day,(int)Integer.valueOf(spinTime.getSelectedItem().toString().replace("시","")),0);
                Date date = c.getTime();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:00:00");
                String data = simpleDateFormat.format(date);

                intent = new Intent(getApplicationContext(), SetPeopleActivity.class);
                intent.putExtra("date",data);
                startActivity(intent);
                Log.d(TAG, "date: "+ data);
            }
        });
    }


}

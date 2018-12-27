package com.collabolab;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
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
    Condition cd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);

        ImageButton ibMenu = findViewById(R.id.ib_menu);
        ibMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(getApplicationContext(), view);
                popup.getMenuInflater().inflate(R.menu.popup, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        Intent intent;
                        if (item.getTitle().equals("상세보기")) {
                            intent = new Intent(getApplicationContext(), ShowActivity.class);
                            startActivity(intent);
                            finish();
                        } else if (item.getTitle().equals("예약하기")) {
                            intent = new Intent(getApplicationContext(), SetDateActivity.class);
                            startActivity(intent);
                            finish();
                        } else if (item.getTitle().equals("내 정보")) {
                            intent = new Intent(getApplicationContext(), MyInfoActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        return true;
                    }
                });
                popup.show();//showing popup menu
            }
        });

        cd = Condition.getInstance();
        cd.setStartDate(null);
        cd.setEndDate(null);

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
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:00:00");
                String data = simpleDateFormat.format(date);

                intent = new Intent(getApplicationContext(), SetPeopleActivity.class);
                intent.putExtra("date",data);
                cd.setStartDate(data);

                c.set(2018,month-1,day,(int)Integer.valueOf(spinTime.getSelectedItem().toString().replace("시",""))+1,0);
                date = c.getTime();
                simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:00:00");
                data = simpleDateFormat.format(date);
                cd.setEndDate(data);
                cd.setDay(day);
                cd.setMonth(month-1);
                Log.d(TAG, "date: "+ data);
                startActivity(intent);
            }
        });
    }


}

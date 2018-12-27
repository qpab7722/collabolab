package com.collabolab;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TimePicker;

import java.util.Calendar;

public class SetDateActivity extends AppCompatActivity {

    CalendarView calendarView;
    TimePicker timePicker;
    Button btn_end;
    Intent intent;
    int hour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);

        calendarView= findViewById(R.id.cv_cal);
        timePicker=findViewById(R.id.tp_time);
        btn_end = findViewById(R.id.btn_datetime);

        //set calendar 2month
        Calendar calendar = Calendar.getInstance();
        Long min = calendar.getTime().getTime();
        Long max = 2629746000L + calendar.getTime().getTime();
        calendarView.setMinDate(min);
        calendarView.setMaxDate(max);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            hour = timePicker.getHour();
        }

        intent = new Intent(this, SetPeopleActivity.class);
        btn_end.setOnClickListener(mClickListener);
    }

    View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //String message = editText.getText().toString();
            //        intent.putExtra(EXTRA_MESSAGE, message);
            startActivity(intent);
        }};

}

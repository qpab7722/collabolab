package com.collabolab;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class SetPeopleActivity extends AppCompatActivity {

    EditText et_people;
    Button btn_end;
    Intent intent;
    String people;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people);

        et_people=findViewById(R.id.et_people);
        btn_end=findViewById(R.id.btn_people);


        intent = new Intent(this, SetTypeActivity.class);
        btn_end.setOnClickListener(mClickListener);


    }

    View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(intent);
            people=et_people.getText().toString();
            Toast.makeText(getApplicationContext(),people,Toast.LENGTH_SHORT).show();
        }};

}

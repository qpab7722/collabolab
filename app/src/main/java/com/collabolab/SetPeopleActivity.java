package com.collabolab;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import de.hdodenhof.circleimageview.CircleImageView;

public class SetPeopleActivity extends Activity {


    CircleImageView btn_people0,btn_people1,btn_people2;
    Intent intent;

    String Date;
    Condition cd;

    TextView tv_datetag,tv_peopletag;
    Button btn_next,btn_pre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people);

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
        cd.setCapacity(null);
        //get intent
        Intent gintent = getIntent();
        Log.e("dsad",cd.getStartDate()+"\n"+cd.getEndDate());
        //capacity=  gintent.getStringExtra("capacity").toString();

        btn_people0=findViewById(R.id.btn_35);
        //ani(btn_people0,-30f,-30f);
        btn_people1=findViewById(R.id.btn_510);
       // ani(btn_people1,30f,-30f);
        btn_people2=findViewById(R.id.btn_10up);
        //ani(btn_people2,-30f,-30f);

        tv_datetag=findViewById(R.id.tv_datetag);
        tv_datetag.setText(cd.getStartDate());
        tv_peopletag=findViewById(R.id.tv_peopletag);

        btn_people0.setOnClickListener(mClickListener);
        btn_people1.setOnClickListener(mClickListener);
        btn_people2.setOnClickListener(mClickListener);

        btn_next=findViewById(R.id.btn_next);
        btn_next.setOnClickListener(mnextClickListener);
        intent = new Intent(this, SetTypeActivity.class);

        btn_pre=findViewById(R.id.btn_pre);
        btn_pre.setOnClickListener(mpreClickListener);
    }

    View.OnClickListener mnextClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(cd.getCapacity()!=null)
                startActivity(intent);
            else
                Toast.makeText(getApplicationContext(),"인원수를 선택해 주세요",Toast.LENGTH_SHORT).show();

        }};
    View.OnClickListener mpreClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }};

    View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getTag().toString()){
                case "0":
                    tv_peopletag.setText(" 3~5명 ");
                    break;
                case "1":
                    tv_peopletag.setText(" 5~10명 ");
                    break;
                case "2":
                    tv_peopletag.setText(" 10명이상 ");
                    break;
            }
            cd.setCapacity(v.getTag().toString());
        }};

    void ani(View v,float xdif, float ydif){
        AnimatorSet animSetXY = new AnimatorSet();
        ObjectAnimator y = ObjectAnimator.ofFloat(v,
                "translationY",v.getY(), xdif);
        y.setRepeatCount(ValueAnimator.INFINITE);
        y.setRepeatMode(ValueAnimator.REVERSE);

        ObjectAnimator x = ObjectAnimator.ofFloat(v,
                "translationX", v.getX(), ydif);
        x.setRepeatCount(ValueAnimator.INFINITE);
        x.setRepeatMode(ValueAnimator.REVERSE);
        animSetXY.playTogether(x, y);
        animSetXY.setInterpolator(new LinearInterpolator());
        animSetXY.setDuration(5000);
        animSetXY.start();
    }

}

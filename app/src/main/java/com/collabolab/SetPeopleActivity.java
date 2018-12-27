package com.collabolab;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.Toast;

public class SetPeopleActivity extends Activity {


    Button btn_people0,btn_people1,btn_people2;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people);

        btn_people0=findViewById(R.id.btn_35);
        ani(btn_people0,80f,80f);
        btn_people1=findViewById(R.id.btn_510);
        ani(btn_people1,-80f,80f);
        btn_people2=findViewById(R.id.btn_10up);
        ani(btn_people2,80f,-80f);

        btn_people0.setOnClickListener(mClickListener);
        btn_people1.setOnClickListener(mClickListener);
        btn_people2.setOnClickListener(mClickListener);


        intent = new Intent(this, SetTypeActivity.class);

    }

    View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            intent.putExtra("capacity",v.getTag().toString());
            startActivity(intent);
            Toast.makeText(getApplicationContext(),""+v.getTag().toString(),Toast.LENGTH_SHORT).show();
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

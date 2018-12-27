package com.collabolab;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class ChooseTImeActivity extends Activity {

    List<Integer> time = new ArrayList<>();

    RecyclerView rvTimeBlock;
    LinearLayoutManager layoutManager;
    TimeBlockAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_time);

        for (int i=0; i<24; i++) { time.add(i); }

        rvTimeBlock = findViewById(R.id.rv_time);
        layoutManager = new LinearLayoutManager(this);
        rvTimeBlock.setLayoutManager(layoutManager);
        adapter = new TimeBlockAdapter(time, this);
        rvTimeBlock.setAdapter(adapter);

        Button btnPre = findViewById(R.id.btn_pre);
        btnPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Button btnNext = findViewById(R.id.btn_next);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PutPurposeActivity.class);
                startActivity(intent);
            }
        });

    }

}

package com.collabolab;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

public class PutPurposeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_put_purpose);

        ImageButton ibMenu = findViewById(R.id.ib_menu);
        ibMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(getApplicationContext(), view);
                popup.getMenuInflater().inflate(R.menu.popup, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        Intent intent;
                        if (item.getTitle().equals("공간소개")) {
                            intent = new Intent(getApplicationContext(), ShowActivity.class);
                            startActivity(intent);
                            finish();
                        } else if (item.getTitle().equals("예약하기")) {
                            intent = new Intent(getApplicationContext(), SetDateActivity.class);
                            startActivity(intent);
                            finish();
                        } else if (item.getTitle().equals("사용현황")) {
                            intent = new Intent(getApplicationContext(), MyInfoActivity.class);
                            startActivity(intent);
                            finish();
                        } else if (item.getTitle().equals("이용내역")) {
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

        FloatingActionButton btn = findViewById(R.id.btn_next);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"예약이 완료되었습니다",Toast.LENGTH_SHORT).show();
            }
        });
    }
}

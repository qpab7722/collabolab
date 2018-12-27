package com.collabolab;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;

public class SetTypeActivity extends AppCompatActivity {


    Button btn_open,btn_com,btn_board,btn_beam,btn_film, btn_end;
    TextView tv_sel1,tv_sel2,tv_sel3;
     ArrayList<String> selectedType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type);
        getSupportActionBar().hide();
        selectedType=new ArrayList<String>();

        btn_open=findViewById(R.id.btn_typeopen);
        btn_com=findViewById(R.id.btn_typecom);
        btn_board=findViewById(R.id.btn_typeboard);
        btn_beam=findViewById(R.id.btn_typebeam);
        btn_film=findViewById(R.id.btn_typefilm);

        btn_open.setOnClickListener(mClickListener);
        btn_com.setOnClickListener(mClickListener);
        btn_board.setOnClickListener(mClickListener);
        btn_beam.setOnClickListener(mClickListener);
        btn_film.setOnClickListener(mClickListener);

        btn_end= findViewById(R.id.btn_typend);
        btn_end.setOnClickListener(mendClickListener);

    }

    View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View button) {
            Button b = (Button)button;
            String text = b.getText().toString();
            if(button.isSelected()){
                //삭제
                for(Iterator<String> it = selectedType.iterator();it.hasNext();){
                    String val =it.next();
                    if(val.equals(text))
                        it.remove();
                }
                //updateVIew();
                button.setSelected(!button.isSelected());
                Log.e("type",selectedType.size()+"   삭제 ");
            }else if (selectedType.size()<3){
                //추가(3개 아래일때만)
                selectedType.add(text);
                //updateVIew();
                button.setSelected(!button.isSelected());
                Log.e("type",selectedType.size()+"   추가 ");
            }
        }};
    View.OnClickListener mendClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View button) {
            createNotification();
        }
    };

    private void updateVIew(){
        tv_sel1.setText("");
        tv_sel2.setText("");
        tv_sel3.setText("");
        if(selectedType.size()>0)
            tv_sel1.setText(selectedType.get(0));
        if(selectedType.size()>1)
            tv_sel2.setText(selectedType.get(1));
        if(selectedType.size()>2)
            tv_sel3.setText(selectedType.get(2));
    }

    private void createNotification() {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "default");

        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle("알림 제목");
        builder.setContentText("알람 세부 텍스트");

        //builder.setColor(Color.RED);
        // 사용자가 탭을 클릭하면 자동 제거
        builder.setAutoCancel(true);

        // 알림 표시
        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(new NotificationChannel("default", "기본 채널", NotificationManager.IMPORTANCE_DEFAULT));
        }

        // id값은
        // 정의해야하는 각 알림의 고유한 int값
        notificationManager.notify(1, builder.build());
    }

}

package com.collabolab;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import de.hdodenhof.circleimageview.CircleImageView;

public class SetTypeActivity extends Activity {


    Button btn_open,btn_com,btn_board,btn_beam,btn_film;
    TextView tv_peopletag,tv_datetag;
    TextView tv_sel1,tv_sel2,tv_sel3;
    ArrayList<String> selectedType;

    //ArrayList<String> selectedTypeTag;

     String capacity;
     String[] itemList;

     Intent intent;
    Condition cd;

    Button btn_next,btn_pre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type);
        cd = Condition.getInstance();

        cd.setItemList(null);
        selectedType=new ArrayList<String>();
        //selectedTypeTag=new ArrayList<String>();


        tv_sel1 = findViewById(R.id.tv_typetag1);
        tv_sel2 = findViewById(R.id.tv_typetag2);
        tv_sel3 = findViewById(R.id.tv_typetag3);
        tv_sel1.setVisibility(View.INVISIBLE);
        tv_sel2.setVisibility(View.INVISIBLE);
        tv_sel3.setVisibility(View.INVISIBLE);

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

        tv_datetag=findViewById(R.id.tv_datetag);
        tv_datetag.setText(cd.getStartDate());

        tv_peopletag=findViewById(R.id.tv_peopletag);
        capacity=  cd.getCapacity();
        switch (capacity){
            case "0":
                tv_peopletag.setText(" 3~5명 ");
                break;
            case "1":
                tv_peopletag.setText(" 6~10명 ");
                break;
            case "2":
                tv_peopletag.setText(" 11명이상 ");
                break;
        }

        btn_next=findViewById(R.id.btn_next);
        btn_next.setOnClickListener(mnextClickListener);

        intent = new Intent(this, RoomReservationActivity.class);

        btn_pre=findViewById(R.id.btn_pre);
        btn_pre.setOnClickListener(mpreClickListener);
    }

    View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View button) {
            Button b = (Button)button;
            String text = b.getTag().toString();
            if(button.isSelected()){
                //삭제
                for(Iterator<String> it = selectedType.iterator();it.hasNext();){
                    String val =it.next();
                    if(val.equals(text))
                        it.remove();
                }
                updateVIew();
                button.setSelected(!button.isSelected());
                Log.e("type",selectedType.size()+"   삭제 ");
            }else if (selectedType.size()<3){
                //추가(3개 아래일때만)
                selectedType.add(text);
                updateVIew();
                button.setSelected(!button.isSelected());
                Log.e("type",selectedType.size()+"   추가 ");
            }
        }};

    private void updateVIew(){
        tv_sel1.setVisibility(View.INVISIBLE);
        tv_sel2.setVisibility(View.INVISIBLE);
        tv_sel3.setVisibility(View.INVISIBLE);
        if(selectedType.size()>0) {
            tv_sel1.setVisibility(View.VISIBLE);
            switch (selectedType.get(0)){
                case "0":
                    tv_sel1.setText("컴퓨터");
                    break;
                case "1":
                    tv_sel1.setText("화이트보드");
                    break;
                case "2":
                    tv_sel1.setText("개방형");
                    break;
                case "3":
                    tv_sel1.setText("빔 프로젝터");
                    break;
                case "4":
                    tv_sel1.setText("스튜디오");
                    break;
            }
        }
        if(selectedType.size()>1){
            tv_sel2.setVisibility(View.VISIBLE);
            switch (selectedType.get(1)){
                case "0":
                    tv_sel2.setText("컴퓨터");
                    break;
                case "1":
                    tv_sel2.setText("화이트보드");
                    break;
                case "2":
                    tv_sel2.setText("개방형");
                    break;
                case "3":
                    tv_sel2.setText("빔 프로젝터");
                    break;
                case "4":
                    tv_sel2.setText("스튜디오");
                    break;
            }
        }
        if(selectedType.size()>2){
            tv_sel3.setVisibility(View.VISIBLE);
            switch (selectedType.get(2)){
                case "0":
                    tv_sel3.setText("컴퓨터");
                    break;
                case "1":
                    tv_sel3.setText("화이트보드");
                    break;
                case "2":
                    tv_sel3.setText("개방형");
                    break;
                case "3":
                    tv_sel3.setText("빔 프로젝터");
                    break;
                case "4":
                    tv_sel3.setText("스튜디오");
                    break;
            }
        }
    }

    View.OnClickListener mnextClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            cd.setItemList(selectedType);
            Log.e("sdaaaaaaaaaa",cd.getCapacity() + cd.getStartDate()+ cd.getEndDate() +cd.getItemList().size());
            new JSONTask().execute("http://52.78.178.50/api/android/search/room_search");
        }};
    View.OnClickListener mpreClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }};

    public class JSONTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... urls) {

            try {
                Log.e("ㅇㅇㅇㅇㅇㅇ","돌아간다`할아부지");
                //JSONObject를 만들고 key value 형식으로 값을 저장해준다.
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("capacity", cd.getCapacity());
                jsonObject.accumulate("itemList", new JSONArray(cd.getItemList()));//new JSONArray(selectedType)
                jsonObject.accumulate("startDate", cd.getStartDate());
                jsonObject.accumulate("endDate", cd.getEndDate());

                HttpURLConnection con = null;
                BufferedReader reader = null;

                try{
                    URL url = new URL(urls[0]);//url을 가져온다.
                    con = (HttpURLConnection) url.openConnection();

                    con.setRequestMethod("POST");
                    con.setRequestProperty("Cache-Control", "no-cache");
                    con.setRequestProperty("Content-Type", "application/json");
                    con.setRequestProperty("Accept", "text/html");
                    con.setDoOutput(true);
                    con.setDoInput(true);
                    con.connect();

                    OutputStream outStream = con.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outStream));
                    writer.write(jsonObject.toString());
                    writer.flush();
                    writer.close();

                    //서버로 부터 데이터를 받음
                    InputStream stream = con.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(stream));
                    StringBuffer buffer = new StringBuffer();
                    String line = "";
                    while((line = reader.readLine()) != null){
                        buffer.append(line);
                    }
                    return buffer.toString();//서버로 부터 받은 값을 리턴해줌 아마 OK!!가 들어올것임

                } catch (MalformedURLException e){
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if(con != null){
                        con.disconnect();
                    }
                    try {
                        if(reader != null){
                            reader.close();//버퍼를 닫아줌
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            intent.putExtra("roomresult",result);
            startActivity(intent);
        }

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

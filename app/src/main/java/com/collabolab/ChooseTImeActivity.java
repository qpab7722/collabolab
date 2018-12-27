package com.collabolab;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ChooseTImeActivity extends Activity {

    List<Integer> time = new ArrayList<>();

    RecyclerView rvTimeBlock;
    LinearLayoutManager layoutManager;
    TimeBlockAdapter adapter;

    int reservenum;
    JSONArray objArr;

    List<Integer> selectedtime = new ArrayList<>();

    Button btn_next;
    String roomid;

    Condition cd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_time);
        cd = Condition.getInstance();

        for (int i=0; i<24; i++) { time.add(i); }

        Intent gintent = getIntent();
        String result=  gintent.getStringExtra("roomresult").toString();
        Log.e("ㅇㅇㅇㅇㅇㅇ",result);
        try {
            objArr = new JSONArray(result);
            reservenum = objArr.length();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for(int i=0;i<reservenum;i++){
            JSONObject jsonOBject = null;
            try {
                jsonOBject = objArr.getJSONObject(i);
                String startDate =jsonOBject.getString("startDate");
                String endDate =jsonOBject.getString("endDate");
                Integer st = Integer.parseInt(startDate);
                selectedtime.add(st);
                roomid=jsonOBject.getString("roomId");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        rvTimeBlock = findViewById(R.id.rv_time);
        layoutManager = new LinearLayoutManager(this);
        rvTimeBlock.setLayoutManager(layoutManager);
        adapter = new TimeBlockAdapter(time, this,selectedtime);
        rvTimeBlock.setAdapter(adapter);

        btn_next=findViewById(R.id.btn_reserveend);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = adapter.getSel();

                cd = Condition.getInstance();

                Calendar c = Calendar.getInstance();
                c.set(2018,cd.getMonth(),cd.getDay(),(int)Integer.parseInt(str),0);
                Date date = c.getTime();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:00:00");
                String data = simpleDateFormat.format(date);
                Log.d("타임!", "date: "+ data);
                cd.setStartDate(data);

                simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:59:59");
                data = simpleDateFormat.format(date);
                cd.setEndDate(data);


                if (str!=null)
                new JSONTask3().execute("http://52.78.178.50/api/android/reservation/reservation");
            }
        });

    }

    public class JSONTask3 extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... urls) {
            try {
                Log.e("ㅇㅇㅇㅇㅇㅇ","돌아간다`할아부지");
                //JSONObject를 만들고 key value 형식으로 값을 저장해준다.
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("userId", "1");
                jsonObject.accumulate("roomId",roomid);
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
            Log.e("ads",result);
            //startActivity(intent);
        }

    }

}

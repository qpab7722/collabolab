package com.collabolab;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RoomReservationActivity extends AppCompatActivity  {


    private ViewPager mViewPager;

    private CardPagerAdapter mCardAdapter;
    private ShadowTransformer mCardShadowTransformer;
    private CardFragmentPagerAdapter mFragmentCardAdapter;
    private ShadowTransformer mFragmentCardShadowTransformer;


    String result;
    int cardNum;
    JSONArray objArr;
    Button btn_end;
    Intent intent;

    String roomid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_roomreservation);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);

        btn_end=findViewById(R.id.btn_cardsel);
        btn_end.setOnClickListener(mClickListener);

        //json데이터 분해~~~
        Intent gintent = getIntent();
        result=  gintent.getStringExtra("roomresult").toString();
        Log.e("ㅇㅇㅇㅇㅇㅇ",result);
        try {
            objArr = new JSONArray(result);
            cardNum = objArr.length();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mCardAdapter = new CardPagerAdapter();
        for(int i=0;i<cardNum;i++){
            JSONObject jsonOBject = null;
            try {
                jsonOBject = objArr.getJSONObject(i);
                String name =jsonOBject.getString("name");
                String id =jsonOBject.getString("roomId");
                Log.e("ㅇㅇㅇㅇㅇㅇ",objArr.length()+"d"+name);
                mCardAdapter.addCardItem(new CardItem(name, id));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        mFragmentCardAdapter = new CardFragmentPagerAdapter(getSupportFragmentManager(),
                dpToPixels(2, this));

        mCardShadowTransformer = new ShadowTransformer(mViewPager, mCardAdapter);
        mFragmentCardShadowTransformer = new ShadowTransformer(mViewPager, mFragmentCardAdapter);

        mViewPager.setAdapter(mCardAdapter);
        mViewPager.setPageTransformer(false, mFragmentCardShadowTransformer);
        mViewPager.setOffscreenPageLimit(3);

        intent = new Intent(this, ChooseTImeActivity.class);
    }

    View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            CardItem item = mCardAdapter.getCardItemAt(mViewPager.getCurrentItem());
            roomid = item.getText();
            Log.e("시간표 줘라",roomid);
            new JSONTask2().execute("http://52.78.178.50/api/common/reserv_check");
            //startActivity(intent);
        }};

    public static float dpToPixels(int dp, Context context) {
        return dp * (context.getResources().getDisplayMetrics().density);
    }


    public class JSONTask2 extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... urls) {
            try {
                Log.e("ㅇㅇㅇㅇㅇㅇ","돌아간다`할아부지");
                //JSONObject를 만들고 key value 형식으로 값을 저장해준다.
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("roomId", roomid);
                jsonObject.accumulate("offsetDate", "2018-12-27");

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
            intent.putExtra("roomresult",result);
            startActivity(intent);
        }

    }

}

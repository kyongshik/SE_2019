package com.example.se_2019.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.se_2019.DBRequest.getVoteResultRequest;
import com.example.se_2019.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ReadResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_result);

        int[] votecount = new int[5]; //각각 투표의 count세기위함
        Intent in = getIntent();
        String roomID = in.getStringExtra("roomID");
        int num = in.getIntExtra("num", 0);
        String chkString = in.getStringExtra("chklist");
        String[] chkArray = chkString.split("@#"); //chklist가져옴

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String json_num = jsonObject.getString("num");
                        int json_int = Integer.parseInt(json_num);
                        if (json_int==num && (jsonObject.getString("roomID").equals(roomID))){
                            String json_votenum = jsonObject.getString("votenum");
                            for (int j = 0; j < json_votenum.length(); j++) {  //어떤 한 사용자가 투표한 목록들을 가져와 array에 나타냄
                                votecount[Integer.parseInt(json_votenum.charAt(j) + "")]++; //투표한 숫자들을 카운트 시켜
                            }
                        }
                    }

                    TextView result1 = findViewById(R.id.result1);
                    TextView result2 = findViewById(R.id.result2);
                    TextView result3 = findViewById(R.id.result3);
                    TextView result4 = findViewById(R.id.result4);
                    TextView result5 = findViewById(R.id.result5);
                    TextView percent1 = findViewById(R.id.percent1);
                    TextView percent2 = findViewById(R.id.percent2);
                    TextView percent3 = findViewById(R.id.percent3);
                    TextView percent4 = findViewById(R.id.percent4);
                    TextView percent5 = findViewById(R.id.percent5);


                    int sum = 0;
                    for (int i = 0; i < votecount.length; i++)
                        sum += votecount[i];
                    for (int i = 0; i < chkArray.length; i++) {
                        int percent = 0;

                        if (i == 0) {
                            result1.setText("1. " + chkArray[i]);

                            percent1.setText("투표한 사람 수: " + votecount[i]);
                        }
                        if (i == 1) {
                            result2.setText("2. " + chkArray[i]);
                            percent2.setText("투표한 사람 수: " + votecount[i]);
                        }
                        if (i == 2) {
                            result3.setText("3. " + chkArray[i]);
                            percent3.setText("투표한 사람 수: " + votecount[i]);
                        }
                        if (i == 3) {
                            result4.setText("4. " + chkArray[i]);
                            percent4.setText("투표한 사람 수: " + votecount[i]);
                        }
                        if (i == 4) {
                            result5.setText("5. " + chkArray[i]);
                            percent5.setText("투표한 사람 수: " + votecount[i]);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        getVoteResultRequest getVoteResultRequest = new getVoteResultRequest(roomID, responseListener);
        RequestQueue queue = Volley.newRequestQueue(ReadResultActivity.this);
        queue.add(getVoteResultRequest);

        //툴바
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //왼쪽에 home버튼 추가
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.toolbar_home);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }


    //TOOLBAR설정
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);//여기서 e/menu_main UI를 가져옴
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {
            Toast.makeText(this, "홈버튼을 눌렀습니다", Toast.LENGTH_SHORT).show();

        }
        if (id == R.id.toolbar_alarm) {
            Toast.makeText(this, "알람버튼을 눌렀습니다", Toast.LENGTH_SHORT).show();
        }
        if (id == R.id.toolbar_profile) {
            Toast.makeText(this, "프로필버튼을 눌렀습니다", Toast.LENGTH_SHORT).show();
            // SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(this);
            Intent intent = new Intent(this, Preferences.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


}

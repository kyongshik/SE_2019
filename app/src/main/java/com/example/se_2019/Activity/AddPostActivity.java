package com.example.se_2019.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.se_2019.Alarm;
import com.example.se_2019.DBRequest.*;
import com.example.se_2019.Note;
import com.example.se_2019.Post;
import com.example.se_2019.R;
import com.example.se_2019.Schedule;
import com.example.se_2019.Vote;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AddPostActivity extends AppCompatActivity {
    //게시글 작성

    public static int NUMBER = 0;
    private String strDate;
    private TextView Datepick;
    private static String Calstr;
    private TextView CalDate;
    private EditText title;
    private EditText content;
    private Date date;
    private SimpleDateFormat dateFormat;
    private static String strBox;
    private CheckBox chkBox;
    private Button addbtn;
    private Button Okbtn;
    private LinearLayout ll;
    private ArrayList<CheckBox> chkList = new ArrayList<>();
    private ArrayList<String> chkListStr = new ArrayList<>();
    private Button saveBtn;
    //private  Button setBtn;//다른 화면에서 사용(선택)

    private int pos = 100;
    String titles = "";
    String contents = "";
    private static Note note;
    private static Schedule schedule;
    private static Post p;
    String userID;
    String roomCode;

    private static Alarm alarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        final TextView textView = findViewById(R.id.textView_spinner);
        Spinner spinner = findViewById(R.id.spinner);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //왼쪽에 home버튼 추가
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.toolbar_home);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

//        final Intent intent = getIntent();
//        String alarm_time = intent.getExtras().getString("alarm_time");
//        String alarm_title = intent.getExtras().getString("alarm_title");

        //전 activity에서 보낸 데이터 전달받음
        Bundle bundle = getIntent().getExtras();
        String userID = bundle.getString("userID");
        roomCode = bundle.getString("room_code");


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                textView.setText("" + parent.getItemAtPosition(position));
                saveBtn = findViewById(R.id.save);
                pos = position;


                CalendarView calendar = findViewById(R.id.calendarview);
                calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(CalendarView view, int year,
                                                    int month, int dayOfMonth) {
                          Toast.makeText(getApplicationContext(), "" + year + "/" + (month + 1) + "/" + dayOfMonth, Toast.LENGTH_SHORT).show();
                        CalDate = findViewById(R.id.date_calendar);
                        Calstr = year + "/" + (month + 1) + "/" + dayOfMonth;
                        CalDate.setText(Calstr);
                    }
                });//날짜 자동으로 입력받기




                Button.OnClickListener mClickListener0 = new View.OnClickListener() {
                    public void onClick(View v) {
                        Log.i("ALARM","언제 눌리는지 보쟈"+String.valueOf(position));
                        NUMBER++;
                        date = new Date();
                        dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm", java.util.Locale.getDefault());
                        strDate = dateFormat.format(date);

                        if (pos == 0) {
                            title = findViewById(R.id.title_post);
                            content = findViewById(R.id.content_post);
                            Datepick = findViewById(R.id.date_post);
                            titles = title.getText().toString();
                            contents = content.getText().toString();
                            note = new Note(userID, strDate, titles, contents);
                            p = new Post(userID, strDate, titles, contents, null, null, 0,  0);
                        } else if (pos == 1) {
                            content = findViewById(R.id.content_vote);
                            Datepick = findViewById(R.id.date_vote);
                            Datepick.setText(strDate);
                            //날짜 받아오기
                            title = findViewById(R.id.title_vote);
                            titles = title.getText().toString();
                            //제목
                            contents = content.getText().toString();
                            //메모 입력받는 것
                            addbtn = findViewById(R.id.AddBox); //ADD LIST
                            Okbtn = findViewById(R.id.OkBtn); //리스트 추가 OK버튼

                            final EditText input = findViewById(R.id.inputstr);
                            Button.OnClickListener mClickListener1 = new View.OnClickListener() {
                                public void onClick(View v) {
                                    input.setVisibility(View.VISIBLE);
                                    Okbtn.setVisibility(View.VISIBLE);
                                }
                            };
                            addbtn.setOnClickListener(mClickListener1);
                            Button.OnClickListener mClickListener2 = new View.OnClickListener() {
                                public void onClick(View v) {
                                    strBox = input.getText().toString();
                                    chkBox = new CheckBox(getApplicationContext());
                                    chkBox.setBackgroundColor(Color.LTGRAY);
                                    chkBox.setText(strBox);
                                    chkBox.setTextColor(Color.BLACK);
                                    chkBox.setVisibility(View.VISIBLE);
                                    if (chkList.size() < 5) {
                                        ll = findViewById(R.id.vote_top);
                                        ll.addView(chkBox);
                                        chkList.add(chkBox);
                                        chkListStr.add(strBox);
                                        input.setText("");
                                    } else {
                                        Toast.makeText(getApplicationContext(), "5개까지만 입력할 수 있습니다.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            };
                            Okbtn.setOnClickListener(mClickListener2);
                            Vote vote = new Vote(userID, strDate, titles, contents, chkListStr);
                            p = new Post(userID, strDate, titles, contents, null, null, 1,  1); //chklist넣어야함

                        } else if (pos == 2) {


                            title = findViewById(R.id.title_calendar);
                            titles = title.getText().toString();

                            content = findViewById(R.id.content_calendar);
                            contents = content.getText().toString();

                            schedule = new Schedule(userID, strDate, titles, contents, Calstr);
                            p = new Post(userID, strDate, titles, contents, null, Calstr, 2, 2);
                            Toast.makeText(getApplicationContext(), "서버서버"+roomCode, Toast.LENGTH_LONG).show();
                            alarm = new Alarm(Calstr, titles, roomCode);
                            check_server();
                        }

                        //서버에 추가
                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    boolean success = jsonObject.getBoolean("successadd");
                                    //boolean success2 = jsonObject.getBoolean("successalarm");

                                    if (success) { //방등록에 성공한 경우
                                        //intent로 다른 창에 뜨게 함
                                        Intent intent = getIntent();
                                        intent.putExtra("postinfo", p);

                                        setResult(RESULT_OK, intent);
                                        finish();

                                    } else { //등록에 실패한 경우
                                        Toast.makeText(getApplicationContext(), "서버등록에 실패하였습니다.", Toast.LENGTH_LONG).show();
                                        return;
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        };

                        AddPostRequest addPostRequest = new AddPostRequest(roomCode, userID, p.getWrite_date(), p.getTitle(), p.getContent(), null, p.getDday(),String.valueOf(p.getPosi()), String.valueOf(p.getNum()), responseListener);
                        RequestQueue queue = Volley.newRequestQueue(AddPostActivity.this);
                        queue.add(addPostRequest);

                    }
                };
                saveBtn.setOnClickListener(mClickListener0);

                if (pos == 0) {
                    View view0 = findViewById(R.id.post);
                    view0.setVisibility(View.VISIBLE);
                    View view1 = findViewById(R.id.vote);
                    view1.setVisibility(View.GONE);
                    View view2 = findViewById(R.id.calendar);
                    view2.setVisibility(View.GONE);
                } else if (pos == 1) {
                    View view0 = findViewById(R.id.post);
                    view0.setVisibility(View.GONE);
                    View view1 = findViewById(R.id.vote);
                    view1.setVisibility(View.VISIBLE);
                    View view2 = findViewById(R.id.calendar);
                    view2.setVisibility(View.GONE);
                } else if (pos == 2) {
                    View view0 = findViewById(R.id.post);
                    view0.setVisibility(View.GONE);
                    View view1 = findViewById(R.id.vote);
                    view1.setVisibility(View.GONE);
                    View view2 = findViewById(R.id.calendar);
                    view2.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
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
            intent.putExtra("userID",userID);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
    public void check_server() {
        Response.Listener<String> responseListener_a = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("successalarm");
                    //Toast.makeText(getApplicationContext(), String.valueOf(success), Toast.LENGTH_LONG).show();
                    if (success) { //방등록에 성공한 경우
                        Toast.makeText(getApplicationContext(), "서버등록에 성공하였습니다.", Toast.LENGTH_LONG).show();
                        //intent로 다른 창에 뜨게 함
                        Intent intent = getIntent();
                        intent.putExtra("roomID",roomCode);
                        setResult(RESULT_OK, intent);
                        finish();

                    } else { //등록에 실패한 경우
                        Toast.makeText(getApplicationContext(), "서버등록에 실패하였습니다.", Toast.LENGTH_LONG).show();
                        return;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        AddAlarm addAlarmRequest = new AddAlarm(alarm.getTime(), alarm.getContent(),roomCode, responseListener_a);
        RequestQueue queue_a = Volley.newRequestQueue(AddPostActivity.this);
        queue_a.add(addAlarmRequest);
    }
}
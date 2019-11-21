package com.example.se_2019.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.se_2019.Note;
import com.example.se_2019.R;
import com.example.se_2019.Schedule;
import com.example.se_2019.Vote;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AddPostActivity extends AppCompatActivity {
    //게시글 작성

    public static int NUMBER = 0;


    private String user = "user"; //나중에 작성자 받아와서 이름띄우는거 해야함
    private String strDate;
    private TextView Datepick;
    private static String Calstr;
    private TextView CalDate;
    private  EditText title;
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
    private  Button saveBtn;
    //private  Button setBtn;//다른 화면에서 사용(선택)

    private int pos = 100;
    String titles = "";
    String contents = "";
    private static Note note;
    private static Schedule schedule;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        final TextView textView = findViewById(R.id.textView_spinner);
        Spinner spinner = findViewById(R.id.spinner);
        //스피너 선택

        //툴바
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //왼쪽에 home버튼 추가
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.toolbar_home);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                textView.setText(""+parent.getItemAtPosition(position));
                //여기서 array.xml에 설정된 카테고리(게시글, 일정, 투표) 글자 받아옴
                //position 0,1,2로 되어있음
                saveBtn = findViewById(R.id.save);
                pos = position;



                Button.OnClickListener mClickListener0 = new View.OnClickListener(){
                    public void onClick(View v){

                        NUMBER++;
                        //여기가 전체 저장버튼이라서 각 화면에 대해서 저장되는 정보가 달라야함
                        if(pos == 0) {
//                            Toast.makeText(getApplicationContext(), "position 0", Toast.LENGTH_SHORT).show();
                            //테스트
                            View view0 = findViewById(R.id.post);
                            view0.setVisibility(View.VISIBLE);
                            View view1 = findViewById(R.id.vote);
                            view1.setVisibility(View.GONE);
                            View view2 = findViewById(R.id.calendar);
                            view2.setVisibility(View.GONE);

                            date = new Date();
                            dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm", java.util.Locale.getDefault());
                            strDate = dateFormat.format(date);
                            title = findViewById(R.id.title_post);
                            content = findViewById(R.id.content_post);
                            Datepick = findViewById(R.id.date_post);
                            titles = title.getText().toString();
                            contents = content.getText().toString();
                            note = new Note(user, strDate, titles, contents);
                            note.setName(user);
                            note.setDate(strDate);
                            note.setTitle(titles);
                            note.setContent(contents);

                            // Toast.makeText(getApplicationContext(), note.getTitle(), Toast.LENGTH_SHORT).show();
                            Intent intent = getIntent();
                            intent.putExtra("postinfo", note);
                            setResult(0, intent);
                            finish();
                        }
                        else if(pos ==1){
                            //투표 화면
                            // Toast.makeText(getApplicationContext(), "position 1", Toast.LENGTH_SHORT).show();
                            //테스트
                            View view0 = findViewById(R.id.post);
                            view0.setVisibility(View.GONE);
                            View view1 = findViewById(R.id.vote);
                            view1.setVisibility(View.VISIBLE);
                            View view2 = findViewById(R.id.calendar);
                            view2.setVisibility(View.GONE);
                            //화면 구성
                            date = new Date();
                            dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm",java.util.Locale.getDefault());
                            strDate = dateFormat.format(date);
                            content = findViewById(R.id.content_vote);
                            Datepick = findViewById(R.id.date_vote);
                            Datepick.setText(strDate);
                            //날짜 받아오기
                            title = findViewById(R.id.title_vote);
                            titles = title.getText().toString();
                            // Toast.makeText(getApplicationContext(), titles, Toast.LENGTH_SHORT).show();
                            titles = titles.replace("'", "''"); //디비에 저장할때 여기서 가져가기
                            //제목
                            contents = content.getText().toString();
                            contents = contents.replace("'", "''");
                            // Toast.makeText(getApplicationContext(), contents, Toast.LENGTH_SHORT).show();
                            //메모 입력받는 것
                            addbtn = findViewById(R.id.AddBox); //ADD LIST
                            Okbtn = findViewById(R.id.OkBtn); //리스트 추가 OK버튼

                            final EditText input = findViewById(R.id.inputstr);
                            Button.OnClickListener mClickListener1 = new View.OnClickListener(){
                                public void onClick(View v){
                                    input.setVisibility(View.VISIBLE);
                                    Okbtn.setVisibility(View.VISIBLE);
                                }

                            };
                            //추가시에 보일 수 있게
                            addbtn.setOnClickListener(mClickListener1);
                            Button.OnClickListener mClickListener2 = new View.OnClickListener(){
                                public void onClick(View v){
                                    strBox = input.getText().toString();
                                    //Toast.makeText(getApplicationContext(), strBox, Toast.LENGTH_SHORT).show();
                                    chkBox = new CheckBox(getApplicationContext());
                                    chkBox.setBackgroundColor(Color.LTGRAY);
                                    chkBox.setText(strBox);
                                    chkBox.setTextColor(Color.BLACK);
                                    chkBox.setVisibility(View.VISIBLE);
                                    if(chkList.size() <5){
                                        ll = findViewById(R.id.vote_top);
                                        ll.addView(chkBox);
                                        chkList.add(chkBox);
                                        chkListStr.add(strBox);
                                        input.setText("");
                                    }else {
                                        Toast.makeText(getApplicationContext(),"5개까지만 입력할 수 있습니다.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            };
                            /////////
                            Okbtn.setOnClickListener(mClickListener2);
                            //투표 리스트 추가하여 생성
                            Vote vote = new Vote(user, strDate, titles, contents, chkListStr);
                            vote.setName(user);
                            vote.setDate(strDate);
                            vote.setTitle(titles);
                            vote.setContent(contents);
                            vote.setChklist(chkListStr);
                            // Toast.makeText(getApplicationContext(), vote.getTitle(), Toast.LENGTH_SHORT).show();
                            Intent intent = getIntent();
                            intent.putExtra("voteinfo", vote);
                            setResult(1, intent);
                            finish();

                        }
                        else if(pos == 2){
                            //일정 화면
                            // Toast.makeText(getApplicationContext(), "position 2", Toast.LENGTH_SHORT).show();
                            //테스트
                            View view0 = findViewById(R.id.post);
                            view0.setVisibility(View.GONE);
                            View view1 = findViewById(R.id.vote);
                            view1.setVisibility(View.GONE);
                            View view2 = findViewById(R.id.calendar);
                            view2.setVisibility(View.VISIBLE);
                            //화면 구성
                            CalendarView calendar = findViewById(R.id.calendarview);
                            calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                                public void onSelectedDayChange(CalendarView view, int year,
                                                                int month, int dayOfMonth) {
                                    //Toast.makeText(getApplicationContext(), "" + year + "/" +
                                    //       (month + 1) + "/" + dayOfMonth, Toast.LENGTH_SHORT).show();
                                    CalDate = findViewById(R.id.date_calendar);
                                    Calstr = year + "/" + (month + 1) + "/" + dayOfMonth;
                                    CalDate.setText(Calstr);
                                }
                            });//날짜 자동으로 입력받기
                            title = findViewById(R.id.title_calendar);
                            titles = title.getText().toString();

                            titles = titles.replace("'", "''"); //디비에 저장할때 여기서 가져가기
                            //제목
                            content = findViewById(R.id.content_calendar);
                            contents = content.getText().toString();
                            contents = contents.replace("'", "''");
                            //Schedule temp = new Schedule(user, Calstr, titles, contents, Calstr);

                            date = new Date();
                            dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm", java.util.Locale.getDefault());
                            strDate = dateFormat.format(date);
                            schedule = new Schedule(user, strDate, titles, contents,Calstr);
                            schedule.setName(user);
                            schedule.setDate(strDate);
                            schedule.setTitle(titles);
                            schedule.setContent(contents);
                            schedule.setDday(Calstr);
                            // Toast.makeText(getApplicationContext(), schedule.getTitle(), Toast.LENGTH_SHORT).show();
                            Intent intent = getIntent();
                            intent.putExtra("calinfo", schedule);
                            setResult(2, intent);
                            finish();
                        }

                    }


                };
                saveBtn.setOnClickListener(mClickListener0);

                ///////////////////////////////////////////////////////////////////////
//                setBtn = findViewById(R.id.Set); //투표에서 체크한후 '선택'버튼
//                Button.OnClickListener mClickListener = new View.OnClickListener(){
//                    public void onClick(View v){
//                        for(int i = 0; i < chkList.size(); i++){
//                            if(chkList.get(i).isChecked()){
//                                Toast.makeText(getApplicationContext(),"==="+i+"===",Toast.LENGTH_SHORT).show();
//                                ///여기서 체크되면 인덱스 0,1,2~~ 확인해서 저장시켜야함.
//                                //chkList.get(i).setClickable(false);
//                            }
//                        }
//                        for(int i = 0; i < chkList.size(); i++) {
//                            chkList.get(i).setClickable(false);
//                        }
//                    }
//
//                };
//                setBtn.setOnClickListener(mClickListener);
                //여기는 작성자화면에서 뜨면 안되고 읽는 사람 입장에서 화면에 띄워야함  xml하고 여기서 코드 옮기기
                /////////////////////////////////////////////////////////////////////////


                if(pos ==0){
                    //게시글 화면
                    //  Toast.makeText(getApplicationContext(), "position 0", Toast.LENGTH_SHORT).show();
                    //테스트
                    View view0 = findViewById(R.id.post);
                    view0.setVisibility(View.VISIBLE);
                    View view1 = findViewById(R.id.vote);
                    view1.setVisibility(View.GONE);
                    View view2 = findViewById(R.id.calendar);
                    view2.setVisibility(View.GONE);

                    ////////////////////
                    date = new Date();
                    dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm", java.util.Locale.getDefault());
                    strDate = dateFormat.format(date);
                    title = findViewById(R.id.title_post);
                    content = findViewById(R.id.content_post);
                    Datepick = findViewById(R.id.date_post);
                    titles = title.getText().toString();
                    contents = content.getText().toString();
                    note = new Note(user, strDate, titles, contents);
                    note.setName(user);
                    note.setDate(strDate);
                    note.setTitle(titles);
                    note.setContent(contents);

//                    Intent intent = getIntent();
//                    intent.putExtra("postinfo", note);
//                    setResult(RESULT_OK, intent);
//                    finish();
                    //titles = titles.replace("'", "''"); //디비에 저장할때 여기서 가져가기


                }
                else if(pos ==1){
                    //투표 화면
                    //    Toast.makeText(getApplicationContext(), "position 1", Toast.LENGTH_SHORT).show();
                    //테스트
                    View view0 = findViewById(R.id.post);
                    view0.setVisibility(View.GONE);
                    View view1 = findViewById(R.id.vote);
                    view1.setVisibility(View.VISIBLE);
                    View view2 = findViewById(R.id.calendar);
                    view2.setVisibility(View.GONE);
                    //화면 구성
                    date = new Date();
                    dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm",java.util.Locale.getDefault());
                    strDate = dateFormat.format(date);
                    content = findViewById(R.id.content_vote);
                    Datepick = findViewById(R.id.date_vote);
                    Datepick.setText(strDate);
                    //날짜 받아오기
                    title = findViewById(R.id.title_vote);
                    titles = title.getText().toString();
                    // Toast.makeText(getApplicationContext(), titles, Toast.LENGTH_SHORT).show();
                    titles = titles.replace("'", "''"); //디비에 저장할때 여기서 가져가기
                    //제목
                    contents = content.getText().toString();
                    contents = contents.replace("'", "''");
                    // Toast.makeText(getApplicationContext(), contents, Toast.LENGTH_SHORT).show();
                    //메모 입력받는 것
                    addbtn = findViewById(R.id.AddBox); //ADD LIST
                    Okbtn = findViewById(R.id.OkBtn); //리스트 추가 OK버튼

                    final EditText input = findViewById(R.id.inputstr);
                    Button.OnClickListener mClickListener1 = new View.OnClickListener(){
                        public void onClick(View v){
                            input.setVisibility(View.VISIBLE);
                            Okbtn.setVisibility(View.VISIBLE);
                        }

                    };
                    //추가시에 보일 수 있게
                    addbtn.setOnClickListener(mClickListener1);
                    Button.OnClickListener mClickListener2 = new View.OnClickListener(){
                        public void onClick(View v){
                            strBox = input.getText().toString();
                            //    Toast.makeText(getApplicationContext(), strBox, Toast.LENGTH_SHORT).show();
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
                            }
                            else {
                                Toast.makeText(getApplicationContext(),"5개까지만 입력할 수 있습니다.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    };
                    Okbtn.setOnClickListener(mClickListener2);
                    //투표 리스트 추가하여 생성
                    Vote vote = new Vote(user, strDate, titles, contents, chkListStr);
                    vote.setName(user);
                    vote.setDate(strDate);
                    vote.setTitle(titles);
                    vote.setContent(contents);
                    vote.setChklist(chkListStr);
                }
                else if(pos == 2){
                    //일정 화면
                    // Toast.makeText(getApplicationContext(), "position 2", Toast.LENGTH_SHORT).show();
                    //테스트
                    View view0 = findViewById(R.id.post);
                    view0.setVisibility(View.GONE);
                    View view1 = findViewById(R.id.vote);
                    view1.setVisibility(View.GONE);
                    View view2 = findViewById(R.id.calendar);
                    view2.setVisibility(View.VISIBLE);
                    //화면 구성
                    CalendarView calendar = findViewById(R.id.calendarview);
                    calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                        public void onSelectedDayChange(CalendarView view, int year,
                                                        int month, int dayOfMonth) {
                            //  Toast.makeText(getApplicationContext(), "" + year + "/" +
                            //        (month + 1) + "/" + dayOfMonth, Toast.LENGTH_SHORT).show();
                            CalDate = findViewById(R.id.date_calendar);
                            Calstr = year + "/" + (month + 1) + "/" + dayOfMonth;
                            CalDate.setText(Calstr);
                        }
                    });//날짜 자동으로 입력받기
                    title = findViewById(R.id.title_calendar);
                    titles = title.getText().toString();

                    titles = titles.replace("'", "''"); //디비에 저장할때 여기서 가져가기
                    //제목
                    content = findViewById(R.id.content_calendar);
                    contents = content.getText().toString();
                    contents = contents.replace("'", "''");
                    schedule = new Schedule(user, strDate, titles, contents,Calstr);
                    schedule.setName(user);
                    schedule.setDate(strDate);
                    schedule.setTitle(titles);
                    schedule.setContent(contents);
                    schedule.setDday(Calstr);
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}


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
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
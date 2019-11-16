package com.example.se_2019.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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

import com.example.se_2019.Note;
import com.example.se_2019.R;
import com.example.se_2019.Schedule;
import com.example.se_2019.Vote;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AddPostActivity extends AppCompatActivity {
    //게시글 작성

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
    private static ArrayList<CheckBox> chkList = new ArrayList<>();
    private  Button saveBtn;
    private  Button setBtn;//다른 화면에서 사용(선택)

    private int pos = 100;
    String titles = "";
    String contents = "";
    private static Note note;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_add_post);
        final TextView textView = findViewById(R.id.textView_spinner);
        Spinner spinner = findViewById(R.id.spinner);
        //스피너 선택

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //final int listnum = position;
                textView.setText(""+parent.getItemAtPosition(position));
                //여기서 array.xml에 설정된 카테고리(게시글, 일정, 투표) 글자 받아옴
                //position 0,1,2로 되어있음
                saveBtn = findViewById(R.id.save);
                Button.OnClickListener mClickListener0 = new View.OnClickListener(){
                    public void onClick(View v){
                        //여기가 전체 저장버튼이라서 각 화면에 대해서 저장되는 정보가 달라야함
                        if(pos == 0) {
                            Toast.makeText(getApplicationContext(), "position 0", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(getApplicationContext(), note.getTitle(), Toast.LENGTH_SHORT).show();
                            Intent intent = getIntent();
                            intent.putExtra("postinfo", note);
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                        else if(pos ==1){

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
                pos = position;

                if(pos ==0){
                    //게시글 화면
                    Toast.makeText(getApplicationContext(), "position 0", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getApplicationContext(), "position 1", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getApplicationContext(), titles, Toast.LENGTH_SHORT).show();
                    titles = titles.replace("'", "''"); //디비에 저장할때 여기서 가져가기
                    //제목
                    contents = content.getText().toString();
                    contents = contents.replace("'", "''");
                    Toast.makeText(getApplicationContext(), contents, Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(getApplicationContext(), strBox, Toast.LENGTH_SHORT).show();
                            chkBox = new CheckBox(getApplicationContext());
                            chkBox.setBackgroundColor(Color.LTGRAY);
                            chkBox.setText(strBox);
                            chkBox.setTextColor(Color.BLACK);
                            chkBox.setVisibility(View.VISIBLE);
                            ll = findViewById(R.id.vote_top);
                            ll.addView(chkBox);
                            chkList.add(chkBox);
                            input.setText("");
                        }
                    };
                    Okbtn.setOnClickListener(mClickListener2);
                    //투표 리스트 추가하여 생성
                    Vote temp = new Vote(user, strDate, titles, contents, chkList);
                }
                else if(pos == 2){
                    //일정 화면
                    Toast.makeText(getApplicationContext(), "position 2", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(getApplicationContext(), "" + year + "/" +
                                    (month + 1) + "/" + dayOfMonth, Toast.LENGTH_SHORT).show();
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
                    Schedule temp = new Schedule(user, Calstr, titles, contents);
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}


        });
    }


}
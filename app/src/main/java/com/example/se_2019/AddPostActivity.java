package com.example.se_2019;


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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AddPostActivity extends AppCompatActivity {
    //게시글 작성

    private String strDate;
    private TextView Datepick;
    private static String Calstr;
    private TextView CalDate;
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
    private  Button setBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        final TextView textView = findViewById(R.id.textView_spinner);
        Spinner spinner = findViewById(R.id.spinner);

//        //날짜 시간 받아오기
//        date = new Date();
//        dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm",java.util.Locale.getDefault());
//        strDate = dateFormat.format(date);
//        content = findViewById(R.id.content);
//        Datepick = findViewById(R.id.date);
//        Datepick.setText(strDate);
//        //메모 입력받는 것
//        String contents = content.getText().toString();
//        contents = contents.replace("'", "''");
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                textView.setText(""+parent.getItemAtPosition(position));
                //여기서 array.xml에 설정된 카테고리(게시글, 일정, 투표) 글자 받아옴
                //position 0,1,2로 되어있음
                saveBtn = findViewById(R.id.save);
                Button.OnClickListener mClickListener0 = new View.OnClickListener(){
                    public void onClick(View v){
                        //여기가 전체 저장버튼이라서 각 화면에 대해서 저장되는 정보가 달라야함
                    }

                };
                saveBtn.setOnClickListener(mClickListener0);
                setBtn = findViewById(R.id.Set);
                Button.OnClickListener mClickListener = new View.OnClickListener(){
                    public void onClick(View v){
                        for(int i = 0; i < chkList.size(); i++){
                            if(chkList.get(i).isChecked()){
                                Toast.makeText(getApplicationContext(),"==="+i+"===",Toast.LENGTH_SHORT).show();
                                ///여기서 체크되면 인덱스 0,1,2~~ 확인해서 저장시켜야함.
                                //chkList.get(i).setClickable(false);
                            }
                        }
                        for(int i = 0; i < chkList.size(); i++) {
                            chkList.get(i).setClickable(false);
                        }
                    }

                };
                setBtn.setOnClickListener(mClickListener);




                if(position ==0){
                    //게시글 화면
                    Toast.makeText(getApplicationContext(), "position 0", Toast.LENGTH_SHORT).show();
                    View view0 = findViewById(R.id.post);
                    view0.setVisibility(View.VISIBLE);
                    View view1 = findViewById(R.id.vote);
                    view1.setVisibility(View.GONE);
                    View view2 = findViewById(R.id.calendar);
                    view2.setVisibility(View.GONE);
                    //날짜 시간 받아오기
                    date = new Date();
                    dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm",java.util.Locale.getDefault());
                    strDate = dateFormat.format(date);
                    content = findViewById(R.id.content_post);
                    Datepick = findViewById(R.id.date_post);
                    Datepick.setText(strDate);
                    //메모 입력받는 것
                    String contents = content.getText().toString();
                    contents = contents.replace("'", "''");

                }
                else if(position ==1){
                    //투표 화면
                    Toast.makeText(getApplicationContext(), "position 1", Toast.LENGTH_SHORT).show();
                    View view0 = findViewById(R.id.post);
                    view0.setVisibility(View.GONE);
                    View view1 = findViewById(R.id.vote);
                    view1.setVisibility(View.VISIBLE);
                    View view2 = findViewById(R.id.calendar);
                    view2.setVisibility(View.GONE);
                    date = new Date();
                    dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm",java.util.Locale.getDefault());
                    strDate = dateFormat.format(date);
                    content = findViewById(R.id.content_vote);
                    Datepick = findViewById(R.id.date_vote);
                    Datepick.setText(strDate);
                    //메모 입력받는 것
                    String contents = content.getText().toString();
                    contents = contents.replace("'", "''");
                    addbtn = findViewById(R.id.AddBox);
                    Okbtn = findViewById(R.id.OkBtn);

                    final EditText input = findViewById(R.id.inputstr);
                    Button.OnClickListener mClickListener1 = new View.OnClickListener(){
                        public void onClick(View v){
                            input.setVisibility(View.VISIBLE);
                            Okbtn.setVisibility(View.VISIBLE);
                        }

                    };
                    addbtn.setOnClickListener(mClickListener1);
                    //strBox = strBox.replace("'", "''");
                    Button.OnClickListener mClickListener2 = new View.OnClickListener(){
                        public void onClick(View v){
                            strBox = input.getText().toString();
                            Toast.makeText(getApplicationContext(), strBox, Toast.LENGTH_SHORT).show();
                            chkBox = new CheckBox(getApplicationContext());
                            chkBox.setText(strBox);
                            chkBox.setVisibility(View.VISIBLE);
                            ll = findViewById(R.id.vote_top);
                            ll.addView(chkBox);
                            chkList.add(chkBox);

                            input.setText("");
                        }

                    };
                    Okbtn.setOnClickListener(mClickListener2);

//                    for(int i = 0; i < chkList.size(); i++){
//                        if(chkList.get(i).isChecked()){
//                            Toast.makeText(getApplicationContext(),"==="+i+"===",Toast.LENGTH_SHORT).show();
//                        }
//                    }


                }
                else if(position == 2){
                    //일정 화면
                    Toast.makeText(getApplicationContext(), "position 2", Toast.LENGTH_SHORT).show();
                    View view0 = findViewById(R.id.post);
                    view0.setVisibility(View.GONE);
                    View view1 = findViewById(R.id.vote);
                    view1.setVisibility(View.GONE);
                    View view2 = findViewById(R.id.calendar);
                    view2.setVisibility(View.VISIBLE);
                    CalendarView calendar = (CalendarView) findViewById(R.id.calendarview);
                    calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                        public void onSelectedDayChange(CalendarView view, int year,
                                                        int month, int dayOfMonth) {
                            Toast.makeText(getApplicationContext(), "" + year + "/" +
                                    (month + 1) + "/" + dayOfMonth, Toast.LENGTH_SHORT).show();
                            CalDate = findViewById(R.id.date_calendar);
                            Calstr = year + "/" + (month + 1) + "/" + dayOfMonth;
                            CalDate.setText(Calstr);
                        }
                    });
                    content = findViewById(R.id.content_calendar);
                    String contents = content.getText().toString();
                    contents = contents.replace("'", "''");
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

}


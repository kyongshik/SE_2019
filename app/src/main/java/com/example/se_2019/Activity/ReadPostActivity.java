package com.example.se_2019.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.se_2019.*;
import com.example.se_2019.R;

import java.util.ArrayList;

public class ReadPostActivity extends AppCompatActivity {


    private TextView name;
    private TextView date;
    private TextView title;
    private TextView content;
    private TextView CalDate;
    private CheckBox chkBox;
    private Button setBtn;
    private Button resultBtn;
    private ArrayList<CheckBox> chkList = new ArrayList<>();

    private LinearLayout llcomment;
    private TextView comment;


    final int REQUESTCODE = 21;
    final int NEW_POST = 22;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_post);
        Intent in = getIntent();
        // Note s = (Note) in.getParcelableExtra("post");
        Post p = in.getParcelableExtra("post");

        //if((Note) in.getParcelableExtra("post") != null){
        if(p.getPosi()==0){
            //Note n = in.getParcelableExtra("post");
            View read_note = findViewById(R.id.Read_Post_note);
            read_note.setVisibility(View.VISIBLE);
            View read_vote = findViewById(R.id.Read_Post_vote);
            read_vote.setVisibility(View.GONE);
            View read_schedule = findViewById(R.id.Read_Post_schedule);
            read_schedule.setVisibility(View.GONE);

            name = findViewById(R.id.Read_Post_noteName);
            date = findViewById(R.id.Read_Post_noteDate);
            title = findViewById(R.id.Read_Post_noteTitle);
            content = findViewById(R.id.Read_Post_noteContent);
            name.setText(p.getName());
            date.setText(p.getWrite_date());
            title.setText(p.getTitle());
            content.setText(p.getContent());

            //Add버튼 클릭이벤트 추가

            resultBtn = findViewById(R.id.Read_Post_Comment_Btn);
            Button.OnClickListener mClickListener1 = new View.OnClickListener(){
                public void onClick(View v) {

                    llcomment = findViewById(R.id.Read_Post_noteList);
                    llcomment.setOrientation(LinearLayout.VERTICAL);
                    comment = new TextView(ReadPostActivity.this);
                    EditText edit = findViewById(R.id.Read_Post_input_Comment);
                    String str = edit.getText().toString();
                    comment.setText(str+"\t\t//"+p.getName());
                    comment.setBackgroundColor(Color.parseColor("#00FFFFFF"));
                    comment.setPadding(20, 10, 10, 10);
                    comment.setTextColor(Color.parseColor("#4B0082"));
                    llcomment.addView(comment);
                    edit.setText("");
                }

            };
            resultBtn.setOnClickListener(mClickListener1);


        }
        else if(p.getPosi()==1){
           // Vote v = in.getParcelableExtra("vote");
            View read_note = findViewById(R.id.Read_Post_note);
            read_note.setVisibility(View.GONE);
            View read_vote = findViewById(R.id.Read_Post_vote);
            read_vote.setVisibility(View.VISIBLE);
            View read_schedule = findViewById(R.id.Read_Post_schedule);
            read_schedule.setVisibility(View.GONE);

            name = findViewById(R.id.Read_Post_voteName);
            date = findViewById(R.id.Read_Post_voteDate);
            title = findViewById(R.id.Read_Post_voteTitle);
            content = findViewById(R.id.Read_Post_voteContent);
            name.setText(p.getName());
            date.setText(p.getWrite_date());
            title.setText(p.getTitle());
            content.setText(p.getContent());
            LinearLayout ll = findViewById(R.id.Read_Post_votetop2);
            String chkString = p.getChklist();
            String[] chkArray = chkString.split("@#");
            for(int i = 0; i <chkArray.length; i++){
                    chkBox = new CheckBox(getApplicationContext());
                    chkBox.setBackgroundColor(Color.LTGRAY);
                    chkBox.setText(chkArray[i]);
                    chkBox.setTextColor(Color.BLACK);
                    chkBox.setVisibility(View.VISIBLE);
                    chkList.add(chkBox);
                    ll.addView(chkBox);
            }
            //체크리스트 추가
//            strBox = input.getText().toString();
//
//            checklist +=strBox+"@#"; //구분자 넣어서 checklist라는 변수에 합침
//            chkBox = new CheckBox(getApplicationContext());
//            chkBox.setBackgroundColor(Color.LTGRAY);
//            chkBox.setText(strBox);
//            chkBox.setTextColor(Color.BLACK);
//            chkBox.setVisibility(View.VISIBLE);
//            if (chkList.size() < 5) {
//                ll = findViewById(R.id.vote_top);
//                ll.addView(chkBox);
//                chkList.add(chkBox);
//                chkListStr.add(strBox);
//                input.setText("");
            ///////////////////////////////////////////////////////////////////////
            setBtn = findViewById(R.id.Read_Post_vote_setBtn); //투표에서 체크한후 '선택'버튼
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

        }
        else if(p.getPosi()==2){
            //Schedule s = in.getParcelableExtra("cal");
            View read_note = findViewById(R.id.Read_Post_note);
            read_note.setVisibility(View.GONE);
            View read_vote = findViewById(R.id.Read_Post_vote);
            read_vote.setVisibility(View.GONE);
            View read_schedule = findViewById(R.id.Read_Post_schedule);
            read_schedule.setVisibility(View.VISIBLE);

            name = findViewById(R.id.Read_Post_scheduleName);
            date = findViewById(R.id.Read_Post_scheduleDate);
            title = findViewById(R.id.Read_Post_scheduleTitle);
            content = findViewById(R.id.Read_Post_scheduleContent);
            CalDate = findViewById(R.id.Read_Post_schedule_CalDate);
            name.setText(p.getName());
            date.setText(p.getWrite_date());
            title.setText(p.getTitle());
            content.setText(p.getContent());
            CalDate.setText(p.getDday());
            //캘린더 표시 추가
        }

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

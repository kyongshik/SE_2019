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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.se_2019.DBRequest.AddVoteRequest;
import com.example.se_2019.Post;
import com.example.se_2019.R;

import org.json.JSONException;
import org.json.JSONObject;

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

    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_post);
        Intent in = getIntent();
        // Note s = (Note) in.getParcelableExtra("post");
        userID = in.getExtras().getString("userID");
        Post p = in.getParcelableExtra("post");
        String userID = in.getStringExtra("userID");
        String roomID = in.getStringExtra("roomID");

        //if((Note) in.getParcelableExtra("post") != null){
        if (p.getPosi() == 0) {
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
            //댓글
            resultBtn = findViewById(R.id.Read_Post_Comment_Btn);
            Button.OnClickListener mClickListener1 = new View.OnClickListener() {
                public void onClick(View v) {
                    llcomment = findViewById(R.id.Read_Post_noteList);
                    llcomment.setOrientation(LinearLayout.VERTICAL);
                    comment = new TextView(ReadPostActivity.this);
                    EditText edit = findViewById(R.id.Read_Post_input_Comment);
                    String str = edit.getText().toString();
                    comment.setText(str + "\t\t//" + p.getName());
                    comment.setBackgroundColor(Color.parseColor("#00FFFFFF"));
                    comment.setPadding(20, 10, 10, 10);
                    comment.setTextColor(Color.parseColor("#4B0082"));
                    llcomment.addView(comment);
                    edit.setText("");
                }

            };
            resultBtn.setOnClickListener(mClickListener1);


        } else if (p.getPosi() == 1) {
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
            for (int i = 0; i < chkArray.length; i++) {
                chkBox = new CheckBox(getApplicationContext());
                chkBox.setBackgroundColor(Color.LTGRAY);
                chkBox.setText(chkArray[i]);
                chkBox.setTextColor(Color.BLACK);
                chkBox.setVisibility(View.VISIBLE);
                chkList.add(chkBox);
                ll.addView(chkBox);
            }
            int num = p.getNum();


            setBtn = findViewById(R.id.Read_Post_vote_setBtn);
            //선택 버튼 클릭시
            Button.OnClickListener mClickListener = new View.OnClickListener() {
                public void onClick(View v) {
                    String votelist = "";
                    for (int i = 0; i < chkList.size(); i++) {
                        if (chkList.get(i).isChecked()) {
                            votelist += String.valueOf(i); //선택한 자리 스트링으로 더해서 votenum이라는 변수에 저장
//                            Toast.makeText(getApplicationContext(), "===" + i + "===", Toast.LENGTH_SHORT).show();
                            ///여기서 체크되면 인덱스 0,1,2~~ 확인해서 저장시켜야함.
                        }
                    }
                    //서버에 추가하는 부분
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                boolean success = jsonObject.getBoolean("success");
                                if (success) { //방등록에 성공한 경우
                                    Toast.makeText(getApplicationContext(), "서버등록에 성공하였습니다~", Toast.LENGTH_LONG).show();
                                } else { //등록에 실패한 경우
                                    Toast.makeText(getApplicationContext(), "서버등록에 실패하였습니다.", Toast.LENGTH_LONG).show();
                                    return;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    AddVoteRequest addVoteRequest = new AddVoteRequest(roomID, num, chkString, votelist, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(ReadPostActivity.this);
                    queue.add(addVoteRequest);

                    for (int i = 0; i < chkList.size(); i++) {
                        chkList.get(i).setClickable(false);
                    }
                }

            };
            setBtn.setOnClickListener(mClickListener);


            //결과 보기 버튼 클릭시
            resultBtn = (Button) findViewById(R.id.Read_Post_vote_ResultBtn);
            resultBtn.setOnClickListener(
                    new View.OnClickListener() {
                        public void onClick(View v) {
                            Intent intent = new Intent(ReadPostActivity.this, ReadResultActivity.class);
                            intent.putExtra("roomID", roomID);
                            intent.putExtra("num", num);
                            intent.putExtra("chklist", p.getChklist());
                            intent.putExtra("userID",userID);
                            startActivity(intent);
                        }
                    }
            );

        } else if (p.getPosi() == 2) {
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
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("userID", userID);
            startActivity(intent);
        }
        if (id == R.id.toolbar_alarm) {
            Toast.makeText(this, "알람버튼을 눌렀습니다", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, content_notice.class);
            intent.putExtra("userID",userID);
            startActivity(intent);
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

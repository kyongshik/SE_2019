package com.example.se_2019;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddRoom extends MainActivity {
    EditText etname,subject_name,prof_name,subject_time;
    Room room;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_room);
        etname = (EditText)findViewById(R.id.etname);
        subject_name = (EditText)findViewById(R.id.subject_name);
        prof_name = (EditText)findViewById(R.id.prof_name);
        subject_time = (EditText)findViewById(R.id.subject_time);
    }
    public void onClick(View v)
    {
        if (v.getId() == R.id.btnCancel){
            finish();
        }
        else
        {
            room = new Room(etname.getText().toString());
            room.setMenu(subject_name.getText().toString());
            room.setMenu(prof_name.getText().toString());
            room.setMenu(subject_time.getText().toString());
            room.setDate(finddate());
            Intent intent = getIntent();
            intent.putExtra("newroom",room);  //Parcelable한 Restaurant를 첨부
            setResult(RESULT_OK,intent);
            finish();
        }
    }

    public String finddate()
    {
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String fmdate = sdf.format(date);
        return fmdate;
    }
}

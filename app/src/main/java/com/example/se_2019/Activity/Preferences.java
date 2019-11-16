package com.example.se_2019.Activity;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.se_2019.Content_Rules;
import com.example.se_2019.R;
import com.example.se_2019.content_notice;

public class Preferences extends PreferenceActivity {
    Preference preference_notice, preference_rule, preference_logout;
    String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //상단바 처리를 위한 코드
        final Intent intent_pre = getIntent();
        userID = intent_pre.getExtras().getString("userID");
        Log.i("PRE","여기들어요냐");

        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        //Log.i("WHY",);
        preference_notice = findPreference("key_notice");
        preference_notice.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent intent = new Intent(Preferences.this, content_notice.class);
                intent.putExtra("userID",userID);
                startActivity(intent);
                return true;
            }
        });
        preference_rule = findPreference("key_rule");
        preference_rule.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent intent = new Intent(Preferences.this, Content_Rules.class);
                intent.putExtra("userID",userID);
                startActivity(intent);
                return true;
            }
        });
        preference_logout = findPreference("key_logout");
        preference_logout.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                new AlertDialog.Builder(Preferences.this)
                        .setTitle("로그아웃").setMessage("로그아웃 하시겠습니까?")
                        .setPositiveButton("로그아웃", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                Intent logoutIntent = new Intent(Preferences.this, LoginActivity.class);
                                logoutIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                logoutIntent.putExtra( "KILL", true );
                                startActivity(logoutIntent);
                            }
                        })
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {

                            }
                        })
                        .show();



                return true;
            }
        });
        //setContentView(R.layout.activity_preferences);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);//여기서 e/menu_main UI를 가져옴
        return true;
    }
    public boolean onOptionsItemSelected (MenuItem item){

        int id = item.getItemId();
        if (id == android.R.id.home) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("userID",userID);
            finish();
            //이때 사용자 아이디 넘겨야함
            startActivity(intent);
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


}

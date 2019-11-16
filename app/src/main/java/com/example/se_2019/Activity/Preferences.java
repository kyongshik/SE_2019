package com.example.se_2019.Activity;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;

import androidx.appcompat.app.AlertDialog;

import com.example.se_2019.Content_Rules;
import com.example.se_2019.R;
import com.example.se_2019.content_notice;

public class Preferences extends PreferenceActivity {
    Preference preference_notice, preference_rule, preference_logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        //Log.i("WHY",);
        preference_notice = findPreference("key_notice");
        preference_notice.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent intent = new Intent(Preferences.this, content_notice.class);
                startActivity(intent);
                return true;
            }
        });
        preference_rule = findPreference("key_rule");
        preference_rule.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent intent = new Intent(Preferences.this, Content_Rules.class);
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


}

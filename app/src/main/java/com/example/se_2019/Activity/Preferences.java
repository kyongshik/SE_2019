package com.example.se_2019.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;

import com.example.se_2019.Content_Rules;
import com.example.se_2019.R;
import com.example.se_2019.content_notice;

public class Preferences extends PreferenceActivity {
    Preference preference_notice, preference_rule;
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
        //setContentView(R.layout.activity_preferences);

    }


}

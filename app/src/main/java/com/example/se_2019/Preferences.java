package com.example.se_2019;



import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;

import com.example.se_2019.R;
import com.example.se_2019.content_notice;

public class Preferences extends PreferenceActivity {
    Preference preference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        //Log.i("WHY",);
        preference = findPreference("key_notice");
        preference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent intent = new Intent(Preferences.this, content_notice.class);
                startActivity(intent);
                return true;
            }
        });
        //setContentView(R.layout.activity_preferences);

    }


}


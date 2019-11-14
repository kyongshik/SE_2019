package com.example.se_2019.Activity;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.example.se_2019.R;

public class Preferences extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);


        //setContentView(R.layout.activity_preferences);

    }


}

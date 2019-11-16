package com.example.se_2019.Activity;

import android.os.AsyncTask;
import android.util.Log;

public class MyAsyncTask extends AsyncTask<String, String, String> {


    @Override
    protected void onPreExecute(){
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
///////////////여기 바꿔
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        if(result != null){
            Log.d("ASYNC", "result = " + result);
        }

    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

}


package com.example.r9_bl.bookshelf.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import com.example.r9_bl.bookshelf.R;

public class LandingActivity extends Activity {

    private boolean loggedOn;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        progressBar = findViewById(R.id.loading_bar);

        //Should be replace with cache login on device
        loggedOn = true;

        new MyTask().execute();
    }

    private void nextPage () {
        if(loggedOn) {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();
        }else {
            Intent intent = new Intent(this, FrontActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private class MyTask extends AsyncTask<Void,Integer,Void> {

        @Override
        protected void onPreExecute() {}

        //Should be real checking login/account progress
        @Override
        protected Void doInBackground(Void... params) {
            for (int i = 1; i <= 100; i+=8) {
                // call to modify UI
                publishProgress(i);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    // don,t use Toast (no UI modify)
                    Log.e("ERROR", "Thread Interrupted in AsyncTask");
                }
            };
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... parameters) {
            progressBar.setProgress(parameters[0]);
        }

        @Override
        protected void onPostExecute(Void parameters) {
            nextPage();
        }
    }

}



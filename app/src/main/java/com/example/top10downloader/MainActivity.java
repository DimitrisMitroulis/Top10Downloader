package com.example.top10downloader;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "main activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hideNavBar();
        Log.d(TAG, "onCreate: starting AsyncTask");
        DownloadData downloadData = new DownloadData();
        downloadData.execute("URL");
        Log.d(TAG, "onCreate: Done");


    }

    private void hideNavBar() {
        Objects.requireNonNull(getSupportActionBar()).hide();
        this.getWindow().getDecorView()
                .setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                );
    }

    private class DownloadData extends AsyncTask<String, Void, String>

    {//String is url to rss feed , where void is for displaying progress bars,  String for return
        private static final String TAG = "Download";

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d(TAG, "onPostExecute: parameter is "+s);
        }


        @Override
        protected String doInBackground(String... strings) {
            Log.d(TAG, "doInBackground:  starts with "+ strings[0]);
            return "do in background Completed";
        }
    }


}
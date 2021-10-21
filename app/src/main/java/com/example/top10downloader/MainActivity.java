package com.example.top10downloader;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

import static java.lang.Thread.sleep;

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

    private class DownloadData extends AsyncTask<String, Void, String> {//String is url to rss feed , where void is for displaying progress bars,  String for return
        private static final String TAG = "Download";

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.d(TAG, "onPostExecute: parameter is " + s);
        }


        @Override
        protected String doInBackground(String... strings) {
            Log.d(TAG, "doInBackground:  starts with " + strings[0]);
            String RssFeed = downloadFXML(strings[0]);
            if (RssFeed == null) {
                Log.d(TAG, "doInBackground: Error downloading");

            }
            return RssFeed;
        }

        private String downloadFXML(String urlPath) {
            StringBuilder xmlResponse = new StringBuilder();

            try {
                URL url = new URL(urlPath);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                int response = connection.getResponseCode();
                Log.d(TAG, "downloadFXML: The response code is " + response);
                InputStream inputStream = connection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader reader = new BufferedReader(inputStreamReader);

            } catch (MalformedURLException e) {
                Log.d(TAG, "downloadFXML: Invalid URL" + e.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

    }
}
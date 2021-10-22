package com.example.top10downloader;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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
    private Button button;
    private int buttonState;
    private static final String  STATE_BUTTON = "buttonState";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hideNavBar();

        button =  findViewById(R.id.button);
        View.OnClickListener ButtonListener = view -> {
            Log.d(TAG, "onCreate: starting AsyncTask");
            DownloadData downloadData = new DownloadData();
            downloadData.execute("URL");
            Log.d(TAG, "onCreate: Done");
            button.setVisibility(View.GONE);//View.VISIBLE
            buttonState = View.GONE;

        };

        button.setOnClickListener(ButtonListener);

    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        buttonState = savedInstanceState.getInt(STATE_BUTTON);
        button.setVisibility(buttonState);


    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(STATE_BUTTON,buttonState);
        super.onSaveInstanceState(outState);
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
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            } catch (MalformedURLException e) {
                Log.d(TAG, "downloadFXML: Invalid URL" + e.getMessage());
            } catch (IOException e) {
                Log.d(TAG, "downloadFXML: Error loading data:"+e.getMessage());
            }
            return null;
        }

    }
}
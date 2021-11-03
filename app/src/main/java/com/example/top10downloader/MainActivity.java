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
import java.util.Arrays;
import java.util.Objects;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "main activity";
    private Button button;
    private int buttonState;
    private static final String STATE_BUTTON = "buttonState";
    
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        buttonState = savedInstanceState.getInt(STATE_BUTTON);
        button.setVisibility(buttonState);
    }//to restore state of button

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(STATE_BUTTON, buttonState);
        super.onSaveInstanceState(outState);
    }//to save state of button
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hideNavBar();
        Log.d(TAG, "onCreate: starting AsyncTask");
        button = findViewById(R.id.button);
        View.OnClickListener ButtonListener = view -> {

            DownloadData downloadData = new DownloadData();
            downloadData.execute("http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=10/xml");

            button.setVisibility(View.GONE);
            buttonState = View.GONE;
        };
        button.setOnClickListener(ButtonListener);
        Log.d(TAG, "onCreate: Done");
     }

    
    private void hideNavBar() {
        Objects.requireNonNull(getSupportActionBar()).hide();
        this.getWindow().getDecorView()
                .setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                );
    }

    private static class DownloadData extends AsyncTask<String, Void, String> {//String is url to rss feed , where void is for displaying progress bars, String for return
        private static final String TAG = "Download";

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //Log.d(TAG, "onPostExecute: parameter is " + s);
            ParseApplications parseApplications = new ParseApplications();
            parseApplications.parse(s); //s is the xml file
        }


        @Override
        protected String doInBackground(String... strings) {
            //Log.d(TAG, "doInBackground:  starts with " + strings[0]);
            String RssFeed = downloadFXML(strings[0]);
            if (RssFeed == null) {
                Log.e(TAG, "doInBackground: Error downloading");
            }
            return RssFeed;
        }

        private String downloadFXML(String urlPath) {//the reuturn from this method goes to onPostExecute
            StringBuilder xmlResult = new StringBuilder();

            try {
                URL url = new URL(urlPath);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                int response = connection.getResponseCode();
                Log.d(TAG, "downloadXML: The response code was " + response);
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                int charsRead;
                char[] inputBuffer = new char[500];
                while(true) {
                    charsRead = reader.read(inputBuffer);
                    if(charsRead < 0)
                        break;

                    if(charsRead > 0) {
                        //xmlResult.append(String.copyValueOf(inputBuffer, 0, charsRead));
                        xmlResult.append(String.copyValueOf(inputBuffer,0,charsRead));

                    }
                }
                reader.close();//when buffered reader closes, input Stream reader and input stream closes as well

                return xmlResult.toString();

            } catch(MalformedURLException e) {
                Log.e(TAG, "downloadXML: Invalid URL " + e.getMessage());
            } catch(IOException e) {
                Log.e(TAG, "downloadXML: IO Exception reading data: " + e.getMessage());
            } catch(SecurityException e) {
                Log.e(TAG, "downloadXML: Security Exception.  Needs permisson? " + e.getMessage());
            }
            return null;
        }

    }


}

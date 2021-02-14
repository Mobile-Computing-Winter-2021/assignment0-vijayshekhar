package com.example.helloworld.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.helloworld.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class DownloadMusicActivity extends AppCompatActivity {

    TextView statusText;
    TextView internetStatus;
    TextView downloadStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_music);


        statusText = findViewById(R.id.status_msg);
        internetStatus = findViewById(R.id.inernet_status_msg);
        downloadStatus = findViewById(R.id.download_status_msg);


        internetStatus.setText("Checking Internet Status...");

        // if internet connection is available
        if (connectionEstablished()) {
            internetStatus.setText("Internet Connection Established...");
            //start downloading task
            new SongDownloadTask().execute("http://faculty.iiitd.ac.in/~mukulika/s1.mp3");
        } else {

            internetStatus.setText("No Internet Connection...");
            statusText.setText("Can't proceed with download");
            downloadStatus.setText("Can't proceed with download");

        }
    }


    // Checking the connection availability
    public boolean connectionEstablished() {

        // constants for mobile connection and wifi connection
        boolean mobileconnection_status = false, wificonnection_status = false, status = false;

        // class objects
        ConnectivityManager conObjc = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkStatus = conObjc.getActiveNetworkInfo();


        if (networkStatus != null) {
            // connected to the internet
            switch (networkStatus.getType()) {

                // case for mobile data
                case ConnectivityManager.TYPE_MOBILE:
                    mobileconnection_status = true;
                    break;
                // case for wifi connection
                case ConnectivityManager.TYPE_WIFI:
                    wificonnection_status = true;
                    break;

                default:
                    break;
            }
        }

        // if any mode of connection is available
        return  mobileconnection_status || wificonnection_status;

    }


    // download song using Async task <-- Asyc taks is depricated-- >
    private class SongDownloadTask extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... urls) {
            try {
                Log.i("DEBUG_TAG", "in doInBackground" + urls[0]);

                return downloadSongLink(urls[0]);
            } catch (IOException e) {
                return "Check the url, cannot download song: "+ urls[0];
            }
        }

        @Override
        protected void onPostExecute(String result) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    playMusicFile();
                }
            });
        }

    }


    private void playMusicFile() {
        Intent intent = new Intent();
        intent.putExtra("msg", "success");
        setResult(202, intent);
    }





    private String downloadSongLink(String dataLink) throws IOException {
        InputStream ipStream = null;
        statusText.setText("Starting Download...");

        try {

            Log.d("DEBUG_TAG", "In download url: ");
            URL link = new URL(dataLink);

            HttpURLConnection conObj = (HttpURLConnection) link.openConnection();

            conObj.setRequestMethod("GET");

            conObj.setConnectTimeout(21000 );
            conObj.setReadTimeout(21000 );


            conObj.setDoInput(true);


            // Starts the query
            conObj.connect();

            int response = conObj.getResponseCode();
            if (response == 200) {
                statusText.setText("Connection Established...");
            }

            downloadStatus.setText("Starting download ");
            Log.d("DEBUG_TAG", "The response received: " + response);

            // static data initialisations
            String fname = "song_download.mp3";  // file name
            byte[] data = new byte[4096];   // raw data
            int l = 0;  // count variable

            // input stream data
            ipStream = conObj.getInputStream();

            // writing data from input stream into file song_download
            FileOutputStream fileopStream = openFileOutput(fname, Context.MODE_PRIVATE);

            // looping for writing data
            while ((l = ipStream.read(data)) != -1) {
                fileopStream.write(data, 0, l);
            }

            String [] dataList = DownloadMusicActivity.this.fileList();
            Log.d("DEBUG_TAG", dataList[0]);

            downloadStatus.setText("File Downloaded");
            return "success";

        } finally {
            if (ipStream != null) {
                ipStream.close();
            }
        }
    }





}
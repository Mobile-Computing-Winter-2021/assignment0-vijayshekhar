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
        if (haveNetworkConnection()) {
            internetStatus.setText("Internet Connection Established...");
            //continue
            new DownloadWebpageTask().execute("http://faculty.iiitd.ac.in/~mukulika/s1.mp3");
        } else {
            internetStatus.setText("No Internet Connection...");
            statusText.setText("Can't proceed with download");
            downloadStatus.setText("Can't proceed with download");
//            this.finish();
        }
    }


    private class DownloadWebpageTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            try {
                Log.i("DEBUG_TAG", "in doInBackground" + urls[0]);
                return downloadUrl(urls[0]);
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
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


    private String downloadUrl(String myurl) throws IOException {
        InputStream is = null;
        statusText.setText("Starting Download...");
        try {
            Log.d("DEBUG_TAG", "In download url: ");
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection)
                    url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds
             */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            if(response==200){
                statusText.setText("Connection Established...");
            }

            downloadStatus.setText("File Downloaded and saved as myfile.mp3");
            Log.d("DEBUG_TAG", "The response is: " + response);
            is = conn.getInputStream();
            // write inputStream into file
            String filename = "myfile.mp3";
            FileOutputStream fos = openFileOutput(filename, Context.MODE_PRIVATE);
            byte[] buffer = new byte[4096];
            int len1 = 0;

            while ((len1 = is.read(buffer)) != -1) {
                fos.write(buffer, 0, len1);
            }
            // Makes sure that the InputStream is closed after the app is
            // finished using it.
            String[] files = DownloadMusicActivity.this.fileList();
            Log.d("DEBUG_TAG", files[0]);
            downloadStatus.setText("File Downloaded and saved as myfile.mp3");
            return "success";
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }


    public boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null) {
            // connected to the internet
            switch (netInfo.getType()) {
                case ConnectivityManager.TYPE_WIFI:
                    haveConnectedWifi = true;
                    break;
                case ConnectivityManager.TYPE_MOBILE:
                    haveConnectedMobile = true;
                    break;
                default:
                    break;
            }
        }
        return haveConnectedWifi || haveConnectedMobile;
    }


}
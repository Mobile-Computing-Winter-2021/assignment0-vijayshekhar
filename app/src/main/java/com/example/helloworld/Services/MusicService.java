package com.example.helloworld.Services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.helloworld.Activities.MainActivity;
import com.example.helloworld.R;

import java.io.File;

public class MusicService extends Service {

    private MediaPlayer player;
    private static final String TAG = "Music_Service";
    public static final String CHANNEL_ID = "channel_id_music";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate() , service started...");

    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        boolean playDownloaded = intent.getBooleanExtra("playDownloaded", false);
        String ip_data = intent.getStringExtra("notificationMsg");
        Log.i(TAG, "Reached in start command");


        notificationGenerate();
        Intent intent_notif = new Intent(this, MainActivity.class);
        PendingIntent intent_pending = PendingIntent.getActivity(this, 0, intent_notif, 0);


        Notification foreground_service_visible = new NotificationCompat.Builder(this, CHANNEL_ID) .setContentTitle("Foreground Service visible").setContentIntent(intent_pending) .setContentText(ip_data).setSmallIcon(R.drawable.ic_baseline_play_arrow_24).build();

        // starting service on foreground
        startForeground(1, foreground_service_visible);
        if (playDownloaded) {
            try {
                File directory = getFilesDir();
                String[] files = fileList();
                File file = new File(directory, files[0]);
                player = new  MediaPlayer();
                player.setDataSource(file.getAbsolutePath());
                player.prepare();
                player.start();
            } catch (Exception e) {
                Log.e("ERROR_TAG", e.getMessage());
                e.printStackTrace();
            }
        } else {
            player = MediaPlayer.create(this, R.raw.sample_audio);
            player.setLooping(true); // Set looping
            player.setVolume(100, 100);
            player.start();
        }

        return Service.START_NOT_STICKY;
    }


//    public void onPause() {
//        if (player != null && player.isPlaying()) {
//            player.pause();
//        }
//        Log.i(TAG, "onPause(), player paused");
//    }

    @Override
    public void onDestroy() {

        // releasing all resources
        player.release();
        Log.i(TAG, "onDestroy() , service stopped and resources released...");
    }

    // to create notification
    private void notificationGenerate() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);

            manager.createNotificationChannel(notificationChannel);
        }
    }


}

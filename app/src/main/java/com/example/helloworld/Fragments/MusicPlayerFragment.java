package com.example.helloworld.Fragments;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.helloworld.Activities.DownloadMusicActivity;
import com.example.helloworld.Activities.MainActivity;
import com.example.helloworld.R;
import com.example.helloworld.Services.MusicService;

public class MusicPlayerFragment extends Fragment {

    public MusicPlayerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_music_player, container, false);
    }

    TextView status;
    boolean fileDownloaded = false;
    boolean isPlaying = false;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        SwitchCompat playPauseButton = view.findViewById(R.id.play_pause_switch);
        SwitchCompat downloadedPlayPauseButton = view.findViewById(R.id.downloaded_file_play_pause_switch);
        status = view.findViewById(R.id.status);


        Button downloadButton = view.findViewById(R.id.download_song_button);
        downloadButton.setOnClickListener(v -> {
            startActivityForResult(new Intent(getContext(), DownloadMusicActivity.class), 201);
        });



        downloadedPlayPauseButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (fileDownloaded) {
                Intent serviceIntent = new Intent(getContext(), MusicService.class);
                if (isChecked) {
                    if (isPlaying) {
                        getContext().stopService(serviceIntent);
                        playPauseButton.setButtonDrawable(R.drawable.ic_baseline_play_arrow_24);
                        playPauseButton.setChecked(false);
                    }
                    isPlaying = true;
                    status.setText("Playing Downloaded file");
                    serviceIntent.putExtra("playDownloaded", true);
                    serviceIntent.putExtra("notificationMsg", "Playing downloaded file");
                    ContextCompat.startForegroundService(getContext(), serviceIntent);
                    downloadedPlayPauseButton.setButtonDrawable(R.drawable.ic_baseline_stop_24);
                } else {
                    isPlaying = false;
                    status.setText("Stopped Playing Downloaded file");
                    getContext().stopService(serviceIntent);
                    downloadedPlayPauseButton.setButtonDrawable(R.drawable.ic_baseline_play_arrow_24);
                }
            }
        });


        playPauseButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Intent serviceIntent = new Intent(getContext(), MusicService.class);
            if (isChecked) {
                if (isPlaying) {
                    getContext().stopService(serviceIntent);
                    downloadedPlayPauseButton.setButtonDrawable(R.drawable.ic_baseline_play_arrow_24);
                    downloadedPlayPauseButton.setChecked(false);
                }
                isPlaying = true;
                status.setText("Playing test file");
                serviceIntent.putExtra("playDownloaded", false);
                serviceIntent.putExtra("notificationMsg", "Playing test file");
                ContextCompat.startForegroundService(getContext(), serviceIntent);
                playPauseButton.setButtonDrawable(R.drawable.ic_baseline_stop_24);
            } else {
                isPlaying = false;
                status.setText("Stopped Playing test file");
                getContext().stopService(serviceIntent);
                playPauseButton.setButtonDrawable(R.drawable.ic_baseline_play_arrow_24);
            }
        });

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 201 && resultCode == 202) {
            if (data != null && data.getStringExtra("msg").equals("success")) {
                fileDownloaded = true;
            }
        }
    }


    @Override
    public void onDestroy() {
        if (isPlaying) {
            Intent serviceIntent = new Intent(getContext(), MusicService.class);
            getContext().stopService(serviceIntent);
        }
        super.onDestroy();
    }
}
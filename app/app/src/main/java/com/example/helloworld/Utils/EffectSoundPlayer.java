package com.example.helloworld.Utils;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.SoundPool;

import com.example.helloworld.R;

import java.util.HashMap;

public class EffectSoundPlayer {
    public static final int SAMPLE_MUSIC = R.raw.sample_audio;

    private static SoundPool soundPool;
    private static HashMap soundPoolMap;
    /** Populate the SoundPool*/
    public static void initSounds(Context context) {
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
            .build();
        soundPool = new SoundPool.Builder().setMaxStreams(2).setAudioAttributes(audioAttributes).build();
        soundPoolMap = new HashMap(1);
        soundPoolMap.put( SAMPLE_MUSIC, soundPool.load(context, R.raw.sample_audio, 1) );
    }


    public static void release(){
        soundPool.release();
    }


    public static void playSound(Context context, int soundID) {
        if(soundPool == null || soundPoolMap == null){
            initSounds(context);
        }
        float volume = 1;
        try {
            soundPool.play((Integer) soundPoolMap.get(soundID), volume, volume, 1, 0, 1f);
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }


}
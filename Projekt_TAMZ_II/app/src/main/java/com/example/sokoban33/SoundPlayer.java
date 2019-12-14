package com.example.sokoban33;

import android.app.Activity;
import android.media.MediaPlayer;

public class SoundPlayer {
    private Activity activity;

    public SoundPlayer(Activity activity){
        this.activity = activity;
    }

    public void playSound(int sound){
        MediaPlayer mp = MediaPlayer.create(this.activity, sound);
        mp.start();
    }
}

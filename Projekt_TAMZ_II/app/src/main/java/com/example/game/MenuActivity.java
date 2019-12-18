package com.example.game;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MenuActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        SoundPlayer soundPlayer = new SoundPlayer(this);
        soundPlayer.playSound(R.raw.song);
        // Commet commet = new Commet(10, 10, 40, 50);
    }

    public void continueCampain(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void quit(View view){
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory( Intent.CATEGORY_HOME );
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
    }

    public void heroSettings(View view){
        Intent intent = new Intent(this, HeroActivity.class);
        startActivity(intent);
    }

    public void scoreBoardAct(View view){
        Intent intent = new Intent(this, ScoreBoardActivity.class);
        startActivity(intent);
    }

    public void chooseLevel(View view){
        Intent intent = new Intent(this, ChooseLevelActivity.class);
        startActivity(intent);
    }
}

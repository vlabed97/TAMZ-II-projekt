package com.example.game;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class WinScreenActivity extends AppCompatActivity {

    public TextView scoreTextView;
    public int score;
    public int mapId;
    private String mapName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win_screen);
        scoreTextView = findViewById(R.id.textViewScore);
        Intent intent = getIntent();
        score = intent.getIntExtra("score", 0);
        mapId = intent.getIntExtra("map_id", 0);
        scoreTextView.setText(score + "");
        GameLoader gameLoader = new GameLoader(this);
        ArrayList<String> maps = gameLoader.getAllMaps();
        mapName = maps.get(mapId);
    }

    public void saveScoreButton(View view){
        SharedPreferences pref = getApplicationContext().getSharedPreferences("HeroPref", 0);
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        databaseHelper.insertScore(mapName, pref.getString("name", null), score);
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }

    public void exitButton(View view){
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }
}

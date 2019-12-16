package com.example.sokoban33;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class WinScreenActivity extends AppCompatActivity {

    public TextView scoreTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win_screen);
        scoreTextView = findViewById(R.id.textViewScore);
    }

    public void saveScoreButton(View view){
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        databaseHelper.insertScore("sharedPreferencesLvl", pref.getString("name", null), 100);
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }

    public void exitButton(View view){
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }
}

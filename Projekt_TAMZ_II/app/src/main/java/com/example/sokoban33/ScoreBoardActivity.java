package com.example.sokoban33;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ScoreBoardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_board);
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        /*
        databaseHelper.insertScore("Level 1", "Boiii", 100);
        databaseHelper.insertScore("Level 2", "Boiii", 120);
        databaseHelper.insertScore("Level 3", "Boiii", 90);
        */
        Cursor scoreCursor = databaseHelper.selectScore();
        ArrayList<String> scoreList = new ArrayList<>();
        while (scoreCursor.moveToNext()){
            scoreList.add(scoreCursor.getString(1) + " " + scoreCursor.getString(2) + " score: " + scoreCursor.getInt(3));
        }
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, scoreList);
        ListView listViewScore = findViewById(R.id.listViewScore);
        listViewScore.setAdapter(arrayAdapter);
    }
}
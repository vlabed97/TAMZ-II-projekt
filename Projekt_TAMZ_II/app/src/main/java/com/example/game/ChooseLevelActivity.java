package com.example.game;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ChooseLevelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_level);
        GameLoader gameLoader = new GameLoader(this);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, gameLoader.getAllMaps());
        ListView listViewScore = findViewById(R.id.mapList);
        listViewScore.setAdapter(arrayAdapter);
        listViewScore.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startGame(i);
            }
        });
    }

    private void startGame(int gameId){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("game_id", gameId);
        startActivity(intent);
    }
}

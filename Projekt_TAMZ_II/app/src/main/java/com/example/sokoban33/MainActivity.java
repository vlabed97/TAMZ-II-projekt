package com.example.sokoban33;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList heroes;
    private SokoView sokoView;
    private int turnNumber;

    private void turnCounter(){
        turnNumber++;
        if (turnNumber >= heroes.size()){
            turnNumber = 0;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sokoView = findViewById(R.id.sokoView);

        turnNumber = 0;

        heroes = new ArrayList<>();
        heroes.add(new Creature(SokoView.HERO, "Bunny"));
        heroes.add(new Creature(SokoView.ENEMY, "Fox"));
    }

    public void moveLeft(View view){
        if (heroes.get(turnNumber).getClass() == Creature.class){
            Creature hero = (Creature)heroes.get(turnNumber);
            hero.moveLeft();
            turnCounter();
            sokoView.invalidate();
        }
    }

    public void moveRight(View view){
        if (heroes.get(turnNumber).getClass() == Creature.class) {
            Creature hero = (Creature)heroes.get(turnNumber);
            hero.moveRight();
            turnCounter();
            sokoView.invalidate();
        }
    }

    public void moveUp(View view){
        if (heroes.get(turnNumber).getClass() == Creature.class) {
            Creature hero = (Creature)heroes.get(turnNumber);
            hero.moveUp();
            turnCounter();
            sokoView.invalidate();
        }
    }

    public void moveDown(View view){
        if (heroes.get(turnNumber).getClass() == Creature.class) {
            Creature hero = (Creature)heroes.get(turnNumber);
            hero.moveDown();
            turnCounter();
            sokoView.invalidate();
        }
    }
}

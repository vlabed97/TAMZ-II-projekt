package com.example.sokoban33;

import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Creature> creatures;
    private SokoView sokoView;
    private int turnNumber;
    private int selectedSpell;
    private Button spell_1_button;
    private Button spell_2_button;
    private Button spell_3_button;
    private Button spell_4_button;


    private void turnCounter(){
        sokoView.invalidate(); // REDRAWS SCENE
        turnNumber++;
        if (turnNumber >= creatures.size()){
            turnNumber = 0;
        }
        TextView turnText = findViewById(R.id.textTurn);
        turnText.setText(creatures.get(turnNumber).name + " " + creatures.get(turnNumber).getHp() + "HP");
        initSpells();
    }

    private void responseToPlayer(){
        if (creatures.get(turnNumber).getClass() == Enemy.class
                || creatures.get(turnNumber).getClass() == Minion.class){
            Log.i("mojLog", "MINIONS TURN");
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Enemy enemy = (Enemy) creatures.get(turnNumber);
                    enemy.move(creatures); // do something
                    turnCounter(); // pass turn to player
                }
            }, 1500);
            Log.i("mojLog", "MINIONS TURN END");
        }
    }

    private void removeDeadCreatures(){
        Creature creatureToRemove = null;
        for(Creature creature: creatures){
            if (creature.dead){
                creatureToRemove = creature;
            }
        }
        if (creatureToRemove != null){
            creatures.remove(creatureToRemove);
        }
    }

    private Creature getCreatureOnPosition(int position){
        for (Creature creature: creatures){
            if (creature.getPosition() == position){
                return creature;
            }
        }
        return null;
    }

    private void initSpells(){
        if (creatures.get(turnNumber).spells.size() > 0) {
            spell_1_button.setText(creatures.get(turnNumber).spells.get(0));
        }
        else{
            spell_1_button.setText("no spell");
        }
        if (creatures.get(turnNumber).spells.size() > 1) {
            spell_2_button.setText(creatures.get(turnNumber).spells.get(1));
        }
        else{
            spell_2_button.setText("no spell");
        }
        if (creatures.get(turnNumber).spells.size() > 2) {
            spell_3_button.setText(creatures.get(turnNumber).spells.get(2));
        }
        else{
            spell_3_button.setText("no spell");
        }
        if (creatures.get(turnNumber).spells.size() > 3) {
            spell_4_button.setText(creatures.get(turnNumber).spells.get(3));
        }
        else{
            spell_4_button.setText("no spell");
        }
    }

    private void initMove(){
        turnCounter();
        responseToPlayer();
        removeDeadCreatures();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        selectedSpell = 0;

        spell_1_button = findViewById(R.id.buttonSpell1);
        spell_2_button = findViewById(R.id.buttonSpell2);
        spell_3_button = findViewById(R.id.buttonSpell3);
        spell_4_button = findViewById(R.id.buttonSpell4);

        sokoView = findViewById(R.id.sokoView);
        sokoView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    int x = (int) event.getX();
                    int y = (int) event.getY();
                    Creature creature = getCreatureOnPosition(sokoView.getClickedPosition(x, y));
                    if (creature != null){
                        Creature caster = creatures.get(turnNumber);
                        if (caster.spells.get(selectedSpell) == "Fireball"){
                            Mage mage = (Mage)caster;
                            mage.fireball(creature);
                        }
                    }
                    initMove();
                }
                return true;
            }
        });

        turnNumber = 0;

        creatures = new ArrayList<>();
        creatures.add(new Mage(SokoView.HERO, "Bunny", sokoView));
        creatures.add(new Hero(SokoView.ENEMY, "Fox", sokoView));
        creatures.add(new Minion(SokoView.MINION, "Minion", creatures, sokoView));

        initSpells();
    }

    public void selectedSpell_1(View view){
        selectedSpell = 0;
        spell_1_button.setBackgroundColor(getResources().getColor(R.color.color_1));
        spell_2_button.setBackgroundColor(getResources().getColor(R.color.color_2));
        spell_3_button.setBackgroundColor(getResources().getColor(R.color.color_2));
        spell_4_button.setBackgroundColor(getResources().getColor(R.color.color_2));
    }

    public void selectedSpell_2(View view){
        selectedSpell = 1;
        spell_1_button.setBackgroundColor(getResources().getColor(R.color.color_2));
        spell_2_button.setBackgroundColor(getResources().getColor(R.color.color_1));
        spell_3_button.setBackgroundColor(getResources().getColor(R.color.color_2));
        spell_4_button.setBackgroundColor(getResources().getColor(R.color.color_2));
    }

    public void selectedSpell_3(View view){
        selectedSpell = 2;
        spell_1_button.setBackgroundColor(getResources().getColor(R.color.color_2));
        spell_2_button.setBackgroundColor(getResources().getColor(R.color.color_2));
        spell_3_button.setBackgroundColor(getResources().getColor(R.color.color_1));
        spell_4_button.setBackgroundColor(getResources().getColor(R.color.color_2));
    }

    public void selectedSpell_4(View view){
        selectedSpell = 3;
        spell_1_button.setBackgroundColor(getResources().getColor(R.color.color_2));
        spell_2_button.setBackgroundColor(getResources().getColor(R.color.color_2));
        spell_3_button.setBackgroundColor(getResources().getColor(R.color.color_2));
        spell_4_button.setBackgroundColor(getResources().getColor(R.color.color_1));
    }

    public void moveLeft(View view){
        if (creatures.get(turnNumber).getClass() == Hero.class
                || creatures.get(turnNumber).getClass() == Mage.class){
            Hero hero = (Hero) creatures.get(turnNumber);
            hero.moveLeft();
            initMove();
        }
    }

    public void moveRight(View view){
        if (creatures.get(turnNumber).getClass() == Hero.class
                || creatures.get(turnNumber).getClass() == Mage.class) {
            Hero hero = (Hero)creatures.get(turnNumber);
            hero.moveRight();
            initMove();
        }
    }

    public void moveUp(View view){
        if (creatures.get(turnNumber).getClass() == Hero.class
                || creatures.get(turnNumber).getClass() == Mage.class) {
            Hero hero = (Hero)creatures.get(turnNumber);
            hero.moveUp();
            initMove();
        }
    }

    public void moveDown(View view){
        if (creatures.get(turnNumber).getClass() == Hero.class
                || creatures.get(turnNumber).getClass() == Mage.class) {
            Hero hero = (Hero)creatures.get(turnNumber);
            hero.moveDown();
            initMove();
        }
    }
}

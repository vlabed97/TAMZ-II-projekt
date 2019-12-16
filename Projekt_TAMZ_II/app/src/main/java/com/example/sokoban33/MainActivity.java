package com.example.sokoban33;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
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
    private GameView gameView;
    private int turnNumber;
    private int selectedSpell;
    private Button spell_1_button;
    private Button spell_2_button;
    private GameLoader gameLoader;
    private int actualMapId = 0;


    private void turnCounter(){
        gameView.invalidate(); // REDRAWS SCENE
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
                    try{
                        Enemy enemy = (Enemy) creatures.get(turnNumber);
                        enemy.move(creatures); // do something
                        turnCounter(); // pass turn to player
                    }
                    catch (IndexOutOfBoundsException e){
                        Log.i("mojLog", "MINIONS is dead");
                    }
                    catch (ClassCastException e){
                        Log.i("mojLog", "Unknown error.. to be solved");
                    }
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
            spell_1_button.setText(creatures.get(turnNumber).spells.get(0).name);
        }
        else{
            spell_1_button.setText("no spell");
        }
        if (creatures.get(turnNumber).spells.size() > 1) {
            spell_2_button.setText(creatures.get(turnNumber).spells.get(1).name);
        }
        else{
            spell_2_button.setText("no spell");
        }
    }

    private void setupNewGame(){
        GameView.level = gameLoader.getMap(this.actualMapId);
        selectedSpell = 0;
        turnNumber = 0;
        creatures = new ArrayList<>();
        SharedPreferences pref = getApplicationContext().getSharedPreferences("HeroPref", 0);
        String heroClass = pref.getString("class", null);
        if(heroClass.equals("mage")){
            creatures.add(new Mage(GameView.HERO, "Bunny", gameView));
        }
        else if(heroClass.equals("warrior")){
            creatures.add(new Warrior(GameView.ENEMY, "Fox", gameView));
        }
        creatures.add(new Minion(GameView.MINION, "Minion", creatures, gameView));
        initSpells();
    }

    private void victory(){
        boolean isWin = true;
        for(Creature creature: creatures){
            if(creature.name == "Minion"){
                isWin = false;
            }
        }
        if(isWin){
            actualMapId++;
            if(actualMapId == 3){
                Intent intent = new Intent(this, WinScreenActivity.class);
                startActivity(intent);
            }
            else{
                try{
                    GameView.level = gameLoader.getMap(this.actualMapId);
                    setupNewGame();
                }
                catch(Exception e){}
            }
        }
    }

    private void initMove(){
        turnCounter();
        responseToPlayer();
        removeDeadCreatures();
        victory();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        actualMapId = intent.getIntExtra("game_id", 0);
        gameLoader = new GameLoader(MainActivity.this);

        spell_1_button = findViewById(R.id.buttonSpell1);
        spell_2_button = findViewById(R.id.buttonSpell2);
        gameView = findViewById(R.id.sokoView);

        gameView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN
                        && creatures.get(turnNumber).getClass() != Minion.class){
                    int x = (int) event.getX();
                    int y = (int) event.getY();
                    Creature creature = getCreatureOnPosition(gameView.getClickedPosition(x, y));
                    MediaPlayer click = MediaPlayer.create(v.getContext(), R.raw.click);
                    MediaPlayer swing = MediaPlayer.create(v.getContext(), R.raw.swing);
                    if (creature != null){
                        Creature caster = creatures.get(turnNumber);
                        if (caster.spells.get(selectedSpell).name == "Comet"){
                            Mage mage = (Mage)caster;
                            mage.comet(creature);
                            swing.start();
                        }
                        else if (caster.spells.get(selectedSpell).name == "Stab"){
                            Mage mage = (Mage)caster;
                            mage.stab(creature);
                            swing.start();
                        }
                        else if (caster.spells.get(selectedSpell).name == "Slash"){
                            Warrior warrior = (Warrior) caster;
                            warrior.slash(creature);
                            swing.start();
                        }
                        else if (caster.spells.get(selectedSpell).name == "Heroic strike"){
                            Warrior warrior = (Warrior) caster;
                            warrior.heroicStrike(creature);
                            swing.start();
                        }
                    }
                    else{
                        click.start();
                    }
                    initMove();
                }
                return true;
            }
        });

        setupNewGame();
    }

    public void selectedSpell_1(View view){
        selectedSpell = 0;
        spell_1_button.setBackground(getResources().getDrawable(R.drawable.spell_slot_selected));
        spell_2_button.setBackground(getResources().getDrawable(R.drawable.spell_slot));
        MediaPlayer click = MediaPlayer.create(view.getContext(), R.raw.click);
        click.start();
    }

    public void selectedSpell_2(View view){
        selectedSpell = 1;
        spell_1_button.setBackground(getResources().getDrawable(R.drawable.spell_slot));
        spell_2_button.setBackground(getResources().getDrawable(R.drawable.spell_slot_selected));
        MediaPlayer click = MediaPlayer.create(view.getContext(), R.raw.click);
        click.start();
    }

    public void moveLeft(View view){
        if (creatures.get(turnNumber).getClass() == Hero.class
                || creatures.get(turnNumber).getClass() == Mage.class
                || creatures.get(turnNumber).getClass() == Warrior.class){
            Hero hero = (Hero) creatures.get(turnNumber);
            hero.moveLeft();
            initMove();
            MediaPlayer step = MediaPlayer.create(view.getContext(), R.raw.step);
            step.start();
        }
    }

    public void moveRight(View view){
        if (creatures.get(turnNumber).getClass() == Hero.class
                || creatures.get(turnNumber).getClass() == Mage.class
                || creatures.get(turnNumber).getClass() == Warrior.class) {
            Hero hero = (Hero)creatures.get(turnNumber);
            hero.moveRight();
            initMove();
            MediaPlayer step = MediaPlayer.create(view.getContext(), R.raw.step);
            step.start();
        }
    }

    public void moveUp(View view){
        if (creatures.get(turnNumber).getClass() == Hero.class
                || creatures.get(turnNumber).getClass() == Mage.class
                || creatures.get(turnNumber).getClass() == Warrior.class) {
            Hero hero = (Hero)creatures.get(turnNumber);
            hero.moveUp();
            initMove();
            MediaPlayer step = MediaPlayer.create(view.getContext(), R.raw.step);
            step.start();
        }
    }

    public void moveDown(View view){
        if (creatures.get(turnNumber).getClass() == Hero.class
                || creatures.get(turnNumber).getClass() == Mage.class
                || creatures.get(turnNumber).getClass() == Warrior.class) {
            Hero hero = (Hero)creatures.get(turnNumber);
            hero.moveDown();
            initMove();
            MediaPlayer step = MediaPlayer.create(view.getContext(), R.raw.step);
            step.start();
        }
    }
}

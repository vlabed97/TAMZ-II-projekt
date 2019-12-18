package com.example.game;

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
    public static ArrayList<Creature> creatures;
    private GameView gameView;
    private int turnNumber;
    private int totalTurnCount = 0;
    private int score = 1000;
    private int selectedSpell;
    private Button spell_1_button;
    private Button spell_2_button;
    private GameLoader gameLoader;
    private int actualMapId = 0;
    private int princessPosition;
    private TextView turnText;
    private int responseLenght = 1000;


    private void turnCounter(){
        turnNumber++;
        if (turnNumber >= creatures.size()){
            turnNumber = 0;
            totalTurnCount++;
        }
        selectCreature();
        initSpells();
    }

    private int getPrincessPosition(){
        int i = 0;
        for(int objectId: GameView.level){
            if(objectId == GameView.CAROTTY){
                return i;
            }
            i++;
        }
        return -1;
    }

    private void responseToPlayer(){
        if (creatures.get(turnNumber).getClass() == Enemy.class
                || creatures.get(turnNumber).getClass() == Minion.class){
            if(!creatures.get(turnNumber).dead){
                turnText.setText("Enemy turn");
            }
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    try{
                        Enemy enemy = (Enemy) creatures.get(turnNumber);
                        enemy.move(creatures); // do something
                        turnCounter(); // pass turn to player
                        turnText.setText("Your turn (" + totalTurnCount + ")");
                    }
                    catch (IndexOutOfBoundsException e){}
                    catch (ClassCastException e){}
                }
            }, responseLenght);
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
        princessPosition = getPrincessPosition();
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
        selectCreature();
        initSpells();
    }

    private void lose(){
        for(Creature creature: creatures){
            if(creature.getClass() == Warrior.class || creature.getClass() == Mage.class){
                if(creature.dead){
                    Intent intent = new Intent(this, LoseActivity.class);
                    startActivity(intent);
                }
            }
        }
    }

    private void victory(){
        if (GameView.HERO == GameView.level[princessPosition - 1]
                || GameView.HERO == GameView.level[princessPosition + 1]
                || GameView.HERO == GameView.level[princessPosition - GameView.lx]
                || GameView.HERO == GameView.level[princessPosition + GameView.lx]
                || GameView.HERO == GameView.level[princessPosition - GameView.lx - 1]
                || GameView.HERO == GameView.level[princessPosition - GameView.lx + 1]
                || GameView.HERO == GameView.level[princessPosition + GameView.lx - 1]
                || GameView.HERO == GameView.level[princessPosition + GameView.lx + 1]
                || GameView.ENEMY == GameView.level[princessPosition - 1]
                || GameView.ENEMY == GameView.level[princessPosition + 1]
                || GameView.ENEMY == GameView.level[princessPosition - GameView.lx]
                || GameView.ENEMY == GameView.level[princessPosition + GameView.lx]
                || GameView.ENEMY == GameView.level[princessPosition - GameView.lx - 1]
                || GameView.ENEMY == GameView.level[princessPosition - GameView.lx + 1]
                || GameView.ENEMY == GameView.level[princessPosition + GameView.lx - 1]
                || GameView.ENEMY == GameView.level[princessPosition + GameView.lx + 1]){
            Intent intent = new Intent(this, WinScreenActivity.class);
            int totalScore = score - totalTurnCount*25;
            if(totalScore < 200){
                totalScore = 200;
            }
            intent.putExtra("score", totalScore);
            intent.putExtra("map_id", actualMapId);
            startActivity(intent);
        }
    }

    private void openGates(){
        int i = 0;
        for(int objectId: GameView.level){
            if(objectId == 2){
                GameView.level[i] = GameView.GRASS;
            }
            i++;
        }
    }

    private boolean enemyDead(){
        for(Creature creature: creatures){
            if(creature.getClass() == Minion.class && creature.dead){
                return true;
            }
        }
        return false;
    }

    private void selectCreature(){
        int i = 0;
        for(int objectId: GameView.specialEffectsLayer){
            if(objectId == 11){
                GameView.specialEffectsLayer[i] = 9;
            }
            i++;
        }
        if(!creatures.get(turnNumber).dead){
            GameView.specialEffectsLayer[creatures.get(turnNumber).position] = 11;
        }
    }

    private void initMove(){
        turnCounter();
        responseToPlayer();
        if(enemyDead()){
            openGates();
            responseLenght = 0;
        }
        victory();
        lose();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        turnText = findViewById(R.id.textTurn);

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
                        if (caster.spells.get(selectedSpell).name == "Comet\n-30 MP"){
                            Mage mage = (Mage)caster;
                            if(mage.mana >= mage.FIREBALL_COST){
                                GameView gameView = findViewById(R.id.sokoView);
                                Log.i("mojLog", String.valueOf(gameView.getWidth()));
                                GameView.commet.actualX = (mage.position%GameView.lx) * (gameView.getWidth()/GameView.lx);
                                GameView.commet.actualY = (mage.position/GameView.lx) * (gameView.getWidth()/GameView.lx);
                                GameView.commet.targetX = x - 30;
                                GameView.commet.targetY = y - 30;
                                GameView.commet.launch();
                                mage.comet(creature);
                                swing.start();
                                initMove();
                            }
                        }
                        else if (caster.spells.get(selectedSpell).name == "Stab"){
                            Mage mage = (Mage)caster;
                            mage.stab(creature);
                            swing.start();
                            initMove();
                        }
                        else if (caster.spells.get(selectedSpell).name == "Slash"){
                            Warrior warrior = (Warrior) caster;
                            warrior.slash(creature);
                            swing.start();
                            initMove();
                        }
                        else if (caster.spells.get(selectedSpell).name == "Heroic strike\n-20 PW"){
                            Warrior warrior = (Warrior) caster;
                            if(warrior.rage >= warrior.HEROICSTRIKE_COST){
                                warrior.heroicStrike(creature);
                                swing.start();
                                initMove();
                            }
                        }
                        else{
                            initMove();
                        }
                    }
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
            if(hero.moveLeft()){
                initMove();
                MediaPlayer step = MediaPlayer.create(view.getContext(), R.raw.step);
                step.start();
            }
        }
    }

    public void moveRight(View view){
        if (creatures.get(turnNumber).getClass() == Hero.class
                || creatures.get(turnNumber).getClass() == Mage.class
                || creatures.get(turnNumber).getClass() == Warrior.class) {
            Hero hero = (Hero)creatures.get(turnNumber);
            if(hero.moveRight()){
                initMove();
                MediaPlayer step = MediaPlayer.create(view.getContext(), R.raw.step);
                step.start();
            }
        }
    }

    public void moveUp(View view){
        if (creatures.get(turnNumber).getClass() == Hero.class
                || creatures.get(turnNumber).getClass() == Mage.class
                || creatures.get(turnNumber).getClass() == Warrior.class) {
            Hero hero = (Hero)creatures.get(turnNumber);
            if(hero.moveUp()){
                initMove();
                MediaPlayer step = MediaPlayer.create(view.getContext(), R.raw.step);
                step.start();
            }
        }
    }

    public void moveDown(View view){
        if (creatures.get(turnNumber).getClass() == Hero.class
                || creatures.get(turnNumber).getClass() == Mage.class
                || creatures.get(turnNumber).getClass() == Warrior.class) {
            Hero hero = (Hero)creatures.get(turnNumber);
            if(hero.moveDown()){
                initMove();
                MediaPlayer step = MediaPlayer.create(view.getContext(), R.raw.step);
                step.start();
            }
        }
    }
}

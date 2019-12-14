package com.example.sokoban33;

import android.os.Handler;
import android.view.View;

import java.util.ArrayList;

public class Creature {
    protected int position;
    protected int heroIcon;

    protected final int MAX_HP = 100;
    protected int hp = 100;
    protected boolean dead;

    protected String name;

    protected ArrayList<Spell> spells;

    protected final Handler handler;

    protected View gameView;

    protected int getHeroPosition(){
        int i = 0;
        for (int objNum: SokoView.level) {
            if(objNum == heroIcon){
                return i;
            }
            i++;
        }
        return -1;
    }

    public Creature(int heroIcon, String name, View gameView){
        this.heroIcon = heroIcon;
        this.position = getHeroPosition();
        this.name = name;
        dead = false;
        spells = new ArrayList<>();
        handler = new Handler();
        this.gameView = gameView;
    }

    public void takeDamage(int dmg){
        hp -= dmg;
        SokoView.specialEffectsLayer[position] = SokoView.SELECTED;

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SokoView.specialEffectsLayer[position] = SokoView.EMPTY;
                gameView.invalidate();
            }
        }, 500);

        if (hp <= 0){
            dead = true;
            heroIcon = 5; // tady zmenit na ikonu lebky
            SokoView.level[position] = heroIcon;
        }
    }

    public int getHp(){
        return hp;
    }

    public int getPosition() {
        return position;
    }

    public void moveLeft(){
        if (MapGuide.isFree(MapGuide.LEFT, position)) {
            SokoView.level[position] = SokoView.GRASS;
            position--;
            SokoView.level[position] = heroIcon;
        }
    }

    public void moveRight(){
        if (MapGuide.isFree(MapGuide.RIGHT, position)) {
            SokoView.level[position] = SokoView.GRASS;
            position++;
            SokoView.level[position] = heroIcon;
        }
    }

    public void moveUp(){
        if (MapGuide.isFree(MapGuide.UP, position)) {
            SokoView.level[position] = SokoView.GRASS;
            position -= SokoView.lx;
            SokoView.level[position] = heroIcon;
        }
    }

    public void moveDown(){
        if (MapGuide.isFree(MapGuide.DOWN, position)) {
            SokoView.level[position] = SokoView.GRASS;
            position += SokoView.lx;
            SokoView.level[position] = heroIcon;
        }
    }
}

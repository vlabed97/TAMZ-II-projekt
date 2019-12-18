package com.example.game;

import android.view.View;

public class Hero extends Creature {

    public Hero(int heroIcon, String name, View gameView){
        super(heroIcon, name, gameView);
    }

    @Override
    public void takeDamage(int dmg) {
        hp -= dmg;
        GameView.specialEffectsLayer[position] = GameView.SELECTED;

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                GameView.specialEffectsLayer[position] = GameView.EMPTY;
            }
        }, 500);

        if (hp <= 0){
            dead = true;
            //heroIcon = 5; // tady zmenit na ikonu lebky
            //GameView.level[position] = heroIcon;
            //Toast.makeText(gameView.getContext(), "You have lost the game!", Toast.LENGTH_LONG).show();
        }
    }
}

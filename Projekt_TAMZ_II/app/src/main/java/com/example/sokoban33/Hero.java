package com.example.sokoban33;

import android.content.Intent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;

public class Hero extends Creature {

    public Hero(int heroIcon, String name, View gameView){
        super(heroIcon, name, gameView);
    }

    @Override
    public void takeDamage(int dmg) {
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
            Toast.makeText(gameView.getContext(), "You have lost the game!", Toast.LENGTH_LONG).show();
        }
    }
}

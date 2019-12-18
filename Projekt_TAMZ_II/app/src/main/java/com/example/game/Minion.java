package com.example.game;

import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class Minion extends Enemy {
    private final int MELE_ATTACK_POWER = 15;

    public Minion(int heroIcon, String name, ArrayList<Creature> creatures, View gameView){
        super(heroIcon, name, creatures, gameView);
    }

    @Override
    public void move(ArrayList<Creature> creatures) {
        if(this.dead){
            return;
        }
        ArrayList heroesInRange = getMeleRange(loadHeroes(creatures));
        if (heroesInRange.size() == 0){
            Log.i("mojLog", "no heroe to attack");
            moveToPlayer(creatures);
        }
        else{
            Hero heroToAttack = getLessHpHero(heroesInRange);
            heroToAttack.takeDamage(MELE_ATTACK_POWER);
            Log.i("mojLog", "hitting " + heroToAttack.name);
        }
    }
}

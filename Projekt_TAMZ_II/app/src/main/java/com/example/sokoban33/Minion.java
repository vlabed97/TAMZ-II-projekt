package com.example.sokoban33;

import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class Minion extends Enemy {
    private final int MELE_ATTACK_POWER = 25;

    public Minion(int heroIcon, String name, ArrayList<Creature> creatures, View gameView){
        super(heroIcon, name, creatures, gameView);
    }

    @Override
    public void move(ArrayList<Creature> creatures) {
        ArrayList heroesInRange = getMeleRange(loadHeroes(creatures));
        if (heroesInRange.size() == 0){
            Log.i("mojLog", "no heroe to attack");
        }
        else{
            Hero heroToAttack = getLessHpHero(heroesInRange);
            heroToAttack.takeDamage(MELE_ATTACK_POWER);
            Log.i("mojLog", "hitting " + heroToAttack.name);
        }
    }
}

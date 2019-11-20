package com.example.sokoban33;

import android.view.View;

public class Mage extends Hero {
    private final int MAX_HP = 80;
    private int hp = 80;

    private final int MAX_MANA = 100;
    private int mana = 100;

    private final int FIREBALL_COST = 50;

    public Mage(int heroIcon, String name, View gameView){
        super(heroIcon, name, gameView);
        spells.add("Fireball");
    }

    public void fireball(Creature creature){
        if (mana >= FIREBALL_COST){
            creature.takeDamage(20);
        }
    }
}

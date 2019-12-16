package com.example.sokoban33;

import android.view.View;

public class Mage extends Hero {
    private final int MAX_HP = 80;
    private int hp = 80;

    private final int MAX_MANA = 100;
    public int mana = 100;

    private final int FIREBALL_COST = 10;

    public Mage(int heroIcon, String name, View gameView){
        super(heroIcon, name, gameView);
        spells.add(new Spell("Comet", R.drawable.comet));
        spells.add(new Spell("Stab", R.drawable.comet));
    }

    public void comet(Creature creature){
        if (mana >= FIREBALL_COST){
            creature.takeDamage(20);
            mana -= FIREBALL_COST;
        }
    }

    public void stab(Creature creature){
        if(creature.position == this.position - 1
                || creature.position == this.position + 1
                || creature.position == this.position - GameView.lx
                || creature.position == this.position + GameView.lx){
            creature.takeDamage(8);
        }
    }
}

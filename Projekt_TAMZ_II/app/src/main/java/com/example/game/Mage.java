package com.example.game;

import android.view.View;

public class Mage extends Hero {
    private final int MAX_HP = 80;
    private int hp = 80;

    public final int MAX_MANA = 100;
    public int mana = 100;

    public final int FIREBALL_COST = 30;

    public Mage(int heroIcon, String name, View gameView){
        super(heroIcon, name, gameView);
        spells.add(new Spell("Comet\n-30 MP", R.drawable.comet));
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
                || creature.position == this.position + GameView.lx
                || creature.position == this.position - GameView.lx - 1
                || creature.position == this.position - GameView.lx + 1
                || creature.position == this.position + GameView.lx - 1
                || creature.position == this.position + GameView.lx + 1){
            creature.takeDamage(15);
        }
    }
}

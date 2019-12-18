package com.example.sokoban33;

import android.view.View;

public class Warrior extends Hero {
    private final int MAX_HP = 120;
    private int hp = 100;

    private final int MAX_RAGE = 100;
    public int rage = 100;

    public final int HEROICSTRIKE_COST = 20;

    public Warrior(int heroIcon, String name, View gameView){
        super(heroIcon, name, gameView);
        spells.add(new Spell("Slash", R.drawable.comet));
        spells.add(new Spell("Heroic strike\n-20 PW", R.drawable.comet));
    }

    public void slash(Creature creature){
        if(creature.position == this.position - 1
                || creature.position == this.position + 1
                || creature.position == this.position - GameView.lx
                || creature.position == this.position + GameView.lx
                || creature.position == this.position - GameView.lx - 1
                || creature.position == this.position - GameView.lx + 1
                || creature.position == this.position + GameView.lx - 1
                || creature.position == this.position + GameView.lx + 1) {
            creature.takeDamage(20);
        }
    }

    public void heroicStrike(Creature creature){
        if (rage >= HEROICSTRIKE_COST){
            if(creature.position == this.position - 1
                    || creature.position == this.position + 1
                    || creature.position == this.position - GameView.lx
                    || creature.position == this.position + GameView.lx
                    || creature.position == this.position - GameView.lx - 1
                    || creature.position == this.position - GameView.lx + 1
                    || creature.position == this.position + GameView.lx - 1
                    || creature.position == this.position + GameView.lx + 1){
                creature.takeDamage(30);
                rage -= HEROICSTRIKE_COST;
            }
        }
    }
}

package com.example.sokoban33;

import android.view.View;

public class Warrior extends Hero {
    private final int MAX_HP = 120;
    private int hp = 100;

    private final int MAX_RAGE = 100;
    private int rage = 100;

    private final int HEROICSTRIKE_COST = 30;

    public Warrior(int heroIcon, String name, View gameView){
        super(heroIcon, name, gameView);
        spells.add(new Spell("Slash", R.drawable.comet));
        spells.add(new Spell("Heroic strike", R.drawable.comet));
    }

    public void slash(Creature creature){
        if(creature.position == this.position - 1
                || creature.position == this.position + 1
                || creature.position == this.position - SokoView.lx
                || creature.position == this.position + SokoView.lx) {
            creature.takeDamage(15);
        }
    }

    public void heroicStrike(Creature creature){
        if (rage >= HEROICSTRIKE_COST){
            if(creature.position == this.position - 1
                    || creature.position == this.position + 1
                    || creature.position == this.position - SokoView.lx
                    || creature.position == this.position + SokoView.lx){
                creature.takeDamage(30);
                rage -= HEROICSTRIKE_COST;
            }
        }
    }
}

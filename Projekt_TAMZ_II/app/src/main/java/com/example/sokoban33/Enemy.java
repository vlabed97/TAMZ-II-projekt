package com.example.sokoban33;

import android.util.Log;
import android.view.View;

import java.time.chrono.HijrahEra;
import java.util.ArrayList;

public class Enemy extends Creature {

    public ArrayList<Hero> loadHeroes(ArrayList<Creature> creatures){
        ArrayList<Hero> heroes = new ArrayList<>();
        for (Creature creature: creatures){
            if (creature.getClass() == Hero.class || creature.getClass() == Mage.class){
                heroes.add((Hero)creature);
                Log.i("mojLog", creature.name);
            }
        }
        return heroes;
    }

    public Enemy(int heroIcon, String name, ArrayList<Creature> creatures, View gameView){
        super(heroIcon, name, gameView);
        loadHeroes(creatures);
    }

    protected ArrayList<Hero> getMeleRange(ArrayList<Hero> heroes) {
        ArrayList<Hero> heroesInRange = new ArrayList<>();
        for (Hero hero: heroes) {
            int heroPosition = hero.getPosition();
            if (heroPosition == this.position - 1
                || heroPosition == this.position + 1
                || heroPosition == this.position - SokoView.lx
                || heroPosition == this.position + SokoView.lx
                || heroPosition == this.position - SokoView.lx - 1
                || heroPosition == this.position - SokoView.lx + 1
                || heroPosition == this.position + SokoView.lx - 1
                || heroPosition == this.position + SokoView.lx + 1){
                heroesInRange.add(hero);
            }
        }
        return heroesInRange;
    }

    protected Hero getLessHpHero(ArrayList<Hero> heroes){
        Hero lessHpHero = null;
        for (Hero hero: heroes) {
            if (lessHpHero == null){
                lessHpHero = hero;
            }

            if (hero.getHp() < lessHpHero.getHp()){
                lessHpHero = hero;
            }
        }
        return lessHpHero;
    }

    protected void moveToPlayer(ArrayList<Creature> creatures){
        Hero target = getLessHpHero(loadHeroes(creatures));
        if(target.position > this.position + SokoView.lx){
            moveDown();
        }
        else if(target.position < this.position - SokoView.lx){
            moveUp();
        }
        else if(target.position < this.position){
            moveLeft();
        }
        else if(target.position > this.position){
            moveRight();
        }
    }

    public void move(ArrayList<Creature> creatures){}
}

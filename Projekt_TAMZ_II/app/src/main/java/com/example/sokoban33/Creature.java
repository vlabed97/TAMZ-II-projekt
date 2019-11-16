package com.example.sokoban33;

public class Creature {
    private int position;
    private int heroIcon;

    private final int MAX_HP = 100;
    private int hp = 100;

    private String name;

    private int getHeroPosition(){
        int i = 0;
        for (int objNum: SokoView.level) {
            if(objNum == heroIcon){
                return i;
            }
            i++;
        }
        return -1;
    }

    public Creature(int heroIcon, String name){
        this.heroIcon = heroIcon;
        this.position = getHeroPosition();
        this.name = name;
    }

    public void moveLeft(){
        if (MapGuide.isFree(MapGuide.LEFT, position)) {
            SokoView.level[position] = SokoView.GRASS;
            position--;
            SokoView.level[position] = heroIcon;
        }
    }

    public void moveRight(){
        if (MapGuide.isFree(MapGuide.RIGHT, position)) {
            SokoView.level[position] = SokoView.GRASS;
            position++;
            SokoView.level[position] = heroIcon;
        }
    }

    public void moveUp(){
        if (MapGuide.isFree(MapGuide.UP, position)) {
            SokoView.level[position] = SokoView.GRASS;
            position -= SokoView.lx;
            SokoView.level[position] = heroIcon;
        }
    }

    public void moveDown(){
        if (MapGuide.isFree(MapGuide.DOWN, position)) {
            SokoView.level[position] = SokoView.GRASS;
            position += SokoView.lx;
            SokoView.level[position] = heroIcon;
        }
    }
}

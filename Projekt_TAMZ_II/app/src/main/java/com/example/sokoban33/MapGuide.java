package com.example.sokoban33;

public class MapGuide {
    public static String LEFT = "left";
    public static String RIGHT = "right";
    public static String UP = "up";
    public static String DOWN = "down";

    public MapGuide(){}

    public static boolean isFree(String site, int position){
        switch (site){
            case "left":
                if (GameView.level[position - 1] == GameView.GRASS) return true;
                else return false;
            case "right":
                if (GameView.level[position + 1] == GameView.GRASS) return true;
                else return false;
            case "up":
                if (GameView.level[position - GameView.lx] == GameView.GRASS) return true;
                else return false;
            case "down":
                if (GameView.level[position + GameView.lx] == GameView.GRASS) return true;
                else return false;
        }
        return false;
    }
}

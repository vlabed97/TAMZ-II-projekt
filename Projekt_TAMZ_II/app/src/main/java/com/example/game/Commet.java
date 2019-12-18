package com.example.game;

import android.graphics.Bitmap;

public class Commet {
    private Bitmap[] frames;
    private Bitmap empty;

    public Bitmap actualFrame;

    public int actualX;
    public int actualY;
    public int targetX;
    public int targetY;

    public Commet(Bitmap[] frames, Bitmap empty){
        this.frames = frames;
        actualFrame = empty;
        this.empty = empty;
    }

    public void launch(){
        // ANIMATION
        new Thread(new Runnable() {
            public void run() {
                int frameNum = 0;
                final int DELAY = 100;
                long previousTime = System.currentTimeMillis();
                while (actualX != targetX || actualY != targetY){
                    if(previousTime + DELAY < System.currentTimeMillis()){
                        if(frameNum == frames.length - 0){
                            frameNum = 0;
                        }
                        actualFrame = frames[frameNum];
                        frameNum++;
                        previousTime = System.currentTimeMillis();
                    }
                }
                actualFrame = empty;
            }
        }).start();

        // MOVEMENT
        new Thread(new Runnable() {
            public void run() {
                final int DELAY = 3;
                long previousTime = System.currentTimeMillis();
                while (actualX != targetX || actualY != targetY){
                    if(previousTime + DELAY < System.currentTimeMillis()){
                        if(actualX > targetX){
                            actualX--;
                        }
                        if(actualX < targetX){
                            actualX++;
                        }
                        if(actualY > targetY){
                            actualY--;
                        }
                        if(actualY < targetY){
                            actualY++;
                        }
                        // Log.i("mojLog", "X: " + actualX + ", Y: " + actualY);
                        previousTime = System.currentTimeMillis();
                    }
                }
            }
        }).start();
    }
}

package com.example.sokoban33;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Debug;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.graphics.Color;

import static java.lang.Math.round;

/**
 * Created by kru13 on 12.10.16.
 */

public class GameView extends View{

    Bitmap[] bmp;
    public static Commet commet;

    static int lx = 10;
    static int ly = 10;

    int width;
    int height;

    final static int GRASS = 0;
    final static int BUSH = 3;
    final static int STONE = 1;
    final static int HERO = 4;
    final static int ENEMY = 6;
    final static int MINION = 7;
    final static int SELECTED = 8;
    final static int EMPTY = 9;
    final static int CAROTTY = 10;
    final static int FLOOR = 12;
    final static int WALL_2 = 13;

    public static int level[] = {
            1,1,1,1,1,1,1,1,1,1,
            1,0,0,0,0,0,0,0,0,1,
            1,0,0,0,7,0,2,10,0,1,
            1,0,1,3,0,0,2,2,2,1,
            1,0,2,3,0,0,0,0,0,1,
            1,0,1,3,0,0,2,0,0,1,
            1,0,0,0,0,2,1,0,0,1,
            1,0,0,4,0,0,0,0,0,1,
            1,0,0,0,0,0,6,0,0,1,
            1,1,1,1,1,1,1,1,1,110
    };

    public static int specialEffectsLayer[] = {
            9,9,9,9,9,9,9,9,9,9,
            9,9,9,9,9,9,9,9,9,9,
            9,9,9,9,9,9,9,9,9,9,
            9,9,9,9,9,9,9,9,9,9,
            9,9,9,9,9,9,9,9,9,9,
            9,9,9,9,9,9,9,9,9,9,
            9,9,9,9,9,9,9,9,9,9,
            9,9,9,9,9,9,9,9,9,9,
            9,9,9,9,9,9,9,9,9,9,
            9,9,9,9,9,9,9,9,9,9
    };

    public int getClickedPosition(int x, int y){
        int cellWidth = this.getMeasuredWidth() / lx;
        return (y / cellWidth)*lx + (x / cellWidth);
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);

        new Thread(new Runnable() {
            public void run() {
                final int DELAY = 50;
                long previousTime = System.currentTimeMillis();
                while(true){
                    if(previousTime + DELAY < System.currentTimeMillis()){
                        invalidate();
                        previousTime = System.currentTimeMillis();
                    }
                }
            }
        }).start();
    }

    void init(Context context) {
        bmp = new Bitmap[15];
        bmp[0] = BitmapFactory.decodeResource(getResources(), R.drawable.grass_1);
        bmp[1] = BitmapFactory.decodeResource(getResources(), R.drawable.grass_3);
        bmp[2] = BitmapFactory.decodeResource(getResources(), R.drawable.box);
        bmp[3] = BitmapFactory.decodeResource(getResources(), R.drawable.grass_2);
        bmp[4] = BitmapFactory.decodeResource(getResources(), R.drawable.bunny32px);
        bmp[5] = BitmapFactory.decodeResource(getResources(), R.drawable.boxok);
        bmp[6] = BitmapFactory.decodeResource(getResources(), R.drawable.fox32px);
        bmp[7] = BitmapFactory.decodeResource(getResources(), R.drawable.minion32px);
        bmp[8] = BitmapFactory.decodeResource(getResources(), R.drawable.selected_area);
        bmp[9] = BitmapFactory.decodeResource(getResources(), R.drawable.empty_alpha);
        bmp[10] = BitmapFactory.decodeResource(getResources(), R.drawable.carotty);
        bmp[11] = BitmapFactory.decodeResource(getResources(), R.drawable.selection);
        bmp[12] = BitmapFactory.decodeResource(getResources(), R.drawable.floor);
        bmp[13] = BitmapFactory.decodeResource(getResources(), R.drawable.wall_2);
        bmp[14] = BitmapFactory.decodeResource(getResources(), R.drawable.rip);
        Bitmap[] commetBitmap = new Bitmap[3];
        commetBitmap[0] = BitmapFactory.decodeResource(getResources(), R.drawable.commet_0);
        commetBitmap[1] = BitmapFactory.decodeResource(getResources(), R.drawable.commet_1);
        commetBitmap[2] = BitmapFactory.decodeResource(getResources(), R.drawable.commet_2);
        commet = new Commet(commetBitmap, bmp[9]);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        width = w / ly;
        height = h / lx;
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        for (int i = 0; i < lx; i++) {
            for (int j = 0; j < ly; j++) {
                canvas.drawBitmap(bmp[GRASS], null,
                        new Rect(j*width, i*height,(j+1)*width, (i+1)*height), null);
                canvas.drawBitmap(bmp[level[i*10 + j]], null,
                        new Rect(j*width, i*height,(j+1)*width, (i+1)*height), null);
                canvas.drawBitmap(bmp[specialEffectsLayer[i*10 + j]], null,
                        new Rect(j*width, i*height,(j+1)*width, (i+1)*height), null);
            }
        }

        canvas.drawBitmap(commet.actualFrame, null, new Rect(commet.actualX, commet.actualY, commet.actualX + 60, commet.actualY + 60), null);
        Paint paintYellow = new Paint();
        paintYellow.setColor(Color.YELLOW);
        paintYellow.setStyle(Paint.Style.FILL);
        paintYellow.setTextSize(30);
        Paint paintBlue = new Paint();
        paintBlue.setColor(Color.BLUE);
        paintBlue.setStyle(Paint.Style.FILL);
        paintBlue.setTextSize(30);
        Paint paintRed = new Paint();
        paintRed.setColor(Color.RED);
        paintRed.setStyle(Paint.Style.FILL);
        paintRed.setTextSize(30);
        for(Creature creature: MainActivity.creatures){
            canvas.drawText(creature.hp + " HP", creature.positionX, creature.positionY, paintRed);
            if(creature.getClass() == Mage.class){
                Mage mage = (Mage) creature;
                canvas.drawText( mage.mana + " MP", creature.positionX, creature.positionY - 30, paintYellow);
            }
            else if(creature.getClass() == Warrior.class){
                Warrior warrior = (Warrior)creature;
                canvas.drawText( warrior.rage + " POWER", creature.positionX, creature.positionY - 30, paintYellow);
            }

        }
    }
}
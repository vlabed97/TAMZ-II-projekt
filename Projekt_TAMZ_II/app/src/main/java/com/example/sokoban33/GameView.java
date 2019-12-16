package com.example.sokoban33;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by kru13 on 12.10.16.
 */

public class GameView extends View{

    Bitmap[] bmp;

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
            1,1,1,1,1,1,1,1,1,1
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

    public GameView(Context context) {
        super(context);
        init(context);
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    void init(Context context) {
        bmp = new Bitmap[11];
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

    }
}
package com.example.cs441_assignment_5;

import android.app.ActionBar;
import android.graphics.Point;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.Timer;
import java.util.TimerTask;


class Ant{
    public float xCord;
    public float yCord;
    public int health = 100;
    public int level = 1;

}

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {
    RelativeLayout newLayout; //layout
    private int _xDelta; //x variable
    private int _yDelta; //y variable
    private float dX;
    private float dY;
    private int screen_width;
    private int screen_height;
    private ImageView ant;
    private float antx; //temp x
    private float anty = 550; //temp x
    private Handler handler = new Handler();
    private Timer timer = new Timer();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get screen size and set up to get the ants in the right part of the screen
        WindowManager wm = getWindowManager();
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screen_height = size.y;
        screen_width = size.x;

        //Gets rid of status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ImageButton shopButton = findViewById(R.id.shopButton);
        ImageView tower1 = findViewById(R.id.tower1);
        tower1.setOnTouchListener(this);
        ant = findViewById(R.id.ant);
//        ant.setOnTouchListener(this);


        //coordinates for the ants
        ant.setX(screen_width + 80.0f);
        ant.setY(550);
        ant.setMaxHeight(100);
        ant.setMaxWidth(100);
        ant.setAdjustViewBounds(true);

        //timer to make the ants move
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        changePos();
                    }
                });
            }
        }, 0, 20);

    }

    //function to move the ants
    public void changePos() {
        antx += 10;
        if (ant.getX() > screen_width) {
            antx = -100.0f;
            anty = 550;
        }
        if (ant.getX() > 240 && ant.getX() < 590) {
            antx-=10;
            anty-=10;
        }
        if (ant.getY() < 230 && ant.getY() > 130) {
            anty+=10;
            antx+=10;
        }
        if (ant.getX() > 590 && ant.getX() < 1050) {
            antx-=10;
            anty+=10;
        }
        if (ant.getY() > 640) {
            anty-=10;
            antx+=10;
        }
        if (ant.getX() > 1050) {
            antx-=10;
            anty-=10;
        }
        if (ant.getY() < 450 && ant.getX() > 1000) {
            anty+=10;
            antx+=10;
        }
        ant.setX(antx);
        ant.setY(anty);
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                dX = view.getX() - event.getRawX();
                dY = view.getY() - event.getRawY();
                break;

            case MotionEvent.ACTION_MOVE:
                view.animate()
                        .x(event.getRawX() + dX)
                        .y(event.getRawY() + dY)
                        .setDuration(0)
                        .start();
                break;
            default:
                return false;
        }
        return true;

    }

}

package com.example.cs441_assignment_5;

import android.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


class ANT{
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Gets rid of status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ImageButton shopButton = findViewById(R.id.shopButton);
        ImageView tower1 = findViewById(R.id.tower1);
        tower1.setOnTouchListener(this);

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

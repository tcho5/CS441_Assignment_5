package com.example.cs441_assignment_5;

import android.graphics.Color;
import android.graphics.Point;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;


class Ant{
    public float xCord;
    public float yCord;
    public int health = 100;
    public int level = 1;

}

class Tower {
    public float range;
}

public class MainActivity extends AppCompatActivity implements View.OnTouchListener, View.OnClickListener, PopupMenu.OnMenuItemClickListener{
    private float dX;
    private float dY;
    private int screen_width;
    private int screen_height;
    private ImageView ant;
    private ImageView bullet;
    int scoreVal = 0;
    int levelVal = 0;
    int cashVal = 500;
    private TextView score;
    private TextView level;
    private TextView cash;
    private float antx; //temp x
    private float anty = 550; //temp x
    private float bulletx;
    private float bullety;
    private Handler handler = new Handler();
    private Timer timer = new Timer();
    private ConstraintLayout layout;
    private ImageButton shopButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layout = findViewById(R.id.constraintLayout);

        //get screen size and set up to get the ants in the right part of the screen
        WindowManager wm = getWindowManager();
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screen_height = size.y;
        screen_width = size.x;

        //Gets rid of status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//
        shopButton = findViewById(R.id.shopButton);
        shopButton.setOnClickListener(this);

        ant = findViewById(R.id.ant);
        score = findViewById(R.id.ScoreText);
        level = findViewById(R.id.LevelText);
        cash = findViewById(R.id.CashText);
        bullet = findViewById(R.id.bullet2);
//      ant.setOnTouchListener(this);


        bullet.setX(screen_width + 80.0f);
        bullet.setY(600);
        bullet.setAdjustViewBounds(true);

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
            ant.setRotation(0);
        }
        if (ant.getY() < 230 && ant.getY() > 130) {
            anty+=10;
            antx+=10;
            ant.setRotation(90);
        }
        if (ant.getX() > 590 && ant.getX() < 1050) {
            antx-=10;
            anty+=10;
            ant.setRotation(180);
        }
        if (ant.getY() > 640) {
            anty-=10;
            antx+=10;
            ant.setRotation(90);
        }
        if (ant.getX() > 1050) {
            antx-=10;
            anty-=10;
            ant.setRotation(0);
        }
        if (ant.getY() < 450 && ant.getX() > 1000) {
            anty+=10;
            antx+=10;
            ant.setRotation(90);
        }
        //decrements score
        if  (ant.getY() < 450 && ant.getX() > 1790) {
            scoreVal--;
            score.setText("Score " + scoreVal);
        }
        ant.setX(antx);
        ant.setY(anty);
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        switch (view.getId()){
            case R.id.shopButton:
                break;
            default: //When they clicked a tower
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
        }
        return false;
    }
    public boolean closeEnough() {
        if((bulletx + antx < 2*antx - 50) && (bullety + anty < 2*anty - 50)){
            return true;
        }
        return false;
    }

    public void shooting(ImageButton tower) {
            if (bulletx < antx && bullety < anty) {
                bulletx += 10;
                bullety += 10;
                bullet.setRotation(0);
            }
            if (bulletx < antx && bullety > anty) {
                bulletx += 10;
                bullety -= 10;
                bullet.setRotation(0);
            }
            if (bulletx > antx && bullety < anty) {
                bulletx -= 10;
                bullety += 10;
                bullet.setRotation(180);
            }
            if (bulletx > antx && bullety > anty) {
                bulletx -= 10;
                bullety -= 10;
                bullet.setRotation(180);
            }
            if (closeEnough()) {
                ant.setX(screen_width + 80.0f);
                ant.setY(550);
                bullet.setX(tower.getX());
                bullet.setY(tower.getY());
                scoreVal += 10;
                cashVal += 100;
                levelVal++;
                score.setText("Score: " +scoreVal);
                cash.setText("Money: $" + cashVal);
                level.setText("Level " + levelVal);
            }
            bullet.setX(bulletx);
            bullet.setY(bullety);
    }

    @Override
    public void onClick(View v) {
        showPopUp(v);
    }

    public void showPopUp(View v){
        PopupMenu popUp = new PopupMenu(this, v);
        popUp.setOnMenuItemClickListener(this);
        popUp.inflate(R.menu.popup_menu);
        popUp.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item){
        switch(item.getItemId()) {
            case R.id.tower1:
                if(cashVal >= 100) {
                    cashVal = cashVal - 100;
                    final ImageButton tower2 = new ImageButton(this);
                    tower2.setImageResource(R.drawable.tower1);
                    tower2.setX(600);
                    tower2.setY(850);
                    tower2.setMaxHeight(175);
                    tower2.setMaxWidth(175);
                    tower2.setAdjustViewBounds(true);
                    tower2.setLayoutParams(new Gallery.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));
                    tower2.setBackgroundColor(Color.TRANSPARENT);
                    tower2.setOnTouchListener(this);
                    layout.addView(tower2);
                    bulletx = tower2.getX();
                    bullety = tower2.getY();
                    Timer timer = new Timer();
                    timer.scheduleAtFixedRate(new TimerTask() {
                                                  @Override
                                                  public void run() {
                                                      shooting(tower2);
                                                  }
                                              },
                            0, 5);   // 1000 Millisecond  = 1 second
                }
                cash.setText("Money: $" + cashVal);
//                Toast.makeText(this, "Item 1 clicked ", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tower2:
                if(cashVal >= 200) {
                    cashVal = cashVal - 200;
                    final ImageButton tower2 = new ImageButton(this);
                    tower2.setImageResource(R.drawable.tower2);
                    tower2.setX(600);
                    tower2.setY(850);
                    tower2.setMaxHeight(200);
                    tower2.setMaxWidth(200);
                    tower2.setAdjustViewBounds(true);
                    tower2.setLayoutParams(new Gallery.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));
                    tower2.setBackgroundColor(Color.TRANSPARENT);
                    tower2.setOnTouchListener(this);
                    layout.addView(tower2);
                    bulletx = tower2.getX();
                    bullety = tower2.getY();
                    Timer timer = new Timer();
                    timer.scheduleAtFixedRate(new TimerTask() {
                                                  @Override
                                                  public void run() {
                                                      shooting(tower2);
                                                  }
                                              },
                            0, 5);
                }
                cash.setText("Money: $" + cashVal);
//                Toast.makeText(this, "Item 2 clicked ", Toast.LENGTH_SHORT).show();
                break;
            default:
                return false;
        }
        return true;
    }
}

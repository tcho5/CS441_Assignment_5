package com.example.cs441_assignment_5;

import android.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Gallery;
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
    RelativeLayout newLayout;
    private int _xDelta;
    private int _yDelta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Gets rid of status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);


        //creates a new layout
        newLayout = new RelativeLayout(this);
        //RelativeLayout.LayoutParams boop = new RelativeLayout.LayoutParams(
        //        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        newLayout.setBackgroundResource(R.drawable.antmap);
        //creates new object
        ImageView tower1 = new ImageView(this);
        tower1.setImageResource(R.drawable.tower1);

        //defines item coordinates and size.
        tower1.setX(200);
        tower1.setY(600);
        tower1.setMaxHeight(200);
        tower1.setMaxWidth(200);
        tower1.setAdjustViewBounds(true);
        tower1.setLayoutParams(new Gallery.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));

        //allows listeoner
        tower1.setOnTouchListener(this);

        //adds item to view
        newLayout.addView(tower1);
        //sets view
        setContentView(newLayout);

    }

    public boolean onTouch(View view, MotionEvent event) {
        final int X = (int) event.getRawX();
        final int Y = (int) event.getRawY();
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                _xDelta = X - lParams.leftMargin;
                _yDelta = Y - lParams.topMargin;
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                break;
            case MotionEvent.ACTION_POINTER_UP:
                break;
            case MotionEvent.ACTION_MOVE:
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view
                        .getLayoutParams();
                layoutParams.leftMargin = X - _xDelta;
                layoutParams.topMargin = Y - _yDelta;
                layoutParams.rightMargin = -250;
                layoutParams.bottomMargin = -250;
                view.setLayoutParams(layoutParams);


                //makes a new Tower in the same spot
                ImageView newTower1 = new ImageView(this);
                newTower1.setImageResource(R.drawable.tower1);
                newTower1.setAdjustViewBounds(true);
                newTower1.setX(200);
                newTower1.setY(600);
                newTower1.setMaxHeight(200);
                newTower1.setMaxWidth(200);
                newTower1.setOnTouchListener(this);

                // Adds the view to the layout
                newLayout.addView(newTower1);
                break;
        }
        newLayout.invalidate();
        return true;
    }

}

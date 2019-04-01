package com.example.cs441_assignment_5;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

class ANT{
    public float xCord;
    public float yCord;
    public int health = 100;
    public int level = 1;
}

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }
}

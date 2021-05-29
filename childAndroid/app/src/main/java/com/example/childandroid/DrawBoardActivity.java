package com.example.childandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;
import android.os.Bundle;

import static com.example.childandroid.display.colors;
import static com.example.childandroid.display.current_color;
import static com.example.childandroid.display.paths;

public class DrawBoardActivity extends AppCompatActivity {
    static Path path = new Path();
    static Paint paint = new Paint();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_board);
    }
    public void redColor(View view){
            paint.setColor(Color.RED);
        currentColor(paint.getColor());
    }

    public void pencil(View view){
        paint.setColor(Color.BLACK);
        currentColor(paint.getColor());
    }

    public void eraser(View view){
        paths.clear();
        colors.clear();
        path.reset();
    }

    public void greenColor(View view){
        paint.setColor(Color.GREEN);
        currentColor(paint.getColor());
    }

    public void yellowColor(View view){
        paint.setColor(Color.YELLOW);
        currentColor(paint.getColor());
    }

    public void pinkColor(View view){
        paint.setColor(Color.MAGENTA);
        currentColor(paint.getColor());
    }
    public void blueColor(View view){
        paint.setColor(Color.BLUE);
        currentColor(paint.getColor());
    }

    public void currentColor(int c){
        current_color = c;
        path = new Path();
    }

}
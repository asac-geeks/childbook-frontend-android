package com.example.childandroid;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.icu.lang.UCharacter;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import static com.example.childandroid.DrawBoardActivity.paint;
import static com.example.childandroid.DrawBoardActivity.path;

public class display extends View {
    public static ArrayList<Path> paths = new ArrayList();
    public static ArrayList<Integer> colors = new ArrayList();
    public ViewGroup.LayoutParams params;
    public static int current;
    public static int current_color = Color.BLACK;
    private void init(Context context){
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(10f);
        params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public display(Context context) {
        super(context);
        init(context);
    }

    public display(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public display(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                path.moveTo(x,y);
                invalidate();
                return true;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(x,y);
                paths.add(path);
                colors.add(current_color);
                return true;
            default:
                return false;
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < paths.size();i++){
            paint.setColor(colors.get(i));
            canvas.drawPath(paths.get(i),paint);
            invalidate();
        }
    }
}

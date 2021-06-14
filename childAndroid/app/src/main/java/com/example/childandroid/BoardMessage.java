package com.example.childandroid;
import android.graphics.Paint;

import java.util.ArrayList;

public class BoardMessage {
    private ArrayList paths;
    private ArrayList colors;
    private Paint paint;

    public BoardMessage(ArrayList paths, ArrayList colors, Paint paint) {
        this.paths = paths;
        this.colors = colors;
        this.paint = paint;
    }

    public ArrayList getPaths() {
        return paths;
    }

    public void setPaths(ArrayList paths) {
        this.paths = paths;
    }

    public ArrayList getColors() {
        return colors;
    }

    public void setColors(ArrayList colors) {
        this.colors = colors;
    }

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }
}

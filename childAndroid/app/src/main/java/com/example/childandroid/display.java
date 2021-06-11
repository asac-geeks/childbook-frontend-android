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
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import static com.example.childandroid.DrawBoardActivity.paint;
import static com.example.childandroid.DrawBoardActivity.path;

public class display extends View {

    public static ArrayList<Path> paths = new ArrayList();
    public static ArrayList<Integer> colors = new ArrayList();
    public ViewGroup.LayoutParams params;
    public static int current;
    public static int current_color = Color.BLACK;
    private Socket mSocket;
    {
        try {
            mSocket = IO.socket("https://node-sockit-childbook.herokuapp.com/");
        } catch (URISyntaxException e) {
            System.out.println("errrrrrrrrrrrrror " + e);
        }
    };
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
        System.out.println("hereeeeeeeeeee0");

    }



    public display(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        System.out.println("hereeeeeeeeeee1");
        socketInit();
    }

    public display(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        System.out.println("hereeeeeeeeeee2");
    }

    private void socketInit(){
        System.out.println("join Board");
        mSocket.connect();
        mSocket.on("join Board", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject)args[0];
                System.out.println(data);
            };
        });
        mSocket.on("drew message", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject)args[0];
                System.out.println(data);
            };
        });
        System.out.println("join Board");
        JSONObject userId = new JSONObject();
        try {
            userId.put("username", "Username" + " Connected");
            mSocket.emit("join Board", userId);
            System.out.println("join Board");
        } catch (
                JSONException e) {
            e.printStackTrace();
        }
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
        BoardMessage boardMessage = new BoardMessage(paths,colors,paint);
        mSocket.emit("chat message", boardMessage);
        for (int i = 0; i < paths.size();i++){
            paint.setColor(colors.get(i));
            canvas.drawPath(paths.get(i),paint);
            invalidate();
        }
    }
}

package com.example.childandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;
import android.os.Bundle;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONObject;

import java.net.URISyntaxException;

import static com.example.childandroid.display.colors;
import static com.example.childandroid.display.current_color;
import static com.example.childandroid.display.paths;

public class DrawBoardActivity extends AppCompatActivity {
    static Path path = new Path();
    static Paint paint = new Paint();
    private Socket mSocket = null;
    private static final String URL = "http://localhost:9112/bord";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_board);
        try {
            IO.Options ops = new IO.Options();
            ops.forceNew = true;
            ops.reconnection = true;
            mSocket = IO.socket(URL, ops);

        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        mSocket.connect();

        if (mSocket.connected()){
            Toast.makeText(DrawBoardActivity.this, "Socket Connected!!",Toast.LENGTH_SHORT).show();
            System.out.println("Socket Connected!!");
            mSocket.on("/topic/messages/" + "areej", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject)args[0];
                System.out.println(data);
            }
        });
        }else{
            System.out.println("not connect");
        }
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
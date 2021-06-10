package com.example.childandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONObject;

import java.net.URISyntaxException;
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        SocketInstance instance = (SocketInstance)getApplication();
//        ops.query = "Bearer " + "authToken";
//        mSocket = instance.getSocketInstance();

         findViewById(R.id.feeds_button).setOnClickListener(v ->{
              Intent feedPageActivityIntent = new Intent(this, FeedPageActivity.class);
              startActivity(feedPageActivityIntent);
         });
    }



    public void goTo(View view) {
        // Do something in response to button click
        Intent intent = new Intent(this, DrawBoardActivity.class);
//        startActivity(intent);
    }
}
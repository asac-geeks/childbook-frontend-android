package com.example.childandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.childandroid.modules.login;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONObject;

import java.net.URISyntaxException;
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
//        SocketInstance instance = (SocketInstance)getApplication();
//        ops.query = "Bearer " + "authToken";
//        mSocket = instance.getSocketInstance();

         findViewById(R.id.feeds_button).setOnClickListener(v ->{
              Intent feedPageActivityIntent = new Intent(this, FeedPageActivity.class);
              startActivity(feedPageActivityIntent);
         });

        findViewById(R.id.mainGameButton).setOnClickListener(v ->{
            Intent feedPageActivityIntent = new Intent(this, GameDetailsActivity.class);
            startActivity(feedPageActivityIntent);
        });

        findViewById(R.id.parent_account).setOnClickListener(v ->{
            Intent parentIntent = new Intent(this, ParentActivity.class);
            startActivity(parentIntent);
        });


    }

    public void goToBoard(View view) {
        // Do something in response to button click
        Intent intent = new Intent(this, DrawBoardActivity.class);
        startActivity(intent);
    }

    public void goToChat(View view) {
        // Do something in response to button click
        Intent intent = new Intent(this, AddUserActivity.class);
        startActivity(intent);
    }

      public void goSignUp(View view) {
        // Do something in response to button click
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }
    public void goToLogin(View view) {
        // Do something in response to button click
        Intent intent = new Intent(this, login.class);
        startActivity(intent);
    }
}
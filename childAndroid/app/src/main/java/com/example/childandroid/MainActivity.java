package com.example.childandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.childandroid.modules.login;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
//        SocketInstance instance = (SocketInstance)getApplication();
//        ops.query = "Bearer " + "authToken";
//        mSocket = instance.getSocketInstance();

         findViewById(R.id.feeds_button).setOnClickListener(v ->{
              Intent feedPageActivityIntent = new Intent(this, GamesPageActivity.class);
              startActivity(feedPageActivityIntent);
         });

        findViewById(R.id.mainGameButton).setOnClickListener(v ->{
            Intent gameDetail = new Intent(this, GameDetailsActivity.class);
            startActivity(gameDetail);
        });

    }

    public void goToBoard(View view) {
        // Do something in response to button click
        Intent intent = new Intent(this, DrawBoardActivity.class);
//        startActivity(intent);
    }

    public void goToChat(View view) {
        // Do something in response to button click
        Intent intent = new Intent(this, AddUserActivity.class);
        startActivity(intent);
    }

    public void goToLogin(View view) {
        // Do something in response to button click
        Intent intent = new Intent(this, login.class);
        startActivity(intent);
    }
}
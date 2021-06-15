package com.example.childandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class CommentDetailActivity extends AppCompatActivity {
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_detail);
        id =  Integer.valueOf(getIntent().getExtras().getString("id"));

    }
}
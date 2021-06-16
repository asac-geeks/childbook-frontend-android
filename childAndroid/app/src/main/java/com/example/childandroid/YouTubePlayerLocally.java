package com.example.childandroid;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import com.google.android.youtube.player.YouTubeInitializationResult;

public class YouTubePlayerLocally extends YouTubeBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_you_tube_player);

        String id = getIntent().getStringExtra("id");
        String title = getIntent().getStringExtra("title");
        String disc = getIntent().getStringExtra("disc");

        TextView titleOnScreen =findViewById(R.id.youtubeitemtitlePlayer);
        TextView discription = findViewById(R.id.localPlayerDiscription);

        titleOnScreen.setText(title);
        discription.setText(disc);

        runVideo(id);
    }

    public void runVideo(String id) {
        // yazan
//        String api_key = "AIzaSyCl2DUwe_iYExrnevvbsjps2nf2ceTAmo8";
        // husam
        String api_key = "AIzaSyAbytVhdgRyfZDstAIgK2Y5qjNH7O55GY4";

        YouTubePlayerView ytPlayer = (YouTubePlayerView) findViewById(R.id.ytPlayer);

        ytPlayer.initialize(api_key, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo(id);
                youTubePlayer.play();
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Toast.makeText(getApplicationContext(), "Video player Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
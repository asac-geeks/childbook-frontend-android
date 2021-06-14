package com.example.childandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.TimeUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.childandroid.modules.GamesApi;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import java.net.URI;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GameDetailsActivity extends AppCompatActivity {
    ImageView image ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_details);

            Integer id = getIntent().getIntExtra("id",1);
            Log.d("id : ", "is id from game detail activity: " + id);
            makeRequest(id);
    }

    public void makeRequest(Integer id){
        String url = "https://as-childbook.herokuapp.com/games/"+id;
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(0, TimeUnit.SECONDS)
                .readTimeout(0, TimeUnit.SECONDS)
                .writeTimeout(0, TimeUnit.SECONDS)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if(response.isSuccessful()){
                    Gson gson = new Gson();
                    String bodyResponse = response.body().string();
                    GamesApi game = gson.fromJson(bodyResponse,  GamesApi.class);

                    GameDetailsActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            TextView title = findViewById(R.id.gameDetailsTitle);

                            ImageView image = findViewById(R.id.gameDetailsImage);
                            TextView genre = findViewById(R.id.gameDetailsGenre);
                            TextView platform = findViewById(R.id.gameDetailsPlatform);
                            TextView profile = findViewById(R.id.gameDetailsProfile);
                            TextView description = findViewById(R.id.gameDetailsDescription);


                            title.setText(game.getTitle());
                            Glide.with(getApplicationContext())
                                    .load(game.getThumbnail())
                                    .placeholder(R.drawable.ic_launcher_foreground)
                                    .into(image);
                            genre.setText(game.getGenre());
                            platform.setText(game.getPlatform());
                            profile.setText(game.getFreetogame_profile_url());
                            description.setText(game.getShort_description());
                        }
                    });
                }
            }
        });
    }
}
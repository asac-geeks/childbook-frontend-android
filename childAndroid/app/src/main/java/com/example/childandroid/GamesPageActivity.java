package com.example.childandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.childandroid.modules.GamesApi;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GamesPageActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games_page);

        // ---------------------------- Games Api Logic Start-------------------------- //
            findViewById(R.id.feedPageSearchButton).setOnClickListener(v->{
                TextView searchValue = findViewById(R.id.gamesApiSearchTextFeild);
                 String value = searchValue.getText().toString();
                 getDataFromUrl(value);

            });
        // ---------------------------- Games Api Logic End-------------------------- //
    }

    // ---------------------------- Games Api Methods Logic Start-------------------------- //
    public void getDataFromUrl(String searchValue){
        String url = "https://as-childbook.herokuapp.com/games/category/"+ searchValue;
        OkHttpClient httpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if(response.isSuccessful()){
                    // serialize
                    String body = response.body().string();
                    Type listType = new TypeToken<ArrayList<GamesApi>>(){}.getType();
                    List<GamesApi> games = new Gson().fromJson(body, listType);

                    GamesPageActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            GamesAdapter gamesAdapter = new GamesAdapter(games);
                            ListView listView = findViewById(R.id.gamesListView);
                            listView.setAdapter(gamesAdapter);
                        }
                    });
                }
            }
        });
    }
    // ---------------------------- Games Api Methods Logic End-------------------------- //
}
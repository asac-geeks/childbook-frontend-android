package com.example.childandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.childandroid.modules.GamesApi;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FeedPageActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_page);

        // ---------------------------- YouTube Logic Start-------------------------- //
            findViewById(R.id.feedPageSearchButton).setOnClickListener(v->{
                TextView searchValue = findViewById(R.id.youTubeSearchTextFeild);
                 String value = searchValue.getText().toString();
                 getDataFromUrl(value);

            });
        // ---------------------------- YouTube Logic End-------------------------- //

    }
    // ---------------------------- YouTube Methods Logic Start-------------------------- //
    public void getDataFromUrl(String searchValue){
        String url = "http://10.0.2.2:4040/games/category/"+ searchValue;
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
                    Gson gson = new Gson();
                    // serialize
                    String body = response.body().string();
                    Type listType = new TypeToken<ArrayList<GamesApi>>(){}.getType();
                    List<GamesApi> games = new Gson().fromJson(body, listType);

                    FeedPageActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                             for(GamesApi game : games){
                                     Log.d("Game", "Game item : " + game.getId()+" - " + game.getTitle());
                             }

//                        recyclerView.setLayoutManager(new LinearLayoutManager(this));
                            Adapter adapter = new Adapter(games);
                            recyclerView.setAdapter(adapter);
//                            adapter.setOnItemClickListener(MainActivity.this);
                            recyclerView.setAdapter(adapter);
                        }
                    });

                }
            }
        });
    }
    // ---------------------------- YouTube Methods Logic End-------------------------- //
}
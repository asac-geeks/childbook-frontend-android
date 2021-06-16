package com.example.childandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;

import com.example.childandroid.modules.GamesApi;
import com.example.childandroid.modules.youtube.YouTubeApi;
import com.example.childandroid.modules.youtube.YouTubeItems;
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

public class feedsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feeds);

        findViewById(R.id.youtubeSearchButton).setOnClickListener(v -> {
            EditText youTubeSearch = (EditText) findViewById(R.id.youtubeSearchfeild);
            String query =  youTubeSearch.getText().toString();
            queryYouTube(query);
        });
    }

    public void queryYouTube(String query){
//       my query: childrenStories
        String url = "https://as-childbook.herokuapp.com/videos/"+ query;

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

                String body = response.body().string();
                Type listType = new TypeToken<ArrayList<YouTubeApi>>(){}.getType();
                List<YouTubeApi> youTubeApi = new Gson().fromJson(body, listType);

                feedsActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        List<YouTubeItems> youTubeItems = new ArrayList<>();
                        for(YouTubeItems video: youTubeApi.get(0).getItems()){
                            youTubeItems.add(video);
                        }
                        YouTubeAdapter youTubeAdapter = new YouTubeAdapter(youTubeItems);
                        ListView youTubeListView = findViewById(R.id.youTubeListView);
                        youTubeListView.setAdapter(youTubeAdapter);
                    }
                });
            }
        });

    }
}
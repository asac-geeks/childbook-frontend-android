package com.example.childandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FeedPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_page);

        String url = "https://as-childbook.herokuapp.com/games/1";
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
                    String body = response.body().string();
                    FeedPageActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                          TextView bodyView = findViewById(R.id.feed_page);
                           bodyView.setText(body.charAt(1));
                            Log.d("external respource", "external resource" + body );
                        }
                    });
                }
            }
        });

        Log.d("Feed Page", " Feed Page success");
    }
}
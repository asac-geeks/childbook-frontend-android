package com.example.childandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;

import com.example.childandroid.modules.Post;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class TempPostDetailsActivity extends AppCompatActivity {
    Post post = new Post();
    Context that = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_post_details);
        String url = "http://10.0.2.2:4040/post/public/"+  getIntent().getExtras().getString("id");
        OkHttpClient httpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        that = this;
        System.out.println("myHandler: here!"); // Do your work here
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
                    System.out.println("isSuccessful");
                    System.out.println(body);
                    System.out.println(response.body());
                    System.out.println(response.body());
                    post = gson.fromJson(body,  Post.class);
                }
            }
        });
        try{
            Thread.sleep(5000);
            TextView username = findViewById(R.id.temp_post_user);
            username.setText(post.getAppUser().getUserName());
            TextView body = findViewById(R.id.temp_post_body);
            body.setText(post.getBody());
            TextView title = findViewById(R.id.temp_post_title);
            title.setText(post.getPostTitle());
        }catch(InterruptedException e){
            e.printStackTrace();
        }

    }

    public void postAccept(View view){
        HttpLoggingInterceptor loggingInterceptor=new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client=new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();
        String json = "{}";
        RequestBody requestBody = RequestBody.create(json, MediaType.parse("application/json"));
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String token = "Bearer "+preferences.getString("token","");
        Request request = new Request.Builder()
                .url("http://10.0.2.2:4040/postverification/" + getIntent().getExtras().getString("id"))
                .header("Authorization", token)
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                System.out.println("failed");
//                e.printStackTrace();

            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if(response.isSuccessful()){
                    String myResponse=response.body().string();
                    response.code();
                    response.isSuccessful();
                    Intent parentPageActivityIntent = new Intent(that, ChildSignInActivity.class);
                    startActivity(parentPageActivityIntent);
                }
            }
        });
    }
}
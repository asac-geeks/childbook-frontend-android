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

public class UpdatePostActivity extends AppCompatActivity {
    Context that = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_post);
        that = this;
    }


    public void updatePost(View view){
        HttpLoggingInterceptor loggingInterceptor=new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        TextView title = findViewById(R.id.update_post_title);
        TextView body = findViewById(R.id.update_post_body);

        OkHttpClient client=new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();
        String json = "{\"postTitle\":\""+title.getText().toString()+"\",\"body\":\""+body.getText().toString()+"\",\"imageUrl\":\""+""+"\"}";
        RequestBody requestBody = RequestBody.create(json, MediaType.parse("application/json"));
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String token = "Bearer "+preferences.getString("token","");
        Request request = new Request.Builder()
                .url("http://10.0.2.2:4040/post/" + getIntent().getExtras().getString("id"))
                .header("Authorization", token)
                .put(requestBody)
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

                    Intent intent = new Intent(that, PostDelailsActivity.class);
                    intent.putExtra("id",getIntent().getExtras().getString("id"));
                    startActivity(intent);
                }
            }
        });
    }
}
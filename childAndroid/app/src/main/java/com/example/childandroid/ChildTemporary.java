package com.example.childandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.example.childandroid.modules.ChildTemResponse;
import com.example.childandroid.modules.ParentResponse;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ChildTemporary extends AppCompatActivity {
    ChildTemResponse childTemResponse =new ChildTemResponse();
    ParentTemporaryCommentsAdapter parentTemporaryCommentsAdapter;
    ParentTemporaryPostsAdapter parentTemporaryPostsAdapter;
    ParentTemporaryShareAdapter parentTemporaryShareAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_temporary);
        String url = "http://10.0.2.2:4040/childtemp";
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String token = "Bearer "+preferences.getString("token","");
        OkHttpClient httpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", token)
                .build();

        RecyclerView recyclerViewPost = findViewById(R.id.temp_posts);
        RecyclerView recyclerViewComments = findViewById(R.id.temp_comments);
        RecyclerView recyclerViewShares = findViewById(R.id.temp_shares);

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

                    childTemResponse = gson.fromJson(body,  ChildTemResponse.class);
                    System.out.println("after Success");
                    System.out.println(childTemResponse);


                }
            }
        });
        try{
            Thread.sleep(5000);
            System.out.println("children");
            ArrayList arr = new ArrayList();
            arr.addAll(childTemResponse.getComments());

            parentTemporaryCommentsAdapter = new ParentTemporaryCommentsAdapter(this, arr);

            arr = new ArrayList();
            arr.addAll(childTemResponse.getPosts());
            parentTemporaryPostsAdapter = new ParentTemporaryPostsAdapter(this, arr);

            arr = new ArrayList();
            arr.addAll(childTemResponse.getShares());
            parentTemporaryShareAdapter = new ParentTemporaryShareAdapter(this, arr);

            recyclerViewComments.setAdapter(parentTemporaryCommentsAdapter);
            recyclerViewPost.setAdapter(parentTemporaryPostsAdapter);
            recyclerViewShares.setAdapter(parentTemporaryShareAdapter);

            recyclerViewPost.setLayoutManager(new LinearLayoutManager(this));
            recyclerViewShares.setLayoutManager(new LinearLayoutManager(this));
            recyclerViewComments.setLayoutManager(new LinearLayoutManager(this));

        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }
}
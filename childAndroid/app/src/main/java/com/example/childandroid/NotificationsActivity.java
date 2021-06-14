package com.example.childandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;

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

public class NotificationsActivity extends AppCompatActivity {
    ParentTemporaryCommentsAdapter parentTemporaryCommentsAdapter;
    ParentTemporaryPostsAdapter parentTemporaryPostsAdapter;
    ParentTemporaryShareAdapter parentTemporaryShareAdapter;
    ParentResponse parentResponse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        String url = "http://10.0.2.2:4040/parentProfile";
        OkHttpClient httpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyTmFtZTEgUGFyZW50IiwiZXhwIjoxNjIzNTY5Mjg3LCJpYXQiOjE2MjM1MzMyODd9.rE2xIyb0UBpOiaBc16CGJREu4i01R1uPGEXwFzCzyCI")
                .build();

        RecyclerView recyclerViewPost = findViewById(R.id.posts);
        RecyclerView recyclerViewComments = findViewById(R.id.comments);
        RecyclerView recyclerViewShares = findViewById(R.id.shares);

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

                            parentResponse = gson.fromJson(body,  ParentResponse.class);
                            System.out.println("after Success");
                            System.out.println(parentResponse);


                        }
                    }
                });
                try{
                    Thread.sleep(5000);
                    System.out.println("children");
                    ArrayList arr = new ArrayList();
                    arr.addAll(parentResponse.getComments());

                    parentTemporaryCommentsAdapter = new ParentTemporaryCommentsAdapter(this, arr);

                    arr = new ArrayList();
                    arr.addAll(parentResponse.getPosts());
                    parentTemporaryPostsAdapter = new ParentTemporaryPostsAdapter(this, arr);

                    arr = new ArrayList();
                    arr.addAll(parentResponse.getShares());
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
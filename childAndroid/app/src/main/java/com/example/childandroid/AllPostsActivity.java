package com.example.childandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.childandroid.modules.Post;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AllPostsActivity extends AppCompatActivity {
    private ArrayList posts = new ArrayList();
    private RecyclerView recyclerView;
    private PostsAdapter postsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_posts);
        recyclerView = findViewById(R.id.posts_child_res);
        String url = "http://10.0.2.2:4040/feeds";


        OkHttpClient httpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyTmFtZTEgUGFyZW50IiwiZXhwIjoxNjIzNTY5Mjg3LCJpYXQiOjE2MjM1MzMyODd9.rE2xIyb0UBpOiaBc16CGJREu4i01R1uPGEXwFzCzyCI")
                .build();

        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
                System.out.println("erorrrrrrrrrrr");
                System.out.println(e);
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if(response.isSuccessful()){
                    Gson gson = new Gson();
                    String body = response.body().string();
                    System.out.println("isSuccessful Mp Posts");
                    Type listType = new TypeToken<Set<Post>>(){}.getType();
                    posts.addAll(new Gson().fromJson(body, listType));
                }
            }
        });
        try{
            Thread.sleep(5000);
            postsAdapter = new PostsAdapter(this, posts);
            recyclerView.setAdapter(postsAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }
    public void addPost(View view){
        Intent intent = new Intent(this, AddPost_Activity.class);
        startActivity(intent);
    }


}
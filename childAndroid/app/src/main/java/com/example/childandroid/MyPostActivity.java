package com.example.childandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;

import com.example.childandroid.modules.AppUser;
import com.example.childandroid.modules.GamesApi;
import com.example.childandroid.modules.ParentResponse;
import com.example.childandroid.modules.Post;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MyPostActivity extends AppCompatActivity {
    private ArrayList posts = new ArrayList();
    private RecyclerView recyclerView;
    private PostsAdapter postsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_post);
        recyclerView =findViewById(R.id.my_posts_child);
        String url = "http://10.0.2.2:4040/myposts";

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String token = "Bearer "+preferences.getString("token","");
        OkHttpClient httpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", token)
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
}
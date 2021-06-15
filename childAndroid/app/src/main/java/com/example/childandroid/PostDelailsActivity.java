package com.example.childandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.childandroid.modules.ParentResponse;
import com.example.childandroid.modules.Post;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class PostDelailsActivity extends AppCompatActivity {
    Post post = new Post();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_delails);
        String url = "http://10.0.2.2:4040/post/public/"+  getIntent().getExtras().getString("id");
        OkHttpClient httpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();


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
            TextView username = findViewById(R.id.post_detail_username);
            TextView body = findViewById(R.id.post_detail_body);
            TextView title = findViewById(R.id.post_detail_title);
            username.setText(post.getAppUser().getUserName());
            body.setText(post.getBody());
            title.setText(post.getPostTitle());
            Button all_comments = findViewById(R.id.all_comments);
            all_comments.setText("All comments =>" + post.getComments().size());
            Button all_share = findViewById(R.id.all_share);
            all_share.setText("All Shares =>" + post.getShares().size());
        }catch(InterruptedException e){
            e.printStackTrace();
        }

    }

    public void updatePost(View view){
        Intent intent = new Intent(PostDelailsActivity.this,UpdatePostActivity.class);
        intent.putExtra("id",getIntent().getExtras().getString("id"));
        startActivity(intent);
    }
    public void deletePost(View view){

        HttpLoggingInterceptor loggingInterceptor=new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client=new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String token = "Bearer "+preferences.getString("token","");

        Request request = new Request.Builder()
                .url("http://10.0.2.2:4040/post/"+getIntent().getExtras().getString("id"))
                .delete()
                .header("Authorization", token)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if(response.isSuccessful()){
                    String myResponse=response.body().string();
                    response.code();
                    response.isSuccessful();
                    Intent intent=new Intent(PostDelailsActivity.this,ChildActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    public void viewShares(View view){
        Intent intent=new Intent(PostDelailsActivity.this,MyPostAllShareActivity.class);
        intent.putExtra("id",getIntent().getExtras().getString("id"));
        startActivity(intent);

    }
    public void allComments(View view){
        Intent intent=new Intent(PostDelailsActivity.this,MyPostAllCommentsActivity.class);
        intent.putExtra("id",getIntent().getExtras().getString("id"));
        startActivity(intent);
    }

    public void addComment(View view){
        HttpLoggingInterceptor loggingInterceptor=new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client=new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();


        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String token = "Bearer "+preferences.getString("token","");
        EditText my_post_comment = findViewById(R.id.my_post_comment);
        String json = "{\"body\":\""+my_post_comment+"\"}";
        RequestBody requestBody = RequestBody.create(json, MediaType.parse("application/json"));
        Request request = new Request.Builder()
                .url("http://10.0.2.2:4040/addComment/"+getIntent().getExtras().getString("id"))
                .post(requestBody)
                .header("Authorization", token)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if(response.isSuccessful()){
                    Intent intent=new Intent(PostDelailsActivity.this,MyPostAllCommentsActivity.class);
                    intent.putExtra("id",getIntent().getExtras().getString("id"));
                    startActivity(intent);
                }
            }
        });
    }
}
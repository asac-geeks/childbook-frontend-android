package com.example.childandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.childandroid.modules.Post;
import com.google.android.material.navigation.NavigationView;
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

public class PostDetailAllActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{


    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Post post = new Post();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail_all);
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
//        ======================navigation bar======================


//        =======================================Hooks
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar_view);
//        =======================================Tool Bar
        setSupportActionBar(toolbar);
//        =======================================Navigation Drawer Menu

// ============================== Hide not needed items from navBar
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String token = "Bearer " + preferences.getString("token", "");
        String checker = preferences.getString("token", "");
        Menu menu = navigationView.getMenu();
        System.out.println( preferences.getString("token", ""));
        if (preferences.getString("token", "").equals("")) {
            menu.findItem(R.id.nav_child_logout).setVisible(false);
            menu.findItem(R.id.nav_parent_logout).setVisible(false);
            menu.findItem(R.id.nav_child_profile).setVisible(false);
            menu.findItem(R.id.nav_parent_profile).setVisible(false);
            menu.findItem(R.id.nav_parent_login).setVisible(true);
            menu.findItem(R.id.nav_child_login).setVisible(true);
            menu.findItem(R.id.nav_child_signUp).setVisible(true);
            menu.findItem(R.id.nav_chat).setVisible(false);
            menu.findItem(R.id.nav_find_friend).setVisible(false);
            menu.findItem(R.id.my_friends_Posts).setVisible(false);

        } else {
            menu.findItem(R.id.nav_child_logout).setVisible(true);
            menu.findItem(R.id.nav_parent_logout).setVisible(true);
            menu.findItem(R.id.nav_child_profile).setVisible(true);
            menu.findItem(R.id.nav_parent_profile).setVisible(true);
            menu.findItem(R.id.nav_parent_login).setVisible(false);
            menu.findItem(R.id.nav_child_login).setVisible(false);
            menu.findItem(R.id.nav_child_signUp).setVisible(false);
            menu.findItem(R.id.nav_chat).setVisible(true);
            menu.findItem(R.id.nav_find_friend).setVisible(true);
            menu.findItem(R.id.my_friends_Posts).setVisible(true);
        }


        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);
    }
    //    ==================================to prevent go out of the the app
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen((GravityCompat.START))) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
        Intent intent = new Intent();
        switch (item.getItemId()) {
            case R.id.nav_home:
                intent = new Intent(PostDetailAllActivity.this, MainActivity.class);

                break;
            case R.id.nav_youtube:
                intent = new Intent(PostDetailAllActivity.this, feedsActivity.class);
                break;
            case R.id.nav_our_games:
                intent = new Intent(PostDetailAllActivity.this, GamesPageActivity.class);
                break;
            case R.id.nav_whiteboard:
                intent = new Intent(PostDetailAllActivity.this, DrawBoardActivity.class);
                break;
            case R.id.nav_child_login:
                intent = new Intent(PostDetailAllActivity.this, ChildSignInActivity.class);
                break;
            case R.id.nav_child_profile:
                intent = new Intent(PostDetailAllActivity.this, ChildActivity.class);
                break;
            case R.id.nav_child_logout:
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor = preferences.edit();
                editor.remove("token");
                editor.commit();
                intent = new Intent(PostDetailAllActivity.this, MainActivity.class);
                break;
            case R.id.nav_parent_login:
                intent = new Intent(PostDetailAllActivity.this, ParentSignInActivity.class);
                break;
            case R.id.nav_parent_profile:
                intent = new Intent(PostDetailAllActivity.this, ParentActivity.class);
                break;
            case R.id.nav_parent_logout:
                preferences = PreferenceManager.getDefaultSharedPreferences(this);
                editor = preferences.edit();
                editor.remove("token");
                editor.commit();
                intent = new Intent(PostDetailAllActivity.this, MainActivity.class);
                break;
            case R.id.nav_child_signUp:
                intent = new Intent(PostDetailAllActivity.this, SignUp.class);
                break;
            case R.id.nav_chat:
                intent = new Intent(PostDetailAllActivity.this, ChatActivity.class);
                break;
            case R.id.nav_find_friend:
                intent = new Intent(PostDetailAllActivity.this, FindUser.class);
                break;
            case R.id.my_friends_Posts:
                intent = new Intent(PostDetailAllActivity.this, AllPostsActivity.class);
                break;
        }
        startActivity(intent);
        return true;
    }

    public void updatePost(View view){
        Intent intent = new Intent(PostDetailAllActivity.this,UpdatePostActivity.class);
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
                    Intent intent=new Intent(PostDetailAllActivity.this,ChildActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    public void viewShares(View view){
        Intent intent=new Intent(PostDetailAllActivity.this,MyPostAllShareActivity.class);
        intent.putExtra("id",getIntent().getExtras().getString("id"));
        startActivity(intent);

    }
    public void allComments(View view){
        Intent intent=new Intent(PostDetailAllActivity.this,MyPostAllCommentsActivity.class);
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
                    Intent intent=new Intent(PostDetailAllActivity.this,MyPostAllCommentsActivity.class);
                    intent.putExtra("id",getIntent().getExtras().getString("id"));
                    startActivity(intent);
                }
            }
        });
    }
}
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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;

import com.example.childandroid.modules.GamesApi;
import com.example.childandroid.modules.youtube.YouTubeApi;
import com.example.childandroid.modules.youtube.YouTubeItems;
import com.google.android.material.navigation.NavigationView;
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

public class feedsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feeds);

        findViewById(R.id.youtubeSearchButton).setOnClickListener(v -> {
            EditText youTubeSearch = (EditText) findViewById(R.id.youtubeSearchfeild);
            String query =  youTubeSearch.getText().toString();
            queryYouTube(query);
        });

        // ---------------------------- Games Api Logic End-------------------------- //
//  =======================================Hooks
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar_view);
//  =======================================Tool Bar
        setSupportActionBar(toolbar);
//  =======================================Navigation Drawer Menu
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
        // ============================== End Of Nav ========================= \\
    }

    public void queryYouTube(String query){
    //  my query: childrenStories
        String url = "http://10.0.2.2:4040/videos/"+ query;

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

    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
        Intent intent = new Intent();
        switch (item.getItemId()) {
            case R.id.nav_home:
                intent = new Intent(feedsActivity.this, MainActivity.class);

                break;
            case R.id.nav_youtube:
                intent = new Intent(feedsActivity.this, feedsActivity.class);
                break;
            case R.id.nav_our_games:
                intent = new Intent(feedsActivity.this, GamesPageActivity.class);
                break;
            case R.id.nav_whiteboard:
                intent = new Intent(feedsActivity.this, DrawBoardActivity.class);
                break;
            case R.id.nav_child_login:
                intent = new Intent(feedsActivity.this, ChildSignInActivity.class);
                break;
            case R.id.nav_child_profile:
                intent = new Intent(feedsActivity.this, ChildActivity.class);
                break;
            case R.id.nav_child_logout:
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor = preferences.edit();
                editor.remove("token");
                editor.commit();
                intent = new Intent(feedsActivity.this, MainActivity.class);
                break;
            case R.id.nav_parent_login:
                intent = new Intent(feedsActivity.this, ParentSignInActivity.class);
                break;
            case R.id.nav_parent_profile:
                intent = new Intent(feedsActivity.this, ParentActivity.class);
                break;
            case R.id.nav_parent_logout:
                preferences = PreferenceManager.getDefaultSharedPreferences(this);
                editor = preferences.edit();
                editor.remove("token");
                editor.commit();
                intent = new Intent(feedsActivity.this, MainActivity.class);
                break;
            case R.id.nav_child_signUp:
                intent = new Intent(feedsActivity.this, SignUp.class);
                break;
            case R.id.nav_chat:
                intent = new Intent(feedsActivity.this, ChatActivity.class);
                break;
            case R.id.nav_find_friend:
                intent = new Intent(feedsActivity.this, FindUser.class);
                break;
            case R.id.my_friends_Posts:
                intent = new Intent(feedsActivity.this, AllPostsActivity.class);
                break;
        }
        startActivity(intent);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen((GravityCompat.START))) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
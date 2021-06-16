package com.example.childandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.TimeUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.childandroid.modules.GamesApi;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import java.net.URI;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GameDetailsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    ImageView image;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_details);

            Integer id = getIntent().getIntExtra("id",1);
            makeRequest(id);

        // ---------------------------- Games Api Logic End-------------------------- //
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
        // ============================== End Of Nav ========================= \\
    }

    public void makeRequest(Integer id){
        String url = "http://10.0.2.2:4040/games/"+id;
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
                if(response.isSuccessful()){
                    Gson gson = new Gson();
                    String bodyResponse = response.body().string();
                    GamesApi game = gson.fromJson(bodyResponse,  GamesApi.class);

                    GameDetailsActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            TextView title = findViewById(R.id.gameDetailsTitle);

                            ImageView image = findViewById(R.id.gameDetailsImage);
                            TextView genre = findViewById(R.id.gameDetailsGenre);
                            TextView platform = findViewById(R.id.gameDetailsPlatform);
                            TextView profile = findViewById(R.id.gameDetailsProfile);
                            TextView description = findViewById(R.id.gameDetailsDescription);

                            title.setText(game.getTitle());
                            Glide.with(getApplicationContext())
                                    .load(game.getThumbnail())
                                    .placeholder(R.drawable.ic_launcher_foreground)
                                    .into(image);
                            genre.setText(game.getGenre());
                            platform.setText(game.getPlatform());
                            profile.setText(game.getFreetogame_profile_url());
                            description.setText(game.getShort_description());
                        }
                    });
                }
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
        Intent intent = new Intent();
        switch (item.getItemId()) {
            case R.id.nav_home:
                intent = new Intent(GameDetailsActivity.this, MainActivity.class);

                break;
            case R.id.nav_youtube:
                intent = new Intent(GameDetailsActivity.this, feedsActivity.class);
                break;
            case R.id.nav_our_games:
                intent = new Intent(GameDetailsActivity.this, GamesPageActivity.class);
                break;
            case R.id.nav_whiteboard:
                intent = new Intent(GameDetailsActivity.this, DrawBoardActivity.class);
                break;
            case R.id.nav_child_login:
                intent = new Intent(GameDetailsActivity.this, ChildSignInActivity.class);
                break;
            case R.id.nav_child_profile:
                intent = new Intent(GameDetailsActivity.this, ChildActivity.class);
                break;
            case R.id.nav_child_logout:
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor = preferences.edit();
                editor.remove("token");
                editor.commit();
                intent = new Intent(GameDetailsActivity.this, MainActivity.class);
                break;
            case R.id.nav_parent_login:
                intent = new Intent(GameDetailsActivity.this, ParentSignInActivity.class);
                break;
            case R.id.nav_parent_profile:
                intent = new Intent(GameDetailsActivity.this, ParentActivity.class);
                break;
            case R.id.nav_parent_logout:
                preferences = PreferenceManager.getDefaultSharedPreferences(this);
                editor = preferences.edit();
                editor.remove("token");
                editor.commit();
                intent = new Intent(GameDetailsActivity.this, MainActivity.class);
                break;
            case R.id.nav_child_signUp:
                intent = new Intent(GameDetailsActivity.this, SignUp.class);
                break;
            case R.id.nav_chat:
                intent = new Intent(GameDetailsActivity.this, ChatActivity.class);
                break;
            case R.id.nav_find_friend:
                intent = new Intent(GameDetailsActivity.this, FindUser.class);
                break;
            case R.id.my_friends_Posts:
                intent = new Intent(GameDetailsActivity.this, AllPostsActivity.class);
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
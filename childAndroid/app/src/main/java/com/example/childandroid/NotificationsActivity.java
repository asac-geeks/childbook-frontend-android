package com.example.childandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;

import com.example.childandroid.modules.ParentResponse;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NotificationsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    ParentTemporaryCommentsAdapter parentTemporaryCommentsAdapter;
    ParentTemporaryPostsAdapter parentTemporaryPostsAdapter;
    ParentTemporaryShareAdapter parentTemporaryShareAdapter;
    ParentResponse parentResponse;


    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        String url = "http://10.0.2.2:4040/parentProfile";
        OkHttpClient httpClient = new OkHttpClient();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String token = "Bearer " + preferences.getString("token", "");
        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", token)
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
                if (response.isSuccessful()) {
                    Gson gson = new Gson();
                    // serialize
                    String body = response.body().string();
                    System.out.println("isSuccessful");
                    System.out.println(body);
                    System.out.println(response.body());
                    System.out.println(response.body());

                    parentResponse = gson.fromJson(body, ParentResponse.class);
                    System.out.println("after Success");
                    System.out.println(parentResponse);


                }
            }
        });
        try {
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

        } catch (InterruptedException e) {
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
        Menu menu = navigationView.getMenu();
        if (preferences.getString("token", "").equals("")) {
            menu.findItem(R.id.nav_child_logout).setVisible(false);
            menu.findItem(R.id.nav_parent_logout).setVisible(false);
            menu.findItem(R.id.nav_child_profile).setVisible(false);
            menu.findItem(R.id.nav_parent_profile).setVisible(false);
            menu.findItem(R.id.nav_parent_login).setVisible(true);
            menu.findItem(R.id.nav_child_login).setVisible(true);
            menu.findItem(R.id.nav_child_signUp).setVisible(true);
            menu.findItem(R.id.nav_chat).setVisible(false);

        } else {
            menu.findItem(R.id.nav_child_logout).setVisible(true);
            menu.findItem(R.id.nav_parent_logout).setVisible(true);
            menu.findItem(R.id.nav_child_profile).setVisible(true);
            menu.findItem(R.id.nav_parent_profile).setVisible(true);
            menu.findItem(R.id.nav_parent_login).setVisible(false);
            menu.findItem(R.id.nav_child_login).setVisible(false);
            menu.findItem(R.id.n        }
        av_child_signUp).setVisible(false);
            menu.findItem(R.id.nav_chat).setVisible(true);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);
        Context context = this;
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
                break;
            case R.id.nav_youtube:
                intent = new Intent(NotificationsActivity.this, feedsActivity.class);
                break;
            case R.id.nav_our_games:
                intent = new Intent(NotificationsActivity.this, GamesPageActivity.class);
                break;
            case R.id.nav_whiteboard:
                intent = new Intent(NotificationsActivity.this, DrawBoardActivity.class);
                break;
            case R.id.nav_child_login:
                intent = new Intent(NotificationsActivity.this, ChildSignInActivity.class);
                break;
            case R.id.nav_child_profile:
                intent = new Intent(NotificationsActivity.this, ChildActivity.class);
                break;
            case R.id.nav_child_logout:
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor = preferences.edit();
                editor.remove("token");
                editor.commit();
                intent = new Intent(NotificationsActivity.this, MainActivity.class);
                break;
            case R.id.nav_parent_login:
                intent = new Intent(NotificationsActivity.this, ParentSignInActivity.class);
                break;
            case R.id.nav_parent_profile:
                intent = new Intent(NotificationsActivity.this, ParentActivity.class);
                break;
            case R.id.nav_parent_logout:
                preferences = PreferenceManager.getDefaultSharedPreferences(this);
                intent = new Intent(NotificationsActivity.this, MainActivity.class);
                editor = preferences.edit();
                editor.remove("token");
                editor.commit();
            case R.id.nav_chat:
                intent = new Intent(NotificationsActivity.this, ChatActivity.class);
                break;
        }
        startActivity(intent);
        return true;
    }
}
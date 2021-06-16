package com.example.childandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;

import com.example.childandroid.modules.ChildTemResponse;
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

public class ChildTemporary extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    ChildTemResponse childTemResponse = new ChildTemResponse();
    ParentTemporaryCommentsAdapter parentTemporaryCommentsAdapter;
    ParentTemporaryPostsAdapter parentTemporaryPostsAdapter;
    ParentTemporaryShareAdapter parentTemporaryShareAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_temporary);
        String url = "http://10.0.2.2:4040/childtemp";
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String token = "Bearer " + preferences.getString("token", "");
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
                if (response.isSuccessful()) {
                    Gson gson = new Gson();
                    // serialize
                    String body = response.body().string();
                    System.out.println("isSuccessful");
                    System.out.println(body);
                    System.out.println(response.body());
                    System.out.println(response.body());

                    childTemResponse = gson.fromJson(body, ChildTemResponse.class);
                    System.out.println("after Success");
                    System.out.println(childTemResponse);


                }
            }
        });
        try {
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
        String checker = preferences.getString("token", "");
        if (checker.equals("")) {
            menu.findItem(R.id.nav_child_logout).setVisible(false);
            menu.findItem(R.id.nav_parent_logout).setVisible(false);
            menu.findItem(R.id.nav_child_profile).setVisible(false);
            menu.findItem(R.id.nav_parent_profile).setVisible(false);
            menu.findItem(R.id.nav_parent_login).setVisible(true);
            menu.findItem(R.id.nav_child_login).setVisible(true);
        } else {
            menu.findItem(R.id.nav_child_logout).setVisible(true);
            menu.findItem(R.id.nav_parent_logout).setVisible(true);
            menu.findItem(R.id.nav_child_profile).setVisible(true);
            menu.findItem(R.id.nav_parent_profile).setVisible(true);
            menu.findItem(R.id.nav_parent_login).setVisible(false);
            menu.findItem(R.id.nav_child_login).setVisible(false);
        }
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
                intent = new Intent(ChildTemporary.this, feedsActivity.class);
                break;
            case R.id.nav_our_games:
                intent = new Intent(ChildTemporary.this, GamesPageActivity.class);
                break;
            case R.id.nav_whiteboard:
                intent = new Intent(ChildTemporary.this, DrawBoardActivity.class);
                break;
            case R.id.nav_child_login:
                intent = new Intent(ChildTemporary.this, ChildSignInActivity.class);
                break;
            case R.id.nav_child_profile:
                intent = new Intent(ChildTemporary.this, ChildActivity.class);
                break;
            case R.id.nav_child_logout:
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor = preferences.edit();
                editor.remove("token");
                editor.commit();
                break;
            case R.id.nav_parent_login:
                intent = new Intent(ChildTemporary.this, ParentSignInActivity.class);
                break;
            case R.id.nav_parent_profile:
                intent = new Intent(ChildTemporary.this, ParentActivity.class);
                break;
            case R.id.nav_parent_logout:
                preferences = PreferenceManager.getDefaultSharedPreferences(this);
                editor = preferences.edit();
                editor.remove("token");
                editor.commit();
                break;
        }
        startActivity(intent);
        return true;
    }
}
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
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.childandroid.modules.AppUser;
import com.example.childandroid.modules.ParentResponse;
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

public class FindUser extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Button userFollow;
    AppUser appUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_user);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String token = "Bearer " + preferences.getString("token", "");
        final Handler handler = new Handler();
        EditText findUser = findViewById(R.id.findUser);


        findViewById(R.id.findUserButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("button");
                String url = "http://192.168.1.82:8090/user/" + findUser.getText().toString();
                System.out.println(url);
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(url)
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        e.printStackTrace();
                        System.out.println("FAILED");
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        if (response.isSuccessful()) {

                            Gson gson = new Gson();
                            response.code();
                            response.isSuccessful();
                            appUser = gson.fromJson(response.body().string(), AppUser.class);

                            String json = "{\"id\":\"" + appUser.getId() + "\"}";
                            System.out.println(json);
                            RequestBody requestBody = RequestBody.create(json, MediaType.parse("application/json"));
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    findViewById(R.id.userInfo).setVisibility(View.VISIBLE);
                                    findViewById(R.id.userFollow).setVisibility(View.VISIBLE);
                                    findViewById(R.id.Follow_card).setVisibility(View.VISIBLE);
                                    TextView userInfo = findViewById(R.id.userInfo);
                                    userInfo.setText(appUser.getUserName());
                                    findViewById(R.id.userFollow).setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            String URL = "http://192.168.1.82:8090/follow/" + appUser.getId();
                                            System.out.println(URL);
                                            OkHttpClient httpClient = new OkHttpClient();
                                            Request request1 = new Request.Builder()
                                                    .url(URL)
                                                    .post(requestBody)
                                                    .header("Authorization", token)
                                                    .build();
                                            httpClient.newCall(request1).enqueue(new Callback() {
                                                @Override
                                                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                                                    System.out.println("Failed");
                                                }

                                                @Override
                                                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                                                    response.isSuccessful();
                                                    response.code();
                                                    System.out.println(response.code());
                                                }
                                            });


                                        }
                                    });
                                }
                            });
                        } else {
                            System.out.println("empty");
                        }
                    }
                });

            }
        });


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
            menu.findItem(R.id.nav_child_signUp).setVisible(false);
            menu.findItem(R.id.nav_chat).setVisible(true);
        }
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);
    }
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
                intent = new Intent(FindUser.this, MainActivity.class);
                break;
            case R.id.nav_youtube:
                intent = new Intent(FindUser.this, feedsActivity.class);
                break;
            case R.id.nav_our_games:
                intent = new Intent(FindUser.this, GamesPageActivity.class);
                break;
            case R.id.nav_whiteboard:
                intent = new Intent(FindUser.this, DrawBoardActivity.class);
                break;
            case R.id.nav_child_login:
                intent = new Intent(FindUser.this, ChildSignInActivity.class);
                break;
            case R.id.nav_child_profile:
                intent = new Intent(FindUser.this, ChildActivity.class);
                break;
            case R.id.nav_child_logout:
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor = preferences.edit();
                intent = new Intent(FindUser.this, MainActivity.class);
                editor.remove("token");
                editor.commit();
                intent = new Intent(FindUser.this, MainActivity.class);
                break;
            case R.id.nav_parent_login:
                intent = new Intent(FindUser.this, ParentSignInActivity.class);
                break;
            case R.id.nav_parent_profile:
                intent = new Intent(FindUser.this, ParentActivity.class);
                break;
            case R.id.nav_parent_logout:
                preferences = PreferenceManager.getDefaultSharedPreferences(this);
                intent = new Intent(FindUser.this, MainActivity.class);
                editor = preferences.edit();
                editor.remove("token");
                editor.commit();
            case R.id.nav_chat:
                intent = new Intent(FindUser.this, ChatActivity.class);
                break;
        }
        startActivity(intent);
        return true;
    }

}
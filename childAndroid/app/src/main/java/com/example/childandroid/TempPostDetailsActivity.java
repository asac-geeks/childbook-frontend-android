package com.example.childandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.childandroid.modules.Post;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Calendar;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class TempPostDetailsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Post post = new Post();
    Context that = this;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_post_details);
        String url = "http://10.0.2.2:4040/post/public/" + getIntent().getExtras().getString("id");
        OkHttpClient httpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();



        findViewById(R.id.accept).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar calendar = Calendar.getInstance();
                        int year = calendar.get(Calendar.YEAR);
                        int month = calendar.get(Calendar.MONTH);
                        int day = calendar.get(Calendar.DAY_OF_MONTH);
                        DatePickerDialog dialog = new DatePickerDialog(SignUp.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDataSetListner, year, month, day);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.show();
                    }
                });
        that = this;
        System.out.println("myHandler: here!"); // Do your work here
        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    postAccept();
                }
            }
        });
        try {
            Thread.sleep(5000);
            TextView username = findViewById(R.id.temp_post_user);
            username.setText(post.getAppUser().getUserName());
            TextView body = findViewById(R.id.temp_post_body);
            body.setText(post.getBody());
            TextView title = findViewById(R.id.temp_post_title);
            title.setText(post.getPostTitle());
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
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
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
                intent = new Intent(TempPostDetailsActivity.this, feedsActivity.class);
                break;
            case R.id.nav_our_games:
                intent = new Intent(TempPostDetailsActivity.this, GamesPageActivity.class);
                break;
            case R.id.nav_whiteboard:
                intent = new Intent(TempPostDetailsActivity.this, DrawBoardActivity.class);
                break;
            case R.id.nav_child_login:
                intent = new Intent(TempPostDetailsActivity.this, ChildSignInActivity.class);
                break;
            case R.id.nav_child_profile:
                intent = new Intent(TempPostDetailsActivity.this, ChildActivity.class);
                break;
            case R.id.nav_child_logout:
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor = preferences.edit();
                editor.remove("token");
                editor.commit();
                break;
            case R.id.nav_parent_login:
                intent = new Intent(TempPostDetailsActivity.this, ParentSignInActivity.class);
                break;
            case R.id.nav_parent_profile:
                intent = new Intent(TempPostDetailsActivity.this, ParentActivity.class);
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

    public void postAccept() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();
        String json = "{}";
        RequestBody requestBody = RequestBody.create(json, MediaType.parse("application/json"));
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String token = "Bearer " + preferences.getString("token", "");
        Request request = new Request.Builder()
                .url("http://10.0.2.2:4040/postverification/" + getIntent().getExtras().getString("id"))
                .header("Authorization", token)
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                System.out.println("failed");
//                e.printStackTrace();

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String myResponse = response.body().string();
                    response.code();
                    response.isSuccessful();
                    Intent parentPageActivityIntent = new Intent(that, ChildSignInActivity.class);
                    startActivity(parentPageActivityIntent);
                }
            }
        });
    }
}
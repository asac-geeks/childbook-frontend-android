package com.example.childandroid;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
//
//import com.example.childandroid.modules.Parent;
//import com.example.childandroid.modules.ParentResponse;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UpdateParentAccountActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{


    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_parent_account);
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
                intent = new Intent(UpdateParentAccountActivity.this, MainActivity.class);
                break;
            case R.id.nav_youtube:
                intent = new Intent(UpdateParentAccountActivity.this, feedsActivity.class);
                break;
            case R.id.nav_our_games:
                intent = new Intent(UpdateParentAccountActivity.this, GamesPageActivity.class);
                break;
            case R.id.nav_whiteboard:
                intent = new Intent(UpdateParentAccountActivity.this, DrawBoardActivity.class);
                break;
            case R.id.nav_child_login:
                intent = new Intent(UpdateParentAccountActivity.this, ChildSignInActivity.class);
                break;
            case R.id.nav_child_profile:
                intent = new Intent(UpdateParentAccountActivity.this, ChildActivity.class);
                break;
            case R.id.nav_child_logout:
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor = preferences.edit();
                intent = new Intent(UpdateParentAccountActivity.this, MainActivity.class);
                editor.remove("token");
                editor.commit();
                intent = new Intent(UpdateParentAccountActivity.this, MainActivity.class);
                break;
            case R.id.nav_parent_login:
                intent = new Intent(UpdateParentAccountActivity.this, ParentSignInActivity.class);
                break;
            case R.id.nav_parent_profile:
                intent = new Intent(UpdateParentAccountActivity.this, ParentActivity.class);
                break;
            case R.id.nav_parent_logout:
                preferences = PreferenceManager.getDefaultSharedPreferences(this);
                intent = new Intent(UpdateParentAccountActivity.this, MainActivity.class);
                editor = preferences.edit();
                editor.remove("token");
                editor.commit();
            case R.id.nav_chat:
                intent = new Intent(UpdateParentAccountActivity.this, ChatActivity.class);
                break;
        }
        startActivity(intent);
        return true;
    }
    public void update_parent(View v){

        EditText  username = findViewById(R.id.parent_username);
        EditText  email = findViewById(R.id.parent_email);
        EditText  password = findViewById(R.id.parent_password);
        Context context = this;
        TextView requiredField = findViewById(R.id.required_field);
        if(!username.getText().toString().equals("") && !email.getText().toString().equals("") && !password.getText().toString().equals("")){
            System.out.println("updateParent");
            String url = "http://10.0.2.2:4040/updateparent";

            String json = "{\"userName\":\""+username.getText().toString()+"\",\"parentPassword\":\""+password.getText().toString()+"\",\"parentEmail\":\""+email.getText().toString()+"\"}";


            RequestBody requestBody = RequestBody.create(json, MediaType.parse("application/json"));
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            String token = "Bearer "+preferences.getString("token","");
            System.out.println("Parent token");
            System.out.println(token);
            OkHttpClient httpClient = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .put(requestBody)
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
                        System.out.println("isSuccessfulUpdate");
                        Intent parentPageActivityIntent = new Intent(context, ParentSignInActivity.class);
                        startActivity(parentPageActivityIntent);
                    }
                }
            });
            requiredField.setText("");
        }else {
            requiredField.setText("Enter Required Fields");
        }
    }

//    @Override
//    public boolean equals(@Nullable @org.jetbrains.annotations.Nullable Object obj) {
//        return super.equals(obj);
//    }
}
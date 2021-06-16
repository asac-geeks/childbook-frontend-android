package com.example.childandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.childandroid.modules.AppUser;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;


import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChildActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private FusedLocationProviderClient fusedLocationClient;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private AppUser childData = new AppUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child);
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
        String token = "Bearer " + preferences.getString("token", "");
        String checker = preferences.getString("token", "");
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
        requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 2);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        CardView add_post = findViewById(R.id. add_post);
        add_post.setOnClickListener(v->{
            Intent intent = new Intent(this, AddPost_Activity.class);
            startActivity(intent);
        });
        CardView child_posts = findViewById(R.id.child_posts);
        child_posts.setOnClickListener(v->{
            Intent intent = new Intent(this, MyPostActivity.class);
            startActivity(intent);
        });
        CardView update_profile_child = findViewById(R.id.update_profile_child);
        update_profile_child.setOnClickListener(v->{
            Intent intent = new Intent(this, UpdateChildActivity.class);
            startActivity(intent);
        });

        CardView temp = findViewById(R.id.button7);
        temp.setOnClickListener(v->{
            Intent intent = new Intent(this, ChildTemporary.class);
            startActivity(intent);
        });

        String url = "http://10.0.2.2:4040/profile";
        System.out.println("token");
        System.out.println(token);
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
                if (response.isSuccessful()) {
                    Gson gson = new Gson();
                    System.out.println("isSuccessfulUpdate");
                    String body = response.body().string();
                    System.out.println("isSuccessful");
                    System.out.println(body);
                    System.out.println(response.body());
                    System.out.println(response.body());
                    childData = gson.fromJson(body, AppUser.class);
                }
            }

        });
        try {
            Thread.sleep(5000);
            System.out.println(childData);
            System.out.println("children");
            TextView email = findViewById(R.id.child_parent_email);
            System.out.println("childData.getEmail()");
            System.out.println(childData.getEmail());
            System.out.println(childData.getUserName());

            email.setText(childData.getEmail());

            TextView username = findViewById(R.id.child_username);
            username.setText(childData.getUserName());

//            TextView date = findViewById(R.id.child_date);
//            date.setText(childData.getDateOfBirth().toString());

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        System.out.println("location1");
                        System.out.println(location);
                        if (location != null) {
                            System.out.println(location.getLongitude());
                            System.out.println(location.getLatitude());
                            String url = "http://10.0.2.2:4040/userLocation";
                            String locationString = Double.toString(location.getLatitude())+"," + Double.toString(location.getLongitude());
                            String json = "{\"location\":\"" + locationString + "\"}";

                            RequestBody body = RequestBody.create(JSON, json);


                            OkHttpClient httpClient = new OkHttpClient();
                            Request request = new Request.Builder()
                                    .url(url)
                                    .put(body)
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
                                    if (response.isSuccessful()) {
                                        Gson gson = new Gson();
                                        System.out.println("isSuccessfulUpdate");
                                    }
                                }
                            });
                        } else {
                            System.out.println("location is null");
                        }

                    }
                });

    }

    public void myPosts(View view) {
        Intent intent = new Intent(this, MyPostActivity.class);
        startActivity(intent);
    }

    public void addPost(View view) {
        Intent intent = new Intent(this, AddPost_Activity.class);
        startActivity(intent);
    }

    public void goToTemporaryChild(View view) {
        Intent intent = new Intent(this, ChildTemporary.class);
        startActivity(intent);
    }

    public void updateChild(View view) {
        // Do something in response to button click
        Intent intent = new Intent(this, UpdateChildActivity.class);
        startActivity(intent);
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
                intent = new Intent(ChildActivity.this, MainActivity.class);

                break;
            case R.id.nav_youtube:
                intent = new Intent(ChildActivity.this, feedsActivity.class);
                break;
            case R.id.nav_our_games:
                intent = new Intent(ChildActivity.this, GamesPageActivity.class);
                break;
            case R.id.nav_whiteboard:
                intent = new Intent(ChildActivity.this, DrawBoardActivity.class);
                break;
            case R.id.nav_child_login:
                intent = new Intent(ChildActivity.this, ChildSignInActivity.class);
                break;
            case R.id.nav_child_profile:
                intent = new Intent(ChildActivity.this, ChildActivity.class);
                break;
            case R.id.nav_child_logout:
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor = preferences.edit();
                editor.remove("token");
                editor.commit();
                intent = new Intent(ChildActivity.this, MainActivity.class);
                break;
            case R.id.nav_parent_login:
                intent = new Intent(ChildActivity.this, ParentSignInActivity.class);
                break;
            case R.id.nav_parent_profile:
                intent = new Intent(ChildActivity.this, ParentActivity.class);
                break;
            case R.id.nav_parent_logout:
                preferences = PreferenceManager.getDefaultSharedPreferences(this);
                editor = preferences.edit();
                editor.remove("token");
                editor.commit();
                intent = new Intent(ChildActivity.this, MainActivity.class);
                break;
            case R.id.nav_child_signUp:
                intent = new Intent(ChildActivity.this, SignUp.class);
                break;
            case R.id.nav_chat:
                intent = new Intent(ChildActivity.this, ChatActivity.class);
                break;

        }
        startActivity(intent);
        return true;
    }
//    ====================================================================


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChildActivity that = (ChildActivity) o;
        return Objects.equals(fusedLocationClient, that.fusedLocationClient) &&
                Objects.equals(drawerLayout, that.drawerLayout) &&
                Objects.equals(navigationView, that.navigationView) &&
                Objects.equals(toolbar, that.toolbar) &&
                Objects.equals(childData, that.childData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fusedLocationClient, drawerLayout, navigationView, toolbar, childData);
    }
}
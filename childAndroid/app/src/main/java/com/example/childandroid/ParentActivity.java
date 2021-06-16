package com.example.childandroid;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;

import com.example.childandroid.modules.AppUser;
import com.example.childandroid.modules.Parent;
import com.example.childandroid.modules.ParentResponse;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.SupportMapFragment;

public class ParentActivity extends FragmentActivity implements OnMapReadyCallback {
    private RecyclerView recyclerView;
    private ParentPageAdapter parentPageAdapter;
    private Parent parentOut = new Parent();
    private List<AppUser> children = new ArrayList();
    private GoogleMap map;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Context context = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent);

        String url = "http://10.0.2.2:4040/parentProfile";
        OkHttpClient httpClient = new OkHttpClient();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String token = "Bearer " + preferences.getString("token", "");
        System.out.println("token12");
        System.out.println(token);
        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", token)
                .build();

        final Handler handler = new Handler();
        recyclerView = (RecyclerView) findViewById(R.id.all_posts_child);

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
                    ParentResponse parent = gson.fromJson(body, ParentResponse.class);
                    parentOut = parent.getParent();
                    System.out.println("parentOut");
                    System.out.println(parentOut);
                    System.out.println(parent);
                    List arr = new ArrayList();
                    arr.addAll(parent.getChildren());
                    children = arr;

                }
            }
        });
        try {
            Thread.sleep(5000);
            System.out.println(children);
            System.out.println("children");
            parentPageAdapter = new ParentPageAdapter(context, children);
            recyclerView.setAdapter(parentPageAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map_parent);
            mapFragment.getMapAsync(this);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        findViewById(R.id.notifications).setOnClickListener(v -> {
            Intent notificationsPageActivityIntent = new Intent(this, NotificationsActivity.class);
            startActivity(notificationsPageActivityIntent);
        });
        findViewById(R.id.update_parent).setOnClickListener(v -> {
            Intent updateParentPageActivityIntent = new Intent(this, UpdateParentAccountActivity.class);
            System.out.println("parentResponse.getParent()");
            startActivity(updateParentPageActivityIntent);
        });


    }

    //    ==================================to prevent go out of the the app
//    @Override
//    public void onBackPressed() {
//        if (drawerLayout.isDrawerOpen((GravityCompat.START))) {
//            drawerLayout.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
//    }
//
//    @Override
//    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
//        Intent intent = new Intent();
//        switch (item.getItemId()) {
//            case R.id.nav_home:
//                break;
//            case R.id.nav_youtube:
//                intent = new Intent(ParentActivity.this, feedsActivity.class);
//                break;
//            case R.id.nav_our_games:
//                intent = new Intent(ParentActivity.this, GamesPageActivity.class);
//                break;
//            case R.id.nav_whiteboard:
//                intent = new Intent(ParentActivity.this, DrawBoardActivity.class);
//                break;
//            case R.id.nav_child_login:
//                intent = new Intent(ParentActivity.this, ChildSignInActivity.class);
//                break;
//            case R.id.nav_child_profile:
//                intent = new Intent(ParentActivity.this, ChildActivity.class);
//                break;
//            case R.id.nav_child_logout:
//                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
//                SharedPreferences.Editor editor = preferences.edit();
//                editor.remove("token");
//                editor.commit();
//                break;
//            case R.id.nav_parent_login:
//                intent = new Intent(ParentActivity.this, ParentSignInActivity.class);
//                break;
//            case R.id.nav_parent_profile:
//                intent = new Intent(ParentActivity.this, ParentActivity.class);
//                break;
//            case R.id.nav_parent_logout:
//                preferences = PreferenceManager.getDefaultSharedPreferences(this);
//                editor = preferences.edit();
//                editor.remove("token");
//                editor.commit();
//                break;
//        }
//        startActivity(intent);
//        return true;
//    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        for (AppUser child : children) {
            if (child.getLocation() != null) {
                String[] location = child.getLocation().split(",");

                LatLng latLng = new LatLng(Float.parseFloat("32.0625"),Float.parseFloat("35.8844"));

                if (location.length == 0) {
                    latLng = new LatLng(Float.parseFloat(location[1]), Float.parseFloat(location[0]));
                }
                map.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title("Marker"));
                map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            }

        }

    }

}

package com.example.childandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private FusedLocationProviderClient fusedLocationClient;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

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
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
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
//        ======================================================================


//        SocketInstance instance = (SocketInstance)getApplication();
//        ops.query = "Bearer " + "authToken";
//        mSocket = instance.getSocketInstance();

//         findViewById(R.id.feeds_button).setOnClickListener(v ->{
//
//              Intent feedPageActivityIntent = new Intent(this, GamesPageActivity.class);
//              startActivity(feedPageActivityIntent);
//         });
//
//        findViewById(R.id.mainGameButton).setOnClickListener(v ->{
//
//            Intent gameDetail = new Intent(this, feedsActivity.class);
//            startActivity(gameDetail);
//        });
//
//        findViewById(R.id.parent_account).setOnClickListener(v ->{
//            Intent parentIntent = new Intent(this, ParentActivity.class);
//            startActivity(parentIntent);
//        });
// findViewById(R.id.logIn).setOnClickListener(new View.OnClickListener() {
//     @Override
//     public void onClick(View v) {
//         goToLogin(v);
//     }
// });

//        findViewById(R.id.findFriends).setOnClickListener(v -> {
//            Intent parentIntent = new Intent(this, FindUser.class);
//            startActivity(parentIntent);
//        });

        requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 2);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        System.out.println("location");
                        if (location != null) {
                            System.out.println(location.getLongitude());
                            System.out.println(location.getLatitude());
//                            Geocoder geocoder = new Geocoder(ChildActivity.this, Locale.getDefault());
//                            try {
//                                List<Address> address=geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),3);
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }

                        }
                    }
                });

    }

    public void goToBoard(View view) {
        // Do something in response to button click
        Intent intent = new Intent(this, DrawBoardActivity.class);
        startActivity(intent);
    }

    public void goToChat(View view) {
        // Do something in response to button click
        Intent intent = new Intent(this, AddUserActivity.class);
        startActivity(intent);
    }

    public void goSignUp(View view) {
        // Do something in response to button click
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }

    public void goToLogin(View view) {
        // Do something in response to button click
        Intent intent = new Intent(this, ChildSignInActivity.class);
        startActivity(intent);
    }

    public void goToParentSignInPage(View view) {
        Intent intent = new Intent(this, ParentSignInActivity.class);
        startActivity(intent);
    }

    public void addTask(View view) {
        Intent intent = new Intent(this, AddPost_Activity.class);
        startActivity(intent);
    }

    public void goChildPage(View view) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token", "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhcmVlaiIsImV4cCI6MTYyMzcyMTI3NiwiaWF0IjoxNjIzNjg1Mjc2fQ._ZhWmniMKiBG9do10orMuMDbjFcrUrMJ-lSxVW61Q_g");
        editor.apply();
        Intent childUser = new Intent(MainActivity.this, ChildActivity.class);
        startActivity(childUser);

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
                intent = new Intent(MainActivity.this, MainActivity.class);

                break;
            case R.id.nav_youtube:
                intent = new Intent(MainActivity.this, feedsActivity.class);
                break;
            case R.id.nav_our_games:
                intent = new Intent(MainActivity.this, GamesPageActivity.class);
                break;
            case R.id.nav_whiteboard:
                intent = new Intent(MainActivity.this, DrawBoardActivity.class);
                break;
            case R.id.nav_child_login:
                intent = new Intent(MainActivity.this, ChildSignInActivity.class);
                break;
            case R.id.nav_child_profile:
                intent = new Intent(MainActivity.this, ChildActivity.class);
                break;
            case R.id.nav_child_logout:
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor = preferences.edit();
                editor.remove("token");
                editor.commit();
                intent = new Intent(MainActivity.this, MainActivity.class);
                break;
            case R.id.nav_parent_login:
                intent = new Intent(MainActivity.this, ParentSignInActivity.class);
                break;
            case R.id.nav_parent_profile:
                intent = new Intent(MainActivity.this, ParentActivity.class);
                break;
            case R.id.nav_parent_logout:
                preferences = PreferenceManager.getDefaultSharedPreferences(this);
                editor = preferences.edit();
                editor.remove("token");
                editor.commit();
                intent = new Intent(MainActivity.this, MainActivity.class);
                break;
            case R.id.nav_child_signUp:
                intent = new Intent(MainActivity.this, SignUp.class);
                break;
            case R.id.nav_chat:
                intent = new Intent(MainActivity.this, AddUserActivity.class);
                break;
            case R.id.nav_find_friend:
                intent = new Intent(MainActivity.this, FindUser.class);
                break;
            case R.id.my_friends_Posts:
                intent = new Intent(MainActivity.this, AllPostsActivity.class);
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
        MainActivity that = (MainActivity) o;
        return Objects.equals(fusedLocationClient, that.fusedLocationClient) &&
                Objects.equals(drawerLayout, that.drawerLayout) &&
                Objects.equals(navigationView, that.navigationView) &&
                Objects.equals(toolbar, that.toolbar);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fusedLocationClient, drawerLayout, navigationView, toolbar);
    }
}


//  nav_home
//  nav_youtube
// nav_our_games
// nav_whiteboard
// nav_child_login
// nav_child_profile
// nav_child_logout
// nav_parent_login
// nav_parent_profile
// nav_parent_logout

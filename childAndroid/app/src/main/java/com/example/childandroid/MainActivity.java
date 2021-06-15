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

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private FusedLocationProviderClient fusedLocationClient;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

//        ======================navigation bar======================


//        =======================================Hooks
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar_view);
//        =======================================Tool Bar
        setSupportActionBar(toolbar);
//        =======================================Navigation Drawer Menu

        // Hide not needed items from navBar
        Menu menu = navigationView.getMenu();
        menu.findItem(R.id.nav_logout).setVisible(false);
        menu.findItem(R.id.nav_profile).setVisible(false);

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
 findViewById(R.id.logIn).setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View v) {
         goToLogin(v);
     }
 });

        findViewById(R.id.findFriends).setOnClickListener(v -> {
            Intent parentIntent = new Intent(this, FindUser.class);
            startActivity(parentIntent);
        });

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
        switch (item.getItemId()) {
            case R.id.nav_home:
                break;
            case R.id.nav_youtube:
                Intent intent = new Intent(MainActivity.this, feedsActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }
//    ====================================================================
}
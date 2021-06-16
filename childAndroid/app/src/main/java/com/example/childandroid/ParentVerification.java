package com.example.childandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
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
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import com.example.childandroid.modules.Parent;
import com.google.android.material.navigation.NavigationView;

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
import okhttp3.logging.HttpLoggingInterceptor;

public class ParentVerification extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_verification);
        CardView verification = findViewById(R.id.Verify);
        Context context = this;
        verification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView parentEmail = findViewById(R.id.ParentEmailVeri);
                TextView verificationNum = findViewById(R.id.SerialNumber);
                HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

                OkHttpClient client = new OkHttpClient.Builder()
                        .addInterceptor(loggingInterceptor)
                        .build();
                String json = "{\"parentEmail\":\"" + parentEmail.getText().toString() + "\",\"serialNumber\":\"" + verificationNum.getText().toString() + "\"}";
                RequestBody requestBody = RequestBody.create(json, MediaType.parse("application/json"));

                Request request = new Request.Builder()
                        .url("http://10.0.2.2:4040/parentverification")
                        .post(requestBody)
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        if (response.isSuccessful()) {
                            String myResponse = response.body().string();
                            response.code();
                            response.isSuccessful();
                            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            System.out.println("token1");
                            System.out.println(myResponse);
                            editor.putString("token", myResponse);
                            editor.apply();
                            Intent verifiedUser = new Intent(ParentVerification.this, ChildActivity.class);
                            startActivity(verifiedUser);
                        }
                    }
                });


            }
        });

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
                intent = new Intent(ParentVerification.this, MainActivity.class);
                break;
            case R.id.nav_youtube:
                intent = new Intent(ParentVerification.this, feedsActivity.class);
                break;
            case R.id.nav_our_games:
                intent = new Intent(ParentVerification.this, GamesPageActivity.class);
                break;
            case R.id.nav_whiteboard:
                intent = new Intent(ParentVerification.this, DrawBoardActivity.class);
                break;
            case R.id.nav_child_login:
                intent = new Intent(ParentVerification.this, ChildSignInActivity.class);
                break;
            case R.id.nav_child_profile:
                intent = new Intent(ParentVerification.this, ChildActivity.class);
                break;
            case R.id.nav_child_logout:
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor = preferences.edit();
                intent = new Intent(ParentVerification.this, MainActivity.class);
                editor.remove("token");
                editor.commit();
                break;
            case R.id.nav_parent_login:
                intent = new Intent(ParentVerification.this, ParentSignInActivity.class);
                break;
            case R.id.nav_parent_profile:
                intent = new Intent(ParentVerification.this, ParentActivity.class);
                break;
            case R.id.nav_parent_logout:
                preferences = PreferenceManager.getDefaultSharedPreferences(this);
                intent = new Intent(ParentVerification.this, MainActivity.class);
                editor = preferences.edit();
                editor.remove("token");
                editor.commit();
                break;
            case R.id.nav_child_signUp:
                intent = new Intent(ParentVerification.this, SignUp.class);
                break;
            case R.id.nav_chat:
                intent = new Intent(ParentVerification.this, ChatActivity.class);
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
        ParentVerification that = (ParentVerification) o;
        return Objects.equals(drawerLayout, that.drawerLayout) &&
                Objects.equals(navigationView, that.navigationView) &&
                Objects.equals(toolbar, that.toolbar);
    }

    @Override
    public int hashCode() {
        return Objects.hash(drawerLayout, navigationView, toolbar);
    }
}
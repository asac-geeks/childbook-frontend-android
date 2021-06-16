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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChildSignInActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {
    String url = "http://10.0.2.2:4040/authenticate";
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_sign_in);


        TextView childUsername = findViewById(R.id.loginChildUserNameFeild);
        TextView childPassword = findViewById(R.id.loginChildPasswordFeild);
        CardView childLoginButton = findViewById(R.id.childLoginButton);

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

        } else {
            menu.findItem(R.id.nav_child_logout).setVisible(true);
            menu.findItem(R.id.nav_parent_logout).setVisible(true);
            menu.findItem(R.id.nav_child_profile).setVisible(true);
            menu.findItem(R.id.nav_parent_profile).setVisible(true);
            menu.findItem(R.id.nav_parent_login).setVisible(false);
            menu.findItem(R.id.nav_child_login).setVisible(false);
            menu.findItem(R.id.nav_child_signUp).setVisible(false);
        }
        Context context = this;

        childLoginButton.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    String username = childUsername.getText().toString();
                                                    String password = childPassword.getText().toString();
                                                    // ------------------------ HTTP client start---------------------------------- //
                                                    // OkHttpClient client = new OkHttpClient();
                                                    System.out.println("here11111");
                                                    OkHttpClient client = new OkHttpClient().newBuilder()
                                                            .connectTimeout(10, TimeUnit.SECONDS)
                                                            .readTimeout(10, TimeUnit.SECONDS)
                                                            .writeTimeout(10, TimeUnit.SECONDS)
                                                            .build();

                                                    JSONObject jsonObject = new JSONObject();
                                                    try {
                                                        jsonObject.put("userName", username);
                                                        jsonObject.put("password", password);
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                    MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                                                    RequestBody body = RequestBody.create(JSON, jsonObject.toString());

                                                    Request request = new Request.Builder()
                                                            .url(url)
                                                            .post(body)
                                                            .build();
                                                    client.newCall(request).enqueue(new Callback() {
                                                        @Override
                                                        public void onFailure(@NotNull Call call, @NotNull IOException e) {
                                                            e.printStackTrace();
                                                        }

                                                        @Override
                                                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                                                            if (response.isSuccessful()) {

                                                                ChildSignInActivity.this.runOnUiThread(new Runnable() {
                                                                    @Override
                                                                    public void run() {
                                                                        if (response.code() == 200) {
                                                                            System.out.println("work fine");
                                                                            String myResponse = null;
                                                                            try {
                                                                                myResponse = response.body().string();
                                                                            } catch (IOException e) {
                                                                                e.printStackTrace();
                                                                            }
                                                                            response.code();
                                                                            response.isSuccessful();
                                                                            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
                                                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                                                            System.out.println("token1");
                                                                            System.out.println(myResponse);
                                                                            editor.putString("token", myResponse);
                                                                            editor.apply();
                                                                            Intent verifiedUser = new Intent(ChildSignInActivity.this, ChildActivity.class);
                                                                            startActivity(verifiedUser);
                                                                        } else {
                                                                            Log.d("response body:", "code: " + response.toString());
                                                                        }
                                                                    }
                                                                });
                                                            }
                                                        }
                                                    });
                                                    // ------------------------ Http client end ----------------------------------//
                                                }});
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
                intent = new Intent(ChildSignInActivity.this, MainActivity.class);

                break;
            case R.id.nav_youtube:
                intent = new Intent(ChildSignInActivity.this, feedsActivity.class);
                break;
            case R.id.nav_our_games:
                intent = new Intent(ChildSignInActivity.this, GamesPageActivity.class);
                break;
            case R.id.nav_whiteboard:
                intent = new Intent(ChildSignInActivity.this, DrawBoardActivity.class);
                break;
            case R.id.nav_child_login:
                intent = new Intent(ChildSignInActivity.this, ChildSignInActivity.class);
                break;
            case R.id.nav_child_profile:
                intent = new Intent(ChildSignInActivity.this, ChildActivity.class);
                break;
            case R.id.nav_child_logout:
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor = preferences.edit();
                editor.remove("token");
                editor.commit();
                break;
            case R.id.nav_parent_login:
                intent = new Intent(ChildSignInActivity.this, ParentSignInActivity.class);
                break;
            case R.id.nav_parent_profile:
                intent = new Intent(ChildSignInActivity.this, ParentActivity.class);
                break;
            case R.id.nav_parent_logout:
                preferences = PreferenceManager.getDefaultSharedPreferences(this);
                editor = preferences.edit();
                editor.remove("token");
                editor.commit();
                break;
            case R.id.nav_child_signUp:
                intent = new Intent(ChildSignInActivity.this, SignUp.class);
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
        ChildSignInActivity that = (ChildSignInActivity) o;
        return Objects.equals(url, that.url) &&
                Objects.equals(drawerLayout, that.drawerLayout) &&
                Objects.equals(navigationView, that.navigationView) &&
                Objects.equals(toolbar, that.toolbar);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, drawerLayout, navigationView, toolbar);
    }
}
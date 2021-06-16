package com.example.childandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.ContentProviderOperation;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ParentSignInActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    String url = "http://10.0.2.2:4040/loginAsParent";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);


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

        TextView email = findViewById(R.id.loginParentEmailFeild);
        TextView passowrd = findViewById(R.id.loginParentPasswordFeild);
        CardView loginButton = findViewById(R.id.parentLoginButton);

        loginButton.setOnClickListener(v->{
            String userName = email.getText().toString();
            String parentPassword = passowrd.getText().toString();
// ------------------------ HTTP client start---------------------------------- //
//            OkHttpClient client = new OkHttpClient();
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .build();

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("userName", userName);
                jsonObject.put("password", parentPassword);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            RequestBody body = RequestBody.create(JSON, jsonObject.toString());

            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
            Context context = this;
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    e.printStackTrace();
                }
                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                      if(response.isSuccessful()){
                          ParentSignInActivity.this.runOnUiThread(new Runnable() {
                              @Override
                              public void run() {
                                  if(response.code() == 200){
                                      String myResponse= null;
                                      try {
                                          myResponse = response.body().string();
                                      } catch (IOException e) {
                                          e.printStackTrace();
                                      }
                                      SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
                                      SharedPreferences.Editor editor = sharedPreferences.edit();
                                      System.out.println("token1");
                                      System.out.println(myResponse);
                                      editor.putString("token",myResponse);
                                      editor.apply();
                                      Intent verifiedUser=new Intent(ParentSignInActivity.this,ParentActivity.class);
                                      startActivity(verifiedUser);
                                      System.out.println("work fine");
                                  }else{
                                      Log.d("response body:", "code: "+ response.toString());
                                  }
                              }
                          });
                      }
                }
            });
            // ------------------------ Http client end ----------------------------------//
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
        Intent intent = new Intent();
        switch (item.getItemId()) {
            case R.id.nav_home:
                intent = new Intent(ParentSignInActivity.this, MainActivity.class);
                break;
            case R.id.nav_youtube:
                intent = new Intent(ParentSignInActivity.this, feedsActivity.class);
                break;
            case R.id.nav_our_games:
                intent = new Intent(ParentSignInActivity.this, GamesPageActivity.class);
                break;
            case R.id.nav_whiteboard:
                intent = new Intent(ParentSignInActivity.this, DrawBoardActivity.class);
                break;
            case R.id.nav_child_login:
                intent = new Intent(ParentSignInActivity.this, ChildSignInActivity.class);
                break;
            case R.id.nav_child_profile:
                intent = new Intent(ParentSignInActivity.this, ChildActivity.class);
                break;
            case R.id.nav_child_logout:
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor = preferences.edit();
                intent = new Intent(ParentSignInActivity.this, MainActivity.class);
                editor.remove("token");
                editor.commit();
                intent = new Intent(ParentSignInActivity.this, MainActivity.class);
                break;
            case R.id.nav_parent_login:
                intent = new Intent(ParentSignInActivity.this, ParentSignInActivity.class);
                break;
            case R.id.nav_parent_profile:
                intent = new Intent(ParentSignInActivity.this, ParentActivity.class);
                break;
            case R.id.nav_parent_logout:
                preferences = PreferenceManager.getDefaultSharedPreferences(this);
                intent = new Intent(ParentSignInActivity.this, MainActivity.class);
                editor = preferences.edit();
                editor.remove("token");
                editor.commit();
            case R.id.nav_chat:
                intent = new Intent(ParentSignInActivity.this, ChatActivity.class);
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
        ParentSignInActivity that = (ParentSignInActivity) o;
        return Objects.equals(drawerLayout, that.drawerLayout) &&
                Objects.equals(navigationView, that.navigationView) &&
                Objects.equals(toolbar, that.toolbar) &&
                Objects.equals(url, that.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(drawerLayout, navigationView, toolbar, url);
    }
}
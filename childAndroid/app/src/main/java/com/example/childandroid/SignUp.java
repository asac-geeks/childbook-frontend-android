package com.example.childandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttp;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUp extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    String Birthdate = "";
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    private DatePickerDialog.OnDateSetListener mDataSetListner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        TextView dateOfBirth = findViewById(R.id.DateOfBirth);
        dateOfBirth.setOnClickListener(new View.OnClickListener() {
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
        mDataSetListner = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                month = month + 1;

                Birthdate = year + "-" + month + "-" + dayOfMonth;
                if (month < 10) {
                    Birthdate = year + "-" + "0" + month + "-" + dayOfMonth;

                }
                if (dayOfMonth < 10) {
                    Birthdate = year + "-" + "0" + month + "-" + "0" + dayOfMonth;
                }

            }
        };


        findViewById(R.id.signUpButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText userName = findViewById(R.id.addUserName);
                String username = userName.getText().toString();

                EditText passWord = findViewById(R.id.addPassword);
                String password = passWord.getText().toString();

                EditText parentEmail = findViewById(R.id.addParentEmail);
                String parentemail = parentEmail.getText().toString();


                HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

                OkHttpClient client = new OkHttpClient.Builder()
                        .addInterceptor(loggingInterceptor)
                        .build();
                String json = "{\"username\":\"" + username + "\",\"password\":\"" + password + "\",\"parentEmail\":\"" + parentemail + "\",\"dateOfBirth\":\"" + Birthdate + "\"}";
                RequestBody requestBody = RequestBody.create(json, MediaType.parse("application/json"));

                Request request = new Request.Builder()
                        .url("http://10.0.2.2:4040/signup")
                        .post(requestBody)
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        System.out.println("failed");
                        e.printStackTrace();

                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        if (response.isSuccessful()) {
                            String myResponse = response.body().string();
                            response.code();
                            response.isSuccessful();
                            Intent parentverification = new Intent(SignUp.this, ParentVerification.class);
                            startActivity(parentverification);
                        }
                    }
                });
// try {
//     Call call = client.newCall(request);
//
//     Response response = call.execute();
//     response.code();
//     System.out.println(response.code());
// }catch (Exception exception){
//     System.out.println("ERROR");
// }
            }


        });


//        Gson gson=new GsonBuilder().serializeNulls().create() ;
//        Retrofit retrofit=new Retrofit.Builder()
//                .baseUrl("https://as-childbook.herokuapp.com/signup")
//                .addConverterFactory(GsonConverterFactory.create(gson))
//                .client(client)
//                .build();


//        String url="https://as-childbook.herokuapp.com/signup";
//        String doPostRequest(String url, String json) throws IOException {
//            RequestBody body = RequestBody.create(JSON, json);
//            Request request = new Request.Builder()
//                    .url(url)
//                    .post(body)
//                    .build();
//            Response response = client.newCall(request).execute();
//            return response.body().string();
//        }

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
                intent = new Intent(SignUp.this, MainActivity.class);
                break;
            case R.id.nav_youtube:
                intent = new Intent(SignUp.this, feedsActivity.class);
                break;
            case R.id.nav_our_games:
                intent = new Intent(SignUp.this, GamesPageActivity.class);
                break;
            case R.id.nav_whiteboard:
                intent = new Intent(SignUp.this, DrawBoardActivity.class);
                break;
            case R.id.nav_child_login:
                intent = new Intent(SignUp.this, ChildSignInActivity.class);
                break;
            case R.id.nav_child_profile:
                intent = new Intent(SignUp.this, ChildActivity.class);
                break;
            case R.id.nav_child_logout:
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor = preferences.edit();
                intent = new Intent(SignUp.this, MainActivity.class);
                editor.remove("token");
                editor.commit();
                intent = new Intent(SignUp.this, MainActivity.class);
                break;
            case R.id.nav_parent_login:
                intent = new Intent(SignUp.this, ParentSignInActivity.class);
                break;
            case R.id.nav_parent_profile:
                intent = new Intent(SignUp.this, ParentActivity.class);
                break;
            case R.id.nav_parent_logout:
                preferences = PreferenceManager.getDefaultSharedPreferences(this);
                intent = new Intent(SignUp.this, MainActivity.class);
                editor = preferences.edit();
                editor.remove("token");
                editor.commit();
            case R.id.nav_chat:
                intent = new Intent(SignUp.this, ChatActivity.class);
                break;
        }
        startActivity(intent);
        return true;
    }

}
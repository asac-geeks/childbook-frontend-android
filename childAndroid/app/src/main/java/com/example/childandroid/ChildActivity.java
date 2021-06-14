package com.example.childandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.childandroid.modules.AppUser;
import com.example.childandroid.modules.ParentResponse;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;


import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChildActivity extends AppCompatActivity {
    private FusedLocationProviderClient fusedLocationClient;
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private AppUser childData = new AppUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child);

        requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION},2);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        String url = "http://10.0.2.2:4040/profile";


        OkHttpClient httpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyTmFtZTEgUGFyZW50IiwiZXhwIjoxNjIzNTY5Mjg3LCJpYXQiOjE2MjM1MzMyODd9.rE2xIyb0UBpOiaBc16CGJREu4i01R1uPGEXwFzCzyCI")
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
                    Gson gson = new Gson();
                    System.out.println("isSuccessfulUpdate");
                    String body = response.body().string();
                    System.out.println("isSuccessful");
                    System.out.println(body);
                    System.out.println(response.body());
                    System.out.println(response.body());
                    childData = gson.fromJson(body,  AppUser.class);
                }
            }


        });
        try{
            Thread.sleep(5000);
            System.out.println(childData);
            System.out.println("children");
            TextView email = findViewById(R.id.child_parent_email);
            email.setText(childData.getEmail());

            TextView username = findViewById(R.id.child_username);
            username.setText(childData.getUserName());

            TextView date = findViewById(R.id.child_date);
            date.setText(childData.getDateOfBirth().toString());

        }catch(InterruptedException e){
            e.printStackTrace();
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        System.out.println("location");
                        if (location != null) {
                            System.out.println(location.getLongitude());
                            System.out.println(location.getLatitude());
                            String url = "http://10.0.2.2:4040/userLocation";
                            String locationString = Double.toString(location.getLatitude()) + Double.toString(location.getLongitude());
                            String json = "{\"location\":\""+locationString+"\"}";

                            RequestBody body = RequestBody.create(JSON, json);


                            OkHttpClient httpClient = new OkHttpClient();
                            Request request = new Request.Builder()
                                    .url(url)
                                    .put(body)
                                    .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyTmFtZTEgUGFyZW50IiwiZXhwIjoxNjIzNTY5Mjg3LCJpYXQiOjE2MjM1MzMyODd9.rE2xIyb0UBpOiaBc16CGJREu4i01R1uPGEXwFzCzyCI")
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
                                        Gson gson = new Gson();
                                        System.out.println("isSuccessfulUpdate");
                                    }
                                }
                            });
                        }else {
                            System.out.println("location is null");
                        }

                    }
                });

    }

    public void myPosts(View view){
        Intent intent = new Intent(this, MyPostActivity.class);
        startActivity(intent);
    }
    public void addPost(View view){
        Intent intent = new Intent(this, AddPost_Activity.class);
        startActivity(intent);
    }
    public void updateChild(View view) {
        // Do something in response to button click
        Intent intent = new Intent(this, DrawBoardActivity.class);
        startActivity(intent);
    }
}
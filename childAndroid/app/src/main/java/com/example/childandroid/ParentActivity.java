package com.example.childandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.childandroid.modules.AppUser;
import com.example.childandroid.modules.Parent;
import com.example.childandroid.modules.ParentResponse;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.SupportMapFragment;

public class ParentActivity extends FragmentActivity implements OnMapReadyCallback {
    private RecyclerView recyclerView;
    private ParentPageAdapter parentPageAdapter;
    private Parent parentOut =new Parent();
    private List<AppUser> children = new ArrayList();
    private GoogleMap map;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Context context = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent);
        String url = "http://10.0.2.2:4040/parentProfile";
        OkHttpClient httpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyTmFtZTEgUGFyZW50IiwiZXhwIjoxNjIzNTY5Mjg3LCJpYXQiOjE2MjM1MzMyODd9.rE2xIyb0UBpOiaBc16CGJREu4i01R1uPGEXwFzCzyCI")
                .build();

        final Handler handler = new Handler();
        recyclerView = (RecyclerView) findViewById(R.id.rec_id_child);

                System.out.println("myHandler: here!"); // Do your work here
                httpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        e.printStackTrace();
                    }
                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        if(response.isSuccessful()){
                            Gson gson = new Gson();
                            // serialize
                            String body = response.body().string();
                            System.out.println("isSuccessful");
                            System.out.println(body);
                            System.out.println(response.body());
                            System.out.println(response.body());
                            ParentResponse parent = gson.fromJson(body,  ParentResponse.class);
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
                try{
                    Thread.sleep(5000);
                    System.out.println(children);
                    System.out.println("children");
                    parentPageAdapter = new ParentPageAdapter(context, children);
                    recyclerView.setAdapter(parentPageAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(context));
                    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                            .findFragmentById(R.id.map_parent);
                    mapFragment.getMapAsync(this);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }




        findViewById(R.id.notifications).setOnClickListener(v ->{
            Intent  notificationsPageActivityIntent = new Intent(this, NotificationsActivity.class);
            startActivity(notificationsPageActivityIntent);
        });
        findViewById(R.id.update_parent).setOnClickListener(v ->{
            Intent  updateParentPageActivityIntent = new Intent(this, UpdateParentAccountActivity.class);
            System.out.println("parentResponse.getParent()");
            startActivity(updateParentPageActivityIntent);
        });


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        for (AppUser child : children){
            String[] location = child.getLocation().split(",");
            LatLng latLng = new LatLng(Float.parseFloat(location[0]),Float.parseFloat(location[0]));

            map.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title("Marker"));
            map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        }

    }
}
package com.example.childandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.childandroid.modules.login;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONObject;

import java.net.URISyntaxException;
public class MainActivity extends AppCompatActivity {
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
//        SocketInstance instance = (SocketInstance)getApplication();
//        ops.query = "Bearer " + "authToken";
//        mSocket = instance.getSocketInstance();

         findViewById(R.id.feeds_button).setOnClickListener(v ->{
              Intent feedPageActivityIntent = new Intent(this, FeedPageActivity.class);
              startActivity(feedPageActivityIntent);
         });

        findViewById(R.id.mainGameButton).setOnClickListener(v ->{
            Intent feedPageActivityIntent = new Intent(this, GameDetailsActivity.class);
            startActivity(feedPageActivityIntent);
        });

        findViewById(R.id.parent_account).setOnClickListener(v ->{
            Intent parentIntent = new Intent(this, ParentActivity.class);
            startActivity(parentIntent);
        });
        requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION},2);
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
        Intent intent = new Intent(this, login.class);
        startActivity(intent);
    }




    public void goChildPage(View view){
        Intent intent = new Intent(this, ChildActivity.class);
        startActivity(intent);
    }
}
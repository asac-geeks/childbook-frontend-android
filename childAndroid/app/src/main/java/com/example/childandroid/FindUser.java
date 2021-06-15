package com.example.childandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.childandroid.modules.AppUser;
import com.example.childandroid.modules.ParentResponse;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class FindUser extends AppCompatActivity {

    Button userFollow;
    AppUser appUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_user);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String token = "Bearer "+preferences.getString("token","");
        final Handler handler = new Handler();
        EditText findUser= findViewById(R.id.findUser);


      findViewById(R.id.findUserButton) .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("button");
                String url = "http://192.168.1.82:8090/user/"+findUser.getText().toString();
                System.out.println(url);
                OkHttpClient client=new OkHttpClient();
                Request request = new Request.Builder()
                       .url(url)
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        e.printStackTrace();
                        System.out.println("FAILED");
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                       if(response.isSuccessful()) {

                           Gson gson=new Gson();
                           response.code();
                           response.isSuccessful();
                           appUser = gson.fromJson(response.body().string(),AppUser.class);

                           String json = "{\"id\":\""+appUser.getId()+"\"}";
                           System.out.println(json);
                           RequestBody requestBody = RequestBody.create(json, MediaType.parse("application/json"));
                           runOnUiThread(new Runnable() {
                               @Override
                               public void run() {

                                   findViewById(R.id.userInfo).setVisibility(View.VISIBLE);
                                   findViewById(R.id.userFollow).setVisibility(View.VISIBLE);
                                   TextView userInfo=findViewById(R.id.userInfo);
                                   userInfo.setText(appUser.getUserName());
                                   findViewById(R.id.userFollow).setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {

                                           String URL = "http://192.168.1.82:8090/follow/"+appUser.getId();
                                           System.out.println(URL);
                                           OkHttpClient httpClient=new OkHttpClient();
                                           Request request1 = new Request.Builder()
                                                   .url(URL)
                                                   .post(requestBody)
                                                   .header("Authorization", token)
                                                   .build();
                                           httpClient.newCall(request1).enqueue(new Callback() {
                                               @Override
                                               public void onFailure(@NotNull Call call, @NotNull IOException e) {
                                                   System.out.println("Failed");
                                               }

                                               @Override
                                               public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                                                  response.isSuccessful();
                                                  response.code();
                                                   System.out.println(response.code());
                                               }
                                           });




                                       }
                                   });
                               }
                           });
                       }else {
                           System.out.println("empty");
                       }
                    }
                });

            }
        });

    }

}
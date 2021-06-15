package com.example.childandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
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
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FindUser extends AppCompatActivity {
    TextView userInfo;
    Button userInfoButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_user);
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
                           System.out.println(response.body().string());
//                            AppUser appUser = gson.fromJson(response.body().string(),AppUser.class);

                           runOnUiThread(new Runnable() {
                               @Override
                               public void run() {
                                   findViewById(R.id.userInfo).setVisibility(View.VISIBLE);
                                   findViewById(R.id.userFollow).setVisibility(View.VISIBLE);
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
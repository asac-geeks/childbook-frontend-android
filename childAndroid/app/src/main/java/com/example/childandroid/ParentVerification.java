package com.example.childandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class ParentVerification extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_verification);
        Button verification=findViewById(R.id.Verify);
        verification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
       String parentEmail=findViewById(R.id.ParentEmailVeri).toString();
       String verificationNum=findViewById(R.id.SerialNumber).toString();
                HttpLoggingInterceptor loggingInterceptor=new HttpLoggingInterceptor();
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

                OkHttpClient client=new OkHttpClient.Builder()
                        .addInterceptor(loggingInterceptor)
                        .build();
                String json = "{\"parentEmail\":\""+parentEmail+"\",\"serialNumber\":\""+verificationNum+"\"}";
                RequestBody requestBody = RequestBody.create(json, MediaType.parse("application/json"));

                Request request = new Request.Builder()
                        .url("http://192.168.1.82:8090/parentverification")
                        .post(requestBody)
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        if(response.isSuccessful()){
                            String myResponse=response.body().string();
                            response.code();
                            response.isSuccessful();
                            Intent verifiedUser=new Intent(ParentVerification.this,MainActivity.class);
                            startActivity(verifiedUser);
                        }
                    }
                });


            }
        });

    }
}
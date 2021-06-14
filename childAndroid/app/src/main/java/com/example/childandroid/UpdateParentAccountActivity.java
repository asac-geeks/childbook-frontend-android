package com.example.childandroid;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.childandroid.modules.Parent;
import com.example.childandroid.modules.ParentResponse;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UpdateParentAccountActivity extends AppCompatActivity {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_parent_account);
    }
    public void update_parent(View v){

        EditText  username = findViewById(R.id.parent_username);
        EditText  email = findViewById(R.id.parent_email);
        EditText  password = findViewById(R.id.parent_password);
        Context context = this;
        TextView requiredField = findViewById(R.id.required_field);
        if(!username.getText().toString().equals("") && !email.getText().toString().equals("") && !password.getText().toString().equals("")){
            System.out.println("updateParent");
            String url = "http://10.0.2.2:4040/updateparent";

            String json = "{\"parentEmail\":\""+email.getText().toString()+"\",\"parentPassword\":\""+password.getText().toString()+"\",\"parentEmail\":\""+email.getText().toString()+"\"}";

            RequestBody body = RequestBody.create(JSON, json);

//            RequestBody formBody = new FormBody.Builder()
//                    .add("parentEmail", "test")
//                    .add("parentPassword", "test")
//                    .add("userName", "test")
//                    .build();
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
                        Intent parentPageActivityIntent = new Intent(context, ParentActivity.class);
                        startActivity(parentPageActivityIntent);
                    }
                }
            });
            requiredField.setText("");
        }else {
            requiredField.setText("Enter Required Fields");
        }
    }
    @Override
    public boolean equals(@Nullable @org.jetbrains.annotations.Nullable Object obj) {
        return super.equals(obj);
    }
}
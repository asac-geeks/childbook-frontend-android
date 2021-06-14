package com.example.childandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentProviderOperation;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
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

public class ParentSignInActivity extends AppCompatActivity {

    String url = "http://10.0.2.2:4040/loginAsParent";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        TextView email = findViewById(R.id.loginParentEmailFeild);
        TextView passowrd = findViewById(R.id.loginParentPasswordFeild);
        Button loginButton = findViewById(R.id.parentLoginButton);

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
}
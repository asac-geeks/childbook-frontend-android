package com.example.childandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Calendar;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class UpdateChildActivity extends AppCompatActivity {
    String Birthdate="";
    private DatePickerDialog.OnDateSetListener mDataSetListner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_chlil);
    }

    public void updateChildButton(View view){
        EditText userName=findViewById(R.id.update_userName_child);
        String username=userName.getText().toString();

        EditText passWord=findViewById(R.id.update_password_child);
        String password=passWord.getText().toString();

        EditText parentEmail=findViewById(R.id.update_email_child);
        String parentemail=parentEmail.getText().toString();
        TextView dateOfBirth=findViewById(R.id.update_dateOfBirth);
        dateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar=Calendar.getInstance();
                int year=calendar.get(Calendar.YEAR);
                int month=calendar.get(Calendar.MONTH);
                int day=calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog=new DatePickerDialog(UpdateChildActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,mDataSetListner,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mDataSetListner=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                month=month+1;

                Birthdate=year+"-"+month+"-"+dayOfMonth;
                if(month<10){
                    Birthdate=year+"-"+"0"+month+"-"+dayOfMonth;

                }
                if(dayOfMonth<10){
                    Birthdate=year+"-"+"0"+month+"-"+"0"+dayOfMonth;
                }

            }
        };

        HttpLoggingInterceptor loggingInterceptor=new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client=new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();
        String json = "{\"username\":\""+username+"\",\"password\":\""+password+"\",\"parentEmail\":\""+parentemail+"\",\"dateOfBirth\":\""+Birthdate+"\"}";
        RequestBody requestBody = RequestBody.create(json, MediaType.parse("application/json"));
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String token = "Bearer "+preferences.getString("token","");
        Request request = new Request.Builder()
                .url("http://10.0.2.2:4040/profile")
                .header("Authorization", token)
                .put(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                System.out.println("failed");
//                e.printStackTrace();

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if(response.isSuccessful()){
                    String myResponse=response.body().string();
                    response.code();
                    response.isSuccessful();             }
            }
        });
    }
}
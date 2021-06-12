package com.example.childandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

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

public class SignUp extends AppCompatActivity {
    String Birthdate="";
  private DatePickerDialog.OnDateSetListener mDataSetListner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        TextView dateOfBirth=findViewById(R.id.DateOfBirth);
        dateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar=Calendar.getInstance();
                int year=calendar.get(Calendar.YEAR);
                int month=calendar.get(Calendar.MONTH);
                int day=calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog  dialog=new DatePickerDialog(SignUp.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,mDataSetListner,year,month,day);
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


findViewById(R.id.signUpButton).setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        EditText userName=findViewById(R.id.addUserName);
        String username=userName.getText().toString();

        EditText passWord=findViewById(R.id.addPassword);
        String password=passWord.getText().toString();

        EditText parentEmail=findViewById(R.id.addParentEmail);
        String parentemail=parentEmail.getText().toString();






        HttpLoggingInterceptor loggingInterceptor=new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client=new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();
        String json = "{\"username\":\""+username+"\",\"password\":\""+password+"\",\"parentEmail\":\""+parentemail+"\",\"dateOfBirth\":\""+Birthdate+"\"}";
        RequestBody requestBody = RequestBody.create(json,MediaType.parse("application/json"));

        Request request = new Request.Builder()
                .url("https://as-childbook.herokuapp.com/signup")
                .post(requestBody)
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
    }

}
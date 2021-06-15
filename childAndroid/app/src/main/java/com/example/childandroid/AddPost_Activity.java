package com.example.childandroid;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.storage.StorageManager;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

public class AddPost_Activity extends AppCompatActivity {
    private static final int PICK_VIDEO = 1;
    private VideoView videoView;
    private Button button;
    private ProgressBar progressBar;
    private EditText editText;
    private Uri videoUri;
    MediaController mediaController;
    Member member;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    UploadTask uploadTask;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        storageReference = FirebaseStorage.getInstance().getReference("Video");
        databaseReference = FirebaseDatabase.getInstance().getReference("Video");
        mAuth = FirebaseAuth.getInstance();

        button = findViewById(R.id.upload_vedio);
        videoView = findViewById(R.id.videoView);
        progressBar = findViewById(R.id.progressBar);
        editText = findViewById(R.id.vedio_name);
        mediaController = new MediaController(this);
        member = new Member();
        videoView.setMediaController(mediaController);
        videoView.start();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent();
                intent.setType("video/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,PICK_VIDEO);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_VIDEO){
            videoUri = data.getData();
            videoView.setVideoURI(videoUri);
            uploadVideo();
        }
    }

    private String getExt(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadVideo(){
        String videoName = editText.getText().toString();
        if(videoUri != null || !TextUtils.isEmpty(videoName)){
            progressBar.setVisibility(View.VISIBLE);
            final StorageReference reference = storageReference.child(System.currentTimeMillis() + "." + getExt(videoUri));
            uploadTask = reference.putFile(videoUri);
            Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if(task.isSuccessful()){
                        throw task.getException();
                    }
                    return reference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>(){
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    System.out.println("task.isSuccessful()");
                    System.out.println(task.isSuccessful());
                    if(task.isSuccessful()){
                        Uri downloadUri = task.getResult();
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(AddPost_Activity.this,"DataSave Save", Toast.LENGTH_SHORT).show();
                        member.setName(videoName);
                        member.setUrl(downloadUri.toString());
                        String i = databaseReference.push().getKey();
                        databaseReference.child(i).setValue(member);
                    }else {
                        Toast.makeText(AddPost_Activity.this,"Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else {
            Toast.makeText(AddPost_Activity.this,"All Fields Are Required", Toast.LENGTH_SHORT).show();

        }
    }
}
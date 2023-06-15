package com.jubayer.firebasenotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Splash extends AppCompatActivity {
    FirebaseAuth fAuth;
    ProgressBar progressBarSp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        fAuth = FirebaseAuth.getInstance();
        Handler handler = new Handler();
        progressBarSp = findViewById(R.id.progressBar5);
        progressBarSp.setVisibility(View.VISIBLE);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

               // check if user is logged
               if (fAuth.getCurrentUser() != null){
                   startActivity(new Intent(getApplicationContext(),MainActivity.class));
                   finish();
               }else{

                   // create new anonymous account
                   fAuth.signInAnonymously().addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                       @Override
                       public void onSuccess(AuthResult authResult) {
                           Toast.makeText(Splash.this, "Logged in with Temporary Account.", Toast.LENGTH_SHORT).show();
                           startActivity(new Intent(getApplicationContext(),MainActivity.class));
                           finish();

                       }
                   }).addOnFailureListener(new OnFailureListener() {
                       @Override
                       public void onFailure(@NonNull Exception e) {
                           Toast.makeText(Splash.this, "Error!" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            progressBarSp.setVisibility(View.VISIBLE);
                            finish();
                       }
                   });

               }

            }
        },2000);
    }
}
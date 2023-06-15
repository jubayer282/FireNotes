package com.jubayer.firebasenotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
    EditText rUserName,rUserEmail,rUserPass,rUserConfPass;
    Button syncAccount;
    TextView loginAct;
    ProgressBar progressBar;
    FirebaseAuth fAuth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

      //   getSupportActionBar().setTitle("Create a New Account.");
        // getSupportActionBar().setDisplayShowHomeEnabled(true);

        rUserName = findViewById(R.id.userName);
        rUserEmail = findViewById(R.id.userEmail);
        rUserPass = findViewById(R.id.password);
        rUserConfPass = findViewById(R.id.passwordConfirm);

        syncAccount = findViewById(R.id.createAccount);
        loginAct = findViewById(R.id.login);
        progressBar = findViewById(R.id.progressBar4);

        fAuth = FirebaseAuth.getInstance();

        syncAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uUserName = rUserName.getText().toString();
                String uUserEmail = rUserEmail.getText().toString();
                String uUserPass = rUserPass.getText().toString();
                String uUserConPass = rUserConfPass.getText().toString();

                if (uUserEmail.isEmpty() || uUserName.isEmpty() || uUserPass.isEmpty() || uUserConPass.isEmpty()){

                    Toast.makeText(Register.this, "All Fields Are Required.", Toast.LENGTH_SHORT).show();
                }
               else if (!uUserPass.equals(uUserConPass)){
                    rUserConfPass.setError("Password Do not Match");
                    progressBar.setVisibility(View.VISIBLE);
                }
                else
                {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(Register.this, "Account is opened.", Toast.LENGTH_SHORT).show();
                    // when write register code then remove this start_activity line...
                    startActivity(new Intent(Register.this, MainActivity.class));
                }

            }
        });

    }
}
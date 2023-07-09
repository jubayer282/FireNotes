package com.jubayer.firebasenotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class Login extends AppCompatActivity {
    EditText IEmail, IPassword;
    Button loginNow;
    TextView forgetPass,createAcc;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    FirebaseUser user;
    ProgressBar spinner;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        IEmail = findViewById(R.id.email);
        IPassword = findViewById(R.id.password);
        spinner = findViewById(R.id.progressBar4);

        createAcc = findViewById(R.id.createAccount);
        user = FirebaseAuth.getInstance().getCurrentUser();

        fAuth = FirebaseAuth.getInstance();
        loginNow = findViewById(R.id.LoginBtn);
        fStore = FirebaseFirestore.getInstance();

        showWarning();

        loginNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mEmail = IEmail.getText().toString();
                String mPassword = IPassword.getText().toString();

                if (mEmail.isEmpty() || mPassword.isEmpty()){
                    Toast.makeText(Login.this, "Fields are required.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // delete notes first
                spinner.setVisibility(View.VISIBLE);
                if (fAuth.getCurrentUser().isAnonymous()){
                    FirebaseUser user =fAuth.getCurrentUser();
                    fStore.collection("notes").document(user.getUid()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void Avoid) {
                           //  Toast.makeText(Login.this, "All Team Notes are Deleted.", Toast.LENGTH_SHORT).show();
                        }
                    });

                    //delete Team user
                    user.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // Toast.makeText(Login.this, "Team user Deleted.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                fAuth.signInWithEmailAndPassword(mEmail,mPassword).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(Login.this, "Login Successful  !", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Login.this, "Something wrong with you " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        spinner.setVisibility(View.GONE);
                    }
                });
            }
        });

     createAcc.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             startActivity(new Intent(getApplicationContext(), Register.class));
         }
     });
    }

    private void showWarning() {
        AlertDialog.Builder warning = new AlertDialog.Builder(this)
                .setTitle("Are you sure?")
                .setMessage("Linking Existing Account will delete all the notes. Create New Account to Save them.")
                .setPositiveButton("Save Note", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(getApplicationContext(), Register.class));
                        finish();
                    }
                }).setNegativeButton("It's ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                    }
                });
        warning.show();
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        startActivity(new Intent(this, MainActivity.class));
        finish();
        return super.onOptionsItemSelected(item);
    }
}
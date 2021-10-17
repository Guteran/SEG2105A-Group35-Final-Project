package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    TextView passwordInput, emailInput, createAccount;

    ProgressBar progressBar;

    Button submitForm;

    FirebaseAuth fbAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailInput      = findViewById(R.id.emailInput);
        passwordInput   = findViewById(R.id.passwordInput);
        progressBar     = findViewById(R.id.logProgressBar);
        submitForm      = findViewById(R.id.submitForm);

        fbAuth = FirebaseAuth.getInstance();

        submitForm.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View view) {
                                              String emailValue = emailInput.getText().toString().trim();
                                              String passwordValue = passwordInput.getText().toString();

                                              // Email validation can be added here
                                              if (TextUtils.isEmpty(emailValue)) {
                                                  emailInput.setError("Email is required!");
                                                  return;
                                              }

                                              // Check password was entered
                                              if (TextUtils.isEmpty(passwordValue)) {
                                                  passwordInput.setError("Password is required!");
                                                  return;
                                              }

                                              // Check password length
                                              if (passwordValue.length() < 6) {
                                                  passwordInput.setError("Password must be at least 6 characters long!");
                                                  return;
                                              }

                                              // Set the visibility of the progress bar
                                              progressBar.setVisibility(View.VISIBLE);

                                              // authentication
                                              fbAuth.signInWithEmailAndPassword(emailValue, passwordValue).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                  @Override
                                                  public void onComplete(@NonNull Task<AuthResult> task) {
                                                      if (task.isSuccessful()) {
                                                          Toast.makeText(LoginActivity.this, "Welcome back!", Toast.LENGTH_LONG).show();
                                                          progressBar.setVisibility(View.INVISIBLE);
                                                          startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                                          finish();
                                                      } else {
                                                          Toast.makeText(LoginActivity.this, "Error! " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                                          progressBar.setVisibility(View.INVISIBLE);
                                                      }
                                                  }
                                              });
                                          }
                                      });

        createAccount = findViewById(R.id.regLink);

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });

        }
    }
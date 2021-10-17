package com.example.finalproject.loginFlow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.View;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalproject.MainActivity;
import com.example.finalproject.R;
import com.example.finalproject.users.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {

    EditText firstName, lastName, email, password, username;

    TextView existingAccount;

    Button submitForm;

    FirebaseAuth fbAuth;

    ProgressBar progressBar;

    Spinner userType;

    DatabaseReference databaseUsers;

    boolean usernameExists = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // User input
        username    = findViewById(R.id.usernameInput);
        firstName   = findViewById(R.id.firstNameInput);
        lastName    = findViewById(R.id.lastNameInput);
        email       = findViewById(R.id.emailInput);
        password    = findViewById(R.id.passwordInput);

        // User type spinner
        userType = (Spinner) findViewById(R.id.userTypeSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.users_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userType.setAdapter(adapter);

        // Submit registration form button
        submitForm = findViewById(R.id.submitForm);

        // Firebase instance
        fbAuth = FirebaseAuth.getInstance();
        databaseUsers = FirebaseDatabase.getInstance().getReference("users");

        // Progress bar
        progressBar = findViewById(R.id.regProgressBar);

        // Already have an account?
        existingAccount = findViewById(R.id.regLink);

        // Registration flow
        submitForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usernameValue = username.getText().toString().trim();
                String firstNameValue = firstName.getText().toString().trim();
                String userTypeValue = userType.getSelectedItem().toString();
                String lastNameValue = lastName.getText().toString().trim();
                String emailValue = email.getText().toString().trim();
                String passwordValue = password.getText().toString();

                // Email validation can be added here
                if(TextUtils.isEmpty(emailValue)){
                    email.setError("Email is required!");
                    return;
                }

                // Check password was entered
                if (TextUtils.isEmpty(passwordValue)){
                    password.setError("Password is required!");
                    return;
                }

                // Check password length
                if (passwordValue.length() < 6){
                    password.setError("Password must be at least 6 characters long!");
                    return;
                }

                // Set the visibility of the progress bar
                progressBar.setVisibility(View.VISIBLE);

                // Register user in Firebase
                if (!userExists(usernameValue)) {
                    fbAuth.createUserWithEmailAndPassword(emailValue, passwordValue).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                progressBar.setVisibility(View.INVISIBLE);
                                String userId = fbAuth.getUid();
                                createUser(userId, userTypeValue, usernameValue, emailValue, firstNameValue, lastNameValue);
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                Toast.makeText(RegisterActivity.this, "Thank you for registering!", Toast.LENGTH_LONG).show();
                                finish();
                            } else {
                                progressBar.setVisibility(View.INVISIBLE);
                                Toast.makeText(RegisterActivity.this, "Error! " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                } else {
                    username.setError("Username already in use!");
                }

                // Already have an account?
                existingAccount.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    }
                });

            }
        });

    }

    private void createUser(String id, String userType, String username, String email, String firstName, String lastName){
        User newUser = new User(id, userType, username, email, firstName, lastName);
        databaseUsers.child(id).setValue(newUser);
    }

    private boolean userExists(String username) {
        if (usernameExists){
            usernameExists = false;
        }
        databaseUsers.orderByChild("username").equalTo(username).addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot){
                if(dataSnapshot.exists()) {
                    usernameExists = true;
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(RegisterActivity.this, "Error! " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        );
        Toast.makeText(RegisterActivity.this, "Username: " + String.valueOf(usernameExists), Toast.LENGTH_LONG).show();
        return usernameExists;

}
}
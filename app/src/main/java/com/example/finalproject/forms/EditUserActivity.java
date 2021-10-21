package com.example.finalproject.forms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.finalproject.R;
import com.example.finalproject.javaClasses.ClassType;
import com.example.finalproject.javaClasses.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditUserActivity extends AppCompatActivity {

    EditText editUsernameInput, editFirstNameInput, editLastNameInput;

    DatabaseReference databaseUsers;

    FirebaseAuth fbAuth;

    Button editUserButton, deleteUserButton;

    User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        editUsernameInput = findViewById(R.id.editUsernameInput);
        editFirstNameInput = findViewById(R.id.editFirstNameInput);
        editLastNameInput = findViewById(R.id.editLastNameInput);

        editUserButton = findViewById(R.id.editUserButton);
        deleteUserButton = findViewById(R.id.deleteUserButton);

        String userId = getIntent().getStringExtra("userId");

        fbAuth = FirebaseAuth.getInstance();

        if (fbAuth.getUid().equals(userId)){
            deleteUserButton.setVisibility(View.INVISIBLE);
        }

        databaseUsers = FirebaseDatabase.getInstance().getReference("users");

        databaseUsers.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user = snapshot.getValue(User.class);
                updateTextFields(user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        editUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = editUsernameInput.getText().toString();
                String firstName = editFirstNameInput.getText().toString();
                String lastName = editFirstNameInput.getText().toString();
                user.setUsername(username);
                user.setFirstName(firstName);
                user.setLastName(lastName);
                databaseUsers.child(userId).setValue(user);
                finish();
            }
        });

        deleteUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseUsers.child(userId).removeValue();
                Toast.makeText(getApplicationContext(), user.getUsername() + "User was removed!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });


    }


    private void updateTextFields(User user) {
        if (user != null){
            editUsernameInput.setText(user.getUsername());
            editFirstNameInput.setText(user.getFirstName());
            editLastNameInput.setText(user.getLastName());
        }
    }

}
package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.finalproject.javaClasses.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserProfileFragment extends Fragment {

    TextView welcomeText, userTypeText;

    FirebaseAuth fbAuth;

    DatabaseReference databaseUsers;

    ProgressBar progressBar;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fbAuth = FirebaseAuth.getInstance();

        databaseUsers = FirebaseDatabase.getInstance().getReference("users");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);
        welcomeText = view.findViewById(R.id.welcomeText);
        userTypeText = view.findViewById(R.id.userTypeText);
        progressBar = view.findViewById(R.id.progressBar);

        databaseUsers.child(fbAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User currentUser = snapshot.getValue(User.class);
                updateUserProfile(currentUser);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
//                updateUserProfile("Anonymous user", "member");
            }
        });

        return view;
    }

    private void updateUserProfile(User user){
        welcomeText.setText("Welcome " + user.getUsername() + "!");
        userTypeText.setText("User type: " + user.getUserType() + ".");
        progressBar.setVisibility(View.INVISIBLE);
        welcomeText.setVisibility(View.VISIBLE);
        userTypeText.setVisibility(View.VISIBLE);
    }

}
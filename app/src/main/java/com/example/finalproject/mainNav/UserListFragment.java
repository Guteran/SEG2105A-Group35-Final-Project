package com.example.finalproject.mainNav;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.finalproject.R;
import com.example.finalproject.javaClasses.User;
import com.example.finalproject.javaClasses.UserList;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserListFragment extends Fragment {

    DatabaseReference databaseUsers;

    FirebaseAuth fbAuth;

    LinearLayout userListContainer;

    ListView userListView;

    List<User> userList;

    boolean isAdmin = false;
    boolean isInstructor = false;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        databaseUsers = FirebaseDatabase.getInstance().getReference("users");

        fbAuth = FirebaseAuth.getInstance();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_user_list, container, false);
        userListContainer = view.findViewById(R.id.userListContainer);

        userListView = view.findViewById(R.id.userListView);

        databaseUsers.child(fbAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User currentUser = snapshot.getValue(User.class);
                String userType = currentUser.getUserType();
                switch (userType){
                    case "admin":
                        isAdmin = true;
                        isInstructor = true;
                        break;
                    case "instructor":
                        isAdmin = false;
                        isInstructor = true;
                        break;
                    default:
                        isAdmin = false;
                        isInstructor = false;
                }
                if (isAdmin){
                    userListContainer.setVisibility(View.VISIBLE);


                } else {
                    Toast.makeText(getContext(), "Only admins can view other users!", Toast.LENGTH_LONG).show();
                    Navigation.findNavController(view).navigate(R.id.userProfileFragment);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });


        userList = new ArrayList<>();
        return view;
    }

    @Override
    public void onStart(){
        super.onStart();
        databaseUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userList.clear();
                for(DataSnapshot postSnapshot : snapshot.getChildren()){
                    User user = postSnapshot.getValue(User.class);
                    userList.add(user);
                }
                UserList userListAdapter = new UserList(getActivity(), userList);
                userListView.setAdapter(userListAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Error! " + error.getMessage(), Toast.LENGTH_LONG);
            }

        });
    }






}
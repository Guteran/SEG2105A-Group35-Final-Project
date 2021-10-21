package com.example.finalproject.mainNav;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.finalproject.forms.CreateClassType;
import com.example.finalproject.R;
import com.example.finalproject.forms.EditClassTypeActivity;
import com.example.finalproject.javaClasses.ClassType;
import com.example.finalproject.javaClasses.ClassTypeList;
import com.example.finalproject.javaClasses.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ClassListFragment extends Fragment {

    Button newClassTypeButton;

    ListView listViewElement;

    List<ClassType> classTypeList;
    DatabaseReference databaseClassTypes, databaseUsers;
    FirebaseAuth fbAuth;

    boolean isAdmin = false;

    boolean isInstructor = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        databaseClassTypes = FirebaseDatabase.getInstance().getReference("classTypes");

        databaseUsers = FirebaseDatabase.getInstance().getReference("users");

        fbAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_class_list, container, false);
        newClassTypeButton = view.findViewById(R.id.newClassTypeButton);

        listViewElement = view.findViewById(R.id.classTypeListElement);

        newClassTypeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CreateClassType.class);
                startActivity(intent);
            }
        });

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
                updateAdminVisibility();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
//                updateUserProfile("Anonymous user", "member");
            }
        });

        listViewElement.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView <? > arg0, View view, int position, long id) {
                // When clicked, show a toast with the TextView text
                if (isAdmin){
                    ClassType classType = (ClassType) listViewElement.getAdapter().getItem(position);
                    Intent intent = new Intent(getContext(), EditClassTypeActivity.class);
                    intent.putExtra("classTypeName", classType.getClassTypeName());
                    intent.putExtra("classTypeDescription", classType.getClassTypeDescription());
                    intent.putExtra("classTypeId", classType.getId());
                    startActivity(intent);
                }
            }
        });

        listViewElement.setClickable(false);

        classTypeList = new ArrayList<>();
        return view;
    }

    @Override
    public void onStart(){
        super.onStart();
        databaseClassTypes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                classTypeList.clear();
                for(DataSnapshot postSnapshot : snapshot.getChildren()){
                    ClassType classType = postSnapshot.getValue(ClassType.class);
                    classTypeList.add(classType);
                }
                ClassTypeList classTypeAdapter = new ClassTypeList(getActivity(), classTypeList);
                listViewElement.setAdapter(classTypeAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Error! " + error.getMessage(), Toast.LENGTH_LONG);
            }

        });
    }

    private void updateAdminVisibility(){
        if (isAdmin){
            newClassTypeButton.setVisibility(View.VISIBLE);
        }
    }

}
package com.example.finalproject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalproject.javaClasses.ClassType;
import com.example.finalproject.javaClasses.ClassTypeList;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ClassListFragment extends Fragment {

    TextView classTypeNameText, classTypeDescriptionText;
    Button createClassTypeButton;
    ListView classTypeListView;

    List<ClassType> classTypeList;
    DatabaseReference databaseClassTypes;

    Button createClassType;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    ActivityResultLauncher<Intent> createClassTypeResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK){
                        Intent data = result.getData();
                        ClassType classType = new ClassType(data.getStringExtra("classTypeName"), data.getStringExtra("classTypeDescription"));
                        String classTypeId = databaseClassTypes.push().getKey();
                        databaseClassTypes.child(classTypeId).setValue(classType);

                        Toast.makeText(getActivity(), "New class was added!", Toast.LENGTH_LONG);
                    }
                }
            }
    );

    public void createClassType(View view){
        Intent intent = new Intent(getActivity().getApplicationContext(), CreateClassType.class);
        createClassTypeResultLauncher.launch(intent);
    }

    @Override
    public void onStart() {
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
                classTypeListView.setAdapter(classTypeAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Error! " + error.getMessage(), Toast.LENGTH_LONG);
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_class_list, container, false);
        classTypeListView = view.findViewById(R.id.classTypeListElement);

        createClassType = (Button) view.findViewById(R.id.createActivityButton);

        createClassType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createClassType(view);
            }
        });


        classTypeList = new ArrayList<>();
        databaseClassTypes = FirebaseDatabase.getInstance().getReference("classTypes");

        return view;
    }
}
package com.example.finalproject.forms;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.finalproject.R;
import com.example.finalproject.javaClasses.ClassType;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditClassTypeActivity extends AppCompatActivity {

    EditText classTypeNameInput, classTypeDescriptionInput;

    DatabaseReference databaseClassTypes;

    Button editClassTypeButton, deleteClassTypeButton;

    String classId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_class_type);

        editClassTypeButton = findViewById(R.id.editUserButton);

        deleteClassTypeButton = findViewById(R.id.deleteUserButton);

        databaseClassTypes = FirebaseDatabase.getInstance().getReference("classTypes");

        classTypeNameInput = findViewById(R.id.editClassTypeNameInput);

        classTypeNameInput.setText(getIntent().getStringExtra("classTypeName"));

        classTypeDescriptionInput = findViewById(R.id.editClassTypeDescriptionInput);

        classTypeDescriptionInput.setText(getIntent().getStringExtra("classTypeDescription"));

        classId = getIntent().getStringExtra("classTypeId");


        editClassTypeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String className = classTypeNameInput.getText().toString();
                String classDescription = classTypeDescriptionInput.getText().toString();
                ClassType classType = new ClassType(classId, className, classDescription);
                databaseClassTypes.child(classId).setValue(classType);
                finish();
            }
        });

        deleteClassTypeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String className = classTypeNameInput.getText().toString();
                databaseClassTypes.child(classId).removeValue();
                Toast.makeText(getApplicationContext(), className + " was removed!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }
}
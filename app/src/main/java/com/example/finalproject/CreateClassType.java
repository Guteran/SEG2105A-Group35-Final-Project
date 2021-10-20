package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalproject.javaClasses.ClassType;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class CreateClassType extends AppCompatActivity {

    EditText classTypeNameInput, classTypeDescriptionInput;

    DatabaseReference databaseClassTypes;

    Button createClassTypeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_class_type);

        databaseClassTypes = FirebaseDatabase.getInstance().getReference("classTypes");

        classTypeNameInput = findViewById(R.id.classTypeNameInput);
        classTypeDescriptionInput = findViewById(R.id.classTypeDescriptionInput);

        createClassTypeButton = findViewById(R.id.createClassTypeButton);

        createClassTypeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (createClassType(view)){
                    Toast.makeText(view.getContext(), "New class was created!", Toast.LENGTH_LONG);
                    finish();
                }
            }
        });

    }



    public boolean createClassType(View view){
        String nameValue = classTypeNameInput.getText().toString();
        String descriptionValue = classTypeDescriptionInput.getText().toString();
        if (TextUtils.isEmpty(nameValue)){
            classTypeNameInput.setError("Please type a class name");
            return false;
        }

        if (TextUtils.isEmpty(descriptionValue)){
            classTypeDescriptionInput.setError("Please include a class description");
            return false;
        }

        String id = databaseClassTypes.push().getKey();
        ClassType classType = new ClassType(nameValue, descriptionValue);
        databaseClassTypes.child(id).setValue(classType);
        return true;
    }
}
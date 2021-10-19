package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class CreateClassType extends AppCompatActivity {

    DatabaseReference databaseClassTypes;

    Button createClassTypeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_class_type);

        databaseClassTypes = FirebaseDatabase.getInstance().getReference("classTypes");


        createClassTypeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText classTypeNameInput = view.findViewById(R.id.classTypeInput);
                String name = classTypeNameInput.getText().toString();
                EditText classTypeDescriptionInput = view.findViewById(R.id.classTypeDescriptionInput);
                String description = classTypeDescriptionInput.getText().toString();

                createClassType(name, description);

            }
        });

    }

    public void createClassType(String classTypeName, String classTypeDescription){
        Intent returnIntent = new Intent();

        returnIntent.putExtra("classTypeName", classTypeName);
        returnIntent.putExtra("classTypeDescription", classTypeDescription);

        setResult(RESULT_OK, returnIntent);

        finish();


    }
}
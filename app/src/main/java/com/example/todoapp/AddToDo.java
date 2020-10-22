package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddToDo extends AppCompatActivity {

    private EditText title, desc;
    private Button add;
    private DbHandler dbHandler;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_do);

        title = findViewById(R.id.editTextTitle);
        desc = findViewById(R.id.editTextDescription);
        add = findViewById(R.id.buttonAdd);
        context = this;

        dbHandler=new DbHandler(context);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userTitle = title.getText().toString();
                String userDesc = desc.getText().toString();
                long started = System.currentTimeMillis();


                ToDo toDo=new ToDo(userTitle,userDesc,started,0);
                dbHandler.addToDo(toDo);
                Toast toast=Toast.makeText(getApplicationContext(),"ToDo Added",Toast.LENGTH_SHORT);
                toast.show();
                startActivity(new Intent(context,MainActivity.class));


            }
        });
    }
}
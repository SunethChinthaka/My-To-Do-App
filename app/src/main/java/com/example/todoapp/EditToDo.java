package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditToDo extends AppCompatActivity {

    private EditText title,des;
    private Button edit;
    private DbHandler dbHandler;
    private Context context;
    private Long updateDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_to_do);

        context = this;
        dbHandler = new DbHandler(context);

        title = findViewById(R.id.editToDoTextTitle);
        des = findViewById(R.id.editToDoTextDescription);
        edit = findViewById(R.id.buttonEdit);

        final String id = getIntent().getStringExtra("id");
        ToDo todo = dbHandler.getSingleToDo(Integer.parseInt(id));

        title.setText(todo.getTitle());
        des.setText(todo.getDescription());

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String titleText=title.getText().toString();
                String decText = des.getText().toString();
                updateDate = System.currentTimeMillis();

                if (title.getText().toString().equals("")){
                    title.setError("Mandatory Field");
                }else if (des.getText().toString().equals("")) {
                    des.setError("Mandatory Field");
                }
                else{
                    ToDo toDo = new ToDo(Integer.parseInt(id),titleText,decText,updateDate,0);
                    int state = dbHandler.updateSingleToDo(toDo);
                    System.out.println(state);
                    Toast toast=Toast.makeText(getApplicationContext(),"Update Successfully",Toast.LENGTH_SHORT);
                    toast.show();
                    startActivity(new Intent(context,MainActivity.class));
                }


            }
        });
    }
}
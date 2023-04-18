package com.example.contactsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText nameEdit;
    EditText ageEdit;
    Spinner spinner;
    Button addButton;
    Button getButton;
    DatabaseControl control;
    RecyclerView list;
    Button deletebutton;
    TextView resultView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        control = new DatabaseControl(this);

        nameEdit = findViewById(R.id.nameEdit);
        ageEdit = findViewById(R.id.ageEdit);
        spinner = findViewById(R.id.spinner);
        addButton = findViewById(R.id.addButton);
        getButton = findViewById(R.id.getButton);
        deletebutton = findViewById(R.id.deletebutton);
        resultView = findViewById(R.id.resultView);
        list = findViewById(R.id.list);

        deletebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view){
                String name = nameEdit.getText().toString();
                control.open();
                control.delete(name);
                control.close();
                onResume();
            }
        });


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameEdit.getText().toString();
                String relationship = ((TextView) spinner.getSelectedView()).getText().toString();
                Integer age = Integer.parseInt(ageEdit.getText().toString());
                control.open();
                boolean itWorked = control.insert(name, relationship, age);
                control.close();
                if (itWorked)
                    Toast.makeText(getApplicationContext(), "Added "+name+" "+relationship, Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(), "FAILED "+name+" "+relationship, Toast.LENGTH_SHORT).show();
            }
        });

        getButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameEdit.getText().toString();
                control.open();
                int year = control.getAge(name);
                String kin = control.getRelationship(name);
                String Name = control.getName(name);
                control.close();
                resultView.setText(Name+" "+kin+" "+year+"yr old");
            }
        });



        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.relationship, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
    @Override
    public void onResume() {
        super.onResume();
        control.open();
        String[] dataset = control.getAllRelationshipsArray();
        control.close();
        list.setLayoutManager(new LinearLayoutManager(this));
        CustomAdapter adapter = new CustomAdapter(dataset);
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomAdapter.ViewHolder viewHolder = (CustomAdapter.ViewHolder) view.getTag();
                TextView textView = viewHolder.getTextView();
                resultView.setText(textView.getText().toString());
            }
        });

        list.setLayoutManager(new LinearLayoutManager(this));

    }
}






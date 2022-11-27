package com.example.easytutonotes;

import androidx.appcompat.app.AppCompatActivity;
import io.realm.Realm;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

public class AddNoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        Spinner rateSpin = (Spinner) findViewById(R.id.rate);
        Spinner moodSpin = (Spinner) findViewById(R.id.mood);
        EditText peopleEdit = (EditText) findViewById(R.id.people);
        MaterialButton saveBtn = findViewById(R.id.savebtn);


        Realm.init(getApplicationContext());

        Realm realm = Realm.getDefaultInstance();


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String people = peopleEdit.getText().toString();
                String rate = rateSpin.getSelectedItem().toString();
                String mood= moodSpin.getSelectedItem().toString();
                long createdTime = System.currentTimeMillis();

                realm.beginTransaction();
                Note note = realm.createObject(Note.class);
                note.setRate(rate);
                note.setMood(mood);
                note.setPeople(people);
                note.setCreatedTime(createdTime);
                realm.commitTransaction();

                Toast.makeText(getApplicationContext(),"Note saved",Toast.LENGTH_SHORT).show();
                finish();


            }
        });


    }
}
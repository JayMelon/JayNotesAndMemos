package com.example.notesandmemos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.Date;

public class NoteEditorActivity extends AppCompatActivity {
    private EditText editTitle, editContent, editPriority;
    private Button saveButton;
    private RadioGroup radioGroupPriority;
    private RadioButton radioHigh, radioMedium, radioLow;
    private NotesDBHelper notesDBHelper;
    private int noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);

        // Initialize views and database helper
        editTitle = findViewById(R.id.edit_title);
        editContent = findViewById(R.id.edit_content);
        radioGroupPriority = findViewById(R.id.priority_radio_group);
        radioHigh = findViewById(R.id.radio_high);
        radioMedium = findViewById(R.id.radio_medium);
        radioLow = findViewById(R.id.radio_low);
        saveButton = findViewById(R.id.save_button);
        notesDBHelper = new NotesDBHelper(this);

        // Check if we are editing an existing note
        noteId = getIntent().getIntExtra("noteId", -1);
        if (noteId != -1) {
            // Load note details based on the Tapped memo
            Note note = notesDBHelper.getNote(noteId);
            editTitle.setText(note.getTitle());
            editContent.setText(note.getContent());
            initSavedNotePriority(note.getPriority());

        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = editTitle.getText().toString();
                String content = editContent.getText().toString();
                int priority = getSelectPriority();
                Date creationDate = new Date(System.currentTimeMillis());
                Date noteDueDate = new Date(System.currentTimeMillis());
                Note note = new Note();
                note.setTitle(title);
                note.setContent(content);
                note.setPriority(priority);
                note.setNoteCreationDate(creationDate);
                note.setNoteDueDate(noteDueDate);

                if (noteId == -1) {
                    // Save a new note
                    notesDBHelper.addNote(note);
                } else {
                    // Update an existing note
                    note.setNoteID(noteId);
                    notesDBHelper.updateNote(note);
                }

                // Return to MainActivity
                setResult(RESULT_OK);
                finish();
            }
        });
    }
    //Inits the radio buttons to be pre checked based on Priority.
    private void initSavedNotePriority(int priority){
        switch(priority){
            case 3:
                radioHigh.setChecked(true);
                break;
            case 2:
                radioMedium.setChecked(true);
                break;
            case 1:
                radioLow.setChecked(true);
                break;
        }

    }
    //Returns the priority based on selected radio buttons
    private int getSelectPriority() {
        if(radioHigh.isChecked()){
            return 3;
        }
        else if(radioMedium.isChecked()){
            return 2;

        }else{
            return 1;
        }
    }
}
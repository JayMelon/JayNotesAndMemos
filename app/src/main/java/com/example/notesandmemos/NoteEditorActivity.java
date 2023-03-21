package com.example.notesandmemos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Date;

public class NoteEditorActivity extends AppCompatActivity {
    private EditText editTitle, editContent, editPriority;
    private Button saveButton;
    private NotesDBHelper notesDBHelper;
    private int noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);

        // Initialize views and database helper
        editTitle = findViewById(R.id.edit_title);
        editContent = findViewById(R.id.edit_content);
        editPriority = findViewById(R.id.edit_priority);
        saveButton = findViewById(R.id.save_button);
        notesDBHelper = new NotesDBHelper(this);

        // Check if we are editing an existing note
        noteId = getIntent().getIntExtra("noteId", -1);
        if (noteId != -1) {
            // Load note details
            Note note = notesDBHelper.getNote(noteId);
            editTitle.setText(note.getTitle());
            editContent.setText(note.getContent());
            editPriority.setText(String.valueOf(note.getPriority()));
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = editTitle.getText().toString();
                String content = editContent.getText().toString();
                int priority = Integer.parseInt(editPriority.getText().toString());
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
}
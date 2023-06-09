package com.example.notesandmemos;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FloatingActionButton fabNewNote;
    private NotesDBHelper notesDBHelper;
    private ArrayList<Note> notes;
    private NoteAdapter noteAdapter;
    private ImageButton homeButton, settingButton;
    String sortOrder, sortby;
    public class NoteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title, content, priority, dueDate;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            content = itemView.findViewById(R.id.content);
            priority = itemView.findViewById(R.id.priority);
            dueDate = itemView.findViewById(R.id.dueDate);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Note note = notes.get(position);
            Intent intent = new Intent(MainActivity.this, NoteEditorActivity.class);
            intent.putExtra("noteId", note.getNoteID());
            ((Activity) MainActivity.this).startActivityForResult(intent, 1);
        }
    }
    //Strings from Preferences to alter table.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initializes Settings Button to send User to Settings Activity
        initSettingsButton();

        // Initialize views and database helper
        recyclerView = findViewById(R.id.recyclerView);
        fabNewNote = findViewById(R.id.fab_new_note);
        notesDBHelper = new NotesDBHelper(this);
        sortOrder = getSharedPreferences(NotesSettingsActivity.Notes_Preferences, Context.MODE_PRIVATE).getString(NotesSettingsActivity.OrderFieldKey, NotesDBHelper.COLUMN_TITLE);
        sortby = getSharedPreferences(NotesSettingsActivity.Notes_Preferences, Context.MODE_PRIVATE).getString(NotesSettingsActivity.SortFieldKey, "ASC");
        // Set up RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Load notes from the database and set up the adapter
        loadNotesFromDatabase();
        noteAdapter = new NoteAdapter(this, notes);
        recyclerView.setAdapter(noteAdapter);

        // Set up the click listener for the FloatingActionButton
        fabNewNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NoteEditorActivity.class);
                startActivityForResult(intent, 1);
            }
        });
    }

    private void loadNotesFromDatabase() {
        notes = notesDBHelper.getAllNotes(sortOrder, sortby);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            // Refresh the list of notes after adding or editing a note
            loadNotesFromDatabase();
            noteAdapter.setNotes(notes);
            noteAdapter.notifyDataSetChanged();
        }
    }

    private void initSettingsButton() {
        settingButton = findViewById(R.id.imageButtonSettings);
        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, NotesSettingsActivity.class);
                i.setFlags(i.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
    }
}

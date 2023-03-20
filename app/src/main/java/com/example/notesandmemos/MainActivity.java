package com.example.notesandmemos;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FloatingActionButton fabNewNote;
    private NotesDBHelper notesDBHelper;
    private ArrayList<Note> notes;
    private NoteAdapter noteAdapter;
    public class NoteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title, content;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            content = itemView.findViewById(R.id.content);
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views and database helper
        recyclerView = findViewById(R.id.recyclerView);
        fabNewNote = findViewById(R.id.fab_new_note);
        notesDBHelper = new NotesDBHelper(this);

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
        String orderBy = "priority DESC"; // Or any other ordering based on your preferences
        notes = notesDBHelper.getAllNotes(orderBy);
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
    }

package com.example.notesandmemos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NoteAdapter extends RecyclerView.Adapter<MainActivity.NoteViewHolder> {
    private ArrayList<Note> notes;
    private Context context;

    public NoteAdapter(Context context, ArrayList<Note> notes) {
        this.notes = notes;
        this.context = context;
    }

    @NonNull
    @Override
    public MainActivity.NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.note_list_item, parent, false);
        return new MainActivity.NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainActivity.NoteViewHolder holder, int position) {
        Note note = notes.get(position);
        holder.title.setText(note.getTitle());
        holder.content.setText(note.getContent());
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    // Add this method to update the list of notes in the adapter
    public void setNotes(ArrayList<Note> newNotes) {
        this.notes = newNotes;
    }
}


package com.example.notesandmemos;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {
    private ArrayList<Note> notes;
    private Context context;

    public NoteAdapter(Context context, ArrayList<Note> notes) {
        this.notes = notes;
        this.context = context;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.note_list_item, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = notes.get(position);
        holder.title.setText(note.getTitle());
        holder.content.setText(note.getContent());
        holder.priority.setText(note.getPriority()+"");
        holder.dueDate.setText(note.getNoteDueDate().toString());
    }
//Returns the size of the notes
    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void setNotes(ArrayList<Note> newNotes) {
        this.notes = newNotes;
    }

    class NoteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title, content, priority, dueDate;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            //Initilizing the TextFields
            title = itemView.findViewById(R.id.title);
            content = itemView.findViewById(R.id.content);
            priority = itemView.findViewById(R.id.priority);
            dueDate = itemView.findViewById(R.id.dueDate);
            //Initilizing listener for edit activity.
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Note note = notes.get(position);
            Intent intent = new Intent(context, NoteEditorActivity.class);
            intent.putExtra("noteId", note.getNoteID());
            ((Activity) context).startActivityForResult(intent, 1);
        }
    }
}

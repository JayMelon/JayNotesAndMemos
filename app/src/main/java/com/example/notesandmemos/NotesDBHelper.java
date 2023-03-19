package com.example.notesandmemos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NotesDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "notes.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NOTES = "notes";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_CONTENT = "content";
    public static final String COLUMN_PRIORITY = "priority";
    public static final String COLUMN_CREATION_DATE = "creation_date";
    public static final String COLUMN_DUE_DATE = "due_date";

    private static final String CREATE_TABLE_NOTES = "CREATE TABLE " + TABLE_NOTES + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_TITLE + " TEXT, " +
            COLUMN_CONTENT + " TEXT, " +
            COLUMN_PRIORITY + " INTEGER, " +
            COLUMN_CREATION_DATE + " INTEGER, " +
            COLUMN_DUE_DATE + " INTEGER" + ")";

    public NotesDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_NOTES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
        onCreate(db);
    }

    public long insertNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TITLE, note.getTitle());
        contentValues.put(COLUMN_CONTENT, note.getContent());
        contentValues.put(COLUMN_PRIORITY, note.getPriority());
        contentValues.put(COLUMN_CREATION_DATE, note.getNoteCreationDate().getTime());
        contentValues.put(COLUMN_DUE_DATE, note.getNoteDueDate().getTime());

        long id = db.insert(TABLE_NOTES, null, contentValues);
        db.close();
        return id;
    }

    public int updateNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TITLE, note.getTitle());
        contentValues.put(COLUMN_CONTENT, note.getContent());
        contentValues.put(COLUMN_PRIORITY, note.getPriority());
        contentValues.put(COLUMN_CREATION_DATE, note.getNoteCreationDate().getTime());
        contentValues.put(COLUMN_DUE_DATE, note.getNoteDueDate().getTime());

        int rowsAffected = db.update(TABLE_NOTES, contentValues, COLUMN_ID + "=?", new String[]{String.valueOf(note.getNoteID())});
        db.close();
        return rowsAffected;
    }

    public void deleteNote(int noteId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NOTES, COLUMN_ID + "=?", new String[]{String.valueOf(noteId)});
        db.close();
    }

    public List<Note> getAllNotes(String orderBy) {
        List<Note> notes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NOTES, null, null, null, null, null, orderBy);
// Adding
        if (cursor.moveToFirst()) {
            do {
                Note note = new Note();
                note.setNoteID(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                note.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));
                note.setContent(cursor.getString(cursor.getColumnIndex(COLUMN_CONTENT)));
                note.setPriority(cursor.getInt(cursor.getColumnIndex(COLUMN_PRIORITY)));
                note.setNoteCreationDate(new Date(cursor.getLong(cursor.getColumnIndex(COLUMN_CREATION_DATE))));
                note.setNoteDueDate(new Date(cursor.getLong(cursor.getColumnIndex(COLUMN_DUE_DATE))));

                notes.add(note);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return notes;
    }
}

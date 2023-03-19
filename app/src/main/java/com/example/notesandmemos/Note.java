package com.example.notesandmemos;

import java.util.Date;

public class Note {
    private int noteID;
    private String title;
    private String content;
    private int priority;
    private Date noteCreationDate;
    private Date noteDueDate;

    public Note() {
    }

    public Note(int noteID, String title, String content, int priority, Date noteCreationDate, Date noteDueDate) {
        this.noteID = noteID;
        this.title = title;
        this.content = content;
        this.priority = priority;
        this.noteCreationDate = noteCreationDate;
        this.noteDueDate = noteDueDate;
    }

    public int getNoteID() {
        return noteID;
    }

    public void setNoteID(int noteID) {
        this.noteID = noteID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Date getNoteCreationDate() {
        return noteCreationDate;
    }

    public void setNoteCreationDate(Date noteCreationDate) {
        this.noteCreationDate = noteCreationDate;
    }

    public Date getNoteDueDate() {
        return noteDueDate;
    }

    public void setNoteDueDate(Date noteDueDate) {
        this.noteDueDate = noteDueDate;
    }
}

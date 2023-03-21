package com.example.notesandmemos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

public class NoteEditorActivity extends AppCompatActivity {
    private EditText editTitle, editContent, editPriority;
    private TextView editDate;
    private Button saveButton, datePickerButton;
    private RadioGroup radioGroupPriority;
    private RadioButton radioHigh, radioMedium, radioLow;
    private NotesDBHelper notesDBHelper;
    private int noteId;
private DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);

        initChangeDateButton();

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
    private void initChangeDateButton() {
        datePickerButton = findViewById(R.id.datePickerBtn);
        initDatePicker();
        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePicker(v);
            }
        });

    }
    private String makeDateString(int day, int month, int year){
        return getMonthFormat(month) + " " + day + " " + year;
    }
    private void initDatePicker(){
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month++;
                String date = makeDateString(day, month, year);
                datePickerButton.setText(date);
            }
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);


        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
    }
    private String getMonthFormat(int month){
        String currentMonth = "";
        switch(month){
            case 1:
                currentMonth = "JAN";
                break;
            case 2:
                currentMonth = "FEB";
                break;
            case 3:
                currentMonth = "MAR";
                break;
            case 4:
                currentMonth = "APR";
                break;
            case 5:
                currentMonth = "MAY";
                break;
            case 6:
                currentMonth = "JUN";
                break;
            case 7:
                currentMonth = "JUL";
                break;
            case 8:
                currentMonth = "AUG";
                break;
            case 9:
                currentMonth = "SEP";
                break;
            case 10:
                currentMonth = "OCT";
                break;
            case 11:
                currentMonth = "NOV";
                break;
            case 12:
                currentMonth = "DEC";
                break;
        }
        return currentMonth;
    }

    public void openDatePicker(View view){
        datePickerDialog.show();
    }
}
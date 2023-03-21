package com.example.notesandmemos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class NotesSettingsActivity extends AppCompatActivity {
    public static final String ContactList_Preferences = "Notes_Preferences";
    public static final String SortFieldKey = "sortField";
    public static final String OrderFieldKey = "sortOrderField";
    RadioButton rbTitle, rbPriority,rbDueDate, rbAscending, rbDescending;
    RadioGroup sortByFieldGroup, sortOrderByFieldGroup;

    private String sortByPreferences, OrderByPreferences;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_settings);
        sortByFieldGroup = findViewById(R.id.radioGroupSortBy);
        sortOrderByFieldGroup = findViewById(R.id.radioGroupSortOrder);
        rbAscending = findViewById(R.id.rbAscending);
        rbDescending = findViewById(R.id.rbDescending);
        rbTitle = findViewById(R.id.rbTitle);
        rbPriority = findViewById(R.id.rbPriority);
        rbDueDate = findViewById(R.id.rbDate);
        //On Initilatizion get preferences settings, If User has nothing saved default is contactName and ASC
        sortByPreferences = getSharedPreferences(ContactList_Preferences, Context.MODE_PRIVATE).getString(OrderFieldKey,NotesDBHelper.COLUMN_TITLE);
        OrderByPreferences = getSharedPreferences(ContactList_Preferences,Context.MODE_PRIVATE).getString(SortFieldKey,"ASC");
        //Buttons update based on Initilzation;
        if (sortByPreferences.equalsIgnoreCase(NotesDBHelper.COLUMN_TITLE)) {
            rbTitle.setChecked(true);
        } else if (sortByPreferences.equalsIgnoreCase(NotesDBHelper.COLUMN_PRIORITY)) {
            rbPriority.setChecked(true);
        } else {
            rbDueDate.setChecked(true);
        }
        if (OrderByPreferences.equalsIgnoreCase("ASC")) {
            rbAscending.setChecked(true);
        } else {
            rbDescending.setChecked(true);
        }
    }



    private void initSortByClick(){
        sortByFieldGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(rbTitle.isChecked()){
                    //Set to Column Name
                    getSharedPreferences(ContactList_Preferences, Context.MODE_PRIVATE).edit().putString(OrderFieldKey,NotesDBHelper.COLUMN_TITLE).apply();
                }
                //Set to City Column Name
                else if(rbPriority.isChecked()){
                    getSharedPreferences(ContactList_Preferences,Context.MODE_PRIVATE).edit().putString(OrderFieldKey,NotesDBHelper.COLUMN_PRIORITY).apply();
                }
                //Set to Birthday Column name
                else {
                    getSharedPreferences(ContactList_Preferences, Context.MODE_PRIVATE).edit().putString(OrderFieldKey,NotesDBHelper.COLUMN_DUE_DATE).apply();
                }
            }
        });
    }
    private void initOrderByClick(){
        sortOrderByFieldGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(rbAscending.isChecked()){
                    getSharedPreferences(ContactList_Preferences,Context.MODE_PRIVATE).edit().putString(SortFieldKey,"ASC").apply();
                }
                else {
                    getSharedPreferences(ContactList_Preferences, Context.MODE_PRIVATE).edit().putString(SortFieldKey,"DESC").apply();
                }
            }
        });
    }
}
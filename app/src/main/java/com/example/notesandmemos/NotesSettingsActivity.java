package com.example.notesandmemos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.RadioGroup;

public class NotesSettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_settings);
    }



    private void initSortByClick(){
        sortByFieldGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(rbName.isChecked()){
                    //Set to Column Name
                    getSharedPreferences(ContactList_Preferences, Context.MODE_PRIVATE).edit().putString(sortFieldKey,DatabaseHelper.COLUMN_CONTACT_NAME).apply();
                }
                //Set to City Column Name
                else if(rbCity.isChecked()){
                    getSharedPreferences(ContactList_Preferences,Context.MODE_PRIVATE).edit().putString(sortFieldKey,DatabaseHelper.COLUMN_CONTACT_CITY).apply();
                }
                //Set to Birthday Column name
                else {
                    getSharedPreferences(ContactList_Preferences, Context.MODE_PRIVATE).edit().putString(sortFieldKey,DatabaseHelper.COLUMN_CONTACT_BIRTHDAY).apply();
                }
            }
        });
    }
}
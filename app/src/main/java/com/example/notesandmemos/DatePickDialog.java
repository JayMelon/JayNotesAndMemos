package com.example.notesandmemos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class DatePickDialog extends DialogFragment {
    Calendar selectedDate;

    public interface SaveDateListener {
        void didFinishDatePickDialog(Calendar selectedDate);
    }

    public DatePickDialog() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        final View view = inflater.inflate(R.layout.select_date, container);

        getDialog().setTitle("Select Date");
        selectedDate = Calendar.getInstance();

        final CalendarView cv = view.findViewById(R.id.calendarView);
        cv.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                selectedDate.set(year, month, day);
            }
        });

        Button selectButton = view.findViewById(R.id.buttonSelect);
        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveItem(selectedDate);
            }
        });
        return view;
    }

    private void saveItem(Calendar selectedDate) {
        SaveDateListener activity = (SaveDateListener) getActivity();
        activity.didFinishDatePickDialog(selectedDate);
        getDialog().dismiss();
    }
}

package com.android.tip.essentialactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.CalendarView;

import com.android.tip.R;

public class CalendarActivity extends AppCompatActivity {
    private CalendarView calendarActivity;
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_view);
        calendarActivity = findViewById(R.id.calendar);
        calendarActivity.setShowWeekNumber(true);
        calendarActivity.setSelectedWeekBackgroundColor(R.color.white);
    }
}
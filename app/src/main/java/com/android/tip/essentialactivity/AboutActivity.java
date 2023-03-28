package com.android.tip.essentialactivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.tip.BuildConfig;
import com.android.tip.R;
public class AboutActivity extends AppCompatActivity {
    private TextView txt;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        txt = findViewById(R.id.version_number);
        txt.setText("Version " + BuildConfig.VERSION_NAME);
    }
}
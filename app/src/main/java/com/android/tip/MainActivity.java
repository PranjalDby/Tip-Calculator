package com.android.tip;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.tip.essentialactivity.AboutActivity;
import com.android.tip.essentialactivity.CalendarActivity;
import com.android.tip.essentialactivity.WebViewHolder;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    public static final  String NOTIFICATION_CHANNEL_ID = "10001";
    private final static  String default_notification_channel_id = "default";
    public Button button;
    private ScrollView scrollView;
    public EditText totalAmount,percent,bill_split;
    public TextView result,splitted;
    private static float total_amnt = 0.0f;
    private static int split = 0;
    private static int percentage = 0;
    private static float reslt = 0.0f;
    private NavigationView navigationView;
//    Drawer layout
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private BroadcastReceiver broadcastReceiver;
    public static float GetRes(){
        if (reslt!=0.0f){
            return reslt;
        }
        return 0.0f;
    }
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.calculate);
        totalAmount = findViewById(R.id.totalAmount);
        percent = findViewById(R.id.per);
        bill_split = findViewById(R.id.bill_split);
        result = findViewById(R.id.result);
        splitted = findViewById(R.id.splitted_to);
        scrollView = findViewById(R.id.scroll_View);
//        drawer and NAvigation Logic
        navigationView = findViewById(R.id.navigation_view);
        drawerLayout = findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.drawer_open,R.string.drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int web_id = R.id.nav_web;
                if(item.getItemId() == R.id.nav_web){
                    Intent i = new Intent(getApplicationContext(), WebViewHolder.class);
                    startActivity(i);
                } else if (item.getItemId() == R.id.nav_calendar) {
                    Intent iCalendar = new Intent(getApplicationContext(), CalendarActivity.class);
                    startActivity(iCalendar);
                }
                else if(item.getItemId() == R.id.About){
                    Intent iAbout = new Intent(getApplicationContext(), AboutActivity.class);
                    startActivity(iAbout);
                }
                return false;
            }
        });
//        main_logic
        totalAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().isEmpty()){
                    total_amnt = Float.parseFloat(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                String ss = Float.toString(total_amnt);
            }
        });
        percent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().isEmpty()){
                    String res = s.toString();
                    percentage = Integer.parseInt(res);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
                String ss = Integer.toString(percentage);
            }
        });
        bill_split.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after){
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().isEmpty()){
                    split = Integer.parseInt(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
//        Notification handler
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float decimal = (float)(percentage / 100.0) + 1;
                float res = total_amnt * decimal;
                res = Math.round(res);
                result.setText("Total Amount " + Float.toString(res) + "$");
                if(split != 0 && split > 1){
                    float dividedEach = res / split ;
                    splitted.setText("On Each Person: " + Float.toString(dividedEach) + "$");
                }
                else{
                    splitted.setText("NIL");
                }
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                pushNotification(Float.toString(res));

            }
        });

    }
    private void pushNotification(String ss){
        Notification not = new Notification();
        if(!ss.isEmpty()) {
            not.pushNotification(this,ss);
        }
    }
    private static boolean isclicked = false;
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(toggle.onOptionsItemSelected(item)){
            isclicked = true;
            return isclicked;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putFloat("result",reslt);
        outState.putInt("splits",split);
        outState.putBoolean("ischecked",isclicked);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        reslt = savedInstanceState.getFloat("result");
        split = savedInstanceState.getInt("splits");
        isclicked  = savedInstanceState.getBoolean("isclicked");
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter in = new IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        registerReceiver(new NetworkChange(),in);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Toast.makeText(getApplicationContext(),"BackPressed",Toast.LENGTH_LONG).show();
    }

}
package com.example.alhugailproject191333;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {


    RequestQueue requestQueueq;
    SharedPreferences sharedp;
    ImageView weatherImage;


    //weather method
    @Override
    protected void onResume() {
        super.onResume();
        requestQueueq.add(Helper.weather(this));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainbase);
        requestQueueq= Volley.newRequestQueue(this);
        sharedp= PreferenceManager.getDefaultSharedPreferences(this);

        //button decleration
        Button firebasebtn =findViewById(R.id.firebasebtn);
        Button weatherbtn =findViewById(R.id.weatherbtn);
        Button sqlitebtn =findViewById(R.id.sqlitebtn);

        // button to go to weather activity
        weatherbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,WeatherActivity.class) );
            }
        });
        // button to go to firebase activity
        firebasebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Firebase_Activity.class));
            }
        });
        // button to go to sqlite activity
        sqlitebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Sqlite_Activity.class) );
            }
        }); }
}
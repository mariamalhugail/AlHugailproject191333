package com.example.mobilefinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class WeatherActivity extends AppCompatActivity {
    //-------------------------------//
    EditText cityinput;
    Button citybtn;
    //---------------------------------//
    SharedPreferences sharedPreference;
    RequestQueue requestQueueq;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        requestQueueq= Volley.newRequestQueue(this);
        requestQueueq.add(Helper.weather(this));

        sharedPreference= PreferenceManager.getDefaultSharedPreferences(this);

         cityinput=findViewById(R.id.inp_weather_city);
         citybtn =findViewById(R.id.btn_set_city);
         requestQueueq.add(Helper.weather(this));

         // button :
        citybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newCityString=cityinput.getText()+"";
                if (newCityString.isEmpty()){
                    Toast.makeText(WeatherActivity.this,"Insert a city",Toast.LENGTH_SHORT).show();
                    return;
                }
                SharedPreferences.Editor editor=sharedPreference.edit();
                editor.putString("CityWeather",newCityString);
                editor.commit();
                requestQueueq.add(Helper.weather(WeatherActivity.this));
            }
        });
    }

}
package com.example.mobilefinal;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast; import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import org.json.JSONObject;

public class Helper {


    public static ImageView addWeatherIcon(AppCompatActivity app, @Nullable View.OnClickListener clickListener){
        ActionBar actionbar;
//---------------------------------//
        ImageView img=new ImageView(app);
        SharedPreferences sharedp= PreferenceManager.getDefaultSharedPreferences(app);
//---------------------------------//
        String Icon=sharedp.getString("icon","");
        Glide.with(app).load(Icon).into(img);
//---------------------------------//
        actionbar = app.getSupportActionBar();
        actionbar.setDisplayOptions(actionbar.getDisplayOptions()
                | ActionBar.DISPLAY_SHOW_CUSTOM);

        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams( Gravity.END
                | Gravity.CENTER_VERTICAL);
        layoutParams.rightMargin = 40;
        img.setLayoutParams(layoutParams);
        actionbar.setCustomView(img);
  if (clickListener ==null){
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String city=sharedp.getString("CityWeather","London");
                    float t=sharedp.getFloat("temperature",-1);
                    float h=sharedp.getFloat("humidity",-1);
                    int c=sharedp.getInt("clouds",-1);
                    // alert:
                    AlertDialog.Builder d=new AlertDialog.Builder(app);
                    d.setTitle("The Weather for "+city);
                    String message="Temperature: "+ t+"C\n"+
                            "Humidity: "+h+"% \n"+"" +
                            "clouds: "+c;
                    d.setMessage(message);

                    d.show();
                }
            });
        }else{
            img.setOnClickListener(clickListener);
        }
    return img;
    }
  public static JsonObjectRequest weather(AppCompatActivity app) {
        SharedPreferences sharedp= PreferenceManager.getDefaultSharedPreferences(app);
        String cityweather=sharedp.getString("CityWeather","London");
        String url ="https://api.openweathermap.org/data/2.5/weather?q="+cityweather+"&appid=468d0b72ee9afffaed6350d0bb7aa659&units=metric";

        JsonObjectRequest json = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject weather = response.getJSONObject("main");
                    String iconName= response.getJSONArray("weather").getJSONObject(0).getString("icon");
                    String iconURL="https://openweathermap.org/img/w/"+iconName+".png";

                    SharedPreferences.Editor editor=sharedp.edit();
                    editor.putString("icon",iconURL);
                    editor.putFloat("temp",(float)weather.getDouble("temperature"));
                    editor.putFloat("humidity",(float)weather.getDouble("humidity"));
                    editor.putInt("clouds",response.getJSONObject("clouds").getInt("all"));
                    editor.commit();

                    addWeatherIcon(app,null);
                  } catch (Exception e) {
                    e.printStackTrace();  } }},
                new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
           Toast.makeText(app,"There's a problem: "+error.getMessage(),Toast.LENGTH_SHORT).show();    }  });
  return json;  }
}

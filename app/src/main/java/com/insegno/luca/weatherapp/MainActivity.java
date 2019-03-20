package com.insegno.luca.weatherapp;

import android.os.StrictMode;
import android.preference.EditTextPreference;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.LogPrinter;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

//scrivo a mano
import android.util.Log;
//sopra scritto a mano
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.NumberFormat;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    public void searchCity(View view) {
        String city = ((EditText) findViewById(R.id.city_input)).getText().toString();

        //URL url = new URL("api.openweathermap.org/data/2.5/weather?q=London,uk&APPID=ee95e989b228aa1ea7d1e5a2acf437a8");
        //URL url = new URL("api.openweathermap.org/data/2.5/weather?q=" + city +"&APPID=ee95e989b228aa1ea7d1e5a2acf437a8");
        try {
            URL url = new URL("https://api.openweathermap.org/data/2.5/weather?q=" + city + "&APPID=ee95e989b228aa1ea7d1e5a2acf437a8");

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            BufferedReader buff = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            //Log.i("WEATHER", buff.readLine());
            // poi ci sono gli altri log vedi video

            JSONObject json = new JSONObject(buff.readLine());

            double longitudine = json.getJSONObject("coord").getDouble("lon");
            double latitudine = json.getJSONObject("coord").getDouble("lat");

            double tempK = json.getJSONObject("main").getDouble("temp");
            double tempC = tempK - 273.15;

           String sky =  json.getJSONArray("weather").getJSONObject(0).getString("main");

           // attenzione: le stanghette laterali tutto a destra, se rosse indicano la riga con errore di compilazione

            Log.i ("WEATHER", "latitudine ");// qui vedi video


          //  ((TextView)findViewById(R.id.coords)).setText(String.valueOf((latitudine + ", " +  longitudine));
          //  ((TextView)findViewById(R.id.temp)).setText(String.valueOf((tempC + "°C"));
            NumberFormat formatter = NumberFormat.getInstance();

            ((TextView)findViewById(R.id.city)).setText((city));
            ((TextView)findViewById(R.id.sky)).setText((sky));
            ((TextView)findViewById(R.id.coords)).setText((latitudine + ", " +  longitudine));
              ((TextView)findViewById(R.id.temp)).setText(formatter.format (tempC) + "°C");

            ((EditText)findViewById(R.id.city_input)).setText((""));




            //}catch //(MalformedURLException e){
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }
}

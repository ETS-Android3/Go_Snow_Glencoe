package com.example.gosnow_glencoe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    ImageView snow_report, snow_sports, snow_chat, resort_info, local_business, google_directions;
    TextView topDepth, accessDepth, current_condition, temperature;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        temperature = findViewById(R.id.tempJson);
        current_condition = findViewById(R.id.currentConditionJson);
        topDepth = findViewById(R.id.topDepthJson);
        accessDepth = findViewById(R.id.accessDepthJson);
        snow_report = findViewById(R.id.snowImage);
        snow_sports = findViewById(R.id.sportImage);
        snow_chat = findViewById(R.id.chatImage);
        resort_info = findViewById(R.id.resortImage);
        local_business = findViewById(R.id.localImage);
        google_directions = findViewById(R.id.directionsImage);

        getSnow();
        getWeatherConditions();
    }

    protected void getSnow() {
        String url = "https://api.weatherunlocked.com/api/snowreport/1398?app_id=7d008ca4&app_key=f2fcfd587f47046f1f04f48cb68a00a3";
//Try another snow report API

        JsonObjectRequest snow = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    String upper = response.getString("uppersnow_cm");
                    String lower = response.getString("lowersnow_cm");
                    String conditions = response.getString("conditions");


                    topDepth.setText(upper);
                    accessDepth.setText(lower);
                    current_condition.setText(conditions);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(snow);
    }

    protected void getWeatherConditions() {
        String url = "https://api.darksky.net/forecast/27a87fa552b2a741f881b3ee639b994a/56.6325,-4.8279";

        JsonObjectRequest weather = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jsonResponse = response.getJSONObject("currently");
                    String temp = jsonResponse.getDouble("temperature") + "°C";
                    String summary = jsonResponse.getString("summary");

                    temperature.setText(temp);
                    current_condition.setText(summary);

                    double temp_int = Double.parseDouble(temp); //sets the string value from api to a double variable
                    double centi = (temp_int - 32) / 1.8000; //takes value from temp_int variable -32 then divides by 1.8000 to set centi with new value
                    centi = Math.round(centi); //Will take value from centi variable and use Math library round method to round to nearest whole number
                    int i = (int)centi; //will set i to an integer of value returned from centi variable
                    temperature.setText(String.valueOf(i));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(weather);
    }

//    protected void getResortForecastInfo(){
//        String url = "https://api.weatherunlocked.com/api/resortforecast/1398?app_id=7d008ca4&app_key=f2fcfd587f47046f1f04f48cb68a00a3";
//
//        JsonObjectRequest weather = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//
//
//                try {
//                    String windSpeed = response.getString("resort.forecast.timeframe.base.windspd_mph");
//
//                    wndspd.setText(windSpeed);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
//    }
}




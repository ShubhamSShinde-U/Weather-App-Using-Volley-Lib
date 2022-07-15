package com.example.yourwesther2o;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.StringReader;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
EditText ecity,ecountry;
TextView tresult;

private final String url="https://api.openweathermap.org/data/2.5/weather";
private final  String appid="d8d6cc6027c90c3a7156fe5b8103d665";
DecimalFormat df= new DecimalFormat("#.##");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Your Weather");
        ecity=findViewById(R.id.ecity);
        ecountry=findViewById(R.id.ecountry);
        tresult=findViewById(R.id.tvresult);



    }

    public void getweatherinfo(View view) {
        String temuri="";
        String city=ecity.getText().toString().trim();
        String country=ecountry.getText().toString().trim();
        if(city.equals("")){
            Toast.makeText(getApplicationContext(),"Enter the name of city",Toast.LENGTH_SHORT).show();
            tresult.setText("City name insert krna tha");

        }else {


                temuri=url+"?q="+city+"&appid="+appid;
                StringRequest stringRequest=new StringRequest(Request.Method.GET, temuri, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("responce",response);
                        String output="";
                        try {
                            JSONObject jsonresponce=new JSONObject(response);
                            JSONArray jsonArray= jsonresponce.getJSONArray("weather");
                            JSONObject joweather=jsonArray.getJSONObject(0);
                            String description=joweather.getString("description");
                            JSONObject joMain=jsonresponce.getJSONObject("main");

                            double temp=joMain.getDouble("temp")-273.15;
                            double feels_like=joMain.getDouble("feels_like")-273.15;
                            float pressure=joMain.getInt("pressure");
                            int  humidity=joMain.getInt("humidity");

                            JSONObject jsonWind=jsonresponce.getJSONObject("wind");
                            String wind= jsonWind.getString("speed");

                            JSONObject jsonCloud=jsonresponce.getJSONObject("clouds");
                            String cloud=jsonCloud.getString("all");

                            JSONObject jsonSys= jsonresponce.getJSONObject("sys");
                            String countryName=jsonSys.getString("country");
                            String cityName=jsonresponce.getString("name");


                            output +="Current weather of "+cityName+" ("+countryName+") "
                                    +"\n Tem: "+df.format(temp)+" C"
                                    +"\n Feel Like: "+df.format(feels_like)+" C"
                                    +"\n Humidity: "+df.format(humidity)+"%"
                                    +"\n Description: "+description
                                    +"\n Wind Speed: "+wind
                                    +"\n Cloudiness: "+wind+"m/s"
                                    +"\n Pressure: "+pressure+"hPa";

                            tresult.setText(output);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
                    }
                });
                RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(stringRequest);


        }
    }

    public void goDev(View view) {
        Intent intent= new Intent(getApplicationContext(),developer.class);
        startActivity(intent);
    }
}
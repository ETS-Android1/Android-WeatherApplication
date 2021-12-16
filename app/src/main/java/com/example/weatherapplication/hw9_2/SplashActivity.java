package com.example.weatherapplication.hw9_2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.weatherapplication.R;
import com.joecheatham.simplehttp.SimpleHttp;

import org.json.JSONException;
import org.json.JSONObject;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        
        SimpleHttp.get("https://ipinfo.io/?token=09035606870157", (i, s) -> {
            try {

                JSONObject jsonObject = new JSONObject(s);
                Statics.currentLocation = jsonObject.getString("city") + ","+jsonObject.getString("region");
                new Handler().postDelayed(() -> {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }, 1500);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }
}
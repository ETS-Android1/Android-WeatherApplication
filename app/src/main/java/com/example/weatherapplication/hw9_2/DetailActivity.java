package com.example.weatherapplication.hw9_2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.weatherapplication.R;
import com.example.weatherapplication.hw9_2.ui.main.SectionsPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        findViewById(R.id.twitter_button).setOnClickListener(v -> {
            String date = "";
            String temp = "";
            String weather = "";

            try {
                JSONArray jsonArray = new JSONArray(Statics.weatherDetails);
                JSONObject jsonObject = new JSONObject(jsonArray.get(0).toString());

                date = jsonObject.getString("date");
                temp = "" + Statics.tempInF(jsonObject.getString("temp"));
                weather = Statics.stateMap.get("" + jsonObject.get("status"));
            } catch (JSONException e) {
                e.printStackTrace();
            }


//            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/share?text=The Temperature in " + Statics.locationDetails + " on "+date+" is " + temp + ". The weather conditions are "+weather+"&hashtags=CSCI571WeatherForecast&url= "));
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/share?text=Check Out " + Statics.locationDetails + "’s Weather! It is " + temp + "°F&hashtags=CSCI571WeatherSearch&url= "));
            startActivity(browserIntent);
        });

        TabLayout.Tab tab = tabs.getTabAt(0);
        tab.setCustomView(LayoutInflater.from(getBaseContext()).inflate(R.layout.tabview1, null));

        tab = tabs.getTabAt(1);
        tab.setCustomView(LayoutInflater.from(getApplicationContext()).inflate(R.layout.tabview2, null));

        tab = tabs.getTabAt(2);
        tab.setCustomView(LayoutInflater.from(getApplicationContext()).inflate(R.layout.tabview3, null));

        ((TextView)findViewById(R.id.title)).setText(Statics.locationDetails);
        findViewById(R.id.back_button).setOnClickListener(v -> {
            finish();
        });
    }
}
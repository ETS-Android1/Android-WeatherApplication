package com.example.weatherapplication.hw9_2.ui.main.searchResult;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.weatherapplication.R;
import com.example.weatherapplication.hw9_2.MainActivity;
import com.example.weatherapplication.hw9_2.PlaceTodayWeatherFragment;
import com.example.weatherapplication.hw9_2.Statics;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    boolean plus = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ((TextView)findViewById(R.id.title)).setText(Statics.searchLocation);
        ViewPager viewPager = findViewById(R.id.view_pager);
        findViewById(R.id.back_button).setOnClickListener(v -> {
//            finish();
            startActivity(new Intent(this, MainActivity.class));
        });

        FloatingActionButton fab = findViewById(R.id.plusminus);
        ArrayList<String> location = Statics.prefs;

        if(location.contains(Statics.searchLocation)) {
            fab.setImageResource(R.drawable.map_marker_minus);
            plus = false;
        } else {
            fab.setImageResource(R.drawable.map_marker_plus);
            plus = true;
        }

        fab.setOnClickListener(v -> {
            if(plus) {
                Statics.prefs.add(Statics.searchLocation);
                Toast.makeText(this, Statics.searchLocation + " was added to favourites", Toast.LENGTH_SHORT).show();
            } else {
                Statics.prefs.remove(Statics.searchLocation);
                Toast.makeText(this, Statics.searchLocation + " was removed from favourites", Toast.LENGTH_SHORT).show();
            }
            plus = !plus;
            if(plus) {
                fab.setImageResource(R.drawable.map_marker_plus);
            } else {
                fab.setImageResource(R.drawable.map_marker_minus);
            }
        });


        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return PlaceTodayWeatherFragment.newInstance(1, Statics.searchLocation, null, SearchActivity.this.getApplicationContext());
            }

            @Override
            public int getCount() {
                return 1;
            }
        });
    }
}
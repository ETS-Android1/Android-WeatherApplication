package com.example.weatherapplication.hw9_2;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.weatherapplication.R;
import com.example.weatherapplication.hw9_2.pojo.Suggestion;
import com.example.weatherapplication.hw9_2.ui.main.searchResult.SearchActivity;
import com.joecheatham.simplehttp.SimpleHttp;
import com.joecheatham.simplehttp.SimpleHttpResponseHandler;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SearchView searchView = findViewById(R.id.searchInput);
        ImageButton imageButton = findViewById(R.id.back_button);
        TextView title = findViewById(R.id.title);
        RelativeLayout relativeLayout = findViewById(R.id.appbar_layout);;

        final SearchView.SearchAutoComplete searchAutoComplete = (SearchView.SearchAutoComplete)searchView.findViewById(R.id.search_src_text);
        searchAutoComplete.setBackgroundColor(Color.BLACK);
        searchAutoComplete.setTextColor(Color.WHITE);
        searchAutoComplete.setDropDownBackgroundResource(android.R.color.white);
        // Create a new ArrayAdapter and add data to search auto complete object.
        String dataArr[] = {};
        ArrayAdapter<String> newsAdapter = new ArrayAdapter<String>(this, R.layout.suggestion_adapter, dataArr);
        searchAutoComplete.setAdapter(newsAdapter);

        imageButton.setOnClickListener(v -> {
            searchAutoComplete.setBackgroundColor(Color.BLACK);
            searchView.setBackgroundColor(Color.BLACK);
            relativeLayout.setBackgroundColor(Color.BLACK);
            imageButton.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));
            searchView.setQuery("",false);
            searchView.setIconified(true);
            imageButton.setVisibility(View.GONE);
            title.setVisibility(View.VISIBLE);
        });
        searchView.setOnSearchClickListener(v -> {
            searchAutoComplete.setBackgroundColor(Color.BLACK);
            searchView.setBackgroundColor(Color.BLACK);
            relativeLayout.setBackgroundColor(Color.BLACK);
            imageButton.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));

            imageButton.setVisibility(View.VISIBLE);
            title.setVisibility(View.GONE);
        });

        searchView.setOnCloseListener(() -> {
            searchAutoComplete.setBackgroundColor(Color.BLACK);
            searchView.setBackgroundColor(Color.BLACK);
            relativeLayout.setBackgroundColor(Color.BLACK);
            imageButton.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));
            imageButton.setVisibility(View.GONE);
            title.setVisibility(View.VISIBLE);
            return false;
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//                Toast.makeText(MainActivity.this, "you clicked " + newText, Toast.LENGTH_LONG).show();
                SimpleHttp.get("https://nodejs-329516.wl.r.appspot.com/citysuggestion?term=" + newText, new SimpleHttpResponseHandler() {
                    @Override
                    public void onResponse(int resultCode, String responseBody) {
                        Suggestion s = Suggestion.deserialize(responseBody);
                        newsAdapter.clear();
                        for (String e: s.suggestion) {
                            newsAdapter.add(e);
                        }
                        newsAdapter.notifyDataSetChanged();
                    }
                });
                return false;
            }
        });

        searchAutoComplete.setOnItemClickListener((adapterView, view, itemIndex, id) -> {
//            Toast.makeText(MainActivity.this, "you clicked ", Toast.LENGTH_LONG).show();

            String queryString=(String)adapterView.getItemAtPosition(itemIndex);
            searchAutoComplete.setText("");
            searchView.setIconified(true);

            Statics.searchLocation = "" + queryString;

            startActivity(new Intent(MainActivity.this, SearchActivity.class));
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        ViewPager viewPager = findViewById(R.id.view_pager);

        DotsIndicator dotsIndicator = (DotsIndicator) findViewById(R.id.dots_indicator);
        FragmentPagerAdapter fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                ArrayList<String> location = new ArrayList<>(Statics.prefs);

                if (position == 0) {
                    return PlaceTodayWeatherFragment.newInstance(position, Statics.currentLocation, this, MainActivity.this.getApplicationContext());
                } else {
                    return PlaceTodayWeatherFragment.newInstance(position, location.get(position - 1), this, MainActivity.this.getApplicationContext());
                }
            }

            @Override
            public int getCount() {
                ArrayList<String> location = new ArrayList<>(Statics.prefs);
                return location.size() + 1;
            }

            @Override
            public int getItemPosition(@NonNull Object object) {
                return PagerAdapter.POSITION_NONE;
            }
        };

        viewPager.setAdapter(fragmentPagerAdapter);
        dotsIndicator.setViewPager(viewPager);
    }
}